package eu.execom.todolistgrouptwo.api.wrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;

import eu.execom.todolistgrouptwo.api.RestApi;
import eu.execom.todolistgrouptwo.model.Task;

@EBean
public class TaskDAOWrapper {

    @RestService
    RestApi restApi;

    public Task create(Task task) {
        return restApi.createTask(task);
    }

    public Task update(Task task) {
        return restApi.updateTask(task, task.getId());
    }

    public Task removeTask(Task task) {
        return restApi.removeTask(task.getId());
    }
}
