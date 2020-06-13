package com.codeinger.notification_fcm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codeinger.notification_fcm.R;
import com.codeinger.notification_fcm.model.Topic;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class TopicAdapter extends FirebaseRecyclerAdapter<Topic, TopicAdapter.TopicViewHolder> {


    public TopicAdapter(@NonNull FirebaseRecyclerOptions<Topic> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TopicViewHolder holder, int position, @NonNull Topic model) {
        holder.topic.setText(model.getTitle());
        holder.subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic, parent, false);
        return new TopicViewHolder(view);
    }

    class TopicViewHolder extends RecyclerView.ViewHolder{

        TextView topic;
        ImageView subscribe;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);

            subscribe = itemView.findViewById(R.id.subscribe);
            topic = itemView.findViewById(R.id.topic);
        }
    }
}
