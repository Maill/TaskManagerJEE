package TasksApp.Data.Interface;

import TasksApp.Data.Contract.Task;

import java.util.List;

public interface ITaskDAO {

    /**
     * Store a new TasksApp.Data.Contract.Task
     * @param taskToCreate TasksApp.Data.Contract.Task to create
     */
    void create(Task taskToCreate);

    /**
     * Gather Tasks from the database
     * @param id Specify the id of the TasksApp.Data.Contract.Task or -1 to get all the records
     * @return A list of TasksApp.Data.Contract.Task
     */
    List<Task> read(int id);

    /**
     * update a TasksApp.Data.Contract.Task
     * @param taskToUpdate TasksApp.Data.Contract.Task to update
     */
    void update(Task taskToUpdate);

    /**
     * Delete a TasksApp.Data.Contract.Task
     * @param taskToDelete TasksApp.Data.Contract.Task to delete
     */
    void Delete(Task taskToDelete);
}
