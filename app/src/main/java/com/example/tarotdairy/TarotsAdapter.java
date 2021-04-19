package com.example.tarotdairy;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TarotsAdapter extends RecyclerView.Adapter<TarotsAdapter.TarotsViewHolder> {

    private ArrayList<Tarots> mList;



    public class TarotsViewHolder extends RecyclerView.ViewHolder{
        protected TextView cardname;
        protected ImageView cardimage;

        public TarotsViewHolder(View view) {
            super(view);
            this.cardname = (TextView) view.findViewById(R.id.tarots_name);
            this.cardimage = (ImageView) view.findViewById(R.id.tarots_image);
        }
    }

    public TarotsAdapter(ArrayList<Tarots> list) {
        this.mList = list;
    }





    @Override
    public TarotsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_tarotcards, viewGroup, false);

        TarotsViewHolder viewHolder = new TarotsViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull TarotsViewHolder viewholder, int position) {

        viewholder.cardname.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);

        viewholder.cardname.setGravity(Gravity.CENTER);


        viewholder.cardname.setText(mList.get(position).getCardname());
        viewholder.cardimage.setImageResource(mList.get(position).getCardimage());

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }
}
