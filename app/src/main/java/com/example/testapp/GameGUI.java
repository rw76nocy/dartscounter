package com.example.testapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GameGUI extends AppCompatActivity {

    private Game game;
    private List<Player> players = new ArrayList<>();
    private String in = "Single";
    private String out = "Single";
    private int points = 501;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamegui);

        Intent intent = getIntent();
        String points = intent.getStringExtra("points");
        String in = intent.getStringExtra("in");
        String out = intent.getStringExtra("out");
        ArrayList<String> names = intent.getStringArrayListExtra("names");

        //Test-Output
        System.out.println("Übergebene Punktzahl: "+points);
        System.out.println("Übergebenes In: "+in);
        System.out.println("Übergebenes Out: "+out);

        for(String name:names){
            System.out.println("Übergebener Spieler: "+name);
        }

        //Intent-Werte dem Spiel zuweisen
        this.points = Integer.parseInt(points);
        this.in = in;
        this.out = out;
        for(String name:names){
            Player player = new Player(name,this.points);
            players.add(player);
        }

        createNewGame();
        System.out.println("Ich bin ein Test-String für den Development-Branch");
    }

    //Fülle GUI mit den ersten beiden Spielern
    public void createNewGame(){
        game = new Game(players,points,in,out);
        game.setActive(players.get(0));




        TextView name1 = findViewById(R.id.name1);
        name1.setText(game.getPlayers().get(0).getName());

        TextView points1 = findViewById(R.id.points1);
        points1.setText(""+game.getPlayers().get(0).getPoints());

        int next = findNextPlayer(game.getActive());

        TextView name2 = findViewById(R.id.name2);
        name2.setText(game.getPlayers().get(next).getName());

        TextView points2 = findViewById(R.id.points2);
        points2.setText(""+game.getPlayers().get(next).getPoints());


    }

    //Suche den Index des nächsten Spielers
    public int findNextPlayer(Player active){
        int index = game.getPlayers().indexOf(active);
        if(index+1==game.getPlayers().size()){
            index = 0;
        }else{
            index++;
        }
        return index;
    }

    //Suche den Index des vorherigen Spielers
    public int findLastPlayer(Player active){
        int index = game.getPlayers().indexOf(active);
        if(index-1<0){
            index = game.getPlayers().size()-1;
        }else{
            index--;
        }
        return index;
    }

    //Zeige die Namen für den aktiven und den nächsten Spieler an
    public void showPlayers(Player active,Player next){
        TextView name1 = findViewById(R.id.name1);
        name1.setText(active.getName());
        TextView name2 = findViewById(R.id.name2);
        name2.setText(next.getName());
    }

    //Zeige die Punkte für den aktiven und den nächsten Spieler an
    public void showPoints(){
        Player active = game.getActive();
        Player next = game.getPlayers().get(findNextPlayer(active));

        TextView points1 = findViewById(R.id.points1);
        points1.setText(""+active.getPoints());
        TextView points2 = findViewById(R.id.points2);
        points2.setText(""+next.getPoints());
    }

    //Gib den aktuellen Average des Spielers aus
    public void showAverage(){

    }

    //Gib die aktuellen Punkte aller Spieler aus
    public void printPlayersPoints(){
        System.out.println("\n");
        for(Player player:game.getPlayers()){
            System.out.println(player.getName()+" : "+player.getPoints());
        }
        System.out.println("\n");
    }

    //Gib alle History-Einträge des Spielers aus
    public void showHistory(Player player){
        List<Integer> history = player.getHistory();
        System.out.println("\n");
        System.out.println("History von Spieler "+player.getName());
        for(Integer entry: history){
            System.out.println(String.valueOf(entry));
        }
        System.out.println("\n");
    }

    //Setze den Gesamtzähler auf 0
    public void clearPoints(){
        TextView tw = (TextView)findViewById(R.id.points);
        tw.setText("0");
    }

    //Entferne alle Wurf-Punkte aus dem GUI
    public void clearDartsPoints(){
        TextView dart1 = (TextView)findViewById(R.id.dart1);
        TextView dart2 = (TextView)findViewById(R.id.dart2);
        TextView dart3 = (TextView)findViewById(R.id.dart3);
        dart1.setText("_");
        dart2.setText("_");
        dart3.setText("_");
    }

    //Wähle das nächste freie Feld aus
    public TextView chooseNextField(){
        TextView dart1 = (TextView)findViewById(R.id.dart1);
        TextView dart2 = (TextView)findViewById(R.id.dart2);
        TextView dart3 = (TextView)findViewById(R.id.dart3);

        if (dart1.getText().equals("_")){
            return dart1;
        }else if (dart2.getText().equals("_")){
            return dart2;
        }else if (dart3.getText().equals("_")){
            return dart3;
        }else return null;
    }

    //Schreibe Wurf-Punkte in das nächste freie Feld
    public void writeDartsPoints(int points){
        if(chooseNextField()!=null){
            chooseNextField().setText(""+points);
        }
    }

    //Wähle das letzte freie Feld aus
    public TextView chooseLastField(){
        TextView dart1 = (TextView)findViewById(R.id.dart1);
        TextView dart2 = (TextView)findViewById(R.id.dart2);
        TextView dart3 = (TextView)findViewById(R.id.dart3);

        if (!dart3.getText().equals("_")){
            return dart3;
        }else if (!dart2.getText().equals("_")){
            return dart2;
        }else if (!dart1.getText().equals("_")){
            return dart1;
        }else return null;
    }

    //Lösche letzte Punktzahl vom GUI
    public void deleteLastDartPoint(){
        if (chooseLastField()!=null){
            chooseLastField().setText("_");
        }
    }

    public void countPoints(Player player, int points){
        int newPoints = player.getPoints()-points;

        switch (out){
            case "Single":{
                //Neue Punktzahl ist größer als 0
                if (newPoints>0){
                    normalThrow(player,points);
                }

                //Neue Punktzahl ist genau 0
                if(newPoints==0){
                    winThrow(player,points);
                }

                //Neue Punktzahl ist kleiner als 0
                if (newPoints<0){
                    overThrow(player,points);
                }
                break;
            }
            case  "Double":{
                //Neue Punktzahl ist größer als 1
                if (newPoints>1){
                    normalThrow(player,points);
                }

                //Neue Punktzahl ist genau 0 und Double Button ist selektiert
                if(newPoints==0 && game.getMultiplier()==2){
                    winThrow(player,points);
                }else if(newPoints==0 && game.getMultiplier()!=2){
                    overThrow(player,points);
                }

                //Neue Punktzahl ist ungleich 0 und kleiner als 2
                if (newPoints!=0 && newPoints<2){
                    overThrow(player,points);
                }
                break;
            }
            case "Master":{
                //Neue Punktzahl ist größer als 1
                if (newPoints>1){
                    normalThrow(player,points);
                }

                //Neue Punktzahl ist genau 0 und (Double oder Triple Button) ist selektiert
                if(newPoints==0 && (game.getMultiplier()==2||game.getMultiplier()==3)){
                    winThrow(player,points);
                }else if(newPoints==0 && game.getMultiplier()==1){
                    overThrow(player,points);
                }

                //Neue Punktzahl ist ungleich 0 und kleiner als 2
                if (newPoints!=0 && newPoints<2){
                    overThrow(player,points);
                }
                break;
            }
        }
    }

    //zähle Wurf als normalen Wurf
    public void normalThrow(Player player, int points){
        int newPoints = player.getPoints()-points;
        player.setPoints(newPoints);
        addToHistory(player,points);
        showPoints();
        setSingleButtonOnActive();
    }

    //Zähle Wurf als Gewinner-Wurf
    public void winThrow(Player player, int points){
        int newPoints = player.getPoints()-points;
        player.setPoints(newPoints);
        addToHistory(player,points);
        showPoints();
        setSingleButtonOnActive();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Player "+player.getName()+" win the game!");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent intent = new Intent(GameGUI.this,ChooseMenu.class);
                startActivity(intent);
            }
        });
        alert.show();
    }

    //Zähle Wurf als überworfen
    public void overThrow(Player player, int points){
        int newPoints = player.getPoints()-points;
        player.setPoints(newPoints);
        addToHistory(player,points);
        int current = player.getHistory().size()%3;

        if(current == 0){
            System.out.println("Lösche die letzten 3 Darts!");
            deleteLastFromHistory(player,3);

            for(int j=0;j<3;j++){
                addToHistory(player,0);
            }

            next();
            setSingleButtonOnActive();
        }else{
            System.out.println("Lösche die letzten "+current+" Darts!");
            deleteLastFromHistory(player,current);

            for(int j=0;j<3;j++){
                addToHistory(player,0);
            }

            next();
            setSingleButtonOnActive();
        }
    }

    //Berechne Punkte anhand des Zahlenbuttons und des aktuellen Punkte-Multiplikators
    public int calculatePoints(View view){
        Button b = (Button) view;
        int points = Integer.parseInt(b.getText().toString())*game.getMultiplier();
        return points;
    }

    //Füge die Punkte in die History des Spieler ein
    public void addToHistory(Player active,int points){
        List<Integer> history = active.getHistory();
        System.out.println("Für Spieler "+active.getName()+" wird der Eintrag "+points+" hinzugefügt!");
        history.add(points);
        active.setHistory(history);
    }

    //Entferne die letzten n Einträge der Spieler-History und rechne die Punkte auf
    public void deleteLastFromHistory(Player active, int number){
        System.out.println("Es müssen die letzten "+number+" Darts abgezogen werden!");
        for(int i=0;i<number;i++){
            System.out.println("Lösche bei Spieler "+active.getName()+" den Eintrag "+active.getHistory().get(active.getHistory().size()-1));
            deleteLastPoints(active);
            deleteLastEntry(active);
        }

        showPoints();
        clearDartsPoints();
        clearPoints();
    }

    //Rechne den letzten History-Eintrag auf die Spieler-Punkte drauf
    public void deleteLastPoints(Player active){
        int points = active.getHistory().get(active.getHistory().size()-1);
        System.out.println("Für Spieler "+active.getName()+" wird der Eintrag "+points+" gelöscht!");
        int newPoints = active.getPoints()+points;
        active.setPoints(newPoints);
    }

    //Entferne den letzten Eintrag der Spieler-History
    public void deleteLastEntry(Player active){
        List<Integer> history = active.getHistory();
        history.remove(history.size()-1);
        active.setHistory(history);
    }

    //Entferne die letzte Punktzahl aus dem Gesamtzähler
    public void refreshPoints(Player active){
        TextView tw = (TextView)findViewById(R.id.points);
        int current = Integer.parseInt(tw.getText().toString());
        int last = active.getHistory().get(active.getHistory().size()-1);
        tw.setText(String.valueOf(current-last));
    }

    //Fülle Dart-Points und Gesamtzähler aus Spieler-History
    public void fillFromHistory(Player active){
        List<Integer> history = active.getHistory();
        clearPoints();
        clearDartsPoints();

        TextView point = (TextView)findViewById(R.id.points);
        TextView dart1 = (TextView)findViewById(R.id.dart1);
        TextView dart2 = (TextView)findViewById(R.id.dart2);
        TextView dart3 = (TextView)findViewById(R.id.dart3);

        int points1 = history.get(history.size()-3);
        int points2 = history.get(history.size()-2);
        int points3 = history.get(history.size()-1);

        //Fülle Dart Points aus history
        dart1.setText(String.valueOf(points1));
        dart2.setText(String.valueOf(points2));
        dart3.setText(String.valueOf(points3));

        //Fülle Points aus history
        point.setText(String.valueOf(points1+points2+points3));

    }

    //Mache den letzten Wurf rückgängig, Spielerwechsel wenn nötig
    public void deleteLastDart(Player active){
        int current = active.getHistory().size() % 3;

        if(current!=0) {
            refreshPoints(active);
            deleteLastDartPoint();
            deleteLastPoints(active);
            deleteLastEntry(active);
            showPoints();
        }

        //Wenn keine Einträge für aktiven Spieler vorhanden und Undo wird gedrückt
        if(current==0){
            //wechsel activen Spieler auf letzten Spieler, wenn letzter spieler schon history einträge hat
            int index = findLastPlayer(game.getActive());
            Player last = game.getPlayers().get(index);
            List<Integer> history = last.getHistory();
            if(history.size()>0){
                //Fülle aus History
                fillFromHistory(last);
                //Lösche letzten Wurf
                refreshPoints(last);
                deleteLastDartPoint();
                deleteLastPoints(last);
                deleteLastEntry(last);
                //setze letzten Spieler auf aktiv
                Player next = game.getActive();
                Player activ = last;
                game.setActive(activ);
                //Zeige Spieler und Punkte an
                showPlayers(activ,next);
                showPoints();
            }
        }
    }

    //Setze den Single-Button auf aktiv und deaktiviere Double- und Triple-Button
    public void setSingleButtonOnActive(){
        //Setze Single Button nach jedem Dart auf Active
        Button s = findViewById(R.id.Single);
        Button d = findViewById(R.id.Double);
        Button t = findViewById(R.id.Triple);

        s.setSelected(true);
        s.setBackgroundColor(getResources().getColor(R.color.green));
        game.setMultiplier(1);
        d.setSelected(false);
        d.setBackgroundColor(getResources().getColor(R.color.black));
        t.setSelected(false);
        t.setBackgroundColor(getResources().getColor(R.color.black));
    }

    public void nextClick(View view) {
        int nextIndex = game.getPlayers().indexOf(game.getActive())+1;
        if(nextIndex == game.getPlayers().size()){
            nextIndex = 0;
        }
        game.setActive(game.getPlayers().get(nextIndex));
        Player next = game.getPlayers().get(findNextPlayer(game.getActive()));

        showPlayers(game.getActive(), next);
        clearDartsPoints();
        clearPoints();
        showPoints();
    }

    //Wähle den nächsten Spieler
    public void next(){
        int nextIndex = game.getPlayers().indexOf(game.getActive())+1;
        if(nextIndex == game.getPlayers().size()){
            nextIndex = 0;
        }
        game.setActive(game.getPlayers().get(nextIndex));
        Player next = game.getPlayers().get(findNextPlayer(game.getActive()));

        showPlayers(game.getActive(), next);
        clearDartsPoints();
        clearPoints();
        showPoints();
    }

    //Hilfsfunktion für die Zahlen-Buttons, berechne Punkte, wähle nächsten Spieler nach drittem Dart
    public void numberClick(View view){
        TextView tw = findViewById(R.id.points);
        int old = Integer.parseInt(tw.getText().toString());
        int points = calculatePoints(view);
        if(chooseNextField()!=null){
            tw.setText(String.valueOf(old+points));
            writeDartsPoints(points);
            countPoints(game.getActive(),points);
            if(chooseNextField()==null){
                System.out.println("Alle DartPoints sind belegt! Dies war der dritte Dart!");
                next();
            }
        }
    }

    //ANFANG DER ONCLICK_METHODEN!!!!!!!!!!!!!!!!!!!

    //Mache den letzten Wurf rückgängig und setze den Single-Button auf aktiv
    public void undoClick(View view) {
        //Setze Single Button auf aktiv
        setSingleButtonOnActive();
        //lösche letzten Wurf
        deleteLastDart(game.getActive());
    }

    //Markiere den Single-Button in grün und deaktiviere alle anderen Multiplikator-Buttons
    public void singleClick(View view) {
        Button b = (Button) view;
        if(b.isSelected()){
            //b.setBackgroundColor(getResources().getColor(R.color.black));
            //b.setSelected(false);
        }else{
            b.setBackgroundColor(getResources().getColor(R.color.green));
            b.setSelected(true);
            game.setMultiplier(1);

            Button d = findViewById(R.id.Double);
            Button t = findViewById(R.id.Triple);
            d.setSelected(false);
            d.setBackgroundColor(getResources().getColor(R.color.black));
            t.setSelected(false);
            t.setBackgroundColor(getResources().getColor(R.color.black));
        }
    }

    //Markiere den Double-Button in grün und deaktiviere alle anderen Multiplikator-Buttons
    public void doubleClick(View view) {
        Button b = (Button) view;
        if(b.isSelected()){

        }else{
            b.setBackgroundColor(getResources().getColor(R.color.green));
            b.setSelected(true);
            game.setMultiplier(2);

            Button s = findViewById(R.id.Single);
            Button t = findViewById(R.id.Triple);
            s.setSelected(false);
            s.setBackgroundColor(getResources().getColor(R.color.black));
            t.setSelected(false);
            t.setBackgroundColor(getResources().getColor(R.color.black));
        }
    }

    //Markiere den Triple-Button in grün und deaktiviere alle anderen Multiplikator-Buttons
    public void tripleClick(View view) {
        Button b = (Button) view;
        if(b.isSelected()){

        }else{
            b.setBackgroundColor(getResources().getColor(R.color.green));
            b.setSelected(true);
            game.setMultiplier(3);

            Button d = findViewById(R.id.Double);
            Button s = findViewById(R.id.Single);
            d.setSelected(false);
            d.setBackgroundColor(getResources().getColor(R.color.black));
            s.setSelected(false);
            s.setBackgroundColor(getResources().getColor(R.color.black));
        }
    }


    //ANFANG DER ZAHLEN-BUTTON-METHODEN

    public void zeroClick(View view) {
        numberClick(view);
    }

    public void oneClick(View view) {
        numberClick(view);
    }

    public void twoClick(View view) {
        numberClick(view);
    }

    public void threeClick(View view) {
        numberClick(view);
    }


    public void fourClick(View view) {
        numberClick(view);
    }

    public void fiveClick(View view) {
        numberClick(view);
    }

    public void sixClick(View view) {
        numberClick(view);
    }

    public void sevenClick(View view) {
        numberClick(view);
    }

    public void eightClick(View view) {
        numberClick(view);
    }

    public void nineClick(View view) {
        numberClick(view);
    }

    public void tenClick(View view) {
        numberClick(view);
    }

    public void elevenClick(View view) {
        numberClick(view);
    }

    public void twelveClick(View view) {
        numberClick(view);
    }

    public void thirteenClick(View view) {
        numberClick(view);
    }

    public void fourteenClick(View view) {
        numberClick(view);
    }

    public void fifteenClick(View view) {
        numberClick(view);
    }

    public void sixteenClick(View view) {
        numberClick(view);
    }

    public void seventeenClick(View view) {
        numberClick(view);
    }

    public void eightteenClick(View view) {
        numberClick(view);
    }

    public void nineteenClick(View view) {
        numberClick(view);
    }

    public void twentyClick(View view) {
        numberClick(view);
    }

    public void bullClick(View view) {
        if (game.getMultiplier()!=3){
            TextView tw = findViewById(R.id.points);
            int old = Integer.parseInt(tw.getText().toString());
            int points = 25*game.getMultiplier();
            if(chooseNextField()!=null){
                tw.setText(String.valueOf(old+points));
                writeDartsPoints(points);
                countPoints(game.getActive(),points);
                if(chooseNextField()==null){
                    System.out.println("Alle DartPoints sind belegt! Dies war der dritte Dart!");
                    next();
                }
            }
        }
    }
}