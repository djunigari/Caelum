package br.com.djun.twittersearch.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.djun.twittersearch.R;
import br.com.djun.twittersearch.tasks.AutenticacaoTask;

public class TwitterSearchActivity extends Activity{
    private AutenticacaoTask autenticacaoTask;
    private EditText texto;
    private ListView lista;

    public TwitterSearchActivity(){
        autenticacaoTask = new AutenticacaoTask();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        texto = (EditText) findViewById(R.id.texto);
        lista = (ListView) findViewById(R.id.lista);

        new AutenticacaoTask().execute();
    }

    public void buscar(View view){
        String filtro = texto.getText().toString();
        if(autenticacaoTask.getAccessToken() == null){
            Toast.makeText(this,"Token não disponivel",Toast.LENGTH_SHORT);
        }else {
            new TwitterTask().execute(filtro);
        }
    }

    private class TwitterTask extends AsyncTask<String,Void,List<String>>{
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(TwitterSearchActivity.this);
            dialog.show();
        }

        @Override
        protected List<String> doInBackground(String... params) {
            try {
                if(params.length == 0){
                    return null;
                }
                String filtro = params[0];
                if(TextUtils.isEmpty(filtro)){
                    return null;
                }

                String urlTwitter = "https://api.twitter.com/1.1/search/tweets.json?q=";
                String url = Uri.parse(urlTwitter + filtro).toString();
                String conteudo = HttpRequest.get(url)
                        .authorization("Bearer " + autenticacaoTask.getAccessToken())
                        .body();

                JSONObject jsonObject = new JSONObject(conteudo);
                JSONArray resultados = jsonObject.getJSONArray("statuses");

                List<String> tweets = new ArrayList<>();
                for(int i = 0; i < resultados.length();i++){
                    JSONObject tweet = resultados.getJSONObject(i);
                    String texto = tweet.getString("text");
                    String usuario = tweet.getJSONObject("user").getString("screen_name");
                    tweets.add(usuario+"-"+texto);
                }
                return tweets;
            } catch (JSONException e) {
                Log.e(getPackageName(),	e.getMessage(),	e);
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPostExecute(List<String> result) {
            if(result != null){
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),android.R.layout.simple_list_item_1,result);
                lista.setAdapter(adapter);
            }
            dialog.dismiss();
        }
    }
}
