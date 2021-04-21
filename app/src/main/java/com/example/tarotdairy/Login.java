package com.example.tarotdairy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    ProgressDialog mDialog;

    TextView login;
    TextView register;
    
    EditText id;
    EditText pw;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        id = findViewById(R.id.login_id);
        pw = findViewById(R.id.login_pw);

        login = findViewById(R.id.login_login);
        register = findViewById(R.id.login_register);

        mDialog = new ProgressDialog(Login.this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.setMessage("로그인...");
                mDialog.show();

                if (!id.getText().toString().equals("") && !pw.getText().toString().equals("")) {
                    loginUser(id.getText().toString(), pw.getText().toString());

                } else {
                    mDialog.dismiss();
                    Toast.makeText(Login.this, "이메일과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

        mListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(getApplication(), My.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    mDialog.dismiss();
                }
            }
        };

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
        

    }

    public void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            
                            //로그인 상태를 트루로 만들어버림
                            SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                            
                            
                            mDialog.dismiss();
                            // 로그인 성공
                            Toast.makeText(Login.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            mAuth.addAuthStateListener(mListener);
                        } else {
                            mDialog.dismiss();
                            // 로그인 실패
                            Toast.makeText(Login.this, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mListener != null) {
            mAuth.removeAuthStateListener(mListener);
        }
    }
}