package TasksApp.Views;

import TasksApp.Data.Contract.Task;
import TasksApp.Data.TaskDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/TaskSuppression")
public class TaskSuppression extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    //region Logging attribute
    private Logger log;
    //endregion

    public TaskSuppression()
    {
        super();
        log = Logger.getLogger(TaskSuppression.class.getName());
    }

    /**
     * Action on HTTP GET call. / Remove the selected task and remove it form the database.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(request.getParameter("remove") != null)
        {
            try
            {
                TaskDAO dao = TaskDAO.getDAOInstance();

                List<Task> taskList = dao.read(Integer.parseInt(request.getParameter("remove")));

                Task task = taskList.get(0);

                dao.Delete(task);

                request.setAttribute("success", true);
            }
            catch(Exception exp)
            {
                request.setAttribute("success", false);

                log.log(Level.SEVERE,"Error in doGet TaskSuppression Servlet constructor\nMessage: " + exp.getMessage() + "\nStacktrace: " + TaskDAO.getStringStackTrace(exp).toString());
            }
            finally
            {
                this.getServletContext().getRequestDispatcher("/WEB-INF/task_suppression.jsp").forward(request, response);
            }
        }

        else
        {
            request.setAttribute("taskID", request.getParameter("task"));

            this.getServletContext().getRequestDispatcher("/WEB-INF/task_suppression.jsp").forward(request, response);
        }
    }
}
