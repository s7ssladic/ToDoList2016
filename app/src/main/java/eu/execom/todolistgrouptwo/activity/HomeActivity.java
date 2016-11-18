package eu.execom.todolistgrouptwo.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.lang.reflect.Type;
import java.util.List;

import eu.execom.todolistgrouptwo.R;
import eu.execom.todolistgrouptwo.adapter.TaskAdapter;
import eu.execom.todolistgrouptwo.model.Task;
import eu.execom.todolistgrouptwo.preference.TaskStore_;

/**
 * Home {@link AppCompatActivity Activity} for navigation and listing all tasks.
 */
@EActivity(R.layout.activity_home)
public class HomeActivity extends AppCompatActivity {

    /**
     * Used for logging purposes.
     */
    private static final String TAG = HomeActivity.class.getSimpleName();

    /**
     * Used for identifying results from different activities.
     */
    protected static final int ADD_TASK_REQUEST_CODE = 42;

    /**
     * Tasks are kept in this list during a user session.
     */
    private List<Task> tasks;

    /**
     * {@link android.content.SharedPreferences SharedPreferences} are used for storing tasks
     * for now instead of a database.
     */
    @Pref
    TaskStore_ taskStore;

    /**
     * {@link FloatingActionButton FloatingActionButton} for starting the
     * {@link AddTaskActivity AddTaskActivity}.
     */
    @ViewById
    FloatingActionButton addTask;

    /**
     * {@link ListView ListView} for displaying the tasks.
     */
    @ViewById
    ListView listView;

    /**
     * {@link TaskAdapter Adapter} for providing data to the {@link ListView listView}.
     */
    @Bean
    TaskAdapter adapter;

    /**
     * Loads tasks from the {@link android.content.SharedPreferences SharedPreferences}
     * and sets the adapter.
     */
    @AfterViews
    void initData() {
        Log.i(TAG, taskStore.tasks().get());
        final Type listType = new TypeToken<List<Task>>() {
        }.getType();
        tasks = new Gson().fromJson(taskStore.tasks().get(), listType);

        listView.setAdapter(adapter);
        adapter.setTasks(tasks);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    // button show
                    addTask.show();
                } else {
                    // button hide
                    addTask.hide();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    /**
     * Called when the {@link FloatingActionButton FloatingActionButton} is clicked.
     */
    @Click
    void addTask() {
        Log.i(TAG, "Add task clicked!");
        AddTaskActivity_.intent(this).startForResult(ADD_TASK_REQUEST_CODE);
    }

    /**
     * Called when the {@link AddTaskActivity AddTaskActivity} finishes.
     *
     * @param responseCode Indicates whether the activity was successful.
     * @param task         The new task.
     */
    @OnActivityResult(ADD_TASK_REQUEST_CODE)
    void onResult(int responseCode, @OnActivityResult.Extra String task) {
        if (responseCode == RESULT_OK) {
            Toast.makeText(this, task, Toast.LENGTH_SHORT).show();
            final Gson gson = new Gson();
            final Task newTask = gson.fromJson(task, Task.class);

            tasks.add(newTask);
            taskStore.tasks().put(gson.toJson(tasks));

            adapter.addTask(newTask);
        }
    }
}
