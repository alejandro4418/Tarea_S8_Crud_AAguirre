//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

void main() {
    Scanner sc = new Scanner(System.in);
    ArrayList<Persona> personas = new ArrayList<>();

    int opcion;
    do {
        System.out.println("\n--- SISTEMA INSTITUCIÓN ---");
        System.out.println("1. Registrar persona");
        System.out.println("2. Mostrar todos los registros");
        System.out.println("3. Actualizar persona por posición");
        System.out.println("4. Eliminar persona por posición");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");

        try {
            opcion = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Error: debe ingresar solo números.");
            sc.nextLine();
            opcion = 0;
            continue;
        }

        switch (opcion) {
            case 1:
                crearPersona(sc, personas);
                break;
            case 2:
                mostrarTodos(personas);
                break;
            case 3:
                actualizarPersona(sc, personas);
                break;
            case 4:
                eliminarPersona(sc, personas);
                break;
            case 5:
                System.out.println("Saliendo del sistema...");
                break;
            default:
                System.out.println("Error: opción inválida. Intente nuevamente.");
        }
    } while (opcion != 5);

    sc.close();
}

// Método para registrar (CREATE)
void crearPersona(Scanner sc, ArrayList<Persona> personas) {
    System.out.println("\n--- Registrar persona ---");
    System.out.println("Seleccione tipo:");
    System.out.println("1. Estudiante");
    System.out.println("2. Docente");
    System.out.print("Opción: ");

    int tipo;
    try {
        tipo = sc.nextInt();
        sc.nextLine();
    } catch (InputMismatchException e) {
        System.out.println("Error: debe ingresar solo números.");
        sc.nextLine();
        return;
    }

    if (tipo != 1 && tipo != 2) {
        System.out.println("Error: opción inválida. Intente nuevamente.");
        return;
    }

    // Validar campos vacíos
    System.out.print("Ingrese cédula: ");
    String cedula = sc.nextLine().trim();
    if (cedula.isEmpty()) {
        System.out.println("Campo obligatorio.");
        return;
    }

    System.out.print("Ingrese nombre: ");
    String nombre = sc.nextLine().trim();
    if (nombre.isEmpty()) {
        System.out.println("Campo obligatorio.");
        return;
    }

    System.out.print("Ingrese edad: ");
    int edad;
    try {
        edad = sc.nextInt();
        sc.nextLine();
        if (edad <= 0) {  // 👈 VALIDACIÓN DE EDAD
            System.out.println("Error: edad inválida.");
            return;
        }
    } catch (InputMismatchException e) {
        System.out.println("Error: debe ingresar solo números.");
        sc.nextLine();
        return;
    }

    if (tipo == 1) {
        System.out.print("Ingrese carrera: ");
        String carrera = sc.nextLine().trim();
        if (carrera.isEmpty()) {
            System.out.println("Campo obligatorio.");
            return;
        }
        personas.add(new Estudiante(cedula, nombre, edad, carrera));
    } else {
        System.out.print("Ingrese asignatura: ");
        String asignatura = sc.nextLine().trim();
        if (asignatura.isEmpty()) {
            System.out.println("Campo obligatorio.");
            return;
        }
        personas.add(new Docente(cedula, nombre, edad, asignatura));
    }

    System.out.println("Registro agregado correctamente.");
}

// Método para mostrar (READ)
void mostrarTodos(ArrayList<Persona> personas) {
    if (personas.isEmpty()) {
        System.out.println("No hay registros.");
        return;
    }

    for (int i = 0; i < personas.size(); i++) {
        System.out.println("\n--- Posición " + i + " ---");
        personas.get(i).mostrarDatos();
    }
}

// Método para actualizar (UPDATE)
void actualizarPersona(Scanner sc, ArrayList<Persona> personas) {
    if (personas.isEmpty()) {
        System.out.println("No hay registros para actualizar.");
        return;
    }

    System.out.print("Ingrese posición a actualizar: ");
    int posicion;
    try {
        posicion = sc.nextInt();
        sc.nextLine();
    } catch (InputMismatchException e) {
        System.out.println("Error: debe ingresar solo números.");
        sc.nextLine();
        return;
    }

    // Validar posición existente
    if (posicion < 0 || posicion >= personas.size()) {
        System.out.println("Registro no encontrado.");
        return;
    }

    Persona p = personas.get(posicion);
    System.out.println("\n--- Datos actuales ---");
    p.mostrarDatos();

    System.out.println("\n--- Ingrese nuevos datos ---");

    System.out.print("Nueva cédula: ");
    String nuevaCedula = sc.nextLine().trim();
    if (nuevaCedula.isEmpty()) {
        System.out.println("Campo obligatorio.");
        return;
    }

    System.out.print("Nuevo nombre: ");
    String nuevoNombre = sc.nextLine().trim();
    if (nuevoNombre.isEmpty()) {
        System.out.println("Campo obligatorio.");
        return;
    }

    System.out.print("Nueva edad: ");
    int nuevaEdad;
    try {
        nuevaEdad = sc.nextInt();
        sc.nextLine();
        if (nuevaEdad <= 0) {  // 👈 VALIDACIÓN DE EDAD
            System.out.println("Error: edad inválida.");
            return;
        }
    } catch (InputMismatchException e) {
        System.out.println("Error: debe ingresar solo números.");
        sc.nextLine();
        return;
    }

    // Actualizar atributos comunes
    p.setCedula(nuevaCedula);
    p.setNombreCompleto(nuevoNombre);
    p.setEdad(nuevaEdad);

    // Actualizar atributos específicos según el tipo
    if (p instanceof Estudiante) {
        System.out.print("Nueva carrera: ");
        String nuevaCarrera = sc.nextLine().trim();
        if (nuevaCarrera.isEmpty()) {
            System.out.println("Campo obligatorio.");
            return;
        }
        ((Estudiante) p).setCarrera(nuevaCarrera);
    } else if (p instanceof Docente) {
        System.out.print("Nueva asignatura: ");
        String nuevaAsignatura = sc.nextLine().trim();
        if (nuevaAsignatura.isEmpty()) {
            System.out.println("Campo obligatorio.");
            return;
        }
        ((Docente) p).setAsignatura(nuevaAsignatura);
    }

    System.out.println("Registro actualizado correctamente.");
}

// Método para eliminar (DELETE)
void eliminarPersona(Scanner sc, ArrayList<Persona> personas) {
    if (personas.isEmpty()) {
        System.out.println("No hay registros para eliminar.");
        return;
    }

    System.out.print("Ingrese posición a eliminar: ");
    int posicion;
    try {
        posicion = sc.nextInt();
        sc.nextLine();
    } catch (InputMismatchException e) {
        System.out.println("Error: debe ingresar solo números.");
        sc.nextLine();
        return;
    }

    // Validar posición existente
    if (posicion < 0 || posicion >= personas.size()) {
        System.out.println("Registro no encontrado.");
        return;
    }

    personas.remove(posicion);
    System.out.println("Registro eliminado correctamente.");
}

