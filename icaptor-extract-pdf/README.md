# icaptor-automation - FrameWork

Converte Html em PDF

Uso
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