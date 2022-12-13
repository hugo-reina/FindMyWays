package com.example.findmyways;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.audiofx.Visualizer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.Inflater;


public class SelectionPiece extends AppCompatActivity {

        private Intent intentText;
        private Intent intent;
        //private List<Piece> mesPieces;

        private JSONObject res;
        private String a;
        private String meteo;
        private String strPiece;
        private String b;
        private String bat;
        private Bundle bundle;
        private TextView txtNomBat;
        private FileInputStream fosIn;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_selection_piece);

            txtNomBat = findViewById(R.id.txt_nomBat);
            //mesPieces = new ArrayList<>();
            //mesPieces.add(new Piece("test","test","sud","est","ouest",5));

            intentText = getIntent();
            if (intentText != null) {
            strPiece = "";
            b = "";
            bat="";
            if (intentText.hasExtra("piece")) {
                strPiece = intentText.getStringExtra("piece");//nom de ma pièce
            }
            if (intentText.hasExtra("b")) {
                b = intentText.getStringExtra("b");//nom du batiement
            }
            if (intentText.hasExtra("bat")) {
                b = intentText.getStringExtra("bat");//nom du batiement
            }

            txtNomBat.setText(b);

            }

            if (strPiece!=""){
                Piece[] piece = new Piece[]{
                        new Piece(strPiece,strPiece + "_nord", strPiece + "_sud", strPiece + "_est", strPiece+ "_ouest",1)
                };

                try {
                    fosIn = openFileInput("data.json");
                    InputStreamReader inputStreamReader = new InputStreamReader(fosIn);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String nbLigne;
                    while ((nbLigne = bufferedReader.readLine())!=null){
                        stringBuilder.append(nbLigne);
                    }
                    res = new JSONObject(stringBuilder.toString());
                    a = res.getString("name");
                    meteo = res.getString("meteo");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.i("done","erreur");
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                Log.i("test",res.toString());
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_piece);

                MyAdapter adapter = new MyAdapter(this,piece, b);

                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);

            }





        }

    /**
     *
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.piece, menu);
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
            //Si on appuie sur un bouton
            if (item.getItemId() == R.id.btn_addP) {
                Toast.makeText(com.example.findmyways.SelectionPiece.this, "Vous allez créer une nouvelle pièce !", Toast.LENGTH_SHORT).show();
                intent = new Intent(SelectionPiece.this, NouveauBatiment.class);
                bundle = new Bundle();
                bundle.putString("1", "2");
                bundle.putString("b", b);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            if(item.getItemId() == R.id.btn_retour){
                intent = new Intent(SelectionPiece.this, MainActivity.class);
                startActivity(intent);
            }

/**
            if (item.getItemId() == R.id.btn_modif) {
                Toast.makeText(SelectionPiece.this, "Vous allez modifier une pièce !", Toast.LENGTH_SHORT).show();
                intent = new Intent(SelectionPiece.this, SelectionModif.class);
                bundle = new Bundle();
                bundle.putString("piece", String.valueOf(str));
                intent.putExtras(bundle);
                startActivity(intent);
            }
 **/
            return false;

        }



}