package rpjava.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParserCSV {
    private static final Random RANDOM = new Random();

    public static Monstre parseMonstre(String ligne) {
        String[] parties = ligne.split(",");
        if (parties.length != 4) {
            throw new IllegalArgumentException("Ligne de monstre invalide : " + ligne);
        }

        String nom = parties[0].trim();
        if (nom.isEmpty()) {
            throw new IllegalArgumentException("Nom de monstre invalide : " + ligne);
        }

        int vie = Integer.parseInt(parties[1].trim());
        int attaque = Integer.parseInt(parties[2].trim());
        int defense = Integer.parseInt(parties[3].trim());

        return new Monstre(nom, vie, attaque, defense);
    }

    public static List<Monstre> chargerMonstres(String cheminFichier) {
        List<Monstre> monstres = new ArrayList<>();

        try {
            for (String ligne : Files.readAllLines(Path.of(cheminFichier))) {
                String ligneNettoyee = ligne.trim();

                if (ligneNettoyee.isEmpty() || estEntete(ligneNettoyee)) {
                    continue;
                }

                monstres.add(parseMonstre(ligneNettoyee));
            }
        } catch (IOException e) {
            throw new IllegalStateException("Impossible de charger le fichier de monstres : " + cheminFichier, e);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valeur numerique invalide dans le fichier de monstres : " + cheminFichier, e);
        }

        if (monstres.isEmpty()) {
            throw new IllegalStateException("Aucun monstre disponible dans " + cheminFichier + ".");
        }

        return monstres;
    }

    public static Monstre tirerMonstreAleatoire(List<Monstre> monstres) {
        if (monstres == null || monstres.isEmpty()) {
            throw new IllegalStateException("Aucun monstre disponible pour le tirage.");
        }

        Monstre modele = monstres.get(RANDOM.nextInt(monstres.size()));
        return modele.copie();
    }

    private static boolean estEntete(String ligne) {
        return ligne.equalsIgnoreCase("nom,pv,atq,def");
    }
}
