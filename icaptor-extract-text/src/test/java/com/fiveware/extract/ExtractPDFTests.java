package com.fiveware.extract;

import com.fiveware.dsl.TypeSearch;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.List;

import static com.fiveware.dsl.pdf.Helpers.helpers;
import static org.junit.Assert.assertEquals;

public class ExtractPDFTests {

	String path;
	String outPathFile;
	String rootDir;

	@Before
	public void setup(){
		rootDir = Paths.get(".").toAbsolutePath().normalize().toString();
		path =rootDir + File.separator + "VOTORANTIM_ENERGIA_LTDA_0282682038_03-2017.pdf";


		outPathFile = rootDir + File.separator +"out.pdf";
	}

	@Test
	public void baseCalculoICms(){
		String path =rootDir + File.separator + "VOTORANTIM CIMENTOS NNE SA_200-8462_01-2017.pdf";

		String text = helpers().pdf()
				.open(path)
				.getText();

		String baseDeCalculo = helpers().pdf()
				.open(path,1)
				.search("BASE DE CALCULO ICMS  ", "\\s?(\\d{1,3}(\\.\\d{3})*,\\d{1,3})")
				.build();

		assertEquals("6.544,28",baseDeCalculo);

	}


	@Test
	public void vencimento(){
		String vencimento = helpers().pdf()
				.open(path,1)
				.search("", TypeSearch.DATE)
				.next(1);

		assertEquals("28/03/2017",vencimento);

	}
	@Test
	public void dataEmissao() throws UnsupportedEncodingException {

		String dataEmissao = helpers().pdf()
				.open(path,1)
				.search(TypeSearch.DATE)
				.next(4);

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
		List<String> telefone = helpers().pdf()
				.open(path)
				.search("(DDD 014 [0-9]{2}-[0-9]{5}-[0-9]{4})")
				.list();

		assertEquals("DDD 014 14-99656-6187",telefone.get(47));
	}

	@Test
	public void valorPagar(){
		String valorPagar = helpers().pdf()
				.open(path)
				.search("\\d{1,3},\\d{1,3}")
				.next(2);

		assertEquals("18,32",valorPagar);
	}

	@Test
	public void cnpj(){
		String build = helpers().pdf()
				.open(path)
				.search("cnpj: ", TypeSearch.CNPJ)
				.build();

		assertEquals("02.558.157/0001-62",build);
	}


//	@Test
//	public void createPojo() {
//
//		Pojo pojo = getPojo(path);
//
//
//		assertEquals("02.558.157/0001-62",pojo.getCnpj());
//		assertEquals("R$18,32",pojo.getValorpagar());
//		assertEquals("R$ 4,25",pojo.getIcms());
//		assertEquals("03/2017",pojo.getReferencia());
//
//		assertEquals("28/03/2017",pojo.getVencimento());
//	}
//
//	public Pojo getPojo(String path) {
//		Map map = new HashMap();
//		map.put(FromTo("cnpj: ","cnpj"), TypeSearch.CNPJ);
//		map.put("icms",  TypeSearch.MONEY);
//		map.put(FromTo("- ","valorpagar"),TypeSearch.MONEY);
//		map.put("",  TypeSearch.DATE);
//		map.put(FromTo("referência: ","referencia"),"[0-9]{2}/[0-9]{4}");
//		map.put(FromTo("Conta","numeroconta")," ([0-9]{10})");
//
//		return (Pojo) helpers()
//				.pdf()
//				.open(path)
//				.writeObject().map(map, Pojo.class).build();
//	}

}
