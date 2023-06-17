package com.example.todolist;

import java.io.Serializable;

public class ToDoItem implements Serializable {
    int id;
    String title;
    String description;
    boolean done;

    public ToDoItem(String title, String description, boolean status) {
        this.title = title;
        this.description = description;
        this.done = status;
        id = 0;
    }

    public ToDoItem(int id, String title, String description, boolean status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setStatus(boolean status) {
        this.done = status;
    }
}
