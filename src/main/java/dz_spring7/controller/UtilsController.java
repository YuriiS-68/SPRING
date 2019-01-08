package dz_spring7.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;

public class UtilsController<T> implements ControllerEntity<T> {
    private Class<T> type;

    @SuppressWarnings("unchecked")
    public UtilsController() {
        this.type = ((Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public T mappingObject(HttpServletRequest req)throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();

        String input = objectMapper.writeValueAsString(getString(req));
        return objectMapper.convertValue(input, type);
    }

    private String getString(HttpServletRequest req)throws IOException{
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader reader = req.getReader()){
            String line;

            while ((line = reader.readLine()) != null){
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }
}
