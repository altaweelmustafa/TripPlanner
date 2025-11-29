package com.example.assone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TodoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TodoAdapter adapter;
    ArrayList<String> todoList = new ArrayList<>();

    EditText edtTask;
    ImageButton btnAdd;

    Trip trip;
    String PREF_KEY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        recyclerView = findViewById(R.id.recyclerTodo);
        edtTask = findViewById(R.id.edtTask);
        btnAdd = findViewById(R.id.btnAddTask);

        trip = (Trip) getIntent().getSerializableExtra("trip");

        // Unique key for saving each trip's tasks
        PREF_KEY = "todo_" + trip.name;

        loadTodos();

        adapter = new TodoAdapter(todoList, new TodoAdapter.OnTodoChanged() {
            @Override
            public void onToggle(int position, boolean value) {

            }
            @Override
            public void onDelete(int position) {
                todoList.remove(position);
                adapter.notifyItemRemoved(position);
                saveTodos();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            String task = edtTask.getText().toString();

            if (!task.isEmpty()) {
                todoList.add(task);
                adapter.notifyItemInserted(todoList.size() - 1);
                edtTask.setText("");
                saveTodos();
            }
        });
    }

    private void saveTodos() {
        SharedPreferences prefs = getSharedPreferences("todos", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        editor.putString(PREF_KEY, gson.toJson(todoList));
        editor.apply();
    }

    private void loadTodos() {
        SharedPreferences prefs = getSharedPreferences("todos", MODE_PRIVATE);
        Gson gson = new Gson();

        String json = prefs.getString(PREF_KEY, null);
        Type type = new TypeToken<ArrayList<String>>(){}.getType();

        ArrayList<String> saved = gson.fromJson(json, type);
        if (saved != null) todoList = saved;
    }
}
