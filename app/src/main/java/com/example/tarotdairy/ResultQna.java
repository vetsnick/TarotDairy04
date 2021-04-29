package com.example.tarotdairy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import javax.xml.transform.Result;

public class ResultQna extends AppCompatActivity {
    String title;
    int cardnum;
    String nick;
    int what;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_qna);



        Bundle extras = getIntent().getExtras();



        SharedPreferences sp = getSharedPreferences("cardsData",MODE_PRIVATE);


        title = extras.getString("title");
        cardnum = extras.getInt("cardnumber");
        nick = extras.getString("fromtc");
        System.out.println("닉네임 테스트3:"+nick);
        what = extras.getInt("from78");

        Intent intent = new Intent(this.getIntent());

        TextView cardname = findViewById(R.id.result_cardname);
        TextView questiontitle = findViewById(R.id.result_question);
        ImageView questioncard = findViewById(R.id.result_card);

        TextView yesno = findViewById(R.id.result_yesno);
        TextView key = findViewById(R.id.result_keyword);

        TextView qt = findViewById(R.id.result_question1);

        Button button1 = findViewById(R.id.result_userscomments);
        
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SaveSharedPreference.getLoggedStatus(getApplicationContext())){
                    //인텐트 댓글 화면
                    Intent intent = new Intent(ResultQna.this, Users_Opinion.class);
                    intent.putExtra("postid", cardnum+"");
                    intent.putExtra("fromresult", nick);
                    intent.putExtra("cardname", cardname.getText());

                    startActivity(intent);
                }

                else {
                    Toast.makeText(ResultQna.this, "로그인이 필요한 기능입니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
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