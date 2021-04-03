package com.example.tarotdairy;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    // 첫 번째 뒤로가기 버튼을 누를때 표시
    private Toast toast;

    ImageView btn1;
    ImageView btn2;
    ImageView btn3;
    ImageView btn4;

    ImageButton gallery;
    ImageButton send;

    ImageView card;
    Random rnd; //랜덤 객체 생성

    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("오늘의 카드");
        setContentView(R.layout.activity_main);


        btn1 = findViewById(R.id.bottom_home);
        btn2 = findViewById(R.id.bottom_diary);
        btn3 = findViewById(R.id.bottom_qna);
        btn4 = findViewById(R.id.bottom_setting);

        gallery = findViewById(R.id.gallery);
        send = findViewById(R.id.send);


        card = findViewById(R.id.todayscard);
//        이미지뷰 클릭 시, 이미지가 랜덤으로 생성
//        Drawable drawable = getResources().getDrawable(R.drawable.galaxy);
//        card.setImageDrawable(drawable);

        rnd = new Random(); //랜덤클래스로부터 랜덤 값 받아오는 변수 작성.

//        이미지뷰에 set 되어있는 데이터가 없으면 랜덤으로 카드가 나오고 저장이 되고
//        있으면 해당  set 되어있는 이미지의 설명 칸으로 이동
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) findViewById(v.getId());
                int num = rnd.nextInt(21);
                Log.v("클릭", num + ""); //버튼을 누르면 로그창에 0부터 21까지 랜덤값이 나타난다 ㅇㅇ 확인
                Toast.makeText(MainActivity.this, num + "번 째 카드가 나옴\n(전에 뽑은 데이터 onCreate 때 덮었으면 else)", Toast.LENGTH_SHORT).show();
//                이제 생성된 랜덤 번호에 따라 이미지 삽입해야됨

//                if( == R.drawable.galaxy){
//
//                }
//                else {
//                    return;
//                }

            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "현재 화면입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HistoryDiary.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HistoryQna.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, My.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "갤러리리리리리", Toast.LENGTH_SHORT).show();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "등로로로로록", Toast.LENGTH_SHORT).show();
            }
        });

//        ratingBar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showCommentWriteActivity();
//            }
//        });




        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


//        datepicker
        TextView btn = (TextView) findViewById(R.id.maindate);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        TextView textView = (TextView) findViewById(R.id.maindate);
        textView.setText(currentDateString);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");

            }
        });

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = (TextView) findViewById(R.id.maindate);
        textView.setText(currentDateString);
    }

    @Override
    public void onBackPressed() {
        // 기존 뒤로가기 버튼의 기능을 막기위해 주석처리 또는 삭제
        // super.onBackPressed();

        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지났으면 Toast Show
        // 2000 milliseconds = 2 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
    }


//    // 자동 호출되는 메서드로,
//    // R.menu.menu_main 을 인플레이션하여 객체로 만든 후 Menu 객체에 설정한다.
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.mainset, menu);
//        return true;
//    }
//
//    // 메뉴 선택 시, onOptionsItemSelected 메소드가 호출된다.
//    // 이 때 MenuItem 객체를 파라미터로 전달받게 되며, 어떤 메뉴를 선택했는지를 id로 구분하여 처리할 수 있다.
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_settings:
//                Toast.makeText(getApplicationContext(), "설정 메뉴", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }





}