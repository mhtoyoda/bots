package com.fiveware.controller;

import com.fiveware.util.BotJarOutPutStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@RestController
@RequestMapping("/upload")
public class FileUploadController {
	static Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	@Autowired
	private BotJarOutPutStream botJarOutPutStream;



	@RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public String upload(@RequestParam("file") MultipartFile[] file) {

		Arrays.stream(file).forEach((f)-> botJarOutPutStream.writeFile(f));

		return "index";
	}



}
