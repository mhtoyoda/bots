package com.fiveware;

import com.fiveware.file.RecordLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by valdisnei on 24/05/17.
 */
public class AutomationImpl implements Automation {

    static Logger logger = LoggerFactory.getLogger(AutomationImpl.class);

    @Override
    public RecordLine execute(RecordLine recordLine) {
        logger.info("recordline {}", recordLine);
        return recordLine;
    }
}
