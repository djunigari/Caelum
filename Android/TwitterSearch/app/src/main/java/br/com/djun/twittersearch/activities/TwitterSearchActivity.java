package br.com.djun.twittersearch.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.djun.twittersearch.R;

public class TwitterSearchActivity extends Activity{
    private EditText texto;
    private ListView lista;
    private String accessToken;

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
        if(accessToken == null){
            Toast.makeText(this,"Token não disponivel",Toast.LENGTH_SHORT);
        }else {
            new TwitterTask().execute(filtro);
        }
    }

    private class AutenticacaoTask extends AsyncTask<Integer,Void,Void>{

        @Override
        protected Void doInBackground(Integer... params) {
            try {
                HashMap<String, String> data = new HashMap<>();
                data.put("grant_type","client_credentials");

                String json = HttpRequest.post("https://api.twitter.com/oauth2/token")
                        .authorization("Basic " + gerarChave())
                        .form(data).body();
                JSONObject	token	=	new JSONObject(json);
                accessToken	=	token.getString("access_token");
            } catch (Exception e) {

            }
            return null;
        }

        private String gerarChave() throws UnsupportedEncodingException {
            String key = "nuDwCVCuyVqJdIwwaqQxC5fUq";
            String secret = "YRjuFNzuP8HaRlJ0JMZgOlPB5CTg78A1wsOYreyxAOLmA9cn19";
            String token = key+":"+secret;
            String base64 =	Base64.encodeToString(token.getBytes(),Base64.NO_WRAP);
            return	base64;
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
                String conteudo = HttpRequest.get(url).authorization("Bearer " + accessToken).body();

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
