package com.example.findmyways;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AjouterPiece extends AppCompatActivity  implements LocationListener {


    /**
     * Declaration
     */
    LocationManager locationManager;
    private  String url = "https://api.openWeathermap.org/data/2.5/weather?lat=48.692054&lon=6.184417&appid=989e7d21ce359aaf25ac1720bd42241c";
    /**
     * private  String urlLL = "https://api.openWeathermap.org/data/2.5/weather?";
     * lat=48.692054&lon=6.184417&
     * private  String appid = "appid=989e7d21ce359aaf25ac1720bd42241c";
     */


    private static final int PHOTO =1;
    private Button img;
    private Button ajouter;
    private ImageView image;
    private Intent intentText;
    private Intent intentImg;
    private Intent intent;
    private Bitmap imageBitmap;
    private Bundle extras;
    private double met;
    private int i;
    private String str;
    private Piece piece;
    private Bitmap Nord;
    private Bitmap Sud;
    private Bitmap Est;
    private Bitmap Ouest;
    private Bundle bundle;
    private String b;
    private ExecutorService service;
    private TextView t;
    private TextView p;
    private FileOutputStream fos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_piece);
        /**
         * Début Initialisation
         */
        img = (Button) findViewById(R.id.btn_photo);
        image = (ImageView) findViewById(R.id.img_piece);
        ajouter = (Button) findViewById(R.id.btn_ajouter);
        i=0;
        intentText = getIntent();
        service = Executors.newSingleThreadExecutor();
        t = findViewById(R.id.txt_meteo);
        p = findViewById(R.id.txt_ville);
        /**
         * Fin Initialisation
         */

        if (ContextCompat.checkSelfPermission(AjouterPiece.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AjouterPiece.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }


        /**
         * Récupère le nom de la pièce et l'affiche
         */

        if (intentText != null){
            str = "";
            b = "";
            if (intentText.hasExtra("p")){
                str = intentText.getStringExtra("p");//nom de ma pièce
            }
            if (intentText.hasExtra("b")){
                b = intentText.getStringExtra("b");//nom de ma pièce
            }
            TextView v = (TextView) findViewById(R.id.lib_bat);
            v.setText(str);
        }
        //

        img.setText("Prendre une photo du mur nord");

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentImg = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentImg, PHOTO);
                switch (i){
                    case 0: //prise de la photo nord
                            i++;
                            img.setText("Prendre une photo du mur sud");

                        break;
                    case 1://prise de la photo sud
                            i++;
                            img.setText("Prendre une photo du mur Est");
                        break;
                    case 2://prise de la photo est
                            i++;
                            img.setText("Prendre une photo du mur Ouest");
                        break;
                    case 3://prise de la photo ouest
                        ajouter.setVisibility(View.VISIBLE);
                        ajouter.setEnabled(true);
                        service.execute(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void run() {
                                try {
                                    met  = meteo(t,p);
                                } catch (IOException | JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                }

            }
        });
    ajouter.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //new Piece(str,);
            try {
                MediaStore.Images.Media.insertImage(getContentResolver(), String.valueOf(image),"nom image","description");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Toast.makeText(AjouterPiece.this, "Votre pièce a bien été enregister !", Toast.LENGTH_SHORT).show();
            piece = new Piece(str,Nord,Sud,Est,Ouest,met);
            intent = new Intent(AjouterPiece.this, SelectionPiece.class);
            bundle = new Bundle();
            bundle.putString("piece", String.valueOf(piece.getMeteo()));
            bundle.putString("bat",b);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    });

    }

    /**
     *
     */
                @Override
                protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                    super.onActivityResult(requestCode, resultCode, data);
                    if (requestCode == PHOTO && resultCode == RESULT_OK) {
                        extras = data.getExtras();
                        imageBitmap = (Bitmap) extras.get("data");
                        fos = null;
                        try {
                            fos = openFileOutput("image.data", MODE_PRIVATE);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        image.setImageBitmap(imageBitmap);
                        switch (i){
                            case 1:
                                Nord = ((BitmapDrawable)image.getDrawable()).getBitmap();
                                break;
                            case 2:
                                Sud = ((BitmapDrawable)image.getDrawable()).getBitmap();
                                break;
                            case 3:
                                Est = ((BitmapDrawable)image.getDrawable()).getBitmap();
                                break;
                            case 4:
                                Ouest = ((BitmapDrawable)image.getDrawable()).getBitmap();
                                break;
                        }
                        try {
                            fos.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private double meteo(TextView t, TextView p) throws IOException, JSONException {


        InputStream in = new java.net.URL(url).openStream();
        JSONObject res = readStream(in);
        String a = res.getString("main");
        String b = getNbr(a);
        String [] c = b.split("\\s+");
        int d = Integer.parseInt(c[0]);
        double e = d+ (Integer.parseInt(c[1])/100.0);
        double temp = e - 273.15;
        p.setText(res.getString("name"));
        String des = res.getString("weather");

        t.setText("Température : " + Math.round(temp*100.0)/100.0  + " Humidité : " + c[9]);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i("done","done");
            }
        });
        return Math.round(temp*100.0)/100.0;
    }

    static String getNbr(String str)
    {
        // Remplacer chaque nombre non numérique par un espace
        str = str.replaceAll("[^\\d]", " ");
        // Supprimer les espaces de début et de fin
        str = str.trim();
        // Remplacez les espaces consécutifs par un seul espace
        str = str.replaceAll(" +", " ");

        return str;
    }

    private JSONObject readStream(InputStream in) throws IOException, JSONException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        return new JSONObject(sb.toString());
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this, " La Longitude est : "+location.getLongitude() + " et la Lattitue est : " + location.getLatitude(), Toast.LENGTH_SHORT).show();
    }
}