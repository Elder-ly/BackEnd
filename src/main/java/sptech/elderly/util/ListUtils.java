package sptech.elderly.util;

import sptech.elderly.entity.Especialidade;

import java.io.StringWriter;
import java.util.List;

public class ListUtils {
    public static String mapToString(List<Especialidade> list) {
        if (list.isEmpty()) {
            return "";
        }
        String string = "";
        for (int i = 0; i < list.size() - 1; i++) {
            string.concat(list.get(i).getNome() + ",");
        }
        return string.concat(list.get(list.size() - 1).getNome());
    }
}
