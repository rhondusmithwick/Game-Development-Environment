package animation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class StringParser {

    private StringParser() {
    }

    public static List<Double> convertStringToDoubleList(String string) {
        String noBrackets = string.replace("[", "");
        noBrackets = noBrackets.replace("]", "");
        List<String> stringList = Arrays.asList(noBrackets.split("\\s*,\\s*"));
        return stringList.stream().map(Double::parseDouble).collect(Collectors.toList());
    }

}
