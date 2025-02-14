package modelo;

import java.util.Random;

public class Enfrentamiento {
    private Equipo equipo1;
    private Equipo equipo2;
    private boolean finalizado;
    private Equipo ganador;

    public Enfrentamiento(Equipo equipo1, Equipo equipo2) {
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.finalizado = false;
        this.ganador = null;
    }

    public Equipo getEquipo1() {
        return equipo1;
    }

    public Equipo getEquipo2() {
        return equipo2;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public Equipo getGanador() {
        return ganador;
    }

    public void marcarComoFinalizado(Equipo ganador) {
        this.finalizado = true;
        this.ganador = ganador;
    }

    public void simular() {
        if (!finalizado && equipo1 != null && equipo2 != null) {
            Random random = new Random();
            this.ganador = random.nextBoolean() ? equipo1 : equipo2;
            this.finalizado = true;
        }
    }
}
