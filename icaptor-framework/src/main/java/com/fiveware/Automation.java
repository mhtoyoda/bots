package com.fiveware;

import com.fiveware.exception.ExceptionBot;
import com.fiveware.exception.Recoverable;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by valdisnei on 24/05/17.
 */
@Service
public interface Automation<T extends Serializable, R extends Serializable> {

	R execute(T recordLine) throws ExceptionBot, Recoverable;

}


