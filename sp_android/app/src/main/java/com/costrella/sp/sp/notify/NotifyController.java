package com.costrella.sp.sp.notify;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import com.costrella.sp.sp.MainActivity;
import com.costrella.sp.sp.R;
import com.costrella.sp.sp.afternotif.AfterNotifyActivity;

import java.util.Random;


public class NotifyController {

    public static int familyId;

    public static void addNotification(Context context, String body)
    {
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.cast_ic_notification_small_icon) //this is the icon in notification
                        .setContentTitle("Szlachetna Paczka - powiadomienie od leadera")   //this is the title of notification
                        .setColor(101)                         // this is the color of notification
                        .setContentText("Otwórz");   //this is the message showed in notification
                        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                        builder.setSound(uri);

        Intent intent = new Intent(context, AfterNotifyActivity.class);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Random generator = new Random();
        int i = generator.nextInt(1000) + 1;

        manager.notify(i, builder.build());
    }
}
