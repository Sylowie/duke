package command;

import storage.Storage;
import task.Task;
import tasklist.TaskList;
import ui.Ui;
import util.DukeException;

public class UnmarkCommand extends Command {
    private final int idx;

    public UnmarkCommand(int idx) {
        this.idx = idx;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (idx < 0 || idx >= tasks.size())
            throw new DukeException("No such task number: " + (idx + 1));
        Task t = tasks.get(idx);
        t.markUndone();
        ui.showLine();
        System.out.println("     OK, I've marked this task as not done yet:");
        System.out.println("       " + t);
        ui.showLine();
        storage.save(tasks.asList());
    }
}
