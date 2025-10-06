# Wb-Suche

## Maven

- api-profile:
    - `mvn clean package -Papi`
    - `mvn spring-boot:run -Papi`
- web-profile:
    - `mvn clean package -Pweb`

## Docker
- api-profile
    - `sudo docker build -t wb-suche-api:1 -f ./Dockerfile.api .`
    - `sudo docker run -p 8080:8080 -d wb-suche-api:1`
- web-profile
    - `sudo docker build -t wb-suche-web:1 -f ./Dockerfile.web .`
    - `sudo docker run -p 8080:8080 -d wb-suche-web:1`

---

- `Dockerfile.api`  
    Baut Spring-App, startet API -> `curl localhost:8080/search?template=[A-Z, a-z, _]&letters=[A-Z, a-z, ?]&page=[0-9]&pageSize=[0-9]&sortBy=alphabetical_asc` und OpenAPI-doc -> `localhost:8080/swagger-ui/index.html`
- `Dockerfile.web`  
    Baut /core, statische Dateien und startet Nginx-Webserver -> `curl localhost:8080/`
