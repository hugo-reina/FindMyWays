package com.example.findmyways;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class visualiser extends AppCompatActivity {

    private Intent intentText;
    private String strPiece;
    private Button btnNext;
    private Button btnBack;
    private ImageView img;
    private FileInputStream fosIn;
    private String name;
    private TextView txt;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiser);
        btnBack = findViewById(R.id.btn_back);
        btnNext = findViewById(R.id.btn_next);
        txt = findViewById(R.id.txt_direction);
        img = findViewById(R.id.image_nseo);
        i=0;
        btnBack.setEnabled(false);


        intentText = getIntent();
        if (intentText != null) {
            strPiece = "";
            if (intentText.hasExtra("nomP")) {
                strPiece = intentText.getStringExtra("nomP");//nom de ma pièce
                name = strPiece + "_nord";
                recupererImg(name);
                txt.setText("Mur Nord");
                btnNext.setEnabled(true);
                btnBack.setEnabled(false);

            }
        }

                btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (i < 4) {
                        i++;
                        changement(i);
                    }

                }
            });

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (i > 0) {
                        i--;
                        changement(i);
                    }
                }
            });





    }

    void recupererImg(String name){
        Bitmap bitmap;

        try {
            fosIn = openFileInput(name);
            bitmap = BitmapFactory.decodeStream(fosIn);
            img.setImageBitmap(Bitmap.createScaledBitmap(bitmap,100,100,false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("done","erreur");
        }
    }

    void changement(int i){
        switch (i) {
            case 0:
                name = strPiece + "_nord";
                recupererImg(name);
                txt.setText("Mur Nord");
                btnNext.setEnabled(true);
                btnBack.setEnabled(false);
                break;
            case 1:
                name = strPiece + "_sud";
                recupererImg(name);
                btnBack.setEnabled(true);
                txt.setText("Mur Sud");
                btnNext.setEnabled(true);
                break;
            case 2:
                name = strPiece + "_est";
                recupererImg(name);
                btnBack.setEnabled(true);
                txt.setText("Mur Est");
                btnNext.setEnabled(true);
                break;
            case 3:
                name = strPiece + "_ouest";
                recupererImg(name);
                btnBack.setEnabled(true);
                txt.setText("Mur Ouest");
                btnNext.setEnabled(false);
                break;

        }
    }
}