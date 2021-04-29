package com.example.tarotdairy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class HistoryDiary extends AppCompatActivity {

    private static SharedPreferences sharedPreference;

    private long backKeyPressedTime = 0;
    private Toast toast;

    ImageView btn1;
    ImageView btn2;
    ImageView btn3;
    ImageView btn4;

    private ArrayList<Diary> mArrayList;
    private DiaryAdapter mAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("다이어리");
        setContentView(R.layout.activity_diary_history);

        loadData();

        mRecyclerView = (RecyclerView) findViewById(R.id.diary_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mAdapter = new DiaryAdapter(this, mArrayList);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


//        //구분선 넣기
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
//                mLinearLayoutManager.getOrientation());
//        mRecyclerView.addItemDecoration(dividerItemDecoration);

        //아이템 클릭했을 때 이벤트
        mRecyclerView.addOnItemTouchListener(new HistoryDiary.RecyclerTouchListener(getApplicationContext(), mRecyclerView, new HistoryDiary.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Diary dia = mArrayList.get(position);

                Intent intent = new Intent(HistoryDiary.this, ResultDiary.class);

                intent.putExtra("picked_date", dia.getDiarytime());

                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));


        btn1 = findViewById(R.id.bottom_home);
        btn2 = findViewById(R.id.bottom_diary);
        btn3 = findViewById(R.id.bottom_qna);
        btn4 = findViewById(R.id.bottom_setting);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryDiary.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HistoryDiary.this, "현재 화면입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryDiary.this, HistoryQna.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryDiary.this, My.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });





//        FloatingActionButton fab = findViewById(R.id.adddiary);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(HistoryDiary.this, "다이어리 추가", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.diaryqna, menu);
//        MenuItem menuItem = menu.findItem(R.id.menu_search);
//        return super.onCreateOptionsMenu(menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private HistoryDiary.ClickListener clickListener;


        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, HistoryDiary.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }


        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }


    private void loadData() {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("DiaryList", MODE_PRIVATE);

        String json = sharedPreferences.getString("diarys", null);

        System.out.println("제이슨3: " + json); //데이터 null로 뜨는지 확인

        Type type = new TypeToken<ArrayList<Diary>>() {
        }.getType();
        mArrayList = gson.fromJson(json, type);

        System.out.println("정렬 순서 (전)" + mArrayList);



        System.out.println("정렬 순서 (후)" + mArrayList);

        if (mArrayList == null) {
            mArrayList = new ArrayList<>();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        mRecyclerView.removeAllViewsInLayout();
        mRecyclerView.setAdapter(mAdapter);
    }
}