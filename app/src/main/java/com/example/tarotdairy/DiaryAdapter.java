package com.example.tarotdairy;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {

    private ArrayList<Diary> mExampleList;
    private Context mContext;

    int setimg;
    float setrate;
    String setreview;


    public static class DiaryViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView review;
        public RatingBar rate;
        public ImageView card;


        public DiaryViewHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.item_diary_date);
            card = itemView.findViewById(R.id.item_diary_card);
            rate = itemView.findViewById(R.id.item_diary_rate);
            review = itemView.findViewById(R.id.item_diary_review);

//            itemView.setOnCreateContextMenuListener(this);

        }
    }

    public DiaryAdapter(HistoryDiary historyDiary, ArrayList<Diary> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public DiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary, parent, false);
        DiaryViewHolder vh = new DiaryViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(DiaryViewHolder holder, int position) {
        Diary currentItem = mExampleList.get(position);

        SharedPreferences sf = mContext.getSharedPreferences("display", mContext.MODE_PRIVATE);

        setimg = sf.getInt(currentItem.getDiarytime()+"todaycard", 0);
        setrate = sf.getFloat(currentItem.getDiarytime()+"todayrate", 0);
        setreview = sf.getString(currentItem.getDiarytime()+"review", "");


        holder.date.setText(currentItem.getDiarytime());
        holder.card.setImageResource(setimg);
        holder.rate.setRating(setrate);
        holder.review.setText(setreview);

        System.out.println("???????:" + setreview + setimg + setrate);

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
//        return (null != mExampleList ? mExampleList.size() : 0);
    }


//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        //?????? ?????????
//        MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "??????");
//        MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "??????");
//        Edit.setOnMenuItemClickListener(onEditMenu);
//        Delete.setOnMenuItemClickListener(onEditMenu);
//    }
//
//    //???????????? ?????? ?????? ??? ?????? ??????
//
//    private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
//        @Override
//        public boolean onMenuItemClick(MenuItem item) {
//            long timere = mExampleList.get(getAdapterPosition()).getTimestamp();
//            int cardre = mExampleList.get(getAdapterPosition()).getCardnum();
//
//            switch (item.getItemId()) {
//                case 1001:
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                    View view = LayoutInflater.from(mContext)
//                            .inflate(R.layout.dialog_addqna, null, false);
//                    builder.setView(view);
//                    final Button ButtonSubmit = (Button) view.findViewById(R.id.qnasubmit);
//                    final EditText editTextTitle = (EditText) view.findViewById(R.id.qnaquestion);
//
//                    editTextTitle.setText(mExampleList.get(getAdapterPosition()).getTitle());
//
//
//
//
//                    final AlertDialog dialog = builder.create();
//                    ButtonSubmit.setOnClickListener(new View.OnClickListener() {
//                        public void onClick(View v) {
//                            String title = editTextTitle.getText().toString();
//
//                            Question que = new Question(timere, title, cardre);
//
//                            mExampleList.set(getAdapterPosition(), que);
//                            notifyItemChanged(getAdapterPosition());
//
//                            dialog.dismiss();
//                        }
//                    });
//
//                    dialog.show();
//
//                    break;
//
//                case 1002:
//
//
//                    //????????? ?????? ???????????? ?????? ???????????????, sharedPreference?????? ?????? ?????? ???????????? sharedPreference ?????? ????????? ??????
////                        long time = mList.get(getAdapterPosition()).getTimestamp();
////                        System.out.println(time);
//                    String title = mExampleList.get(getAdapterPosition()).getTitle();
////                        System.out.println(title);
////                        int card = mList.get(getAdapterPosition()).getCardnum();
////                        System.out.println(card);
//                    SharedPreferences sharedPreferences = mContext.getSharedPreferences("mycontacts", 0);
//                    int maxId = sharedPreferences.getInt("maxID", 0);
//                    for (int i = 1; i <= maxId; i++) {
//                        long pretime = sharedPreferences.getLong("time" + i, 0);
//                        String pretitle = sharedPreferences.getString("title"+i, "");
//                        int precard = sharedPreferences.getInt("card"+i, 0);
//
//                        System.out.println("??? ??????"+timere);
//                        System.out.println("??? ??????"+pretime);
//
//
//                        System.out.println("??? ??????"+cardre);
//                        System.out.println("??? ??????"+precard);
//
//
//
//                        System.out.println("??? ??????"+title);
//                        System.out.println("??? ??????"+pretitle);
//
//                        if (pretime == timere) {
//                            if (pretitle == title) {
//                                if (precard == cardre) {
//                                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                                    editor.remove("time" + i);
//                                    editor.remove("title" + i);
//                                    editor.remove("card" + i);
//                                    editor.commit();
//                                }
//                            }
//                        }
//
//                    }         mExampleList.remove(getAdapterPosition());
//                    notifyItemRemoved(getAdapterPosition());
//                    notifyItemRangeChanged(getAdapterPosition(), mExampleList.size());
//
//                    break;
//            }
//            return true;
//        }
//    };

}
