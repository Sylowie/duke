package sy;

import parser.Parser;
import command.Command;
import storage.Storage;
import tasklist.TaskList;
import ui.Ui;
import util.DukeException;

/**
 * {@code Sy} class is the main entry point of the chatbot
 * application.
 * initializes core components (UI, Storage, TaskList,
 * and Parser),
 * running the main interaction loop, handling user input, and executing
 * commands.
 * 
 * Usage: Run this class to start the chatbot:
 * {@code java sy.Sy}
 */
public class Sy {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

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

    /*
     * Runs Duke
     * This method shows a welcome message then enters a loop where it continuously
     * reads commands from the user and executes them.
     * If the user enters "bye" command, the loop will terminate and exit
     * application
     * If the user enters an invalid command or error occurs, an error message will
     * be displayed.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                if (fullCommand == null) {
                    ui.showGoodbye();
                    break;
                }
                fullCommand = fullCommand.trim();
                if (fullCommand.isEmpty()) {
                    ui.showLine();
                    continue;
                }

                ui.showLine();
                Command c = Parser.parse(fullCommand); // may throw DukeException
                c.execute(tasks, ui, storage); // may throw DukeException
                isExit = c.isExit();

            } catch (DukeException e) {
                ui.showError(e.getMessage());

            } catch (Exception e) { // ✅ unexpected errors
                ui.showError("Oops, something went wrong: " + e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Starts Duke application
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        new Sy().run();
    }
}
