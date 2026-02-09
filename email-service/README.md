# Email Service - Microservicio de Simulación de Correo

Microservicio independiente para simular el envío de correos electrónicos.

## 🚀 Características

- Endpoint POST para recibir solicitudes de envío de correo
- Simulación de envío mediante logs en consola
- Validación de datos de entrada
- Puerto independiente (8081) para no interferir con el API principal

## 📋 Requisitos

- Java 21
- Gradle

## ⚙️ Configuración

El servicio corre por defecto en el puerto **8081**.

Puedes cambiar el puerto en `src/main/resources/application.properties`:

```properties
server.port=8081
```

## 🏃 Ejecutar el Servicio

### Opción 1: Usando Gradle Wrapper (Recomendado)

```bash
cd email-service
./gradlew bootRun
```

### Opción 2: Compilar y ejecutar JAR

```bash
cd email-service
./gradlew build
java -jar build/libs/email-service-0.0.1-SNAPSHOT.jar
```

## 📡 API Endpoints

### POST /api/email/send

Simula el envío de un correo electrónico.

**Request Body:**

```json
{
  "email": "usuario@example.com",
  "placa": "ABC123",
  "mensaje": "Su vehículo ha sido registrado exitosamente",
  "parqueaderoNombre": "Parqueadero Central"
}
```

**Response:**

```json
{
  "mensaje": "Correo Enviado"
}
```

**Status Code:** 200 OK

## 🧪 Probar el Servicio

### Usando cURL:

```bash
curl -X POST http://localhost:8081/api/email/send \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "placa": "ABC123",
    "mensaje": "Prueba de correo",
    "parqueaderoNombre": "Parqueadero Test"
  }'
```

### Usando PowerShell:

```powershell
$body = @{
    email = "test@example.com"
    placa = "ABC123"
    mensaje = "Prueba de correo"
    parqueaderoNombre = "Parqueadero Test"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8081/api/email/send" `
  -Method Post `
  -ContentType "application/json" `
  -Body $body
```

## 📝 Logs

Cuando se envía un correo, verás en la consola:

```
========================================
SIMULACIÓN DE ENVÍO DE CORREO
========================================
Destinatario: test@example.com
Placa: ABC123
Parqueadero: Parqueadero Test
Mensaje: Prueba de correo
========================================
Correo enviado exitosamente
========================================
```

## 🔧 Validaciones

El servicio valida:

- ✅ Email con formato válido
- ✅ Todos los campos son obligatorios
- ✅ No se aceptan valores vacíos

## 📦 Estructura del Proyecto

```
email-service/
├── src/
│   └── main/
│       ├── java/
│       │   └── nelumbo/
│       │       └── email/
│       │           ├── EmailServiceApplication.java
│       │           ├── controller/
│       │           │   └── EmailController.java
│       │           ├── service/
│       │           │   └── EmailService.java
│       │           └── dto/
│       │               ├── EmailRequest.java
│       │               └── EmailResponse.java
│       └── resources/
│           └── application.properties
├── build.gradle
└── README.md
```

## 🔗 Integración con API Principal

El API principal de parking se comunica con este microservicio a través del endpoint `/api/email/send`.

La URL del microservicio se configura en el `application.properties` del API principal:

```properties
email.service.url=http://localhost:8081
```
