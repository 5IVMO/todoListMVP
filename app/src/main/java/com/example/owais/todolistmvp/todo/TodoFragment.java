package com.example.owais.todolistmvp.todo;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owais.todolistmvp.R;
import com.example.owais.todolistmvp.data.Todo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.owais.todolistmvp.R.id.button_edit;

/**
 * Created by Owais on 10/3/2017.
 */
public class TodoFragment extends ListFragment implements TodoContract.View, View.OnClickListener {
    private Button buttonAddTodo;
    EditText editTextTitle, editTextDescription;
    private TodoContract.UserActionListener userActionListener;
    FloatingActionButton fab;
    Dialog dialog;
    private String description;
    private String title;
    ArrayList<Todo> todoArrayList;
    MyCustomAdapter mListAdapter;
    ListView lvItems;
    ProgressBar progressBar;

    public TodoFragment() {
        // Requires empty public constructor
    }

    public static TodoFragment newInstance() {
        return new TodoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        todoArrayList = new ArrayList<Todo>(0);
        mListAdapter = new MyCustomAdapter(getActivity(), todoArrayList);
        setListAdapter(mListAdapter);
        userActionListener = new TodoPresenter(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar_loading);
        lvItems = (ListView) view.findViewById(android.R.id.list);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        userActionListener.loadTodos();
    }

    @Override
    public void showTodo(Object todo) {
        if (todo instanceof Todo) {
            todoArrayList.add((Todo) todo);
        } else {
            if (todoArrayList.size() > 0) {
                todoArrayList.clear();
                todoArrayList.addAll((List) todo);
            } else {
                todoArrayList.addAll((List) todo);
            }
        }
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeItem(long id) {
        //Using iterator to avoid concurrent modification exception.
        Iterator<Todo> iter = todoArrayList.iterator();
        while (iter.hasNext()) {
            Todo todo = iter.next();
            if (todo.getId() == id)
                iter.remove();
        }
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateItem(int position, Todo todo) {
        todoArrayList.get(position).setTitle(todo.getTitle());
        todoArrayList.get(position).setDescription(todo.getDescription());
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        lvItems.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        lvItems.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                setupDialog();
                buttonAddTodo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        title = editTextTitle.getText().toString();
                        description = editTextDescription.getText().toString();
                        if (!(title.equals("") || description.equals(""))) {
                            userActionListener.AddTodo(title, description);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Fields Should not be left Empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    private void setupDialog() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add_todo);
        dialog.show();
        buttonAddTodo = (Button) dialog.findViewById(R.id.button_add_todo);
        editTextTitle = (EditText) dialog.findViewById(R.id.editText_Title);
        editTextDescription = (EditText) dialog.findViewById(R.id.editText_Description);
    }

    public class MyCustomAdapter extends ArrayAdapter {
        public MyCustomAdapter(Activity activity, ArrayList<Todo> list) {
            super(activity, 0, list);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.custom_list_item, null);
            }
            final Todo st = (Todo) getItem(position);
            TextView t1 = (TextView) convertView.findViewById(R.id.titleTextView);
            t1.setText("Title: " + st.getTitle());
            TextView t2 = (TextView) convertView.findViewById(R.id.dateTextView);
            t2.setText("Description: " + st.getDescription());
            Button buttonEdit = (Button) convertView.findViewById(button_edit);
            Button buttonDelete = (Button) convertView.findViewById(R.id.button_delete);
            buttonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setupDialog();
                    editTextTitle.setText(st.getTitle());
                    editTextDescription.setText(st.getDescription());
                    buttonAddTodo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            title = editTextTitle.getText().toString();
                            description = editTextDescription.getText().toString();
                            if (!(title.equals("") || description.equals(""))) {
                                userActionListener.UpdateTodo(position, st.getId(), title, description);
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "Fields Should not be left Empty", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userActionListener.DeleteTodo(st.getId());
                }
            });
            return convertView;
        }
    }
}
