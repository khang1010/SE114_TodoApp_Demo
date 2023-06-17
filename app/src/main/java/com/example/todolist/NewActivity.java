package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.temporal.Temporal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText title;
    private EditText description;
    private Spinner status;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        title  = findViewById(R.id.titleEditTxt);
        description = findViewById(R.id.desEditTxt);
        saveButton = findViewById(R.id.saveBtn);
        status = findViewById(R.id.statusSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(adapter);
        status.setOnItemSelectedListener(this);
        ToDoItem toDoItem = (ToDoItem) getIntent().getSerializableExtra("todoItem");

        if (toDoItem != null) {
            title.setText(toDoItem.getTitle());
            description.setText(toDoItem.getDescription());
            if (toDoItem.isDone())
                status.setSelection(0);
            else
                status.setSelection(1);
        }

        saveButton.setOnClickListener(view -> {
            String txt1 = title.getText().toString();
            String txt2 = description.getText().toString();
            boolean isDone;
            if (status.getSelectedItemPosition() == 0) {
                isDone = true;
            } else
                isDone = false;
            if (toDoItem != null) {
                PLaceHolderAPI.placeHolderApi.updateTodo(toDoItem.getId(), new ToDoItem(txt1, txt2, isDone)).enqueue(new Callback<ToDoItem>() {
                    @Override
                    public void onResponse(Call<ToDoItem> call, Response<ToDoItem> response) {
                        Toast.makeText(NewActivity.this, "Update to do successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ToDoItem> call, Throwable t) {
                        Toast.makeText(NewActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(NewActivity.this, MainActivity.class);
                setResult(RESULT_CANCELED, intent);
                finish();
            } else {
                PLaceHolderAPI.placeHolderApi.addTodo(new ToDoItem(txt1, txt2, isDone)).enqueue(new Callback<ToDoItem>() {
                    @Override
                    public void onResponse(Call<ToDoItem> call, Response<ToDoItem> response) {
                        Toast.makeText(NewActivity.this, "Add to do successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ToDoItem> call, Throwable t) {
                        Toast.makeText(NewActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(NewActivity.this, MainActivity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        String selected = adapterView.getItemAtPosition(i).toString();
//        Toast.makeText(this, selected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}