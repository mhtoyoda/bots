package com.fiveware;

import java.io.Serializable;

import com.fiveware.model.OutTextRecord;

/**
 * Created by valdisnei on 24/05/17.
 */
public interface Automation<T extends Serializable> {
    	
	OutTextRecord execute(T recordLine);
}
