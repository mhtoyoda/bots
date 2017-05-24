package com.fiveware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by valdisnei on 23/05/17.
 */
@Component
public class AutomationImpl implements Automation{

    Logger logger = LoggerFactory.getLogger(AutomationImpl.class);


    @Override
    public Object execute(Object object) {
        logger.info("teste {}",object);

        return object;
    }
}
