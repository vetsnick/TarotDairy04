package com.example.tarotdairy;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>
//        implements Filterable
{

    private ArrayList<Question> mList;
    private ArrayList<Question> exampleListFull;

    private Context mContext;


    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        protected TextView date;
        protected TextView title;
        protected ImageView card;


        public CustomViewHolder(View view) {
            super(view);
            this.date = (TextView) view.findViewById(R.id.item_qna_date);
            this.title = (TextView) view.findViewById(R.id.item_qna_title);
            this.card = (ImageView) view.findViewById(R.id.item_qna_card);

            view.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //메뉴 추가가
            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "수정");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }

        //캔텍스트 메뉴 클릭 시 동작 설정

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                long timere = mList.get(getAdapterPosition()).getTimestamp();
                int cardre = mList.get(getAdapterPosition()).getCardnum();

                switch (item.getItemId()) {
                    case 1001:

                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        View view = LayoutInflater.from(mContext)
                                .inflate(R.layout.dialog_addqna, null, false);
                        builder.setView(view);
                        final Button ButtonSubmit = (Button) view.findViewById(R.id.qnasubmit);
                        final EditText editTextTitle = (EditText) view.findViewById(R.id.qnaquestion);

                        editTextTitle.setText(mList.get(getAdapterPosition()).getTitle());




                        //수정 버튼 눌렀을 때...
                        final AlertDialog dialog = builder.create();
                        ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                String title = editTextTitle.getText().toString();

                                Question que = new Question(timere, title, cardre);

                                mList.set(getAdapterPosition(), que);
                                notifyItemChanged(getAdapterPosition());

                                SharedPreferences sharedPreferences = mContext.getSharedPreferences("mycontacts", 0);
                                int maxId = sharedPreferences.getInt("maxID", 0);
                                for (int i = 1; i <= maxId; i++) {
//                                    String pretitle = sharedPreferences.getString("title" + i, "");
                                    long pretime = sharedPreferences.getLong("time" + i, 0);
                                    int precard = sharedPreferences.getInt("card"+i, 0);

                                    if (pretime == timere) {
                                            if (precard == cardre) {
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("title" + i, title);
                                                editor.commit();
                                            }
                                        }
                                    }



                                dialog.dismiss();
                            }
                        });

                        dialog.show();

                        break;

                    case 1002:


                        //하단은 코드 뷰에서는 삭제 가능하지만, sharedPreference에선 삭제 되지 않음으로 sharedPreference 삭제 코드가 필요
//                        long time = mList.get(getAdapterPosition()).getTimestamp();
//                        System.out.println(time);
                        String title = mList.get(getAdapterPosition()).getTitle();
//                        System.out.println(title);
//                        int card = mList.get(getAdapterPosition()).getCardnum();
//                        System.out.println(card);
                        SharedPreferences sharedPreferences = mContext.getSharedPreferences("mycontacts", 0);
                        int maxId = sharedPreferences.getInt("maxID", 0);
                        for (int i = 1; i <= maxId; i++) {
                            long pretime = sharedPreferences.getLong("time" + i, 0);
                            String pretitle = sharedPreferences.getString("title"+i, "");
                            int precard = sharedPreferences.getInt("card"+i, 0);

                            System.out.println("전 시간"+timere);
                            System.out.println("후 시간"+pretime);


                            System.out.println("전 카드"+cardre);
                            System.out.println("후 카드"+precard);



                            System.out.println("전 제목"+title);
                            System.out.println("후 제목"+pretitle);

                            if (pretime == timere) {
                                if (pretitle == title) {
                                    if (precard == cardre) {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.remove("time" + i);
                                        editor.remove("title" + i);
                                        editor.remove("card" + i);
                                        editor.commit();
                                    }
                                }
                            }

                        }         mList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), mList.size());

                        break;
                }
                return true;
            }
        };

    }


    

    public CustomAdapter(Context context, ArrayList<Question> list) {
        mList = list;
        mContext = context;

        exampleListFull = new ArrayList<>(mList);
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_qna, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        Question current = mList.get(position);

        long unixTime = current.getTimestamp();
        Date time = new Date(unixTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm (E)", Locale.KOREA);
        String formattedTime = sdf.format(time);
        viewholder.date.setText(formattedTime);

//        viewholder.title.setTextSize();
//        viewholder.date.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
//        viewholder.card.setImaTextSize(TypedValue.COMPLEX_UNIT_SP, 25);

        viewholder.title.setGravity(Gravity.LEFT);
        viewholder.date.setGravity(Gravity.LEFT);
//        viewholder.card.setGravity(Gravity.CENTER);


        viewholder.title.setText(mList.get(position).getTitle());
//        viewholder.date.setText(mList.get(position).getTimestamp());
        viewholder.card.setImageResource(mList.get(position).getCardnum());
    }

//    @Override
//    public int getItemCount() {
//        return mList.size();
//    }


        @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


//    @Override
//    public Filter getFilter() {
//        return exampleFilter;
//    }

//    private Filter exampleFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            ArrayList<Question> filteredList = new ArrayList<>();
//
//            if (constraint == null || constraint.length() == 0) {
//                filteredList.addAll(exampleListFull);
//            } else {
//                String filterPattern = constraint.toString().toLowerCase().trim();
//
//                for (Question item : exampleListFull) {
//                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
//                        filteredList.add(item);
//                    }
//                }
//            }
//
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//
//            return results;
//        }

//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            mList.clear();
//            mList.addAll((List) results.values);
//            notifyDataSetChanged();
//        }
//    };

}