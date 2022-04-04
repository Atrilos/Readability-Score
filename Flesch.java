package readability;

public class Flesch implements Strategy {

    @Override
    public double compute(Input input) {
        return 0.39 * (input.words / input.sentences)
                + 11.8 * (input.syllables / input.words) -15.59;
    }
}
