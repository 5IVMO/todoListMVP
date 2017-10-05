package com.example.owais.todolistmvp.todo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.owais.todolistmvp.R;

public class TodoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        if (null == savedInstanceState) {
            initFragment(TodoFragment.newInstance());
        }
    }

    private void initFragment(Fragment detailFragment) {
        // Add the AddNoteFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, detailFragment);
        transaction.commit();
    }
}
