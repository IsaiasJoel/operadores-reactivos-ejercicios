package com.idsoftware.operadores_reactivos_basicos;

import com.idsoftware.operadores_reactivos_basicos.modelos.Producto;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

class EjerciciosOperadoresBasicosTest {

    /**
     * Crea un flujo (Flux) que emita los números del 1 al 10 y suscribe un Subscriber para imprimir cada número en la consola.
     */
    @Test
    void ejercicio1() {
        Flux.range(1, 10).subscribe(System.out::println);
    }

    /**
     * Dado un flujo de nombres de personas, usa el operador map para transformar cada nombre en su longitud (número de caracteres) y suscribe un Subscriber para imprimir la longitud de cada nombre.
     */
    @Test
    void ejercicio2() {
        List<String> nombres = List.of("Isaías", "Pepe", "Rolando", "Isoé", "Hermina", "Porfiria", "Adelina", "Angélica", "Luzvinda");
        Flux.fromIterable(nombres).map(String::length).subscribe(System.out::println);
    }

    /**
     * Crea un flujo que emita una lista de números del 1 al 20. Usa el operador filter para emitir solo los números pares y suscribe un Subscriber para imprimir esos números.
     */
    @Test
    void ejercicio3() {
        Flux.range(1, 20).filter(numero -> numero % 2 == 0).subscribe(System.out::println);
    }

    /**
     * Dado un flujo de cadenas que representan números, usa filter para eliminar las cadenas que no pueden convertirse en números enteros y luego usa map para convertir las cadenas válidas en números enteros. Imprime el resultado.
     */
    @Test
    void ejercicio4() {
        List<String> cadenasComoNumeros = List.of("10", "veinte", "4", "9", "treinta y seis");
        Flux.fromIterable(cadenasComoNumeros)
                .filter(cadena -> {
                    try {
                        Integer.parseInt(cadena);
                        return true;
                    } catch (Exception ex) {
                        return false;
                    }
                })
                .map(Integer::parseInt)
                .subscribe(System.out::println);
    }

    /**
     * Crea un Flux a partir de una lista de objetos Producto, donde cada producto tiene un nombre y un precio.
     * Usa filter para emitir solo los productos cuyo precio sea mayor a 100 y luego usa map para extraer solo el nombre del producto.
     */
    @Test
    void ejercicio5() {
        List<Producto> productos = List.of(
                new Producto("Televisor", 100.54),
                new Producto("Horno Microondas", 80.0),
                new Producto("Play Station 5", 500.0),
                new Producto("Cocina", 190.0),
                new Producto("Juguete a control remoto", 19.90)
        );

        Flux.fromIterable(productos)
                .filter(producto -> producto.getPrecio() >= 100)
                .map(Producto::getNombre)
                .subscribe(System.out::println);
    }

    /**
     * Crea dos flujos (Flux) que emitan secuencias de números. Usa el operador merge para combinar ambos flujos en uno solo
     * y suscribe un Subscriber para imprimir todos los números emitidos.
     */
    @Test
    void ejercicio6() {
        Flux<Integer> numerosPares$ = Flux.just(2, 4, 6, 8, 10);
        Flux<Integer> numerosImpares$ = Flux.just(1, 3, 5, 7, 9);

        Flux.merge(numerosPares$, numerosImpares$).subscribe(System.out::println);
    }

    /**
     * Crea un flujo que emita un número cada segundo. Usa el operador take para limitar la emisión a los primeros 5 números.
     * Imprime cada número en la consola.
     */
    @Test
    void ejercicio7() throws InterruptedException {
        //Flux.interval(Duration) genera un flujo que emite valores
        // comenzando desde 0 y aumenta en 1 cada intervalo de tiempo especificado.

        Flux.interval(Duration.ofSeconds(1)).take(5).subscribe(System.out::println);
//        Otra forma
        //        Flux.range(1,10).delayElements(Duration.ofSeconds(1)).take(5).subscribe(System.out::println);

        Thread.sleep(10000);  // Mantener activo durante 10 segundos. Es importante porque de lo contrario no se apreciaría
    }

    /**
     * Crea un flujo que emita una serie de números, pero lanza una excepción cuando se emite el número 5.
     * Usa el operador onErrorReturn para manejar la excepción y emitir el número 0 en caso de error.
     */
    @Test
    void ejercicio8() {
        Flux.just(10, 20, 4, 8, 5, 9, 18, 2)

                //la excepción ocurre en el suscribe, cuando el flujo ya ha sido emitido

//                .subscribe(
//                        numero -> {
//                            System.out.println("Número emitido: " + numero);
//                            if (numero == 5) {
//                                throw new IllegalArgumentException("Oops! Se ha emitido un error");
//                            }
//                        },
//                        error -> System.err.println("Error: " + error),
//                        () -> System.out.println("Flujo completado")
//                );

                //Si se desea controlar el error, antes que el flujo se emita, debemos usar operadores
                .map(numero -> {
                    if (numero == 5) {
                        throw new IllegalArgumentException("Oops! Se ha emitido un error");
                    }
                    return numero;
                })
                .onErrorReturn(0)
                .subscribe(
                        numero -> System.out.println("Número emitido: " + numero),
                        error -> System.err.println("Error: " + error),
                        () -> System.out.println("Flujo completado")
                );
    }

    /**
     * Crea un flujo de palabras (cadenas de texto) y usa flatMap para emitir cada letra de cada palabra como un flujo separado. Imprime cada letra en la consola.
     */
    @Test
    void ejercicio9() {
        Flux.just("Hola", "este", "es", "un", "ejercicio")
                .flatMap(palabra -> Flux.fromArray(palabra.split("")))
                .subscribe(System.out::println);
    }

    /**
     * Crea un flujo que emita una lista de números. Usa reduce para calcular la suma de todos los números emitidos y suscribe un Subscriber para imprimir la suma total.
     */
    @Test
    void ejercicio10() {
        Flux.range(1, 10).reduce(0, Integer::sum).subscribe(System.out::println);
    }
}
