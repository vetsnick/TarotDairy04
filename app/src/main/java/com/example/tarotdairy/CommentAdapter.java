package com.example.tarotdairy;

import android.content.Context;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {


    private ArrayList<Comment> mList;

    private Context mContext;


    public class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        protected TextView comment;

        public CommentViewHolder(View view) {
            super(view);

            this.comment = (TextView) view.findViewById(R.id.item_comment_text);

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


                switch (item.getItemId()) {
                    case 1001:

                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        View view = LayoutInflater.from(mContext)
                                .inflate(R.layout.dialog_maincomment, null, false);
                        builder.setView(view);
                        final Button ButtonSubmit = (Button) view.findViewById(R.id.commentsubmit);
                        final EditText editTextComment = (EditText) view.findViewById(R.id.comment);

                        editTextComment.setText(mList.get(getAdapterPosition()).getComment());


                        final AlertDialog dialog = builder.create();
                        ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                String commenta = editTextComment.getText().toString();

                                Comment que = new Comment(commenta);

                                mList.set(getAdapterPosition(), que);
                                notifyItemChanged(getAdapterPosition());

                                dialog.dismiss();
                            }
                        });

                        dialog.show();

                        break;

                    case 1002:

                        mList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), mList.size());

                        break;

                }
                return true;
            }
        };
    }

    public CommentAdapter(Context context, ArrayList<Comment> list) {
        mList = list;
        mContext = context;
    }


    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_comment, viewGroup, false);

        CommentViewHolder viewHolder = new CommentViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
//        Comment current = mList.get(position);

        holder.comment.setGravity(Gravity.LEFT);

        holder.comment.setText(mList.get(position).getComment());

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }
}
