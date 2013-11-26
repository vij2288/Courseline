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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deccan_courseline_activity_register);
		mdb=new DBUtil(this);
		
		fname = (EditText) findViewById(R.id.firstName);
		email = (EditText) findViewById(R.id.signupEmail);
		pwd = (EditText) findViewById(R.id.signupPwd);
		repwd = (EditText) findViewById(R.id.signupRePwd);
		Button signup = (Button) findViewById(R.id.signupButton);
		
		signup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Log.d("REGISTER", "pwd: " + pwd.toString() + " repwd: " + repwd.toString());
				if (pwd.getText().toString().equals(repwd.getText().toString())) {
					mdb.insertUser(fname.getText().toString(), email.getText().toString(), pwd.getText().toString());
					
					Toast toast = Toast.makeText(getBaseContext(),"Registration Successful!",Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					
					Intent reg2home = new Intent(getBaseContext(),HomeActivity.class);
					reg2home.putExtra("userID", email.getText().toString());
					startActivity(reg2home);
				// if passwords mismatch
				} else {
					fname.setText("");
					email.setText("");
					pwd.setText("");
					repwd.setText("");
					Toast toast = Toast.makeText(getBaseContext(),"Password mismatch! Retry.",Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
