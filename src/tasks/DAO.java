package tasks;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DAO {

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
            emf = Persistence.createEntityManagerFactory("BaseTask", null);
            em = emf.createEntityManager();
            tx = em.getTransaction();
        } catch (Exception exp) {
            throw exp;
        }

    }

    /**
     * Open the connection for data request
     */
    private void BeginConnection(){
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
    private void DestroyConnection(){
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
     * Store a new tasks.Task
     * @param taskToCreate tasks.Task to create
     */
    public void Create(Task taskToCreate){
        BeginConnection();
        try {
            em.persist(taskToCreate);
        } catch (Exception exp) {
            log.info("Error on Create method\nMessage: " + exp.getMessage() + "\nStacktrace: " + getStringStackTrace(exp).toString());
        } finally {
            ApplyRequest();
        }
    }

    /**
     * Gather Tasks from the database
     * @param id Specify the id of the tasks.Task or -1 to get all the records
     * @return A list of tasks.Task
     */
    public List<Task> Read(int id){
        List<Task> toReturn = new ArrayList<>();
        BeginConnection();
        try {
            if(id == -1){
                toReturn.addAll(em.createQuery("FROM Task t").getResultList());
            } else {
                toReturn.add(em.find(Task.class, id));
            }
        } catch (Exception exp) {
            log.info("Error on Read method\nMessage: " + exp.getMessage() + "\nStacktrace: " + getStringStackTrace(exp).toString());
        } finally {
            ApplyRequest();
        }

        return toReturn;
    }

    /**
     * Update a tasks.Task
     * @param taskToUpdate tasks.Task to update
     */
    public void Update(Task taskToUpdate){
        BeginConnection();
        try {
            em.merge(taskToUpdate);
        } catch (Exception exp) {
            log.info("Error on Update method\nMessage: " + exp.getMessage() + "\nStacktrace: " + getStringStackTrace(exp).toString());
        } finally {
            ApplyRequest();
        }
    }

    /**
     * Delete a tasks.Task
     * @param taskToDelete tasks.Task to delete
     */
    public void Delete(Task taskToDelete){
        BeginConnection();
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
    private static DAO DAOInstance = new DAO();

    /**
     * Initialize an instance of tasks.DAO
     * @return A tasks.DAO Object instance
     */
    public static DAO getDAOInstance() {
        return DAOInstance;
    }
    //endregion

    //region Constructor
    /**
     * Private constructor. Initialize the connection.
     */
    private DAO(){
        log = Logger.getLogger(DAO.class.getName());
        try {
            InitConnection();
        } catch (Exception exp){
            log.info("Error on DAO constructor\nMessage: " + exp.getMessage() + "\nStacktrace: " + getStringStackTrace(exp).toString());
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
            DestroyConnection();
        } catch (Exception exp){
            log.info("Error on DAO destructor\nMessage: " + exp.getMessage() + "\nStacktrace: " + getStringStackTrace(exp).toString());
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
    private StringBuilder getStringStackTrace(Exception exp){
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
