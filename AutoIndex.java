package readability;

public class AutoIndex implements Strategy {

    @Override
    public double compute(Input input) {
        return 4.71 * input.characters / input.words
                + 0.5 * input.words / input.sentences - 21.43;
    }

}
