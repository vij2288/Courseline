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
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PicsActivity extends Activity {

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
		setTitle("Manage Pics");
		getActionBar().setIcon(R.drawable.tbar_icon);
		setContentView(R.layout.manage_pics);

		mdb = new DBUtil(this);

		userID = getIntent().getStringExtra("userID");
		Log.d("SUBM", "User ID: " + userID);
		courseID = getIntent().getStringExtra("courseID");
		Log.d("SUBM", "Course ID: " + courseID);
		subm = (Submission) getIntent().getSerializableExtra("subm");
		Log.d("SUBM", "Sub ID: " + subm.getSubId());
		course = mdb.selectCourse(courseID);

		// add pics button listener
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

		Button b2 = (Button) findViewById(R.id.addPics);
		b2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("SUBM", "user wants to add a pic note");
				dialog.show();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (subm != null && course != null) {
			// images list
			imageArry.clear();
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
			Log.d("SUBM", "Imagearray's size " + imageArry.size());

			imageAdapter = new NotesAdapter(this,
					R.layout.deccan_courseline_notes, imageArry);
			dataList = (ListView) findViewById(R.id.list);
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
					Intent pics2img = new Intent(getBaseContext(),
							ImageActivity.class);
					pics2img.putExtra("imagename", theImage);
					pics2img.putExtra("imageid", name);
					pics2img.putExtra("userID", userID);
					pics2img.putExtra("courseID", courseID);
					pics2img.putExtra("subm", subm);
					startActivity(pics2img);
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
		Intent galleryIntent = new Intent();
		galleryIntent.setType("image/*");
		galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
		galleryIntent.putExtra("crop", "true");
		galleryIntent.putExtra("aspectX", 0);
		galleryIntent.putExtra("aspectY", 0);
		galleryIntent.putExtra("outputX", 400);
		galleryIntent.putExtra("outputY", 300);
		galleryIntent.putExtra("return-data", true);
		startActivityForResult(
				Intent.createChooser(galleryIntent, "Complete action using"),
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
