package com.example.tarotdairy;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    private final int PICK_IMAGE = 1111;
    private final int REQUEST_IMAGE_CAPTURE = 111;
    private int STORAGE_PERMISSION_CODE = 1;
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

    RatingBar ratingbar;
    LinearLayout ll;

    EditText comment;


    int[] img = {R.drawable.carda, R.drawable.cardb, R.drawable.cardc, R.drawable.cardd, R.drawable.carde, R.drawable.cardf, R.drawable.cardg, R.drawable.cardh, R.drawable.cardi, R.drawable.cardj, R.drawable.cardk, R.drawable.cardl, R.drawable.cardm, R.drawable.cardn, R.drawable.cardo, R.drawable.cardp, R.drawable.cardq, R.drawable.cardr, R.drawable.cards, R.drawable.cardt, R.drawable.cardu, R.drawable.cardv};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("오늘의 카드");
        setContentView(R.layout.activity_main);


        comment = findViewById(R.id.main_comment);

        btn1 = findViewById(R.id.bottom_home);
        btn2 = findViewById(R.id.bottom_diary);
        btn3 = findViewById(R.id.bottom_qna);
        btn4 = findViewById(R.id.bottom_setting);

        gallery = findViewById(R.id.gallery);
        send = findViewById(R.id.send);

        card = findViewById(R.id.todayscard);

        ratingbar = findViewById(R.id.mainrating);
        ll = findViewById(R.id.mainratingtouch);





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

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                withRatingBar(v);
//                final RatingBar dlgrating = new RatingBar(MainActivity.this);
//
//                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
//                dlg.setView(R.layout.activity_rating);
//
//                dlg.setTitle("별점 입력");
//                dlgrating.setNumStars(5);
//                dlgrating.setStepSize(0.5f);
//                dlg.setView(dlgrating);
//
//
//                dlg.setPositiveButton("저장", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ratingbar.setRating(dlgrating.getRating());
//                    }
//                });
//                dlg.show();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateListDialog();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // 아이디 또는 비밀번호가 일치하지 않습니다 사용 예정
                if ( comment.getText().toString().length() == 0 ) {
                    Toast.makeText(MainActivity.this, "작성된 코멘트가 없습니다.", Toast.LENGTH_SHORT).show();
                    comment.requestFocus();
                    return;
                }
                else {
                    Toast.makeText(MainActivity.this, "등록", Toast.LENGTH_SHORT).show();
                    comment.setText("");
                }
            }
        });


        Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);


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


        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardPicker();
            }
        });

    }


        public void withRatingBar(View v) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            builder.setTitle("");
            View dialogLayout = inflater.inflate(R.layout.activity_rating, null);
            final RatingBar withratingBar = dialogLayout.findViewById(R.id.ratingBar);
            builder.setView(dialogLayout);
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ratingbar.setRating(withratingBar.getRating());
                }
            });
            builder.show();
        }


    //  1. 다이얼로그 생성
    public void CreateListDialog() {
        final List<String> ListItems = new ArrayList<>();
        ListItems.add("카메라");
        ListItems.add("갤러리");

        final String[] items = ListItems.toArray(new String[ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setItems(items, (dialog, pos) -> {
            String selectedText = items[pos];

            if (selectedText.equals("카메라")) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                    fromCamera();

                } else {
                    requestCameraPermission();
                }


            } else if (selectedText.equals("갤러리")) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    fromGallery();

                } else {
                    requestStoragePermission();
                }
            }
        });
        builder.show();
    }


    private void cardPicker() {
        Random ram = new Random();
        int num = ram.nextInt(img.length);
        card.setImageResource(img[num]);
        Toast.makeText(MainActivity.this, num + "번 째 카드가 나옴\n(생성된 파일 & 제한 추가 예정)", Toast.LENGTH_SHORT).show();
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


    // 자동 호출되는 메서드로,
    // R.menu.menu_main 을 인플레이션하여 객체로 만든 후 Menu 객체에 설정한다.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainset, menu);
        return true;
    }

    // 메뉴 선택 시, onOptionsItemSelected 메소드가 호출된다.
    // 이 때 MenuItem 객체를 파라미터로 전달받게 되며, 어떤 메뉴를 선택했는지를 id로 구분하여 처리할 수 있다.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:

                Intent intent = new Intent(MainActivity.this, MySettings.class);
                startActivity(intent);

//                Toast.makeText(getApplicationContext(), "설정 메뉴", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    //카메라에서 이미지 가져오는 작업
    private void fromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    //갤러리에서 이미지 가져오는 작업
    private void fromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(getApplicationContext(), "하단 리스트에 띄울 예정", Toast.LENGTH_SHORT).show();

//        if (requestCode == PICK_IMAGE) {
//            if (resultCode == RESULT_OK) {
//                try {
//                    InputStream in = getContentResolver().openInputStream(data.getData());
//
//                    Bitmap img = BitmapFactory.decodeStream(in);
//                    in.close();
//
//                    circle.setImageBitmap(img);
//                } catch (Exception e) {
//
//                }
//            }
//
//        }
//        if (requestCode == REQUEST_IMAGE_CAPTURE) {
//            try {
//                Bundle extras = data.getExtras();
//                Bitmap imageBitmap = (Bitmap) extras.get("data");
//                ((CircleImageView) findViewById(R.id.my_circle)).setImageBitmap(imageBitmap);
//
//            } catch (Exception e) {
//
//            }
//        } else if (resultCode == RESULT_CANCELED) {
//            Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
//        }
    }


    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("TarotDiary의 다음 작업을 허용하시겠습니까? 기기 사진, 미디어, 파일 액세스")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }


    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("권한 요청")
                    .setMessage("TarotDiary의 다음 작업을 허용하시겠습니까? 사진 및 동영상 촬영")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "권한 부여 완료", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "권한 거절", Toast.LENGTH_SHORT).show();
            }
        }

    }
}