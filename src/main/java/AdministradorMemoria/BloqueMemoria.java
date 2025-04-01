package AdministradorMemoria;

public class BloqueMemoria {
    private int memoriaAsignada;
    private String estado;

    public BloqueMemoria(int memoriaAsignada, String estado) {
        this.memoriaAsignada = memoriaAsignada;
        this.estado = estado;
    }

    public int getMemoriaAsignada() {
        return memoriaAsignada;
    }

    public void setMemoriaAsignada(int nuevaMemoria) {
        this.memoriaAsignada = nuevaMemoria;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Bloque (" + memoriaAsignada + " KB) - " + estado;
    }
}

