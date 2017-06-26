package com.fiveware;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.fiveware.Extract.FromTo;
import static org.junit.Assert.assertEquals;

public class ExtractPDFTests {

	String path;
	@Before
	public void setup(){
		path ="/home/valdisnei/dev/VOTORANTIM_ENERGIA_LTDA_0282682038_03-2017.pdf";
	}

	@Test
	public void vencimento(){

		String vencimento = new Extract(path,1)
				.search("Vencimento", TypeSearch.DATE)
				.build();

		assertEquals("28/03/2017",vencimento);

	}
	@Test
	public void dataEmissao(){

		String dataEmissao = new Extract(path,1)
				.search("Data de emissão: ", TypeSearch.DATE)
				.build();

		assertEquals("07/03/2017",dataEmissao);
	}

	@Test
	public void icms(){


		String icms = new Extract(path,2)
				.search("ICMS ",  TypeSearch.MONEY)
				.build();

		assertEquals("R$ 4,25",icms);
	}

	@Test
	public void valorPagar(){
		String valorPagar = new Extract(path,1)
				.search("- ",  TypeSearch.MONEY)
				.build();

		assertEquals("R$18,32",valorPagar);
	}

	@Test
	public void cnpj(){
		String cnpj = new Extract(path,2)
				.search("CNPJ: ",  TypeSearch.CNPJ)
				.build();

		assertEquals("02.558.157/0001-62",cnpj);
	}


	@Test
	public void exportToPojo() {

		Map map = new HashMap();
		map.put(FromTo("cnpj:","cnpj"),TypeSearch.CNPJ);
		map.put("icms",  TypeSearch.MONEY);
		map.put(FromTo("Total a Pagar - ","valorpagar"),TypeSearch.MONEY);
		map.put("vencimento",  TypeSearch.DATE);
		map.put(FromTo("Data de emissão: ","dataemissao"),TypeSearch.DATE);
		map.put(FromTo("Conta","numeroconta")," ([0-9]{10})");

		MyCLass myClass = (MyCLass) new Extract(path)
				.search(map,MyCLass.class)
				.build();

		assertEquals("02.558.157/0001-62",myClass.getCnpj());
		assertEquals("R$18,32",myClass.getValorpagar());
		assertEquals("R$ 4,25",myClass.getIcms());
		assertEquals("07/03/2017",myClass.getDataemissao());

		assertEquals("28/03/2017",myClass.getVencimento());
	}

}
