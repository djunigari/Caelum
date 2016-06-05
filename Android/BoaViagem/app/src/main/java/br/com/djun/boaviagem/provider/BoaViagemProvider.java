package br.com.djun.boaviagem.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import br.com.djun.boaviagem.repositories.DatabaseHelper;

import static br.com.djun.boaviagem.provider.BoaViagemContract.*;
import static br.com.djun.boaviagem.provider.BoaViagemContract.AUTHORITY;
import static br.com.djun.boaviagem.provider.BoaViagemContract.GASTO_PATH;
import static br.com.djun.boaviagem.provider.BoaViagemContract.VIAGEM_PATH;

public class BoaViagemProvider extends ContentProvider {
    private DatabaseHelper helper;

    private static final int VIAGENS = 1;
    private static final int VIAGEM_ID = 2;
    private static final int GASTOS = 3;
    private static final int GASTO_ID = 4;
    private static final int GASTOS_VIAGEM_ID = 5;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        uriMatcher.addURI(AUTHORITY,VIAGEM_PATH,VIAGENS);
        uriMatcher.addURI(AUTHORITY,VIAGEM_PATH+"/#",VIAGEM_ID);
        uriMatcher.addURI(AUTHORITY,GASTO_PATH,GASTOS);
        uriMatcher.addURI(AUTHORITY,GASTO_PATH+"/#",GASTO_ID);
        uriMatcher.addURI(AUTHORITY,GASTO_PATH+"/"+VIAGEM_PATH+"/#",GASTOS_VIAGEM_ID);
    }


    public BoaViagemProvider(){
        helper = new DatabaseHelper(getContext());
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = helper.getReadableDatabase();
        switch (uriMatcher.match(uri)){
            case VIAGENS:
                return	db.query(VIAGEM_PATH,projection,selection,selectionArgs,null,null,sortOrder);
            case VIAGEM_ID:
                selection = Viagem._ID + " = ?";
                selectionArgs = new String[] {uri.getLastPathSegment()};
                return	db.query(VIAGEM_PATH,projection,selection,selectionArgs,null,null,sortOrder);
            case GASTOS:
                return	db.query(GASTO_PATH,projection,selection,selectionArgs,null,null,sortOrder);
            case GASTO_ID:
                selection = Gasto._ID + " = ?";
                selectionArgs = new String[] {uri.getLastPathSegment()};
                return	db.query(GASTO_PATH,projection,selection,selectionArgs,null,null,sortOrder);
            case GASTOS_VIAGEM_ID:
                selection = Gasto.VIAGEM_ID + " = ?";
                selectionArgs = new String[] {uri.getLastPathSegment()};
                return	db.query(GASTO_PATH,projection,selection,selectionArgs,null,null,sortOrder);
            default:
                throw new IllegalArgumentException("Uri desconhecida");
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case VIAGENS:
                return	Viagem.CONTENT_TYPE;
            case VIAGEM_ID:
                return	Viagem.CONTENT_ITEM_TYPE;
            case GASTO_ID:
                return	Gasto.CONTENT_ITEM_TYPE;
            case GASTOS:
            case GASTOS_VIAGEM_ID:
                return	Gasto.CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Uri desconhecida");
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = helper.getWritableDatabase();
        long id;
        switch (uriMatcher.match(uri)){
            case VIAGEM_ID:
                id = db.insert(VIAGEM_PATH,null,values);
                return Uri.withAppendedPath(Viagem.CONTENT_URI, String.valueOf(id));
            case GASTO_ID:
                id = db.insert(GASTO_PATH,null,values);
                return Uri.withAppendedPath(Gasto.CONTENT_URI, String.valueOf(id));
            default:
                throw new IllegalArgumentException("Uri desconhecida");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        switch (uriMatcher.match(uri)){
            case VIAGEM_ID:
                selection = Viagem._ID + " = ?";
                selectionArgs = new String[] {uri.getLastPathSegment()};
                return db.delete(VIAGEM_PATH,selection,selectionArgs);
            case GASTO_ID:
                selection = Gasto._ID + " = ?";
                selectionArgs = new String[] {uri.getLastPathSegment()};
                return db.delete(GASTO_PATH,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Uri desconhecida");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        switch (uriMatcher.match(uri)){
            case VIAGEM_ID:
                selection = Viagem._ID + " = ?";
                selectionArgs = new String[] {uri.getLastPathSegment()};
                return db.update(VIAGEM_PATH,values,selection,selectionArgs);
            case GASTO_ID:
                selection = Gasto._ID + " = ?";
                selectionArgs = new String[] {uri.getLastPathSegment()};
                return db.update(GASTO_PATH,values,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Uri desconhecida");
        }
    }
}
