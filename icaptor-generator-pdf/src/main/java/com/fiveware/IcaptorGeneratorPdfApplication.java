package com.fiveware;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class IcaptorGeneratorPdfApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(IcaptorGeneratorPdfApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		testePDF1();
	}

	private void testePDF1() {
		String path ="/Users/valdisnei/dev/VOTORANTIM_CIMENTOS_NNE_SA_200-8462_01-2017.pdf";
		Path folder = Paths.get(path);


//		String vencimento = new ExtractPDF(folder,1)
//							.search("VENCIMENTO ", TypeSearch.DATE)
//							.build();
//
//		System.out.println("vencimento = " + vencimento);
//
//
//		String dataEmissao = new ExtractPDF(folder,1)
//				.search("DATA DE EMISSAO",TypeSearch.DATE)
//				.build();
//
//		System.out.println("data emissao = " + dataEmissao);
//
//
//		String icms = new ExtractPDF(folder,2)
//				.search("ICMS ",  TypeSearch.MONEY)
//				.build();
//
//		System.out.println("ICMS = " + icms);
//
//
//		String valorPagar = new ExtractPDF(folder,1)
//				.search("PAGAR ",  TypeSearch.MONEY)
//				.build();
//
//		System.out.println("Valor Pagar = " + valorPagar);
//
//
//		String cnpj = new ExtractPDF(folder,1)
//				.search("CNPJ / CPF",  TypeSearch.CNPJ)
//				.build();
//
//		System.out.println("CNPJ = " + cnpj);
//
//
//		String build = new ExtractPDF(folder, 1)
//				.search("COD. DEB. AUTOMATICO ", TypeSearch.NUMBER)
//				.build();
//
//		System.out.println("cod. deb = " + build);
//
//
//
//		String cep = new ExtractPDF(folder, 1)
//				.search("TABOAO", TypeSearch.CEP)
//				.build();
//
//		System.out.println("cep = " + cep);


		String vpn = new ExtractPDF(folder, 1)
				.search("TC  V  P N I  P", TypeSearch.MONEY)
				.build();

		System.out.println("vpn = " + vpn);

		String inscr = new ExtractPDF(folder, 1)
				.search("INSC. ESTADUAL:", TypeSearch.INSCR)
				.build();

		System.out.println("inscr = " + inscr);




	}

	private void testePDF2() {
		String path="/Users/valdisnei/dev/VOTORANTIM_ENERGIA_LTDA_028268203/8_03-2017.pdf";

//		String path="/home/valdisnei/dev/00009133362-0000-FIXO_022017.pdf";

		Path folder = Paths.get(path);


		String vencimento = new ExtractPDF(folder,1)
				.search("Vencimento", TypeSearch.DATE)
				.build();

		System.out.println("vencimento = " + vencimento);


		String dataEmissao = new ExtractPDF(folder,1)
				.search("Data de emissão: ",TypeSearch.DATE)
				.build();

		System.out.println("data emissao = " + dataEmissao);


		String icms = new ExtractPDF(folder,2)
				.search("ICMS ",  TypeSearch.MONEY)
				.build();

		System.out.println("ICMS = " + icms);


		String valorPagar = new ExtractPDF(folder,1)
				.search("Total a Pagar - ",  TypeSearch.MONEY)
				.build();

		System.out.println("Valor Pagar = " + valorPagar);


		String cnpj = new ExtractPDF(folder,2)
				.search("CNPJ: ",  TypeSearch.CNPJ)
				.build();

		System.out.println("CNPJ = " + cnpj);
	}
}
