package eu.execom.todolistgrouptwo.database.wrapper;


import org.androidannotations.annotations.EBean;
import org.androidannotations.ormlite.annotations.OrmLiteDao;

import java.util.List;

import eu.execom.todolistgrouptwo.database.DatabaseHelper;
import eu.execom.todolistgrouptwo.database.dao.TaskDAO;
import eu.execom.todolistgrouptwo.model.Task;
import eu.execom.todolistgrouptwo.model.User;

@EBean
public class TaskDAOWrapper {

    @OrmLiteDao(helper = DatabaseHelper.class)
    TaskDAO taskDAO;

    public List<Task> findByUser(User user) {
        return taskDAO.findByUser(user);
    }

    public void create(Task task) {
        taskDAO.create(task);
    }

}
