package com.example.tarotdairy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ResultDiary extends AppCompatActivity {

    String date, text;
    int cardnum;
    float rate;

    ImageView cardimg;
    TextView cardname, cardkey;
    RatingBar ratingBar;
    EditText editText, comment;
    ImageButton send;

    private ArrayList<Comment> mArrayList;
    private CommentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_diary);

        //날짜 가져옴
        Bundle extras = getIntent().getExtras();
        date = extras.getString("picked_date");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(date);
        actionBar.setDisplayHomeAsUpEnabled(true);

        cardimg = findViewById(R.id.diary_result_cardimg);
        cardname = findViewById(R.id.diary_result_cardname);
        cardkey = findViewById(R.id.diary_result_keyword);
        ratingBar = findViewById(R.id.diary_result_ratingbar);
        editText = findViewById(R.id.diary_result_edittext);
        comment = findViewById(R.id.diary_result_commet);
        send = findViewById(R.id.diary_result_send);

        SharedPreferences sf = getSharedPreferences("display",MODE_PRIVATE);
        cardnum = sf.getInt(date+"todaycard", 0);
        rate = sf.getFloat(date+"todayrate", 0);
        text = sf.getString(date+"review", "");

        cardimg.setImageResource(cardnum);
        ratingBar.setRating(rate);
        editText.setText(text);


        SharedPreferences sp = getSharedPreferences("cardsData",MODE_PRIVATE);
        String name = sp.getString(cardnum+"cardname", "재시도");
        cardname.setText(name);

        String keyword = sp.getString(cardnum+"keyword", "재시도");
        cardkey.setText(keyword);


        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);//키보드 다음 버튼을 완료 버튼으로 바꿔줌

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    //키보드에 완료버튼을 누른 후 수행할 것 = review(한줄평) 저장
                    SharedPreferences sf = getSharedPreferences("display",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sf.edit();

                    String reviewline = editText.getText().toString();
                    editor.putString(date+"review", reviewline);

                    editor.commit();

                    //키보드 숨기기
                    InputMethodManager imm= (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    
                    return true;
                }
                return false;
            }
        });
//===============================================================================================
        loadcomment();

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.diary_result_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new CommentAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
//===============================================================================================


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comment.getText().toString().length() == 0) {
                    Toast.makeText(ResultDiary.this, "작성된 코멘트가 없습니다.", Toast.LENGTH_SHORT).show();
                    comment.requestFocus();
                    return;
                } else {
                    String com = comment.getText().toString();
                    Comment comment = new Comment(com);
                    mArrayList.add(0, comment); //RecyclerView의 첫 줄에 삽입
                    mAdapter.notifyItemInserted(0);
                    mRecyclerView.scrollToPosition(0);
                    SharedPreferences sp = getSharedPreferences("display", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    Gson gson = new Gson();
                    String json1 = gson.toJson(mArrayList);
                    editor.putString(date+"key", json1);
                    editor.apply();
                }
                comment.setText("");
                mRecyclerView.scrollToPosition(0);
            }
        });

    }



    private void loadcomment() {
        SharedPreferences sp = getSharedPreferences("display", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString(date+"key", null);
        Type type = new TypeToken<ArrayList<Comment>>() {}.getType();
        mArrayList = gson.fromJson(json, type);

        if (mArrayList == null) {
            mArrayList = new ArrayList<>();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}