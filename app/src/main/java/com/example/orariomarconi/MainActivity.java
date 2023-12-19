package com.example.orariomarconi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);



        Button prof = findViewById(R.id.prof);
        Button classi = findViewById(R.id.classi);
        Button aule = findViewById(R.id.aule);
        AutoCompleteTextView input = findViewById(R.id.input);

        ArrayList<String> cose = new ArrayList<String>();

        InputStream inputStream = this.getResources().openRawResource(R.raw.ore);
        Scanner scn = new Scanner(inputStream).useDelimiter("\\A");

        String[] line;
        while (scn.hasNextLine()) {
            line=scn.nextLine().split(";");
            //prof
            if (!check(cose, cose.size(), line[0])) cose.add(line[0]);
            //classe
            if(!check(cose, cose.size(), line[1])) cose.add(line[1]);
            //aule
            if(!check(cose, cose.size(), line[3])) cose.add(line[3]);
        }




        String[] suggerimenti = new String[cose.size()];

        for (int i = 0; i < cose.size(); i++) {
            suggerimenti[i]=cose.get(i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, suggerimenti);
        input.setAdapter(adapter);


        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                salva("prof", input.getText().toString(), "0");
                Intent intent = new Intent(MainActivity.this, PagTable.class);
                startActivity(intent);
            }
        });

        classi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("jecky2", "cliccato --> classe" + input.getText().toString());
                salva("classi", input.getText().toString(), "1");
                Intent intent = new Intent(MainActivity.this, PagTable.class);
                startActivity(intent);
            }
        });

        aule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salva("aule", input.getText().toString(), "3");
                Intent intent = new Intent(MainActivity.this, PagTable.class);
                startActivity(intent);
            }
        });
    }

    public static boolean check(ArrayList<String> a, int limit,  String val){
        for (int i = 0; i < limit; i++) {
            if (a.get(i).equals(val)) return true;
        }
        return false;
    }


    protected void salva(String uno, String due, String tre){
        SharedPreferences sharedPreferences = getSharedPreferences("cose", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.d("jecky2", "salvato --> " + uno + " " + due + " " + tre);
        editor.putString("1", uno);
        editor.putString("2", due);
        editor.putString("3", tre);
        editor.apply();
    }


}