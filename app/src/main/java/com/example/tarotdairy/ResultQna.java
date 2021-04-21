package com.example.tarotdairy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ResultQna extends AppCompatActivity {
    String title;
    int cardnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_qna);

        SharedPreferences sp = getSharedPreferences("cardsData",MODE_PRIVATE);



        Bundle extras = getIntent().getExtras();

        title = extras.getString("title");
        cardnum = extras.getInt("cardnumber");

        int what = extras.getInt("from78");

        Intent intent = new Intent(this.getIntent());


        TextView cardname = findViewById(R.id.result_cardname);
        TextView questiontitle = findViewById(R.id.result_question);
        ImageView questioncard = findViewById(R.id.result_card);

        TextView yesno = findViewById(R.id.result_yesno);
        TextView key = findViewById(R.id.result_keyword);

        TextView qt = findViewById(R.id.result_question1);

        if(what == 0){
            qt.setVisibility(View.GONE);
            questiontitle.setVisibility(View.GONE);
        }



        String resulttitle = title;
        int resultcard = intent.getIntExtra("cardnumber", 1);


        questiontitle.setText(resulttitle);
        questioncard.setImageResource(resultcard);






        String name = sp.getString(cardnum+"cardname", "재시도");
        cardname.setText(name);

        String yesorno = sp.getString(cardnum+"yesno", "재시도");
        yesno.setText(yesorno);

        String keyword = sp.getString(cardnum+"keyword", "재시도");
        key.setText(keyword);


    }
}