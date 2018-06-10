package com.vitamin.wecantalk;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;
import com.vitamin.wecantalk.Adapter.CommunityRoomListViewAdapter;
import com.vitamin.wecantalk.POJO.CommunityRoomListViewPOJO;
import com.vitamin.wecantalk.UIActivity.CommunityRoomActivity;
import com.vitamin.wecantalk.UIActivity.MainFragmentActivity;

/**
 * Created by anemo on 2018-06-10.
 */
public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String messageBody="";

        //추가한것
        //sendNotification(remoteMessage.getData().get("message"));

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "FCM Data Message : " + remoteMessage.getData());
            messageBody = remoteMessage.getData().get("message_body");
            if (messageBody != null) {
                toast(messageBody);
            }
        }

        if (remoteMessage.getNotification() != null) {
            messageBody = remoteMessage.getNotification().getBody();
            Log.d(TAG, "FCM Notification Message Body : " + messageBody);
            toast(messageBody);
        }
        broadcastIntent(messageBody);
        //sendNotification(remoteMessage.getNotification().getBody());
    }

    void toast(final String message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainFragmentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("FCM로 부터 메세지가 왔습니다.")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    public void broadcastIntent(String messageBody){
        Intent intent = new Intent();
        intent.setAction("CUSTOM_EVENT");
        intent.putExtra("data",messageBody);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}








//    private void sendNotification(String messageBody) {
//        @SuppressWarnings("deprecation") NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("FCM Push Test") // 이부분은 어플 켜놓은 상태에서 알림 메세지 받으면 저 텍스트로 띄워준다.
//                .setContentText(messageBody)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//    }
//
//}
