package com.nandhu.employeedata.utils;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;



public class BaseActivity extends AppCompatActivity {
    Toolbar toolbar;
    private ProgressWheel progress;
    public boolean isProgressShowing=false;
    private ActionBar actionBar = null;
    RecyclerView attachmentsRecycler;
    private static final int SELECT_FILE_REQUEST = 777;
    private static final int PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE = 23;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }





    protected ActionBar getActiveActionBar() {
        if (actionBar == null) {
            ActionBarUtils.setActionBar(this, false);
            actionBar = getSupportActionBar();
        }
        return actionBar;
    }
    public void showAlert(String message, String title) {

        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);

        builder.setMessage(message)
                .setTitle(title);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public ProgressWheel getProgress() {
        if (progress == null) {
            progress = new ProgressWheel(this);
        }
        return progress;
    }

    public boolean getProgressStatus()
    {
        if (progress==null){
            return false;
        }else {
            return true;
        }
    }
    public void showProgressWheel() {

        isProgressShowing=true;
        ViewGroup parent = (ViewGroup) getProgress().getParent();
        if (parent != null) {
            parent.removeView(getProgress());
        }

        FrameLayout rootLayout = (FrameLayout) findViewById(android.R.id.content);
        rootLayout.addView(getProgress());
        /*dialog = new Dialog(this);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setLayout(width, height);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_lottie);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();*/
    }

    public void hideProgressWheel(boolean animation) {
        ViewGroup parent = (ViewGroup) getProgress().getParent();
        if (parent != null) {
            parent.removeView(getProgress());
            progress = null;
        }

        if (progress!=null){
            progress=null;
        }
        isProgressShowing=false;
        /*if (dialog!=null){
            dialog.dismiss();
        }*/
    }

    public void progressWheelSetTitle(String title) {

        ViewGroup parent = (ViewGroup) getProgress().getParent();
        if(parent != null) {
            getProgress().setTitleText(title);
        }
        isProgressShowing=true;
    }

    public void progressWheelHideTitle() {

        ViewGroup parent = (ViewGroup) getProgress().getParent();
        if(parent != null) {
            getProgress().setTitleText("");
            getProgress().hideTitleView();
        }
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    public void showAlert(String message, String title, DialogInterface.OnClickListener onClickListener) {

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);

            builder.setMessage(message)
                    .setTitle(title);
            builder.setCancelable(false);
            builder.setPositiveButton("Retry", onClickListener);

            AlertDialog dialog = builder.create();
            dialog.show();
        }catch (Exception e){}


    }



}