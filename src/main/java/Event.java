public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) throws DukeException {
        super(description);
        if (from == null || from.trim().isEmpty() || to == null || to.trim().isEmpty()) {
            throw new DukeException("Event requires both /from <start> and /to <end>.");
        }
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
