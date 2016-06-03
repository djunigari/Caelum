package br.com.djun.boaviagem.repositories;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import br.com.djun.boaviagem.DatabaseHelper;

public class BaseRepository {
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public BaseRepository(Context context){
        helper = new DatabaseHelper(context);
    }

    public SQLiteDatabase getDb(){
        if(db == null){
            db = helper.getWritableDatabase();
        }
        return db;
    }

    public void close(){
        helper.close();
    }
}
