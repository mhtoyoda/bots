package com.fiveware.extract;

import com.fiveware.Pojo;
import com.fiveware.dsl.TypeSearch;
import com.fiveware.dsl.excel.IExcel;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fiveware.dsl.pdf.Helpers.FromTo;
import static com.fiveware.dsl.pdf.Helpers.helpers;
import static org.junit.Assert.assertEquals;

/**
 * Created by valdisnei on 03/07/17.
 */
@Ignore
public class PdfXExcelTests {

    static Logger logger = LoggerFactory.getLogger(PdfXExcelTests.class);

    String path,pathFile2;
    String fileExcel;
    Pojo pojo,pojo2;

    @Before
    public void setup(){
        Path resourceDirectory = Paths.get("src/test/resources");
        String rootDir = resourceDirectory.normalize().toString();
        fileExcel=rootDir + File.separator + "excel.xls";
        path =rootDir + File.separator + "VOTORANTIM_ENERGIA_LTDA_0282682038_03-2017.pdf";
        pathFile2 =rootDir + File.separator + "VOTENER-VOTORANTIM COMERCIALIZADORA_0292026574_03-2017.pdf";


        String referencia = "ncia: ";

        Map map = new HashMap();
        map.put(FromTo("cnpj: ","cnpj"), TypeSearch.CNPJ);
        map.put("icms",  TypeSearch.MONEY);
        map.put(FromTo("- ","valorpagar"),TypeSearch.MONEY);
        map.put("vencimento",  TypeSearch.DATE);
        map.put(FromTo(referencia,"referencia"),"[0-9]{2}/[0-9]{4}");
        map.put(FromTo("Conta","numeroconta")," ([0-9]{10})");

        pojo= (Pojo) helpers()
                .pdf()
                .open(path)
                .writeObject().map(map, Pojo.class)
                .build();

        assertEquals("02.558.157/0001-62",pojo.getCnpj());
        assertEquals("R$18,32",pojo.getValorpagar());
        assertEquals("R$ 4,25",pojo.getIcms());
        assertEquals("03/2017",pojo.getReferencia());

        assertEquals("28/03/2017",pojo.getVencimento());



        pojo2= (Pojo) helpers()
                .pdf()
                .open(pathFile2)
                .writeObject().map(map, Pojo.class)
                .build();
    }

    @Test
    public void populateSheet(){
        try {

            List<Pojo> objects = Lists.newArrayList();
            objects.add(pojo);
            objects.add(pojo2);

            IExcel excel = helpers().excel()
                    .open(fileExcel)
                    .createSheet("importPDF")
                    .readObject("A1", objects)
                    .build();

            String valorPagar1 = (String) excel.cell("C2").build();
            String valorPagar2 = (String) excel.cell("C3").build();

            excel.deleteSheet().build();


            assertEquals("R$18,32",valorPagar1);
            assertEquals("R$771,65",valorPagar2);





        } catch (IOException | InstantiationException | IllegalAccessException e) {
            logger.error("{}",e);
        }




    }

}
