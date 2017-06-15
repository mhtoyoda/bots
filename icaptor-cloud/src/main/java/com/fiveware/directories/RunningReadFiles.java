package com.fiveware.directories;

import com.fiveware.io.RunningWatchServiceRecursive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by valdisnei on 05/06/17.
 */

@Service
public class RunningReadFiles extends RunningWatchServiceRecursive {

    @Value("${extension.input.file}")
    private String extensionFile;


    @Autowired
    private ReadInputFile readInputFile;

    @Override
    public void postRegister(Path path) throws IOException {
        if (isValidFileType(path)) {
            readInputFile.readFile(path.toFile().getAbsoluteFile().getPath());
        }
    }

    @Override
    public boolean isValidFileType(Path file) {
        return (file.toString().endsWith(extensionFile));
    }
}
