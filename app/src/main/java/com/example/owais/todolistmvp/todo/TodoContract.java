package com.example.owais.todolistmvp.todo;

import com.example.owais.todolistmvp.data.Todo;

import java.util.List;

/**
 * Created by Owais on 10/3/2017.
 */
public interface TodoContract {
    interface View {
        void showProgress();

        void hideProgress();

        void showTodoList(List<Todo> todoList);

        void showNewTodo(Todo todo);

        void showMessage(String message);
    }

    interface UserActionListener {
        void AddTodo(String title, String description);

        void UpdateTodo(int id, String title, String description);

        void DeleteTodo(long id);

        void loadTodos();
    }
}
