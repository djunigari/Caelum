package br.com.djun.boaviagem.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.djun.boaviagem.domain.Gasto;

public class GastoRepository extends BaseRepository{
    public GastoRepository(Context context) {
        super(context);
    }

    public long save(Gasto g){
        ContentValues values = new ContentValues();
        values.put("categoria",g.getCategoria());
        values.put("data",g.getData().getTime());
        values.put("valor", g.getValor());
        values.put("descricao",g.getDescricao());
        values.put("local",g.getLocal());
        values.put("viagem_id",g.getViagemId());
        return getDb().insert("gasto",null,values);
    }

    public long update(Gasto g){
        ContentValues values = new ContentValues();
        values.put("categoria",g.getCategoria());
        values.put("data",g.getData().getTime());
        values.put("valor", g.getValor());
        values.put("descricao",g.getDescricao());
        values.put("local",g.getLocal());
        values.put("viagem_id",g.getViagemId());
        return getDb().update("gasto",values,"_id = ?",new String[]{g.getId().toString()});
    }

    public Gasto findById(Long id){
        Cursor cursor = getDb().rawQuery("SELECT _id,categoria,data,valor,descricao,local,viagem_id FROM gasto WHERE _id = ?", new String[]{id.toString()});
        cursor.moveToFirst();
        return criarGasto(cursor);
    }

    public List<Gasto> getGastosByViagemId(Long id){
        Cursor cursor = getDb().rawQuery("SELECT _id,categoria,data,valor,descricao,local,viagem_id FROM gasto WHERE viagem_id = ?", new String[]{id.toString()});
        List<Gasto> list = new ArrayList<>();
        while(cursor.moveToNext()){
            list.add(criarGasto(cursor));
        }
        cursor.close();
        return list;
    }

    public List<Gasto> getGastos(){
        Cursor cursor = getDb().rawQuery("SELECT _id,categoria,data,valor,descricao,local,viagem_id FROM gasto", null);
        List<Gasto> list = new ArrayList<>();
        while(cursor.moveToNext()){
            list.add(criarGasto(cursor));
        }
        cursor.close();
        return list;
    }

    private Gasto criarGasto(Cursor c) {
        Gasto gasto = new Gasto();
        gasto.setId(c.getLong(0));
        gasto.setCategoria(c.getString(1));
        gasto.setData(new Date(c.getLong(2)));
        gasto.setValor(c.getDouble(3));
        gasto.setDescricao(c.getString(4));
        gasto.setLocal(c.getString(5));
        gasto.setViagemId(c.getInt(6));
        return gasto;
    }

}
