package com.example.tarotdairy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    //파이어베이스 인증 객체 생성
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    TextView register;
    ProgressDialog mDialog;
    EditText nick;
    EditText id;
    EditText pw1;
    EditText pw2;
    EditText email;


    String userID;
//    Button checknick;
//    Button checkid;

    View rootView;
    boolean isKeyboardShowing = false;
    //현재 키보드가 보이는상태인지 아닌지를 담는 불린 / 트루 -> 키보드가 보이는상태
    int keyboardHeight;
//현재 디바이스의 키보드 높이


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //파이어베이스 접근 설정
        // user = firebaseAuth.getCurrentUser();
        mAuth =  FirebaseAuth.getInstance();
        //firebaseDatabase = FirebaseDatabase.getInstance().getReference();

         mDialog = new ProgressDialog(Register.this);


        register = findViewById(R.id.register_register);

        nick = findViewById(R.id.register_nick);
        id = findViewById(R.id.register_id);
        pw1 = findViewById(R.id.register_pw1);
        pw2 = findViewById(R.id.register_pw2);
//        email = findViewById(R.id.register_email);

//        checkid = findViewById(R.id.checkid);
//        checknick = findViewById(R.id.checknick);

        fStore = FirebaseFirestore.getInstance();



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if ( nick.getText().toString().trim().length() == 0 ) {
                    Toast.makeText(Register.this, "닉네임을 입력하세요", Toast.LENGTH_SHORT).show();
                    nick.requestFocus();
                    return;
                }

                if ( nick.getText().toString().length() < 2 || nick.getText().toString().length() > 12) {
                    Toast.makeText(Register.this, "닉네임은 최소 2글자, 최대 12글자입니다.", Toast.LENGTH_SHORT).show();
                    nick.requestFocus();
                    return;
                }

                if ( id.getText().toString().length() == 0 ) {
                    Toast.makeText(Register.this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                    id.requestFocus();
                    return;
                }

                if ( id.getText().toString().length() < 6 ||  id.getText().toString().length() > 20) {
                    Toast.makeText(Register.this, "아이디는 최소 6글자, 최대 20글자입니다.", Toast.LENGTH_SHORT).show();
                    id.requestFocus();
                    return;
                }


                if ( pw1.getText().toString().length() == 0 ) {
                    Toast.makeText(Register.this, "비밀번호를 설정해주세요", Toast.LENGTH_SHORT).show();
                    pw1.requestFocus();
                    return;
                }

                if ( pw1.getText().toString().length() < 6 || pw1.getText().toString().length() > 20 ) {
                    Toast.makeText(Register.this, "비밀번호는 최소 6글자, 최대 20글자입니다.", Toast.LENGTH_SHORT).show();
                    pw1.requestFocus();
                    return;
                }
                
                if ( pw2.getText().toString().length() == 0 ) {
                    Toast.makeText(Register.this, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                    pw2.requestFocus();
                    return;
                }
//                if ( email.getText().toString().length() == 0 ) {
//                    Toast.makeText(Register.this, "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
//                    email.requestFocus();
//                    return;
//                }

                if ( !pw1.getText().toString().equals(pw2.getText().toString()) ) {
                    Toast.makeText(Register.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    pw2.requestFocus();
                    return;
                }


                else {
                    mDialog.setMessage("신규 가입 중...");
                    mDialog.show();
                    //가입 정보 가져오기
//                    String firenick = nick.getText().toString().trim();
//                    String fireid = id.getText().toString().trim();
//                    String firepw = pw1.getText().toString().trim();
                    createUser(nick.getText().toString(), id.getText().toString(), pw1.getText().toString());

                }

//                finish();
            }
        });




//        checkid.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(Register.this, "id 중복 확인", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        checknick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(Register.this, "nick 중복 확인", Toast.LENGTH_SHORT).show();
//            }
//        });


        //      onCreate
        rootView = this.getWindow().getDecorView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
//                키보드의 show & noShow(동작순간)에 뷰의 크기가 변하게된다.
//                이를 이용해서 키보드의 높이를 구한다.
                getKeyboardHeight(rootView);
            }
        });

    }

    private void createUser(String firenick, String fireid, String firepw) {
        mAuth.createUserWithEmailAndPassword(fireid, firepw).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User(firenick);

                    FirebaseDatabase.getInstance().getReference("users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                userID = mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("users").document(userID);
                                Map<String,Object> user = new HashMap<>();
                                user.put("usernick",firenick);
                                user.put("userid", fireid);
                                user.put("userimg", null);

                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG", "onSuccess : user Profile is created for "+ userID);
                                    }
                                });

                                mDialog.dismiss();
                                // 회원가입 성공시
                                Toast.makeText(Register.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                mDialog.dismiss();
                                // 계정이 중복된 경우
                                Toast.makeText(Register.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });





                } else {
                    mDialog.dismiss();
                    // 계정이 중복된 경우
                    Toast.makeText(Register.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void getKeyboardHeight(View targetView){
//      키보드의 크기를 구하는 함수
//      구하는 원리는 엑티비티 혹은 프래그먼트 전체값을 구한다 screenHeight
//      현재 뷰의 입력정보가 들어간 rect객체의 하단값(바닥면)의 값을 구함 rectangle.bottom
//      rectangle.bottom 값의 화면상 위치는 디바이스의 맨 하단 혹은 키보드의 바로 위 두가지의 경우가 있다.
//      전체높이에서 현재 뷰의 높이를 빼서 남은 값으로 키보드의 값을 유추한다.

        Rect rectangle = new Rect();
        targetView.getWindowVisibleDisplayFrame(rectangle);
        int screenHeight = targetView.getRootView().getHeight();
        int tempKeyboardSize  = screenHeight - rectangle.bottom;

        if (tempKeyboardSize > screenHeight * 0.1) {
//            대부분의 키보드 높이가 전체의 10프로이상차지해서 0.1로 정함
            keyboardHeight  = screenHeight - rectangle.bottom;
            if (!isKeyboardShowing) {
//                키보드가 보여지는 경우
                isKeyboardShowing = true;
            }
        }
        else {
            // 키보드가 안보이는 경우
            if (isKeyboardShowing) {
                isKeyboardShowing = false;
            }
        }

    }







}