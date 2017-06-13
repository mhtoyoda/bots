package com.fiveware.directories;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private Producer<MessageInputDictionary> producer;

    public void readFile(final String path) throws IOException {
        String[] fields = {"campo1","campo2"};
        List<Record> records = fileUtil.linesFrom(new File(path), fields, "|");

        records
                .stream()
                .map(Record::getRecordMap)
                .collect(Collectors.toList())
                .stream().map(Map::values).forEach(consumerCollection());

    }

    private Consumer<Collection<Object>> consumerCollection() {
        return v -> {
            StringJoiner joiner = new StringJoiner("|");
            v.forEach((valor) -> joiner.add((CharSequence) valor));

            MessageInputDictionary dictionary = new MessageInputDictionary();
            dictionary.setLine(joiner.toString());
            dictionary.setTypeMessage(TypeMessage.INPUT_DICTIONARY);
            dictionary.setDescription("branch:icaptor-58");

            producer.send(dictionary);
        };
    }

}
