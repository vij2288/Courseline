package deccan.courseline;

import java.util.ArrayList;
import java.util.Iterator;

import entities.Course;
import entities.Student;

import local.DBUtil;
import local.LocalUtil;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class AdditionActivity extends Activity {

	ArrayList<String> courses = new ArrayList<String>();
	DBUtil mdb;
	Cursor mCursor;
	EditText searchText;
	Button search;
	Button add;
	String userID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("ADDITION", "Inside on create");
		super.onCreate(savedInstanceState);
		setTitle("Add Course");
		getActionBar().setIcon(R.drawable.tbar_icon);
		setContentView(R.layout.deccan_courseline_activity_addition);
		userID = getIntent().getStringExtra("userID");
		Log.d("ADDITION", "User id with the intent is "+userID);

		mdb = new DBUtil(this);

		courses.add("15213");
		courses.add("18644");
		courses.add("18641");
		
		searchText = (EditText) findViewById(R.id.searchText);		
		TableRow.LayoutParams par = new TableRow.LayoutParams();
		par.weight = 0.6f;
		searchText.setLayoutParams(par);
		search = (Button) findViewById(R.id.searchButton);
		// Search/Add Button click listeners
		search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Log.d("ADDITION", "Inside on click of search");
				Course course = null;
				course = mdb.selectCourse(searchText.getText().toString());
				if (course != null) {
					Log.d("ADDITION", "found course locally");
					TableLayout results = (TableLayout) findViewById(R.id.resultsTable);
					results.removeAllViews();
					TableRow row1 = new TableRow(getBaseContext());
					TextView t1 = new TextView(getBaseContext());
					TableRow.LayoutParams params = new TableRow.LayoutParams();
					params.weight = 0.6f;
					t1.setLayoutParams(params);
					t1.setText(course.getCourseNumber());
					t1.setTextSize(25);
					t1.setTextColor(Color.BLACK);
					row1.addView(t1);
					Button b1 = new Button(getBaseContext());
					b1.setTextSize(25);
		            //b1.setWidth(0);
		            b1.setTypeface(null, Typeface.BOLD);
		            b1.setTextColor(Color.WHITE);
		            b1.setBackground(getResources().getDrawable((R.drawable.blue_menu_btn)));
					b1.setText("Add Course");
					row1.addView(b1);
					row1.setPadding(0, 40, 0, 0);
					results.addView(row1, new TableLayout.LayoutParams(
							LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					
					b1.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							Log.d("ADDITION", "Inside on click of add");
							mCursor = mdb.selectUser(userID);
							if (mCursor.getCount() > 0) {
								mCursor.moveToFirst();
								if (mCursor.getInt(8) < 5) {
									Log.d("ADDITION",
											"got records from select user");
									int courseCount = mCursor.getInt(8);
									int colIndex = 2;
									boolean found = false;
									while (courseCount > 0) {
										if (mCursor.getString(colIndex)
												.equalsIgnoreCase(
														searchText.getText()
																.toString())) {
											found = true;
											// toast present
											Toast toast = Toast.makeText(
													getBaseContext(),
													"Course already added",
													Toast.LENGTH_LONG);
											toast.setGravity(Gravity.CENTER, 0,
													0);
											toast.show();
											break;
										}
										colIndex++;
										courseCount--;
									}
									courseCount = mCursor.getInt(8);
									if (found == false) {
										switch (courseCount) {
										case 0:
											Log.d("ADDITION",
													"1st subject updated Locallly");
											mdb.updateUser(userID, searchText
													.getText().toString(),
													null, null, null, null, 1);
											break;
										case 1:
											Log.d("ADDITION",
													"2nd subject updated Locallly");
											mdb.updateUser(userID, mCursor
													.getString(2), searchText
													.getText().toString(),
													null, null, null, 2);
											break;
										case 2:
											Log.d("ADDITION",
													"3rd subject updated Locallly");
											mdb.updateUser(userID, mCursor
													.getString(2), mCursor
													.getString(3), searchText
													.getText().toString(),
													null, null, 3);
											break;
										case 3:
											Log.d("ADDITION",
													"4th subject updated Locallly");
											mdb.updateUser(userID, mCursor
													.getString(2), mCursor
													.getString(3), mCursor
													.getString(4), searchText
													.getText().toString(),
													null, 4);
											break;
										case 4:
											Log.d("ADDITION",
													"5th subject updated Locallly");
											mdb.updateUser(userID, mCursor
													.getString(2), mCursor
													.getString(3), mCursor
													.getString(4), mCursor
													.getString(5), searchText
													.getText().toString(), 5);
											break;

										}
										Toast toast = Toast.makeText(
												getBaseContext(),
												"Course added",
												Toast.LENGTH_LONG);
										toast.setGravity(Gravity.CENTER, 0, 0);
										toast.show();
										// intent???
										Course c1 = mdb.selectCourse(searchText
												.getText().toString());
										Log.d("ADDITION",
												"Course: " + c1.toString());

										mCursor = mdb.selectUser(userID);
										if (mCursor.getCount() > 0) {
											mCursor.moveToFirst();
											String str = new String();
											for (int i = 0; i <= 8; i++) {
												if (mCursor.getString(i) != null) {
													str += mCursor.getString(i)
															+ ", ";
												} else {
													str += "null, ";
												}
											}
											Log.d("ADDITION", "userTable: "
													+ str);
										}

									}

								} else {
									// toast exceeded
									Toast toast = Toast.makeText(
											getBaseContext(),
											"You already have 5 Courses",
											Toast.LENGTH_LONG);
									toast.setGravity(Gravity.CENTER, 0, 0);
									toast.show();
								}
							}
						}
					});
				} else {

					TableLayout results = (TableLayout) findViewById(R.id.resultsTable);
					results.removeAllViews();
					Iterator<String> it = courses.iterator();
					while (it.hasNext()) {
						String s = it.next();
						if (s.equalsIgnoreCase(searchText.getText().toString())) {

							Log.d("ADDITION", "contacting server");
							TableRow row1 = new TableRow(getBaseContext());
							TextView t1 = new TextView(getBaseContext());
							TableRow.LayoutParams params = new TableRow.LayoutParams();
							params.weight = 0.6f;
							t1.setLayoutParams(params);
							t1.setText(s);
							t1.setTextSize(25);
							t1.setTextColor(Color.BLACK);
							row1.addView(t1);
							Button b1 = new Button(getBaseContext());
							b1.setTextSize(25);
				            //b1.setWidth(0);
				            b1.setTypeface(null, Typeface.BOLD);
				            b1.setTextColor(Color.WHITE);
				            b1.setBackground(getResources().getDrawable((R.drawable.blue_menu_btn)));
							b1.setText("Add Course");
							row1.addView(b1);
							row1.setPadding(0, 40, 0, 0);
							results.addView(row1, new TableLayout.LayoutParams(
									LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
							
							b1.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View arg0) {
									if (courses.contains(searchText.getText()
											.toString())) {
										Student student = new Student();
										String filename = Environment
												.getExternalStorageDirectory()
												.getPath()
												+ "/Download/cmu_f13_"
												+ searchText.getText()
														.toString() + ".xml";
										System.out.println("Static file path: "
												+ filename);
										LocalUtil.ImportCourseData(student,
												filename);
										if (student != null) {
											Course course = student.courses
													.get(0);
											// add to courses table
											mdb.insertCourse(course);
											Log.d("ADDITION", "insert done");
											Log.d("ADDITION",
													"Course: before db select "
															+ course.toString());
											// add to users table
											mCursor = mdb.selectUser(userID);
											if (mCursor.getCount() > 0) {
												mCursor.moveToFirst();
												String str = new String();
												for (int i = 0; i <= 8; i++) {
													if (mCursor.getString(i) != null) {
														str += mCursor
																.getString(i)
																+ ", ";
													} else {
														str += "null, ";
													}
												}
												Log.d("ADDITION", "userTable: "
														+ str);
											} else {
												Log.d("ADDITION",
														"mDB selectuser didn't return anything");
											}

											if (mCursor.getCount() > 0) {
												Log.d("ADDITION",
														"inside adding the course to user");
												mCursor.moveToFirst();
												if (mCursor.getInt(8) < 5) {
													int courseCount = mCursor
															.getInt(8);
													switch (courseCount) {
													case 0:
														Log.d("ADDITION",
																"1st subject updated globally");
														mdb.updateUser(
																userID,
																searchText
																		.getText()
																		.toString(),
																null, null,
																null, null, 1);
														break;
													case 1:
														Log.d("ADDITION",
																"2nd subject updated globally");
														mdb.updateUser(
																userID,
																mCursor.getString(2),
																searchText
																		.getText()
																		.toString(),
																null, null,
																null, 2);
														break;
													case 2:
														Log.d("ADDITION",
																"3rd subject updated globally");
														mdb.updateUser(
																userID,
																mCursor.getString(2),
																mCursor.getString(3),
																searchText
																		.getText()
																		.toString(),
																null, null, 3);
														break;
													case 3:
														Log.d("ADDITION",
																"4th subject updated globally");
														mdb.updateUser(
																userID,
																mCursor.getString(2),
																mCursor.getString(3),
																mCursor.getString(4),
																searchText
																		.getText()
																		.toString(),
																null, 4);
														break;
													case 4:
														Log.d("ADDITION",
																"5th subject updated globally");
														mdb.updateUser(
																userID,
																mCursor.getString(2),
																mCursor.getString(3),
																mCursor.getString(4),
																mCursor.getString(5),
																searchText
																		.getText()
																		.toString(),
																5);
														break;

													}
													Toast toast = Toast
															.makeText(
																	getBaseContext(),
																	"Course added",
																	Toast.LENGTH_LONG);
													toast.setGravity(
															Gravity.CENTER, 0,
															0);
													toast.show();
													// intent???
													Course c1 = mdb
															.selectCourse(searchText
																	.getText()
																	.toString());
													Log.d("ADDITION",
															"Course: "
																	+ c1.toString());

													mCursor = mdb
															.selectUser(userID);
													if (mCursor.getCount() > 0) {
														mCursor.moveToFirst();
														String str = new String();
														for (int i = 0; i <= 8; i++) {
															if (mCursor
																	.getString(i) != null) {
																str += mCursor
																		.getString(i)
																		+ ", ";
															} else {
																str += "null, ";
															}
														}
														Log.d("ADDITION",
																"userTable: "
																		+ str);
													}

												} else {
													// toast exceeded
													Toast toast = Toast
															.makeText(
																	getBaseContext(),
																	"You already have 5 Courses",
																	Toast.LENGTH_LONG);
													toast.setGravity(
															Gravity.CENTER, 0,
															0);
													toast.show();
												}
											}
										}
									} else {
										Toast toast = Toast.makeText(
												getBaseContext(),
												"Course not found.",
												Toast.LENGTH_LONG);
										toast.setGravity(Gravity.CENTER, 0, 0);
										toast.show();
									}
								}
							});
						}
					}
				}
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
		mdb.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.addition, menu);
		return true;
	}

}
