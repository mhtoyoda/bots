package com.fiveware.merge;

import com.fiveware.Pojo;
import com.fiveware.dsl.TypeSearch;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.fiveware.dsl.Helpers.FromTo;
import static com.fiveware.dsl.Helpers.helpers;
import static org.junit.Assert.assertEquals;

/**
 * Created by valdisnei on 03/07/17.
 */
public class PdfXExcelTests {

    String path;
    String outPathFile;
    String url;
    String fileExcel;
    String outFileHtml;
    Pojo pojo;
    @Before
    public void setup(){
        String rootDir = Paths.get(".").toAbsolutePath().normalize().toString();
        fileExcel=rootDir + File.separator + "sal.xls";
        path =rootDir + File.separator + "VOTORANTIM_ENERGIA_LTDA_0282682038_03-2017.pdf";

        Map map = new HashMap();
        map.put(FromTo("cnpj: ","cnpj"), TypeSearch.CNPJ);
        map.put("icms",  TypeSearch.MONEY);
        map.put(FromTo("- ","valorpagar"),TypeSearch.MONEY);
        map.put("vencimento",  TypeSearch.DATE);
        map.put(FromTo("Data de emissão: ","dataemissao"),TypeSearch.DATE);
        map.put(FromTo("Conta","numeroconta")," ([0-9]{10})");

        pojo= (Pojo) helpers()
                .pdf()
                .open(path)
                .converter().map(map, Pojo.class).build();


        assertEquals("02.558.157/0001-62",pojo.getCnpj());
        assertEquals("R$18,32",pojo.getValorpagar());
        assertEquals("R$ 4,25",pojo.getIcms());
        assertEquals("07/03/2017",pojo.getDataemissao());

        assertEquals("28/03/2017",pojo.getVencimento());
    }

    @Test
    public void populateSheet(){
        try {
            helpers().excel()
                    .open(fileExcel)
                    .sheet(0)
                    .readObject("H1",pojo)
                    .build();
        } catch (IOException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }


    }

}
