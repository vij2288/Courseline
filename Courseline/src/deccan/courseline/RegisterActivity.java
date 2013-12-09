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

public class RegisterActivity extends Activity {

	Student student = null;
	DBUtil mdb;
	boolean loginSuccess = false;
	Cursor mCursor;
	EditText fname = null, email = null, pwd = null, repwd = null;

	/*
	 * Registration Activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("New User Registration");
		getActionBar().setIcon(R.drawable.tbar_icon);
		setContentView(R.layout.deccan_courseline_activity_register);
		mdb = new DBUtil(this);

		// get form fields
		fname = (EditText) findViewById(R.id.firstName);
		email = (EditText) findViewById(R.id.signupEmail);
		pwd = (EditText) findViewById(R.id.signupPwd);
		repwd = (EditText) findViewById(R.id.signupRePwd);
		Button signup = (Button) findViewById(R.id.signupButton);

		// sanity check the input fields and register the user
		signup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (((fname.getText().toString() != null)
						&& (email.getText().toString() != null) 
						&& (email.getText().toString().contains("@") 
						&& (email.getText().toString().endsWith(".edu"))))
						&& (pwd.getText().toString() != null)
						&& (repwd.getText().toString() != null)) {
					
					Log.d("REGISTER", "pwd: " + pwd.toString() + " repwd: "
							+ repwd.toString());
					// if passwords match
					if (pwd.getText().toString()
							.equals(repwd.getText().toString())) {
						mdb.insertUser(fname.getText().toString(), email
								.getText().toString(), pwd.getText().toString());

						Toast toast = Toast.makeText(getBaseContext(),
								"Registration Successful!", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();

						Intent reg2home = new Intent(getBaseContext(),
								HomeActivity.class);
						reg2home.putExtra("userID", email.getText().toString());
						startActivityForResult(reg2home, 500);
						overridePendingTransition(R.anim.slide_in_right,
								R.anim.slide_out_left);
					} else {
						// if passwords don't match
						Toast toast = Toast.makeText(getBaseContext(),
								"Password mismatch! Retry.", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
				} else {
					// if email not valid
					Toast toast = Toast.makeText(getBaseContext(),
							"Please enter valid email and password",
							Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
				// clear fields
				fname.setText("");
				email.setText("");
				pwd.setText("");
				repwd.setText("");
			}
		});
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// take care of return transition direction
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
