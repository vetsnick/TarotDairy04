package com.example.tarotdairy;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    TextView btn;

    TextView keyword;

    RatingBar ratingbar;
    LinearLayout ll;

    EditText comment;


    private ArrayList<Comment> mArrayList;
    private CommentAdapter mAdapter;
    private int count = -1;

    int[] img = {R.drawable.carda, R.drawable.cardb, R.drawable.cardc, R.drawable.cardd, R.drawable.carde, R.drawable.cardf, R.drawable.cardg, R.drawable.cardh, R.drawable.cardi, R.drawable.cardj, R.drawable.cardk, R.drawable.cardl, R.drawable.cardm, R.drawable.cardn, R.drawable.cardo, R.drawable.cardp, R.drawable.cardq, R.drawable.cardr, R.drawable.cards, R.drawable.cardt, R.drawable.cardu, R.drawable.cardv};
    String[] cardname = {"0: 광대", "1: 마술사", "2: 고위 여사제", "3: 여황제", "4: 남황제", "5: 교황", "6: 연인들", "7: 전차", "8: 힘", "9:은둔자", "10: 운명의 수레바퀴", "11: 정의", "12: 매달린 사람", "13: 죽음", "14: 절제", "15: 악마", "16: 타워", "17: 별", "18: 달", "19: 해", "20: 심판", "21: 세계"};
    String[] hintkeyword = {"모험, 시작, 무지, 자유, 순수", "창조, 영감, 능력", "지식, 총명, 순결", "풍요, 모성", "권위, 부성, 책임", "교육, 종교, 전통, 관대", "사랑, 조화, 관계", "전진, 성공, 의지", "힘, 용기, 설득", "탐색, 차분한, 자기성찰", "행운, 업보, 전환점", "균형, 정당, 진실", "자기희생, 인내, 새로운 관점", "이별, 새로운 시작", "균형, 절제, 조화", "사심, 타락, 성욕", "파괴, 급격한 변화", "희망, 믿음, 갱신", "불안, 환상, 직관", "긍정, 성공, 활력", "부활, 판단, 용서", "완성, 통합, 성취"};
    String[] yesno = {"YES", "YES", "글쎄요", "YES", "YES", "Maybe", "YES", "YES", "YES", "YES", "YES", "글쎄요", "Maybe", "NO", "YES", "NO", "NO", "NO", "NO", "YES", "YES", "YES"};


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

        keyword = findViewById(R.id.main_keyword);

        btn = (TextView) findViewById(R.id.maindate);

        ////////////////////////////////////
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.main_recyclerView);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);


        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mArrayList = new ArrayList<>();
        mAdapter = new CommentAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setNestedScrollingEnabled(false);

// 아래는 구분선 주는거
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
//                mLinearLayoutManager.getOrientation());
//        mRecyclerView.addItemDecoration(dividerItemDecoration);
        ////////////////////////////////////


        //나중에 스플래쉬에 넣을 일이 생기면 최초 실행을 넣어주도록 하자
        SharedPreferences sharedPreference = getSharedPreferences("cardsData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();

        //최초 실행 여부 확인 (최초 실행일 경우, 카드 정보들을 입력해준다)
        boolean first = sharedPreference.getBoolean("isFirst", false);

        if (first == false) {

            //하단은 카드들의 1.키워드, 2.yes/no 저장해주는 과정
            int a;
            for (int i = 0; i < 22; i++) {
                a = img[i];
                editor.putString(a + "cardname", cardname[i]);
                editor.putString(a + "keyword", hintkeyword[i]);
                editor.putString(a + "yesno", yesno[i]);
                System.out.println(i + "번 실행");
                editor.commit();
            }

            editor.putBoolean("isFirst", true);
            editor.commit();
        } else {
            Log.d("Is first Time?", "not first");
        }


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


                //키보드 내리기
//                InputMethodManager mInputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                mInputMethodManager.hideSoftInputFromWindow(comment.getWindowToken(), 0);

                // 아이디 또는 비밀번호가 일치하지 않습니다 사용 예정
                if (comment.getText().toString().length() == 0) {
                    Toast.makeText(MainActivity.this, "작성된 코멘트가 없습니다.", Toast.LENGTH_SHORT).show();
                    comment.requestFocus();
                    return;
                } else {

                    String com = comment.getText().toString();

                    Comment comment = new Comment(com);

                    mArrayList.add(0, comment); //RecyclerView의 첫 줄에 삽입
//                        mArrayList.add(data); // RecyclerView의 마지막 줄에 삽입

//                        mAdapter.notifyDataSetChanged();
                    mAdapter.notifyItemInserted(0);

                    mRecyclerView.scrollToPosition(0);

                }

                comment.setText("");
                mRecyclerView.scrollToPosition(0);
            }
        });


        Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);


//        datepicker
        String currentDateString = DateFormat.getDateInstance(DateFormat.LONG).format(c.getTime());
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
        //★아래는 로딩 다이얼로그 (나중에 카드 정보 액티비티 띄울 때 사용하자)
//        ProgressDialog oDialog = new ProgressDialog(this,
//                android.R.style.Theme_DeviceDefault_Light_Dialog);
//        oDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        oDialog.setMessage("잠시만 기다려 주세요.");
//
//        oDialog.show();

        Random ram = new Random();
        int num = ram.nextInt(img.length);
        card.setImageResource(img[num]);
        keyword.setText("키워드: " + num + "번 카드 키워드 작성 예정");
        Toast.makeText(MainActivity.this, "다시 안뽑히게 설정하기 (DB)", Toast.LENGTH_SHORT).show();
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