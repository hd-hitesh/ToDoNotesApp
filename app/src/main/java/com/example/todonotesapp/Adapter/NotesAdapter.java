package com.example.todonotesapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.todonotesapp.Model.Notes;
import com.example.todonotesapp.R;
import com.example.todonotesapp.clickListeners.ItemClickListeners;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private List<Notes> listNotes;
    private ItemClickListeners itemClickListeners;

    public NotesAdapter(List<Notes> list, ItemClickListeners itemClickListeners) {
        this.listNotes = list;
        this.itemClickListeners = itemClickListeners;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //adding layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_adapter_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        //binding data to layout
        final Notes notes = listNotes.get(position);
        final String title = notes.getTille();
        final String description = notes.getDescription();

        holder.textViewTitle.setText(title);
        holder.textViewDescription.setText(description);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListeners.onClick(notes);
            }
        });
    }

    @Override
    public int getItemCount() {
        //showing no, of item in list
        return listNotes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle,textViewDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
        }
    }
}
