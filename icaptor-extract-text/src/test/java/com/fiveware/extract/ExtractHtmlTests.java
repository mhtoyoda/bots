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

	String outPathFile;
	String fileHtml;
	URL url;

	@Before
	public void setup() throws MalformedURLException {
		String rootDir = Paths.get(".").toAbsolutePath().normalize().toString();
		outPathFile = rootDir + File.separator +"out.pdf";
		fileHtml=rootDir + File.separator + "cartaExemplo.html";
		url = new URL("https://www.uol.com.br");
	}


	@Test
	public void convertFromFileHtml() throws InvalidParameterException, MalformedURLException, IOException, TikaException, SAXException {
		File file = helpers().html()
				.fromFile(fileHtml)
				.toPdf()
				.buildToFile(outPathFile);

		InputStream in = new FileInputStream(file);
		assertTrue(in.available()>-1);
	}

	@Test
	public void convertFromUrl() throws InvalidParameterException, MalformedURLException, IOException, TikaException, SAXException {
		File file = helpers().html()
				.fromUrl(url)
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
