package com.example.tarotdairy;

public class Opinion {

    public String firebasekey; // 파이어베이스리얼타임에 등록된 키 값
    String opinionnick; // 사용자 닉네임
    long opiniontime; // 작성한 시간
    String opiniontext; // 작성한 내용


    public Opinion() {}

    public Opinion(/*String firebasekey,*/ String opinionnick, long opiniontime, String opiniontext) {
//        this.firebasekey = firebasekey;
        this.opinionnick = opinionnick;
        this.opiniontime = opiniontime;
        this.opiniontext = opiniontext;
    }

    public String getFirebasekey() {
        return firebasekey;
    }

    public void setFirebasekey(String firebasekey) {
        this.firebasekey = firebasekey;
    }

    public String getOpinionnick() {
        return opinionnick;
    }

    public void setOpinionnick(String opinionnick) {
        this.opinionnick = opinionnick;
    }

    public long getOpiniontime() {
        return opiniontime;
    }

    public void setOpiniontime(long opiniontime) {
        this.opiniontime = opiniontime;
    }

    public String getOpiniontext() {
        return opiniontext;
    }

    public void setOpiniontext(String opiniontext) {
        this.opiniontext = opiniontext;
    }
}
