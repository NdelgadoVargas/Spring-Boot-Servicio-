
Prueba tecnica avla-chile.

Requisitos previos

- Visual studio code
- base de dato : Mysql (Preferencia "Mysql Workbench")
- java version: 17


Instalación

Visual studio code install : 
- Extension pack for java 
- JDK 17
- Spring initializr Java Support

Video tutorial install (0:00 - 3:10 m) :
[Ver video](https://www.youtube.com/watch?v=FUgk0gHRFqI&t=206s&ab_channel=divcode)

IMPORTANTE: 
- Es necesario tener la base de datos creada en Mysql con el nombre "avla_chile" para poder generar las tablas de forma automatica.
- En application.properties se encuentra la conexion a la base de dato video configuracion: 
[Ver video](https://www.youtube.com/watch?v=Ewbf0GQ_fNI&ab_channel=ElivarLargo)



CLONAR REPOSITORIO:

- git clone https://github.com/NdelgadoVargas/Spring-Boot-Servicio-.git
- git pull origin main 

POSIBLE ERROR AL CLONAR REPOSITORIO: 
- CARPETA token: Se cambia a mayuscula su inicial y su archivo .java se cambia a minuscula su incial correccion:

    - token
        -GenerateTokenJwt.java