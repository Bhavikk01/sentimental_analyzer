package com.example.sentimental_analyzer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sentimental_analyzer.CreateNewNote;
import com.example.sentimental_analyzer.R;
import com.example.sentimental_analyzer.models.NotesModel;

import java.util.List;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {


    Context context;
    List<NotesModel> data;

    public CardViewAdapter(Context context, List<NotesModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CardViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.card_notion,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewAdapter.ViewHolder holder, int position) {
        Log.d("TAG", "onBindViewHolder: " + data.get(position).getNotesContent() + "\n" + data.get(position).getNotesTitle() + "\n" + data.get(position).getUid());
        holder.notion.setText(data.get(position).getNotesContent());
        holder.time.setText(data.get(position).getDateTime());
        holder.note.setOnClickListener(view -> {
            Intent intent = new Intent(context, CreateNewNote.class);
            intent.putExtra("userId", data.get(position).getUid());
            intent.putExtra("notesContent", data.get(position).getNotesContent());
            intent.putExtra("notesTitle", data.get(position).getNotesTitle());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView notion;
        TextView time;
        LinearLayout note;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            notion = itemView.findViewById(R.id.notion_textview);
            time = itemView.findViewById(R.id.time_textview);
            note = itemView.findViewById(R.id.note);
        }
    }
}