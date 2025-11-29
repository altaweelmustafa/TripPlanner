package com.example.assone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    ArrayList<String> todos;
    OnTodoChanged listener;

    public interface OnTodoChanged {
        void onToggle(int position, boolean value);
        void onDelete(int position);
    }

    public TodoAdapter(ArrayList<String> todos, OnTodoChanged listener) {
        this.todos = todos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        String task = todos.get(position);

        holder.checkTask.setText(task);
        holder.checkTask.setChecked(false);

        holder.checkTask.setOnCheckedChangeListener((buttonView, isChecked) -> {
            listener.onToggle(holder.getAdapterPosition(), isChecked);
        });

        holder.btnDelete.setOnClickListener(v -> listener.onDelete(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    static class TodoViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkTask;
        ImageButton btnDelete;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            checkTask = itemView.findViewById(R.id.checkTodo);
            btnDelete = itemView.findViewById(R.id.btnDeleteTodo);
        }
    }
}
