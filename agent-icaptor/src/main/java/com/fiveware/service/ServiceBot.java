package com.fiveware.service;

import com.fiveware.exception.ExceptionBot;
import com.fiveware.model.OutTextRecord;
import org.springframework.stereotype.Service;

/**
 * Created by valdisnei on 5/31/17.
 */

@Service
public interface ServiceBot<T> {
    OutTextRecord callBot(String nameBot, T parameter) throws ExceptionBot;
    OutTextRecord callBot(String nameBot, String endpoint, T parameter) throws ExceptionBot;
}
