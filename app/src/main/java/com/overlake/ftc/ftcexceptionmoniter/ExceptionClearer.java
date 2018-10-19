package com.overlake.ftc.ftcexceptionmoniter;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExceptionClearer
{
    private Button button;
    private String path;
    private ExceptionFileWatcher exceptionFileWatcher;

    public ExceptionClearer(Button button, String path, ExceptionFileWatcher watcher) {
        this.button = button;
        this.path = path;
        this.exceptionFileWatcher = watcher;
    }

    public void addListener() {
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                clearException();
            }
        });
    }

    private void clearException() {
        exceptionFileWatcher.stop();
        FileOutputStream fileOutputStream = getOutPutStream();
        clearFile(fileOutputStream);
        exceptionFileWatcher.watch();
    }

    private FileOutputStream getOutPutStream() {
        try
        {
            return new FileOutputStream(path);
        }
        catch (FileNotFoundException e)
        {
            throw new IllegalArgumentException("Couldn't find file at path: " + path);
        }
    }

    private void clearFile(FileOutputStream fileOutputStream) {
        try
        {
            fileOutputStream.write(new byte[0]);
            fileOutputStream.close();
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException("Failed to clear file at path: " + path);
        }
    }
}
