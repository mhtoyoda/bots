package com.fiveware;

import com.fiveware.dsl.BuilderPDF;
import com.fiveware.dsl.BuilderSearch;
import com.fiveware.dsl.TypeSearch;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.fiveware.dsl.Extract.FromTo;
import static com.fiveware.dsl.Extract.extract;
import static org.junit.Assert.assertEquals;

public class ExtractPDFTests {

	String path;
	String outPathFile;
	@Before
	public void setup(){
		path ="/home/valdisnei/dev/VOTORANTIM_ENERGIA_LTDA_0282682038_03-2017.pdf";
		outPathFile = "/home/valdisnei/dev/workspace-icaptor/icaptor-automation/out.pdf";

	}

	@Test
	public void athenapdf(){

		String url="http://www.globo.com.br";
		extract().html().open(url).outPutFile(outPathFile).buildToFile();

		String pdf = extract().pdf().open(outPathFile)
				.search("dólar", "\\s?(R\\$ ?\\d{1,3}(\\.\\d{3})*,\\d{3})")
				.build();

		assertEquals("R$ 3,283",pdf);

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

		BuilderPDF open = extract()
				.pdf()
				.open(path);

		BuilderSearch search = open.search("cnpj:", TypeSearch.CNPJ);



		Pojo pojo = (Pojo) open.converter().map(map, Pojo.class).build();


		assertEquals("02.558.157/0001-62",pojo.getCnpj());
		assertEquals("R$18,32",pojo.getValorpagar());
		assertEquals("R$ 4,25",pojo.getIcms());
		assertEquals("07/03/2017",pojo.getDataemissao());

		assertEquals("28/03/2017",pojo.getVencimento());
	}

}
