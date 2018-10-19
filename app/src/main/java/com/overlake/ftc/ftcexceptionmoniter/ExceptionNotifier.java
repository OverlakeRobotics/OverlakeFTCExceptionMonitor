package com.overlake.ftc.ftcexceptionmoniter;

import android.app.Notification;
import android.app.NotificationManager;

public class ExceptionNotifier
{
    public static final String Title = "Exception Was Thrown!";
    public static final String Description = "Click here for more information about the exception";

    private NotificationManager manager;
    private Notification.Builder builder;

    public ExceptionNotifier(Notification.Builder builder, NotificationManager manager) {
        this.manager = manager;
        this.builder = builder;
    }

    public void sendNotification() {
        manager.notify(0, builder.build());
    }
}
