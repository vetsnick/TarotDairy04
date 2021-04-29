package com.example.tarotdairy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Users_Opinion extends AppCompatActivity implements View.OnClickListener {
    

    private ChildEventListener mChildEventListener;
    private ArrayAdapter<Opinion> mAdapter;
    private ListView mListView;

    private EditText mEdtMessage;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    String postid;
    String usernick;
    ImageButton post;
    TextView delete;
    DatabaseReference dataRef, likesrefernce;

    FirebaseDatabase database;

    String cardname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_users__opinion);

        mEdtMessage = findViewById(R.id.opinion_edittext);
        post = findViewById(R.id.opinion_send);

        mListView = (ListView) findViewById(R.id.opinion_list);

        // 전에 받아온 카드 이름 저장
        Intent intent = getIntent();
        postid = intent.getStringExtra("postid");
        usernick = intent.getStringExtra("fromresult");
        cardname = intent.getStringExtra("cardname");

        System.out.println("닉네임 테스트4:" + usernick);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(cardname);

        mListView = (ListView) findViewById(R.id.opinion_list);
        mAdapter = new OpinionAdapter(this, R.layout.item_opinion);
        mListView.setAdapter(mAdapter);
        mEdtMessage = (EditText) findViewById(R.id.opinion_edittext);
        findViewById(R.id.opinion_send).setOnClickListener(this);


        initFirebaseDatabase();


        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int which_item = position;
                
                if(usernick == mAdapter.getItem(position).getOpinionnick()){
                    new AlertDialog.Builder(Users_Opinion.this)
                            .setIcon(android.R.drawable.ic_delete)
                            .setTitle("삭제하시겠습니까?")
                            .setMessage("선택한 댓글을 삭제합니다.")
                            .setNegativeButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String a = mAdapter.getItem(position).getFirebasekey();
                                    //삭제 처리
                                    firebaseDatabase.getReference().child(postid + "opinion").child(a).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(Users_Opinion.this, "삭제 성공", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            System.out.println("error: " + e.getMessage());
                                            Toast.makeText(Users_Opinion.this, "삭제 실패", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            })
                            .setPositiveButton("아니요", null)
                            .show();
                }else{
                    Toast.makeText(Users_Opinion.this, "다른 유저의 글입니다", Toast.LENGTH_SHORT).show();
                }
                return true;

            }

        });


    }

    private void initFirebaseDatabase() {

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Opinion chatData = dataSnapshot.getValue(Opinion.class);
                chatData.firebasekey = dataSnapshot.getKey();
                mAdapter.add(chatData);
                mListView.smoothScrollToPosition(mAdapter.getCount());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String firebaseKey = dataSnapshot.getKey();
                int count = mAdapter.getCount();
                for (int i = 0; i < count; i++) {
                    if (mAdapter.getItem(i).firebasekey.equals(firebaseKey)) {
                        mAdapter.remove(mAdapter.getItem(i));
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        firebaseDatabase.getReference(postid+"opinion").addChildEventListener(mChildEventListener);
    }


    @Override
    public void onClick(View v) {
        System.out.println("닉넴이 대체 머야"+usernick);
        String message = mEdtMessage.getText().toString();
        if (!TextUtils.isEmpty(message)) {
            System.out.println("전송 작동" + usernick + message + System.currentTimeMillis() + " 완료?");
            Opinion chatData = new Opinion(usernick, System.currentTimeMillis(), message);
//            chatData.opinionnick = usernick;
//            chatData.opiniontime = System.currentTimeMillis();
//            chatData.opiniontext = message;
            databaseReference.child(postid+"opinion").push().setValue(chatData);
            mEdtMessage.setText("");
            System.out.println("전송 작동 데이터: " + chatData + " 완료?");
        }
        if (usernick == "") {
            Toast.makeText(Users_Opinion.this, "로그인이 필요합니다", Toast.LENGTH_SHORT).show();
        }
    }



//    @Override
//    protected void onDestory() {
//        super.onDestory();
//        mDatabaseReference.removeEventListener(mChildEventListener);
//    }


}