package com.example.inhm.project_heatcon;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 데이터 베이스를 관리하는 클래스이다.
 * SQLite헬퍼를 상속받아 데이터베이스 업그레이드 시 기존 데이터의 내용을 보안하였다.
 */

public class MyDataBases extends SQLiteOpenHelper {

    public MyDataBases(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table mytable(check text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table mytable;";
        db.execSQL(sql);
        onCreate(db);
    }

    public MyDataBases(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


}
