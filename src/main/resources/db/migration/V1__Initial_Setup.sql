-- 1. Tablas base
CREATE TABLE role (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE permission (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE user (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role_id INTEGER NOT NULL,
    status INTEGER DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    token TEXT,
    FOREIGN KEY (role_id) REFERENCES role(id)
);

CREATE TABLE role_permission (
    role_id INTEGER NOT NULL,
    permission_id INTEGER NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES role(id),
    FOREIGN KEY (permission_id) REFERENCES permission(id)
);

CREATE TABLE parking (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    capacity INTEGER NOT NULL,
    cost_per_hour REAL NOT NULL,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE vehicle_record (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    plate TEXT NOT NULL CHECK (length(plate) = 6),
    entry_date DATETIME NOT NULL,
    parking_id INTEGER NOT NULL,
    FOREIGN KEY (parking_id) REFERENCES parking(id)
);

CREATE TABLE parking_history (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    plate TEXT NOT NULL,
    entry_date DATETIME NOT NULL,
    exit_date DATETIME NOT NULL,
    total_cost REAL NOT NULL,
    parking_id INTEGER NOT NULL,
    FOREIGN KEY (parking_id) REFERENCES parking(id)
);

-- 2. Inserción de Datos Iniciales (Roles y Permisos)
INSERT INTO role (id, name, description) VALUES
(1, 'ADMIN', 'Administrador del sistema'),
(2, 'SOCIO', 'Usuario socio');

INSERT INTO permission (id, name, description) VALUES
(1, 'CREATE_SOCIO', 'Crear nuevos usuarios exclusivamente con el rol de Socio'),
(2, 'MANAGE_PARKING', 'Realizar el CRUD completo de parqueaderos y asignarles socios'),
(3, 'VIEW_VEHICLES', 'Ver el listado y detalle de vehículos en un parqueadero'),
(4, 'SEND_EMAILS', 'Ejecutar la simulación de envío de correos a los socios'),
(5, 'VIEW_INDICATORS', 'Consultar tableros, gráficas y KPIs de rendimiento'),
(6, 'REGISTER_ENTRY', 'Registrar la entrada de un vehículo a un parqueadero'),
(7, 'REGISTER_EXIT', 'Registrar la salida de un vehículo y efectuar el cobro'),
(8, 'VIEW_MY_PARKING', 'Permite a un socio ver los parqueaderos que tiene a su cargo');

-- 3. Asociación de Permisos a Roles
-- ADMIN tiene todos los permisos básicos excepto registrar entrada/salida (aunque podrías dárselos)
INSERT INTO role_permission (role_id, permission_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 8);

-- SOCIO tiene permisos de operación
INSERT INTO role_permission (role_id, permission_id) VALUES
(2, 3), (2, 5), (2, 6), (2, 7), (2, 8);
