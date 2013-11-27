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
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends Activity {

	Student student = null;
	DBUtil mdb;
	boolean loginSuccess = false;
	Cursor mCursor;
	EditText email = null, pwd = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deccan_courseline_activity_main);
		
		mdb=new DBUtil(this);
		student = new Student();

		email = (EditText) findViewById(R.id.loginEmail);
		pwd = (EditText) findViewById(R.id.loginPassword);
		Button signin = (Button) findViewById(R.id.signinButton);
		Button signup = (Button) findViewById(R.id.signupButton);
		
		signin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mCursor=mdb.selectUser(email.getText().toString());
				
				String userID = email.getText().toString();
				// if entry exists
				if(mCursor.getCount()>0){
					mCursor.moveToFirst();
					String pwd1=mCursor.getString(7);
					
					Log.d("LOGIN", "pwd:" + pwd.getText().toString() + " pwd1: " + pwd1);
					// if password matches
					if(pwd.getText().toString().equals(pwd1)){
						email.setText("");
						pwd.setText("");
						
						Intent main2home = new Intent(getBaseContext(), HomeActivity.class);
						main2home.putExtra("userID", userID);
						startActivity(main2home);						
					} else {
						email.setText("");
						pwd.setText("");
						Toast.makeText(getBaseContext(),"Login incorrect!",Toast.LENGTH_LONG).show();
					}
				} else {
					email.setText("");
					pwd.setText("");
					Toast toast = Toast.makeText(getBaseContext(),"Account not found!",Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
			}
		});
		
		signup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent main2reg = new Intent(getBaseContext(), RegisterActivity.class);
				startActivity(main2reg);	
			}
		});

		/*
		// Parse static file
		String filename = Environment.getExternalStorageDirectory().getPath()
				+ "/Download/cmu_f13_15213.xml";
		System.out.println("Static file path: " + filename);
		LocalUtil.ImportCourseData(student, filename);
		
		filename = Environment.getExternalStorageDirectory().getPath()
				+ "/Download/cmu_f13_18641.xml";
		System.out.println("Static file path: " + filename);
		LocalUtil.ImportCourseData(student, filename);
		
		filename = Environment.getExternalStorageDirectory().getPath()
				+ "/Download/cmu_f13_18644.xml";
		System.out.println("Static file path: " + filename);
		LocalUtil.ImportCourseData(student, filename);
		
		System.out.println(student.toString());
		*/
		
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
		
		
	/*	Intent main2home = new Intent(getBaseContext(), HomeActivity.class);
		main2home.putExtra("student", student);
		startActivity(main2home);*/
	}
/*	
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
*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
