package sy;

import java.util.*;

import task.Deadline;
import task.Event;
import task.Task;
import task.Todo;
import util.DukeException;
// import ui.Ui;
import db.Db;

public class Sy {

    private final Db storage = new Db();
    // private final Ui ui = new Ui();

    private final List<Task> tasks = new ArrayList<>(); // dynamic

    public Sy() throws DukeException {
        this.tasks.addAll(storage.load()); // load on startup
    }

    public static void main(String[] args) {
        try {
            new Sy().run();
        } catch (DukeException e) {
            System.out.println("Failed to start: " + e.getMessage());
        }
    }

    private void run() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Hello! I'm Sy\n" + "What can I do for you?\n");

        while (true) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println("___________________________");
                System.out.println("      " + "Bye! And don't come again!");
                System.out.println("___________________________");
                sc.close();
                break;
            }

            try {
                handle(input);
                storage.save(tasks);
            } catch (DukeException e) {
                System.out.println("___________________________");
                System.out.println("      " + e.getMessage());
                System.out.println("___________________________");
            }
        }
    }

    private void handle(String input) throws DukeException {
        if (input.isEmpty()) {
            return;
        }

        if (input.startsWith("todo ")) {
            String description = input.substring(5).trim();
            if (description.isEmpty()) {
                throw new DukeException("todo what?");
            } else {
                addTodo(description);
            }

        } else if (input.startsWith("deadline ")) {
            String body = input.substring(9);
            String[] parts = body.split("/by", 2);
            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                throw new DukeException("deadline for what?");
            } else {
                addDeadline(parts[0].trim(), parts[1].trim());
            }

        } else if (input.startsWith("event ")) {
            String body = input.substring(6);
            String[] p1 = body.split("/from", 2);
            if (p1.length < 2 || p1[0].trim().isEmpty()) {
                throw new DukeException("what event?");
            } else {
                String desc = p1[0].trim();
                String[] p2 = p1[1].split("/to", 2);
                if (p2.length < 2 || p2[0].trim().isEmpty() || p2[1].trim().isEmpty()) {
                    System.out.println("Usage: event <description> /from <start> /to <end>");
                    return;
                }
                addEvent(desc, p2[0].trim(), p2[1].trim());
            }

        } else if (input.equals("list")) {
            list();

        } else if (input.startsWith("mark ")) {
            String arg = input.substring(5).trim();
            mark(arg);

        } else if (input.startsWith("unmark ")) {
            String arg = input.substring(7).trim();
            unmark(arg);

        } else if (input.startsWith("delete ")) {
            String arg = input.substring(7).trim();
            delete(arg);

        } else {
            throw new DukeException(
                    "      Typo? :/\n"
                            + "      I dont think thats a command, perhaps todo, event, deadline? Or u're missing a space");
        }
    }

    private void addTodo(String description) throws DukeException {
        Todo t = new Todo(description);
        addTaskAndAcknowledge(t);
    }

    private void addDeadline(String description, String by) throws DukeException {
        Deadline d = new Deadline(description, by);
        addTaskAndAcknowledge(d);
    }

    private void addEvent(String description, String from, String to) throws DukeException {
        Event e = new Event(description, from, to);
        addTaskAndAcknowledge(e);
    }

    private void addTaskAndAcknowledge(Task t) {
        tasks.add(t);
        System.out.println("___________________________");
        System.out.println("     Got it. I've added this task:");
        System.out.println("       " + t);
        System.out.println("     Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("___________________________");
    }

    private void list() {
        System.out.println("___________________________");
        System.out.println("     Here are the tasks in your list:");
        if (tasks.isEmpty()) {
            System.out.println("     NOTHING!\n" + "     U're free... for the time being");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("     " + (i + 1) + "." + tasks.get(i));
            }
        }
        System.out.println("___________________________");
    }

    private void mark(String input) throws DukeException {
        try {
            int i = Integer.parseInt(input);
            if (i < 1 || i > tasks.size()) {
                throw new DukeException("No such task number: " + i);
            } else {
                Task n = tasks.get(i - 1);
                n.markDone();
                System.out.println("___________________________");
                System.out.println("     Nice! I've marked this task as done:");
                System.out.println("       " + n);
                System.out.println("___________________________");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please provide a valid task number to mark.");
        }
    }

    private void unmark(String input) throws DukeException {
        try {
            int i = Integer.parseInt(input);
            if (i < 1 || i > tasks.size()) {
                throw new DukeException("No such task number: " + i);
            }
            Task n = tasks.get(i - 1);
            n.markUndone();
            System.out.println("___________________________");
            System.out.println("     OK, I've marked this task as not done yet:");
            System.out.println("       " + n);
            System.out.println("___________________________");
        } catch (NumberFormatException e) {
            System.out.println("Please provide a valid task number to unmark.");
        }
    }

    private void delete(String input) throws DukeException {
        try {
            int i = Integer.parseInt(input);
            if (i < 1 || i > tasks.size()) {
                throw new DukeException("No such task number: " + i);
            }
            Task n = tasks.get(i - 1);
            tasks.remove(n);
            System.out.println("___________________________");
            System.out.println("     Noted. I've removed this task:");
            System.out.println("       " + n);
            System.out.println("___________________________");
        } catch (NumberFormatException e) {
            System.out.println("Please provide a valid task number to unmark.");
        }
    }
}