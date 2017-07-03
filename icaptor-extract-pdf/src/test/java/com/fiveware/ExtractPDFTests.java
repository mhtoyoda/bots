package com.fiveware;

import com.fiveware.dsl.TypeSearch;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.fiveware.dsl.Helpers.FromTo;
import static com.fiveware.dsl.Helpers.helpers;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Ignore
public class ExtractPDFTests {

	String path;
	String outPathFile;
	String url;
	String fileHtml;
	String fileExcel;
	String outFileHtml;
	@Before
	public void setup(){
			String rootDir = Paths.get(".").toAbsolutePath().normalize().toString();
		path =rootDir + File.separator + "VOTORANTIM_ENERGIA_LTDA_0282682038_03-2017.pdf";
		outPathFile = rootDir + File.separator +"out.pdf";
		url="http://www.globo.com.br";
		fileHtml=rootDir + File.separator + "cartaExemplo.html";
		fileExcel=rootDir + File.separator + "sal.xls";
		outFileHtml =rootDir + File.separator + "out.html";
	}

	@Test
	public void converteExcelToHtml() throws IOException {
		Object cell = helpers().excel()
				.open(fileExcel)
				.sheet(0)
				.cell("A1", 10.00)
				.cell("A2", 20.00)
				.formula("A3", "SUM(A1:A2)")
				.cell("A4", LocalDate.now())
				.build();


		System.out.println("integer = " + cell);
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

//		System.out.println("texto = " + texto);

	}

	@Test
	public void vencimento(){
		String vencimento = helpers().pdf()
				.open(path,1)
				.search("Vencimento", TypeSearch.DATE)
				.build();

		assertEquals("28/03/2017",vencimento);

	}
	@Test
	public void dataEmissao(){

		String dataEmissao = helpers().pdf()
				.open(path,1)
				.search("Data de emissão: ", TypeSearch.DATE)
				.build();

		assertEquals("07/03/2017",dataEmissao);
	}

	@Test
	public void icms(){
		String icms = helpers().pdf()
				.open(path, 2)
				.search("ICMS ",  TypeSearch.MONEY)
				.build();

		assertEquals("R$ 4,25",icms);
	}

	@Test
	public void pis(){
		String pis = helpers().pdf()
				.open(path, 2)
				.search("pis",  TypeSearch.MONEY)
				.build();

		assertEquals("R$ 0,11",pis);
	}

	@Test
	public void telefone(){
		String telefone = helpers().pdf()
				.open(path, 6)
				.search("Número",  "[0-9]{2}-[0-9]{5}-[0-9]{4}")
				.build();

		assertEquals("14-99656-6187",telefone);
	}

	@Test
	public void valorPagar(){
		String valorPagar = helpers().pdf()
				.open(path)
				.search("- ", TypeSearch.MONEY)
				.build();

		assertEquals("R$18,32",valorPagar);
	}

	@Test
	public void cnpj(){
		String build = helpers().pdf()
				.open(path,2)
				.search("CNPJ: ", TypeSearch.CNPJ)
				.build();

		assertEquals("02.558.157/0001-62",build);
	}


	@Test
	public void createPojo() {

		Map map = new HashMap();
		map.put(FromTo("cnpj:","cnpj"),TypeSearch.CNPJ);
		map.put("icms",  TypeSearch.MONEY);
		map.put(FromTo("Total a Pagar - ","valorpagar"),TypeSearch.MONEY);
		map.put("vencimento",  TypeSearch.DATE);
		map.put(FromTo("Data de emissão: ","dataemissao"),TypeSearch.DATE);
		map.put(FromTo("Conta","numeroconta")," ([0-9]{10})");

		Pojo pojo = (Pojo) helpers()
				.pdf()
				.open(path)
				.converter().map(map, Pojo.class).build();


		assertEquals("02.558.157/0001-62",pojo.getCnpj());
		assertEquals("R$18,32",pojo.getValorpagar());
		assertEquals("R$ 4,25",pojo.getIcms());
		assertEquals("07/03/2017",pojo.getDataemissao());

		assertEquals("28/03/2017",pojo.getVencimento());
	}

}
