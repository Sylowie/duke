import java.util.*;

public class Sy {

    private static ArrayList<Task> Tasks = new ArrayList<>(); // dynamic

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Hello! I'm Sy\n" + "What can I do for you?\n" + "Bye. Hope to see you again!");

        while (true) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println("___________________________");
                System.out.println("      " + "Bye!");
                System.out.println("___________________________");
                break;

            } else if (input.equals("list")) {
                System.out.println("___________________________");
                for (int i = 0; i < Tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + Tasks.get(i)); // i+1 cus initial is 0
                }
                System.out.println("___________________________");

            } else if (input.startsWith("mark ")) {
                int i = Integer.parseInt(input.substring(5).trim());
                Task n = Tasks.get(i - 1);
                n.markDone();
                System.out.println("\n Nice! I've marked this task as done: ");
                System.out.println(" " + n);
                System.out.println("___________________________");

            } else if (input.startsWith("unmark ")) {
                int i = Integer.parseInt(input.substring(7).trim());
                Task n = Tasks.get(i - 1);
                n.markUndone();
                System.out.println("\n OK, I've marked this task as not done yet:");
                System.out.println(" " + n);
                System.out.println("___________________________");

            } else {
                Task t = new Task(input);
                Tasks.add(t);
                System.out.println("___________________________");
                System.out.println("added: " + input);
                System.out.println("___________________________");
            }
        }
    }
}
