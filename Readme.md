# Aplicação de cadastro de cliente
Aplicação em javafx para cadastro de cliente
## Tecnologia
* Java 20
* Java Fx
* Postgresql

## Aplicação

| Coluna 1 | Coluna 2 |
|----------|----------|
| Valor    | valor 2  |

```java
public synchronized Connection getConexao() throws SQLException {
        if(conn == null){
            //conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ecommerce", "postgres", "postgres");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/ecommerce", "root", "");
        }

        return conn;
    }
```
