package com.fiveware.config.directories;

import com.fiveware.config.agent.AgentConfig;
import com.fiveware.exception.AttributeLoadException;
import com.fiveware.io.RunningWatchServiceRecursive;
import com.fiveware.loader.JarConfiguration;
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

    @Value("${io.bot.extension.file}")
    private String extensionFile;

    @Autowired
    private JarConfiguration jarConfiguration;

    @Autowired
    private AgentConfig agentConfig;

    @Override
    public void includeAction(Path path) throws IOException, AttributeLoadException, IllegalAccessException {
        if (isValidTypeFile(path)) {
            jarConfiguration.saveConfigurations(path.toFile().getAbsoluteFile().getPath());
            agentConfig.initAgent();
        }
    }

    @Override
    public boolean isValidTypeFile(Path file) {
        return (file.toString().endsWith(extensionFile));
    }

	@Override
	protected void excludeAction(Path path) throws IOException, AttributeLoadException {
		if (isValidTypeFile(path)) {
			jarConfiguration.removeBot(path.toString());
        }	
	}
	
	public boolean validateClassLoader(String classLoader){
		return true;
	}
}
