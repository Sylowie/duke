package command;

import storage.Storage;
import task.Task;
import tasklist.TaskList;
import ui.Ui;
import util.DukeException;
import java.util.*;

// public class MarkCommand extends Command {
//     private final int idx; // zero-based

//     public MarkCommand() {
//         this.idx = -1;
//     }

//     public MarkCommand(int idx) {
//         this.idx = idx;
//     }

//     @Override
//     public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
//         if (idx < 0 || idx >= tasks.size())
//             throw new DukeException("No such task number: " + (idx + 1));
//         Task t = tasks.get(idx);
//         t.markDone();
//         ui.showLine();
//         System.out.println("     Nice! I've marked this task as done:");
//         System.out.println("       " + t);
//         ui.showLine();
//         storage.save(tasks.asList());
//     }
// }

public class MarkCommand extends Command {
    // stores input of task number 1 2 3 etc
    private final String raw;

    public MarkCommand(String rawArgs) {
        if (rawArgs == null) {
            this.raw = "";
        } else {
            this.raw = rawArgs.trim();
        }
    }

    private List<Integer> parseZeroBased() throws DukeException {
        if (raw.isEmpty()) {
            throw new DukeException("What task??");
        }
        String[] Inputs = raw.split("\\s+");
        LinkedHashSet<Integer> set = new LinkedHashSet<Integer>(); // LinkedHashSet to remove Duplicates
        for (String IndividualInputs : Inputs) {
            int n;
            try {
                n = Integer.parseInt(IndividualInputs);
            } catch (NumberFormatException e) {
                throw new DukeException("Not a valid task number: " + IndividualInputs);
            }
            if (n <= 0)
                throw new DukeException("Task numbers must be positive: " + IndividualInputs);
            set.add(n - 1);
        }
        return new ArrayList<Integer>(set);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        List<Integer> idxs = parseZeroBased();

        for (Integer i : idxs) {
            if (i < 0 || i >= tasks.size())
                throw new DukeException("No such task number: " + (i + 1));
        }

        List<Task> changed = new ArrayList<Task>(idxs.size());
        for (Integer i : idxs) {
            Task t = tasks.get(i);
            t.markDone();
            changed.add(t);
        }

        ui.showLine();
        System.out.println("     Nice! I've marked these task(s) as done:");
        for (Task t : changed)
            System.out.println("       " + t);
        ui.showLine();
        storage.save(tasks.asList());
    }
}