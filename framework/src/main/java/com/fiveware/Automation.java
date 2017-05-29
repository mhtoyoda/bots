package com.fiveware;

import com.fiveware.model.OutTextRecord;
import org.springframework.stereotype.Component;

/**
 * Created by valdisnei on 24/05/17.
 */
@Component
public interface Automation<T> {
	OutTextRecord execute(T recordLine);
}
