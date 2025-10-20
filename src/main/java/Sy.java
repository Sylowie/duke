import java.util.*;

public class Sy {

    private static ArrayList<Task> Tasks = new ArrayList<>(); // dynamic

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Hello! I'm Sy\n" + "What can I do for you?\n");

        while (true) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println("___________________________");
                System.out.println("      " + "Bye! And don't come again!");
                System.out.println("___________________________");
                break;
                // sc.close();
            }

            try {
                handle(input);
            } catch (DukeException e) {
                System.out.println("___________________________");
                System.out.println("      " + e.getMessage());
                System.out.println("___________________________");
            }
        }
    }

    private static void handle(String input) throws DukeException {
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

    private static void addTodo(String description) throws DukeException {
        Todo t = new Todo(description);
        addTaskAndAcknowledge(t);
    }

    private static void addDeadline(String description, String by) throws DukeException {
        Deadline d = new Deadline(description, by);
        addTaskAndAcknowledge(d);
    }

    private static void addEvent(String description, String from, String to) throws DukeException {
        Event e = new Event(description, from, to);
        addTaskAndAcknowledge(e);
    }

    private static void addTaskAndAcknowledge(Task t) {
        Tasks.add(t);
        System.out.println("___________________________");
        System.out.println("     Got it. I've added this task:");
        System.out.println("       " + t);
        System.out.println("     Now you have " + Tasks.size() + " tasks in the list.");
        System.out.println("___________________________");
    }

    private static void list() {
        System.out.println("___________________________");
        System.out.println("     Here are the tasks in your list:");
        if (Tasks.isEmpty()) {
            System.out.println("     NOTHING!\n" + "     U're free... for the time being");
        } else {
            for (int i = 0; i < Tasks.size(); i++) {
                System.out.println("     " + (i + 1) + "." + Tasks.get(i));
            }
        }
        System.out.println("___________________________");
    }

    private static void mark(String input) throws DukeException {
        try {
            int i = Integer.parseInt(input);
            if (i < 1 || i > Tasks.size()) {
                throw new DukeException("No such task number: " + i);
            } else {
                Task n = Tasks.get(i - 1);
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

    private static void unmark(String input) throws DukeException {
        try {
            int i = Integer.parseInt(input);
            if (i < 1 || i > Tasks.size()) {
                throw new DukeException("No such task number: " + i);
            }
            Task n = Tasks.get(i - 1);
            n.markUndone();
            System.out.println("___________________________");
            System.out.println("     OK, I've marked this task as not done yet:");
            System.out.println("       " + n);
            System.out.println("___________________________");
        } catch (NumberFormatException e) {
            System.out.println("Please provide a valid task number to unmark.");
        }
    }

    private static void delete(String input) throws DukeException {
        try {
            int i = Integer.parseInt(input);
            if (i < 1 || i > Tasks.size()) {
                throw new DukeException("No such task number: " + i);
            }
            Task n = Tasks.get(i - 1);
            Tasks.remove(n);
            System.out.println("___________________________");
            System.out.println("     Noted. I've removed this task:");
            System.out.println("       " + n);
            System.out.println("___________________________");
        } catch (NumberFormatException e) {
            System.out.println("Please provide a valid task number to unmark.");
        }
    }
}