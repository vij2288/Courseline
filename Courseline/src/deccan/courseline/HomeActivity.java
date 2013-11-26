package deccan.courseline;

import local.DBUtil;
import entities.Student;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.database.Cursor;
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
	FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	ChartFragment fragment = new ChartFragment();
	String userID = null;
	DBUtil mdb;
	Cursor mCursor;
	int value = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deccan_courseline_activity_home);

		userID = getIntent().getStringExtra("userID");
		
//		Student student = (Student) getIntent().getSerializableExtra("student");
	/*	
		ChartFragment chart = new ChartFragment();		
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.chartView, chart).commit();
	*/	

		//mdb = new DBUtil(this);
		
		//TableLayout filters = (TableLayout) findViewById(R.id.tableFilters);
		TableRow name = (TableRow) findViewById(R.id.rowName);
		TextView t = new TextView(this);
		t.setText("Filters:");
		t.setTextSize(35);
		name.addView(t);
//		filters.addView(name, new TableLayout.LayoutParams(
  //              LayoutParams.FILL_PARENT,
    //            LayoutParams.WRAP_CONTENT));
		
		TableLayout courses = (TableLayout) findViewById(R.id.tableCourses);
		TableRow row1 = new TableRow(this);
		TextView t1 = new TextView(this);
		t1.setText("15-213");
		t1.setTextSize(25);
		row1.addView(t1);
		CheckBox c1 = new CheckBox(this);
		c1.setChecked(true);
		row1.addView(c1);
		courses.addView(row1, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
		
		TableRow row2 = new TableRow(this);
		TextView t2 = new TextView(this);
		t2.setText("18-648");
		t2.setTextSize(25);
		row2.addView(t2);
		CheckBox c2 = new CheckBox(this);
		c2.setChecked(true);
		row2.addView(c2);
		courses.addView(row2, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

		TableRow row3 = new TableRow(this);
		TextView t3 = new TextView(this);
		t3.setText("18-641");
		t3.setTextSize(25);
		row3.addView(t3);
		CheckBox c3 = new CheckBox(this);
		c3.setChecked(true);
		row3.addView(c3);
		courses.addView(row3, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

		TableLayout submissions = (TableLayout) findViewById(R.id.tableSubmissions);
		TableRow row4 = new TableRow(this);
		TextView t4 = new TextView(this);
		t4.setText("Quizzes");
		t4.setTextSize(25);
		row4.addView(t4);
		CheckBox c4 = new CheckBox(this);
		c4.setChecked(true);
		row4.addView(c4);
		submissions.addView(row4, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

		
		// Install CheckBox listeners
		c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				fragment.c1 = isChecked;
				fragment.drawChart();
			}
		});
		
		c2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				fragment.c2 = isChecked;
				fragment.drawChart();
			}
		});
		
		c3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				fragment.c3 = isChecked;
				fragment.drawChart();
			}
		});
		
		Button add = (Button) findViewById(R.id.add);
		Button delete = (Button) findViewById(R.id.delete);
		// Add/Delete Button click listeners
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent home2add = new Intent(getBaseContext(), AdditionActivity.class);
				home2add.putExtra("userID", userID);
				startActivity(home2add);
			}
		});
		
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent home2delete = new Intent(getBaseContext(), DeletionActivity.class);
				home2delete.putExtra("userID", userID);
				startActivity(home2delete);
			}
		});
		
		fragmentTransaction.replace(R.id.chartView, fragment);
		fragmentTransaction.commit();
	}
	
	@Override
	protected void onResume() {
		super.onResume();

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
/*
	@Override
	public void onBackPressed() {
		this.finish();

	}
*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
