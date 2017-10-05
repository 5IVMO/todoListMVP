package com.example.owais.todolistmvp.data;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Owais on 10/3/2017.
 */
public interface TodoRepository {

    interface GetTodoCallback<T> {

        void onTodoLoaded(T todo);
    }
    interface ResponseCallBack{
        void onSuccess(long id);
        void onFailure();
    }

    void getAllTodo(@NonNull GetTodoCallback<List<Todo>> callback);

    void getTodo(@NonNull long todoId, @NonNull GetTodoCallback<Todo> callback);

    void saveTodo(@NonNull Todo todo,ResponseCallBack callBack);

    void updateTodo(@NonNull Todo todo,ResponseCallBack callBack);

    void deleteTodo(@NonNull long todoId,ResponseCallBack callBack);
}
