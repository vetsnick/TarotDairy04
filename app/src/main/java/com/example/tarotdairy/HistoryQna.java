package com.example.tarotdairy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class HistoryQna extends AppCompatActivity {


    private static SharedPreferences sharedPreference;
    static final String mFILENAME ="mycontacts";

    private long backKeyPressedTime = 0;
    private Toast toast;

    ImageView btn1;
    ImageView btn2;
    ImageView btn3;
    ImageView btn4;

    FloatingActionButton floatingActionButton;


    private ArrayList<Question> mArrayList;
    private CustomAdapter mAdapter;
    private int count = -1;

    int[] img = {R.drawable.carda, R.drawable.cardb, R.drawable.cardc, R.drawable.cardd, R.drawable.carde, R.drawable.cardf, R.drawable.cardg, R.drawable.cardh, R.drawable.cardi, R.drawable.cardj, R.drawable.cardk, R.drawable.cardl, R.drawable.cardm, R.drawable.cardn, R.drawable.cardo, R.drawable.cardp, R.drawable.cardq, R.drawable.cardr, R.drawable.cards, R.drawable.cardt, R.drawable.cardu, R.drawable.cardv};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("QnA");
        setContentView(R.layout.activity_qna_histroy);

        
        sharedPreference = getSharedPreferences(mFILENAME, MODE_PRIVATE); //쉐프 할당 mycontacts가  저장할 xml파일 이름. MODE_PRIVATE는 현재 사용하는 앱에서만 저장파일을 사용하겠다는 의미
        ////////////////////////////
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.qna_recycler_view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);

        mArrayList = new ArrayList<>();


        //arraylist에 데이터를 추가 후, 역순으로 바꿔준다
        Collections.reverse(mArrayList);
        Log.d("아니", "역순 되나요");

        mAdapter = new CustomAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        //구분선 넣기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);




        //아이템 클릭했을 때 이벤트
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Question ques = mArrayList.get(position);

                Intent intent = new Intent(HistoryQna.this, ResultQna.class);

                intent.putExtra("title", ques.getTitle());
                intent.putExtra("cardnumber", ques.getCardnum());

                System.out.println(ques.getCardnum()+"씹씹씹");

                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));



        ////////////////////////////////////

        sharedPreference = getSharedPreferences(mFILENAME, MODE_PRIVATE);
        int maxID = sharedPreference.getInt("maxID", 0);

//        for(int i = maxID; i==1; i--)
        for(int i = 1; i<=maxID; i++) {

            long time = sharedPreference.getLong("time" + i, 0);

//            System.out.println(time);

            if (time == 0) continue;

            String title = sharedPreference.getString("title" + i, "");

//            System.out.println(title);

            if (title.length() == 0) continue;

            int card = sharedPreference.getInt("card" + i, 0);
//            int cardlength = (int)(Math.log10(card)+1);

//            System.out.println(card);

            if (card == 0) continue;

            Question quedata = new Question(time ,title ,card);

            mArrayList.add(quedata);

        }
        mAdapter.notifyDataSetChanged(); //새로고침




        /////////////////////////////////////


        btn1 = findViewById(R.id.bottom_home);
        btn2 = findViewById(R.id.bottom_diary);
        btn3 = findViewById(R.id.bottom_qna);
        btn4 = findViewById(R.id.bottom_setting);

        floatingActionButton = findViewById(R.id.qna_add);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryQna.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryQna.this, HistoryDiary.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HistoryQna.this, "현재 화면입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryQna.this, My.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(HistoryQna.this);
                android.app.AlertDialog.Builder dlg = new AlertDialog.Builder(HistoryQna.this);
                dlg.setTitle("질문 입력");
                dlg.setView(editText);
                dlg.setPositiveButton("답변 카드 생성", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        if (editText.getText().length() == 0) {
                            Toast.makeText(HistoryQna.this, "질문을 입력해주세요", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
//                            count++;
                            Random ram = new Random();
                            int num = ram.nextInt(img.length);

                            long time = System.currentTimeMillis();
                            String title = editText.getText().toString();
                            int card = img[num];


                            Question que = new Question(time, title, card);

                            mArrayList.add(0, que); //RecyclerView의 첫 줄에 삽입
//                        mArrayList.add(data); // RecyclerView의 마지막 줄에 삽입

//                        mAdapter.notifyDataSetChanged();
                            mAdapter.notifyItemInserted(0);

                            mRecyclerView.scrollToPosition(0);

                            dialog.dismiss();


                            //위에까지는 리사이클러뷰에 단순히 추가하는 과정이다
                            //밑에는 쉐프를 이용한 저장 과정
                            SharedPreferences.Editor editor = sharedPreference.edit();
                            int maxID = sharedPreference.getInt("maxID",0)+1; //maxID라는 게시판전체에 번호를 줘서 객체를 식별 저장버튼을 누를때마다 +1씩 됨
                            editor.putLong("time"+maxID, time);
                            editor.putString("title"+maxID, title);
                            editor.putInt("card"+maxID, card);
                            editor.putInt("maxID", maxID);
                            editor.commit(); //쉐프에 저장
                        }

                    }
                });
                dlg.show();
            }
        });
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.diaryqna, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }




//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_search:
//
//
//
//                break;
//            default:
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private HistoryQna.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final HistoryQna.ClickListener clickListener) {
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
}