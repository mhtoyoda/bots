package com.fiveware.directories;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.file.FileUtil;
import com.fiveware.messaging.Producer;
import com.fiveware.messaging.TypeMessage;
import com.fiveware.model.MessageInputDictionary;
import com.fiveware.model.Record;

/**
 * Created by valdisnei on 13/06/17.
 */
@Component
public class ReadInputFile {

    public static final int N_LINES = 3;
    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private Producer<MessageInputDictionary> producer;

    public void readFile(final String path) throws IOException {
        String[] fields = {"campo1","campo2"};
        List<Record> records = fileUtil.linesFrom(new File(path), fields, "|");

        //TODO Change constant N_LINES to implementation of IcaptorAgent's Instance Number
        List<List<Record>> partition = Lists.partition(records, N_LINES);

        partition.stream().forEach((r)-> sendListToQueue(r));

    }

    private void sendListToQueue(List<Record> _listPart) {
        final List<String> lines = Lists.newArrayList();

        Consumer<List<Record>> listPart = records ->
                records
                        .stream()
                        .map(Record::getRecordMap)
                        .collect(Collectors.toList())
                        .stream().map(Map::values).forEach(addLines(lines));
        listPart.accept(_listPart);

        MessageInputDictionary dictionary = new MessageInputDictionary(lines,TypeMessage.INPUT_DICTIONARY,"branch:icaptor-58");
        producer.send("BOT",dictionary);

    }

    private Consumer<Collection<Object>> addLines(List<String> lines) {
        return  line -> {
            final StringJoiner joiner = new StringJoiner("|");
            line.forEach((column) -> joiner.add((CharSequence) column));
            lines.add(joiner.toString());
        };
    }
}
