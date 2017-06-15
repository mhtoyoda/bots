package com.fiveware.directories;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.fiveware.model.MessageHeader;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.file.FileUtil;
import com.fiveware.messaging.Producer;
import com.fiveware.messaging.TypeMessage;
import com.fiveware.model.MessageBot;
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
    private Producer<MessageBot> producer;

    public void readFile(final String path) throws IOException {
        String[] fields = {"campo1","campo2"};
        List<Record> records = fileUtil.linesFrom(new File(path), fields, "|");

        //TODO Change constant N_LINES to implementation of IcaptorAgent's Instance Number
        List<List<Record>> partition = Lists.partition(records, N_LINES);


        MessageHeader messageHeader = new MessageHeader.MessageHeaderBuilder(path, records.size())
                .chuncksInitial(0)
                .chuncksEnd(10)
                .timeStamp(20000L).build();


        partition.stream().forEach((r)-> sendListToQueue(r,messageHeader));

    }

    private void sendListToQueue(List<Record> _listPart, MessageHeader messageHeader) {
        final List<String> lines = Lists.newArrayList();

        Consumer<List<Record>> listPart = records ->
                records
                        .stream()
                        .map(Record::getRecordMap)
                        .collect(Collectors.toList())
                        .stream().map(Map::values).forEach(convertMapToCSVLine(lines));
        listPart.accept(_listPart);

        MessageBot dictionary = new MessageBot(lines,TypeMessage.INPUT_DICTIONARY,"branch:icaptor-58",messageHeader);
        producer.send("BOT",dictionary);

    }

    private Consumer<Collection<Object>> convertMapToCSVLine(List<String> lines) {
        return  line -> {
            final StringJoiner joiner = new StringJoiner("|");
            line.forEach((column) -> joiner.add((CharSequence) column));
            lines.add(joiner.toString());
        };
    }
}
