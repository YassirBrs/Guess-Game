package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button ButtonOk;
    private EditText editTextNumber;
    private TextView textViewIndication;
    private ProgressBar progressBarTentative;
    private TextView textViewTentative;
    private TextView textViewScore;
    private ListView listViewHistorique;
    private List<String> historique = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private int secret;
    private int counter;
    private int score;
    private List<String> listHistory = new ArrayList<>();
    private int MaxTentative = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButtonOk = findViewById(R.id.buttonOk);
        editTextNumber = findViewById(R.id.editTextNumber);
        textViewIndication = findViewById(R.id.textViewIndication);
        textViewTentative = findViewById(R.id.textViewTentative);
        textViewScore = findViewById(R.id.textView2);
        progressBarTentative = findViewById(R.id.progressBarTent);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, historique);
        listViewHistorique = findViewById(R.id.listView);
        listViewHistorique.setAdapter(adapter);

        initialisation();


        ButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(editTextNumber.getText().toString());
                historique.add(counter + " =>:" + num);
                adapter.notifyDataSetChanged();
                Log.i("MyInfos", getString(R.string.str_score) +

                        counter + "=>" + num);
                if (num > secret) {
                    textViewIndication.setText(R.string.valeur_sup);
                } else if (num < secret) {
                    textViewIndication.setText(R.string.valeur_inf);
                } else {
                    textViewIndication.setText(R.string.bravo);
                    score += 5;
                    textViewScore.setText(String.valueOf(score));
                    rejouer();
                }
                ++counter;
                textViewTentative.setText(String.valueOf(counter));
                progressBarTentative.setProgress(counter);
                if (counter > MaxTentative) {
                    rejouer();
                }
            }
        });
    }

    private void rejouer() {
//        Log.i("MyLog", "Bravo !!!! \n REJOUER.....");
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.rejouer));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.oui), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initialisation();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.non), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.show();

    }

    private void initialisation() {
        secret = 1 + (int) (Math.random() * 100);
        counter = 1;
        textViewTentative.setText(String.valueOf(counter));
        progressBarTentative.setProgress(counter);
        progressBarTentative.setMax(MaxTentative);
        textViewScore.setText("0");
        editTextNumber.setText("");
        historique.clear();
        adapter.notifyDataSetChanged();

    }
}
