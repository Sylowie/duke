package command;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import storage.Storage;
import task.Task;
import tasklist.TaskList;
import ui.Ui;
import util.DukeException;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for {@link MarkCommand}.
 */
public class MarkCommandTest {

    /**
     * A stub Storage that does nothing to avoid writing files during tests.
     */
    static class StubStorage extends Storage {
        @Override
        public void save(List<Task> tasks) {
            // Do nothing for test
        }
    }

    /**
     * A stub Ui that does nothing to avoid console output during tests.
     */
    static class StubUi extends Ui {
        @Override
        public void showLine() {
            // Do nothing
        }
    }

    @Test
    public void testMarkCommand_marksCorrectTaskAsDone() throws DukeException {
        // Arrange: create a TaskList with one task
        TaskList taskList = new TaskList(new ArrayList<>());
        taskList.add(new Task("read book"));

        MarkCommand cmd = new MarkCommand(0); // mark the first task

        cmd.execute(taskList, new StubUi(), new StubStorage());

        Task task = taskList.get(0);
        assertTrue(task.isDone(), "Task should be marked as done after executing MarkCommand");
        assertEquals("[X] read book", task.toString(), "toString() should reflect marked status");
    }

    @Test
    public void testMarkCommand_invalidIndex_throwsException() {
        TaskList taskList = new TaskList(new ArrayList<>());

        MarkCommand cmd = new MarkCommand(5); // invalid index
        assertThrows(DukeException.class, () -> cmd.execute(taskList, new StubUi(), new StubStorage()),
                "Should throw DukeException for invalid index");
    }
}
