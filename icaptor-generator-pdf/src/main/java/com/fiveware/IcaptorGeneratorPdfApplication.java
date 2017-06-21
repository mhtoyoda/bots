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
		String path="/home/valdisnei/dev/VOTORANTIM_ENERGIA_LTDA_0282682038_03-2017.pdf";

//		String path="/home/valdisnei/dev/00009133362-0000-FIXO_022017.pdf";
		Path folder = Paths.get(path);



		String vencimento = new ExtractPDF(folder,1)
							.search("Vencimento",TypeSearch.VENCIMENTO)
							.build();

		System.out.println("vencimento = " + vencimento);


		String cnpj = new ExtractPDF(folder,2)
				.search("Nome da Empresa:", "Nome da Empresa:\\W+")
				.build();

		System.out.println("cnpj = " + cnpj);


	}
}
