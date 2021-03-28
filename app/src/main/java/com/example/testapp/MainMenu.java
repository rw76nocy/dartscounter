package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

    }

    public void playClick(View view) {
        Intent intent = new Intent(this,ChooseMenu.class);
        startActivity(intent);
    }

    public void endClick(View view) {
        this.finish();
        System.exit(0);
    }
}