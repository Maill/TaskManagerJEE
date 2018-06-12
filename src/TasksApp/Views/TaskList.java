package TasksApp.Views;

import TasksApp.Data.Contract.Task;
import TasksApp.Data.TaskDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/TaskList")
public class TaskList extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    //region Logging attribute
    private Logger log;
    //endregion

    public TaskList() 
    {
        super();
        log = Logger.getLogger(TaskList.class.getName());
    }

    /**
     * Action on HTTP GET call. / Sets the WEB page to show and sets the data to show
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
        boolean onlyUrgent =  request.getParameter("urgent") != null;

        Date currentDate = new Date();

        request.setAttribute("currentDate", currentDate.getTime());

		try
		{
			TaskDAO myDao = TaskDAO.getDAOInstance();

			List<Task> allTasks = myDao.read(-1);

			if(onlyUrgent)
            {
                List<Task> urgentTasks = new ArrayList<>();

                for (Task task : allTasks) {
                    if (task.isUrgent()) {
                        urgentTasks.add(task);
                    }
                }

                request.setAttribute("urgent", true);
                request.setAttribute("TasksApp", urgentTasks);
            }

            else
            {
                request.setAttribute("urgent", false);
                request.setAttribute("TasksApp", allTasks);
            }
		}
		catch(Exception exp)
		{
		    log.log(Level.SEVERE,"Error in doGet TaskList Servlet constructor\nMessage: " + exp.getMessage() + "\nStacktrace: " + TaskDAO.getStringStackTrace(exp).toString());
		}
		finally
		{
			response.setStatus(HttpServletResponse.SC_OK);

            this.getServletContext().getRequestDispatcher("/WEB-INF/task_list.jsp").forward(request, response);
		}
	}
}
