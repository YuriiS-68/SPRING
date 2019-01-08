package dz_spring7.controller;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ControllerEntity<T> {
    T mappingObject(HttpServletRequest req) throws IOException;
}
