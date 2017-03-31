package ando.com.loginasssignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by mohit on 28/3/17.
 */

public class UserTable {
    private Context mContext;
    private SQLiteDatabase sqLiteDatabase;
    private String sql;

    public static String Table_Name = "userTable";
    public static String Col_User_ID = "id";
    public static String Col_User_FName = "fname";
    public static String Col_User_LName = "lname";
    public static String Col_User_Email = "email";
    public static String Col_User_Pass = "pass";
    public static String Col_User_BDate = "bdate";
    public static String Col_User_Hobbies = "hobbies";
    public static String Col_User_Gender = "gender";

    public static String Create_Table_User = "create table if not exists " +
            Table_Name + "(" +
            Col_User_ID + " integer primary key autoincrement not null," +
            Col_User_FName + " text," +
            Col_User_LName + " text," +
            Col_User_Email + " text unique," +
            Col_User_Pass + " text," +
            Col_User_BDate + " text," +
            Col_User_Gender + " text," +
            Col_User_Hobbies + " text" +
            ")";

    public static String Delete_Table_User = "drop table if exists " + Table_Name;


    public UserTable(Context context){
        mContext = context;
        sqLiteDatabase = Database.getDatabaseInstance(mContext);
    }

    public long insertUser(User user){
        return sqLiteDatabase.insertWithOnConflict(Table_Name,null,getValuesFromUser(user),SQLiteDatabase.CONFLICT_IGNORE);
    }

    public boolean isUserExist(String email){
        sql = "select * from " + Table_Name + " where " + Col_User_Email + "=?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{email});
        if(cursor.getCount() >= 1){
            return true;
        }else{
            return false;
        }
    }

    public User getUserByEmail(String email){
        sql = "select * from " + Table_Name + " where " + Col_User_Email + "=?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{email});
        User user;
        if(cursor.moveToFirst()){
            user = getUserFromCursor(cursor);
            Helper.log(
                    "FirstName: " + user.getFname()
                    +"\nLast Name: " + user.getLname()
                    +"\nGender: " + user.getGender()
                    +"\nHobbies: " + user.getHobbies()
                    +"\nBirth Date: " + user.getbDate()
                    +"\nEmail: " + user.getEmail()
                    +"\nPassword: " + user.getPass()
            );
        }else{
            user = null;
        }

        return user;
    }

    public void getAlluser(){
        sql = "select * from " + Table_Name;
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        User user;
        if(cursor.moveToFirst()){
            do {
                user = getUserFromCursor(cursor);
                Helper.log(
                        "FirstName: " + user.getFname()
                                + "\nLast Name: " + user.getLname()
                                + "\nGender: " + user.getGender()
                                + "\nHobbies: " + user.getHobbies()
                                + "\nBirth Date: " + user.getbDate()
                                + "\nEmail: " + user.getEmail()
                                + "\nPassword: " + user.getPass()
                );
            }while(cursor.moveToNext());
        }else{
            user = null;
        }
    }

    public User loginWithEmailPassword(String email, String pass){
        sql = "select * from " + Table_Name + " where " + Col_User_Email + " =? " + " and " + Col_User_Pass + " =?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{email,pass});
        User user;
        if(cursor.moveToFirst()){
            user = getUserFromCursor(cursor);
        }else{
            user = null;
        }
        return user;
    }

    private ContentValues getValuesFromUser(User user){
        ContentValues values = new ContentValues();
        values.put(Col_User_FName,user.getFname());
        values.put(Col_User_LName,user.getLname());
        values.put(Col_User_Email,user.getEmail());
        values.put(Col_User_Pass,user.getPass());
        values.put(Col_User_BDate,user.getbDate());
        values.put(Col_User_Hobbies,user.getHobbies());
        values.put(Col_User_Gender,user.getGender());
        return values;
    }

    private User getUserFromCursor(Cursor cursor){
        User user = new User();
        user.setFname(fromCursor(cursor,Col_User_FName));
        user.setId(cursor.getInt(cursor.getColumnIndex(Col_User_ID)));
        user.setLname(fromCursor(cursor,Col_User_LName));
        user.setEmail(fromCursor(cursor,Col_User_Email));
        user.setPass(fromCursor(cursor,Col_User_Pass));
        user.setbDate(fromCursor(cursor,Col_User_BDate));
        user.setHobbies(fromCursor(cursor,Col_User_Hobbies));
        user.setGender(fromCursor(cursor,Col_User_Gender));
        return user;
    }

    private String fromCursor(Cursor cursor,String colName){
        return cursor.getString(cursor.getColumnIndex(colName));
    }

    public void UpgradeTableUser(){
        sqLiteDatabase.execSQL(Delete_Table_User);
        sqLiteDatabase.execSQL(Create_Table_User);
    }

}
