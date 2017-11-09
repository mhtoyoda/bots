package com.fiveware.io;


import com.fiveware.model.Bot;
import com.fiveware.model.Record;
import com.fiveware.model.StatusProcessTaskEnum;
import com.fiveware.model.Task;
import com.fiveware.service.ServiceElasticSearch;
import com.fiveware.task.TaskManager;
import com.fiveware.util.CsvConvertUtil;
import com.fiveware.util.FileUtil;
import com.fiveware.validate.RuleValidations;
import com.fiveware.validate.ValidationFileErrorException;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by valdisnei on 13/06/17.
 */
@Component
public class ReadInputFile {

    private static Logger logger = LoggerFactory.getLogger(ReadInputFile.class);

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private TaskManager taskManager;

    @Autowired
    private RuleValidations ruleValidations;

    @Autowired
    private CsvConvertUtil csvConvertUtil;

    @Autowired
    private ServiceElasticSearch serviceElasticSearch;

    public void readFile(final Long userId, final String nameBot, MultipartFile file, DeferredResult<ResponseEntity<String>> resultado) throws IOException{
        Task task = createTask(nameBot, userId);
        Bot bot = task.getBot();
        String separatorFile = bot.getSeparatorFile();
        Iterable<String> split = Splitter.on(",").split(bot.getFieldsInput());
        String[] fields = Iterables.toArray(split, String.class);
        List<String> lines = fileUtil.linesFrom(file.getInputStream());

        // Devolve a thread do Browser

        try {
            taskManager.updateTask(task.getId(), StatusProcessTaskEnum.VALIDATING);
            ruleValidations.executeValidations(lines, fields, separatorFile);

            resultado.setResult(ResponseEntity.ok().body("OK"));

            taskManager.updateTask(task.getId(), StatusProcessTaskEnum.PROCESSING);
            List<Record> recordList = fileUtil.linesFrom(lines, fields, separatorFile);
            List<String> linesCVS = convertMapToCsvLine(separatorFile, recordList);
            createItemTask(task, linesCVS);
        } catch (ValidationFileErrorException e) {
            serviceElasticSearch.error(e);
            logger.error("{}", e);
            taskManager.updateTask(task.getId(), StatusProcessTaskEnum.REJECTED);
            resultado.setResult(ResponseEntity.badRequest().body(e.getMessage()));

        } catch (IOException e) {
            serviceElasticSearch.error(e);
            logger.error("{}", e.getMessage());
            taskManager.updateTask(task.getId(), StatusProcessTaskEnum.ERROR);
            resultado.setResult(ResponseEntity.badRequest().body(e.getMessage()));
        }
    }

    private void createItemTask(Task task, List<String> linesCVS) {
        if (CollectionUtils.isEmpty(linesCVS)) {
            taskManager.createItemTask(task, "");
        } else {
            linesCVS.stream().forEach(line -> {
                taskManager.createItemTask(task, line);
            });
        }
    }

    private Task createTask(String nameBot, Long userId) {
        return taskManager.createTask(nameBot, userId);
    }

    private List<String> convertMapToCsvLine(String separatorFile, List<Record> recordList) {
        return csvConvertUtil.convertMapToCsvLine(separatorFile, recordList);
    }
}