package com.fiveware.service;

import org.springframework.stereotype.Service;

import com.fiveware.exception.ExceptionBot;
import com.fiveware.model.OutTextRecord;

/**
 * Created by valdisnei on 5/31/17.
 */
@Service
public interface ServiceBot<T> {
    OutTextRecord callBot(String nameBot, T parameter) throws ExceptionBot;
    OutTextRecord callBot(String nameBot, String endpoint, T parameter) throws ExceptionBot;
}
