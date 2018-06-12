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
                DAO dao = DAO.getDAOInstance();

                List<Task> taskList = dao.read(Integer.parseInt(request.getParameter("remove")));

                Task task = taskList.get(0);

                dao.Delete(task);

                request.setAttribute("success", true);
            }
            catch(Exception exp)
            {
                request.setAttribute("success", false);

                System.out.println(exp.getMessage());
                System.out.println(DAO.getStringStackTrace(exp));
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
