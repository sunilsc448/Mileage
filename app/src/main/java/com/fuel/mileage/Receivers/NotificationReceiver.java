package com.fuel.mileage.Receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;

import com.fuel.mileage.Activities.MainActivity;
import com.fuel.mileage.R;
import com.fuel.mileage.Utilities.Extras;

/**
 * Created by Sunil Kumar on 8/1/2016.
 */
public class NotificationReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Calculate Your Mileage!")
                .setContentText("Touch to see pending items")
                .setSmallIcon(R.drawable.calendar_icon)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent).build();

        Notification notification = builder.build();
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_SOUND;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Extras.NOTIFICATION_ID, notification);

        //((App)context.getApplicationContext()).setNotification();
    }
}
