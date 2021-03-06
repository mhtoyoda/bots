package com.fiveware.dsl.html;

import com.fiveware.UtilsPages;
import com.fiveware.dsl.TypeSearch;
import com.fiveware.dsl.pdf.Convert;
import com.fiveware.dsl.pdf.Pdf;
import com.fiveware.dsl.pdf.Search;
import com.fiveware.dsl.pdf.core.PageIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.fiveware.dsl.pdf.Convert.convert;

/**
 * Created by valdisnei on 27/06/17.
 */
class ConvertHtmlToPdf implements Html{

    static Logger logger = LoggerFactory.getLogger(ConvertHtmlToPdf.class);
    private String url;
    private String outPut;
    private Pdf pdf;

    public ConvertHtmlToPdf(Pdf pdf) {
        this.pdf = pdf;
    }

    protected ConvertHtmlToPdf(String url) {
        this.url = url;
    }

    protected ConvertHtmlToPdf() {
    }

    @Override
    public Html open(String url) {
        return new ConvertHtmlToPdf(url);
    }

    @Override
    public Html outPutFile(String file) {
        setOutPut(file);
        return this;
    }

    @Override
    public Convert fromFile(String fileHtml){
        return convert(fileHtml);
    }

    @Override
    public Convert fromUrl(URL url){
        return convert(url);
    }


    @Override
    public Search search(String textSearch, TypeSearch typeSearch) {
        buildToFile();
        open(getOutPut());
        Search search = pdf.getSearch();
        return search.seek(textSearch,typeSearch);
    }

    @Override
    public ExtractHtml file(String fileHtml) throws IOException {
        ExtractHtml builderExtractHtml = new ExtractTextFromHtml();
        return builderExtractHtml.file(fileHtml);
    }

    @Override
    public void buildToFile() {
        ResponseEntity<byte[]> response = getResponseEntity();

        if (response.getStatusCode() == HttpStatus.OK) {
            writerFile(response);
        }
    }
    protected PageIterator buildToPages() {
        ResponseEntity<byte[]> response = getResponseEntity();

        if (response.getStatusCode() == HttpStatus.OK) {
            return writerBytes(response);
        }

        return null;
    }

    private ResponseEntity<byte[]> getResponseEntity() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(
                new ByteArrayHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> entity = new HttpEntity<String>(headers);


        final String downloadUrl="http://34.206.50.158:8080/open?auth=arachnys-weaver&url="+url;


        return restTemplate.exchange(downloadUrl, HttpMethod.GET, entity,
                                                                    byte[].class, "0");
    }

    private void writerFile(ResponseEntity<byte[]> response) {
        try {
            Files.write(Paths.get(this.outPut), response.getBody());
        } catch (IOException e) {
            logger.error("{}",e);
        }
    }

    private PageIterator writerBytes(ResponseEntity<byte[]> response) {
        try {
            Files.write(Paths.get(this.outPut), response.getBody());
            return UtilsPages.pages(this.outPut);

        } catch (IOException e) {
            logger.error("{}",e);
        }

        return null;

    }


    protected void setOutPut(String outPut) {
        this.outPut = outPut;
    }

    protected String getOutPut() {
        return outPut;
    }
}
