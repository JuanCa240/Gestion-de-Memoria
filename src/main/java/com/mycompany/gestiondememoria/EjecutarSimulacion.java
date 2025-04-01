package com.mycompany.gestiondememoria;

import java.util.Scanner;

public class EjecutarSimulacion {
    public Scanner teclado;
    public MemoriaFisica memoriaFisica;
    public BloqueMemoria bloqueMemoria;
    public GestionMemoria gestionMemoria;
    
    public EjecutarSimulacion(){
        teclado = new Scanner(System.in);
        memoriaFisica = new MemoriaFisica(0);
        gestionMemoria = new GestionMemoria(memoriaFisica);
    }
    
    public void ejecutarSimulacion(){
        while(true){
            System.out.println("\n1.) Crear memoria física");
            System.out.println("2.) Dividir memoria en bloques");
            System.out.println("3.) Mostrar estado de la memoria");
            System.out.println("4.) Solicitar memoria para un proceso");
            System.out.println("5.) Salir");
            
            System.out.print("\nIngrese una opción: ");
            String opcion = teclado.nextLine();

            switch(opcion){
                case "1":
                    crearMemoriaFisica();
                    break;
                case "2":
                    definirBloquesMemoria();
                    break;
                case "3":
                    mostrarEstadoMemoria();
                    break;
                case "4":
                    solicitarMemoriaProceso();
                    break;
                case "5":
                    System.out.println("Saliendo del programa...");
                return;
                
                default:
                    System.out.println("Opción no válida, intente de nuevo.");
            }
        }
    }
    

    public void crearMemoriaFisica(){
        System.out.print("Ingrese la capacidad total de la memoria física (KB): ");
        int capacidadTotal = teclado.nextInt();
        teclado.nextLine();
        
        memoriaFisica = new MemoriaFisica(capacidadTotal);
        gestionMemoria = new GestionMemoria(memoriaFisica);
        
        System.out.println("\nMemoria física creada con " + capacidadTotal + " KB.");
    }
     
    public void definirBloquesMemoria() {
        if (memoriaFisica == null) {
            System.err.println("Debe crear la memoria física primero.");
            return;
        }
        
        System.out.print("Ingrese la cantidad de bloques de memoria: ");
        int cantidadBloques = teclado.nextInt();
        int[] tamaniosBloques = new int[cantidadBloques];

        for (int i = 0; i < cantidadBloques; i++) {
            System.out.print("Tamaño del Bloque " + (i + 1) + " (KB): ");
            tamaniosBloques[i] = teclado.nextInt();
        }
         teclado.nextLine();

        gestionMemoria.dividirMemoria(tamaniosBloques);
    }
    
    public void solicitarMemoriaProceso() {
        if (memoriaFisica == null) {
            System.out.println("Debe crear la memoria física primero.");
            return;
        }

        if (memoriaFisica.getBloques().isEmpty()) {
            System.out.println("No hay bloques de memoria disponibles. Divida la memoria en bloques primero.");
            return;
        }

        System.out.print("Ingrese el ID del proceso: ");
        int id = teclado.nextInt();
        System.out.print("Ingrese el tamaño de memoria que necesita el proceso (KB): ");
        int tamanio = teclado.nextInt();
        teclado.nextLine(); 

        Proceso nuevoProceso = new Proceso(id, tamanio);

        System.out.println("Seleccione el algoritmo de asignación:");
        System.out.println("1.) First-Fit");
        System.out.println("2.) Best-Fit");
        System.out.println("3.) Worst-Fit");
        System.out.print("Ingrese una opción: ");
        String opcion = teclado.nextLine();

        boolean asignado = false;

        switch (opcion) {
            case "1":
                asignado = gestionMemoria.asignarMemoriaFirstFit(nuevoProceso);
                break;
            case "2":
                asignado = gestionMemoria.asignarMemoriaBestFit(nuevoProceso);
                break;
            case "3":
                asignado = gestionMemoria.asignarMemoriaWorstFit(nuevoProceso);
                break;
            default:
                System.out.println("Opción no válida, regresando al menú.");
                return;
        }

        if (asignado) {
            System.out.println("Memoria asignada correctamente.");
        } else {
            System.out.println("No se pudo asignar memoria al proceso.");
        }
    }
 
    public void mostrarEstadoMemoria() {
        if (memoriaFisica == null) {
            System.out.println("Debe crear la memoria física primero.");
            return;
        }
        gestionMemoria.mostrarBloques();
    }

    public static void main(String[] args) {
        EjecutarSimulacion ejecutar = new EjecutarSimulacion();
        ejecutar.ejecutarSimulacion();
    }
}
