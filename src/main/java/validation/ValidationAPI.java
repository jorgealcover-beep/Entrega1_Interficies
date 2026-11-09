package validation;

/**
 * Llibreria pròpia per validar les dades dels formularis.
 * Tots els mètodes són estàtics, així es poden cridar sense crear cap objecte:
 * ValidationAPI.nomValid("Anna")
 */
public class ValidationAPI {

    // Comprova que el text no sigui null ni estigui buit
    public static boolean campBuit(String text) {
        return text == null || text.trim().isEmpty();
    }

    // Comprova que el nom comenci amb lletra majúscula
    public static boolean nomValid(String nom) {
        if (campBuit(nom)) {
            return false;
        }
        char primera = nom.trim().charAt(0);
        return Character.isUpperCase(primera);
    }

    // El DNI ha de tenir 9 caràcters (8 números i una lletra)
    public static boolean dniValid(String dni) {
        if (campBuit(dni)) {
            return false;
        }
        dni = dni.trim();
        if (dni.length() != 9) {
            return false;
        }
        for (int i = 0; i < 8; i++) {
            if (!Character.isDigit(dni.charAt(i))) {
                return false;
            }
        }
        return Character.isLetter(dni.charAt(8));
    }

    // El telèfon ha de tenir 9 números
    public static boolean telefonValid(String telefon) {
        if (campBuit(telefon)) {
            return false;
        }
        telefon = telefon.trim();
        if (telefon.length() != 9) {
            return false;
        }
        for (int i = 0; i < telefon.length(); i++) {
            if (!Character.isDigit(telefon.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
