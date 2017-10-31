package com.fiveware.random;

import com.fiveware.exception.RecoverableException;
import com.fiveware.exception.RuntimeBotException;
import com.fiveware.exception.UnRecoverableException;

public interface ErrorRandom {

	public void throwError() throws RecoverableException, UnRecoverableException, RuntimeBotException;
}
