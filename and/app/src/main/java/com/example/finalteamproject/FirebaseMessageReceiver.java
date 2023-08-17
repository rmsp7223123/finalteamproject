package com.example.finalteamproject;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.example.finalteamproject.main.MainActivity;
import com.example.finalteamproject.main.MainAlarmHistoryActivity;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;
import java.util.concurrent.ExecutionException;

public class FirebaseMessageReceiver extends FirebaseMessagingService {
    private final String TAG = "확인용";
    private static int uniqueRandomValue = new Random().nextInt();
    @Override
    public void onNewToken(@NonNull String token)
    {
        Log.d(TAG, "Refreshed token: " + token);
    }
    // Override onMessageReceived() method to extract the
    // title and
    // body from the message passed in FCM



    @Override
    public void
    onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData() != null) {
            String checkValue = remoteMessage.getData().get("check");

            if (checkValue != null) {
                if (checkValue.equals("addFriend")) {
                    String title = remoteMessage.getNotification().getTitle();
                    String message = remoteMessage.getNotification().getBody();
                    showNotification(this, title, message);
//                    showAddFriendDialogOnMainThread(
//                            remoteMessage.getNotification().getTitle(),
//                            remoteMessage.getNotification().getBody());

                } else if (checkValue.equals("msgFriend")) {
//                    Intent intent = new Intent(this, YourActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
                    // 메시지 일경우에 그 채팅방으로 이동하게
                }
            }
        }

//        if (remoteMessage.getNotification() != null) {
//            showNotification(this,
//                    remoteMessage.getNotification().getTitle(),
//                    remoteMessage.getNotification().getBody());
//        }


    }

    // Method to get the custom Design for the display of
    // notification.
    private static RemoteViews getCustomDesign(Context context, String title,
                                        String message)
    {
        RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(),
                R.layout.notification);

        remoteViews.setTextViewText(R.id.title, title);
        remoteViews.setTextViewText(R.id.message, message);
//        try {
//            Bitmap bitmap = Glide.with(context)
//                    .asBitmap()
//                    .load(R.drawable.haerin2)
//                    .circleCrop()
//                    .submit(512, 512)
//                    .get();
//            Drawable d = new BitmapDrawable(context.getResources(), bitmap);
//            remoteViews.setImageViewBitmap(R.id.icon , bitmap);
//   //         remoteViews.setImageViewResource(R.id.icon ,   d.id);
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }


      /*  remoteViews.setImageViewResource(R.id.icon,
                R.drawable.haerin2);*/

        return remoteViews;
    }

    // Method to display the notifications
    public static void showNotification(Context context,String title,
                                 String message)
    {
        // Pass the intent to switch to the MainActivity
        Intent intent
                = new Intent(context, MainAlarmHistoryActivity.class);
        intent.putExtra("addFriend" , true);
        intent.putExtra("title" , title);
        intent.putExtra("message",message);
        // Assign channel ID
        String channel_id = "notification_channel";
        // Here FLAG_ACTIVITY_CLEAR_TOP flag is set to clear
        // the activities present in the activity stack,
        // on the top of the Activity that is to be launched
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Pass the intent to PendingIntent to start the
        // next Activity
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE
        );

        // Create a Builder object using NotificationCompat
        // class. This will allow control over all the flags
        NotificationCompat.Builder builder
                = new NotificationCompat
                .Builder(context,
                channel_id)
                .setSmallIcon(R.drawable.baseline_mail_outline_24)
                .setAutoCancel(true)
                .setVibrate(new long[] { 1000, 1000, 1000,
                        1000, 1000 })
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .setCustomContentView(getCustomDesign(context,title,message));
        NotificationManager notificationManager
                = (NotificationManager)context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        // Check if the Android Version is greater than Oreo
        if (Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel
                    = new NotificationChannel(
                    channel_id, "web_app",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(
                    notificationChannel);
        }

        notificationManager.notify(uniqueRandomValue, builder.build());
        uniqueRandomValue++;
    }

}


