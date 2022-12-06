package com.example.findmyways;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    /**
     * Declaration
     */
    private Intent intentText;
    private Intent intent;
    private RecyclerView recyclerView; // la vue
    private RecyclerView.Adapter adapter; // l'adaptateur
    private RecyclerView.LayoutManager layoutManager; // le gesdtionnaire de mise en page
    private String str;
    private Bundle bundle;
    /**
     * Fin declaration
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Début Initialisation
         */
        recyclerView = (RecyclerView)findViewById(R.id.list_batiment);
        recyclerView.setHasFixedSize(true);
        /**
         * Fin Initialisation
         */

        intentText = getIntent();
        if (intentText != null){
            str = "";
            if (intentText.hasExtra("piece")){
                str = intentText.getStringExtra("piece");//nom de ma pièce
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
        inflater.inflate(R.menu.main, menu);
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
        if (item.getItemId() == R.id.btn_addB) {
            Toast.makeText(MainActivity.this, "Vous allez créer un nouveau batiment !", Toast.LENGTH_SHORT).show();
            intent = new Intent(MainActivity.this, NouveauBatiment.class);
            bundle = new Bundle();
            bundle.putString("1", "1");
            intent.putExtras(bundle);
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