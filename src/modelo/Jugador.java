package modelo;

public class Jugador {
    private String nombre;
    private String apellido;
    private String email;
    private int dorsal;
    private String posicion;

    public Jugador(String nombre, String apellido, String email, int dorsal, String posicion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.dorsal = dorsal;
        this.posicion = posicion;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getDorsal() { return dorsal; }
    public void setDorsal(int dorsal) { this.dorsal = dorsal; }
    
    public String getPosicion() { return posicion; }
    public void setPosicion(String posicion) { this.posicion = posicion; }

    @Override
    public String toString() {
        return "Jugador{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", dorsal=" + dorsal +
                ", posicion='" + posicion + '\'' +
                '}';
    }
}