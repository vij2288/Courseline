package deccan.courseline;

import local.DBUtil;
import local.LocalUtil;
import entities.Student;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	Student student = null;
	DBUtil mdb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deccan_courseline_activity_main);
		
		mdb=new DBUtil(this);
		Cursor mCursor;
		student = new Student();
		
		// Parse static file
		String filename = Environment.getExternalStorageDirectory().getPath()
				+ "/Download/cmu_f13_15213.xml";
		System.out.println("Static file path: " + filename);
		LocalUtil.ImportCourseData(student, filename);
		System.out.println(student.toString());
	/* ***************************Database testing******************************************************* 	
	 * ******************************DONT DELETE*********************************************************
	 * **************************************************************************************************
		Log.d("sumne","before calling insert");
		//Integer.parseInt(student.courses.get(1).getCourseNumber())
	   // mdb.insertUser("Giri","giri" ,15213 , 0, 0, 0, 0);
		System.out.println("table");
		mCursor=mdb.selectUser("giri");
		if (mCursor.getCount() > 0) {
			mCursor.moveToFirst();
			System.out.println("The first course is "+mCursor.getLong(2));
		}
		else
			System.out.println("Select didn't work");
		System.out.println("going to delete");
		mdb.deleteUser("giri");
		System.out.println("deleted");
		mCursor=mdb.selectUser("giri");
		if (mCursor.getCount() > 0) {
			mCursor.moveToFirst();
			System.out.println("The first course is "+mCursor.getLong(2));
		}
		else
			System.out.println("Select didn't work");
		*/
		
		
		Intent main2home = new Intent(getBaseContext(), HomeActivity.class);
		//main2home.putExtra("student", student);
		startActivity(main2home);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
