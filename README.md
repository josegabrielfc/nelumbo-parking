# 🚗 Sistema de Gestión de Parqueaderos - Nelumbo Parking API

API REST desarrollada con Spring Boot para la gestión de parqueaderos, control de vehículos y generación de indicadores de negocio.

## 📋 Tabla de Contenidos

- [Características](#características)
- [Tecnologías](#tecnologías)
- [Requisitos Previos](#requisitos-previos)
- [Instalación y Configuración](#instalación-y-configuración)
- [Ejecución del Proyecto](#ejecución-del-proyecto)
- [Arquitectura](#arquitectura)
- [Endpoints Principales](#endpoints-principales)
- [Usuarios por Defecto](#usuarios-por-defecto)
- [Variables de Entorno](#variables-de-entorno)

---

## ✨ Características

- ✅ **Autenticación JWT** con roles y permisos
- ✅ **CRUD de Parqueaderos** (solo Admin)
- ✅ **CRUD de Usuarios Socio** (solo Admin)
- ✅ **Registro de Entrada/Salida de Vehículos** con validaciones
- ✅ **Cálculo automático de costos** basado en tiempo de permanencia
- ✅ **Indicadores de Negocio** para Admin y Socio
- ✅ **Notificaciones por Email** con microservicio independiente
- ✅ **Arquitectura Hexagonal** (Ports & Adapters)
- ✅ **Base de datos SQLite** con persistencia local

---

## 🛠️ Tecnologías

- **Java 21**
- **Spring Boot 3.4.1**
  - Spring Web
  - Spring Data JPA
  - Spring Security
  - Spring Validation
- **JWT (jjwt 0.12.6)**
- **SQLite** con Hibernate Community Dialects
- **Lombok**
- **Gradle 8.x**

---

## 📦 Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- **Java 21** o superior ([Descargar aquí](https://www.oracle.com/java/technologies/downloads/))
- **Gradle 8.x** (opcional, el proyecto incluye Gradle Wrapper)
- **Git** (para clonar el repositorio)

---

## ⚙️ Instalación y Configuración

### 1. Clonar el Repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
cd parking
```

### 2. Configurar Variables de Entorno

Crea un archivo `.env` en la raíz del proyecto con el siguiente contenido:

```env
DB_URL=jdbc:sqlite:parking.db
JWT_SECRET=tu_clave_secreta_super_segura_aqui_minimo_256_bits
JWT_EXPIRATION=21600000
```

### 3. Verificar Dependencias

El proyecto usa **Gradle Wrapper**, por lo que no necesitas instalar Gradle manualmente. Las dependencias se descargarán automáticamente.

---

## 🚀 Ejecución del Proyecto

### Opción 1: Usando Gradle Wrapper (Recomendado)

**En Windows:**

```bash
./gradlew bootRun
```

**En Linux/Mac:**

```bash
./gradlew bootRun
```

### Opción 2: Usando tu IDE

1. Abre el proyecto en **IntelliJ IDEA** o **Eclipse**
2. Espera a que se descarguen las dependencias
3. Ejecuta la clase principal: `ParkingApplication.java`

### Opción 3: Generar y ejecutar el JAR

```bash
.\gradlew build
java -jar build/libs/parking-0.0.1-SNAPSHOT.jar
```

---

## 🌐 Acceso a la API

Una vez iniciado el servidor, la API estará disponible en:

```
http://localhost:8080
```

---

## 🏗️ Arquitectura

El proyecto sigue la **Arquitectura Hexagonal (Ports & Adapters)** con la siguiente estructura:

```
src/main/java/nelumbo/api/parking/
├── domain/                    # Capa de Dominio (Lógica de Negocio)
│   ├── model/                 # Entidades de dominio
│   ├── port/
│   │   ├── in/                # Puertos de entrada (Use Cases)
│   │   └── out/               # Puertos de salida (Repositorios)
│   └── exception/             # Excepciones personalizadas
│
├── application/               # Capa de Aplicación
│   └── service/               # Implementación de Use Cases
│
└── infrastructure/            # Capa de Infraestructura
    ├── adapter/
    │   ├── in/web/            # Controladores REST
    │   └── out/persistence/   # Adaptadores de BD
    ├── config/                # Configuraciones (Security, JWT, etc.)
    └── exception/             # Manejador global de excepciones
```

---

## 📡 Endpoints Principales

### 🔐 Autenticación

| Método | Endpoint          | Descripción                   |
| ------ | ----------------- | ----------------------------- |
| POST   | `/api/auth/login` | Iniciar sesión (devuelve JWT) |

**Ejemplo de Login:**

```json
POST /api/auth/login
{
  "email": "admin@mail.com",
  "password": "admin"
}
```

**Respuesta:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

### 👥 Usuarios (Solo ADMIN)

| Método | Endpoint     | Descripción         |
| ------ | ------------ | ------------------- |
| POST   | `/api/users` | Crear usuario SOCIO |

---

### 🅿️ Parqueaderos (Solo ADMIN)

| Método | Endpoint                        | Descripción                     |
| ------ | ------------------------------- | ------------------------------- |
| POST   | `/api/parkings`                 | Crear parqueadero               |
| PUT    | `/api/parkings/{id}`            | Actualizar parqueadero          |
| DELETE | `/api/parkings/{id}`            | Eliminar parqueadero            |
| GET    | `/api/parkings`                 | Listar todos                    |
| GET    | `/api/parkings/{id}`            | Obtener por ID                  |
| GET    | `/api/parkings/socio/{socioId}` | Listar parqueaderos de un socio |

---

### 🚗 Gestión de Vehículos

| Método | Endpoint                           | Descripción                   | Rol         |
| ------ | ---------------------------------- | ----------------------------- | ----------- |
| POST   | `/api/check-in`                    | Registrar entrada de vehículo | SOCIO       |
| POST   | `/api/check-out`                   | Registrar salida de vehículo  | SOCIO       |
| GET    | `/api/vehicles/active/{parkingId}` | Listar vehículos activos      | ADMIN/SOCIO |

---

### 📧 Notificaciones por Email

| Método | Endpoint          | Descripción                                                | Rol   |
| ------ | ----------------- | ---------------------------------------------------------- | ----- |
| POST   | `/api/email/send` | Enviar correo (valida que la placa esté en el parqueadero) | ADMIN |

**Ejemplo de Request:**

```json
POST /api/email/send
{
  "email": "cliente@example.com",
  "placa": "ABC123",
  "mensaje": "Su vehículo ha sido registrado exitosamente",
  "parqueaderoId": 1
}
```

**Respuesta:**

```json
{
  "mensaje": "Correo Enviado"
}
```

> **Nota:** Este endpoint valida que la placa especificada se encuentre actualmente en el parqueadero indicado. Si no se encuentra, retorna un error 404.

---

### 📊 Indicadores

#### Para ADMIN:

| Método | Endpoint                                    | Descripción                                   |
| ------ | ------------------------------------------- | --------------------------------------------- |
| GET    | `/api/indicators/admin/top-vehicles`        | Top 10 vehículos más frecuentes               |
| GET    | `/api/indicators/admin/top-socios`          | Top 3 socios con más ganancias                |
| GET    | `/api/indicators/admin/top-parkings`        | Top 10 parqueaderos con más flujo             |
| GET    | `/api/indicators/admin/weekly-top-socios`   | Top 3 socios con más ingresos (semana)        |
| GET    | `/api/indicators/admin/weekly-top-parkings` | Top 3 parqueaderos con más ganancias (semana) |

#### Para SOCIO:

| Método | Endpoint                                         | Parámetros                         | Descripción                 |
| ------ | ------------------------------------------------ | ---------------------------------- | --------------------------- |
| GET    | `/api/indicators/socio/earnings/{socioId}`       | `?period=today\|week\|month\|year` | Ganancias del socio         |
| GET    | `/api/indicators/parking/earnings/{parkingId}`   | `?period=today\|week\|month\|year` | Ganancias de un parqueadero |
| GET    | `/api/indicators/socio/first-timers/{parkingId}` | -                                  | Vehículos primerizos        |

---

## 👤 Usuarios por Defecto

Al iniciar la aplicación por primera vez, se crea automáticamente un usuario administrador:

| Email            | Password | Rol   |
| ---------------- | -------- | ----- |
| `admin@mail.com` | `admin`  | ADMIN |

> **Importante:** Cambia estas credenciales en producción.

---

## 🔑 Variables de Entorno

| Variable         | Descripción                         | Valor por Defecto        |
| ---------------- | ----------------------------------- | ------------------------ |
| `DB_URL`         | URL de conexión a SQLite            | `jdbc:sqlite:parking.db` |
| `JWT_SECRET`     | Clave secreta para firmar JWT       | (Requerido)              |
| `JWT_EXPIRATION` | Tiempo de expiración del token (ms) | `21600000` (6h)          |

---

## 🧪 Pruebas con Postman/Thunder Client

1. **Login:**
   - POST `http://localhost:8080/api/auth/login`
   - Body: `{ "email": "admin@mail.com", "password": "admin" }`
   - Copia el `token` de la respuesta

2. **Usar el Token:**
   - En las siguientes peticiones, agrega el header:
   - `Authorization: Bearer <TU_TOKEN_AQUI>`

3. **Crear un Socio:**
   - POST `http://localhost:8080/api/users`
   - Body:

   ```json
   {
     "name": "Juan Pérez",
     "email": "juan@mail.com",
     "password": "123456"
   }
   ```

4. **Crear un Parqueadero:**
   - POST `http://localhost:8080/api/parkings`
   - Body:
   ```json
   {
     "name": "Parqueadero Central",
     "capacity": 50,
     "costPerHour": 3000.0,
     "socioId": 2
   }
   ```

---

## 📝 Notas Importantes

- La base de datos SQLite se crea automáticamente en la raíz del proyecto (`parking.db`)
- Las fechas se almacenan en formato `YYYY-MM-DD HH:MM:SS`
- Las placas deben tener exactamente 6 caracteres alfanuméricos (sin ñ)
- El costo se calcula automáticamente al registrar la salida

---

## 🤝 Contribuciones

Este proyecto fue desarrollado siguiendo principios de Clean Architecture y SOLID.

---

## 📄 Licencia

Este proyecto es de uso empresarial.
