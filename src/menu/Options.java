package menu;

public class Options {
    private static final String[] fields = new String[]{"Input", "Action"};
    private final String action;
    private final String input;
    private final Command command;

    public Options(String action, String input, Command command) {
        this.action = action;
        this.input = input;
        this.command = command;
    }
    public String getAction() {
        return action;
    }

    public String getInput() {
        return input;
    }

    public static String[] getFields() {
        return fields;
    }



    public void execute() {
        this.command.execute();
    }

    public String[] toStringArray() {
        return new String[]{this.input, this.action};
    }


}
