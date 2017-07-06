package com.fiveware;

import com.fiveware.dsl.TypeSearch;
import com.fiveware.model.Record;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.fiveware.dsl.pdf.Helpers.FromTo;
import static com.fiveware.dsl.pdf.Helpers.helpers;

/**
 * Created by valdisnei on 06/07/17.
 */
public class ExtractPdfTest {


    @Test
    public void extract(){
        String path = "";
        Map map = new HashMap();
        map.put(FromTo("cnpj: ","cnpj"), TypeSearch.CNPJ);
        map.put("icms",  TypeSearch.MONEY);
        map.put(FromTo("- ","valorpagar"),TypeSearch.MONEY);
        map.put("vencimento",  TypeSearch.DATE);
        map.put(FromTo("referência: ","referencia"),"[0-9]{2}/[0-9]{4}");
        map.put(FromTo("Conta","numeroconta")," ([0-9]{10})");

        Record record = new Record();

        helpers()
                .pdf()
                .open(path)
                .writeRecord(map, record).build();
    }
}
