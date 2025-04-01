package AdministradorMemoria;

public class Proceso {
    private int id;
    private int tamanioSolicitud;
    private String estado;
    private BloqueMemoria bloqueAsignado;

    public Proceso(int id, int tamanioSolicitud) {
        this.id = id;
        this.tamanioSolicitud = tamanioSolicitud;
        this.estado = "Esperando";
        this.bloqueAsignado = null;
    }

    public int getId() {
        return id;
    }

    public int getTamanioSolicitud() {
        return tamanioSolicitud;
    }

    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public BloqueMemoria getBloqueAsignado() {
        return bloqueAsignado;
    }
    
    public void asignarMemoria(BloqueMemoria bloque) {
        this.bloqueAsignado = bloque;
        this.estado = "Ejecutando";
    }

    public void liberarMemoria() {
        if (bloqueAsignado != null) {
            bloqueAsignado.setEstado("Disponible");
            bloqueAsignado = null;
            this.estado = "Terminado";
        }
    }

    @Override
    public String toString() {
        return "Proceso " + id + " - Tamaño: " + tamanioSolicitud + " KB - Estado: " + estado;
    }

}
