package com.fiveware.dsl.pdf;

import java.util.Map;

/**
 * Created by valdisnei on 05/07/17.
 */
public interface Write {
    Write map(Map map, Class className);

    Object build();
}
