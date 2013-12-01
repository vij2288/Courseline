package deccan.courseline;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import local.DBUtil;
import entities.Course;
import entities.Notes;
import entities.Submission;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
		setTitle("Submission Details");
		getActionBar().setIcon(R.drawable.tbar_icon);
		setContentView(R.layout.deccan_courseline_activity_submission);
		// dataList = (ListView) findViewById(R.id.list);

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
			/*
			 * String subHead = getString(R.string.sub_head); subHead =
			 * "SUBMISSION"; ((TextView)
			 * findViewById(R.id.sub_head)).setText(subHead);
			 */
			TableLayout subTable = (TableLayout) findViewById(R.id.subTable);
			subTable.removeAllViews();
			/*
			 * RelativeLayout.LayoutParams parm =
			 * (RelativeLayout.LayoutParams)subTable.getLayoutParams();
			 * parm.width = 600;//RelativeLayout.LayoutParams.MATCH_PARENT;
			 * subTable.setLayoutParams(parm);
			 */String s1 = null, s2 = null;

			s1 = course.getCourseName();
			TableRow row = new TableRow(getBaseContext());
			TextView t1 = new TextView(getBaseContext());
			t1.setText(s1);
			t1.setTextSize(25);
			t1.setTextColor(Color.DKGRAY);
			t1.setTypeface(t1.getTypeface(), Typeface.BOLD);
			row.addView(t1);
			subTable.addView(row, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

			s1 = subm.getSubName();
			row = new TableRow(getBaseContext());
			t1 = new TextView(getBaseContext());
			t1.setText(s1);
			t1.setTextSize(50);
			t1.setWidth(0);
			TableRow.LayoutParams params1 = new TableRow.LayoutParams();
			params1.weight = 1.0f;
			t1.setLayoutParams(params1);
			t1.setPadding(0, 10, 0, 0);
			t1.setTextColor(Color.rgb(0x2f, 0x66, 0x99));
			t1.setTypeface(t1.getTypeface(), Typeface.BOLD);
			row.addView(t1);
			subTable.addView(row, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

			s1 = "Released on";
			s2 = subm.getReleaseDate().toString();
			row = new TableRow(getBaseContext());
			t1 = new TextView(getBaseContext());
			t1.setText(s1);
			t1.setTextSize(25);
			t1.setTextColor(Color.BLACK);
			// t1.setTypeface(t1.getTypeface(), Typeface.BOLD);
			row.addView(t1);
			TextView t2 = new TextView(getBaseContext());
			t2 = new TextView(getBaseContext());
			t2.setText(s2);
			t2.setTextSize(25);
			t2.setTextColor(Color.BLACK);
			t2.setTypeface(t2.getTypeface(), Typeface.BOLD);
			row.addView(t2);
			subTable.addView(row, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

			s1 = "Due on";
			s2 = subm.getDueDate().toString();
			row = new TableRow(getBaseContext());
			t1 = new TextView(getBaseContext());
			t1.setText(s1);
			t1.setTextSize(25);
			t1.setTextColor(Color.BLACK);
			// t1.setTypeface(t1.getTypeface(), Typeface.BOLD);
			row.addView(t1);
			t2 = new TextView(getBaseContext());
			t2 = new TextView(getBaseContext());
			t2.setText(s2);
			t2.setTextSize(25);
			t2.setTextColor(Color.BLACK);
			t2.setTypeface(t2.getTypeface(), Typeface.BOLD);
			row.addView(t2);
			subTable.addView(row, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

			s1 = "Weightage";
			s2 = Integer.toString(subm.getWeightPercent()) + "%";
			row = new TableRow(getBaseContext());
			t1 = new TextView(getBaseContext());
			t1.setText(s1);
			t1.setTextSize(25);
			t1.setTextColor(Color.BLACK);
			// t1.setTypeface(t1.getTypeface(), Typeface.BOLD);
			row.addView(t1);
			t2 = new TextView(getBaseContext());
			t2 = new TextView(getBaseContext());
			t2.setText(s2);
			t2.setTextSize(25);
			t2.setTextColor(Color.BLACK);
			t2.setTypeface(t2.getTypeface(), Typeface.BOLD);
			row.addView(t2);
			subTable.addView(row, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

			s1 = "Description";
			row = new TableRow(getBaseContext());
			t1 = new TextView(getBaseContext());
			t1.setText(s1);
			t1.setTextSize(35);
			params1 = new TableRow.LayoutParams();
			params1.weight = 1.0f;
			t1.setLayoutParams(params1);
			t1.setPadding(0, 20, 0, 0);
			t1.setTextColor(Color.GRAY);
			t1.setTypeface(t1.getTypeface(), Typeface.BOLD);
			row.addView(t1);
			subTable.addView(row, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

			s1 = subm.getDescription();
			row = new TableRow(getBaseContext());
			t1 = new TextView(getBaseContext());
			t1.setText(s1);
			t1.setTextSize(20);
			t1.setWidth(0);

			params1 = new TableRow.LayoutParams();
			params1.weight = 1.0f;
			t1.setLayoutParams(params1);

			t1.setTextColor(Color.BLACK);
			row.addView(t1);
			subTable.addView(row, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

			s1 = "Personal Note";
			row = new TableRow(getBaseContext());
			t1 = new TextView(getBaseContext());
			t1.setText(s1);
			t1.setTextSize(35);
			t1.setWidth(0);
			params1 = new TableRow.LayoutParams();
			params1.weight = 1.0f;
			t1.setLayoutParams(params1);
			t1.setPadding(0, 20, 0, 0);
			t1.setTextColor(Color.GRAY);
			t1.setTypeface(t1.getTypeface(), Typeface.BOLD);
			row.addView(t1);
			subTable.addView(row, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

			mCursor = mdb.selectSub(userID, courseID, subm.getSubId());
			if (mCursor.getCount() > 0) {
				mCursor.moveToFirst();
				s1 = mCursor.getString(8);
				Log.d("SUBM", "Notes" + s1);
			} else {
				s1 = "(NO NOTES ADDED)";
			}
			row = new TableRow(getBaseContext());
			t1 = new TextView(getBaseContext());
			t1.setText(s1);
			t1.setTextSize(20);
			t1.setWidth(0);

			params1.weight = 1.0f;
			t1.setLayoutParams(params1);
			t1.setTextColor(Color.BLACK);
			row.addView(t1);
			subTable.addView(row, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

			Log.d("SUBM", "Displayed everything except buttons ");
			// Notes button
			row = new TableRow(getBaseContext());

			Button b1 = new Button(getBaseContext());
			TableRow.LayoutParams params = new TableRow.LayoutParams();
			params.weight = 1.0f;
			b1.setPadding(6, 6, 6, 6);
			b1.setLayoutParams(params);
			b1.setTextSize(25);
			b1.setWidth(0);
			b1.setTypeface(null, Typeface.BOLD);
			b1.setTextColor(Color.WHITE);
			b1.setBackground(getResources().getDrawable(
					(R.drawable.blue_menu_btn)));
			b1.setText("Manage Note");
			row.setPadding(0, 6, 400, 6);
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

			s1 = "Pictures";
			row = new TableRow(getBaseContext());
			t1 = new TextView(getBaseContext());
			t1.setText(s1);
			t1.setTextSize(35);
			t1.setWidth(0);
			params1 = new TableRow.LayoutParams();
			params1.weight = 1.0f;
			t1.setLayoutParams(params1);
			t1.setPadding(0, 20, 0, 0);
			t1.setTextColor(Color.GRAY);
			t1.setTypeface(t1.getTypeface(), Typeface.BOLD);
			row.addView(t1);
			subTable.addView(row, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

			// Pics button
			TableRow row1 = new TableRow(getBaseContext());
			Button b2 = new Button(getBaseContext());
			params = new TableRow.LayoutParams();
			params.weight = 0.5f;
			b2.setPadding(6, 100, 6, 6);
			b2.setLayoutParams(params);
			b2.setTextSize(25);
			b2.setWidth(0);
			b2.setTypeface(null, Typeface.BOLD);
			b2.setTextColor(Color.WHITE);
			b2.setBackground(getResources().getDrawable(
					(R.drawable.blue_menu_btn)));
			b2.setText("Manage Pics");
			row1.setPadding(0, 6, 400, 6);
			row1.addView(b2);
			subTable.addView(row1, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

			// manage pics
			b2.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent sub2pics = new Intent(getBaseContext(),
							PicsActivity.class);
					sub2pics.putExtra("userID", userID);
					sub2pics.putExtra("courseID", courseID);
					sub2pics.putExtra("subm", subm);
					startActivity(sub2pics);
				}
			});

			// Email button
			row1 = new TableRow(getBaseContext());
			b2 = new Button(getBaseContext());
			params = new TableRow.LayoutParams();
			params.weight = 0.5f;
			b2.setPadding(6, 100, 6, 6);
			b2.setLayoutParams(params);
			b2.setTextSize(25);
			b2.setWidth(0);
			b2.setTypeface(null, Typeface.BOLD);
			b2.setTextColor(Color.WHITE);
			b2.setBackground(getResources().getDrawable(
					(R.drawable.blue_menu_btn)));
			b2.setText("Email Instructors");
			row1.setPadding(0, 6, 400, 6);
			row1.addView(b2);
			subTable.addView(row1, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

			// email instructor
			b2.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(Intent.ACTION_SEND);
					i.setType("message/rfc822");
					i.putExtra(Intent.EXTRA_EMAIL,
							new String[] { course.getEmail() });
					i.putExtra(Intent.EXTRA_SUBJECT, subm.getSubName()
							+ " Query");
					try {
						startActivity(Intent.createChooser(i, "Send mail..."));

					} catch (android.content.ActivityNotFoundException ex) {
						Toast toast = Toast.makeText(getBaseContext(),
								"No Email Client Installed", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
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
