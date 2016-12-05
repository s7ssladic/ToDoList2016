package eu.execom.todolistgrouptwo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import eu.execom.todolistgrouptwo.R;
import eu.execom.todolistgrouptwo.database.wrapper.TaskDAOWrapper;
import eu.execom.todolistgrouptwo.model.Task;

@EActivity(R.layout.activity_one_task)
public class OneTaskActivity extends AppCompatActivity {

    private static final String TAG = OneTaskActivity.class.getSimpleName();
    @Bean
    TaskDAOWrapper taskDAOWrapper;

    @ViewById(R.id.titleOneTask)
    EditText title;

    @ViewById(R.id.descriptionOneTask)
    EditText description;

    @ViewById(R.id.isFinished)
    CheckBox cbIsFinished;

    @Extra
    int indexOfTask;

    @Extra
    String task;

    Task currentTask;

    @Extra("task")
    void parseTask(String task) {
        Gson gb = new GsonBuilder().create();
        currentTask = gb.fromJson(task, Task.class);
    }

    @AfterViews
    void readTask() {
        title.setText(currentTask.getTitle());
        description.setText(currentTask.getDescription());
        cbIsFinished.setChecked(currentTask.isFinished());
    }

    @Click(R.id.deleteTask)
    void deleteTask(){
        doDeleteTask(currentTask);
    }

    @Background
    void doDeleteTask(Task task) {
        try {
            taskDAOWrapper.removeTask(task);
            successDeleted();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            errorUpdate();
        }
    }

    @UiThread
    void successDeleted() {
        Intent intent = new Intent();
        intent.putExtra("indexOfTask", indexOfTask);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Click(R.id.updateTask)
    void updateTask() {
        currentTask.setTitle(title.getText().toString());
        currentTask.setDescription(description.getText().toString());
        currentTask.setFinished(cbIsFinished.isChecked());

        doUpdateTask(currentTask);
    }

    @Background
    void doUpdateTask(Task task) {
        try {
            taskDAOWrapper.update(task);
            successUpdated();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            errorUpdate();
        }
    }

    @UiThread
    void errorUpdate() {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        setResult(RESULT_CANCELED);
    }

    @UiThread
    void successUpdated() {
        Intent intent = new Intent();
        Gson gson = new Gson();
        intent.putExtra("task", gson.toJson(currentTask))
                .putExtra("indexOfTask", indexOfTask);
        setResult(RESULT_OK, intent);
        finish();
    }
}
