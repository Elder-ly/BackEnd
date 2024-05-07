package sptech.elderly.util;

import java.io.StringWriter;
import java.util.List;

public class ListUtils {
    public static String mapToString(List<String> list) {
        if (list.isEmpty()) {
            return "";
        }
        String string = "";
        for (int i = 0; i < list.size() - 1; i++) {
            string.concat(list.get(i) + ",");
        }
        return string.concat(list.get(list.size() - 1));
    }
}
