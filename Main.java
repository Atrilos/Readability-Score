package readability;

public class Main {

    public static void main(String[] args) {
        Input input = new Input(args);
        Context readability = new Context(new AutoIndex());
        readability.runApp(input);
    }
}
