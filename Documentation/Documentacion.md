# Documento de Arquitectura de Software #

## Nombre del Software ##

### Autores: ###
*   Samantha Caamal
*   Elena Castañeda
*   Kirbey García
*   Esthefany Mezquita

## Introducción ##

## Propósito ##

Este documento tiene como objetivo proporcionar una descripción general del sistema, así como mantener organizada la arquitectura técnica,   
esto a través de una serie de vistas arquitectónicas que representan los distintos aspectos del sistema. Se pretende capturar y   
transmitir las decisiones arquitectónicas del sistema para proveer una fuente de referencia a los analistas y a los diseñadores de la aplicación.

## Alcance ##

Este documento tiene como fin, describir el proyecto **Nombre del Proy** y detallar los principales diagramas del mismo, describen las caraterísticas más importantes para la realización del proyecto y está dirigido a los analistas y desarrolladores del proyecto para propósitos informativos y de entendimiento de la arquitectura y componentes del sistema **Nombre del Proy**. Las características mencionadas a continuación pueden ser modificadas, y se pueden agregar más características en el dado caso que sea aprobada una solicitud de cambio.

## Documentos de referencia ##

A continuación se listan los documentos de referencias utilizados para el proyecto:

*       Dailymotion API Documentation
                *https://developer.dailymotion.com/player/
*       Daylimotion Player API Documentation
                *https://developer.dailymotion.com/player/#player-customisation
*       Tres

## Arquitectura ##

**Descripción de la arquitectura utilizada (Capas) (Describir responsabilidad de las capas)**

![image](https://github.com/RadioNoise7/Nube-Proy/blob/main/Documentation/img/capas_arquitectura.png)

**Capa de Presentación**    
 Aquí agrupamos las clases y métodos que conforman el API del servicio web, tales como GET, POST, PUT, DELETE, etc.

*     Controller. Controladores que reciben y procesan las solicitudes del usuario.


**Lógica de Negocio**    
Se encarga de ejecutar procesamientos y aplicar reglas de negocio. Aquí se contienen las clases que tienen la «sabiduría» o conocimiento   
sobre la lógica de lo que hace el servicio web.  

*     Capa Service. Componentes encargados de ejecutar procesos complejos de la lógica de negocio.

**Datos**    

*     Capa de implementación de acceso a datos: ORM Hibernate.

Adicionalmente tenemos un tipo de objeto que transporta la información entre estas capas de nuestra aplicación, para reducir el acoplamiento. Los Data Transport Object (DTO) o Value Object (VO) que se implementan como objetos planos de java (Plain Old Java Object o POJO), es decir, simples contenedores de datos, sin métodos o comportamiento.

**Diagrama de Arquitectura**

A continuación se presenta el diagrama de arquitectura con descripción (Arquitectura del proyecto completo)

![image](https://github.com/RadioNoise7/Nube-Proy/blob/main/Documentation/img/diagrama_arquitectura.jpg)

**Diagramas de secuencia:**

*Create*

*Read*

*Update*

*Delete*

**Diagrama de la Base de Datos**

**Entidades*

Descripcion de las entidades del sistema

**Diagrama entidad relación**
A continuación se presenta el diagrama de Entidad-Relación del sistema:

![image](https://github.com/RadioNoise7/Nube-Proy/blob/main/Documentation/img/Diagrama E-R.jpg)

## Documentación de la API ##

En esta sección se cita la documentación de la API

## Documentación Individual de cada Endpoint por cada entidad ##

### *URL (URI)* ###

#### Usuario ####
*   GET/usuarios 
*   GET/usuarios/{idUsuario} 
*   POST/usuarios 
*   PUT/usuarios/{idUsuario} 
*   DELETE/usuarios/{idUsuario}    


#### Proveedores de video ####
*   GET/proveedores
*   GET/proveedores/{idProveedor} 
*   POST/proveedores
*   PUT/proveedores/{idProveedor} 
*   DELETE/proveedores/{idProveedor} 

#### Comentarios ####
*   GET/comentarios
*   GET/comentarios/{idComentario} 
*   POST/comentarios
*   PUT/comentarios/{idComentario} 
*   DELETE/comentarios/{idComentario} 

#### Interacciones ####
*   GET/interacciones
*   GET/interacciones/{idInteraccion} 
*   POST/interacciones
*   PUT/interacciones/{idInteraccion} 
*   DELETE/interacciones/{idInteraccion}

#### Videos ####
*   GET/videos
*   GET/videos/{idVideo} 
*   POST/videos
*   DELETE/videos/{idVideo} 


### *Descripción* ###
#### Usuario ####
Entidad que representa a los usuarios que interactúan con el sistema y hacen uso de este, interacciones tales como: ver videos, dar like o dislike y comentar, o a al administrador del sistema, quien se encarga de administrar videos, usuarios y proveedores, esto dependerá del rol.

#### Proveedores de video ####
Entidad que representa a los proveedores de algunos videos.

#### Comentarios ####
Entidad que representa a los comentarios que los usuarios pueden realizar en los videos.

#### Interacciones ####
Entidad que representa los likes o dislikes que los usuarios pueden dejar en los videos.

#### Videos ####
Entidad que representa los videos que los usuarios pueden ver en el sitio.

### *Campos requeridos* ###
#### Usuario ####
idUsuario    
nombre    
apellido    
correo    
rol    

#### Proveedores de video ####

#### Comentarios ####
idComentario    
idUsuario     
idVideo     
contenido   

#### Interacciones ####
idInteraccion     
idUsuario     
idVideo     
Tipo(like o dislike)

#### Videos ####
idVideo    
video    

### *Validaciones* ###
#### Usuario ####
idUsuario --- Que no exista otro usuario conel mismo idUsuario (POST)    
correo --- Que cumpla con el formato de correo  
#### Comentarios ####
contenido --- Que el campo no este vacio
#### Interacciones ####
idUsuario | idVideo --- El usuario solo puede reaccionar una vez a un video, por tanto solo puede haber una interacción por cada 
combinación idVideo e idUsuario.   
#### Videos ####
video --- Formato valido


### *Tipo de dato de cada campo* ###

#### Usuario ####
idUsuario---[Integer]     
nombre------[String]    
apellido----[String]      
correo------[String]    
rol---------[String]    

#### Proveedores de video ####

#### Comentarios ####
idComentario---[Integer]    
idUsuario------[Integer]     
idVideo--------[Integer]     
contenido------[String]   

#### Interacciones ####
idInteraccion---[Integer]      
idUsuario-------[Integer]      
idVideo---------[Integer]      
Tipo------------[boolean]

#### Videos ####
idVideo----[Integer]    
video------[File]   


### *Respuesta (Response)* ###



## Ejemplos del Request ##

## Criterios de calidad ##

La API deberá cumplir con las siguientes características que aseguran la confiabilidad y disponibilidad del mismo.

*Los fallos ocurridos en la API deben ser transparentes para el usuario.
*La API deberá ser tolerante a fallos y ser capaz de recuperarse de los mismos.
*La API deberá estar disponible para el usuario en todo momento.
*La 80% de las transacciones realizadas deberán ser completadas con éxito.
*La información proporcionada al usuario deberá ser íntegra y completa, (es decir la información deberá llegar completa al usuario y sin ninguna modificación).
*La API deberá finalizar toda transacción u operación realizada dentro del aplicativo, es decir, no deberá quedar pausada la funcionalidad de la aplicación si la API llegara a fallar
*Las información sensible del usuario, como datos de inicio de sesión (en caso de requerirse) deberá ser resguardada y tratada con un algoritmo de encriptación

