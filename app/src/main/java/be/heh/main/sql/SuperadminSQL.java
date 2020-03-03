package be.heh.main.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SuperadminSQL extends SQLiteOpenHelper {

    private static final String TABLE_USER = "TABLE_SUPERADMIN";
    private static final String COL_ID = "ID";
    private static final String COL_LOGIN = "LOGIN";
    private static final String COL_PASSWORD = "PASSWORD";
    private static final String COL_EMAIL = "EMAIL";
    private static  final  String COL_ROLE = "ROLE";
    private static final String CREATE_BDD = "CREATE TABLE "+
            TABLE_USER + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_LOGIN + " TEXT NOT NULL, " + COL_PASSWORD + " TEXT NOT NULL, " +
            COL_EMAIL + " TEXT NOT NULL, "+ COL_ROLE + " TEXT NOT NULL);";


    public SuperadminSQL(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_USER);
    }


}
