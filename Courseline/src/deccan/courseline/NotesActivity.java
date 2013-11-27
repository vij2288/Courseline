package deccan.courseline;

import local.DBUtil;
import entities.Course;
import entities.Submission;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NotesActivity extends Activity {

	DBUtil mdb;
	Cursor mCursor;
	String userID;
	String courseID;
	Submission subm = null;
	Course course = null;
	Button b1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deccan_courseline_activity_notes);

		userID = getIntent().getStringExtra("userID");
		Log.d("NOTES", "User ID: " + userID);
		courseID = getIntent().getStringExtra("courseID");
		Log.d("NOTES", "Course ID: " + courseID);
		subm = (Submission) getIntent().getSerializableExtra("subm");
		Log.d("NOTES", "Sub ID: " + subm.getSubId());

		final EditText edt = (EditText) findViewById(R.id.editNotes);

		mCursor = mdb.selectSub(userID, courseID, subm.getSubId());
		if (mCursor.getCount() > 0) {
			mCursor.moveToFirst();
			edt.setText(mCursor.getString(8));
		}

		b1 = (Button) findViewById(R.id.Editbutton);

		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String notes = edt.getText().toString();
				mCursor = mdb.selectSub(userID, courseID, subm.getSubId());
				if (mCursor.getCount() > 0) {
					mCursor.moveToFirst();
					mdb.updateSub(userID, courseID, subm.getSubId(),
							mCursor.getBlob(3), mCursor.getBlob(4),
							mCursor.getBlob(5), mCursor.getBlob(6),
							mCursor.getBlob(7), notes);
				} else {
					mdb.insertSub(userID, courseID, subm.getSubId(), null,
							null, null, null, null, notes);
				}
				Toast toast = Toast.makeText(getBaseContext(),
						"Your new note for this submission has been saved",
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notes, menu);
		return true;
	}

}
