package tasks;

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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        this.getServletContext().getRequestDispatcher("/WEB-INF/task_creation.jsp").forward(request, response);
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

            Task task = new Task(request.getParameter("taskName"), request.getParameter("taskDescription"), calendar.getTime(), request.getParameter("taskUrgency") != null);

            dao.Create(task);

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
            this.getServletContext().getRequestDispatcher("/WEB-INF/task_creation.jsp").forward(request, response);
        }
    }
}
