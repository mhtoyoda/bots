package com.fiveware.loader;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Set;

import com.fiveware.model.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.fiveware.Automation;
import com.fiveware.exception.AttributeLoadException;
import com.fiveware.helpers.BotClassloaderContextBuilder;
import com.fiveware.helpers.FieldsDictionary;
import com.fiveware.helpers.InputDictionaryContextBuilder;
import com.fiveware.helpers.OutputDictionaryContextBuilder;
import com.fiveware.helpers.ParameterContextBuilder;
import com.fiveware.metadata.IcaptorMetaInfo;
import com.google.common.collect.Lists;

@Component
public class JarConfiguration {

    static Logger logger = LoggerFactory.getLogger(JarConfiguration.class);

    @Autowired
    private ClassLoaderConfig classLoaderConfig;

    @Autowired
    private MessageSource messageSource;

    @SuppressWarnings("rawtypes")
    public void saveConfigurations(String pathJar) throws MalformedURLException, AttributeLoadException, IllegalAccessException {
        String nameBot = null, classLoaderInfo = null, nameJar = null, version = null, description = null;
        ClassLoader classLoader = getClassLoader(pathJar);

        Set<Class<? extends Automation>> subTypesOf = getSubTypes(classLoader);

        for (Class<? extends Automation> clazz : subTypesOf) {
            try {
                Class<?> automationClass = classLoader.loadClass(clazz.getCanonicalName());
                Annotation[] annotations = automationClass.getAnnotations();
                for (Annotation annotation : annotations) {
                    Class<? extends Annotation> annotationType = annotation.annotationType();
                    if (annotationType.getSimpleName().equals("Icaptor")) {
                        try {
                            nameBot = (String) getValue(annotation, annotationType, IcaptorMetaInfo.VALUE.getValue());
                            classLoaderInfo = (String) getValue(annotation, annotationType,
                                    IcaptorMetaInfo.CLASSLOADER.getValue());
                            assertClassLoaderBot(classLoaderInfo, clazz.getCanonicalName());
                            nameJar = StringUtils.substringAfterLast(pathJar, "/");
                            version = (String) getValue(annotation, annotationType, IcaptorMetaInfo.VERSION.getValue());
                            description = (String) getValue(annotation, annotationType, IcaptorMetaInfo.DESCRIPTION.getValue());
                        } catch (NoSuchMethodException | SecurityException | IllegalAccessException
                                | InvocationTargetException e) {
                            logger.error("Problema rotina saveconfiguration: ", e);
                        }
                    }
                }

                String method = (String) IcaptorMetaInfo.VALUE.getValueAtribute(automationClass, "IcaptorMethod");
                String endpoint = (String) IcaptorMetaInfo.ENDPOINT.getValueAtribute(automationClass, "IcaptorMethod");
                Class<?> typeParameter = (Class<?>) IcaptorMetaInfo.TYPEPARAMETER.getValueAtribute(automationClass,
                        "IcaptorMethod");
                InputDictionaryContext inputDictionaryContext = getInputDictionaryAttributes(automationClass);
                OutputDictionaryContext outputDictionaryContext = getOutputDictionaryAttributes(automationClass);
                List<IcaptorPameterContext> parameterContext = getIcaptorPametersContextAttributes(automationClass);
                if (CollectionUtils.isEmpty(parameterContext)) {
                    parameterContext = Lists.newArrayList();
                    IcaptorPameterContext icaptorPameterContext = getIcaptorPameterContextAttributes(automationClass);
                    if (null != icaptorPameterContext) {
                        parameterContext.add(icaptorPameterContext);
                    }
                }
                BotClassLoaderContext botClassLoaderContext = getBotClassLoaderContext(nameBot, classLoaderInfo,
                        nameJar, version, method, endpoint, description, inputDictionaryContext, outputDictionaryContext, getUrl(pathJar),
                        typeParameter, parameterContext);

                saveAttributesClassLoader(botClassLoaderContext);
            } catch (ClassNotFoundException e) {
                logger.error("Error load jar bot: {}", e.getMessage());
            } catch (AttributeLoadException e) {
                throw new AttributeLoadException(e.getMessage());
            }
        }
    }

    private void assertClassLoaderBot(String classLoaderInfo, String className) throws ClassNotFoundException {
        if (!classLoaderInfo.equals(className)) {
            throw new ClassNotFoundException("ClassLoader [" + className + "] - Error Name Configuration: " + classLoaderInfo);
        }
    }

    private void saveAttributesClassLoader(BotClassLoaderContext botClassLoaderContext) {
        classLoaderConfig.savePropertiesBot(botClassLoaderContext);
    }

    private BotClassLoaderContext getBotClassLoaderContext(String nameBot, String classLoaderInfo, String nameJar,
                                                           String version, String method, String endpoint, String description, InputDictionaryContext inputDictionaryContext,
                                                           OutputDictionaryContext outputDictionaryContext, URL url, Class<?> typeParameter, List<IcaptorPameterContext> pameterContexts) throws ClassNotFoundException {

        BotClassloaderContextBuilder builder = new BotClassloaderContextBuilder(inputDictionaryContext,
                outputDictionaryContext, pameterContexts);


        if (description.length() > Bot.DESCRIPTION_LENGTH)
            throw new ClassNotFoundException(
                    messageSource.getMessage("bot.description.length", new Object[]{nameBot, Bot.DESCRIPTION_LENGTH, description.length()}, null));

        //@formatter on
        return builder.nameBot(nameBot)
                .classLoader(classLoaderInfo)
                .nameJar(nameJar)
                .versionJar(version)
                .method(method)
                .endpoint(endpoint)
                .description(description)
                .url(url)
                .typeParameter(typeParameter)
                .build();
        //@formatter off
    }

