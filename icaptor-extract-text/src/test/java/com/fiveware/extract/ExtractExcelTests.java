package com.fiveware.extract;

import com.fiveware.PojoExcel;
import com.fiveware.dsl.excel.IExcel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static com.fiveware.dsl.excel.IExcel.Formula;
import static com.fiveware.dsl.pdf.Helpers.helpers;

public class ExtractExcelTests {

	String fileExcel;
	@Before
	public void setup() throws IOException, IllegalAccessException, InstantiationException {
		String rootDir = Paths.get(".").toAbsolutePath().normalize().toString();
		fileExcel=rootDir + File.separator + "sal.xls";

	}

	@Test
	public void extractExcelAndPopulatePojo() throws IOException {
		IExcel excel = helpers().excel()
				.open(fileExcel)
				.createSheet("testeFormula")
				.cell("A1", 50.0D)
				.cell("A2", 30.0D)
				.cell(Formula("A3", "SUM(A1:A2)"));

				excel.build();


		PojoExcel pojo = (PojoExcel) excel
				.convert("A1", "valor1", PojoExcel.class)
				.convert("A2", "valor2", PojoExcel.class)
				.convert("A3", "total", PojoExcel.class)
				.toObject();


		Assert.assertTrue(new Double(50).equals(pojo.getValor1()));
		Assert.assertTrue(new Double(30).equals(pojo.getValor2()));
		Assert.assertTrue("SUM(A1:A2)".equals(pojo.getTotal()));

		excel.deleteSheet().build();
    }

	@Test
	public void readObject() throws IOException, IllegalAccessException, InstantiationException {

		PojoExcel pojo = new PojoExcel();
		pojo.setValor1(50D);
		pojo.setValor2(30D);
		pojo.setTotal("=SUM(A2:B2)");

		IExcel excel = helpers().excel()
				.open(fileExcel)
				.createSheet("testeFormula")
				.readObject("A1", pojo)
				.build();


		Double b2 = (Double) excel.cell("B2").build();
		excel.deleteSheet().build();

		Assert.assertTrue(new Double(30).equals(b2));
	}


	@Test
	public void totalSheets() throws IOException {

		Integer integer = helpers().excel()
				.open(fileExcel)
				.totalSheets();

		Assert.assertTrue(new Integer(1).equals(integer));
	}


}

