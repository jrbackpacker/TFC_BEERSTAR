-- Tabla usuario
CREATE TABLE usuario (
    id_usuario SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    rol VARCHAR(50),
    direccion TEXT
);

-- Tabla carrito
CREATE TABLE carrito (
    id_carrito SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TYPE estado_pedido AS ENUM ('pendiente', 'procesando', 'completado', 'cancelado');

-- Tabla pedido
CREATE TABLE pedido (
    id_pedido SERIAL PRIMARY KEY,
    fecha_pedido TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2),
    estado estado_pedido NOT NULL DEFAULT 'pendiente', 
    id_carrito INT NOT NULL,
    FOREIGN KEY (id_carrito) REFERENCES carrito(id_carrito)
);

-- Tabla proveedor
CREATE TABLE proveedor (
    id_proveedor SERIAL PRIMARY KEY,
    nombre_empresa VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100)
);

-- Tabla articulo
CREATE TABLE articulo (
    id_articulo SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    capacidad INT,
    stock INT,
    id_proveedor INT NOT NULL,
    FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor)
);

-- Tabla detalle_pedido
CREATE TABLE detalle_pedido (
    id_detalle_pedido SERIAL PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_articulo INT NOT NULL,
    cantidad INT NOT NULL,
    subtotal DECIMAL(10,2),
    FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido),
    FOREIGN KEY (id_articulo) REFERENCES articulo(id_articulo)
);

-- Tabla pago
CREATE TABLE pago (
    id_pago SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL,
    iva DECIMAL(4,2),
    total DECIMAL(10,2),
    metodo_pago VARCHAR(20),
    estado_pago VARCHAR(20),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- Tabla lote_articulo
CREATE TABLE lote_articulo (
    id_lote_articulo SERIAL PRIMARY KEY,
    id_articulo INT NOT NULL,
    valor DECIMAL(10,2),
    FOREIGN KEY (id_articulo) REFERENCES articulo(id_articulo)
);

-- Tabla promocion
CREATE TABLE promocion (
    id_promocion SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    id_articulo INT NOT NULL,
    FOREIGN KEY (id_articulo) REFERENCES articulo(id_articulo)
);
