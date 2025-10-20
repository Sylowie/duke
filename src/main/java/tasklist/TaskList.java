package tasklist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import task.Task;

public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> initial) {
        this.tasks = new ArrayList<>(initial);
    }

    public void add(Task t) {
        tasks.add(t);
    }

    public Task remove(int indexZeroBased) {
        return tasks.remove(indexZeroBased);
    }

    public Task get(int indexZeroBased) {
        return tasks.get(indexZeroBased);
    }

    public int size() {
        return tasks.size();
    }

    public List<Task> asList() {
        return Collections.unmodifiableList(tasks);
    }
}
