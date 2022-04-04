package readability;

public class Context {

    private Strategy strategy;

    Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void runApp(Input input) {
        switch(input.testMode) {
            case ARI -> {
                setStrategy(new AutoIndex());
                executeStrategy(input);
            }
            case FK -> {
                setStrategy(new Flesch());
                executeStrategy(input);
            }
            case SMOG -> {
                setStrategy(new SMOG());
                executeStrategy(input);
            }
            case CL -> {
                setStrategy(new Coleman());
                executeStrategy(input);
            }
            case ALL -> executeAll(input);
        }
    }

    private void executeStrategy(Input input) {
        double score = this.strategy.compute(input);
        switch (strategy.getClass().toString()) {
            case "class readability.AutoIndex" -> System.out.printf("Automated Readability Index: %.2f (about %d-year-olds).%n",
                    score, recommendedAge((int) Math.ceil(score)));
            case "class readability.Flesch" -> System.out.printf("Flesch–Kincaid readability tests: %.2f (about %d-year-olds).%n",
                    score, recommendedAge((int) Math.ceil(score)));
            case "class readability.SMOG" -> System.out.printf("Simple Measure of Gobbledygook: %.2f (about %d-year-olds).%n",
                    score, recommendedAge((int) Math.ceil(score)));
            case "class readability.Coleman" -> System.out.printf("Coleman–Liau index: %.2f (about %d-year-olds).%n",
                    score, recommendedAge((int) Math.ceil(score)));
        }
    }

    private void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    private void executeAll(Input input) {
        double mean = recommendedAge((int) Math.ceil(this.strategy.compute(input)));
        executeStrategy(input);
        setStrategy(new Flesch());
        mean += recommendedAge((int) Math.ceil(this.strategy.compute(input)));
        executeStrategy(input);
        setStrategy(new SMOG());
        mean += recommendedAge((int) Math.ceil(this.strategy.compute(input)));
        executeStrategy(input);
        setStrategy(new Coleman());
        mean += recommendedAge((int) Math.ceil(this.strategy.compute(input)));
        executeStrategy(input);
        System.out.printf("%nThis text should be understood in average by %.2f-year-olds.",
                mean / 4);
    }

    private int recommendedAge(int roundedScore) {
        int temp;
        switch (roundedScore) {
            case 1 -> temp = 6;
            case 2 -> temp = 7;
            case 3 -> temp = 9;
            case 4 -> temp = 10;
            case 5 -> temp = 11;
            case 6 -> temp = 12;
            case 7 -> temp = 13;
            case 8 -> temp = 14;
            case 9 -> temp = 15;
            case 10 -> temp = 16;
            case 11 -> temp = 17;
            case 12 -> temp = 18;
            case 13 -> temp = 24;
            case 14 -> temp = 24;
            default -> temp = 24;
        }
        return temp;
    }
}
