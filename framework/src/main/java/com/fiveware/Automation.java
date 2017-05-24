package com.fiveware;

import org.springframework.stereotype.Service;

/**
 * Created by valdisnei on 23/05/17.
 */

@Service
public interface Automation {
    Object execute(Object object);
}
