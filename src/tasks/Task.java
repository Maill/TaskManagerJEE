package tasks;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Task {

    //region Attributes
    @Id
    @GeneratedValue
    private int id;
    private String title;
    private String description;
    private Date scheduledDate;
    private boolean isUrgent;
    //endregion

    //region Constructors
    public Task(){
        super();
    }

    public Task(String title, String description, Date scheduledDate, boolean isUrgent) {
        this.title = title;
        this.description = description;
        this.scheduledDate = scheduledDate;
        this.isUrgent = isUrgent;
    }
    //endregion

    //region Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public boolean isUrgent() {
        return isUrgent;
    }
    //endregion

    //region Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public void setUrgent(boolean urgent) {
        isUrgent = urgent;
    }
    //endregion
}
