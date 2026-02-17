---
trigger: always_on
---

# Backend Rules — Hexagonal Architecture + Exceptions (Java)

Este backend está construido con **Arquitectura Hexagonal (Ports & Adapters)** usando **Java + Spring Boot**.

El agente DEBE respetar:

- Arquitectura Hexagonal (no violar capas)
- Principios SOLID
- DRY (no duplicar lógica)
- Separación de responsabilidades
- Código limpio y mantenible

Flujo obligatorio para nuevas funcionalidades:

> Dominio → Aplicación → Infraestructura → Web

---

# Estructura para Crear un Endpoint

## 1. Input Port (Dominio)

Ruta:

```
domain/port/in/
```

- Crear "Service" interfaz que se implementa en el caso de uso.
- Sin dependencias de Spring.

---

## 2. Output Ports (Dominio, si aplica)

Ruta:

```
domain/port/out/
```

- Interfaces para persistencia o servicios externos.

---

## 3. Use Case (Aplicación)

Ruta:

```
application/usecase/
```

- Clase termina en `UseCase`.
- `@Service`.
- Implementa el puerto de entrada.
- Contiene la lógica de negocio.

---

## 4. Persistencia (Infraestructura)

Ruta:

```
infrastructure/adapter/out/persistence/
```

Crear:

- Entidad JPA
- Repository Spring Data
- Adapter que implementa el Output Port

Sin lógica de negocio.

---

## 5. Web Adapter (Infraestructura)

### DTOs

```
infrastructure/adapter/in/web/dto/
```

# DTO Rules

- Usar `record`.
- Request / Response separados.
- Validaciones Jakarta.

### Mapper

```
infrastructure/adapter/in/web/mapper/
```

DTO ↔ dominio.

### Controller

```
infrastructure/adapter/in/web/controller/
```

Reglas:

- `@RestController`
- Inyectar **Input Port (interfaz)**, no implementación.
- Sin lógica de negocio.
- Seguridad con `@PreAuthorize` si aplica.

---

# Crear Endpoint (Resumen)

1. **Input Port** → `domain/port/in/` → interfaz
2. **Output Port** → `domain/port/out/` → interfaces (DB)
3. **UseCase** → `application/service/` → `@Service`, lógica negocio
4. **Persistence Adapter** → `infrastructure/adapter/out/persistence/`
5. **Web Adapter** → DTOs, Mapper, Controller

---

# Exceptions — Regla Obligatoria

Sistema centralizado de errores.

Archivo fuente de códigos:

```
domain/exception/ErrorCodes.java
```

Ahí se definen TODOS los errores del sistema.

Para lanzar errores usar siempre:

Ejemplo:

```java
throw new ApplicationException(ErrorCodes.WRONG_CREDENTIALS);
```

Reglas:

- ❌ No crear RuntimeException nuevas
- ❌ No strings hardcodeados
- ✅ Siempre usar ErrorCodes
- ✅ Excepciones desde dominio o aplicación
- ✅ Controllers no manejan lógica de errores

Si falta un error:

1. Agregar en `ErrorCodes`
2. Usarlo con `ApplicationException`

---

# Reglas Globales

✅ Controllers sin lógica
✅ Dominio independiente de frameworks
✅ Usar DTOs, no entidades JPA en responses
✅ Inyección por interfaces
✅ Validaciones en capa correcta
✅ Reutilizar código (DRY)

---

# Prohibido

❌ Saltar capas
❌ Repositorios en controllers
❌ Lógica en infraestructura
❌ Dependencias Spring en dominio
❌ Duplicar lógica existente
❌ Exceptions fuera de ErrorCodes

---

# Objetivo

Sistema desacoplado, consistente y mantenible con Hexagonal + SOLID + manejo de errores centralizado.
