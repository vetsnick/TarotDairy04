package com.example.tarotdairy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    TextView login;
    TextView register;
    
    EditText id;
    EditText pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        id = findViewById(R.id.login_id);
        pw = findViewById(R.id.login_pw);

        login = findViewById(R.id.login_login);
        register = findViewById(R.id.login_register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // 아이디 또는 비밀번호가 일치하지 않습니다 사용 예정
                if ( id.getText().toString().length() == 0 ) {
                    Toast.makeText(Login.this, "아이디를 확인해주세요", Toast.LENGTH_SHORT).show();
                    id.requestFocus();
                    return;
                }

                else if ( pw.getText().toString().length() == 0 ) {
                    Toast.makeText(Login.this, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                    pw.requestFocus();
                    return;
                }

                else {
                    Intent intent = new Intent(getApplication(), My.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
        

    }
}