# API-REST ABOUT REGISTER USER
Esta API-REST permite registrar usuarios en la base de datos de la aplicación
tambien nos permite hacer login y obtener un token al poder inicial sesion correctamente.

# Getting Started
- Para poder ejecutar la aplicacion necesita tener JDK 17 y Maven instalado en su maquina.
- tambien en caso de persitir la base de datos necesita tener instalado MySQL en su maquina o h2 que es una base de datos in memory.
- puede clonar el repositorio y ejecutar el comando `mvn spring-boot:run` para ejecutar la aplicacion.
- la aplicacion se ejecutara en el puerto 8003 y la url base es `http://localhost:8003/api/v1/`
- para poder ver la documentacion de la API puede acceder a la url `http://localhost:8080/api/v1/swagger-ui.html`
- para poder ver la base de datos h2 puede acceder a la url `http://localhost:8080/api/v1/h2-console` y usar las siguientes credenciales:
  - JDBC URL: jdbc:h2:mem:testdb
  - User Name: admin
  - Password: admin

# Payload de la API
- para poder registrar un usuario necesita enviar una peticion POST a la url `http://localhost:8003/api/v1/users/register` con el siguiente body:
- Request:  
- ```json
  {
    "name": "Jhon Doe",
    "email": "Jhon@gmail.com",
    "password": "Jhon",
    "phones": [
      {
        "number": "1234567",
        "citycode": "1",
        "countrycode": "57"
      }
    ]
  }
  ``
- Response:
  ```json
  {
    "id": "d1e1d1e1-1d1e-1d1e-1d1e-1d1e1d1e1d1e",
    "created": "2021-10-10T00:00:00.000Z",
    "modified": "2021-10-10T00:00:00.000Z",
    "lastlogin": "2021-10-10T", 
    "token":"JSkejwqk9",
    "active": true
  }
  ```
- para poder hacer login necesita enviar una peticion POST a la url `http://localhost:8003/api/v1/users/login` con el siguiente body:
- Request:  
- ```json
  {
    "email": "admin@gmail.com",
    "password": "admin"
  }
  ```
- Response:
  ```json
  {
    "Token": "Jajdjjkxsnwepnainppsnanspnscnpnianasn-9-302dj2n00n12-nieodlaljdcl"
  }
  ```
# Technologies used
- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Security
- H2 Database
- Lombok
- Swagger
- Junit
- Mockito
- Maven
- SpringDoc OpenAPI -Documentation

# Author
-Starling Javier Diaz Aquino
- Email:starlingdiaz70@gmail.com
- Linkedin: https://www.linkedin.com/in/starling-diaz-aquino-908225181/
- Github: http://github.com/NSTLRD

# License
This project is licensed under the MIT License - see the LICENSE.md file for details.
```