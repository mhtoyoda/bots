package com.fiveware;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import com.fiveware.exception.AuthenticationBotException;
import com.fiveware.exception.RecoverableException;
import com.fiveware.exception.RuntimeBotException;
import com.fiveware.exception.UnRecoverableException;
import com.fiveware.parameter.ParameterValue;

@Service
public interface Automation<T extends Serializable, R extends Serializable> {

	R execute(T recordLine, ParameterValue parameters) throws RuntimeBotException, UnRecoverableException, RecoverableException, AuthenticationBotException;

}


