package com.example.owais.todolistmvp.data;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Owais on 10/3/2017.
 */
public class TodoRepositoryImpl implements TodoRepository {
    TodoHelper todoHelper;

    public TodoRepositoryImpl(Context context) {
        todoHelper = new TodoHelper(context);
    }

    @Override
    public void getAllTodo(@NonNull GetTodoCallback callback) {
        List<Todo> todoList = todoHelper.getAllTodo();
        callback.onTodoLoaded(todoList);
    }

    @Override
    public void getTodo(@NonNull long todoId, @NonNull GetTodoCallback callback) {
        Todo todo = todoHelper.getTodo(todoId);
        callback.onTodoLoaded(todo);
    }

    @Override
    public void saveTodo(@NonNull Todo todo, ResponseCallBack callBack) {
        long response = todoHelper.insertTodo(todo);
        if (response > 0)
            callBack.onSuccess(response);
        else
            callBack.onFailure();
    }

    @Override
    public void updateTodo(@NonNull Todo todo, ResponseCallBack callBack) {
        long response = todoHelper.updateTodo(todo);
        if (response > 0) {
            callBack.onSuccess(response);
        } else {
            callBack.onFailure();
        }
    }

    @Override
    public void deleteTodo(@NonNull long todoId, ResponseCallBack callBack) {
        int response = todoHelper.removeTodo(todoId);
        if (response > 0) {
            callBack.onSuccess(response);
        } else {
            callBack.onFailure();
        }
    }
}
