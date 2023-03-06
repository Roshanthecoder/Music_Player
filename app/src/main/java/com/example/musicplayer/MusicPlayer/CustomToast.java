package com.example.musicplayer.MusicPlayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicplayer.R;

public class CustomToast extends Toast {
    private Context context;
    private String msg;

    public CustomToast(Context context,String msg) {
        super(context);
        this.context=context;
        this.msg=msg;
        View view= LayoutInflater.from(context).inflate(R.layout.customtoast_row,null);
        TextView txtMsg = view.findViewById(R.id.txt_message);
        txtMsg.setText(msg);
        setView(view);
        setDuration(Toast.LENGTH_SHORT);
    }
}
