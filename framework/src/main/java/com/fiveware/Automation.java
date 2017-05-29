package com.fiveware;

import com.fiveware.model.OutTextRecord;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by valdisnei on 24/05/17.
 */
@Service
public interface Automation<T extends Serializable> {
	OutTextRecord execute(T recordLine);
}
