package readability;

public class SMOG implements Strategy{

    @Override
    public double compute(Input input) {
        return 1.043
                * Math.sqrt(input.polysyllables * (30 / input.sentences))
                + 3.1291;
    }

}
