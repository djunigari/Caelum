package br.com.djun.twittersearch.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import br.com.djun.twittersearch.R;
import br.com.djun.twittersearch.activities.TweetActivity;
import br.com.djun.twittersearch.tasks.AutenticacaoTask;

public class NotificacaoService extends Service {
    private AutenticacaoTask autenticacaoTask;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        autenticacaoTask = new AutenticacaoTask();
        autenticacaoTask.execute();
        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(1);
        long delayInicial = 0;
        long periodo = 10;
        TimeUnit unit = TimeUnit.SECONDS;
        pool.scheduleWithFixedDelay(new NotificacaoTask(),delayInicial,periodo,unit);
        return START_STICKY;
    }

    public boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info == null){
            return false;
        }

        return info.isConnected();
    }

    private void criarNotificacao(String user, String msg, int id){
        System.out.println("fffffffffffffffffffffffffffffffffffffffffffffff");
        int icone = R.drawable.ic_launcher;
        String aviso = getString(R.string.aviso);
        long data = System.currentTimeMillis();
        String titulo = user+" "+getString(R.string.titulo);

        Context context = getApplicationContext();
        Intent intent = new Intent(context, TweetActivity.class);
        intent.putExtra(TweetActivity.USUARIO, user);
        intent.putExtra(TweetActivity.TEXTO, msg);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,id,intent, 0);

        Notification notification = new Notification.Builder(context)
                .setSmallIcon(icone)
                .setTicker(aviso)
                .setContentTitle(titulo)
                .setContentText(msg)
                .setNumber(100)
                .setAutoCancel(false)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setLights(Color.RED, 3000, 3000)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .getNotification();


        NotificationManager	notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id,	notification);
        System.out.println("ggggggggggggggggggggggggggggggggggggggggggg");
    }

    private class NotificacaoTask implements Runnable{
        private	String baseUrl = "https://api.twitter.com/1.1/search/tweets.json";
        private	String refreshUrl = "?q=@android";

        @Override
        public void run() {
            if(!isConnected()){
                return;
            }
            try{
                String url = baseUrl + refreshUrl;
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa    "+autenticacaoTask.getAccessToken());
                System.out.println("bbbbbbbbbbbbbbb     "+url);
                String conteudo = HttpRequest.get(url)
                        .authorization("Bearer " + autenticacaoTask.getAccessToken())
                        .body();

                System.out.println("cccccccccccccccc"+conteudo);

                JSONObject jsonObject = new JSONObject(conteudo);
                refreshUrl = jsonObject.getJSONObject("search_metadata").getString("refresh_url");
                JSONArray resultados = jsonObject.getJSONArray("statuses");

                for(int i = 0; i < resultados.length(); i++){
                    JSONObject tweet = resultados.getJSONObject(i);
                    String texto = tweet.getString("text");
                    String usuario = tweet.getJSONObject("user")
                            .getString("screen_name");
                    criarNotificacao(usuario,	texto,	i);
                }
                System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
            }catch(JSONException e){
                Log.e(getPackageName(), e.getMessage(), e);
            }
        }
    }
}
