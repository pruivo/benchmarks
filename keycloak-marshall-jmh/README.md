Keycloak Marshalling Benchmark

```bash
mvn clean package
java --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.util.concurrent=ALL-UNNAMED -jar target/benchmarks.jar
```
