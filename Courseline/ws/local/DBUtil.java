package local;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import deccan.courseline.R;

import entities.Course;
import entities.SubType;
import entities.Submission;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;

public class DBUtil extends SQLiteOpenHelper {

	private static String databaseName = "CourselineDB";
	private static int databaseVersion = 1;

	private static String userTable = "Users";
	private static String courseTable = "Courses";
	private static String subTable = "Submissions";

	private static String userColumn1 = "Name";
	private static String userColumn2 = "UserID";
	private static String userColumn3 = "Course1";
	private static String userColumn4 = "Course2";
	private static String userColumn5 = "Course3";
	private static String userColumn6 = "Course4";
	private static String userColumn7 = "Course5";
	private static String userColumn8 = "Password";
	private static String userColumn9 = "Count";

	private static String courseColumn1 = "CourseName";
	private static String courseColumn2 = "CourseID";
	private static String courseColumn3 = "Semester";
	private static String courseColumn4 = "SubmissionID";
	private static String courseColumn5 = "SubmissionName";
	private static String courseColumn6 = "SubmissionType";
	private static String courseColumn7 = "ReleaseDate";
	private static String courseColumn8 = "DueDate";
	private static String courseColumn9 = "WeightPercentage";
	private static String courseColumn10 = "WeightPonits";
	private static String courseColumn11 = "Desc";

	private static String subColumn1 = "UserID";
	private static String subColumn2 = "CourseID";
	private static String subColumn3 = "SubID";
	private static String subColumn4 = "Pic1";
	private static String subColumn5 = "Pic2";
	private static String subColumn6 = "Pic3";
	private static String subColumn7 = "Pic4";
	private static String subColumn8 = "Pic5";
	private static String subColumn9 = "Notes";

	public DBUtil(Context context) {
		super(context, databaseName, null, databaseVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + userTable + " (" + userColumn1
				+ " NOT NULL, " + userColumn2 + " NOT NULL, " + userColumn3
				+ ", " + userColumn4 + ", " + userColumn5 + ", " + userColumn6
				+ ", " + userColumn7 + ", " + userColumn8 + " NOT NULL,"
				+ userColumn9 + " integer NOT NULL);";
		db.execSQL(sql);

		sql = "CREATE TABLE " + courseTable + " (" + courseColumn1
				+ " NOT NULL, " + courseColumn2 + "," + courseColumn3 + ","
				+ courseColumn4 + "," + courseColumn5 + "," + courseColumn6
				+ "," + courseColumn7 + "," + courseColumn8 + ","
				+ courseColumn9 + "," + courseColumn10 + "," + courseColumn11
				+ ");";
		db.execSQL(sql);

		sql = "CREATE TABLE " + subTable + " (" + subColumn1 + " NOT NULL, "
				+ subColumn2 + " NOT NULL," + subColumn3 + " NOT NULL,"
				+ subColumn4 + " BLOB," + subColumn5 + " BLOB," + subColumn6
				+ " BLOB," + subColumn7 + " BLOB," + subColumn8 + " BLOB,"
				+ subColumn9 + ");";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + userTable;
		db.execSQL(sql);
		sql = "DROP TABLE IF EXISTS " + courseTable;
		db.execSQL(sql);
		sql = "DROP TABLE IF EXISTS " + subTable;
		db.execSQL(sql);
		onCreate(db);
	}

	/* Insert values into user table */
	public long insertUser(String name, String userID, String pwd) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		Log.d("DBUTIL", "Creating a new user with "+name+" "+userID);
		values.put(userColumn1, name); 
		values.put(userColumn2, userID);
		values.put(userColumn8, pwd);
		values.put(userColumn9, 0);
		return db.insert(userTable, null, values);
	}

