package local;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBUtil extends SQLiteOpenHelper {
	
	private static String databaseName = "CourselineDB";
	private static int databaseVersion =1;
	private static String userTable="Users";
	private static String courseTable="Courses";
	private static String userColumn1="Name";
	private static String userColumn2="UserID";
	private static String userColumn3="Course1";
	private static String userColumn4="Course2";
	private static String userColumn5="Course3";
	private static String userColumn6="Course4";
	private static String userColumn7="Course5";
	private static String courseColumn1="CourseName";
	private static String courseColumn2="CourseID";
	private static String courseColumn3="Semester";
	private static String courseColumn4="SubmissionID";
	private static String courseColumn5="SubmissionName";
	private static String courseColumn6="SubmissionType";
	private static String courseColumn7="ReleaseDate";
	private static String courseColumn8="DueDate";
	private static String courseColumn9="WeightPercentage";
	private static String courseColumn10="WeightPonts";
	private static String courseColumn11="Desc";
	
	
	public DBUtil(Context context){
		super(context,databaseName,null,databaseVersion);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql="CREATE TABLE "+userTable+" ("
				+userColumn1+" NOT NULL, "
				+userColumn2+" NOT NULL, "
				+userColumn3+", "
				+userColumn4+", "
				+userColumn5+", "
				+userColumn6+", "
				+userColumn7+");";
		db.execSQL(sql);	
		
		sql="CREATE TABLE "+courseTable+" ("
				+courseColumn1+" NOT NULL, "
				+courseColumn2+","
				+courseColumn3+","
				+courseColumn4+","
				+courseColumn5+","
				+courseColumn6+","
				+courseColumn7+","
				+courseColumn8+","
				+courseColumn9+","
				+courseColumn10+","
				+courseColumn11+");";
		db.execSQL(sql);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS "+userTable;
		db.execSQL(sql);
		sql="DROP TABLE IF EXISTS "+courseTable;
		db.execSQL(sql);
		onCreate(db);		
	}
	
	public long insertUser(String name,String userID,int c1,int c2,int c3,int c4,int c5){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		Log.d("sumne","came into insert");
		values.put(userColumn1, name);
		values.put(userColumn2, userID);
		values.put(userColumn3, c1);
		values.put(userColumn4, c2);
		values.put(userColumn5, c3);
		values.put(userColumn6, c4);
		values.put(userColumn7, c5);
		return db.insert(userTable,null, values);
	}
	
	public long insertCourse(String cName,int cID,String sem,int subID,String subName,
			String subType,Date rDate,Date dDate,int wPercent,int wPoints,String desc){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(courseColumn1, cName);
		values.put(courseColumn2, cID);
		values.put(courseColumn3, sem);
		values.put(courseColumn4, subID);
		values.put(courseColumn5, subName);
		values.put(courseColumn6, subType);
		values.put(courseColumn7, rDate.toString());
		values.put(courseColumn8, dDate.toString());
		values.put(courseColumn9, wPercent);
		values.put(courseColumn10, wPoints);
		values.put(courseColumn11, desc);		
		return db.insert(courseTable, null, values);
	}
	
	public Cursor selectUser(String userID){
		System.out.println("inside select");
		Log.d("sumne","inside select");
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM "+userTable+" where "+userColumn2+" ="+"'"+userID+"';";
		return db.rawQuery(sql,null);
	}
	
	public Cursor selectCourse(int cID){
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM "+userTable+" where "+userColumn2+" ="+"'"+cID+"';";
		return db.rawQuery(sql,null);
	}
	
	public void deleteUser(String userID){
		Log.d("sumne","inside select");
		SQLiteDatabase db = this.getWritableDatabase();
		String whereClause=userColumn2+" = ?";
		String[] whereArgs ={userID};
		db.delete(userTable, whereClause, whereArgs);
	}
	
	public void deleteCourse(int cID){
		SQLiteDatabase db = this.getWritableDatabase();
		String whereClause=courseColumn2+" =?";
		String[] whereArgs={Integer.toString(cID)};
		db.delete(courseTable, whereClause, whereArgs);
	}
	
	public void updateUser(String userID,int c1,int c2,int c3,int c4,int c5){
		SQLiteDatabase db = this.getWritableDatabase();
		String whereClause=userColumn2+" =?";
		String[] whereArgs={userID};
		ContentValues values = new ContentValues();
		values.put(userColumn3, c1);
		values.put(userColumn4, c2);
		values.put(userColumn5, c3);
		values.put(userColumn6, c4);
		values.put(userColumn7, c5);
		db.update(userTable, values, whereClause, whereArgs);		
	}
	
}
