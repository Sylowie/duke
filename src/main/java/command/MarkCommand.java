package command;

import storage.Storage;
import task.Task;
import tasklist.TaskList;
import ui.Ui;
import util.DukeException;

public class MarkCommand extends Command {
    private final int idx; // zero-based

    public MarkCommand(int idx) {
        this.idx = idx;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (idx < 0 || idx >= tasks.size())
            throw new DukeException("No such task number: " + (idx + 1));
        Task t = tasks.get(idx);
        t.markDone();
        ui.showLine();
        System.out.println("     Nice! I've marked this task as done:");
        System.out.println("       " + t);
        ui.showLine();
        storage.save(tasks.asList());
    }
}
