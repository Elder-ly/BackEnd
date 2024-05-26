package sptech.elderly.util;

import sptech.elderly.entity.Especialidade;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class ListUtils {
    public static String mapToString(List<Especialidade> list) {
        if (list.isEmpty()) {
            return "";
        }

        try(StringWriter stringEspecialidades = new StringWriter()) {
            for (int i = 0; i < list.size() - 1; i++) {
                stringEspecialidades.append(list.get(i).getNome()).append(",");
            }
            stringEspecialidades.append(list.get(list.size() - 1).getNome());
            return stringEspecialidades.toString();
        } catch (IOException e) {
            System.out.println("Erro ao listar especialidades");
            return "Erro ao listar especialidades";
        }
    }
}
