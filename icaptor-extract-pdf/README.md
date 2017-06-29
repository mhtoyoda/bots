# FrameWork

Converte Html em PDF

Como usar
---
```java
import static com.fiveware.dsl.Extract.extract;
public class Exemplo {

    public static void main(String[] args){
       String url="http://www.globo.com.br";
       String outPathFile = "/out.pdf";
        extract().html().open(url).outPutFile(outPathFile).buildToFile();
    }        
}
```

Extract texto de um arquivo PDF

Como usar
---
```java
import static com.fiveware.dsl.Extract.extract;
public class Exemplo {

    public static void main(String[] args){
       String outPathFile = "/out.pdf";
       String dolar = extract().pdf().open(outPathFile)
       				.search("dólar", TypeSearch.MONEY)
       				.build();
    }        
}
```