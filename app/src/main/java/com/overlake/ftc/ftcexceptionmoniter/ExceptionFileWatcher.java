package com.overlake.ftc.ftcexceptionmoniter;

import android.os.FileObserver;
import android.support.annotation.Nullable;
import android.widget.TextView;

public class ExceptionFileWatcher extends FileObserver
{
    private String exception;
    private ExceptionRenderer renderer;
    private ExceptionNotifier notifier;
    private ExceptionReader reader;

    public ExceptionFileWatcher(String path, ExceptionRenderer renderer, ExceptionNotifier notifier)
    {
        super(path);
        this.notifier = notifier;
        this.renderer = renderer;
        this.reader = new ExceptionReader(path);
    }

    public void watch() {
        reader.ensureFileExists();
        renderer.render(reader.read());
        this.startWatching();
    }

    public void stop() {
        this.stopWatching();
    }

    @Override
    public void onEvent(int event, String path)
    {
        if (exceptionWritten(event)) {
            renderer.render(reader.read());
            notifier.sendNotification();
        }
    }

    private boolean exceptionWritten(int event) {
        return event == FileObserver.CLOSE_WRITE;
    }
}
