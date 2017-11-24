package com.fiveware.task;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.dsl.file.FileExtractUtil;
import com.fiveware.model.BotFormatter;
import com.fiveware.model.ItemTask;
import com.fiveware.model.Task;
import com.fiveware.model.TaskFile;
import com.fiveware.service.ServiceBot;
import com.fiveware.service.ServiceTaskFile;
import com.google.common.collect.Lists;

@Component
public class TaskFileManager {

	private static Logger log = LoggerFactory.getLogger(TaskFileManager.class);

	@Autowired
	private ServiceTaskFile serviceTaskFile;

	@Autowired
	private ServiceBot serviceBot;

	@Autowired
	private FileExtractUtil fileExtractUtil;

	public List<TaskFile> getFileTaskById(Long taskId) {
		List<TaskFile> taskList = serviceTaskFile.getFileTaskById(taskId);
		return taskList;
	}

	private String getIndexField(BotFormatter botFormatter, String dataOut) {
		try {
			JSONObject jsonObject = new JSONObject(dataOut);
			String value = (String) jsonObject.get(botFormatter.getFieldIndex());
			return value;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}

	private byte[] getFileByteArray(BotFormatter botFormatter, String dataOut) {
		try {
			JSONObject jsonObject = new JSONObject(dataOut);
			String value = (String) jsonObject.get(botFormatter.getFieldName());
			return Base64.getDecoder().decode(value.getBytes());
//			return value.getBytes();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String generateFileName(BotFormatter botFormatter, String dataOut) {
		String typeFile = botFormatter.getTypeFile().toLowerCase();
		String indexField = getIndexField(botFormatter, dataOut);
		String fileName = indexField + "." + typeFile;
		return fileName;
	}

	private void saveTaskFile(Task task, byte[] zip) {
		TaskFile taskFile = new TaskFile();
		taskFile.setTask(task);
		taskFile.setFile(zip);
		taskFile = serviceTaskFile.save(taskFile);
	}

	public void processTaskFile(Task task, List<ItemTask> itemTaskList) {
		Map<String, List<byte[]>> filesMap = new HashMap<>();
		byte[] fileTxtByte = writeFileDataOut(dataOutList(itemTaskList));
		filesMap.put("saida.txt", Lists.newArrayList(fileTxtByte));
		executeFormatter(task, itemTaskList, filesMap);		
		generateFileZip(task, filesMap);
	}

	private void executeFormatter(Task task, List<ItemTask> itemTaskList, Map<String, List<byte[]>> filesMap) {
		List<BotFormatter> botFormatters = getBotFormatters(task.getBot().getNameBot());
		if (CollectionUtils.isNotEmpty(botFormatters)) {
			for (ItemTask itemTask : itemTaskList) {
				List<byte[]> list = Lists.newArrayList();
				for (BotFormatter botFormatter : botFormatters) {
					String key = generateFileName(botFormatter, itemTask.getDataOut());
					log.info("Generate file to task id: {} - item task id: {} - field: {}", task.getId(),
							itemTask.getId(), botFormatter.getFieldName());
					byte[] file = getFileByteArray(botFormatter, itemTask.getDataOut());
					if (null != file) {
						if (filesMap.containsKey(key)) {
							filesMap.get(key).add(file);
						} else {
							list.add(file);
							filesMap.put(key, list);
						}
					}
				}
			}
		}
	}

	private void generateFileZip(Task task, Map<String, List<byte[]>> filesMap) {
		try {
			byte[] zip = fileExtractUtil.createZipFile(filesMap);
			saveTaskFile(task, zip);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private byte[] writeFileDataOut(List<String> itens) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(baos))){
			itens.forEach(item -> {
				try {
					writer.write(item);
					writer.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});			
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return baos.toByteArray();
	}

	private List<BotFormatter> getBotFormatters(String nameBot) {
		List<BotFormatter> botFormatters = serviceBot.findBotFormatter(nameBot);
		return botFormatters;
	}

	public void deleteTaskFileManager(TaskFile taskFile) {
		serviceTaskFile.delete(taskFile);
	}
	
	private List<String> dataOutList(List<ItemTask> itens){
		List<String> list = itens.stream()
				.filter(item -> StringUtils.isNotBlank(item.getDataOut()))
				.map((itemTask) -> itemTask.getDataOut())
				.collect(Collectors.toList());
		return list;
	}
}