package TasksApp.Views;

import TasksApp.Data.Contract.Task;
import TasksApp.Data.TaskDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

@WebServlet("/TaskCreation")
public class TaskCreation extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public TaskCreation()
    {
        super();
    }

    /**
     * Action on HTTP GET call. / Sets the WEB page to show
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        this.getServletContext().getRequestDispatcher("/WEB-INF/task_creation.jsp").forward(request, response);
    }

    /**
     * Action on HTTP POST call. / create the task from form to the database
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(request.getParameter("taskYear")),
                Integer.parseInt(request.getParameter("taskMonth")),
                Integer.parseInt(request.getParameter("taskDay")),
                Integer.parseInt(request.getParameter("taskHour")),
                Integer.parseInt(request.getParameter("taskMinute")), 0);

        try
        {
            TaskDAO dao = TaskDAO.getDAOInstance();

            Task task = new Task(request.getParameter("taskName"), request.getParameter("taskDescription"), calendar.getTime(), request.getParameter("taskUrgency") != null);

            dao.create(task);

            request.setAttribute("success", true);
        }
        catch(Exception exp)
        {
            request.setAttribute("success", false);

            System.out.println(exp.getMessage());
            System.out.println(TaskDAO.getStringStackTrace(exp));
        }
        finally
        {
            this.getServletContext().getRequestDispatcher("/WEB-INF/task_creation.jsp").forward(request, response);
        }
    }
}
