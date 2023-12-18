package com.example.orariomarconi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class PagTable extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);

        Map<String, Map<String, String[][]>> ore = new HashMap<String, Map<String, String[][]>>();

        ArrayList<String[]> line = new ArrayList<String[]>();

        InputStream inputStream = this.getResources().openRawResource(R.raw.ore);
        Scanner scn = new Scanner(inputStream).useDelimiter("\\A");

        while (scn.hasNextLine()) {
            line.add(scn.nextLine().split(";"));
        }

        ore.put("prof", readProf(line));
        ore.put("classi", readClassi(line));
        ore.put("aule", readAule(line));


        String[] filtro = leggi();


        /*for (int j = 0; j < ore.get(filtro[0]).get(filtro[1]).length; j++) {
            String st = " " + ore.get(filtro[0]).get(filtro[1])[j][0]  + " " + ore.get(filtro[0]).get(filtro[1])[j][1] + " " + ore.get(filtro[0]).get(filtro[1])[j][2] + " " + ore.get(filtro[0]).get(filtro[1])[j][3];
            Log.d("jecky2", " --> " + st);
        }*/


        String[][] a = {{"SAGLIA SARA","3BI","LIT","A320","1","7"}};
        Log.d("jecky", " filtri:" + filtro[0] + " " + filtro[1] + " " + filtro[2]);
        String[][] array = ore.get(filtro[0]).get(filtro[1]);

        if (array==null) {
            Log.d("jecky", "array nullo");
            visualizza(a, -1);
        }
        else
            visualizza(ore.get(filtro[0]).get(filtro[1]), Integer.parseInt(filtro[2]));
    }

    //int textViewId = getResources().getIdentifier(idText, "id", getPackageName());

    // Trova il riferimento al TextView
    //tuoTextView = findViewById(textViewId);

    public void visualizza(String[][] a, int not){

        int[] passati = new int[a.length];
        String[] valore = new String[a.length];

        for (int i = 0; i < valore.length; i++) {
            valore[i]="";
        }

        for (int i = 0; i < a.length; i++) {
            Log.d("jecky", "fatto"+i);
            for (int j = 0; j < a[i].length-2; j++) {
                if (not==j) continue;
                valore[i]+=a[i][j]+"\n";
            }
            //t4_8"
            //0    ;1     ;2      ;3   ;4     ;5
            //prof;classe;materia;aula;giorno;ora
            String id = "t"+a[i][4]+"_"+a[i][5];
            Log.d("jecky", "id"+id);
            int cella = getResources().getIdentifier(id, "id", getPackageName());
            TextView textView = findViewById(cella);

            textView.setText(valore[i]);
        }


    }



    protected String[] leggi(){
        String[] s = new String[3];
        SharedPreferences filtro = getSharedPreferences("cose", Context.MODE_PRIVATE);
        s[0] = filtro.getString("1", "prof");
        s[1] = filtro.getString("2", "DE CARLI LORENZO");
        s[2] = filtro.getString("3", "0");
        return s;
    }

    public static Map<String, String[][]> readProf(ArrayList<String[]> line){
        Map<String, String[][]> prof = new HashMap<String, String[][]>();

        int c=0;
        int i=0;
        while (i+1<line.size()){
            int j=i;
            ArrayList<String[]> tempProf = new ArrayList<String[]>();
            while ( j+1<line.size() && line.get(i)[0].equals(line.get(j)[0]) ){
                String[] temp = new String[6];
                for (int k = 0; k <6; k++) {
                    temp[k]=line.get(j)[k];
                }
                tempProf.add(temp);
                j++;

            }
            //System.out.printf("j --> %d\n", j);
            prof.put(line.get(i)[0], tempProf.toArray(new String[tempProf.size()][6]));
            c++;
            i=j;
        }
        return  prof;
    }

    public static Map<String, String[][]> readAule(ArrayList<String[]> line) {

        Map<String, String[][]> aule = new HashMap<String, String[][]>();
        String[] passati = new String[1000];

        //0    ;1     ;2      ;3   ;4     ;5
        //prof;classe;materia;aula;giorno;ora
        int c=0;
        int i=0;
        while (i<line.size()){
            //System.out.println(i);
            String current = line.get(i)[3];
            if (check(passati, c, current)) {
                i++;
                continue;
            }
            passati[c]=current;
            c++;
            ArrayList<String[]> cl = new ArrayList<String[]>();
            int j=i;
            while(j<line.size()){
                if (current.equals(line.get(j)[3])){
                    String[] temp = new String[6];
                    for (int k = 0; k <temp.length; k++) {
                        temp[k]=line.get(j)[k];
                    }
                    cl.add(temp);
                }
                j++;
            }
            aule.put(current, cl.toArray(new String[cl.size()][6]));
            i++;
        }
        return aule;
    }


    public static Map<String, String[][]> readClassi(ArrayList<String[]> line) {

        Map<String, String[][]> classi = new HashMap<String, String[][]>();
        String[] passati = new String[1000];

        //0    ;1     ;2      ;3   ;4     ;5
        //prof;classe;materia;aula;giorno;ora
        int c=0;
        int i=0;
        while (i<line.size()){
            //System.out.println(i);
            String current = line.get(i)[1];
            if (check(passati, c, current)) {
                i++;
                continue;
            }
            passati[c]=current;
            c++;
            ArrayList<String[]> cl = new ArrayList<String[]>();
            int j=i;
            while(j<line.size()){
                if (current.equals(line.get(j)[1])){
                    String[] temp = new String[6];
                    for (int k = 0; k <temp.length; k++) {
                        temp[k]=line.get(j)[k];
                    }
                    cl.add(temp);
                }
                j++;
            }
            classi.put(current, cl.toArray(new String[cl.size()][6]));
            i++;
        }
        return classi;
    }

    public static boolean check(String[] a, int limit,  String val){
        for (int i = 0; i < limit; i++) {
            if (a[i].equals(val)) return true;
        }
        return false;
    }

}
