package TasksApp.Data;

import TasksApp.Data.Contract.Task;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TaskDAO {

    //region Connection attributes
    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction tx;
    //endregion

    //region Logging attribute
    private Logger log;
    //endregion

    //region Connection Manager
    /**
     * Initiate the connection from configuration
     */
    private void InitConnection() {
        log.info("Initialize connection");
        try {
            // Set the connection
            emf = Persistence.createEntityManagerFactory("BaseTask");
            em = emf.createEntityManager();
            tx = em.getTransaction();
        } catch (Exception exp) {
            throw exp;
        }

    }

    /**
     * Open the connection for data request
     */
    private void beginConnection(){
        log.info("Begin connection");
        try {
            //Checking if an active connection exists
            if (!tx.isActive()){
                // Begin connection
                tx.begin();
            }
        } catch (Exception exp) {
            throw exp;
        }
    }

    /**
     * Apply the data request in queue
     */
    private void ApplyRequest(){
        log.info("Apply request in queue");
        try {
            // Apply requests
            tx.commit();
        } catch (Exception exp) {
            throw exp;
        }
    }

    /**
     * Close the connection
     */
    private void destroyConnection(){
        log.info("Closing connection");
        try {
            // Close connection
            em.close();
            emf.close();
            // Reset connection
            tx = null;
            em = null;
            emf = null;
        } catch (Exception exp) {
            throw exp;
        }
    }
    //endregion

    //region Data requests
    /**
     * Store a new TasksApp.Data.Contract.Task
     * @param taskToCreate TasksApp.Data.Contract.Task to create
     */
    public void create(Task taskToCreate){
        beginConnection();
        try {
            em.persist(taskToCreate);
        } catch (Exception exp) {
            log.info("Error on create method\nMessage: " + exp.getMessage() + "\nStacktrace: " + getStringStackTrace(exp).toString());
        } finally {
            ApplyRequest();
        }
    }

    /**
     * Gather Tasks from the database
     * @param id Specify the id of the TasksApp.Data.Contract.Task or -1 to get all the records
     * @return A list of TasksApp.Data.Contract.Task
     */
    public List<Task> read(int id){
        List<Task> toReturn = new ArrayList<>();
        beginConnection();
        try {
            if(id == -1){
                toReturn.addAll(em.createQuery("FROM Task t").getResultList());
            } else {
                toReturn.add(em.find(Task.class, id));
            }
        } catch (Exception exp) {
            log.info("Error on read method\nMessage: " + exp.getMessage() + "\nStacktrace: " + getStringStackTrace(exp).toString());
        } finally {
            ApplyRequest();
        }

        return toReturn;
    }

    /**
     * update a TasksApp.Data.Contract.Task
     * @param taskToUpdate TasksApp.Data.Contract.Task to update
     */
    public void update(Task taskToUpdate){
        beginConnection();
        try {
            em.merge(taskToUpdate);
        } catch (Exception exp) {
            log.info("Error on update method\nMessage: " + exp.getMessage() + "\nStacktrace: " + getStringStackTrace(exp).toString());
        } finally {
            ApplyRequest();
        }
    }

    /**
     * Delete a TasksApp.Data.Contract.Task
     * @param taskToDelete TasksApp.Data.Contract.Task to delete
     */
    public void Delete(Task taskToDelete){
        beginConnection();
        try {
            em.remove(taskToDelete);
        } catch (Exception exp) {
            log.info("Error on Delete method\nMessage: " + exp.getMessage() + "\nStacktrace: " + getStringStackTrace(exp).toString());
        } finally {
            ApplyRequest();
        }
    }
    //endregion

    //region Instance
    private static TaskDAO DAOInstance = new TaskDAO();

    /**
     * Initialize an instance of TasksApp.Data.TaskDAO
     * @return A TasksApp.Data.TaskDAO Object instance
     */
    public static TaskDAO getDAOInstance() {
        return DAOInstance;
    }
    //endregion

    //region Constructor
    /**
     * Private constructor. Initialize the connection.
     */
    private TaskDAO(){
        log = Logger.getLogger(TaskDAO.class.getName());
        try {
            InitConnection();
        } catch (Exception exp){
            log.info("Error on TaskDAO constructor\nMessage: " + exp.getMessage() + "\nStacktrace: " + getStringStackTrace(exp).toString());
        }

    }
    //endregion

    //region Destructor
    /**
     * Close the connection when garbage collector fire
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        try {
            destroyConnection();
        } catch (Exception exp){
            log.info("Error on TaskDAO destructor\nMessage: " + exp.getMessage() + "\nStacktrace: " + getStringStackTrace(exp).toString());
        }
        super.finalize();
    }
    //endregion

    //region Behavior methods
    /**
     * Print the exception into a StringBuilder
     * @param exp Exception to transform into string
     * @return Exception transform into StringBuilder
     */
    public static StringBuilder getStringStackTrace(Exception exp){
        StringBuilder sb = new StringBuilder();

        StackTraceElement[] elements = exp.getStackTrace();
        for (int i = 1; i < elements.length; i++) {
            StackTraceElement s = elements[i];
            sb.append("\tat " + s.getClassName() + "." + s.getMethodName()
                    + "(" + s.getFileName() + ":" + s.getLineNumber() + ")\n");
        }

        return sb;
    }
    //endregion
}
