package abb.pfg.main.backend.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Usuario
 *
 */
public class TestUtils {

	public static String objectToJson(Object object) {
		ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String readTestFile(String filePath) {
        // Implementación para leer un archivo de prueba y devolver su contenido como una cadena
        // Utiliza las clases y métodos adecuados para leer el archivo
        return ""; // Devuelve el contenido del archivo como una cadena
    }
}
