package com.example.owais.todolistmvp.todo;

import android.content.Context;

import com.example.owais.todolistmvp.data.Todo;
import com.example.owais.todolistmvp.data.TodoRepository;
import com.example.owais.todolistmvp.data.TodoRepositoryImpl;

import java.util.List;

/**
 * Created by Owais on 10/3/2017.
 */
public class TodoPresenter implements TodoContract.UserActionListener {
    private final TodoContract.View todoView;
    TodoRepository todoRepository;

    public TodoPresenter(Context context, TodoContract.View todoView) {
        this.todoView = todoView;
        todoRepository = new TodoRepositoryImpl(context);
    }

    @Override
    public void AddTodo(String title, String description) {
        final Todo todo = new Todo();
        todo.setTitle(title);
        todo.setDescription(description);
        todoRepository.saveTodo(todo, new TodoRepository.ResponseCallBack() {
            @Override
            public void onSuccess(long id) {
                todoView.showMessage("Successfully add Todo");
                todoRepository.getTodo(id, new TodoRepository.GetTodoCallback<Todo>() {
                    @Override
                    public void onTodoLoaded(Todo todo) {
                        todoView.showNewTodo(todo);
                    }
                });
            }

            @Override
            public void onFailure() {
                todoView.showMessage("Addition Failed");
            }
        });
    }

    @Override
    public void UpdateTodo(int id, String title, String description) {
        final Todo todo = new Todo();
        todo.setId(id);
        todo.setTitle(title);
        todo.setDescription(description);
        todoRepository.updateTodo(todo, new TodoRepository.ResponseCallBack() {
            @Override
            public void onSuccess(long id) {
                todoView.showMessage("Successfully update todo");
                loadTodos();
            }

            @Override
            public void onFailure() {
                todoView.showMessage("Update Failed");
            }
        });
    }

    @Override
    public void DeleteTodo(long id) {
        todoRepository.deleteTodo(id, new TodoRepository.ResponseCallBack() {
            @Override
            public void onSuccess(long id) {
                todoView.showMessage("Successfully delete todo");
                loadTodos();
            }

            @Override
            public void onFailure() {
                todoView.showMessage("Delete Failed");
            }
        });
    }

    @Override
    public void loadTodos() {
        todoRepository.getAllTodo(new TodoRepository.GetTodoCallback<List<Todo>>() {
            @Override
            public void onTodoLoaded(List<Todo> todo) {
                todoView.hideProgress();
                todoView.showTodoList(todo);
            }
        });
    }
}
