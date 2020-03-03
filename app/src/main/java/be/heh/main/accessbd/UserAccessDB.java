package be.heh.main.accessbd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import be.heh.main.entity.SuperAdministrateur;
import be.heh.main.sql.SuperadminSQL;

public class UserAccessDB {

    private static final int VERSION = 1;
    private static final String NAME_DB = "User.db";
    /*SUPERADMIN*/
    private static final String TABLE_SUPERADMIN = "table_superadmin";
    private static final int NUM_COL_ID = 0;
    private static final String COL_ID = "ID";
    private static final String COL_LOGIN = "LOGIN";
    private static final int NUM_COL_LOGIN = 1;
    private static final String COL_PASSWORD = "PASSWORD";
    private static final int NUM_COL_PASSWORD = 2;
    private static final String COL_EMAIL = "EMAIL";
    private static final int NUM_COL_EMAIL = 3;
    /*SUPERADMIN*/
    /*USERS*/
    private static final int NUM_COL_ROLE = 4;
    private static final String COL_ROLE = "ROLE";
    /*USERS*/

    private SQLiteDatabase db;
    private SuperadminSQL superadminSQL;


    public UserAccessDB(Context c){
        superadminSQL = new SuperadminSQL(c,NAME_DB,null,VERSION);


    }

    public void openForWrite(){
        db = superadminSQL.getWritableDatabase();
    }

    public void openForRead(){
        db = superadminSQL.getReadableDatabase();
    }

    public void Close(){
        db.close();
    }

    public long insertSuperAdmin(SuperAdministrateur u){
        ContentValues content = new ContentValues();
        content.put(COL_LOGIN, u.getLogin());
        content.put(COL_PASSWORD, u.getPassword());
        content.put(COL_EMAIL, u.getMail());
        content.put(COL_ROLE,u.getRole());
        return db.insertOrThrow(TABLE_SUPERADMIN, null, content);
    }

    public void removeTableSuperUser()
    {
        superadminSQL.onUpgrade(db,0,0);
    }

    public int updateSuperAdmin(int i, SuperAdministrateur u){
        ContentValues content = new ContentValues();
        content.put(COL_LOGIN, u.getLogin());
        content.put(COL_PASSWORD, u.getPassword());
        content.put(COL_EMAIL, u.getMail());
        content.put(COL_ROLE,u.getRole());
        return db.update(TABLE_SUPERADMIN, content, COL_ID + " = " + i, null);
    }

    public int removeSuperAdmin(String login) {
        return db.delete(TABLE_SUPERADMIN, COL_EMAIL + " = " +
                login, null);
    }

    public int truncateSuperAdmin() {
        return db.delete(TABLE_SUPERADMIN,null,null);
    }

    public SuperAdministrateur cursorToSuperadmin(Cursor c) {
        if (c.getCount() == 0) {
            c.close();
            System.out.println("okqdqdqsdqsdsq");
            return null;
        }

        c.moveToFirst();
        SuperAdministrateur user1 = new SuperAdministrateur();
        user1.setId(c.getInt(NUM_COL_ID));
        user1.setLogin(c.getString(NUM_COL_LOGIN));
        user1.setPassword(c.getString(NUM_COL_PASSWORD));
        user1.setMail(c.getString(NUM_COL_EMAIL));
        user1.setRole(c.getString(NUM_COL_ROLE));
        c.close();

        return user1;
    }

    public SuperAdministrateur getSuperAdminMailAndPassword(String mail, String password)
    {
      Cursor c = db.rawQuery("SELECT * FROM "+TABLE_SUPERADMIN+ " WHERE "+COL_EMAIL+ " IS "+"'"+mail+"'"+ " AND "+COL_PASSWORD+" IS "+"'"+password+"'"+";",null);
      return  cursorToSuperadmin(c);
    }

    public SuperAdministrateur getSuperAdminId(String id)
    {
        int idint=Integer.parseInt(id);
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_SUPERADMIN+ " WHERE "+COL_ID+ " IS "+"'"+idint+"'"+ ";",null);
        return  cursorToSuperadmin(c);
    }
    public SuperAdministrateur getSuperAdmin(String login)
    {
        Cursor c = db.query(TABLE_SUPERADMIN,new String[] {
                COL_ID, COL_LOGIN,COL_PASSWORD,COL_EMAIL,COL_ROLE},
                COL_EMAIL + " LIKE \"" + login + "\"",
        null,null,null,COL_LOGIN);

        return cursorToSuperadmin(c);
    }



    public SuperAdministrateur getSuperAdminPassword(String password)
    {
        Cursor c = db.query(TABLE_SUPERADMIN,new String[] {
                        COL_ID, COL_LOGIN,COL_PASSWORD,COL_EMAIL,COL_ROLE},
                COL_PASSWORD + " LIKE \"" + password + "\"",
                null,null,null,COL_LOGIN);

        return cursorToSuperadmin(c);
    }

    public ArrayList<SuperAdministrateur> getAllSuperAdmin(){
        Cursor c = db.query(TABLE_SUPERADMIN, new String[] {
                COL_ID, COL_LOGIN, COL_PASSWORD,COL_EMAIL,COL_ROLE
        },null,null,null,null,COL_LOGIN);

        ArrayList<SuperAdministrateur> tabUser = new ArrayList<>();
        if(c.getCount() == 0)
        {
            c.close();
            return  tabUser;
        }

        while(c.moveToNext())
        {
            SuperAdministrateur user1 = new SuperAdministrateur();
            user1.setId(c.getInt(NUM_COL_ID));
            user1.setLogin(c.getString(NUM_COL_LOGIN));
            user1.setPassword(c.getString(NUM_COL_PASSWORD));
            user1.setMail(c.getString(NUM_COL_EMAIL));
            user1.setRole(c.getString(NUM_COL_ROLE));
            tabUser.add(user1);

        }

        c.close();

        return tabUser;
    }

    public int removeUser(String id) {

        int idint=Integer.parseInt(id);
        return db.delete(TABLE_SUPERADMIN, COL_ID + " = " +
                idint, null);
    }




}
