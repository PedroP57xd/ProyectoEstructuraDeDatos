
# 🚀 Proyecto Final – Simulación de Rutas con Grafos (BFS y DFS)

## 📌 Información General

- **Autor:** Pedro Panjón  
- **Carrera:** Computación  
- **Materia:** Estructura de Datos  
- **Fecha:** 09/02/2026  

---

## 📖 Resumen

Este proyecto consiste en el desarrollo de una aplicación que ayuda a simular el funcionamiento básico de una plataforma de rutas como **Google Maps**, utilizando **grafos** y los algoritmos **BFS** y **DFS**.

El sistema permite crear nodos, conexiones, seleccionar puntos de inicio y fin, y aplicar algoritmos de búsqueda para encontrar las rutas óptimas.

El objetivo principal es reforzar los conocimientos sobre **estructuras de datos no lineales** y **algoritmos de recorrido de grafos** mediante una aplicación visual e interactiva.

---

## 🧠 1. Marco Teórico

### 📍 1.1 Grafos

Un **grafo** es una estructura de datos no lineal formada por:

- 🔹 **Nodos (Vértices):** Representan puntos o ubicaciones.
- 🔹 **Aristas:** Representan conexiones entre nodos.

En este proyecto, los grafos permiten modelar rutas similares a sistemas de navegación.

Tipos de conexiones:

- ➡️ **Unidireccionales**
- ↔️ **Bidireccionales**

---

### 📍 1.2 Algoritmo BFS (Breadth-First Search)

El algoritmo **BFS** realiza una búsqueda por niveles.

**Características:**

- ✔️ Explora primero los nodos más cercanos.
- ✔️ Garantiza encontrar el camino más corto.
- ✔️ Usa una estructura tipo **cola (Queue)**.
- ✔️ Mayor consumo de memoria.

Se utiliza para encontrar rutas mínimas.

---

### 📍 1.3 Algoritmo DFS (Depth-First Search)

El algoritmo **DFS** explora el grafo en profundidad.

**Características:**

- ✔️ Explora caminos largos primero.
- ✔️ No garantiza la ruta más corta.
- ✔️ Usa **pila (Stack)** o recursión.
- ✔️ Menor consumo de memoria.

Se emplea para analizar rutas alternativas.

---

### 📍 1.4 Grafos en Navegación

Los sistemas de mapas digitales utilizan grafos para representar:

- Calles
- Intersecciones
- Rutas

Este proyecto simula ese funcionamiento.

---

## 🛠️ 2. Materiales y Herramientas

### 💻 Software Utilizado

- **Lenguaje:** Java  
- **IDE:** NetBeans  
- **Interfaz:** Java Swing  
- **Sistema Operativo:** Windows  
- **Control de Versiones:** Git / GitHub  
- **Archivos:** TXT  

---

## ⚙️ 3. Proceso de Desarrollo

### 📐 3.1 Diseño del Sistema

El proyecto se desarrolló con arquitectura:

- 🧩 **Modelo:** Nodo, ResultadosBusqueda  
- 🎨 **Vista:** PanelMap, Ventana  
- 🎮 **Controlador:** BFS, DFS, Paths  

Permite organización y mantenimiento.

---

### 🧱 3.2 Construcción del Grafo

El usuario puede:

- ➕ Agregar nodos
- ❌ Eliminar nodos
- 🔗 Unir nodos
- 🎯 Seleccionar inicio y fin
- 🧹 Limpiar grafo

Cada nodo almacena su posición y vecinos.

---

### 🧮 3.3 Implementación de Algoritmos

Se implementaron:

- 🔵 BFS (Anchura)
- 🟢 DFS (Profundidad)

Ambos recorren el grafo y generan rutas.

---

### 🎞️ 3.4 Visualización

Las rutas se muestran mediante:

- 🎨 Colores dinámicos
- ⏱️ Animación
- ⭐ Ruta óptima resaltada

Esto facilita la comprensión.

---

### 💾 3.5 Almacenamiento

Se permite:

- 📂 Guardar grafos
- 📥 Cargar archivos
- ♻️ Reutilizar configuraciones

---

## 📊 4. Resultados

Durante las pruebas se obtuvo:

- ✔️ Correcta creación de grafos
- ✔️ Ejecución funcional de BFS y DFS
- ✔️ Visualización clara
- ✔️ Comparación de tiempos
- ✔️ Identificación de rutas óptimas

---

### 📈 4.1 Comparación BFS vs DFS

| Algoritmo | Tiempo | Longitud | Precisión |
|-----------|--------|----------|-----------|
| BFS       | Medio  | Menor    | Alta      |
| DFS       | Bajo   | Variable | Media     |

BFS mostró mejor desempeño en rutas cortas, mientras que DFS exploró más caminos.

---

## ✅ 5. Conclusiones

- 📌 Los grafos son fundamentales en sistemas de rutas.
- 📌 BFS encuentra caminos mínimos.
- 📌 DFS explora rutas alternativas.
- 📌 El sistema facilita el aprendizaje visual.
- 📌 Se reforzaron conceptos de estructuras no lineales.
- 📌 Se aplicó teoría en un entorno práctico.

El proyecto cumplió con los objetivos propuestos.

---

## 🚀 6. Trabajos Futuros

Mejoras propuestas:

- ➕ Implementar Dijkstra
- ➕ Agregar pesos
- ➕ Mejorar interfaz
- ➕ Usar mapas reales
- ➕ Optimizar rendimiento

---

## 📚 7. Bibliografía

1. Cormen, T. H. *Introduction to Algorithms*. MIT Press, 2009.  
2. Weiss, M. *Data Structures and Algorithm Analysis in Java*. Pearson, 2014.  
3. Oracle. Java Documentation.  
4. GeeksforGeeks. BFS and DFS.  
5. Goodrich & Tamassia. Data Structures.  

---

## 🌐 Repositorio

🔗 **Enlace:** *(Colocar aquí el link de GitHub)*

---

## 🙌 Agradecimientos

Gracias al docente y a la institución por los conocimientos impartidos durante la asignatura.

