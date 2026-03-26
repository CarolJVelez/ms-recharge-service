# ms-recharge-service

Prueba técnica backend desarrollada con **Java + Spring Boot**, orientada a la autenticación de usuarios, compra de recargas móviles y consulta de historial de transacciones.

## Descripción general

Este proyecto implementa una API REST para:

- Autenticar usuarios mediante JWT
- Procesar recargas móviles
- Consultar el historial de recargas del usuario autenticado

Aunque la prueba original fue planteada en **Nest.js con TypeScript**, en esta solución se desarrolló el mismo alcance funcional usando **Spring Boot**, manteniendo las reglas de negocio, la seguridad, la persistencia y una estructura por capas inspirada en **DDD**.

## Alcance implementado

Actualmente el proyecto cubre los siguientes puntos de la prueba:

### Nivel 0
- endpoint `POST /auth/login`
- Autenticación con JWT
- Configuración del secreto y valores sensibles mediante variables de entorno

### Nivel 1
- endpoint `POST /recharges/buy`
- Validación de reglas de negocio para monto y número celular
- Protección de rutas con JWT

### Nivel 2
- Persistencia de recargas en base de datos
- Historial de recargas del usuario autenticado
- Integración con PostgreSQL usando Docker
- Migraciones con Flyway

### Nivel 3
- Se implementaron pruebas unitarias para parte de la lógica de negocio
- Las pruebas de integración fueron planteadas, aunque su configuración no quedó completamente estabilizada

### Nivel 4
- Organización del proyecto en capas inspiradas en DDD:
    - `domain`
    - `application`
    - `infrastructure`

### Bonus
- Publicación de evento de dominio `RechargeSucceededEvent` después de una recarga exitosa
- Integración con Kafka para publicar y consumir el evento

## Tecnologías usadas

- Java 11
- Spring Boot 2.7.x
- Spring Web
- Spring Security
- Spring Data JPA
- PostgreSQL
- Flyway
- JWT
- Gradle
- Docker / Docker Compose
- Lombok
- MapStruct
- OpenAPI / Swagger 
- Apache Kafka
- Spring for Apache Kafka

## Cómo ejecutar el proyecto

### 1. Configurar variables de entorno

Antes de ejecutar la aplicación, se deben definir las variables necesarias para la base de datos, JWT y Kafka.

### Variables requeridas

