package com.fiveware.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.function.Predicate;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveware.exception.AuthenticationBotException;
import com.fiveware.exception.RecoverableException;
import com.fiveware.exception.RuntimeBotException;
import com.fiveware.exception.UnRecoverableException;
import com.fiveware.loader.ClassLoaderConfig;
import com.fiveware.loader.ClassLoaderRunner;
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.OutTextRecord;
import com.fiveware.model.OutputDictionaryContext;
import com.fiveware.parameter.ParameterValue;
import com.fiveware.processor.ProcessorFields;
import com.fiveware.model.StatusProcessItemTaskEnum;
import com.fiveware.model.StatusProcessTaskEnum;

/**
 * Created by valdisnei on 29/05/17.
 */
@Service
public class ServiceBotClassLoader<T> {

    static Logger logger = LoggerFactory.getLogger(ServiceBotClassLoader.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClassLoaderRunner classLoaderRunner;

    @Autowired
    private ClassLoaderConfig classLoaderConfig;

    public OutTextRecord getOutTextRecord(T parameter, ProcessorFields processorFields)
            throws ClassNotFoundException, RuntimeBotException, IOException, InstantiationException, IllegalAccessException,
            NoSuchMethodException {

        Optional<BotClassLoaderContext> botClassLoaderContext = processorFields.getContext();

        ClassLoader classLoader = classLoaderRunner.loadClassLoader(botClassLoaderContext.get().getNameBot());
        Class clazz = classLoader.loadClass(botClassLoaderContext.get().getClassLoader());
        Class o = classLoader.loadClass(botClassLoaderContext.get().getTypeParameter().getName());
        Class param = classLoader.loadClass(ParameterValue.class.getName());
        Class<?> aClass = o.newInstance().getClass();
        Class<?> paramClass = param.newInstance().getClass();

        Method execute = clazz.getMethod(botClassLoaderContext.get().getMethod(), o, param);

        Object o1 = null;
        if (null != parameter) {
            o1 = objectMapper.convertValue(parameter, aClass);
        }

        Object o2 = null;
        if (null != processorFields.getParameterValue()) {
            o2 = objectMapper.convertValue(processorFields.getParameterValue(), paramClass);
        }

        Object obj = null;
        try {
            if (null != o1) {
                obj = execute.invoke(clazz.newInstance(), o1, o2);
            } else {
                obj = execute.invoke(clazz.newInstance());
            }

            processorFields.getMessageBot().setStatuProcessEnum(StatusProcessTaskEnum.SUCCESS);
            processorFields.getMessageBot().setStatusProcessItemTaskEnum(StatusProcessItemTaskEnum.SUCCESS);

            processorFields.getMessageBot().setLineResult(objectMapper.writeValueAsString(obj));


            if (obj instanceof List)
                return new OutTextRecord(objectMapper.convertValue(obj, Map[].class));

            if (botClassLoaderContext.get().getOutputDictionary().getFields().length==1
                    && "listJson".equalsIgnoreCase(botClassLoaderContext.get().getOutputDictionary().getFields()[0])) {
                List<Map<String, Object>> myObjects = objectMapper.readValue(new String(((String) obj).getBytes()),
                                                            new TypeReference<ArrayList<HashMap<String, Object>>>() {});
                Map[] objects = myObjects.toArray(new HashMap[myObjects.size()]);
                return new OutTextRecord(objects);
            }



            Map map = objectMapper.convertValue(obj, Map.class);
            HashMap[] hashMaps = {(HashMap) map};

            return new OutTextRecord(hashMaps);

        } catch (InvocationTargetException e) {

            Predicate<Class> predicate = new Predicate<Class>() {
                @Override
                public boolean test(Class o) {
                    return o.getName().equals(e.getCause().getClass().getName());
                }
            };

            if (predicate.test(UnRecoverableException.class)) {
                processorFields.getMessageBot().setStatusProcessItemTaskEnum(StatusProcessItemTaskEnum.ERROR);

                HashMap[] hashMaps = handleException(processorFields, new UnRecoverableException(e.getCause()));

                return new OutTextRecord(hashMaps);
            } else if (predicate.test(RecoverableException.class)) {
                HashMap[] hashMaps = handleException(processorFields, new RecoverableException(e.getCause()));

                return new OutTextRecord(hashMaps);
            } else if (predicate.test(AuthenticationBotException.class)) {
                HashMap[] hashMaps = handleException(processorFields, new AuthenticationBotException(e.getCause()));

                return new OutTextRecord(hashMaps);
            } else {
                throw new RuntimeBotException(e.getTargetException().getMessage());
            }
        }


    }

    private HashMap[] handleException(ProcessorFields processorFields, Exception ex) {
        Map map = new HashMap();
        String fields = getOutputDictionary(processorFields);
        map.put("ERROR", fields + "|" + ex.getMessage());
        HashMap[] hashMaps = {(HashMap) map};

        processorFields.getMessageBot().setLineResult(fields + "|" + ex.getMessage());
        processorFields.getMessageBot().setException(ex);

        return hashMaps;
    }

    private String getOutputDictionary(ProcessorFields processorFields) {
        OutputDictionaryContext outputDictionary = processorFields.getContext().get().getOutputDictionary();
        return outputDictionary.fieldsToLine();

    }

    protected ClassLoader getClassLoader(String pathJar) throws MalformedURLException {
        ClassLoader classLoader = new URLClassLoader(new URL[]{getUrl(pathJar)});
        return classLoader;
    }

    protected URL getUrl(String pathJar) throws MalformedURLException {
        File fileJar = new File(pathJar);
        return fileJar.toURI().toURL();
    }
}