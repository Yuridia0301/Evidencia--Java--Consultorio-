public class Paciente extends Usuario {
    private String historialMedico;

    public Paciente(String id, String nombreCompleto, String historialMedico) {
        super(id, nombreCompleto);
        this.historialMedico = historialMedico;
    }

    @Override
    public void mostrarDatos() {
        System.out.println("ID Paciente: " + getId() + " | Nombre: " + getNombreCompleto() + " | Historial: " + historialMedico);
    }

    public String getHistorialMedico() {
        return historialMedico;
    }

    public void setHistorialMedico(String historialMedico) {
        this.historialMedico = historialMedico;
    }
}
