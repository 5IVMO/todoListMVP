package com.example.owais.todolistmvp.data;

/**
 * Created by Owais on 10/3/2017.
 */
public class Todo {
    private int id;
    private String title;
    private String description;

    public Todo() {
    }

    public Todo(int id, String title, String description) {
        this.description = description;
        this.id = id;
        this.title = title;
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
}
