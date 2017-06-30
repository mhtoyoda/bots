# icaptor-automation


Start Server (cloud)
-  ```java -jar target/icaptor-cloud-0.0.1-SNAPSHOT.jar``` 

start client (agent)
- ```java -Dspring.config.location=file:$HOME/.icaptor-out-file/icaptor-platform.properties -jar target/icaptor-agent-0.0.1-SNAPSHOT.jar```

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
        helpers().html()
        				.open(url)
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
