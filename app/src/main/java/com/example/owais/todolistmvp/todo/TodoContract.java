package com.example.owais.todolistmvp.todo;

import com.example.owais.todolistmvp.data.Todo;

/**
 * Created by Owais on 10/3/2017.
 */
public interface TodoContract {
    interface View<T> {
        void showProgress();

        void hideProgress();

        void showTodo(T todo);

        void showMessage(String message);

        void removeItem(long id);

        void updateItem(int position, Todo todo);
    }

    interface UserActionListener {
        void AddTodo(String title, String description);

        void UpdateTodo(int position, int id, String title, String description);

        void DeleteTodo(long id);

        void loadTodos();
    }
}
