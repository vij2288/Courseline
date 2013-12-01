package deccan.courseline;

import java.util.HashMap;
import java.util.Iterator;

import local.DBUtil;
import entities.Course;
import entities.SubType;
import entities.Submission;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class HomeActivity extends FragmentActivity {

	FragmentManager fragmentManager = getSupportFragmentManager();
	FragmentTransaction fragmentTransaction = fragmentManager
			.beginTransaction();
	ChartFragment fragment = new ChartFragment();
	public String userID = null;
	DBUtil mdb;
	Cursor mCursor;
	int[] color = new int[] { 0xff660066, 0xffff0000, 0xff006633, 0xffff007f, 0xff0000ff};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Dashboard");
		getActionBar().setIcon(R.drawable.tbar_icon);
		setContentView(R.layout.deccan_courseline_activity_home);

		userID = getIntent().getStringExtra("userID");

		// Student student = (Student)
		// getIntent().getSerializableExtra("student");
		/*
		 * ChartFragment chart = new ChartFragment();
		 * getSupportFragmentManager().beginTransaction()
		 * .replace(R.id.chartView, chart).commit();
		 */

		// display "Filters:"
		TableRow name = (TableRow) findViewById(R.id.rowName);
		TextView t = new TextView(this);
		t.setText("Filters:");
		t.setTextSize(35);
		t.setTypeface(Typeface.DEFAULT_BOLD);
		t.setPadding(25, 0, 0, 0);
		name.addView(t);

		Button add = (Button) findViewById(R.id.add);
		Button delete = (Button) findViewById(R.id.delete);

		// Add/Delete Button click listeners
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent home2add = new Intent(getBaseContext(),
						AdditionActivity.class);
				home2add.putExtra("userID", userID);
				startActivityForResult(home2add, 500);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			}
		});

		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent home2delete = new Intent(getBaseContext(),
						DeletionActivity.class);
				home2delete.putExtra("userID", userID);
				startActivityForResult(home2delete, 500);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			}
		});

		fragmentTransaction.replace(R.id.chartView, fragment);
		fragmentTransaction.commit();
		fragment.userID = new String(userID);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// initialize subm present hashmap
		HashMap<SubType, Boolean> submPresentMap = new HashMap<SubType, Boolean>();
		for (SubType type : SubType.values()) {
			submPresentMap.put(type, Boolean.FALSE);
		}

		mdb = new DBUtil(this);
		// display courses & checkboxes
		TableLayout courses = (TableLayout) findViewById(R.id.tableCourses);
		courses.removeAllViews();
		mCursor = mdb.selectUser(userID);
		Log.d("HOME", "got mCursor");
		if (mCursor.getCount() > 0) {
			mCursor.moveToFirst();
			int count = mCursor.getInt(8);
			int i = 0;
			while (i != count) {
				Log.d("HOME", "displaying courses/checkboxes " + i);
				String c_id = mCursor.getString(2 + i);
				Course course = null;
				course = mdb.selectCourse(c_id);
				Iterator<Submission> it = course.submissions.iterator();
				while (it.hasNext()) {
					Submission sub = it.next();
					submPresentMap.put(sub.getSubType(), Boolean.TRUE);
				}

				TableRow row1 = new TableRow(this);
				TextView t1 = new TextView(this);
				t1.setText(c_id);
				t1.setTextSize(25);
				t1.setTextColor(color[i]);
				row1.addView(t1);
				CheckBox c1 = new CheckBox(this);
				c1.setChecked(true);
				//c1.setPadding(0, 0, 40, 0);
				row1.addView(c1);
				row1.setPadding(25, 0, 40, 0);
				courses.addView(row1, new TableLayout.LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

				final int c_num = i;
				// Install CheckBox listener
				c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						fragment.crs[c_num] = isChecked;
						fragment.drawChart();
					}
				});

				i++;
			}
		}

		// display submission filters
		TableLayout submissions = (TableLayout) findViewById(R.id.tableSubmissions);
		submissions.removeAllViews();
		int i = 0;
		TableRow row1 = null;
		for (SubType type : SubType.values()) {
			if (i % 2 == 0) {
				row1 = new TableRow(this);
			}
			TextView t1 = new TextView(this);
			if (type == SubType.QUIZ)
				t1.setText(type.toString().replace("_", " ") + "ZES");
			else
				t1.setText(type.toString().replace("_", " ") + "S");
			t1.setTextSize(20);
			t1.setPadding(20, 0, 0, 0);
			row1.addView(t1);
			CheckBox c1 = new CheckBox(this);

			if (submPresentMap.get(type) == Boolean.FALSE) {
				t1.setTextColor(Color.GRAY);
				c1.setClickable(false);
				c1.setChecked(false);
			} else {
				c1.setChecked(true);
			}

			row1.addView(c1);

			final int subtype_num = i;
			// Install CheckBox listener
			c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					fragment.subs[subtype_num] = isChecked;
					fragment.drawChart();
				}
			});

			
			if (i % 2 == 1) {
				submissions.addView(row1, new TableLayout.LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			}
			i++;
		}
		if (i % 2 == 1) {
			submissions.addView(row1, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		}

		mdb.close();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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

	/*
	 * @Override public void onBackPressed() { this.finish();
	 * 
	 * }
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
