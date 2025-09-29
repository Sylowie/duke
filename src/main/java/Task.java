public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) throws DukeException {
        if (description == null || description.trim().isEmpty()) {
            throw new DukeException("Description cannot be empty.");
        }
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }

    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
