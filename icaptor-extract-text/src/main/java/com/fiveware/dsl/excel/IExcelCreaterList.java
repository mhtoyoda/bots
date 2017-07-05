package com.fiveware.dsl.excel;

import java.io.IOException;

/**
 * Created by valdisnei on 03/07/17.
 */
public interface IExcelCreaterList {

    IExcelCreaterList readObject(String reference, Object object) throws IllegalAccessException, InstantiationException;

    IExcel build() throws IOException;
}
