package com.example.tarotdairy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class TarotCards78 extends AppCompatActivity {



    private ArrayList<Tarots> mArrayList;
    private TarotsAdapter mAdapter;

    int[] img = {R.drawable.carda, R.drawable.cardb, R.drawable.cardc, R.drawable.cardd, R.drawable.carde, R.drawable.cardf, R.drawable.cardg, R.drawable.cardh, R.drawable.cardi, R.drawable.cardj, R.drawable.cardk, R.drawable.cardl, R.drawable.cardm, R.drawable.cardn, R.drawable.cardo, R.drawable.cardp, R.drawable.cardq, R.drawable.cardr, R.drawable.cards, R.drawable.cardt, R.drawable.cardu, R.drawable.cardv};
    String[] cardname = {"0: 광대","1: 마술사","2: 고위 여사제","3: 여황제","4: 남황제","5: 교황","6: 연인들","7: 전차","8: 힘","9:은둔자","10: 운명의 수레바퀴","11: 정의","12: 매달린 사람","13: 죽음","14: 절제","15: 악마","16: 타워","17: 별","18: 달","19: 해","20: 심판","21: 세계"};

    




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarot_cards78);


        int[] img = {R.drawable.carda, R.drawable.cardb, R.drawable.cardc, R.drawable.cardd, R.drawable.carde, R.drawable.cardf, R.drawable.cardg, R.drawable.cardh, R.drawable.cardi, R.drawable.cardj, R.drawable.cardk, R.drawable.cardl, R.drawable.cardm, R.drawable.cardn, R.drawable.cardo, R.drawable.cardp, R.drawable.cardq, R.drawable.cardr, R.drawable.cards, R.drawable.cardt, R.drawable.cardu, R.drawable.cardv};


        ////////////////////////////
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.tarotcards_recyclerview);


        int numberOfColumns = 2; //한줄에 2개의 컬럼
        GridLayoutManager mGridLayoutManger = new GridLayoutManager(this, numberOfColumns);
        mRecyclerView.setLayoutManager(mGridLayoutManger);

        mArrayList = new ArrayList<>();

        for (int i = 0; i < 22; i++){
                    mArrayList.add(new Tarots(cardname[i], img[i]));
        }


        mAdapter = new TarotsAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mGridLayoutManger.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        //아이템 클릭했을 때 이벤트
        mRecyclerView.addOnItemTouchListener(new TarotCards78.RecyclerTouchListener(getApplicationContext(), mRecyclerView, new TarotCards78.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Tarots dia = mArrayList.get(position);

                Intent intent = new Intent(TarotCards78.this, ResultQna.class);

                intent.putExtra("from78", 0);
                intent.putExtra("cardnumber", dia.getCardimage());

                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));


        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Tarots tarots = mArrayList.get(position);
//                Toast.makeText(TarotCards78.this, "카드명: "+tarots.getCardname(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        

        
        
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private TarotCards78.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final TarotCards78.ClickListener clickListener) {
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