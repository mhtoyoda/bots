package com.fiveware.io;

import com.fiveware.dao.AgentDAO;
import com.fiveware.util.FileUtil;
import com.fiveware.messaging.Producer;
import com.fiveware.messaging.TypeMessage;
import com.fiveware.model.MessageBot;
import com.fiveware.model.MessageHeader;
import com.fiveware.model.Record;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by valdisnei on 13/06/17.
 */
@Component
public class ReadInputFile {

    public static final int N_LINES = 2;
    
    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private Producer<MessageBot> producer;

    @Autowired
    private AgentDAO agentDAO;

    public void readFile(final String queueName, final String path, InputStream file) throws IOException {
        String[] fields = {"campo1"};
        List<Record> records = fileUtil.linesFrom(file, fields, "|");

        List<List<Record>> partition = Lists.partition(records, getLinesRecord(records).intValue());

        AtomicInteger rangeChuncks = new AtomicInteger(0);

        partition
                .stream()
                .forEach((splitList) -> {
                    sendListToQueue(splitList, queueName, path, records.size(), rangeChuncks);
                });

    }

    private Long getLinesRecord(List<Record> records) {
        Long agentCount = agentDAO.count();
		Long totalAgent = agentCount > 0 ? agentCount : N_LINES;
        int size = records.size();
        long linesRecord = Math.floorDiv(size, totalAgent);
        return linesRecord;
    }

    private void sendListToQueue(List<Record> recordList, String queueName, String path, Integer size,
                                 AtomicInteger rangeChuncks) {
        final List<String> lines = Lists.newArrayList();

        Consumer<List<Record>> consumer = records ->
                records
                        .stream()
                        .map(Record::getRecordMap)
                        .collect(Collectors.toList())
                        .stream().map(Map::values).forEach(convertMapToCSVLine(lines));
        consumer.accept(recordList);

        MessageHeader messageHeader = new MessageHeader
                .MessageHeaderBuilder(path, size)
                .chuncksInitial(rangeChuncks.get() + 1)
                .chuncksEnd(rangeChuncks.addAndGet(recordList.size()))
                .timeStamp(System.currentTimeMillis())
                .build();

        MessageBot messageBot = new MessageBot(lines, Lists.newArrayList(),
                TypeMessage.INPUT_DICTIONARY,"", messageHeader, path);

        producer.send(queueName, messageBot);

    }

    private Consumer<Collection<Object>> convertMapToCSVLine(List<String> lines) {
        return line -> {
            final StringJoiner joiner = new StringJoiner("|");
            line.forEach((column) -> joiner.add((CharSequence) column));
            lines.add(joiner.toString());
        };
    }
}
