package com.mycompany.gestiondememoria;

import java.util.ArrayList;


public class MemoriaFisica {
    private int capacidadTotal;
    private ArrayList<BloqueMemoria> bloques;
   

    public MemoriaFisica(int capaciadTotal) {
        this.capacidadTotal = capaciadTotal;
        this.bloques = new ArrayList<>(); 
        BloqueMemoria bloqueInicial = new BloqueMemoria(capacidadTotal, "Disponible");
        bloques.add(bloqueInicial);

    }
    
    public int getCapacidadTotal() {
        return capacidadTotal;
    }

    public ArrayList<BloqueMemoria> getBloques() {
        return bloques;
    }

    public void setBloques(ArrayList<BloqueMemoria> bloques) {
        this.bloques = bloques;
    }
    
}
