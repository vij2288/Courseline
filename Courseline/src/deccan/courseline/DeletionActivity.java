package deccan.courseline;

import local.DBUtil;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class DeletionActivity extends Activity {
	DBUtil mdb;
	String userID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Delete Course");
		getActionBar().setIcon(R.drawable.tbar_icon);
		setContentView(R.layout.deccan_courseline_activity_deletion);

		userID = getIntent().getStringExtra("userID");
		refreshTable();
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

	private void refreshTable() {
		mdb = new DBUtil(this);
		Cursor mCursor;
		String delete_head = getString(R.string.delete_head);
		mCursor = mdb.selectUser(userID);
		String[] c = new String[5];
		if (mCursor.getCount() > 0) {
			mCursor.moveToFirst();
			c[0] = mCursor.getString(2);
			c[1] = mCursor.getString(3);
			c[2] = mCursor.getString(4);
			c[3] = mCursor.getString(5);
			c[4] = mCursor.getString(6);
			if (c[0] == null && c[1] == null && c[2] == null && c[3] == null
					&& c[4] == null) {
				delete_head = "There are no courses to be deleted";
				((TextView) findViewById(R.id.delete_Text))
				.setText(delete_head);
				
				TableLayout results = (TableLayout) findViewById(R.id.deleteTable);
				results.removeAllViews();
			} else {
				delete_head = "The courses that you have chosen are";
				((TextView) findViewById(R.id.delete_Text))
						.setText(delete_head);

				TableLayout results = (TableLayout) findViewById(R.id.deleteTable);
				results.removeAllViews();
				for (int i = 0; i < mCursor.getInt(8); i++) {
					final int index = i;
					TableRow row1 = new TableRow(getBaseContext());
					TextView t1 = new TextView(getBaseContext());
					TableRow.LayoutParams params = new TableRow.LayoutParams();
					params.weight = 0.6f;
					t1.setLayoutParams(params);
					t1.setText(c[i]);
					t1.setTextSize(25);
					t1.setTextColor(Color.BLACK);
					row1.addView(t1);
					Button b1 = new Button(getBaseContext());
					b1.setTextSize(25);
		            //b1.setWidth(0);
		            b1.setTypeface(null, Typeface.BOLD);
		            b1.setTextColor(Color.WHITE);
		            b1.setBackground(getResources().getDrawable((R.drawable.red_menu_btn)));
					b1.setText("Remove Course");
					row1.addView(b1);
					row1.setPadding(6, 6, 6, 6);
					results.addView(row1, new TableLayout.LayoutParams(
							LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					
					b1.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							DBUtil ndb = new DBUtil(getBaseContext());
							Cursor nCursor;
							nCursor = ndb.selectUser(userID);
							if (nCursor.getCount() > 0) {
								nCursor.moveToFirst();
								switch (index) {
								case 0:
									ndb.updateUser(userID,
											nCursor.getString(3),
											nCursor.getString(4),
											nCursor.getString(5),
											nCursor.getString(6), null,
											(nCursor.getInt(8) - 1));
									break;
								case 1:
									ndb.updateUser(userID,
											nCursor.getString(2),
											nCursor.getString(4),
											nCursor.getString(5),
											nCursor.getString(6), null,
											(nCursor.getInt(8) - 1));
									break;
								case 2:
									ndb.updateUser(userID,
											nCursor.getString(2),
											nCursor.getString(3),
											nCursor.getString(5),
											nCursor.getString(6), null,
											(nCursor.getInt(8) - 1));
									break;
								case 3:
									ndb.updateUser(userID,
											nCursor.getString(2),
											nCursor.getString(3),
											nCursor.getString(4),
											nCursor.getString(6), null,
											(nCursor.getInt(8) - 1));
									break;
								case 4:
									ndb.updateUser(userID,
											nCursor.getString(2),
											nCursor.getString(3),
											nCursor.getString(4),
											nCursor.getString(5), null,
											(nCursor.getInt(8) - 1));
									break;

								}
								Toast toast = Toast
										.makeText(
												getBaseContext(),
												"Course deleted",
												Toast.LENGTH_LONG);
								toast.setGravity(
										Gravity.CENTER, 0, 0);
								toast.show();
								
								nCursor=ndb.selectUser(userID);
								if(nCursor.getCount()>0){
									nCursor.moveToFirst();
									String str = new String();
									for (int i=0; i<=8; i++) {
										if (nCursor.getString(i) != null) {
											str += nCursor.getString(i) + ", ";
										} else {
											str += "null, ";
										}
									}
									Log.d("DELETION", "userTable: " + str);
								}
								ndb.close();
								refreshTable();
							}

						}
					});
				}
			}
		}
		mdb.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deletion, menu);
		return true;
	}

}
