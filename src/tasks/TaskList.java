package tasks;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@WebServlet(name = "tasks.TaskList")
public class TaskList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DAO dao = DAO.getDAOInstance();

            Task task = new Task("JEE Project", "A project about CRUD tasks", Date.from(Instant.now()), false);

            dao.Create(task);

            List<Task> rr = dao.Read(-1);

            Task taskFroDatabase = rr.get(0);

            taskFroDatabase.setTitle("JEE Project Update");
            dao.Update(taskFroDatabase);

            dao.Delete(taskFroDatabase);
        } catch(Exception exp) {
            System.out.println(exp.getMessage());
            System.out.println(exp.getStackTrace());
        } finally {
            response.setStatus(HttpServletResponse.SC_OK);
        }

    }
}
