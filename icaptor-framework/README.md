## FrameWork

* Pré-Requisito Mínimo
    * dependência:
```xml        
<dependency>
    <groupId>icaptor-framework</groupId>
    <artifactId>icaptor-framework</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

Exemplo - 1
---
* Converte Html em PDF

```java
import static com.fiveware.dsl.Helpers.helpers;
public class Exemplo1 {

    public static void main(String[] args){
       String url="http://www.globo.com.br";
       String outPathFile = "/out.pdf";
        helpers().html().open(url)
                        .outPutFile(outPathFile)
                        .buildToFile();
    }        
}
```

Exemplo - 2
---
* Converte de HTML para PDF e extrai texto  

```java
import static com.fiveware.dsl.Helpers.helpers;
public class Exemplo2 {

    public static void main(String[] args){
       String url="http://www.globo.com.br";
       String outPathFile = "/out.pdf";
       String dolar = extract().html().open(url).outPutFile(outPathFile)
     	 			  .search("dólar", TypeSearch.MONEY)
     				  .build();
    }        
}
```

Exemplo - 3
---
* Extrai texto de um elemento HTML  

```java
import static com.fiveware.dsl.Helpers.helpers;
public class Exemplo3 {

    public static void main(String[] args){
       String fileHtml="/Arquivo.html";
      
        String texto =  helpers().html()
                                 .file(fileHtml)
                                 .selectElement("div");
        
         String textoDoElemento =  helpers().html()
                                            .file(fileHtml)
                                            .selectElement("div")
                                            .text(5); //numero do elemento
                              				
    }        
}
```
Exemplo - 4 
---
* Extrai texto de um arquivo PDF

```java
import static com.fiveware.dsl.Helpers.helpers;
import com.fiveware.dsl.TypeSearch;
public class Exemplo3 {

    public static void main(String[] args){
       String path = "/out.pdf";
       String vencimento = helpers().pdf()
       				.open(path,1) 
       				.search("Vencimento", TypeSearch.DATE)
       				.build();
       
       String icms = helpers().pdf()
       				.open(path, 2)
       				.search("ICMS ",  TypeSearch.MONEY)
       				.build();
       
       String telefone = helpers().pdf()
       				.open(path, 6)
       				.search("Número",  "[0-9]{2}-[0-9]{5}-[0-9]{4}")
       				.build();
       
       Pattern pattern = Pattern.compile("\\s?([0-9]{2}.[0-9]{3}.[0-9]{3}\\/[0-9]{4}-[0-9]{2})");
       String cnpj = helpers().pdf()
       				.open(path,2)
       				.search("CNPJ: ", pattern)
       				.build();
       
       
    }        
}
```

Exemplo - 5 
---
* Extrai texto de um arquivo PDF e popula os resultados em um Objeto

```java

import java.util.HashMap;
import java.util.Map;

import static com.fiveware.dsl.Helpers.FromTo;
import static helpers;
import com.fiveware.dsl.TypeSearch;

public class Exemplo4 {

    public static void main(String[] args){
       String outPathFile = "/out.pdf";
      	Map map = new HashMap();
      		map.put(FromTo("cnpj:","cnpj"),TypeSearch.CNPJ);
      		map.put("icms",  TypeSearch.MONEY);
      		map.put(FromTo("Total a Pagar - ","valorpagar"),TypeSearch.MONEY);
      		map.put("vencimento",  TypeSearch.DATE);
      		map.put(FromTo("Data de emissão: ","dataemissao"),TypeSearch.DATE);
      		map.put(FromTo("Conta","numeroconta")," ([0-9]{10})");
      
      		Pojo pojo = (Pojo) helpers()
            				.pdf()
            				.open(path)
            				.converter().map(map, Pojo.class).build();
    }        
}

public class Pojo {
    private String cnpj;
    private String icms;
    private String valorpagar;
    private String vencimento;
    private String dataemissao;
    private String numeroconta;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getIcms() {
        return icms;
    }

    public void setIcms(String icms) {
        this.icms = icms;
    }

    public String getValorpagar() {
        return valorpagar;
    }

    public void setValorpagar(String valorpagar) {
        this.valorpagar = valorpagar;
    }

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    public String getDataemissao() {
        return dataemissao;
    }

    public void setDataemissao(String dataemissao) {
        this.dataemissao = dataemissao;
    }

    public String getNumeroconta() {
        return numeroconta;
    }

    public void setNumeroconta(String numeroconta) {
        this.numeroconta = numeroconta;
    }

}

```

Exemplo - 6
---
* Uso do BotAutomation

```java
import static com.fiveware.dsl.Helpers.helpers;
import static com.fiveware.automate.BotAutomationBuilder.Web;
import static com.fiveware.automate.BotWebBrowser.PHANTOM;

public class Exemplo3 {

    public static void main(String[] args){
        String baseUrl = "http://www.correios.com.br/";

        BotScreen telaConsultaCep = Web().driver(PHANTOM).openPage(baseUrl + "/para-voce");
        telaConsultaCep.windowMaximize();
        telaConsultaCep.find().elementBy().id("acesso-busca").sendKeys(args);		
        telaConsultaCep.find().elementBy().cssSelector("input.acesso-busca-submit").click();
        
        telaConsultaCep.waitForPageToLoadFor(400);
        
        		Iterator<String> windows = telaConsultaCep.iteratorWindowHandles();
        		windows.next();
        
        		telaConsultaCep.window(windows.next());
        
        		String resultado = telaConsultaCep.find()
        				.elementBy().xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/p").getText();
        
        		if ("DADOS NAO ENCONTRADOS".equalsIgnoreCase(resultado)){
        			return null;			
        		}
        
        		String logradouro = telaConsultaCep.find()
        				.elementBy().xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/table/tbody/tr[2]/td[1]")
        				.getText();
        		String bairro = telaConsultaCep.find()
        				.elementBy().xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/table/tbody/tr[2]/td[2]")
        				.getText();
        		String localidade = telaConsultaCep.find()
        				.elementBy().xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/table/tbody/tr[2]/td[3]")
        				.getText();
        		String cep = telaConsultaCep.find()
        				.elementBy().xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/table/tbody/tr[2]/td[4]")
        				.getText();
        
        		logger.info(" Endereco: {} - {} - {} - {}", logradouro, bairro, localidade, cep);
                              				
    }        
}
```
