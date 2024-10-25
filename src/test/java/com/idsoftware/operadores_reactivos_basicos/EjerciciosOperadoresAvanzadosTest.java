package com.idsoftware.operadores_reactivos_basicos;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

class EjerciciosOperadoresAvanzadosTest {

    // Crea dos flujos que emitan una secuencia de números y letras.
    // Usa el operador zip() para combinarlos en un flujo de pares (número, letra)
    // y suscribe un Subscriber para imprimir cada par.
    @Test
    void ejercicio1() {
        /*
         * zip: operador de combinación utilizado en programación reactiva
         * para combinar múltiples flujos en un solo flujo

         * Si los flujos tienen diferentes longitudes,
         * zip emitirá elementos hasta que el flujo más corto se complete

         * Contexto de uso:
         * - Sincronización de Flujos de Datos
         * - Enriquecimiento de Datos
         *
         */
        Flux<String> fluxLetras = Flux.just("a", "b", "c", "d", "e", "f", "g", "h", "i");
        Flux<Integer> fluxNumeros = Flux.range(1, 7);

        Flux.zip(fluxLetras, fluxNumeros, (letra, numero) -> letra + numero).subscribe(System.out::println);
    }

    // Crea dos flujos que emitan números en intervalos de tiempo diferentes.
    // Usa merge() para combinar ambos flujos en uno solo
    // y suscribe un Subscriber para imprimir todos los números emitidos.
    @Test
    void ejercicio2() throws InterruptedException {
        Flux<Integer> numerosUno = Flux.just(2, 3, 6, 1, 0, 9).delayElements(Duration.ofSeconds(1));
        Flux<Integer> numerosDos = Flux.just(10, 20, 30, 40, 50, 60).delayElements(Duration.ofSeconds(5));

        Flux.merge(numerosUno, numerosDos).subscribe(
                (data) -> System.out.println(data),
                (error) -> System.out.println(error),
                () -> System.out.println("Se completó la emisión")
        );
        Thread.sleep(50000);
    }

    // Crea dos flujos que emitan listas de nombres.
    // Usa concat() para combinarlos en un solo flujo y suscribe
    // un Subscriber para imprimir todos los nombres en el orden en que fueron emitidos.
    @Test
    void ejercicio3() {
        Flux<String> nombresMasculinos = Flux.just("Isaías", "Juan", "Daniel", "Darwin");
        Flux<String> nombresFemeninos = Flux.just("Mayra", "Sandra", "Rebeca", "Camila");

        Flux.concat(nombresFemeninos, nombresMasculinos).subscribe(
                (nombre) -> System.out.println("Nombre: " + nombre),
                (error) -> System.out.println("Ocurrión un error durante la emisión"),
                () -> System.out.println("Terminó la emisión")
        );
    }

    // Crea dos flujos: uno que emita precios de productos y otro que emita cantidades.
    // Usa combineLatest() para emitir pares (precio, cantidad) cada vez que uno de los flujos emita un nuevo valor.
    // Imprime los pares en la consola.
    @Test
    void ejercicio4() throws InterruptedException {
        Flux<Double> precios = Flux.just(10.3, 9.99, 5.0, 2.3).delayElements(Duration.ofSeconds(2));
        Flux<Integer> cantidades = Flux.just(1, 9, 2, 8, 3).delayElements(Duration.ofSeconds(1));

        Flux.combineLatest(precios, cantidades, (precio, cantidad) -> precio + "-" + cantidad)
                .subscribe(System.out::println);

        Thread.sleep(10000);
    }

    //Crea un flujo que emita una serie de números y lanza una excepción cuando se emite el número 3.
    // Usa onErrorReturn() para manejar el error y emitir un valor por defecto, como 0,
    // cuando ocurre la excepción. Imprime todos los números emitidos.
    @Test
    void ejercicio5() {
        Flux.just(9, 8, 7, 2, 3, 5, 6)
                .onErrorReturn(0)
                .map(numero -> {
                    if (numero == 3) {
                        throw new IllegalArgumentException("Error durante la emisión");
                    }
                    return numero;
                })
                .subscribe(
                        System.out::println,
                        System.out::println,
                        () -> System.out.println("Se completó la emisión")
                );
    }

