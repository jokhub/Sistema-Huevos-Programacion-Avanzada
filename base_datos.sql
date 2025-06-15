CREATE DATABASE eggceptional;

\c eggceptional;

CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre_usuario VARCHAR(50) UNIQUE NOT NULL,
    contrasena VARCHAR(50) NOT NULL,
    rol VARCHAR(20) NOT NULL CHECK (rol IN ('gerente', 'administrador'))
);

CREATE TABLE clientes (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL,
    telefono VARCHAR(8),
    nit VARCHAR(15),
    estado VARCHAR(50)
);

CREATE TABLE venta (
    id SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    id_cliente INTEGER NOT NULL,
    fecha DATE,
    total FLOAT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios (id),
    FOREIGN KEY (id_cliente) REFERENCES clientes (id)
);

CREATE TABLE reporte (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(20),
    fecha_gen DATE,
    descripcion VARCHAR(1000),
    id_usuario INTEGER NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios (id)
);

CREATE TABLE producto (
    id_producto SERIAL PRIMARY KEY,
    nombre VARCHAR(100),
    tipo VARCHAR(30),
    precio_unitario FLOAT,
    precio_mayor FLOAT
);

CREATE TABLE detalle_venta (
    id_detalle SERIAL PRIMARY KEY,
    id_venta INTEGER NOT NULL,
    id_producto INTEGER NOT NULL,
    cantidad FLOAT,
    precio FLOAT,
    FOREIGN KEY (id_venta) REFERENCES venta (id),
    FOREIGN KEY (id_producto) REFERENCES producto (id_producto)
);

CREATE TABLE inventario (
    id SERIAL PRIMARY KEY,
    id_producto INTEGER NOT NULL,
    cantidad FLOAT,
    fecha_actualizacion DATE,
    FOREIGN KEY (id_producto) REFERENCES producto (id_producto)
);

INSERT INTO usuarios (nombre_usuario, contrasena, rol) VALUES
('gerente1', '1234', 'gerente'),
('admin1', 'abcd', 'administrador');
