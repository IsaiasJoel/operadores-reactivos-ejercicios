# Ejercicios de operadores reactivos con Project Reactor

Este repositorio contiene una serie de ejercicios para practicar el uso de operadores reactivos utilizando **Project Reactor**. Estos ejercicios están diseñados para ayudar a entender y aplicar conceptos de **programación reactiva** en **Java**, explorando diferentes operadores para el procesamiento de flujos de datos asíncronos y el manejo de eventos.

## Enunciados de los ejercicios

### Ejercicios básicos
1. Crea un flujo (Flux) que emita los números del 1 al 10 y suscribe un Subscriber para imprimir cada número en la consola.
2. Dado un flujo de nombres de personas, usa el operador map para transformar cada nombre en su longitud (número de caracteres) y suscribe un Subscriber para imprimir la longitud de cada nombre.
3. Crea un flujo que emita una lista de números del 1 al 20. Usa el operador filter para emitir solo los números pares y suscribe un Subscriber para imprimir esos números.
4. Dado un flujo de cadenas que representan números, usa filter para eliminar las cadenas que no pueden convertirse en números enteros y luego usa map para convertir las cadenas válidas en números enteros. Imprime el resultado.
5. Crea un Flux a partir de una lista de objetos Producto, donde cada producto tiene un nombre y un precio. Usa filter para emitir solo los productos cuyo precio sea mayor a 100 y luego usa map para extraer solo el nombre del producto.
6. Crea dos flujos (Flux) que emitan secuencias de números. Usa el operador merge para combinar ambos flujos en uno solo y suscribe un Subscriber para imprimir todos los números emitidos.
7. Crea un flujo que emita un número cada segundo. Usa el operador take para limitar la emisión a los primeros 5 números. Imprime cada número en la consola.
8. Crea un flujo que emita una serie de números, pero lanza una excepción cuando se emite el número 5. Usa el operador onErrorReturn para manejar la excepción y emitir el número 0 en caso de error.
9. Crea un flujo de palabras (cadenas de texto) y usa flatMap para emitir cada letra de cada palabra como un flujo separado. Imprime cada letra en la consola.
10. Crea un flujo que emita una lista de números. Usa reduce para calcular la suma de todos los números emitidos y suscribe un Subscriber para imprimir la suma total.

### Ejercicios avanzados
1. Crea dos flujos que emitan una secuencia de números y letras. Usa el operador zip() para combinarlos en un flujo de pares (número, letra) y suscribe un Subscriber para imprimir cada par.
2. Crea dos flujos que emitan números en intervalos de tiempo diferentes. Usa merge() para combinar ambos flujos en uno solo y suscribe un Subscriber para imprimir todos los números emitidos.
3. Crea dos flujos que emitan listas de nombres. Usa concat() para combinarlos en un solo flujo y suscribe un Subscriber para imprimir todos los nombres en el orden en que fueron emitidos.
4. Crea dos flujos: uno que emita precios de productos y otro que emita cantidades. Usa combineLatest() para emitir pares (precio, cantidad) cada vez que uno de los flujos emita un nuevo valor. Imprime los pares en la consola.
5. Crea un flujo que emita una serie de números y lanza una excepción cuando se emite el número 3. Usa onErrorReturn() para manejar el error y emitir un valor por defecto, como 0, cuando ocurre la excepción. Imprime todos los números emitidos.
6. Crea un flujo que emita valores, pero lanza una excepción al emitir el número 5. Usa onErrorResume() para cambiar a otro flujo que emita números del 6 al 10 cuando ocurre la excepción. Imprime todos los números en la consola.
7. Crea un flujo que emita números, pero lanza una excepción al emitir el número 4. Usa retry(2) para intentar de nuevo hasta dos veces cuando ocurra un error. Imprime todos los números emitidos.
8. Crea un flujo que emita números, pero asegúrate de que no emita ningún valor. Usa defaultIfEmpty() para emitir un valor por defecto (por ejemplo, -1) si el flujo está vacío. Imprime el resultado.
9. Crea un flujo que emita números, pero asegúrate de que no emita ningún valor. Usa switchIfEmpty() para cambiar a otro flujo que emita una serie de números (por ejemplo, 1, 2, 3) si el flujo original está vacío. Imprime todos los números emitidos.
10. Crea un flujo que emita una serie de números. Usa all() para verificar si todos los números son mayores que 0 e imprime el resultado. Luego, usa any() para verificar si algún número es par y también imprime el resultado.