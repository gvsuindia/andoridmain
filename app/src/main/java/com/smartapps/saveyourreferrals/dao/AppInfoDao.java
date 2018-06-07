package com.smartapps.saveyourreferrals.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table APP_INFO.
*/
public class AppInfoDao extends AbstractDao<AppInfo, String> {

    public static final String TABLENAME = "APP_INFO";

    /**
     * Properties of entity AppInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property App_name = new Property(0, String.class, "app_name", false, "APP_NAME");
        public final static Property Package_name_name = new Property(1, String.class, "package_name_name", true, "PACKAGE_NAME_NAME");
        public final static Property Referral_text = new Property(2, String.class, "referral_text", false, "REFERRAL_TEXT");
        public final static Property Is_favourite = new Property(3, String.class, "is_favourite", false, "IS_FAVOURITE");
        public final static Property Base64_applogo = new Property(4, String.class, "base64_applogo", false, "BASE64_APPLOGO");
        public final static Property Test1 = new Property(5, String.class, "test1", false, "TEST1");
        public final static Property Test2 = new Property(6, String.class, "test2", false, "TEST2");
        public final static Property Test3 = new Property(7, String.class, "test3", false, "TEST3");
        public final static Property Test4 = new Property(8, String.class, "test4", false, "TEST4");
        public final static Property Time = new Property(9, String.class, "time", false, "TIME");
    };


    public AppInfoDao(DaoConfig config) {
        super(config);
    }
    
    public AppInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'APP_INFO' (" + //
                "'APP_NAME' TEXT," + // 0: app_name
                "'PACKAGE_NAME_NAME' TEXT PRIMARY KEY NOT NULL ," + // 1: package_name_name
                "'REFERRAL_TEXT' TEXT," + // 2: referral_text
                "'IS_FAVOURITE' TEXT," + // 3: is_favourite
                "'BASE64_APPLOGO' TEXT," + // 4: base64_applogo
                "'TEST1' TEXT," + // 5: test1
                "'TEST2' TEXT," + // 6: test2
                "'TEST3' TEXT," + // 7: test3
                "'TEST4' TEXT," + // 8: test4
                "'TIME' TEXT);"); // 9: time
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'APP_INFO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, AppInfo entity) {
        stmt.clearBindings();
 
        String app_name = entity.getApp_name();
        if (app_name != null) {
            stmt.bindString(1, app_name);
        }
 
        String package_name_name = entity.getPackage_name_name();
        if (package_name_name != null) {
            stmt.bindString(2, package_name_name);
        }
 
        String referral_text = entity.getReferral_text();
        if (referral_text != null) {
            stmt.bindString(3, referral_text);
        }
 
        String is_favourite = entity.getIs_favourite();
        if (is_favourite != null) {
            stmt.bindString(4, is_favourite);
        }
 
        String base64_applogo = entity.getBase64_applogo();
        if (base64_applogo != null) {
            stmt.bindString(5, base64_applogo);
        }
 
        String test1 = entity.getTest1();
        if (test1 != null) {
            stmt.bindString(6, test1);
        }
 
        String test2 = entity.getTest2();
        if (test2 != null) {
            stmt.bindString(7, test2);
        }
 
        String test3 = entity.getTest3();
        if (test3 != null) {
            stmt.bindString(8, test3);
        }
 
        String test4 = entity.getTest4();
        if (test4 != null) {
            stmt.bindString(9, test4);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(10, time);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1);
    }    

    /** @inheritdoc */
    @Override
    public AppInfo readEntity(Cursor cursor, int offset) {
        AppInfo entity = new AppInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // app_name
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // package_name_name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // referral_text
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // is_favourite
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // base64_applogo
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // test1
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // test2
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // test3
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // test4
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // time
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, AppInfo entity, int offset) {
        entity.setApp_name(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setPackage_name_name(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setReferral_text(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setIs_favourite(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setBase64_applogo(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTest1(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTest2(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setTest3(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setTest4(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setTime(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(AppInfo entity, long rowId) {
        return entity.getPackage_name_name();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(AppInfo entity) {
        if(entity != null) {
            return entity.getPackage_name_name();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
