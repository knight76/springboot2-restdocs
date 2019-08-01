### SpringBoot 2.1 & Spring Rest Docs & Gradle Example

### Compile & Run boot
```
gradle clean build bootRun
```

### Create snippet(asciidoc) & Build
```
gradle clean build
```
created build/asciidoc/html5


### Copy generated files to resources/static (can be used by gradle)
Copy build/asciidoc/html5/*.adoc to resources/static


### Run Springboot

```
java -jar build/libs/api-restdocs-0.0.1-SNAPSHOT.jar
```


### Api-docs URL
```
http://localhost:8080/api-docs.html
```
