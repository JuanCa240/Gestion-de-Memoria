package com.mycompany.gestiondememoria;

import java.util.ArrayList;

public class GestionMemoria {
    private MemoriaFisica memoriaFisica;
    
    public GestionMemoria(MemoriaFisica memoriaFisica){
        this.memoriaFisica = memoriaFisica;
    }
    
    public void dividirMemoria(int[] tamanosBloques) {
        int sumaBloques = 0;
        
        for (int tam : tamanosBloques) {
            sumaBloques += tam;
        }

        if (sumaBloques != memoriaFisica.getCapacidadTotal()) {
            System.out.println("Error: La suma de los bloques (" + sumaBloques + " KB) no coincide con la capacidad total de la memoria (" + memoriaFisica.getCapacidadTotal() + " KB). Intente nuevamente.");
            return;
        }

        // Limpiar bloques previos y agregar los nuevos
        ArrayList<BloqueMemoria> nuevosBloques = new ArrayList<>();
        for (int tam : tamanosBloques) {
            nuevosBloques.add(new BloqueMemoria(tam, "Disponible"));
        }

        memoriaFisica.setBloques(nuevosBloques);
        
        System.out.println("¡Memoria dividida en bloques con éxito.!");
        mostrarEstadisticasMemoria();
      
    }
    
    //Metodo First-Fit
    
    // 🔹 Método First-Fit
    public boolean asignarMemoriaFirstFit(Proceso proceso) {
        ArrayList<BloqueMemoria> bloques = memoriaFisica.getBloques();

        for (int i = 0; i < bloques.size(); i++) {
            BloqueMemoria bloque = bloques.get(i);

            if (bloque.getMemoriaAsignada() >= proceso.getTamanioSolicitud() && bloque.getEstado().equals("Disponible")) {
                // Asignar el bloque al proceso
                proceso.asignarMemoria(bloque);
                bloque.setEstado("Ocupado");

                // Si sobra espacio, dividir el bloque
                int espacioSobrante = bloque.getMemoriaAsignada() - proceso.getTamanioSolicitud();
                if (espacioSobrante > 0) {
                    BloqueMemoria nuevoBloque = new BloqueMemoria(espacioSobrante, "Disponible");
                    bloques.add(nuevoBloque);
                    bloque.setMemoriaAsignada(proceso.getTamanioSolicitud()); // Reducir el tamaño del bloque original
                }

                System.out.println("Proceso " + proceso.getId() + " asignado a un bloque de " + proceso.getTamanioSolicitud() + " KB.");
                mostrarEstadisticasMemoria();
                return true;
            }
        }

        System.out.println("No hay espacio disponible para el proceso " + proceso.getId() + ".");
        return false;
    }
    
    public boolean asignarMemoriaBestFit(Proceso proceso) {
        ArrayList<BloqueMemoria> bloques = memoriaFisica.getBloques();
        int mejorIndice = -1;
        int menorEspacioSobrante = Integer.MAX_VALUE;

        for (int i = 0; i < bloques.size(); i++) {
            BloqueMemoria bloque = bloques.get(i);
            int espacioSobrante = bloque.getMemoriaAsignada() - proceso.getTamanioSolicitud();

            if (bloque.getEstado().equals("Disponible") && espacioSobrante >= 0 && espacioSobrante < menorEspacioSobrante) {
                mejorIndice = i;
                menorEspacioSobrante = espacioSobrante;
            }
        }

        if (mejorIndice != -1) {
            BloqueMemoria bloque = bloques.get(mejorIndice);
            proceso.asignarMemoria(bloque);
            bloque.setEstado("Ocupado");

            if (menorEspacioSobrante > 0) {
                BloqueMemoria nuevoBloque = new BloqueMemoria(menorEspacioSobrante, "Disponible");
                bloques.add(nuevoBloque);
                bloque.setMemoriaAsignada(proceso.getTamanioSolicitud());
            }

            System.out.println("Proceso " + proceso.getId() + " asignado a un bloque de " + proceso.getTamanioSolicitud() + " KB (Best-Fit).");
            mostrarEstadisticasMemoria();
            return true;
        }

        System.out.println("No hay espacio disponible para el proceso " + proceso.getId() + " (Best-Fit).");
        return false;
    }
    
    public boolean asignarMemoriaWorstFit(Proceso proceso) {
        ArrayList<BloqueMemoria> bloques = memoriaFisica.getBloques();
        int peorIndice = -1;
        int mayorEspacioDisponible = -1;

        for (int i = 0; i < bloques.size(); i++) {
            BloqueMemoria bloque = bloques.get(i);
            int espacioSobrante = bloque.getMemoriaAsignada() - proceso.getTamanioSolicitud();

            if (bloque.getEstado().equals("Disponible") && espacioSobrante >= 0 && espacioSobrante > mayorEspacioDisponible) {
                peorIndice = i;
                mayorEspacioDisponible = espacioSobrante;
            }
        }

        if (peorIndice != -1) {
            BloqueMemoria bloque = bloques.get(peorIndice);
            proceso.asignarMemoria(bloque);
            bloque.setEstado("Ocupado");

            if (mayorEspacioDisponible > 0) {
                BloqueMemoria nuevoBloque = new BloqueMemoria(mayorEspacioDisponible, "Disponible");
                bloques.add(nuevoBloque);
                bloque.setMemoriaAsignada(proceso.getTamanioSolicitud());
            }

            System.out.println("Proceso " + proceso.getId() + " asignado a un bloque de " + proceso.getTamanioSolicitud() + " KB (Worst-Fit).");
            mostrarEstadisticasMemoria();
            return true;
        }

        System.out.println("No hay espacio disponible para el proceso " + proceso.getId() + " (Worst-Fit).");
        return false;
    }

    public void mostrarBloques() {
        System.out.println("Estado actual de la memoria:");
        ArrayList<BloqueMemoria> bloques = memoriaFisica.getBloques();

        for (int i = 0; i < bloques.size(); i++) {
            BloqueMemoria bloque = bloques.get(i);
            System.out.println(bloque);
        }
    }
    
    public void mostrarEstadisticasMemoria() {
        int totalMemoria = memoriaFisica.getCapacidadTotal();
        int memoriaUtilizada = 0;
        int memoriaFragmentada = 0;

        for (BloqueMemoria bloque : memoriaFisica.getBloques()) {
            if (bloque.getEstado().equals("Ocupado")) {
                memoriaUtilizada += bloque.getMemoriaAsignada();
            } else {
                memoriaFragmentada += bloque.getMemoriaAsignada();
            }
        }
        
        int memoriaLibre = totalMemoria - memoriaUtilizada;
        double porcentajeUso = (memoriaUtilizada * 100.0) / totalMemoria;
        double porcentajeFragmentacion = (memoriaFragmentada * 100.0) / totalMemoria;
        
        System.out.println("\n=== Estadísticas de Memoria ===");
        System.out.println("Memoria Total: " + totalMemoria + " KB");
        System.out.println("Memoria Utilizada: " + memoriaUtilizada + " KB (" + porcentajeUso + "%)");
        System.out.println("Memoria Libre: " + memoriaLibre + " KB");
        System.out.println("Memoria Fragmentada: " + memoriaFragmentada + " KB (" + porcentajeFragmentacion + "%)\n");
    }
}
