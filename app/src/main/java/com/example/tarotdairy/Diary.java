package com.example.tarotdairy;

public class Diary {

    String diarytime;
    int diarycardnum;
    float diaryrate;
    String diaryreview;

    public Diary(String diarytime, int diarycardnum, float diaryrate, String diaryreview) {
        this.diarytime = diarytime;
        this.diarycardnum = diarycardnum;
        this.diaryrate = diaryrate;
        this.diaryreview = diaryreview;
    }

    public String getDiarytime() {
        return diarytime;
    }

    public void setDiarytime(String diarytime) {
        this.diarytime = diarytime;
    }

    public int getDiarycardnum() {
        return diarycardnum;
    }

    public void setDiarycardnum(int diarycardnum) {
        this.diarycardnum = diarycardnum;
    }

    public float getDiaryrate() {
        return diaryrate;
    }

    public void setDiaryrate(float diaryrate) {
        this.diaryrate = diaryrate;
    }

    public String getDiaryreview() {
        return diaryreview;
    }

    public void setDiaryreview(String diaryreview) {
        this.diaryreview = diaryreview;
    }


}
