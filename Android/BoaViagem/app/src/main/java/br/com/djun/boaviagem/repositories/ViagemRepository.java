package br.com.djun.boaviagem.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.djun.boaviagem.Constantes;
import br.com.djun.boaviagem.R;
import br.com.djun.boaviagem.domain.Viagem;

public class ViagemRepository extends BaseRepository{
    public ViagemRepository(Context context) {
        super(context);
    }

    public long save(Viagem v){
        ContentValues values = new ContentValues();
        values.put("destino",v.getDestino());
        values.put("data_chegada",v.getDataChegada().getTime());
        values.put("data_saida",v.getDataSaida().getTime());
        values.put("orcamento",v.getOrcamento());
        values.put("quantidade_pessoas",v.getQuantidadePessoas());
        values.put("tipo_viagem", v.getTipoViagem());

        return getDb().insert("viagem", null, values);
    }

    public long update(Viagem v){
        ContentValues values = new ContentValues();
        values.put("destino",v.getDestino());
        values.put("data_chegada",v.getDataChegada().getTime());
        values.put("data_saida",v.getDataSaida().getTime());
        values.put("orcamento",v.getOrcamento());
        values.put("quantidade_pessoas",v.getQuantidadePessoas());
        values.put("tipo_viagem", v.getTipoViagem());

        return getDb().update("viagem",values," _id = ?",new String[]{v.getId().toString()});
    }

    public Viagem findById(Long id){
        Cursor cursor = getDb().rawQuery("SELECT _id, tipo_viagem, destino, data_chegada,data_saida,orcamento,quantidade_pessoas FROM viagem WHERE _id = ?" , new String[]{id.toString()});
        cursor.moveToFirst();
        Viagem viagem = criarViagem(cursor);
        cursor.close();
        return viagem;
    }

    public List<Viagem> listarViagens(){
        Cursor cursor = getDb().rawQuery("SELECT _id, tipo_viagem, destino, data_chegada,data_saida,orcamento,quantidade_pessoas FROM viagem", null);
        List<Viagem> list = new ArrayList<>();
        while(cursor.moveToNext()){
            list.add(criarViagem(cursor));
        }
        cursor.close();
        return list;
    }

    public void removerViagem(Long id) {
        String[] where = {id.toString()};
        getDb().delete("gasto","viagem_id=?",where);
        getDb().delete("viagem","_id=?",where);
    }

    public double getTotalGastoByViagemId(Long id){
        Cursor cursor = getDb().rawQuery("SELECT SUM(valor) FROM gasto WHERE viagem_id = ?", new String[]{id.toString()});
        cursor.moveToFirst();
        double total = cursor.getDouble(0);
        cursor.close();
        return total;
    }

    private Viagem criarViagem(Cursor c){
        Viagem viagem = new Viagem();
        viagem.setId(c.getLong(0));
        viagem.setTipoViagem(c.getInt(1));
        viagem.setDestino(c.getString(2));
        viagem.setDataChegada(new Date(c.getLong(3)));
        viagem.setDataSaida(new Date(c.getLong(4)));
        viagem.setOrcamento(c.getDouble(5));
        viagem.setQuantidadePessoas(c.getInt(6));
        return viagem;
    }
}
