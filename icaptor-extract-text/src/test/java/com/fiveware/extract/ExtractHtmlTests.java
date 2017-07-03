package com.fiveware.extract;

import com.fiveware.dsl.TypeSearch;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static com.fiveware.dsl.Helpers.helpers;
import static org.junit.Assert.assertTrue;

public class ExtractHtmlTests {

	String path;
	String outPathFile;
	String url;
	String fileHtml;
	String fileExcel;
	String outFileHtml;
	@Before
	public void setup(){
		String rootDir = Paths.get(".").toAbsolutePath().normalize().toString();
		outPathFile = rootDir + File.separator +"out.pdf";
		url="http://www.globo.com.br";
		fileHtml=rootDir + File.separator + "cartaExemplo.html";
		fileExcel=rootDir + File.separator + "sal.xls";
		outFileHtml =rootDir + File.separator + "out.html";
	}


	@Test
	@Ignore
	public void converteHtmlToPdf(){
		helpers().html()
				.open(url)
				.outPutFile(outPathFile)
				.buildToFile();
	}

	@Test
	@Ignore
	public void convertHtmlToPdf(){

		String dolar = helpers().html()
				.open(url)
				.outPutFile(outPathFile)
				.search("dólar", TypeSearch.MONEY)
				.build();

		//TODO fixe-me little bit workaround
		String replaceCifrao = dolar.replaceFirst("R\\$","");
		replaceCifrao = replaceCifrao.replaceFirst(",",".");

		assertTrue(Double.valueOf("3.291") <= Double.valueOf(replaceCifrao));
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
