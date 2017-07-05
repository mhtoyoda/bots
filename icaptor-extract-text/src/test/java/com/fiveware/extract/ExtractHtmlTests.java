package com.fiveware.extract;

import org.apache.tika.exception.TikaException;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.security.InvalidParameterException;

import static com.fiveware.dsl.pdf.Helpers.helpers;
import static org.junit.Assert.assertTrue;

public class ExtractHtmlTests {

	String path;
	String outPathFile;
	String urlString;
	String fileHtml;
	String fileExcel;
	String outFileHtml;
	URL url;
	@Before
	public void setup() throws MalformedURLException {
		String rootDir = Paths.get(".").toAbsolutePath().normalize().toString();
		outPathFile = rootDir + File.separator +"out.pdf";
		urlString ="http://www.globo.com.br";
		fileHtml=rootDir + File.separator + "cartaExemplo.html";
		fileExcel=rootDir + File.separator + "sal.xls";
		outFileHtml =rootDir + File.separator + "out.html";
		url = new URL("https://www.uol.com.br");
	}


	@Test
	public void doConversion() throws InvalidParameterException, MalformedURLException, IOException, TikaException, SAXException {
		File file = helpers().html()
				.fromFile(fileHtml)
				.toPdf()
				.buildToFile(outPathFile);

		InputStream in = new FileInputStream(file);

		assertTrue(in.available()>-1);
	}

	@Test
	public void extractHtml() throws IOException {
		String texto = helpers().html()
				.file(fileHtml)
				.selectElement("div")
				.text(5); //numero do elemento

		assertTrue(texto.startsWith("Fazer tour Sair Selecione"));

	}

}
