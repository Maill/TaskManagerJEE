package tasks;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/TaskList")
public class TaskList extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

    public TaskList() 
    {
        super();
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
			DAO myDao = DAO.getDAOInstance();

			List<Task> allTasks = myDao.Read(-1);

			if(onlyUrgent)
            {
                List<Task> urgentTasks = new ArrayList<>();

                for(int i = 0; i < allTasks.size(); i++)
                {
                    if(allTasks.get(i).isUrgent())
                    {
                        urgentTasks.add(allTasks.get(i));
                    }
                }

                request.setAttribute("urgent", true);
                request.setAttribute("tasks", urgentTasks);
            }

            else
            {
                request.setAttribute("urgent", false);
                request.setAttribute("tasks", allTasks);
            }
		}
		catch(Exception exp)
		{
			System.out.println(exp.getMessage());
			System.out.println(DAO.getStringStackTrace(exp));
		}
		finally
		{
			response.setStatus(HttpServletResponse.SC_OK);

            this.getServletContext().getRequestDispatcher("/WEB-INF/task_list.jsp").forward(request, response);
		}
	}
}