- `SERVER_PORT`
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET`
- `JWT_EXPIRATION`
- `KAFKA_BOOTSTRAP_SERVERS`
- `KAFKA_RECHARGE_TOPIC`
- `KAFKA_RECHARGE_GROUP`

### Ejemplo de archivo `.env.example`

```env
SERVER_PORT=8081
DB_URL=jdbc:postgresql://localhost:5440/ms-recharge
DB_USERNAME=postgres
DB_PASSWORD=postgres
JWT_SECRET=replace_with_a_secure_secret_of_at_least_32_chars
JWT_EXPIRATION=3600000
KAFKA_BOOTSTRAP_SERVERS=localhost:9092
KAFKA_RECHARGE_TOPIC=recharge-succeeded
KAFKA_RECHARGE_GROUP=recharge-service-group
```
### 2. Levantar infraestructura con Docker

```docker
docker compose up -d
```
Este comando levanta los servicios necesarios para la ejecución local del proyecto:

- PostgreSQL
- Kafka 
- Zookeeper

## Arquitectura del proyecto

El proyecto fue organizado siguiendo una estructura por capas inspirada en **Domain-Driven Design (DDD)**, con el objetivo de separar responsabilidades y evitar que la lógica de negocio quedara mezclada con detalles de infraestructura.

### Domain
Contiene el núcleo del negocio:
- Modelos del dominio
- Reglas de validación
- Puertos
- Excepciones

En esta capa se definieron las validaciones principales de la recarga, por ejemplo:
- Monto mínimo y máximo
- Formato del número celular

### Application
Contiene los casos de uso y la orquestación de la lógica:
- Handlers
- DTOs
- Mapeos
- Servicios de aplicación

Esta capa coordina la entrada y salida de datos entre el dominio y la infraestructura.

### Infrastructure
Contiene todo lo relacionado con tecnología y acceso externo:
- Controladores REST
- Seguridad JWT
- Configuración
- Repositorios JPA
- Adaptadores
- Persistencia

## Manejo de eventos de dominio (Bonus)

Como parte del bonus, se implementó la publicación de eventos de dominio después de una recarga exitosa.

Cuando una recarga se registra correctamente, el caso de uso publica un `RechargeSucceededEvent`. Para esto se usó una abstracción `EventBus`, con el fin de desacoplar la lógica del dominio de la infraestructura de mensajería.

La implementación concreta utiliza Kafka como mecanismo de publicación y consumo de eventos. El productor publica el evento en el tópico configurado para recargas exitosas, y un consumidor lo recibe mediante `@KafkaListener`.

En esta versión, el consumidor registra un log al recibir el evento. Esto deja preparada la solución para extender el flujo en el futuro con notificaciones, auditoría o integración con otros servicios sin afectar la lógica principal de recargas.

## Decisiones técnicas

### 1. Uso de Spring Boot en lugar de Nest.js
La prueba fue planteada inicialmente en Nest.js, pero se decidió implementar la solución en **Spring Boot** porque el objetivo principal del ejercicio era resolver el problema de negocio y no limitarse a un framework específico.

Spring Boot permite cubrir el mismo alcance funcional:
- Autenticación con JWT
- Validaciones
- Persistencia
- Endpoints REST
- Separación por capas
- Configuración por variables de entorno

### 2. Autenticación con correo y contraseña
En lugar de usar `username + password`, se implementó autenticación con **email + password**.

Esta decisión se tomó por varias razones:

- En sistemas reales, el correo suele ser un identificador más natural para autenticación
- Evita crear un campo adicional solo para login
- Facilita la unicidad del usuario
- Hace más simple la consulta del usuario en base de datos
- Mejora la trazabilidad y la coherencia con escenarios reales

En el enunciado se muestra `username` dentro de un esquema sugerido, pero no como una restricción obligatoria. Por eso se optó por una implementación más cercana a un caso real.
Ademas, siguiendo el principio de YAGNI se optó por solo realizar los endpoints y desarrollo de lo pedido en la prueba, por esta razón se carga un usuario de prueba mediante migración Flyway para facilitar la validación del flujo de autenticación..

### 3. Uso de PostgreSQL en lugar de SQLite
La prueba propone SQLite como alternativa sencilla, pero también menciona PostgreSQL con Docker como bonus.

Se eligió **PostgreSQL** porque:
- Permite una solución más cercana a un entorno real
- Facilita la escalabilidad del proyecto
- Se integra muy bien con Docker para una ejecución local simple
- Deja una base preparada para futuras extensiones del dominio

### 4. Paginación en el historial en lugar de lista simple
El endpoint de historial fue implementado con respuesta paginada y no como una lista simple.

Esto se hizo porque, aunque una lista funciona bien para pocos registros, en un sistema real el historial de transacciones puede crecer rápidamente. La paginación permite:

- Controlar mejor la cantidad de datos por respuesta
- Reducir consumo de memoria y tiempo de respuesta
- Mejorar la experiencia del cliente consumidor
- Dejar el endpoint preparado para crecer sin cambios estructurales posteriores

Por esa razón, el historial devuelve:
- `content`
- `page`
- `size`
- `totalElements`
- `totalPages`

En otras palabras, se privilegió una solución más mantenible y escalable frente a una respuesta plana.

### 5. Publicación de eventos con Kafka

Como parte del bonus del ejercicio, se implementó la publicación de eventos después de una recarga exitosa.

Se eligió Kafka porque permite desacoplar la operación principal de recarga respecto a procesos secundarios, por ejemplo auditoría, notificaciones o trazabilidad. Para mantener la solución simple, el evento actualmente es consumido por un listener que registra un log, dejando la base lista para futuras extensiones.

## Reglas de negocio implementadas

### Recarga
- valor mínimo: `1.000`
- valor máximo: `100.000`

### Número celular
- debe iniciar en `3`
- debe tener exactamente `10` caracteres
- solo debe contener números

### Seguridad
- todas las rutas, excepto login, requieren un token JWT válido

## Usuario de prueba cargado por Flyway

Para facilitar la ejecución y las pruebas, se creó un usuario inicial mediante una migración Flyway.

### Credenciales de prueba
- **email:** `caroljvelez@gmail.com`
- **password:** `Caroljvelez1234`

Esto permite probar el flujo completo desde el inicio.

## Endpoints disponibles

---

## 1. Login

### Endpoint
`POST /auth/login`

### Descripción
Autentica al usuario y retorna un token JWT válido.

### Request
```json
{
  "email": "caroljvelez@gmail.com",
  "password": "Caroljvelez1234"
}
```
### Ejemplo curl
```curl 
curl --location 'localhost:8081/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "caroljvelez@gmail.com",
    "password": "Caroljvelez1234"
}'
```
### Response
```json 
{
  "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjYXJvbGp2ZWxlejFAZ21haWwuY29tIiwidXNlcklkIjoyLCJuYW1lIjoiQ2Fyb2wiLCJpYXQiOjE3NzQ0OTYyMDIsImV4cCI6MTc3NDQ5OTgwMn0.fOPPUVCf2Svs-NNg143YmlHSBRDtG_ZdTM8Rg8QKu8g",
  "access_token_type": "Bearer",
  "expires_in": 3600
}
```
### Consideración

La respuesta incluye no solo el token sino también el tipo y el tiempo de expiración, para que el cliente tenga la información necesaria sin asumir valores por defecto.

## 2. Recarga de Móviles

### Endpoint
`POST /recharges/buy`

### Descripción
Procesa una nueva recarga móvil para el usuario autenticado. Es importante resaltar que no existe restricción al recargar un movil diferente al del cliente, es decir, el cliente puede recargar cualquier numero de telefono. 

### Headers requeridos
- `Authorization: Bearer <token>`

### Request
```json
{
  "amount": 10000,
  "phoneNumber": "3172985405"
}
```
### Ejemplo curl
```curl 
curl --location 'localhost:8081/recharges/buy' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <token>' \
--data '{
  "amount": 10000,
  "phoneNumber": "3172985405"
}'
```
### Response
```json 
{
  "id": "b362702b-cb71-4093-8ef2-e54165174fd5",
  "phoneNumber": "3172985405",
  "amount": 10000,
  "userId": 2,
  "createdAt": "2026-03-25T22:34:30.4036566"
}
```
### Validaciones aplicadas

- El monto debe estar entre 1000 y 100000
- El número debe iniciar en 3
- El número debe tener 10 dígitos
- El número solo puede contener valores numéricos

## 3. Historial de recargas

### Endpoint
`GET /recharges/history?page=0&size=2`

### Descripción
Retorna el historial de recargas del usuario autenticado de forma paginada.

### Headers requeridos
- `Authorization: Bearer <token>`

### Ejemplo curl
```curl 
curl --location 'localhost:8081/recharges/history?page=0&size=2' \
--header 'Authorization: Bearer <token>'
```
### Response
```json 
{
  "content": [
    {
      "id": "cef4ecfa-9526-4872-abab-a3af88c365ea",
      "phoneNumber": "3172985404",
      "amount": 1000,
      "userId": 2,
      "createdAt": "2026-03-25T21:49:36.840958"
    },
    {
      "id": "3c5a5c5e-e5f1-404e-9132-914a89419215",
      "phoneNumber": "3172985405",
      "amount": 1000,
      "userId": 2,
      "createdAt": "2026-03-25T21:50:11.811303"
    }
  ],
  "page": 0,
  "size": 2,
  "totalElements": 6,
  "totalPages": 3
}
```
### Consideración sobre la respuesta paginada

Se prefirió una respuesta paginada sobre una lista simple porque el historial de transacciones es una consulta que naturalmente puede crecer con el tiempo. Con paginación, el cliente puede consumir la información por bloques y el backend evita cargar todo el historial en una sola respuesta.

### Validaciones aplicadas

- El monto debe estar entre 1000 y 100000
- El número debe iniciar en 3
- El número debe tener 10 dígitos
- El número solo puede contener valores numéricos

## Cumplimiento de la prueba

- Nivel 0: completado
- Nivel 1: completado
- Nivel 2: completado
- Nivel 3: parcial
- Nivel 4: completado
- Bonus: implementado mediante publicación y consumo de eventos con Kafka