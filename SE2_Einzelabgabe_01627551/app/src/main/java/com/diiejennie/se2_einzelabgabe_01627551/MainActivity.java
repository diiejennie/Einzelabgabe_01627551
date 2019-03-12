package com.diiejennie.se2_einzelabgabe_01627551;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

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

    public void senden(View view){
        //  se2-isys.aau.at, Port: 53212
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
