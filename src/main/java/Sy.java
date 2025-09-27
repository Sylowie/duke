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
                System.out.println("      " + "Bye!");
                System.out.println("___________________________");
                break;
            }
            handle(input);
        }
    }

    private static void handle(String input) {
        if (input.isEmpty())
            return;

        if (input.startsWith("todo ")) {
            String description = input.substring(5).trim();
            addTodo(description);

        } else if (input.startsWith("deadline ")) {
            // format: deadline <desc> /by <when>
            String body = input.substring(9);
            String[] parts = body.split("/by", 2);
            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                System.out.println("Usage: deadline <description> /by <when>");
                return;
            }
            addDeadline(parts[0].trim(), parts[1].trim());

        } else if (input.startsWith("event ")) {
            // format: event <desc> /from <start> /to <end>
            String body = input.substring(6);
            String[] p1 = body.split("/from", 2);
            if (p1.length < 2 || p1[0].trim().isEmpty()) {
                System.out.println("Usage: event <description> /from <start> /to <end>");
                return;
            }
            String desc = p1[0].trim();
            String[] p2 = p1[1].split("/to", 2);
            if (p2.length < 2 || p2[0].trim().isEmpty() || p2[1].trim().isEmpty()) {
                System.out.println("Usage: event <description> /from <start> /to <end>");
                return;
            }
            addEvent(desc, p2[0].trim(), p2[1].trim());

        } else if (input.equals("list")) {
            list();

        } else if (input.startsWith("mark ")) {
            String arg = input.substring(5).trim();
            mark(arg);

        } else if (input.startsWith("unmark ")) {
            String arg = input.substring(7).trim();
            unmark(arg);

        } else {
            System.out.println("Unknown command: " + input);
        }
    }

    private static void addTodo(String description) {
        Todo t = new Todo(description);
        addTaskAndAcknowledge(t);
    }

    private static void addDeadline(String description, String by) {
        Deadline d = new Deadline(description, by);
        addTaskAndAcknowledge(d);
    }

    private static void addEvent(String description, String from, String to) {
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
        for (int i = 0; i < Tasks.size(); i++) {
            System.out.println("     " + (i + 1) + "." + Tasks.get(i));
        }
        System.out.println("___________________________");
    }

    private static void mark(String input) {
        try {
            int i = Integer.parseInt(input);
            if (i < 1 || i > Tasks.size()) {
                System.out.println("No such task number: " + i);
                return;
            }
            Task n = Tasks.get(i - 1);
            n.markDone();
            System.out.println("___________________________");
            System.out.println("     Nice! I've marked this task as done:");
            System.out.println("       " + n);
            System.out.println("___________________________");
        } catch (NumberFormatException e) {
            System.out.println("Please provide a valid task number to mark.");
        }
    }

    private static void unmark(String input) {
        try {
            int i = Integer.parseInt(input);
            if (i < 1 || i > Tasks.size()) {
                System.out.println("No such task number: " + i);
                return;
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
}