package tasks;

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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        DAO dao = DAO.getDAOInstance();

        List<Task> taskList = dao.Read(Integer.parseInt(request.getParameter("task")));

        Task task = taskList.get(0);

        request.setAttribute("task", task);

        this.getServletContext().getRequestDispatcher("/WEB-INF/task_modification.jsp").forward(request, response);
    }

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
            DAO dao = DAO.getDAOInstance();

            List<Task> taskList = dao.Read(Integer.parseInt(request.getParameter("taskID")));

            Task task = taskList.get(0);

            request.setAttribute("task", task);

            task.setTitle(request.getParameter("taskName"));
            task.setDescription(request.getParameter("taskDescription"));
            task.setScheduledDate(calendar.getTime());
            task.setUrgent(request.getParameter("taskUrgency") != null);

            dao.Update(task);

            request.setAttribute("success", true);
        }
        catch(Exception exp)
        {
            request.setAttribute("success", false);

            System.out.println(exp.getMessage());
            System.out.println(exp.getStackTrace());
        }
        finally
        {
            this.getServletContext().getRequestDispatcher("/WEB-INF/task_modification.jsp").forward(request, response);
        }
    }
}
