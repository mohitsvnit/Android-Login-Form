package ando.com.loginasssignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mohit on 28/3/17.
 */

public class Database extends SQLiteOpenHelper {

    public static String DatabaseName = "logginAssignment.db";
    private static SQLiteDatabase databaseInstance = null;

    public static SQLiteDatabase getDatabaseInstance(Context context){
        if(databaseInstance == null){
            databaseInstance = new Database(context).getWritableDatabase();
        }
        return databaseInstance;
    }

    private Database(Context context) {
        super(context, DatabaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserTable.Create_Table_User);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserTable.Delete_Table_User);
        db.execSQL(UserTable.Create_Table_User);
    }
}
