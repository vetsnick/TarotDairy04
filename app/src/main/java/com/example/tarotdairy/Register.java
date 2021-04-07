package com.example.tarotdairy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    TextView register;

    EditText nick;
    EditText id;
    EditText pw1;
    EditText pw2;
    EditText email;

    Button checknick;
    Button checkid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.register_register);

        nick = findViewById(R.id.register_nick);
        id = findViewById(R.id.register_id);
        pw1 = findViewById(R.id.register_pw1);
        pw2 = findViewById(R.id.register_pw2);
        email = findViewById(R.id.register_email);

        checkid = findViewById(R.id.checkid);
        checknick = findViewById(R.id.checknick);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( nick.getText().toString().length() == 0 ) {
                    Toast.makeText(Register.this, "닉네임을 입력하세요", Toast.LENGTH_SHORT).show();
                    nick.requestFocus();
                    return;
                }

                if ( id.getText().toString().length() == 0 ) {
                    Toast.makeText(Register.this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                    id.requestFocus();
                    return;
                }
                if ( pw1.getText().toString().length() == 0 ) {
                    Toast.makeText(Register.this, "비밀번호를 설정해주세요", Toast.LENGTH_SHORT).show();
                    pw1.requestFocus();
                    return;
                }
                if ( pw2.getText().toString().length() == 0 ) {
                    Toast.makeText(Register.this, "비밀번호 확인을 해주세요", Toast.LENGTH_SHORT).show();
                    pw2.requestFocus();
                    return;
                }
                if ( email.getText().toString().length() == 0 ) {
                    Toast.makeText(Register.this, "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                    return;
                }

                if ( !pw1.getText().toString().equals(pw2.getText().toString()) ) {
                    Toast.makeText(Register.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    pw1.setText("");
                    pw2.setText("");
                    pw1.requestFocus();
                    return;
                }

                finish();
            }
        });




        checkid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Register.this, "id 중복 확인", Toast.LENGTH_SHORT).show();
            }
        });

        checknick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Register.this, "nick 중복 확인", Toast.LENGTH_SHORT).show();
            }
        });


    }
}