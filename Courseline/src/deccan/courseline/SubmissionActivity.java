package deccan.courseline;

import local.DBUtil;
import entities.Course;
import entities.Submission;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SubmissionActivity extends Activity {

	DBUtil mdb;
	Cursor mCursor;
	String userID;
	String courseID;
	Submission subm = null;
	Course course = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deccan_courseline_activity_submission);
		
		mdb=new DBUtil(this);

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
						Log.d("SUBM", "Notes"+s2);
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
			Button b1 = new Button(getBaseContext());
			b1.setText("Add Notes");
			row.addView(b1);
			subTable.addView(row, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			b1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(getBaseContext(),NotesActivity.class);
					i.putExtra("userID",userID);
					i.putExtra("courseID",courseID);
					i.putExtra("subm", subm);
					startActivity(i);
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

}
