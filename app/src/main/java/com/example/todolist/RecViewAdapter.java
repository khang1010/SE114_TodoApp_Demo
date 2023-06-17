package com.example.todolist;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecViewAdapter extends RecyclerView.Adapter<RecViewAdapter.ViewHolder> {

    private List<ToDoItem> items = new ArrayList<>();
    private Context context;

    public RecViewAdapter(Context context) {
        this.context = context;
    }

    public void setItems(List<ToDoItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(items.get(position).title);
        holder.id.setText("#" + position);
        if (items.get(position).isDone()) {
            holder.status.setText("Done");
        } else {
            holder.status.setText("Not Done");
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addTodo(ToDoItem item) {
        this.items.add(item);
        notifyDataSetChanged();
    }
    public void removeTodo(ToDoItem item) {
        this.items.remove(item);
        notifyDataSetChanged();
    }
    public void removeTodo(int position) {
        this.items.remove(position);
        notifyDataSetChanged();
    }
    public void setTodo(ToDoItem item, int position) {
        items.get(position).setTitle(item.getTitle());
        items.get(position).setDescription(item.getDescription());
        notifyDataSetChanged();
    }
    public ToDoItem getTodo(int position) {
        return items.get(position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private TextView title;
        private TextView id, status;
        private ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleText);
            id = itemView.findViewById(R.id.idText);
            layout = itemView.findViewById(R.id.layout_rec);
            status = itemView.findViewById(R.id.statusText);

            layout.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(this.getAdapterPosition(), 0, 0, "Delete");
            contextMenu.add(this.getAdapterPosition(), 1, 0, "Edit");
        }
    }
}