    //Crea un flujo que emita valores, pero lanza una excepción al emitir el número 5.
    // Usa onErrorResume() para cambiar a otro flujo que emita números del 6 al 10 cuando ocurre la excepción.
    // Imprime todos los números en la consola.
    @Test
    void ejercicio6() {
        Flux.just(9, 8, 7, 5, 2, 3, 6)
                .map(numero -> {
                    if (numero == 5) {
                        throw new IllegalArgumentException("Error durante la emisión");
                    }
                    return numero;
                })
                .onErrorResume((e) -> {
                    System.out.println("Se detectó un error: " + e.getMessage());
                    System.out.println("Obteniendo flujo alternativo");

                    return Flux.just(6, 7, 8, 9, 10);
                })
                .subscribe(System.out::println);
    }

    // Crea un flujo que emita números, pero lanza una excepción al emitir el número 4.
    // Usa retry(2) para intentar de nuevo hasta dos veces cuando ocurra un error.
    // Imprime todos los números emitidos.
    @Test
    void ejercicio7() {
        // Cuando se produce un error en el flujo, en lugar de finalizar, el operador retry vuelve a iniciar el flujo desde el principio
        Flux<Integer> flujo = Flux.<Integer>create(sink -> {
                    System.out.println("Intentando operación...");
                    sink.next(1);
                    sink.next(2);
                    sink.error(new RuntimeException("Error simulado"));
                })
                .retry(3)  // Reintentar 3 veces en caso de error
                .doOnError(e -> System.out.println("Error después de reintentar: " + e.getMessage()));

        flujo.subscribe(
                data -> System.out.println("Recibido: " + data),
                error -> System.out.println("Error final: " + error.getMessage())
        );
    }

    //Crea un flujo que emita números, pero asegúrate de que no emita ningún valor.
    // Usa defaultIfEmpty() para emitir un valor por defecto (por ejemplo, -1)
    // si el flujo está vacío. Imprime el resultado.
    @Test
    void ejercicio8() {
        // Crea un flujo vacío
        Flux<Integer> flujoVacio = Flux.<Integer>empty()
                .defaultIfEmpty(-1);  // Emite -1 si el flujo está vacío

        // Suscribimos al flujo e imprimimos el resultado
        flujoVacio.subscribe(
                valor -> System.out.println("Valor emitido: " + valor),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("Flujo completado")
        );
    }

    // Crea un flujo que emita números, pero asegúrate de que no emita ningún valor.
    // Usa switchIfEmpty() para cambiar a otro flujo que emita una serie de números (por ejemplo, 1, 2, 3)
    // si el flujo original está vacío. Imprime todos los números emitidos.
    @Test
    void ejercicio9() {
        // Crea un flujo vacío
        Flux<Integer> flujoVacio = Flux.empty();

        // Usa switchIfEmpty para cambiar a un nuevo flujo si el original está vacío
        Flux<Integer> flujoConValores = flujoVacio
                .switchIfEmpty(Flux.just(1, 2, 3));

        // Suscribimos al flujo e imprimimos todos los valores emitidos
        flujoConValores.subscribe(
                valor -> System.out.println("Valor emitido: " + valor),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("Flujo completado")
        );
    }

    // Crea un flujo que emita una serie de números. Usa all() para verificar si todos los números son mayores que 0
    // e imprime el resultado. Luego, usa any() para verificar si algún número es par y también imprime el resultado.
    @Test
    void ejercicio10() {
        // Crea un flujo que emite una serie de números
        Flux<Integer> flujoNumeros = Flux.just(1, 2, -1, 4, 5);

        // Usa all() para verificar si todos los números son mayores que 0
        Mono<Boolean> todosMayoresQueCero = flujoNumeros.all(numero -> numero > 0);

        // Suscríbete y verifica el resultado de all()
        todosMayoresQueCero.subscribe(
                resultado -> System.out.println("¿Todos los números son mayores que 0? " + resultado)
        );

        // Reinicializa el flujo porque all() consume el flujo original
        flujoNumeros = Flux.just(1, 2, 3, 4, 5);

        // Usa any() para verificar si algún número es par
        Mono<Boolean> algunPar = flujoNumeros.any(numero -> numero % 2 == 0);

        // Suscríbete y verifica el resultado de any()
        algunPar.subscribe(
                resultado -> System.out.println("¿Algún número es par? " + resultado)
        );
    }
}