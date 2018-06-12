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
import java.util.List;

@WebServlet("/TaskModification")
public class TaskModification extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public TaskModification()
    {
        super();
    }

    /**
     * Action on HTTP GET call. / Sets the WEB page to show and get the task to edit
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        TaskDAO dao = TaskDAO.getDAOInstance();

        List<Task> taskList = dao.read(Integer.parseInt(request.getParameter("task")));

        Task task = taskList.get(0);

        request.setAttribute("task", task);

        this.getServletContext().getRequestDispatcher("/WEB-INF/task_modification.jsp").forward(request, response);
    }

    /**
     * Action on HTTP POST call. / Edit the task from the form and update it in the database.
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

            List<Task> taskList = dao.read(Integer.parseInt(request.getParameter("taskID")));

            Task task = taskList.get(0);

            request.setAttribute("task", task);

            task.setTitle(request.getParameter("taskName"));
            task.setDescription(request.getParameter("taskDescription"));
            task.setScheduledDate(calendar.getTime());
            task.setUrgent(request.getParameter("taskUrgency") != null);

            dao.update(task);

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
            this.getServletContext().getRequestDispatcher("/WEB-INF/task_modification.jsp").forward(request, response);
        }
    }
}
