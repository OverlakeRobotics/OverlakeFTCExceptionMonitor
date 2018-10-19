package com.overlake.ftc.ftcexceptionmoniter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.FileObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import br.tiagohm.materialfilechooser.MaterialFileChooser;
import br.tiagohm.materialfilechooser.Sorter;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ExceptionMonitor extends AppCompatActivity
{
    private final String ExceptionPath = "/FTC_Exceptions.txt";

    private ExceptionFileWatcher fileWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception_moniter);

        fileWatcher = new ExceptionFileWatcher(getWatchFilePath(), createRenderer(), createNotifier());
        fileWatcher.watch();

        ExceptionClearer exceptionClearer = new ExceptionClearer(
                (Button)findViewById(R.id.ExceptionClearButton),
                getWatchFilePath(),
                fileWatcher
        );
        exceptionClearer.addListener();
    }

    private String getWatchFilePath() {
        return Environment.getExternalStorageDirectory() + ExceptionPath;
    }

    private ExceptionRenderer createRenderer() {
        TextView exceptionText = findViewById(R.id.ExceptionView);
        exceptionText.setMovementMethod(new ScrollingMovementMethod());
        return new ExceptionRenderer(exceptionText);
    }

    private ExceptionNotifier createNotifier() {
        Intent intent = new Intent(ExceptionMonitor.this, ExceptionMonitor.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(ExceptionMonitor.this, 0, intent, 0);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(ExceptionNotifier.Title)
                .setContentText(ExceptionNotifier.Description)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        return new ExceptionNotifier(builder, notificationManager);
    }
}
