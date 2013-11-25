package deccan.courseline;


import local.DBUtil;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.TextView;

public class DeletionActivity extends Activity {
	DBUtil mdb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deccan_courseline_activity_deletion);
		
		String userID="";/* take this value from intent */
		mdb = new DBUtil(this);
		Cursor mCursor;
		String delete_head=getString(R.string.delete_head);
		mCursor = mdb.selectUser(userID);
		if (mCursor.getCount() > 0) {
			mCursor.moveToFirst();
			String c1 = mCursor.getString(2);
			String c2 = mCursor.getString(3);
			String c3 = mCursor.getString(4);
			String c4 = mCursor.getString(5);
			String c5 = mCursor.getString(6);
			if(c1==null && c2==null && c3 == null && c4==null && c5 == null)
				delete_head="There are no courses to be deleted";
			else
				delete_head="The courses that you have chosen are";	
		}
		((TextView) findViewById(R.id.delete_Text)).setText(delete_head);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deletion, menu);
		return true;
	}

}
