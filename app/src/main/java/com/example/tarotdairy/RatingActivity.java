package com.example.tarotdairy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class RatingActivity extends AppCompatActivity {

    RatingBar ratingBar;
    EditText contentsInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        Button saveButton = (Button)findViewById(R.id.saverate);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMain();
            }
        });

        Intent intent = getIntent();
        processIntent(intent);

    }


    private void processIntent(Intent intent) {
        if(intent != null) {
            float rating = intent.getFloatExtra("rating", 0.0f);
            ratingBar.setRating(rating);
        }
    }

    public void returnToMain(){
        String contents = contentsInput.getText().toString();
        float ratingbarupdate = ratingBar.getRating();

        Intent intent = new Intent();
        intent.putExtra("ratingbarupadate", ratingbarupdate);

        setResult(RESULT_OK, intent);
        finish();
    }


}