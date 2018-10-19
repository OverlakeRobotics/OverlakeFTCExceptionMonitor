package com.overlake.ftc.ftcexceptionmoniter;

import android.widget.TextView;

import org.w3c.dom.Text;

public class ExceptionRenderer
{
    private TextView view;

    public ExceptionRenderer(TextView view) {
        this.view = view;
    }

    public void render(String exception) {
        view.setText(exception);
    }
}
