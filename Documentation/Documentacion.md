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

*Descripción de la arquitectura utilizada (Capas) (Describir responsabilidad de las capas)*

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

**Diagramas de secuencia:**

*Create*

*Read*

*Update*

*Delete*

**Diagrama de la Base de Datos**

**Entidades**

Descripcion de las entidades del sistema

**Diagrama entidad relación**

## Documentación de la API ##

En esta sección se cita la documentación de la API

## Documentación Individual de cada Endpoint por cada entidad ##

*URL (URI)*

*Descripción*

*Campos requeridos*

*Validaciones*

*Tipo de dato de cada campo*

*Respuesta (Response)*

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

