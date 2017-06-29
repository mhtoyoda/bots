package com.fiveware;

import com.fiveware.dsl.TypeSearch;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.fiveware.dsl.Extract.FromTo;
import static com.fiveware.dsl.Extract.extract;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExtractPDFTests {

	String path;
	String outPathFile;
	String url;
	@Before
	public void setup(){
		String rootDir = Paths.get(".").toAbsolutePath().normalize().toString();
		path =rootDir + File.separator + "VOTORANTIM_ENERGIA_LTDA_0282682038_03-2017.pdf";
		outPathFile = rootDir + File.separator +"out.pdf";
		url="http://www.globo.com.br";
	}

	@Test
	public void converteHtmlToPdf(){
		extract().html().open(url).outPutFile(outPathFile).buildToFile();
	}

	@Test
	public void extractHtml(){

		String dolar = extract().html().open(url).outPutFile(outPathFile)
				.search("dólar", TypeSearch.MONEY)
				.build();

		//TODO fixe-me little bit workaround
		String replaceCifrao = dolar.replaceFirst("R\\$","");
		replaceCifrao = replaceCifrao.replaceFirst(",",".");

		assertTrue(Double.valueOf("3.291") <= Double.valueOf(replaceCifrao));

	}

	@Test
	public void vencimento(){
		String vencimento = extract().pdf().open(path,1)
				.search("Vencimento", TypeSearch.DATE)
				.build();

		assertEquals("28/03/2017",vencimento);

	}
	@Test
	public void dataEmissao(){

		String dataEmissao = extract().pdf().open(path,1)
				.search("Data de emissão: ", TypeSearch.DATE)
				.build();

		assertEquals("07/03/2017",dataEmissao);
	}

	@Test
	public void icms(){
		String icms = extract().pdf().open(path, 2)
				.search("ICMS ",  TypeSearch.MONEY)
				.build();

		assertEquals("R$ 4,25",icms);
	}

	@Test
	public void valorPagar(){
		String valorPagar = extract().pdf().open(path)
				.search("- ", TypeSearch.MONEY)
				.build();

		assertEquals("R$18,32",valorPagar);
	}

	@Test
	public void cnpj(){
		String build = extract().pdf()
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

		Pojo pojo = (Pojo) extract()
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
