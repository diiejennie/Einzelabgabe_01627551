package com.diiejennie.se2_einzelabgabe_01627551;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private EditText matrikelnummer;
    private TextView labelErgebnis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matrikelnummer = findViewById(R.id.matrikelnummer);
        labelErgebnis = findViewById(R.id.label_ServerAntwort);
    }

    class ServerAnfrage extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {
            // Brauchen Matrikelnummer
            String matrikelnummerStr = matrikelnummer.getText().toString();
            try {
                // Verbindung herstellen
                Socket socket = new Socket("se2-isys.aau.at",53212);
                DataOutputStream zumServer = new DataOutputStream(socket.getOutputStream());
                BufferedReader vomServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // Matrikelnummer senden
                zumServer.writeBytes(matrikelnummerStr+"\n");
                // Antwort vom Server erhalten
                String antwort = vomServer.readLine();
                // Verbingung schließen
                socket.close();
                return antwort;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void senden(View view){
        ServerAnfrage anfrage = new ServerAnfrage();
        anfrage.execute((Void)null);
        try {
            String antwort = anfrage.get();
            labelErgebnis.setText(antwort);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void berechnen(View view){
        // Aufgabe 2: Sortieren + Primzahlen entfernen
        String ergebnis = "";
        // 1. Matrikelnummer holen
        String matrikelnummerStr = matrikelnummer.getText().toString();
        // 2. Sortieren
        char[] matrikelnummerZiffern = matrikelnummerStr.toCharArray();
        Arrays.sort(matrikelnummerZiffern);
        // 3. Primzahlen streichen
        for (char ziffer:matrikelnummerZiffern) {
            // 3.1 Prüfen ob NICHT prim
            if(!istPrimzahl(ziffer)){
                ergebnis += ziffer;
            }
        }
        // 4. Ergebnis auf label schreiben
        labelErgebnis.setText(ergebnis);
    }

    private boolean istPrimzahl(char ziffer) {
        int zahl = Integer.parseInt(ziffer+"");
        if(zahl==0){
            return false;
        }
        for (int i = 2; i <= Math.sqrt(zahl); i++) {
            if(zahl % i == 0){
                return false;
            }
        }
        return true;
    }


}
