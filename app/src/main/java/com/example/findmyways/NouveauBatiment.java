package com.example.findmyways;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NouveauBatiment extends AppCompatActivity {

    /**
     *
     */
    private Button s;
    private EditText t;
    private Intent intentObjet;
    private String str;
    private TextView txt1;
    private TextView txt2;
    private String nom;
    private ArrayList<Piece> mesPieces;

    /**
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveau_batiment);

        /**
         *
         */
        s = findViewById(R.id.btn_suivant);
        t = findViewById(R.id.txt_batiment);
        txt1 = findViewById(R.id.txt_objet1);
        txt2 = findViewById(R.id.txt_objet2);
        intentObjet = getIntent();
        nom = "";
        /**
         *
         */

        if (intentObjet != null){
            str = "";
            if (intentObjet.hasExtra("1")){
                str = intentObjet.getStringExtra("1");//nom de ma pièce
                switch (str){
                    case "1":
                        txt1.setText("Création du bâtiment");
                        txt2.setText("Nom du bâtiment : ");
                        break;
                    case "2":
                        txt1.setText("Création de la pièce");
                        txt2.setText("Nom de la pièce : ");
                        break;
                }
            }


        }

        /**
         *
         */
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = t.getText().toString();
                if(text.matches("")){
                    Toast.makeText(NouveauBatiment.this, "Veuillez inserer un nom pour votre bâtiment", Toast.LENGTH_SHORT).show();
                }else {
                    switch (str){
                        case "1":
                            nom = "b";
                            ChangementPage(SelectionPiece.class, text, nom);
                            break;
                        case "2":
                            nom = "p";
                            ChangementPage(AjouterPiece.class, text, nom);
                            break;
                    }

                }
            }
        });
    }

    void ChangementPage(Class activity, String text, String nom){

        Batiment batiment = new Batiment(text,mesPieces);
        Intent intent = new Intent(NouveauBatiment.this, activity);
        Bundle bundle = new Bundle();
        bundle.putString(nom,text);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}