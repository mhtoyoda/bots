package com.fiveware;

import com.fiveware.file.RecordLine;
import org.springframework.stereotype.Component;

/**
 * Created by valdisnei on 24/05/17.
 */
@Component
public interface Automation {
    RecordLine execute(RecordLine recordLine);
}
