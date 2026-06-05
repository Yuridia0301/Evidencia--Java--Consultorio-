public class Doctor extends Usuario {
    private String especialidad;

    public Doctor(String id, String nombreCompleto, String especialidad) {
        super(id, nombreCompleto);
        this.especialidad = especialidad;
    }

    @Override
    public void mostrarDatos() {
        System.out.println("ID Doctor: " + getId() + " | Nombre: " + getNombreCompleto() + " | Especialidad: " + especialidad);
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}