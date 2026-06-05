public class Cita {
    private String idCita;
    private String fechaHora;
    private String motivo;
    private Doctor doctorAsignado;
    private Paciente pacienteAsignado;

    public Cita(String idCita, String fechaHora, String motivo, Doctor doctorAsignado, Paciente pacienteAsignado) {
        this.idCita = idCita;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.doctorAsignado = doctorAsignado;
        this.pacienteAsignado = pacienteAsignado;
    }

    public void agendarCita() {
        System.out.println("Cita " + idCita + " agendada exitosamente para el " + fechaHora);
    }

    public String getIdCita() { return idCita; }
    public void setIdCita(String idCita) { this.idCita = idCita; }

    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public Doctor getDoctorAsignado() { return doctorAsignado; }
    public void setDoctorAsignado(Doctor doctorAsignado) { this.doctorAsignado = doctorAsignado; }

    public Paciente getPacienteAsignado() { return pacienteAsignado; }
    public void setPacienteAsignado(Paciente pacienteAsignado) { this.pacienteAsignado = pacienteAsignado; }
}
