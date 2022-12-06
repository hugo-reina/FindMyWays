package com.example.findmyways;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Photo extends AppCompatActivity {

    /**
     * Déclaration
     */
    private ImageView b;
    FileInputStream fis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

         b = (ImageView) findViewById(R.id.img_photo);

        fis = null;
        /**
         * Permet de prendre une photo
         */
        try {
            fis = openFileInput("image.data");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}