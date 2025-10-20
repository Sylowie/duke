package sy;

import parser.Parser;
import command.Command;
import storage.Storage;
import tasklist.TaskList;
import ui.Ui;
import util.DukeException;

public class Sy {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Sy() {
        ui = new Ui();
        storage = new Storage();
        try {
            tasks = new TaskList(storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Sy().run();
    }
}
