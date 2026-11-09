package model;

import java.util.EnumMap;
import java.util.Map;


public class Joc {

    private final String titol;
    private final String genere;
    private final Map<Plataforma, Integer> unitatsTotals = new EnumMap<>(Plataforma.class);
    private final Map<Plataforma, Integer> unitatsDisponibles = new EnumMap<>(Plataforma.class);

   
    public Joc(String titol, String genere, Map<Plataforma, Integer> unitats) {
        this.titol = titol;
        this.genere = genere;
        for (Map.Entry<Plataforma, Integer> e : unitats.entrySet()) {
            unitatsTotals.put(e.getKey(), e.getValue());
            unitatsDisponibles.put(e.getKey(), e.getValue());
        }
    }

    public String getTitol() { return titol; }
    public String getGenere() { return genere; }

    
    public boolean tePlataforma(Plataforma p) {
        return unitatsTotals.containsKey(p);
    }

    public int getUnitatsTotals(Plataforma p) {
        return unitatsTotals.getOrDefault(p, 0);
    }

    public int getUnitatsDisponibles(Plataforma p) {
        return unitatsDisponibles.getOrDefault(p, 0);
    }

    /** Serveix per habilitar o deshabilitar els radio buttons. */
    public boolean estaDisponible(Plataforma p) {
        return getUnitatsDisponibles(p) > 0;
    }

    /** Descompta una unitat en llogar. Llança excepció si no n'hi ha. */
    public void reservarUnitat(Plataforma p) {
        if (!estaDisponible(p)) {
            throw new IllegalStateException("No hi ha unitats disponibles de " + titol + " per a " + p);
        }
        unitatsDisponibles.put(p, getUnitatsDisponibles(p) - 1);
    }

    /** Retorna una unitat en tornar el lloguer. */
    public void retornarUnitat(Plataforma p) {
        if (!tePlataforma(p) || getUnitatsDisponibles(p) >= getUnitatsTotals(p)) {
            throw new IllegalStateException("Retorn invàlid de " + titol + " per a " + p);
        }
        unitatsDisponibles.put(p, getUnitatsDisponibles(p) + 1);
    }

    @Override
    public String toString() {
        return titol;
    }
}
