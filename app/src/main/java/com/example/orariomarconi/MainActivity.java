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

        String[] suggerimenti = {};
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