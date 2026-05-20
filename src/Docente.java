public class Docente extends Persona {
    private String asignatura;

    public Docente(String cedula, String nombreCompleto, int edad, String asignatura) {
        super(cedula, nombreCompleto, edad);
        this.asignatura = asignatura;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    @Override
    public void mostrarDatos() {
        super.mostrarDatos();
        System.out.println("Asignatura: " + asignatura);
        System.out.println("Tipo: Docente");
    }
}