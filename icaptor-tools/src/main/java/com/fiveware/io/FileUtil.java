package com.fiveware.io;

import com.fiveware.model.MessageBot;
import com.fiveware.model.OutTextRecord;
import com.fiveware.model.Record;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FileUtil {

    @Value("${io.worker.dir}")
    private String workDir;

    @Value("${io.file-read}")
    private String workerFileRead;


    static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public List<Record> linesFrom(InputStream file, String[] fields, String separator) throws IOException {
        List<String> linhas = Lists.newArrayList();

        buildScanner(file, linhas);

        List<Record> lines = getLines(linhas, fields, separator);
        return lines;
    }

    private void buildScanner(InputStream file, List<String> linhas) {

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            linhas.add(line);
        }
        scanner.close();
    }

    public List<Record> linesFrom(List<String> linhas, String[] fields, String separator) throws IOException {
        List<Record> lines = getLines(linhas, fields, separator);
        return lines;
    }

    public void writeFile(String fileNameOut, String separator, OutTextRecord result) throws IOException {
        File fileOut = new File(fileNameOut);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileOut, true))) {

            List<Map<String, Object>> collect = getMaps(result);

            collect.stream().map(Map::values).forEach(v -> consumer(v, separator, bw));
        }
    }


    private void consumer(Collection<Object> list, String separator, BufferedWriter bw) {
        StringJoiner joiner = addJoiner(separator, list);
        try {
            bw.write(joiner.toString());
            bw.newLine();
        } catch (IOException e) {
            logger.error("{}", e);
        }
    }

    private List<Record> getLines(List<String> lines, String[] fields, String separator) {
        List<Record> recordsLines = Lists.newArrayList();

        lines.forEach(record -> {
            String[] recordArray = StringUtils.split(record, separator);//record.split(separator);
            if (recordArray.length == fields.length) {
                Record recordLines = new Record();
                for (int i = 0; i < fields.length; i++) {
                    recordLines.addRecordLine(fields[i], recordArray[i]);
                }
                recordsLines.add(recordLines);
            }
        });
        return recordsLines;
    }

    private List<Map<String, Object>> getMaps(OutTextRecord result) {
        return Optional
                .ofNullable(Arrays.stream(result.getMap()).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }


    private StringJoiner addJoiner(String separator, Collection<Object> v) {
        StringJoiner joiner = new StringJoiner(separator);
        v.forEach(valor -> joiner.add((CharSequence) valor));
        return joiner;
    }


    public void addResult(MessageBot messageBot, OutTextRecord result, String separatorInput) {

        List<Map<String, Object>> collect = getMaps(result);

        List<String> lines = Lists.newArrayList();

        collect.stream().map(Map::values).forEach(v -> {
            StringJoiner joiner = addJoiner(separatorInput, v);
            lines.add(joiner.toString());
        });

        messageBot.getLineResult().addAll(lines);

    }

    public void writeFile(List<String> linesResult, MessageBot obj) {

        String path = workerFileRead + File.separator + obj.getPathFile();

        File fileOut = new File( path);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileOut, true))) {

            linesResult.forEach((line)->{
                try {
                    bw.write(line);
                    bw.newLine();

                } catch (IOException e) {
                    logger.error("{}", e);
                }
            });
        } catch (IOException e) {
            logger.error("{}", e);
        }
    }

}