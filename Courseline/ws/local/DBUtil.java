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
	private static String subTable="Submissions";
	
	private static String userColumn1="Name";
	private static String userColumn2="UserID";
	private static String userColumn3="Course1";
	private static String userColumn4="Course2";
	private static String userColumn5="Course3";
	private static String userColumn6="Course4";
	private static String userColumn7="Course5";
	private static String userColumn8="ProfilePic";
	
	private static String courseColumn1="CourseName";
	private static String courseColumn2="CourseID";
	private static String courseColumn3="Semester";
	private static String courseColumn4="SubmissionID";
	private static String courseColumn5="SubmissionName";
	private static String courseColumn6="SubmissionType";
	private static String courseColumn7="ReleaseDate";
	private static String courseColumn8="DueDate";
	private static String courseColumn9="WeightPercentage";
	private static String courseColumn10="WeightPonits";
	private static String courseColumn11="Desc";
	
	private static String subColumn1="UserID";
	private static String subColumn2="CourseID";
	private static String subColumn3="SubID";
	private static String subColumn4="Pic1";
	private static String subColumn5="Pic2";
	private static String subColumn6="Pic3";
	private static String subColumn7="Pic4";
	private static String subColumn8="Pic5";
	private static String subColumn9="Notes";
	
	
	
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
				+userColumn7+", "
				+userColumn8+" BLOB);";
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
		
		sql="CREATE TABLE "+subTable+" ("
				+subColumn1+" NOT NULL, "
				+subColumn2+" NOT NULL,"
				+subColumn3+" NOT NULL,"
				+subColumn4+" BLOB,"
				+subColumn5+" BLOB,"
				+subColumn6+" BLOB,"
				+subColumn7+" BLOB,"
				+subColumn8+" BLOB,"
				+subColumn9+");";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS "+userTable;
		db.execSQL(sql);
		sql="DROP TABLE IF EXISTS "+courseTable;
		db.execSQL(sql);
		sql="DROP TABLE IF EXISTS "+subTable;
		db.execSQL(sql);
		onCreate(db);		
	}
	
	/* Insert values into user table */
	public long insertUser(String name,String userID,String c1,String c2,String c3,
			String c4,String c5, byte[] profilePic){
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
		values.put(userColumn8, profilePic);
		return db.insert(userTable,null, values);
	}
	
	public long insertCourse(String cName,String cID,String sem,String subID,String subName,
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
	
	public long insertSub(String userID,String cID,String subID,byte[] pic1,byte[] pic2, byte[] pic3,
			byte[] pic4,byte[] pic5,String notes){
		SQLiteDatabase db = this.getWritableDatabase();
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
	
	public Cursor selectUser(String userID){
		System.out.println("inside select");
		Log.d("sumne","inside select");
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM "+userTable+" WHERE "+userColumn2+" ="+"'"+userID+"';";
		return db.rawQuery(sql,null);
	}
	
	public Cursor selectCourse(String cID){
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM "+userTable+" WHERE "+userColumn2+" ="+"'"+cID+"';";
		return db.rawQuery(sql,null);
	}
	
	public Cursor selectSub(String userID,String cID,String subID){
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM "+subTable+" WHERE "+subColumn1+" ="+"'"+userID+"'"+
				" AND "+subColumn2+" ="+"'"+cID+"'"+" AND "+subColumn3+" ="+"'"+subID+"';";
		return db.rawQuery(sql,null);
	}
	
	public void deleteUser(String userID){
		Log.d("sumne","inside select");
		SQLiteDatabase db = this.getWritableDatabase();
		String whereClause=userColumn2+" = ?";
		String[] whereArgs ={userID};
		db.delete(userTable, whereClause, whereArgs);
	}
	
	public void deleteCourse(String cID){
		SQLiteDatabase db = this.getWritableDatabase();
		String whereClause=courseColumn2+" =?";
		String[] whereArgs={cID};
		db.delete(courseTable, whereClause, whereArgs);
	}
	
	public void deleteSub(String userID,String cID,String subID){
		SQLiteDatabase db = this.getWritableDatabase();
		String whereClause=subColumn1+" =?"+" AND"+subColumn2+" =?"+" AND"+subColumn3;
		String[] whereArgs={userID,cID,subID};
		db.delete(courseTable, whereClause, whereArgs);		
	}
	
	public void updateUser(String userID,String c1,String c2,String c3,String c4,String c5){
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
	
	public void updateSub(String userID,String cID,String subID,byte[] pic1,byte[] pic2, byte[] pic3,
			byte[] pic4,byte[] pic5,String notes){
		SQLiteDatabase db = this.getWritableDatabase();
		String whereClause=subColumn1+" =?"+" AND"+subColumn2+" =?"+" AND"+subColumn3;
		String[] whereArgs={userID,cID,subID};
		ContentValues values = new ContentValues();
		values.put(subColumn4, pic1);
		values.put(subColumn5, pic2);
		values.put(subColumn6, pic3);
		values.put(subColumn7, pic4);
		values.put(subColumn8, pic5);
		values.put(subColumn9, notes);
		db.update(subTable, values, whereClause, whereArgs);		
	}
   
}
