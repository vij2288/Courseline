package deccan.courseline;

import local.DBUtil;
import entities.Student;
import android.os.Bundle;
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
import android.widget.Toast;

public class MainActivity extends Activity {

	Student student = null;
	DBUtil mdb;
	boolean loginSuccess = false;
	Cursor mCursor;
	EditText email = null, pwd = null;

	/*
	 * CourseLine
	 * 
	 * Team Deccan
	 * Sagar Medikeri, Vijith Venkatesh, Chidambaram Bhat
	 * 
	 * App starts here. This is the Sign Up activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Sign In");
		getActionBar().setIcon(R.drawable.tbar_icon);
		setContentView(R.layout.deccan_courseline_activity_main);

		mdb = new DBUtil(this);
		student = new Student();
		
		// Get the form fields
		email = (EditText) findViewById(R.id.loginEmail);
		pwd = (EditText) findViewById(R.id.loginPassword);
		Button signin = (Button) findViewById(R.id.signinButton);
		Button signup = (Button) findViewById(R.id.signupButton);

		// sanity check the form fields and sign in the user if credentials are correct
		signin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (((email.getText().toString() != null) && (email.getText()
						.toString().contains("@") && (email.getText()
						.toString().endsWith(".edu"))))
						&& (pwd.getText().toString() != null)) {

					mCursor = mdb.selectUser(email.getText().toString());

					String userID = email.getText().toString();
					// if entry exists
					if (mCursor.getCount() > 0) {
						mCursor.moveToFirst();
						String pwd1 = mCursor.getString(7);

						Log.d("LOGIN", "pwd:" + pwd.getText().toString()
								+ " pwd1: " + pwd1);
						// if password matches
						if (pwd.getText().toString().equals(pwd1)) {
							email.setText("");
							pwd.setText("");

							Intent main2home = new Intent(getBaseContext(),
									HomeActivity.class);
							main2home.putExtra("userID", userID);
							startActivityForResult(main2home, 500);
							overridePendingTransition(R.anim.slide_in_right,
									R.anim.slide_out_left);
						} else {
							email.setText("");
							pwd.setText("");
							Toast.makeText(getBaseContext(),
									"Login incorrect!", Toast.LENGTH_LONG)
									.show();
						}
					} else {
						email.setText("");
						pwd.setText("");
						Toast toast = Toast.makeText(getBaseContext(),
								"Account not found!", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
				} else {
					email.setText("");
					pwd.setText("");
					Toast toast = Toast.makeText(getBaseContext(),
							"Please enter valid email and password",
							Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
			}
		});

		// take user to Sign up activity if he wishes
		signup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent main2reg = new Intent(getBaseContext(),
						RegisterActivity.class);
				startActivityForResult(main2reg, 500);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
