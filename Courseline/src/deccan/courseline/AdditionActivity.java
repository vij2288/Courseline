package deccan.courseline;

import java.util.ArrayList;

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
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
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
	ImageButton search;
	Button add;
	String userID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("ADDITION","Inside on create");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deccan_courseline_activity_addition);
		userID = getIntent().getStringExtra("userID");

		mdb = new DBUtil(this);

		courses.add("15213");
		courses.add("18644");
		courses.add("18641");

		searchText = (EditText) findViewById(R.id.searchText);
		search = (ImageButton) findViewById(R.id.searchButton);
		// Search/Add Button click listeners
		search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Log.d("ADDITION","Inside on click");
				mCursor = mdb.selectCourse(searchText.getText().toString());
				if (mCursor.getCount() > 0) {
					Log.d("ADDITION","found course locally");
					mCursor.moveToFirst();
					TableLayout results = (TableLayout) findViewById(R.id.resultsTable);
					TableRow row1 = new TableRow(getBaseContext());
					TextView t1 = new TextView(getBaseContext());
					t1.setText(mCursor.getString(0));
					t1.setTextSize(25);
					row1.addView(t1);
					Button b1 = new Button(getBaseContext());
					b1.setText("Add Course");
					row1.addView(b1);
					results.addView(row1,
							new TableLayout.LayoutParams(
									LayoutParams.FILL_PARENT,
									LayoutParams.WRAP_CONTENT));

					b1.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							mCursor = mdb.selectUser(userID);
							if (mCursor.getCount() > 0) {
								mCursor.moveToFirst();
								if (mCursor.getInt(8) < 5) {
									int courseCount = mCursor.getInt(8);
									int colIndex = 2;
									Boolean found = false;
									while (courseCount > 0) {
										if (mCursor.getString(colIndex) == searchText
												.getText().toString()) {
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
									if (found == false) {
										switch (courseCount) {
										case 0:
											mdb.updateUser(userID, searchText
													.getText().toString(),
													null, null, null, null, 1);
											break;
										case 1:
											mdb.updateUser(userID, mCursor
													.getString(2), searchText
													.getText().toString(),
													null, null, null, 2);
											break;
										case 2:
											mdb.updateUser(userID, mCursor
													.getString(2), mCursor
													.getString(3), searchText
													.getText().toString(),
													null, null, 3);
											break;
										case 3:
											mdb.updateUser(userID, mCursor
													.getString(2), mCursor
													.getString(3), mCursor
													.getString(4), searchText
													.getText().toString(),
													null, 4);
											break;
										case 4:
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
					for (int i = 0; i < courses.size(); i++) {
						TableRow row1 = new TableRow(getBaseContext());
						TextView t1 = new TextView(getBaseContext());
						t1.setText(courses.get(i));
						t1.setTextSize(25);
						row1.addView(t1);
						Button b1 = new Button(getBaseContext());
						b1.setText("Add Course");
						row1.addView(b1);
						results.addView(row1, new TableLayout.LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT));

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
											+ searchText.getText().toString()
											+ ".xml";
									System.out.println("Static file path: "
											+ filename);
									LocalUtil.ImportCourseData(student,
											filename);
									if (student != null) {
										Course course = student.courses.get(0);
										// add to courses table
										mdb.insertCourse(course);
										// add to users table
										mCursor = mdb.selectUser(userID);
										if ((mCursor.getCount() > 0)
												&& (mCursor.getInt(8) < 5)) {
											mCursor.moveToFirst();
											int courseCount = mCursor.getInt(8);
											switch (courseCount) {
											case 0:
												mdb.updateUser(userID,
														searchText.getText()
																.toString(),
														null, null, null, null,
														1);
												break;
											case 1:
												mdb.updateUser(userID, mCursor
														.getString(2),
														searchText.getText()
																.toString(),
														null, null, null, 2);
												break;
											case 2:
												mdb.updateUser(userID, mCursor
														.getString(2), mCursor
														.getString(3),
														searchText.getText()
																.toString(),
														null, null, 3);
												break;
											case 3:
												mdb.updateUser(userID, mCursor
														.getString(2), mCursor
														.getString(3), mCursor
														.getString(4),
														searchText.getText()
																.toString(),
														null, 4);
												break;
											case 4:
												mdb.updateUser(userID, mCursor
														.getString(2), mCursor
														.getString(3), mCursor
														.getString(4), mCursor
														.getString(5),
														searchText.getText()
																.toString(), 5);
												break;

											}
											Toast toast = Toast.makeText(
													getBaseContext(),
													"Course added",
													Toast.LENGTH_LONG);
											toast.setGravity(Gravity.CENTER, 0,
													0);
											toast.show();
											// intent???
										} else {
											// toast exceeded
											Toast toast = Toast
													.makeText(
															getBaseContext(),
															"You already have 5 Courses",
															Toast.LENGTH_LONG);
											toast.setGravity(Gravity.CENTER, 0,
													0);
											toast.show();
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
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.addition, menu);
		return true;
	}

}
