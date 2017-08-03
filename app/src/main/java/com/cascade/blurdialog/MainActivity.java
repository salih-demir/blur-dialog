package com.cascade.blurdialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showBlurDialog(View v) {
        AlertDialog alertDialog = new AlertDialog();
        alertDialog.show(getFragmentManager(), AlertDialog.class.getSimpleName());
    }
}