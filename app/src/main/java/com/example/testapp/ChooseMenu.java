package com.example.testapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChooseMenu extends AppCompatActivity {

    private ArrayList<String> names = new ArrayList<>();
    private String points;
    private String in;
    private String out;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_menu);
    }

    public void setClick(View view) {
        Spinner sp1 = findViewById(R.id.numberPoints);
        this.points = sp1.getSelectedItem().toString();
        System.out.println("Punkte: " +this.points);

        Spinner sp2 = findViewById(R.id.inOption);
        this.in = sp2.getSelectedItem().toString();
        System.out.println("In: " +this.in);

        Spinner sp3 = findViewById(R.id.outOption);
        this.out = sp3.getSelectedItem().toString();
        System.out.println("Out: " +this.out);

        //Testausgabe der Spielernamen
        for(int i=0;i<this.names.size();i++){
            System.out.println("Name: "+names.get(i));
        }

        //Checke ob mindestens 2 Spieler verfügbar sind, ansonsten gib eine Meldung aus
        if(names.size()>=2){
            //Points, In, Out, Players names per Intent an Game GUI übergeben
            Intent intent = new Intent(this, GameGUI.class);
            sendInputToGame(intent,points,in,out,names);
            startActivity(intent);
        }else{
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Please enter at least 2 Players!");
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });
            alert.show();
        }
    }

    public void plusClick(View view) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        //alert.setTitle("Name of the Player");
        alert.setMessage("Please enter the player name:");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if(!String.valueOf(input.getText()).equals("")){
                    names.add(String.valueOf(input.getText()));
                    System.out.println("Spielername: "+String.valueOf(input.getText()));
                    addToTable(String.valueOf(input.getText()));
                }else{
                    AlertDialog.Builder alert = new AlertDialog.Builder(ChooseMenu.this);
                    alert.setMessage("Playername is not valid!");
                    alert.show();
                }


            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();

    }

    public void minusClick(View view) {
        removeAllSelected();
    }

    public void addToTable(String name){
        LinearLayout table = findViewById(R.id.table);
        TableRow row = new TableRow(this);
        TextView tw = new TextView(this);
        tw.setText(name);
        tw.setTextSize(20);
        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) v;
                if(textView.isSelected()){
                    textView.setBackgroundResource(R.drawable.rounded_white);
                    v.setSelected(false);
                }else{
                    textView.setBackgroundResource(R.drawable.rounded_yellow);
                    v.setSelected(true);
                }
            }
        });

        tw.setGravity(Gravity.CENTER);
        tw.setPadding(0,10,0,10);
        row.setGravity(Gravity.CENTER);
        row.addView(tw);
        table.addView(row);
    }

    public void removeAllSelected(){
        LinearLayout table = findViewById(R.id.table);
        List<Integer> indexList = new ArrayList<>();
        //Suche nach selektierten TextViews
        for(int i=0;i<table.getChildCount();i++){
            TableRow row = (TableRow) table.getChildAt(i);
            TextView tw = (TextView) row.getChildAt(0);
            if (tw.isSelected()){
                indexList.add(i);
                System.out.println("Index "+i+" ist selektiert!");
            }
        }
        //Lösche von hinten an alle selektierten TextViews und Namen
        for(int j=indexList.size()-1;j>=0;j--){
            TableRow row = (TableRow) table.getChildAt(indexList.get(j));
            TextView tw = (TextView) row.getChildAt(0);
            row.removeView(tw);
            table.removeView(row);
            System.out.println(names.get(indexList.get(j))+" wird gelöscht! Der Index ist "+indexList.get(j));
            String name = names.get(indexList.get(j));
            names.remove(name);
        }
    }

    public void sendInputToGame(Intent intent,String points,String in, String out, ArrayList<String> names){
        //Daten per Intent.putExtra() an Game übergeben
        intent.putExtra("points",points);
        intent.putExtra("in",in);
        intent.putExtra("out",out);
        intent.putStringArrayListExtra("names",names);
    }
}