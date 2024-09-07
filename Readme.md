# keycloak-restapi-extension
Build jar 
```sh
./gradlew build -x test
```

Copy `build/libs/keycloak-simple-spi-1.0.0.jar` and deploy to keycloak with path `/opt/keycloak/providers/keycloak-simple-spi-1.0.0.jar`

Testing SPI Keycloak rest extension with 
```sh
curl -v http://localhost:8080/auth/realms/master/custom-rest-api
```
You will get result
```json
{
    "message": "Hello Aphisit Namracha from custom Keycloak REST API!"
}
```
or if you test with
```sh
curl -v http://localhost:8080/auth/realms/master/custom-rest-api/custom
```

You will get result
```json
{
    "message": "Hello Aphisit Namracha from custom!"
}
```