	public void insertCourse(Course c) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		Iterator<Submission> it = c.submissions.iterator();
		Log.d("DBUTIL", "Creating a new course "+c.toString());
		long ret;
		while (it.hasNext()) {
			Submission s = it.next();
			values.put(courseColumn1, c.getCourseName());
			values.put(courseColumn2, c.getCourseNumber());
			values.put(courseColumn3, c.getSemester());
			values.put(courseColumn4, s.getSubId());
			values.put(courseColumn5, s.getSubName());
			values.put(courseColumn6, s.getSubType().toString());
			values.put(courseColumn7, s.getReleaseDate().toString());
			values.put(courseColumn8, s.getDueDate().toString());
			values.put(courseColumn9, s.getWeightPercent());
			values.put(courseColumn10, s.getWeightPoints());
			values.put(courseColumn11, s.getDescription());
			ret = db.insert(courseTable, null, values);
			if (ret == -1) {
				Log.d("ADDITION",
						"one of the inserts failed with submission ID"
								+ s.getSubId());
				return;
			}
		}

	}

	public long insertSub(String userID, String cID, int subID, byte[] pic1,
			byte[] pic2, byte[] pic3, byte[] pic4, byte[] pic5, String notes) {
		SQLiteDatabase db = this.getWritableDatabase();
		Log.d("DBUTIL", "Creating a new Submission for user"+userID+" for course "+cID+" submission id "+subID);
		Log.d("DBUTIL", "Notes" + notes);
		ContentValues values = new ContentValues();
		values.put(subColumn1, userID);
		values.put(subColumn2, cID);
		values.put(subColumn3, subID);
		values.put(subColumn4, pic1);
		values.put(subColumn5, pic2);
		values.put(subColumn6, pic3);
		values.put(subColumn7, pic4);
		values.put(subColumn8, pic5);
		values.put(subColumn9, notes);
		return db.insert(subTable, null, values);
	}

	public Cursor selectUser(String userID) {
		Log.d("DBUTIL", "selecting the user row with id "+userID);
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM " + userTable + " WHERE " + userColumn2
				+ " =" + "'" + userID + "';";
		return db.rawQuery(sql, null);
	}

	public Course selectCourse(String cID) {
		SQLiteDatabase db = this.getReadableDatabase();
		Course course = null;
		Log.d("DBUTIL", "selecting the course row with id "+cID);
		Cursor mCursor;
		String sql = "SELECT * FROM " + courseTable + " WHERE " + courseColumn2
				+ " =" + "'" + cID + "';";
		mCursor = db.rawQuery(sql, null);
		DateFormat formatter = new SimpleDateFormat(
				"EEE MMM dd HH:mm:ss z yyyy");
		Date releaseDate = null;
		Date dueDate = null;
		if (mCursor.getCount() > 0) {
			mCursor.moveToFirst();
			Log.d("ADDITION", "# OF ASSIGNMENTS: " + mCursor.getCount());
			int count = mCursor.getCount();
			course = new Course();
			course.setCourseName(mCursor.getString(0));
			course.setCourseNumber(mCursor.getString(1));
			course.setSemester(mCursor.getString(2));

			while (count > 0) {
				Submission sub = new Submission();
				sub.setSubId(mCursor.getInt(3));
				sub.setSubName(mCursor.getString(4));
				sub.setSubType(SubType.valueOf(mCursor.getString(5)
						.toUpperCase()));

				try {
					releaseDate = formatter.parse(mCursor.getString(6));
					dueDate = formatter.parse(mCursor.getString(7));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sub.setReleaseDate(releaseDate);
				sub.setDueDate(dueDate);
				sub.setWeightPercent(mCursor.getInt(8));
				sub.setWeightPoints(mCursor.getInt(9));
				sub.setDescription(mCursor.getString(10));
				mCursor.moveToNext();
				count--;
				course.submissions.add(sub);
			}

		}
		return course;
	}

	public Cursor selectSub(String userID, String cID, int subID) {
		SQLiteDatabase db = this.getReadableDatabase();
		Log.d("DBUTIL", "selecting the submission row with id "+subID+" for user"+userID+" for the course "+cID);
		String sql = "SELECT * FROM " + subTable + " WHERE " + subColumn1
				+ " =" + "'" + userID + "'" + " AND " + subColumn2 + " =" + "'"
				+ cID + "'" + " AND " + subColumn3 + " =" + subID + ";";
		// String[] columns ={subColumn1,subColumn2,subColumn3};
		// String[] args ={userID,
		return db.rawQuery(sql, null);
	}

	public void deleteUser(String userID) {
		Log.d("DBUTIL", "Deleting the user with id "+userID);
		SQLiteDatabase db = this.getWritableDatabase();
		String whereClause = userColumn2 + " = ?";
		String[] whereArgs = { userID };
		db.delete(userTable, whereClause, whereArgs);
	}

	public void deleteCourse(String cID) {
		Log.d("DBUTIL", "Deleting the course with id "+cID);
		SQLiteDatabase db = this.getWritableDatabase();
		String whereClause = courseColumn2 + " =?";
		String[] whereArgs = { cID };
		db.delete(courseTable, whereClause, whereArgs);
	}

	public void deleteSub(String userID, String cID, int subID) {
		Log.d("DBUTIL", "Deleting the submission with id "+subID+" for the user "+userID+ " of the course "+cID);
		SQLiteDatabase db = this.getWritableDatabase();
		String whereClause = subColumn1 + " =?" + " AND " + subColumn2 + " =?"
				+ " AND " + subColumn3 + " =?";
		String[] whereArgs = { userID, cID, Integer.toString(subID) };
		db.delete(courseTable, whereClause, whereArgs);
	}

	public void updateUser(String userID, String c1, String c2, String c3,
			String c4, String c5, int count) {
		Log.d("DBUTIL", "Updating the user with id "+userID);
		SQLiteDatabase db = this.getWritableDatabase();
		String whereClause = userColumn2 + " =?";
		String[] whereArgs = { userID };
		ContentValues values = new ContentValues();
		values.put(userColumn3, c1);
		values.put(userColumn4, c2);
		values.put(userColumn5, c3);
		values.put(userColumn6, c4);
		values.put(userColumn7, c5);
		values.put(userColumn9, count);
		db.update(userTable, values, whereClause, whereArgs);
	}

	public void updateSub(String userID, String cID, int subID, byte[] pic1,
			byte[] pic2, byte[] pic3, byte[] pic4, byte[] pic5, String notes) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor mCursor;
		Log.d("DBUTIL", "Updating the submission with id "+ subID+" for the user "+userID+ " of the course "+cID);

		String sql = "UPDATE " + subTable + " SET " + subColumn4 + "=" + pic1
				+ ", " + subColumn5 + "=" + pic2 + ", " + subColumn6 + "="
				+ pic3 + ", " + subColumn7 + "=" + pic4 + ", " + subColumn8
				+ "=" + pic5 + ", " + subColumn9 + "='" + notes + "'" + " WHERE "
				+ subColumn1 + "='" + userID + "' AND " + subColumn2 + "='"
				+ cID+ "' AND " +subColumn3+"="+subID+";";
		Log.d("DBUTIL","Update Query "+sql);
		/*
		 * String whereClause = subColumn1 + " =?" + " AND " + subColumn2 +
		 * " =?" + " AND " + subColumn3 + " =?"; String[] whereArgs = { userID,
		 * cID, Integer.toString(subID) }; Log.d("DBUTIL", "update ->Notes "+
		 * notes); ContentValues values = new ContentValues();
		 * values.put(subColumn4, pic1); values.put(subColumn5, pic2);
		 * values.put(subColumn6, pic3); values.put(subColumn7, pic4);
		 * values.put(subColumn8, pic5); values.put(subColumn9, notes); int
		 * count = db.update(subTable, values, whereClause, whereArgs);
		 */
		mCursor=db.rawQuery(sql, null);
		if(mCursor.getCount()>0){
			Log.d("DBUTIL", "Table updated with count "+ mCursor.getCount() );
		}
		else
			Log.d("DBUTIL", "no update happened "+ mCursor.getCount() );	
	}

}
