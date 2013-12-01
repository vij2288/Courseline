package deccan.courseline;

import local.DBUtil;
import entities.Course;
import entities.Submission;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageActivity extends Activity {

	DBUtil mdb;
	Cursor mCursor;
	String userID;
	String courseID;
	Submission subm = null;
	Course course = null;

	Button btnDelete;
	ImageView imageDetail;
	String imageId;
	Bitmap theImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("View Image");
		getActionBar().setIcon(R.drawable.tbar_icon);
		setContentView(R.layout.deccan_courseline_activity_image);
		btnDelete = (Button) findViewById(R.id.buttondelete);
		imageDetail = (ImageView) findViewById(R.id.imagenotes);

		mdb = new DBUtil(this);

		userID = getIntent().getStringExtra("userID");
		Log.d("IMAGE-NOTES", "User ID: " + userID);
		courseID = getIntent().getStringExtra("courseID");
		Log.d("IMAGE-NOTES", "Course ID: " + courseID);
		subm = (Submission) getIntent().getSerializableExtra("subm");
		Log.d("IMAGE-NOTES", "Sub ID: " + subm.getSubId());
		course = mdb.selectCourse(courseID);

		Intent intnt = getIntent();
		theImage = (Bitmap) intnt.getParcelableExtra("imagename");
		imageId = intnt.getStringExtra("imageid");
		Log.d("IMAGE-NOTES", "Sub ID: " + imageId);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image, menu);
		return true;
	}
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
	}

	@Override
	protected void onResume() {
		super.onResume();
		imageDetail.setImageBitmap(theImage);
		btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int i = Integer.parseInt(imageId);
				mCursor = mdb.selectSub(userID, courseID, subm.getSubId());
				if (mCursor.getCount() > 0) {
					mCursor.moveToFirst();
					switch (i) {
					case 1:
						mdb.updateSub(userID, courseID, subm.getSubId(),
								mCursor.getBlob(4), mCursor.getBlob(5),
								mCursor.getBlob(6), mCursor.getBlob(7), null,
								mCursor.getString(8));
						break;
					case 2:
						mdb.updateSub(userID, courseID, subm.getSubId(),
								mCursor.getBlob(3), mCursor.getBlob(5),
								mCursor.getBlob(6), mCursor.getBlob(7), null,
								mCursor.getString(8));
						break;
					case 3:
						mdb.updateSub(userID, courseID, subm.getSubId(),
								mCursor.getBlob(3), mCursor.getBlob(4),
								mCursor.getBlob(6), mCursor.getBlob(7), null,
								mCursor.getString(8));
						break;
					case 4:
						mdb.updateSub(userID, courseID, subm.getSubId(),
								mCursor.getBlob(3), mCursor.getBlob(4),
								mCursor.getBlob(5), mCursor.getBlob(7), null,
								mCursor.getString(8));
						break;
					case 5:
						mdb.updateSub(userID, courseID, subm.getSubId(),
								mCursor.getBlob(3), mCursor.getBlob(4),
								mCursor.getBlob(5), mCursor.getBlob(6), null,
								mCursor.getString(8));
						break;
					}
					Toast toast = Toast.makeText(
							getBaseContext(),
							"Note Pic Deleted",
							Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0,
							0);
					toast.show();
				}
				onBackPressed();
			}
		});


	}

}
