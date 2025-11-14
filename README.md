# 🚗 Proyecto Final – Parqueo
Sistema de control de parqueo desarrollado en **Java** utilizando **Swing** y **SQLite**.  
Permite gestionar usuarios, vehículos, entradas, salidas, cobros y disponibilidad de spots dentro del estacionamiento.

---

## 📌 Características principales

- Registro de **usuarios** y **vehículos**.
- Asignación de **áreas** según:
  - Rol (Estudiante / Catedrático)
  - Tipo de vehículo (Carro / Moto)
- Control de **entradas** con cobro:
  - **Plano** (Q15)
  - **Variable** (Q10 por hora redondeada).
- Control de **salidas** con cálculo automático del cobro.
- Actualización automática del estado del spot:
  - `libre`
  - `ocupado`
- Registro completo de cada ticket con método de pago.
- Visualización del **mapa del parqueo** con imágenes.
- Persistencia en base de datos **SQLite**.

---

## 🛠️ Tecnologías utilizadas

| Tecnología | Uso |
|-----------|-----|
| **Java SE 8+** | Lógica del sistema |
| **Swing** | Interfaz gráfica (JFrames, JPanel, JTabbedPane, etc.) |
| **SQLite** | Base de datos embebida |
| **JDBC** | Conexión a SQLite |
| **NetBeans** | Entorno de desarrollo recomendado |

---

## 📁 Estructura del proyecto
src/
└── com.mycompany.proyectofinal/
├── PROYECTOFINAL.java # Clase principal
├── mostrardatos.java # Ventana principal (UI)
├── Spot.java # Entradas y salidas + lógica principal
├── Usuariomolde.java # Modelo de usuario
├── usuariodatos.java # Inserción de usuario y vehículo
├── vehiculo.java # Molde del vehículo
├── cobro.java # Clase auxiliar de cobros
├── conexiondatos.java # Conexión a SQLite
├── mapa.java # Panel que carga imágenes
├── vista.java # Ventana del mapa


---

## 🗄️ Base de datos

El proyecto usa un archivo:



Basededatos/basededatos.db


### Tablas principales:

- **Usuario** → Datos personales.
- **Vehiculo** → Placa, modelo, rol, relación con usuario.
- **Area** → Define zonas según rol y tipo.
- **Spot** → Lugares del parqueo.
- **Tickets** → Registro de entrada, salida, cobros y método de pago.

---

## ▶️ Instalación y ejecución

### 1. Requisitos previos
- Tener instalado **Java JDK 8+**
- Tener **NetBeans** (recomendado)
- Verificar que el archivo:


Basededatos/basededatos.db

exista en la ruta exacta del proyecto.

---

### 2. Abrir el proyecto
1. En NetBeans: **File → Open Project**
2. Seleccionar la carpeta del proyecto.
3. Esperar a que carguen las dependencias.

---

### 3. Ejecutar
Ejecutar la clase:



PROYECTOFINAL.java


Esto abrirá la ventana principal del sistema.

---

## 🧑‍💻 Uso del sistema

### 🟦 1. Registrar usuario y vehículo
- Ir a pestaña **Registro**.
- Ingresar nombre, carnet, placa y modelo.
- Seleccionar rol.
- Presionar **Ingresar**.
- Se guardará en la base de datos.

---

### 🟩 2. Registrar entrada
- En la pestaña **Entradas / Salidas**:
  - Escribir placa.
  - Elegir modalidad (Variable o Flat).
  - Ingresar monto recibido.
  - Elegir método de pago.
- Presionar **Ingresar2**.
- El sistema asigna un spot libre y registra ticket.

---

### 🟥 3. Registrar salida
- Ingresar placa.
- Ingresar monto recibido.
- Seleccionar método de pago.
- Presionar **Ingresar**.
- El sistema:
  - Calcula cobro,
  - Genera cambio,
  - Actualiza ticket,
  - Libera spot.

---

### 🗺️ 4. Ver mapa del parqueo
- En la pestaña **Registro**, presionar **Mostrar mapa**.
- Se abrirá una ventana con las imágenes del parqueo.

---

## 🧩 Posibles errores comunes

| Mensaje | Causa | Solución |
|--------|--------|----------|
| Vehículo no encontrado | No está registrado | Registrar antes en pestaña Registro |
| No hay spots libres | Área llena | Esperar disponibilidad |
| Billete insuficiente | El monto no cubre el cobro | Ingresar monto suficiente |
| Ticket no encontrado | No hay entrada activa | Verificar placa |

---

## 👨‍🏫 Autor
Proyecto desarrollado como trabajo académico.  
**Anderzon Say**

---

## 📜 Licencia
Este proyecto es de uso académico y puede modificarse para fines educativos.

