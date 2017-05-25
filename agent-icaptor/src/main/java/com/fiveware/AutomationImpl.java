package com.fiveware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiveware.file.RecordLine;

/**
 * Created by valdisnei on 24/05/17.
 */
public class AutomationImpl { 
	//implements Automation {

    static Logger logger = LoggerFactory.getLogger(AutomationImpl.class);

//    @Override
    public RecordLine execute(RecordLine recordLine) {
        logger.info("recordline {}", recordLine);
        return recordLine;
    }
}
