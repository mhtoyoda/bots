package com.fiveware.directories;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.io.RunningWatchServiceRecursive;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by valdisnei on 05/06/17.
 */

@Service
public class RunningReadFiles extends RunningWatchServiceRecursive {



    @Override
    public void action(Path path) throws IOException, AttributeLoadException {
    }

    @Override
    public boolean isValidTypeFile(Path file) {
        return false;
    }
}
