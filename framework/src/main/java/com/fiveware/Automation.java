package com.fiveware;

import java.io.Serializable;

import org.springframework.stereotype.Service;

/**
 * Created by valdisnei on 24/05/17.
 */
@Service
public interface Automation<T extends Serializable, R extends Serializable> {
	R execute(T recordLine);
}
