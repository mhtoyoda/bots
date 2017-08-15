package com.fiveware.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveware.exception.RuntimeBotException;
import com.fiveware.exception.UnRecoverableException;
import com.fiveware.loader.ClassLoaderConfig;
import com.fiveware.loader.ClassLoaderRunner;
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.OutTextRecord;
import com.fiveware.model.OutputDictionaryContext;
import com.fiveware.processor.ProcessorFields;
import com.fiveware.task.StatuProcessTaskEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by valdisnei on 29/05/17.
 */
@Service
public  class ServiceBotClassLoader<T> {

    static Logger logger = LoggerFactory.getLogger(ServiceBotClassLoader.class);

    @Autowired
    private ObjectMapper objectMapper;  
    
    @Autowired
    private ClassLoaderRunner classLoaderRunner;

    @Autowired
    private ClassLoaderConfig classLoaderConfig;

    @Autowired
    private MessageSource messageSource;



    public OutTextRecord executeMainClass(String nameBot,String endpoint, T parameter) throws IOException,
            ClassNotFoundException, InstantiationException, IllegalAccessException,
            NoSuchMethodException, RuntimeBotException {
        Optional<BotClassLoaderContext> botClassLoaderContext = classLoaderConfig.getPropertiesBot(nameBot);

        if(!endpoint.equals(botClassLoaderContext.get().getEndpoint()))
            throw new RuntimeBotException(messageSource.getMessage("endPoint.notFound",new Object[]{endpoint},null));

        return getOutTextRecord(parameter, null);
    }


    public OutTextRecord getOutTextRecord(T parameter, ProcessorFields processorFields)
            throws ClassNotFoundException, RuntimeBotException, IOException, InstantiationException, IllegalAccessException,
            NoSuchMethodException {

        Optional<BotClassLoaderContext> botClassLoaderContext = processorFields.getContext();

        ClassLoader classLoader = classLoaderRunner.loadClassLoader(botClassLoaderContext.get().getNameBot());
        Class clazz = classLoader.loadClass(botClassLoaderContext.get().getClassLoader());
        Class o = classLoader.loadClass(botClassLoaderContext.get().getTypeParameter().getName());

        Class<?> aClass = o.newInstance().getClass();
        Method execute = clazz.getMethod(botClassLoaderContext.get().getMethod(), aClass);

        Object o1 = null;
        if( null != parameter ){
            o1 = objectMapper.convertValue(parameter, aClass);
        }

        Object obj = null;
        try{

        	if( null != o1){
        		obj =  execute.invoke(clazz.newInstance(), o1);
        	}else{
        		obj =  execute.invoke(clazz.newInstance());
        	}

            processorFields.getMessageBot().setStatuProcessEnum(StatuProcessTaskEnum.SUCCESS);
            processorFields.getMessageBot().setLineResult(objectMapper.writeValueAsString(obj));

        }catch (InvocationTargetException e) {

            if (e.getCause().getClass().getName().equals(UnRecoverableException.class.getName())){

                UnRecoverableException unRecoverable = new UnRecoverableException(e.getCause());

                Map map = new HashMap();
                String fields = getOutputDictionary(processorFields);
                map.put("ERROR",  fields+"|"+unRecoverable.getMessage());
                HashMap[] hashMaps = {(HashMap) map};

                processorFields.getMessageBot().setStatuProcessEnum(StatuProcessTaskEnum.ERROR);
                processorFields.getMessageBot().setLineResult(fields+"|"+unRecoverable.getMessage());

                return new OutTextRecord(hashMaps);
            }else{
                throw new RuntimeBotException(e.getTargetException().getMessage());
            }
        }

        if (obj instanceof List)
            return new OutTextRecord(objectMapper.convertValue(obj, Map[].class));

        Map map = objectMapper.convertValue(obj, Map.class);
        HashMap[] hashMaps = {(HashMap) map};

        return new OutTextRecord(hashMaps);
    }

    private String getOutputDictionary(ProcessorFields processorFields) {
        OutputDictionaryContext outputDictionary = processorFields.getContext().get().getOutputDictionary();
        return outputDictionary.fieldsToLine();

    }

    protected ClassLoader getClassLoader(String pathJar) throws MalformedURLException {
        ClassLoader classLoader = new URLClassLoader(new URL[] { getUrl(pathJar) });
        return classLoader;
    }

    protected URL getUrl(String pathJar) throws MalformedURLException {
        File fileJar = new File(pathJar);
        return fileJar.toURI().toURL();
    }
}