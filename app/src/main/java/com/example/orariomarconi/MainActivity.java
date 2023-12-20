package com.example.orariomarconi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.chip.Chip;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    public String[] save = {"prof","0"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        int colore = Color.parseColor("#FF00FF");

        AutoCompleteTextView input = findViewById(R.id.input);
        Chip chipProf = findViewById(R.id.chipProf);
        Chip chipClassi = findViewById(R.id.chipClassi);
        Chip chipAule = findViewById(R.id.chipAule);
        Chip chipConferma = findViewById(R.id.chipConferma);

        InputStream inputStream = this.getResources().openRawResource(R.raw.ore);
        Scanner scn = new Scanner(inputStream).useDelimiter("\\A");

        // lista di p prof, c classi, a aule
        ArrayList<String> p = new ArrayList<String>(), c = new ArrayList<String>(), a = new ArrayList<String>();

        String[] line;
        while (scn.hasNextLine()) {
            line=scn.nextLine().split(";");
            if (!check(p, p.size(), line[0])) p.add(line[0]);
            if(!check(c, c.size(), line[1])) c.add(line[1]);
            if(!check(a, a.size(), line[3])) a.add(line[3]);
        }


        chipProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("jecky2", "cliccato chip prof");
                updateColorChip(chipProf, chipClassi, chipAule, chipConferma, colore);
                input.setAdapter(arrayToArrayAdapter(arrayListToArray(p)));
                save[0]="prof";
                save[1] = "0";
                input.setText("");
            }
        });

        chipClassi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("jecky2", "cliccato chip classi");
                updateColorChip(chipClassi, chipProf, chipAule, chipConferma, colore);
                input.setAdapter(arrayToArrayAdapter(arrayListToArray(c)));
                save[0]="classi";
                save[1] = "1";
                input.setText("");
            }
        });

        chipAule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("jecky2", "cliccato chip aule");
                updateColorChip(chipAule, chipProf, chipClassi, chipConferma, colore);
                input.setAdapter(arrayToArrayAdapter(arrayListToArray(a)));
                save[0]="aule";
                save[1] = "3";
                input.setText("");
            }
        });


        String[] suggerimenti = {};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, suggerimenti);
        input.setAdapter(adapter);

        chipConferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("jecky2", "cliccato --> Chipconferma" + input.getText().toString());
                if (check(p, p.size(), input.getText().toString()) || check(c, c.size(), input.getText().toString()) || check(a, a.size(), input.getText().toString())){
                    chipConferma.setChipBackgroundColor(ColorStateList.valueOf(Color.GREEN));
                    salva(save[0], input.getText().toString(), save[1]);
                    Intent intent = new Intent(MainActivity.this, PagTable.class);
                    startActivity(intent);
                    finish();
                }else chipConferma.setChipBackgroundColor(ColorStateList.valueOf(Color.RED));
            }
        });

    }

    public void updateColorChip(Chip selected, Chip c, Chip c1, Chip conf, int colore){
        selected.setChipBackgroundColor(ColorStateList.valueOf(colore));
        c.setChipBackgroundColor(ColorStateList.valueOf(0));
        c1.setChipBackgroundColor(ColorStateList.valueOf(0));
        conf.setChipBackgroundColor(ColorStateList.valueOf(0));
    }

    public static boolean check(ArrayList<String> a, int limit,  String val){
        for (int i = 0; i < limit; i++) {
            if (a.get(i).equals(val)) return true;
        }
        return false;
    }


    protected void salva(String uno, String due, String tre){
        //prof/classi/aule = tipo
        //uno tipo, due nome di tipo, tre da skippare
        SharedPreferences sharedPreferences = getSharedPreferences("cose", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.d("jecky2", "salvato --> " + uno + " " + due + " " + tre);
        editor.putString("1", uno);
        editor.putString("2", due);
        editor.putString("3", tre);
        editor.apply();
    }


    public String[] arrayListToArray(ArrayList<String> a){
        String[] s = new String[a.size()];
        for (int i = 0; i < a.size(); i++) {
            s[i]=a.get(i);
        }
        return s;
    }

    public ArrayAdapter<String> arrayToArrayAdapter(String[] s){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, s);
        return adapter;
    }

}