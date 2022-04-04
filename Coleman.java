package readability;

public class Coleman implements Strategy {

    @Override
    public double compute(Input input) {
        return 0.0588 * input.l - 0.296 * input.s - 15.8;
    }

}
