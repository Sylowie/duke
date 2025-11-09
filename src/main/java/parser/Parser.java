package parser;

import command.Command;
import command.AddDeadlineCommand;
import command.AddEventCommand;
import command.AddTodoCommand;
import command.DeleteCommand;
import command.ExitCommand;
import command.ListCommand;
import command.MarkCommand;
import command.UnmarkCommand;
import command.FindCommand;
import util.DukeException;

public class Parser {
    public static Command parse(String input) throws DukeException {
        if (input == null || input.trim().isEmpty()) {
            throw new DukeException("Empty command.");
        }
        String s = input.trim();
        // assert given that if statement already covers
        assert s != null : "Trimmed input should not be null";
        assert !s.isEmpty() : "Trimmed input should not be empty";
        s = s.toLowerCase();

        if (s.equals("bye"))
            return new ExitCommand();
        if (s.equals("list"))
            return new ListCommand();

        if (s.startsWith("todo ")) {
            String desc = s.substring(5).trim();
            assert desc != null : "todo description should never be null";
            if (desc.isEmpty())
                throw new DukeException("todo what?");
            return new AddTodoCommand(desc);
        }

        if (s.startsWith("deadline ")) {
            String body = s.substring(9);
            String[] parts = body.split("/by", 2);
            assert parts != null && parts.length > 0 : "Splitting deadline must produce parts";
            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                throw new DukeException("deadline by? Usage: deadline <description> /by <date>");
            }
            return new AddDeadlineCommand(parts[0].trim(), parts[1].trim());
        }

        if (s.startsWith("event ")) {
            String body = s.substring(6);
            String[] p1 = body.split("/from", 2);
            assert p1 != null && p1.length > 0 : "Event split by /from should produce parts";
            if (p1.length < 2 || p1[0].trim().isEmpty())
                throw new DukeException("what event?");
            String desc = p1[0].trim();
            String[] p2 = p1[1].split("/to", 2);
            if (p2.length < 2 || p2[0].trim().isEmpty() || p2[1].trim().isEmpty()) {
                throw new DukeException("Usage: event <description> /from <start> /to <end>");
            }
            return new AddEventCommand(desc, p2[0].trim(), p2[1].trim());
        }

        if (s.startsWith("mark ")) {
            String idxs = s.substring(5).trim();
            return new MarkCommand(idxs);
        }

        if (s.startsWith("unmark ")) {
            String idxs = s.substring(7).trim();
            return new UnmarkCommand(idxs);
        }

        if (s.startsWith("delete ")) {
            int idx = parseIndex(s.substring(7).trim());
            return new DeleteCommand(idx);
        }

        if (s.startsWith("find ")) {
            String desc = s.substring(5).trim();
            return new FindCommand(desc);
        }

        throw new DukeException("Typo?");
    }

    private static int parseIndex(String s) throws DukeException {
        try {
            int i = Integer.parseInt(s);
            if (i < 1)
                throw new DukeException("Index must be >= 1");
            return i - 1;
        } catch (NumberFormatException e) {
            throw new DukeException("Please provide a valid task number.");
        }
    }
}
