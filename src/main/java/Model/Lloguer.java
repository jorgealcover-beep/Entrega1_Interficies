package model;

import java.time.LocalDate;


public class Lloguer {

    
    public enum Estat {
        ACTIU("Actiu"),
        RETORNAT("Retornat"),
        FORA_DE_TERMINI("Fora de termini");

        private final String text;

        Estat(String text) { this.text = text; }

        @Override
        public String toString() { return text; }
    }

    
    public static final int DIES_LLOGUER = 3;

    private final int numRegistre;
    private final Usuari usuari;
    private final Joc joc;
    private final Plataforma plataforma;
    private final LocalDate dataInici;
    private final LocalDate dataPrevista;
    private LocalDate dataRetorn; // null mentre no s'ha retornat

    
    public Lloguer(int numRegistre, Usuari usuari, Joc joc, Plataforma plataforma) {
        this(numRegistre, usuari, joc, plataforma,
             LocalDate.now(), LocalDate.now().plusDays(DIES_LLOGUER));
    }

    
    public Lloguer(int numRegistre, Usuari usuari, Joc joc, Plataforma plataforma,
                   LocalDate dataInici, LocalDate dataPrevista) {
        this.numRegistre = numRegistre;
        this.usuari = usuari;
        this.joc = joc;
        this.plataforma = plataforma;
        this.dataInici = dataInici;
        this.dataPrevista = dataPrevista;
    }

    public int getNumRegistre() { return numRegistre; }
    public Usuari getUsuari() { return usuari; }
    public Joc getJoc() { return joc; }
    public Plataforma getPlataforma() { return plataforma; }
    public LocalDate getDataInici() { return dataInici; }
    public LocalDate getDataPrevista() { return dataPrevista; }
    public LocalDate getDataRetorn() { return dataRetorn; }

    public boolean isRetornat() {
        return dataRetorn != null;
    }

    
    public boolean isActiu() {
        return !isRetornat();
    }

    public Estat getEstat() {
        if (isRetornat()) {
            return Estat.RETORNAT;
        }
        if (LocalDate.now().isAfter(dataPrevista)) {
            return Estat.FORA_DE_TERMINI;
        }
        return Estat.ACTIU;
    }

   
    public void retornar() {
        if (isRetornat()) {
            throw new IllegalStateException("El lloguer " + numRegistre + " ja està retornat.");
        }
        joc.retornarUnitat(plataforma);
        dataRetorn = LocalDate.now();
    }
}
