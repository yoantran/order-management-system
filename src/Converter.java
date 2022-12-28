import java.text.DecimalFormat;

public class Converter {
    private static final String pattern = "#########.##########";
    private static final DecimalFormat Format = new DecimalFormat("#########.##########");

    public Converter() {
    }

    public static String toDecimal(double number) {
        return Format.format(number);
    }
}
