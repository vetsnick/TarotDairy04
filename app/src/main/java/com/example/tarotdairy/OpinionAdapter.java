package com.example.tarotdairy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class OpinionAdapter extends ArrayAdapter<Opinion> {

    private final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("a h:mm", Locale.getDefault());


    public OpinionAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_opinion, null);

            viewHolder = new ViewHolder();
            viewHolder.usernick = (TextView) convertView.findViewById(R.id.item_opinion_nick);
            viewHolder.usertime = (TextView) convertView.findViewById(R.id.item_opinion_time);
            viewHolder.usertext = (TextView) convertView.findViewById(R.id.item_opinion_text);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Opinion chatData = getItem(position);
        viewHolder.usernick.setText(chatData.opinionnick);
        viewHolder.usertime.setText(mSimpleDateFormat.format(chatData.opiniontime));
        viewHolder.usertext.setText(chatData.opiniontext);

        return convertView;
    }


    private class ViewHolder {
        private TextView usernick;
        private TextView usertime;
        private TextView usertext;
    }


}
