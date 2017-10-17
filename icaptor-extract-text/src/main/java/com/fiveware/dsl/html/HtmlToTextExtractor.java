package com.fiveware.dsl.html;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlToTextExtractor {

	public static String convertTableToText(String html) {

		String result = "";
		Document jsoup = null;

		jsoup = Jsoup.parse(html);
		Elements rows = jsoup.select("tr");
		for (Element row : rows) {
			Elements tds = row.select("td,th");
			for (Element td : tds) {
				result += td.text() + "\t";
			}
			result += "\n";

		}

		return result;

	}

	

}
