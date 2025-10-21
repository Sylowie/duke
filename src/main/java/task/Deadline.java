package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import util.DateTime;

public class Deadline extends Task {
    private final String byRaw; // store original user input string

    // Access raw string (used by Storage)
    public String getByRaw() {
        return byRaw;
    }

    // Constructor for user input (keeps raw string)
    public Deadline(String description, String byStr) {
        super(description);
        this.byRaw = byStr.trim(); // store raw without parsing
    }

    // Optional: constructor if given a LocalDateTime (e.g., from tests)
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.byRaw = by.format(DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
    }

    // ✅ Parse only when needed (lazy parsing)
    public LocalDateTime getParsedBy() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        return LocalDateTime.parse(byRaw, formatter);
    }

    @Override
    public String toString() {
        DateTimeFormatter displayFmt = DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm");
        return "[D]" + super.toString() + " (by: " + DateTime.formatRawDateTimeSafe(byRaw) + ")";
    }
}