    private OutputDictionaryContext getOutputDictionaryAttributes(Class<?> automationClass)
            throws AttributeLoadException {
        String typeFileOut = (String) IcaptorMetaInfo.TYPEFILEOUT.getValueAtribute(automationClass, "OutputDictionary");
        String[] fieldsOut = (String[]) IcaptorMetaInfo.FIELDS.getValueAtribute(automationClass, "OutputDictionary");
        String separatorOut = (String) IcaptorMetaInfo.SEPARATOR.getValueAtribute(automationClass, "OutputDictionary");
        String nameFileOut = (String) IcaptorMetaInfo.NAMEFILEOUT.getValueAtribute(automationClass, "OutputDictionary");

        OutputDictionaryContextBuilder builder = new OutputDictionaryContextBuilder(new FieldsDictionary(fieldsOut));
        return builder.nameFileOut(nameFileOut).typeFileOut(typeFileOut).separator(separatorOut).build();
    }

    private InputDictionaryContext getInputDictionaryAttributes(Class<?> automationClass)
            throws AttributeLoadException {
        String typeFileIn = (String) IcaptorMetaInfo.TYPEFILEIN.getValueAtribute(automationClass, "InputDictionary");
        String[] fields = (String[]) IcaptorMetaInfo.FIELDS.getValueAtribute(automationClass, "InputDictionary");
        String separator = (String) IcaptorMetaInfo.SEPARATOR.getValueAtribute(automationClass, "InputDictionary");

        InputDictionaryContextBuilder builder = new InputDictionaryContextBuilder(new FieldsDictionary(fields));
        builder.typeFileIn(typeFileIn).separator(separator).build();
        return builder.typeFileIn(typeFileIn).separator(separator).build();
    }

    private List<IcaptorPameterContext> getIcaptorPametersContextAttributes(Class<?> automationClass) throws AttributeLoadException {
        List<IcaptorPameterContext> parameters = IcaptorMetaInfo.VALUE.getValueAtributeParameters(automationClass, "IcaptorParameters");
        return parameters;
    }

    private IcaptorPameterContext getIcaptorPameterContextAttributes(Class<?> automationClass) throws AttributeLoadException {
        String value = (String) IcaptorMetaInfo.VALUE.getValueAtribute(automationClass, "IcaptorParameter");
        if (null != value) {
            String regexValidate = (String) IcaptorMetaInfo.REGEXVALIDATE.getValueAtribute(automationClass, "IcaptorParameter");
            String nameTypeParameter = (String) IcaptorMetaInfo.NAMETYPEPARAMETER.getValueAtribute(automationClass, "IcaptorParameter");
            boolean exclusive = (Boolean) IcaptorMetaInfo.EXCLUSIVE.getValueAtribute(automationClass, "IcaptorParameter");
            boolean credential = (Boolean) IcaptorMetaInfo.CREDENTIAL.getValueAtribute(automationClass, "IcaptorParameter");

            ParameterContextBuilder builder = new ParameterContextBuilder();
            IcaptorPameterContext icaptorPameterContext = builder.value(value)
                    .regexValidate(regexValidate).nameTypeParameter(nameTypeParameter)
                    .exclusive(exclusive).credential(credential).build();
            return icaptorPameterContext;
        }
        return null;
    }

    protected Object getValue(Annotation annotation, Class<? extends Annotation> annotationType, String attribute)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method methodTarget = annotationType.getMethod(attribute);
        Object value = methodTarget.invoke(annotation);
        return value;
    }

    protected ClassLoader getClassLoader(String pathJar) throws MalformedURLException {
        ClassLoader classLoader = new URLClassLoader(new URL[]{getUrl(pathJar)});
        return classLoader;
    }

    protected URL getUrl(String pathJar) throws MalformedURLException {
        File fileJar = new File(pathJar);
        return fileJar.toURI().toURL();
    }

    @SuppressWarnings("rawtypes")
    protected Set<Class<? extends Automation>> getSubTypes(ClassLoader classLoader) throws MalformedURLException, IllegalAccessException {
        ConfigurationBuilder config = new ConfigurationBuilder().setUrls(ClasspathHelper.forClassLoader(classLoader))
                .addClassLoader(classLoader);
        Reflections reflections = new Reflections(config);
        Set<Class<? extends Automation>> subTypesOf = reflections.getSubTypesOf(Automation.class);
        if (subTypesOf.size() == 0) {
            logger.error("Classe Automation não encontrada!");
            throw new IllegalAccessException("Classe Automation não encontrada!");
        }

        return subTypesOf;
    }

    public void removeBot(String pathJar) {
        classLoaderConfig.removeBot(pathJar);
    }
}