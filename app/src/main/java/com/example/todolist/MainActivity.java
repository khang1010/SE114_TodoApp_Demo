package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.moshi.Moshi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecViewAdapter adapter;
    private int idPosition;
    Moshi moshi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new RecViewAdapter(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllTodos(adapter);
//        ArrayList<ToDoItem> items = new ArrayList<>();
//        items.add(new ToDoItem("Hello", "Xin chao!", "10/10/2003"));
//
//        adapter.setItems(items);
    }

//    @Override
//    protected void onResume() {
//        ToDoItem todo = (ToDoItem) getIntent().getSerializableExtra("todo");
//        if (todo != null) {
//            adapter.addTodo(todo);
//        }
//        super.onResume();
//    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
//                        Intent intent = result.getData();
//                        ToDoItem todo = (ToDoItem) intent.getSerializableExtra("todo");
//                        adapter.addTodo(todo);
                        getAllTodos(adapter);
                    } else if (result.getResultCode() == RESULT_CANCELED) {
//                        Intent intent = result.getData();
//                        ToDoItem todo = (ToDoItem) intent.getSerializableExtra("todo");
//                        adapter.setTodo(todo, idPosition);
                        getAllTodos(adapter);
                    }
                }
            }
    );
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newItem:
//                startActivity(new Intent(MainActivity.this, NewActivity.class));
                launcher.launch(new Intent(MainActivity.this, NewActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                PLaceHolderAPI.placeHolderApi.deleteTodo(adapter.getTodo(item.getGroupId()).getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(MainActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
                getAllTodos(adapter);
//                adapter.removeTodo(item.getGroupId());
                break;
            case 1:
                Intent intent = new Intent(MainActivity.this, NewActivity.class);
                intent.putExtra("todoItem", adapter.getTodo(item.getGroupId()));
                idPosition = item.getGroupId();
                launcher.launch(intent);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void getAllTodos(RecViewAdapter recViewAdapter) {

        PLaceHolderAPI.placeHolderApi.getAllTodo().enqueue(new Callback<List<ToDoItem>>() {
            @Override
            public void onResponse(Call<List<ToDoItem>> call, Response<List<ToDoItem>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    List<ToDoItem> items = response.body();
                    recViewAdapter.setItems(items);
                }
            }

            @Override
            public void onFailure(Call<List<ToDoItem>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}