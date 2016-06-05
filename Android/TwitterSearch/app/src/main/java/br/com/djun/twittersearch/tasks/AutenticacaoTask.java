package br.com.djun.twittersearch.tasks;

import android.os.AsyncTask;
import android.util.Base64;

import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONObject;

import java.util.HashMap;

public class AutenticacaoTask extends AsyncTask<Void,Void,Void>{
    private String accessToken;

    @Override
    protected Void doInBackground(Void... params) {
        try {
            HashMap<String, String> data = new HashMap<>();
            data.put("grant_type","client_credentials");

            String json = HttpRequest.post("https://api.twitter.com/oauth2/token")
                    .authorization("Basic " + gerarChave())
                    .form(data).body();
            JSONObject token	=	new JSONObject(json);
            accessToken	=	token.getString("access_token");
        } catch (Exception e) {

        }
        return null;
    }

    private String gerarChave(){
        String key = "nuDwCVCuyVqJdIwwaqQxC5fUq";
        String secret = "YRjuFNzuP8HaRlJ0JMZgOlPB5CTg78A1wsOYreyxAOLmA9cn19";
        String token = key+":"+secret;
        String base64 =	Base64.encodeToString(token.getBytes(),Base64.NO_WRAP);
        return	base64;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
