# icaptor-automation


Start Server (cloud)
-  ```java -jar target/icaptor-cloud-0.0.1-SNAPSHOT.jar``` 

start client (agent)
- ```java -Dspring.config.location=file:$HOME/.icaptor-out-file/icaptor-platform.properties -jar target/icaptor-agent-0.0.1-SNAPSHOT.jar```

## FrameWork

* Pré-Requisito 

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
import static com.fiveware.dsl.Extract.extract;
public class Exemplo1 {

    public static void main(String[] args){
       String url="http://www.globo.com.br";
       String outPathFile = "/out.pdf";
        extract().html().open(url).outPutFile(outPathFile).buildToFile();
    }        
}
```

Exemplo - 2 
---
* Extrai texto de um arquivo PDF

```java
import static com.fiveware.dsl.Extract.extract;
import com.fiveware.dsl.TypeSearch;
public class Exemplo2 {

    public static void main(String[] args){
       String outPathFile = "/out.pdf";
       String dolar = extract().pdf().open(outPathFile)
       				.search("dólar", TypeSearch.MONEY)
       				.build();
    }        
}
```

Exemplo - 3 
---
* Extrai texto de um arquivo PDF e popula os resultados em um Objeto

```java

import java.util.HashMap;
import java.util.Map;

import static com.fiveware.dsl.Extract.FromTo;
import static com.fiveware.dsl.Extract.extract;
import com.fiveware.dsl.TypeSearch;

public class Exemplo3 {

    public static void main(String[] args){
       String outPathFile = "/out.pdf";
      	Map map = new HashMap();
      		map.put(FromTo("cnpj:","cnpj"),TypeSearch.CNPJ);
      		map.put("icms",  TypeSearch.MONEY);
      		map.put(FromTo("Total a Pagar - ","valorpagar"),TypeSearch.MONEY);
      		map.put("vencimento",  TypeSearch.DATE);
      		map.put(FromTo("Data de emissão: ","dataemissao"),TypeSearch.DATE);
      		map.put(FromTo("Conta","numeroconta")," ([0-9]{10})");
      
      		Pojo pojo = (Pojo) extract()
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