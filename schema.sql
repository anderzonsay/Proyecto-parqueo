CREATE TABLE sqlite_sequence(name,seq)

CREATE TABLE Area (
    Id TEXT PRIMARY KEY,
    Rol TEXT NOT NULL CHECK(Rol IN ('Estudiante', 'Catedratico')),
    Capacidad INTEGER NOT NULL,
    Tipovehiculo TEXT NOT NULL CHECK(Tipovehiculo IN ('carro', 'moto'))
)
CREATE TABLE Usuario (
    IDusuario INTEGER PRIMARY KEY AUTOINCREMENT,
    Nombres TEXT NOT NULL,
    Carnet INTEGER NOT NULL UNIQUE
)
CREATE TABLE vehiculo (
    Placa TEXT PRIMARY KEY,
    Tipovehiculo TEXT NOT NULL CHECK(Tipovehiculo IN ('carro', 'moto')),
    Area TEXT NOT NULL CHECK(Area IN ('Catedratico', 'Estudiante')),
    IdUsuario INTEGER NOT NULL,
    FOREIGN KEY (IdUsuario) REFERENCES Usuario(IDusuario),
    FOREIGN KEY (Area) REFERENCES Area(Id)
)
CREATE TABLE spot (
    Id INTEGER PRIMARY KEY AUTOINCREMENT,
    Area TEXT NOT NULL,
    Tipodevehiculo TEXT NOT NULL CHECK(Tipodevehiculo IN ('carro', 'moto')),
    Estado TEXT NOT NULL CHECK(Estado IN ('libre','ocupado','reservado')),
    FOREIGN KEY (Area) REFERENCES Area(Id)
)
CREATE TABLE "tickets" (
	"ticket_id"	INTEGER,
	"placa"	TEXT NOT NULL,
	"area_id"	TEXT NOT NULL,
	"spot_id"	INTEGER NOT NULL,
	"fecha_ingreso"	DATETIME NOT NULL,
	"fecha_salida"	DATETIME,
	"modo"	TEXT NOT NULL CHECK("modo" IN ('plano', 'variable')),
	"monto"	REAL DEFAULT 0, metodo_pago TEXT CHECK(metodo_pago IN ('efectivo', 'tarjeta')) DEFAULT 'efectivo',
	PRIMARY KEY("ticket_id" AUTOINCREMENT),
	FOREIGN KEY("area_id") REFERENCES "Area"("Id"),
	FOREIGN KEY("placa") REFERENCES "vehiculo"("Placa"),
	FOREIGN KEY("spot_id") REFERENCES "spot"("Id")
)
