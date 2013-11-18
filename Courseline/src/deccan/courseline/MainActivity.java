package deccan.courseline;

import local.LocalUtil;
import entities.Student;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	Student student = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deccan_courseline_activity_main);
		
		student = new Student();
		// Parse static file
		String filename = Environment.getExternalStorageDirectory().getPath()
				+ "/Download/cmu_f13_15213.xml";
		System.out.println("Static file path: " + filename);
		LocalUtil.ImportCourseData(student, filename);
		System.out.println(student.toString());
		
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
