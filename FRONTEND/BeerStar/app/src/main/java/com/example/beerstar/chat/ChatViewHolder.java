package com.example.beerstar.chat;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import com.example.beerstar.R;

import androidx.recyclerview.widget.RecyclerView;

public class ChatViewHolder extends RecyclerView.ViewHolder {
    private final TextView messageText;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        messageText = itemView.findViewById(R.id.messageText);
    }

    public void bind(ChatMessage message) {
        messageText.setText(message.getText());
    }
}
