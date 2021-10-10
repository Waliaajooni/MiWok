package com.example.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the View that shows the numbers category
        TextView number = (TextView) findViewById(R.id.numbers);
        // Set a click listener on that View
        if (number != null) {
            number.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent numbersIntent = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(numbersIntent);
                }
            });
        }

        TextView color = (TextView) findViewById(R.id.colors);
        // Set a click listener on that View
        if (color != null) {
            color.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent colorsIntent = new Intent(MainActivity.this, ColorsActivity.class);
                    startActivity(colorsIntent);
                }
            });
        }

        TextView fam = (TextView) findViewById(R.id.family);
        // Set a click listener on that View
        if (fam != null) {
            fam.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent famIntent = new Intent(MainActivity.this, FamilyActivity.class);
                    startActivity(famIntent);
                }
            });
        }

        TextView phrase = (TextView) findViewById(R.id.phrases);
        // Set a click listener on that View
        if (phrase != null) {
            phrase.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent phrasesIntent = new Intent(MainActivity.this, PhrasesActivity.class);
                    startActivity(phrasesIntent);
                }
            });
        }
    }

}
