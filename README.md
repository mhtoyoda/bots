# icaptor-automation


Start Server (cloud)
-  ```java -jar target/icaptor-cloud-0.0.1-SNAPSHOT.jar``` 

start client (agent)
- ```java -Dspring.config.location=file:$HOME/.icaptor-out-file/icaptor-platform.properties -jar target/icaptor-agent-0.0.1-SNAPSHOT.jar```

## FrameWork

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
* Extrair texto de um arquivo PDF

```java
import static com.fiveware.dsl.Extract.extract;
public class Exemplo2 {

    public static void main(String[] args){
       String outPathFile = "/out.pdf";
       String dolar = extract().pdf().open(outPathFile)
       				.search("dólar", TypeSearch.MONEY)
       				.build();
    }        
}
```