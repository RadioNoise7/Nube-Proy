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

## Documentos de referencia ##

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


*Diagrama de arquitectura con descripción (Arquitectura del proyecto completo)*

*Diagrama de secuencia para los procesos más imporantes de la App (CRUD)*

*Diagrama de la base de datos*

*Descripción de las entidades*

*Diagrama entidad relación*

## Documentación de la API ##

## Documentación Individual de cada Endpoint por cada entidad ##

*URL (URI)*

*Descripción*

*Campos requeridos*

*Validaciones*

*Tipo de dato de cada campo*

*Respuesta (Response)*

## Ejemplos del Request ##

## Criterios de calidad ##
