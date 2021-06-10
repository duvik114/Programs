package expression.evaluation;

public class DoubleEvaluation implements Evaluation<Double> {

    @Override
    public Double andT(Double t1, Double t2) throws NumberFormatException {
        return Double.parseDouble (String.valueOf(Integer.parseInt(String.valueOf(t1)) & Integer.parseInt(String.valueOf(t2))));
    }

    @Override
    public Double orT(Double t1, Double t2) throws NumberFormatException {
        return Double.parseDouble (String.valueOf(Integer.parseInt(String.valueOf(t1)) | Integer.parseInt(String.valueOf(t2))));
    }

    @Override
    public Double xorT(Double t1, Double t2) throws NumberFormatException {
        return Double.parseDouble (String.valueOf(Integer.parseInt(String.valueOf(t1)) ^ Integer.parseInt(String.valueOf(t2))));
    }

    @Override
    public Double addT(Double t1, Double t2) throws NumberFormatException {
        return t1 + t2;
    }

    @Override
    public Double constT(int i) {
        return (double) i;
    }

    @Override
    public Double divT(Double t1, Double t2) throws NumberFormatException {
        return t1 / t2;
    }

    @Override
    public Double mulT(Double t1, Double t2) throws NumberFormatException {
        return t1 * t2;
    }

    @Override
    public Double subT(Double t1, Double t2) throws NumberFormatException {
        return t1 - t2;
    }

    @Override
    public Double invT(Double aDouble) throws NumberFormatException {
        return -aDouble;
    }
}
