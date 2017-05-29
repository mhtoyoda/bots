package com.fiveware.schedule;

import com.fiveware.service.ServiceBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by valdisnei on 26/05/17.
 */

@Component
public class RunBots {


    static Logger logger = LoggerFactory.getLogger(RunBots.class);

    @Autowired
    private ServiceBot serviceBot;

    @Scheduled(fixedRate = 5000)
    public void run() {
     //   serviceBot.callBot();
    }

}
