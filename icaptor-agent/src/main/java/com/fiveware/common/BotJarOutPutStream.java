package com.fiveware.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by valdisnei on 20/01/17.
 */
@Component
public class BotJarOutPutStream {

    Logger logger = LoggerFactory.getLogger(BotJarOutPutStream.class);


    @Value("${io.worker.dir}")
    private String workDir;

    public void upLoad(MultipartFile bot) {

        FileOutputStream fop = null;

        try {
            File file = new File(workDir + File.separator+ bot.getOriginalFilename());
            fop = new FileOutputStream(file);

            // get the content in bytes
            byte[] contentInBytes = bot.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            logger.info("Done");

        } catch (IOException e) {
            logger.error("problema ao carregar jar",e);
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                logger.error("problema ao carregar jar",e);
            }
        }

    }

}
