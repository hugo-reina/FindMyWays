package com.example.findmyways;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SelectionPiece extends AppCompatActivity {

        private Intent intentText;
        private Intent intent;
        private RecyclerView recyclerView; // la vue
        private RecyclerView.Adapter adapter; // l'adaptateur
        private RecyclerView.LayoutManager layoutManager; // le gesdtionnaire de mise en page
        private String strPiece;
        private String p;
        private String b;
        private String bat;
        private Bundle bundle;
        private TextView txtNomBat;
        private TextView test;
        private String nomBat;
        private InputStream fosIn;
        private OutputStream fosOut;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_selection_piece);

            txtNomBat = findViewById(R.id.txt_nomBat);
            test = findViewById(R.id.test_txt);


            intentText = getIntent();
            if (intentText != null) {
            strPiece = "";
            p = "";
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

            ImageView imgtest = findViewById(R.id.img_test);
            test.setText(strPiece);
            txtNomBat.setText(b);
            String name = strPiece;
            Bitmap bitmap;

                try {
                    fosIn = openFileInput(name);
                    bitmap = BitmapFactory.decodeStream(fosIn);
                    imgtest.setImageBitmap(Bitmap.createScaledBitmap(bitmap,100,100,false));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.i("done","erreur");
                }

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