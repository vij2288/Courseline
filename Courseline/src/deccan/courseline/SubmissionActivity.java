package deccan.courseline;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import local.DBUtil;
import entities.Course;
import entities.Notes;
import entities.Submission;
import android.R.color;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActionBar.LayoutParams;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SubmissionActivity extends Activity {

	DBUtil mdb;
	Cursor mCursor;
	String userID;
	String courseID;
	Submission subm = null;
	Course course = null;

	private static final int CAMERA_REQUEST = 1;
	private static final int PICK_FROM_GALLERY = 2;
	ListView dataList;
	byte[] imageName;
	Bitmap theImage;
	NotesAdapter imageAdapter;

	ArrayList<Notes> imageArry = new ArrayList<Notes>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deccan_courseline_activity_submission);

		mdb = new DBUtil(this);

		userID = getIntent().getStringExtra("userID");
		Log.d("SUBM", "User ID: " + userID);
		courseID = getIntent().getStringExtra("courseID");
		Log.d("SUBM", "Course ID: " + courseID);
		subm = (Submission) getIntent().getSerializableExtra("subm");
		Log.d("SUBM", "Sub ID: " + subm.getSubId());
		course = mdb.selectCourse(courseID);

	}

	@Override
	protected void onResume() {
		super.onResume();

		if (subm != null && course != null) {
			Log.d("SUBM", "Inside on resume's condition ");
			String subHead = getString(R.string.sub_head);
			subHead = "SUBMISSION";
			((TextView) findViewById(R.id.sub_head)).setText(subHead);
			TableLayout subTable = (TableLayout) findViewById(R.id.subTable);
			subTable.removeAllViews();
			String s1 = null, s2 = null;

			for (int i = 0; i < 11; i++) {
				switch (i) {
				case 0:
					s1 = "Course Name";
					s2 = course.getCourseName();
					break;
				case 1:
					s1 = "Course ID";
					s2 = course.getCourseNumber();
					break;
				case 2:
					s1 = "Submission ID";
					s2 = Integer.toString(subm.getSubId());
					break;
				case 3:
					s1 = "Submission Name";
					s2 = subm.getSubName();
					break;
				case 4:
					s1 = "Submission Type";
					s2 = subm.getSubType().toString();
					break;
				case 5:
					s1 = "Release Date";
					s2 = subm.getReleaseDate().toString();
					break;
				case 6:
					s1 = "Due Date";
					s2 = subm.getDueDate().toString();
					break;
				case 7:
					s1 = "Weight %";
					s2 = Integer.toString(subm.getWeightPercent());
					break;
				case 8:
					s1 = "Weight Points";
					s2 = Integer.toString(subm.getWeightPoints());
					break;
				case 9:
					s1 = "Description";
					s2 = subm.getDescription();
					break;
				case 10:
					s1 = "Your Notes";
					mCursor = mdb.selectSub(userID, courseID, subm.getSubId());
					if (mCursor.getCount() > 0) {
						mCursor.moveToFirst();
						s2 = mCursor.getString(8);
						Log.d("SUBM", "Notes" + s2);
					} else {
						s2 = "(NO NOTES ADDED)";
					}
					break;
				}
				displaySubmission(subTable, s1, s2);
			}

			Log.d("SUBM", "Displayed everything except buttons ");
			// for pics
			TableRow row = new TableRow(getBaseContext());
			/*
			 * TextView t123 = new TextView(getBaseContext());
			 * t123.setText("Add note to this Submission");
			 * t123.setTextSize(25); row.addView(t123);
			 */
			Button b1 = new Button(getBaseContext());
			b1.setText("Add Notes");
			/*
			 * LinearLayout.LayoutParams layoutParams = new
			 * LinearLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
			 * LayoutParams.WRAP_CONTENT);
			 */
			// b1.setLayoutParams (layoutParams);

			// b1.setScaleY(20);
			row.addView(b1);
			subTable.addView(row, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			b1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(getBaseContext(), NotesActivity.class);
					i.putExtra("userID", userID);
					i.putExtra("courseID", courseID);
					i.putExtra("subm", subm);
					startActivityForResult(i, 500);
					overridePendingTransition(R.anim.slide_in_right,
							R.anim.slide_out_left);

				}
			});

			Log.d("SUBM", "Going to display the pictures");
			TableRow row1 = new TableRow(getBaseContext());
			/*
			 * TextView t = new TextView(getBaseContext());
			 * t.setText("Your Note Images"); t.setTextSize(25);
			 */
			// t.setTextColor(color.holo_red_dark);
			Button b2 = new Button(getBaseContext());
			b2.setText("Add Image");
			// b2.setLayoutParams (new LayoutParams(LayoutParams.WRAP_CONTENT,
			// LayoutParams.WRAP_CONTENT));
			// b2.setScaleY(20);
			// row1.addView(t);
			row1.addView(b2);
			subTable.addView(row1, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			dataList = (ListView) findViewById(R.id.list);
			// List<Notes> notes;

			mCursor = mdb.selectSub(userID, courseID, subm.getSubId());
			if (mCursor.getCount() > 0) {
				Log.d("SUBM", "Checking users pic notes");
				mCursor.moveToFirst();
				int i = 3;
				int j = 1;
				while (mCursor.getBlob(i) != null) {
					Log.d("SUBM", "User has previous notes");
					Notes n = new Notes();
					n.setImage(mCursor.getBlob(i));
					n.setName(Integer.toString(j));
					imageArry.add(n);
					i++;
					j++;
				}
			}

			imageAdapter = new NotesAdapter(this,
					R.layout.deccan_courseline_notes, imageArry);
			dataList.setAdapter(imageAdapter);

			dataList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						final int position, long id) {
					imageName = imageArry.get(position).getImage();
					String name = imageArry.get(position).getName();
					ByteArrayInputStream imageStream = new ByteArrayInputStream(
							imageName);
					theImage = BitmapFactory.decodeStream(imageStream);
					Intent intent = new Intent(getBaseContext(),
							ImageActivity.class);
					intent.putExtra("imagename", theImage);
					intent.putExtra("imageid", name);
					intent.putExtra("userID", userID);
					intent.putExtra("courseID", courseID);
					intent.putExtra("subm", subm);
					startActivity(intent);
				}
			});

			final String[] option = new String[] { "Take from Camera",
					"Select from Gallery" };
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.select_dialog_item, option);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Select Option");
			builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Log.d("Selected Item", String.valueOf(which));
					if (which == 0) {
						callCamera();
					}
					if (which == 1) {
						callGallery();
					}

				}
			});

			final AlertDialog dialog = builder.create();
			b2.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Log.d("SUBM", "user wants to add a pic note");
					dialog.show();
				}
			});
		}

	}

	@Override
	protected void onRestart() {
		super.onRestart();

	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subject, menu);
		return true;
	}

	public void displaySubmission(TableLayout subTable, String s1, String s2) {
		TableRow row = new TableRow(getBaseContext());
		TextView t1 = new TextView(getBaseContext());
		TextView t2 = new TextView(getBaseContext());
		t1.setText(s1);
		t1.setTextSize(25);
		row.addView(t1);
		t2.setText(s2);
		t2.setTextSize(25);
		row.addView(t2);
		subTable.addView(row, new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	}

	public void callCamera() {
		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		Log.d("SUBM", "Calling the camera Image capture intent");
		cameraIntent.putExtra("crop", "true");
		cameraIntent.putExtra("aspectX", 0);
		cameraIntent.putExtra("aspectY", 0);
		cameraIntent.putExtra("outputX", 400);
		cameraIntent.putExtra("outputY", 300);
		startActivityForResult(cameraIntent, CAMERA_REQUEST);

	}

	public void callGallery() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 0);
		intent.putExtra("aspectY", 0);
		intent.putExtra("outputX", 400);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		startActivityForResult(
				Intent.createChooser(intent, "Complete action using"),
				PICK_FROM_GALLERY);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

		if (resultCode != RESULT_OK) {
			Log.d("SUBM", "Camera Or gallery result wasn't ok");
			return;
		}

		switch (requestCode) {
		case CAMERA_REQUEST:
			Log.d("SUBM", "result was ok, checking for the result in camera");
			Bundle extras = data.getExtras();
			if (extras != null) {
				Log.d("SUBM",
						"we have an image, going to store it in the database");
				Bitmap yourImage = extras.getParcelable("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte imageInByte[] = stream.toByteArray();
				mCursor = mdb.selectSub(userID, courseID, subm.getSubId());
				if (mCursor.getCount() > 0) {
					Log.d("SUBM", "Going to edit " + mCursor.getCount()
							+ " record");
					mCursor.moveToFirst();
					int i = 3;
					while (mCursor.getBlob(i) != null) {
						i++;
					}
					Log.d("SUBM", "After the while the value of i is " + i);
					switch (i) {
					case 3:
						Log.d("SUBM", "Updating first pic note");
						mdb.updateSub(userID, courseID, subm.getSubId(),
								imageInByte, null, null, null, null,
								mCursor.getString(8));
						break;
					case 4:
						Log.d("SUBM", "Updating second pic note");
						mdb.updateSub(userID, courseID, subm.getSubId(),
								mCursor.getBlob(3), imageInByte, null, null,
								null, mCursor.getString(8));
						break;
					case 5:
						Log.d("SUBM", "Updating third pic note");
						mdb.updateSub(userID, courseID, subm.getSubId(),
								mCursor.getBlob(3), mCursor.getBlob(4),
								imageInByte, null, null, mCursor.getString(8));
						break;
					case 6:
						Log.d("SUBM", "Updating fourth pic note");
						mdb.updateSub(userID, courseID, subm.getSubId(),
								mCursor.getBlob(3), mCursor.getBlob(4),
								mCursor.getBlob(5), imageInByte, null,
								mCursor.getString(8));
						break;
					case 7:
						Log.d("SUBM", "Updating fifth pic note");
						mdb.updateSub(userID, courseID, subm.getSubId(),
								mCursor.getBlob(3), mCursor.getBlob(4),
								mCursor.getBlob(5), mCursor.getBlob(6),
								imageInByte, mCursor.getString(8));
						break;
					case 8:
						Log.d("SUBM", "user already has 5 images");
						break;
					}

				} else {
					Log.d("SUBM",
							"New submission record for this user is created");
					mdb.insertSub(userID, courseID, subm.getSubId(),
							imageInByte, null, null, null, null, null);
				}

			} else {
				Log.d("SUBM", "Camera didn't return any image");
			}
			break;
		case PICK_FROM_GALLERY:
			Log.d("SUBM", "result was ok, checking for the result in gallery");
			Bundle extras2 = data.getExtras();
			if (extras2 != null) {
				Log.d("SUBM",
						"we have an image, going to store it in the database");
				Bitmap yourImage = extras2.getParcelable("data");
				// convert bitmap to byte
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte imageInByte[] = stream.toByteArray();
				mCursor = mdb.selectSub(userID, courseID, subm.getSubId());
				if (mCursor.getCount() > 0) {
					Log.d("SUBM", "Going to edit " + mCursor.getCount()
							+ " record");
					mCursor.moveToFirst();
					int i = 3;
					while (mCursor.getBlob(i) != null) {
						i++;
					}
					Log.d("SUBM", "After the while the value of i is " + i);
					switch (i) {
					case 3:
						Log.d("SUBM", "Updating first pic note");
						mdb.updateSub(userID, courseID, subm.getSubId(),
								imageInByte, null, null, null, null,
								mCursor.getString(8));
						break;
					case 4:
						Log.d("SUBM", "Updating second pic note");
						mdb.updateSub(userID, courseID, subm.getSubId(),
								mCursor.getBlob(3), imageInByte, null, null,
								null, mCursor.getString(8));
						break;
					case 5:
						Log.d("SUBM", "Updating third pic note");
						mdb.updateSub(userID, courseID, subm.getSubId(),
								mCursor.getBlob(3), mCursor.getBlob(4),
								imageInByte, null, null, mCursor.getString(8));
						break;
					case 6:
						Log.d("SUBM", "Updating fourth pic note");
						mdb.updateSub(userID, courseID, subm.getSubId(),
								mCursor.getBlob(3), mCursor.getBlob(4),
								mCursor.getBlob(5), imageInByte, null,
								mCursor.getString(8));
						break;
					case 7:
						Log.d("SUBM", "Updating fifth pic note");
						mdb.updateSub(userID, courseID, subm.getSubId(),
								mCursor.getBlob(3), mCursor.getBlob(4),
								mCursor.getBlob(5), mCursor.getBlob(6),
								imageInByte, mCursor.getString(8));
						break;
					case 8:
						Log.d("SUBM", "user already has 5 images");
						break;
					}

				} else {
					Log.d("SUBM",
							"New submission record for this user is created");
					mdb.insertSub(userID, courseID, subm.getSubId(),
							imageInByte, null, null, null, null, null);
				}

			} else {
				Log.d("SUBM", "Gallery didn't return any image");
			}
			break;

		}
	}

}
