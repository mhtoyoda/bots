package com.fiveware.dsl.pdf;

import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.Objects;
import java.util.stream.Stream;

class HtmlToPDFImpl implements Convert{

	static Logger logger = LoggerFactory.getLogger(HtmlToPDFImpl.class);

	private Dimension format = PD4Constants.A4;
	private boolean landscapeValue = false;
	private boolean fitPageVertically = false;
	private int topValue = 10;
	private int leftValue = 10;
	private int rightValue = 10;
	private int bottomValue = 10;
	private int userSpaceWidth = 750;

	private String textoHtml;
	private URL url;


	private byte[] bytes;

	public HtmlToPDFImpl(String fileHtml) {
		this.textoHtml = readHtml(fileHtml);
	}

	public HtmlToPDFImpl(URL url) {
		this.url=url;
	}


	private byte[] convertHtmlToPdf(String html, String baseUrl, String charsetName) throws InvalidParameterException, MalformedURLException, IOException {
		PD4ML pd4ml = getPd4ML();

		InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(html.getBytes(charsetName)), charsetName);

		ByteArrayOutputStream pdf = new ByteArrayOutputStream();

		if (baseUrl == null) {
			pd4ml.render(reader, pdf);
		} else {
			pd4ml.render(reader, pdf, new URL(baseUrl));
		}

		return pdf.toByteArray();
	}


	@Override
	public byte[] requestHtmlToPdf(URL url) throws InvalidParameterException, MalformedURLException, IOException {
		PD4ML pd4ml = getPd4ML();

		ByteArrayOutputStream pdf = new ByteArrayOutputStream();

		pd4ml.render(url, pdf);
		
		return pdf.toByteArray();
	}

	@Override
	public Convert toPdf()  {
		try {
			if (Objects.isNull(this.url))
				bytes = convertHtmlToPdf(this.textoHtml, null, "ISO8859_1");
			else
				bytes = requestHtmlToPdf(this.url);

		} catch (IOException e) {
			logger.error("{}",e);
		}
		return this;
	}

	@Override
	public byte[] buildToBytes()  {
		return bytes;
	}

	@Override
	public File buildToFile(String outFile)  {
		File fileToWriteTo = new File(outFile);
		try {
			Files.write(bytes, fileToWriteTo);
		} catch (IOException e) {
			logger.error("{}",e);
		}
		return fileToWriteTo;
	}


	private PD4ML getPd4ML() {
		PD4ML pd4ml = new PD4ML();

		pd4ml.setPageSize(landscapeValue ? pd4ml.changePageOrientation(format) : format);

		if (this.fitPageVertically) {
			pd4ml.adjustHtmlWidth();
			pd4ml.fitPageVertically();
		} else {
			pd4ml.setPageInsetsMM(new Insets(topValue, leftValue, bottomValue, rightValue));
			pd4ml.setHtmlWidth(userSpaceWidth);
			pd4ml.protectPhysicalUnitDimensions();
		}
		return pd4ml;
	}


	private String readHtml(String fileHtml) {
		final StringBuilder textoHtml = new StringBuilder();


		try (Stream<String> lines = java.nio.file.Files.lines(Paths.get(fileHtml), Charset.forName("ISO8859_1"))) {

			lines.forEach((s)->textoHtml.append(s));

		} catch (IOException e) {
			logger.error("{}",e);
		}

		return textoHtml.toString();

	}

}
