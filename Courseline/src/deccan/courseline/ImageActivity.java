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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

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
		
		imageDetail.setImageBitmap(theImage);
		btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		
	}

}
