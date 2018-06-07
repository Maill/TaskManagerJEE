package tasks;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/TaskSuppression")
public class TaskSuppression extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public TaskSuppression()
    {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(request.getParameter("remove") != null)
        {
            try
            {
                DAO dao = DAO.getDAOInstance();

                List<Task> taskList = dao.Read(Integer.parseInt(request.getParameter("remove")));

                Task task = taskList.get(0);

                dao.Delete(task);

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