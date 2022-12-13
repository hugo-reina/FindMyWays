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
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AjouterPiece extends AppCompatActivity {


    /**
     * Declaration
     */
    private  String url = "https://api.openWeathermap.org/data/2.5/weather?lat=48.692054&lon=6.184417&appid=989e7d21ce359aaf25ac1720bd42241c";

     //private  String urlLL = "https://api.openWeathermap.org/data/2.5/weather?";
     //lat=48.692054&lon=6.184417&
     //private  String appid = "appid=989e7d21ce359aaf25ac1720bd42241c";


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
    private Batiment batiment;
    private int i;
    private String str;
    private ArrayList<Piece> listPiece;
    private Piece piece;
    private String Nord;
    private String Sud;
    private String Est;
    private String Ouest;
    private Bundle bundle;
    private String b;
    private ExecutorService service;
    private TextView t;
    private TextView p;
    private OutputStream fos;
    private OutputStream fosJson;
    private InputStream fosIn;
    private FileOutputStream out;
    private JsonWriter f;
    private File file;
    private String FILE_NAME = "piece.json";
    private Sensor orientation;
    private float x,y,z;

    //




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

        /** TODO ajouter une bousolle pour nord sud est ouest
         *
         */

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
                                try{
                                    met  = meteo(t,p);
                                } catch (JSONException | IOException e) {
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
                MediaStore.Images.Media.insertImage(getContentResolver(), String.valueOf(image),str+"","description");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Toast.makeText(AjouterPiece.this, "Votre pièce a bien été enregister !", Toast.LENGTH_SHORT).show();
            piece = new Piece(str,Nord,Sud,Est,Ouest,met);
            //json.saveData();
            intent = new Intent(AjouterPiece.this, SelectionPiece.class);
            bundle = new Bundle();

            JSONObject obj = new JSONObject();
            try {
                File file = getFileStreamPath("data.json");
                if(file.exists()) {
                    Log.i("exist","oui");
                    fosIn = openFileInput("data.json");
                    InputStreamReader inputStreamReader = new InputStreamReader(fosIn);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String nbLigne;
                    while ((nbLigne = bufferedReader.readLine())!=null){
                        stringBuilder.append(nbLigne);
                    }
                    obj = new JSONObject(stringBuilder.toString());
                }
                    obj.put("name2", str);
                    obj.put("meteo2", met);
                    fosJson = openFileOutput("data.json", MODE_PRIVATE);
                    fosJson.write(obj.toString().getBytes(StandardCharsets.UTF_8));
                    fosJson.flush();
                    fosJson.close();

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }

            bundle.putString("piece", String.valueOf(piece.getNom()));
            bundle.putString("bat",b);
            intent.putExtras(bundle);
            //listPiece.add(piece);
            //batiment.setPieces(listPiece);
            startActivity(intent);
        }
    });

    }

    /**
     *
     */

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.direction, menu);
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean onOptionsItemSelected(MenuItem item) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
            }
        });
        if (item.getItemId() == R.id.btn_direct) {
            intent = new Intent(AjouterPiece.this, getDirection.class);
            startActivity(intent);
        }
        return false;
    }


                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                    super.onActivityResult(requestCode, resultCode, data);
                    if (requestCode == PHOTO && resultCode == RESULT_OK) {
                        extras = data.getExtras();
                        imageBitmap = (Bitmap) extras.get("data");
                        Log.i("done","" + imageBitmap);
                        fos = null;
                        switch (i){
                            case 1:
                                Nord = savePhoto("_nord");
                                break;
                            case 2:
                                Sud = savePhoto("_sud");
                                break;
                            case 3:
                                Est = savePhoto("_est");
                                break;
                            case 4:
                                Ouest = savePhoto("_ouest");
                                break;
                        }
                        try {
                            fos.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

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
        Log.i("done","" + temp);
        //String des = res.getString("weather");

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


    /**
     * Sauvegarde les photos et l'affiche directement, renvoie le nom de la photo
     * @param or
     * @return
     */
    private String savePhoto(String or){
                    String name = str + or;
        try {
            fos = openFileOutput(name, MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("done","erreur");
        }
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        try {
            fosIn = openFileInput(name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageBitmap = BitmapFactory.decodeStream(fosIn);
        image.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap,100,100,false));
        return name;
    }

    /**
     *
     * @param params
     * @param mJsonResponse
     */
    public void mCreateAndSaveFile(String params, String mJsonResponse) {
        try {
            FileWriter file = new FileWriter("/data/data/" + getApplicationContext().getPackageName() + "/" + params);
            file.write(mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}