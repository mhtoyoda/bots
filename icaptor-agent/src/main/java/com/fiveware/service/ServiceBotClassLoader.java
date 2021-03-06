package com.fiveware.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveware.exception.AuthenticationBotException;
import com.fiveware.exception.RecoverableException;
import com.fiveware.exception.RuntimeBotException;
import com.fiveware.exception.UnRecoverableException;
import com.fiveware.loader.ClassLoaderRunner;
import com.fiveware.model.*;
import com.fiveware.parameter.ParameterIcaptor;
import com.fiveware.parameter.ParameterValue;
import com.fiveware.processor.ProcessorFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.function.Predicate;

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
    private ServiceElasticSearch serviceElasticSearch;

    public OutTextRecord getOutTextRecord(T _input, ProcessorFields processorFields)
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


        Object input = null;
        if (!Objects.isNull(_input))
            input = objectMapper.convertValue(_input, aClass);

        Object parameters = null;
        if (!Objects.isNull(processorFields.getParameterValue())) {
            parameters = objectMapper.convertValue(processorFields.getParameterValue(), paramClass);
        }

        try {

            Object output = execute.invoke(clazz.newInstance(), input, parameters);

            processorFields.getMessageBot().setStatuProcessEnum(StatusProcessTaskEnum.SUCCESS);
            processorFields.getMessageBot().setStatusProcessItemTaskEnum(StatusProcessItemTaskEnum.SUCCESS);
            processorFields.getMessageBot().setLineResult(objectMapper.writeValueAsString(output));
            processorFields.getMessageBot().setException(null);

            if (output instanceof List) {
                OutTextRecord outTextRecord = new OutTextRecord(objectMapper.convertValue(output, Map[].class));
                serviceElasticSearch.log(input); // arquivo de entrada
                serviceElasticSearch.log(parameters); // parameters
                //Todo implementar para enviar cada item da lista para o elasticSearch
                //serviceElasticSearch.log(obj); //arquivo de saida
                return outTextRecord;
            }

            if (botClassLoaderContext.get().getOutputDictionary().getFields().length == 1
                    && "listJson".equalsIgnoreCase(botClassLoaderContext.get().getOutputDictionary().getFields()[0])) {
                List<Map<String, Object>> myObjects = objectMapper.readValue(new String(((String) output).getBytes()),
                        new TypeReference<ArrayList<HashMap<String, Object>>>() {
                        });
                Map[] objects = myObjects.toArray(new HashMap[myObjects.size()]);
                return new OutTextRecord(objects);
            }

            serviceElasticSearch.log(input); // arquivo de entrada
            serviceElasticSearch.log(parameters); // parameters
            serviceElasticSearch.log(output); //arquivo de saida

            Map map = objectMapper.convertValue(output, Map.class);
            HashMap[] hashMaps = {(HashMap) map};

            return new OutTextRecord(hashMaps);


        } catch (IllegalStateException e) {
            logger.error("{}", e);
            processorFields.getMessageBot().setStatusProcessItemTaskEnum(StatusProcessItemTaskEnum.ERROR);

            HashMap[] hashMaps = handleException(processorFields, new UnRecoverableException(e.getMessage()));

            serviceElasticSearch.error(e);
            return new OutTextRecord(hashMaps);

        } catch (InvocationTargetException e) {


            Predicate<Class> predicate = new Predicate<Class>() {
                @Override
                public boolean test(Class o) {
                    return o.getName().equals(e.getCause().getClass().getName());
                }
            };

            processorFields.getMessageBot().setStatusProcessItemTaskEnum(StatusProcessItemTaskEnum.ERROR);

            if (predicate.test(UnRecoverableException.class)) {

                HashMap[] hashMaps = handleException(processorFields, new UnRecoverableException(e.getCause()));

                serviceElasticSearch.error(e);

                return new OutTextRecord(hashMaps);
            } else if (predicate.test(RecoverableException.class)) {
                processorFields.getMessageBot().setStatusProcessItemTaskEnum(StatusProcessItemTaskEnum.PROCESSING);

                HashMap[] hashMaps = handleException(processorFields, new RecoverableException(e.getCause()));


                serviceElasticSearch.error(e);

                return new OutTextRecord(hashMaps);
            } else if (predicate.test(AuthenticationBotException.class)) {
                HashMap[] hashMaps = handleException(processorFields, new AuthenticationBotException(e.getCause()));

                serviceElasticSearch.error(e);

                return new OutTextRecord(hashMaps);
            } else {
                HashMap[] hashMaps = handleException(processorFields, new UnRecoverableException(e.getCause()));

                serviceElasticSearch.error(e);
                return new OutTextRecord(hashMaps);
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