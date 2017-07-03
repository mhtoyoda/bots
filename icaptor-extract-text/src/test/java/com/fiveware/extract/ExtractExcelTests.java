package com.fiveware.extract;

import com.fiveware.PojoExcel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static com.fiveware.dsl.Helpers.helpers;

public class ExtractExcelTests {

	String fileExcel;
	@Before
	public void setup(){
		String rootDir = Paths.get(".").toAbsolutePath().normalize().toString();
		fileExcel=rootDir + File.separator + "sal.xls";
	}


	@Test
	public void insertValueInCell() throws IOException {
		Object build = helpers().excel()
				.open(fileExcel)
				.sheet(0)
				.cell("A1", 10.0)
				.cell("A2", 40.0)
				.cell("A3", "SUM(A1:A2)")
				.build(); // retorna o ultimo elemento inserido


		Assert.assertTrue("SUM(A1:A2)".equals(build));

	}


	@Test
	public void extractExcelAndPopulatePojo() throws IOException {
		PojoExcel pojo = (PojoExcel) helpers().excel()
				.open(fileExcel)
				.sheet(0)
				.convert("A1", "valor1", PojoExcel.class)
				.convert("A2", "valor2", PojoExcel.class)
				.convert("A3", "formula", PojoExcel.class)
				.toObject();


		Assert.assertTrue(new Double(10).equals(pojo.getValor1()));
		Assert.assertTrue(new Double(40).equals(pojo.getValor2()));
		Assert.assertTrue("SUM(A1:A2)".equals(pojo.getTotal()));
    }

	@Test
	public void readObject() throws IOException, IllegalAccessException, InstantiationException {

		PojoExcel pojoExcel = new PojoExcel();
		pojoExcel.setValor1(50D);
		pojoExcel.setValor2(30D);
		pojoExcel.setTotal("SUM(A2:B2)");

		helpers().excel()
				.open(fileExcel)
				.sheet(1)
				.readObject("A1",pojoExcel)
				.build();


//		Assert.assertTrue(new Double(10).equals(pojo.getValor1()));
//		Assert.assertTrue(new Double(40).equals(pojo.getValor2()));
//		Assert.assertTrue("SUM(A1:A2)".equals(pojo.getTotal()));
	}



	@Test
	public void extractCell() throws IOException {
		Double a1= (Double) helpers().excel()
				.open(fileExcel)
				.sheet(0)
				.cell("A1")
				.build();

		String formula= (String) helpers().excel()
				.open(fileExcel)
				.sheet(0)
				.cell("A3")
				.build();

		Assert.assertTrue(new Double(10).equals(a1));
		Assert.assertTrue("SUM(A1:A2)".equals(formula));
	}

	@Test
	public void totalSheets() throws IOException {

		Integer integer = helpers().excel()
				.open(fileExcel)
				.totalSheets();

		Assert.assertTrue(new Integer(1).equals(integer));
	}



}

