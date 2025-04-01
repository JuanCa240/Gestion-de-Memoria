package EficienciaMemoria;

public class Fragmentacion {
    protected String tipo;
    protected int memoriaPerdida;
    
    public Fragmentacion(String tipo, int memoriaPerdida) {
        this.tipo = tipo;
        this.memoriaPerdida = memoriaPerdida;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public int getMemoriaPerdida() {
        return memoriaPerdida;
    }
    
    @Override
    public String toString() {
        return "Tipo: " + tipo + ", Memoria perdida: " + memoriaPerdida + " KB";
    }
}