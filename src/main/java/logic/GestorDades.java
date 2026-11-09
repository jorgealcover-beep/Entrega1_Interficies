package logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import model.Joc;
import model.Lloguer;
import model.Plataforma;
import model.Usuari;

/**
 * Guarda les dades en memòria (llistes) i controla les regles del lloguer.
 */
public class GestorDades {

    private ArrayList<Usuari> usuaris = new ArrayList<>();
    private ArrayList<Joc> jocs = new ArrayList<>();
    private ArrayList<Lloguer> lloguers = new ArrayList<>();
    private int properNumRegistre = 1;

    public GestorDades() {
        carregarDadesInicials();
    }

    // ---------- Dades inicials ----------

    private void carregarDadesInicials() {
        
        Usuari laura = new Usuari("Laura", "García", "Pons", "12345678A", "600111222");
        Usuari marc = new Usuari("Marc", "López", "Serra", "87654321B", "600333444");
        usuaris.add(laura);
        usuaris.add(marc);

       
        HashMap<Plataforma, Integer> unitats1 = new HashMap<>();
        unitats1.put(Plataforma.PS5, 2);
        unitats1.put(Plataforma.SWITCH2, 1);
        Joc zelda = new Joc("The Legend of Zelda", "Aventura", unitats1);

        
        HashMap<Plataforma, Integer> unitats2 = new HashMap<>();
        unitats2.put(Plataforma.XBOX, 2);
        unitats2.put(Plataforma.PC, 1);
        Joc cyberpunk = new Joc("Cyberpunk 2077", "Acció", unitats2);

        jocs.add(zelda);
        jocs.add(cyberpunk);

        
        LocalDate avui = LocalDate.now();
        zelda.reservarUnitat(Plataforma.SWITCH2);
        Lloguer l1 = new Lloguer(properNumRegistre, laura, zelda, Plataforma.SWITCH2,
                avui.minusDays(10), avui.minusDays(7));
        lloguers.add(0, l1);
        properNumRegistre++;

        
        cyberpunk.reservarUnitat(Plataforma.XBOX);
        Lloguer l2 = new Lloguer(properNumRegistre, marc, cyberpunk, Plataforma.XBOX);
        lloguers.add(0, l2);
        properNumRegistre++;
    }

    // ---------- Consultes ----------

    public ArrayList<Usuari> getUsuaris() {
        return usuaris;
    }

    public ArrayList<Joc> getJocs() {
        return jocs;
    }

    
    public ArrayList<Lloguer> getLloguers() {
        return lloguers;
    }

    public ArrayList<Lloguer> getLloguersActius() {
        ArrayList<Lloguer> actius = new ArrayList<>();
        for (Lloguer l : lloguers) {
            if (!l.isRetornat()) {
                actius.add(l);
            }
        }
        return actius;
    }

    public int getTotalLloguers() {
        return lloguers.size();
    }

    public int getProperNumRegistre() {
        return properNumRegistre;
    }

    // ---------- Usuaris ----------

    public void crearUsuari(Usuari nou) throws GestorException {
        for (Usuari u : usuaris) {
            if (u.getDni().equalsIgnoreCase(nou.getDni())) {
                throw new GestorException("Ja existeix un usuari amb aquest DNI.");
            }
        }
        usuaris.add(nou);
    }

    public void eliminarUsuari(Usuari usuari) throws GestorException {
        if (usuari == null) {
            throw new GestorException("Has de seleccionar un usuari.");
        }
        for (Lloguer l : lloguers) {
            if (l.getUsuari() == usuari && !l.isRetornat()) {
                throw new GestorException("No es pot eliminar l'usuari perquè té lloguers actius.");
            }
        }
        usuaris.remove(usuari);
    }

    // ---------- Jocs ----------

    public void crearJoc(Joc nou) throws GestorException {
        for (Joc j : jocs) {
            if (j.getTitol().equalsIgnoreCase(nou.getTitol())) {
                throw new GestorException("Ja existeix un joc amb aquest títol.");
            }
        }
        jocs.add(nou);
    }

    public void eliminarJoc(Joc joc) throws GestorException {
        if (joc == null) {
            throw new GestorException("Has de seleccionar un joc.");
        }
        for (Lloguer l : lloguers) {
            if (l.getJoc() == joc && !l.isRetornat()) {
                throw new GestorException("No es pot eliminar el joc perquè té lloguers actius.");
            }
        }
        jocs.remove(joc);
    }

    // ---------- Lloguers ----------

    public Lloguer registrarLloguer(Usuari usuari, Joc joc, Plataforma plataforma)
            throws GestorException {
        if (usuari == null || joc == null || plataforma == null) {
            throw new GestorException("Has de seleccionar usuari, joc i plataforma.");
        }
        if (!joc.estaDisponible(plataforma)) {
            throw new GestorException("No queden unitats d'aquest joc per a aquesta plataforma.");
        }

        
        for (Lloguer l : lloguers) {
            if (!l.isRetornat() && l.getUsuari() == usuari
                    && l.getJoc() == joc && l.getPlataforma() == plataforma) {
                throw new GestorException("Aquest usuari ja té aquest joc llogat en aquesta plataforma.");
            }
        }

        joc.reservarUnitat(plataforma);
        Lloguer nou = new Lloguer(properNumRegistre, usuari, joc, plataforma);
        lloguers.add(0, nou);
        properNumRegistre++;
        return nou;
    }

    public void tornarLloguer(Lloguer lloguer) throws GestorException {
        if (lloguer == null) {
            throw new GestorException("Has de seleccionar un lloguer.");
        }
        if (lloguer.isRetornat()) {
            throw new GestorException("Aquest lloguer ja està retornat.");
        }
        lloguer.retornar();
    }
}
