package com.mralex6204.android.androidchat.chat.ui.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mralex6204.android.androidchat.R;
import com.mralex6204.android.androidchat.entities.ChatMessage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by oavera on 27/07/16.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context context;
    private List<ChatMessage> chatMessages;

    public ChatAdapter(Context context, List<ChatMessage> chatMessages) {
        this.context = context;
        this.chatMessages = chatMessages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_chat, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        String msg = chatMessage.getMsg();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
        Drawable drawable = context.getResources().getDrawable(R.drawable.buble);

        int color = context.getResources().getColor(R.color.colorMessagePrimary);
        int forecolor = context.getResources().getColor(R.color.forecolorMessagePrimary);
        int gravity = Gravity.RIGHT;

        if (!chatMessage.isSentByMe()) {
            color = context.getResources().getColor(R.color.colorMessage);
            forecolor = context.getResources().getColor(R.color.forecolorMessage);
            gravity = Gravity.LEFT;
        }

        drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
        params.gravity = gravity;

        holder.txtMessage.setText(msg);
        holder.txtMessage.setTextColor(forecolor);
        holder.txtMessage.setLayoutParams(params);
        holder.txtMessage.setBackground(drawable);

    }

    private int _fetchColor(int color) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data,
                new int[]{color});
        int returnColor = a.getColor(0, 0);
        a.recycle();
        return returnColor;
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public void add(ChatMessage msg) {

        if (!chatMessages.contains(msg)) {
            chatMessages.add(msg);
            notifyDataSetChanged();
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtMessage)
        TextView txtMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
