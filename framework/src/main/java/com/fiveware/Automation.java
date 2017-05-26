package com.fiveware;

import com.fiveware.model.OutTextRecord;

/**
 * Created by valdisnei on 24/05/17.
 */
public interface Automation<T> {
    	
	OutTextRecord execute(T recordLine);
}
