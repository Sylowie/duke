package command;

import storage.Storage;
import tasklist.TaskList;
import ui.Ui;
import util.DukeException;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException;

    public boolean isExit() {
        return false;
    }
}
