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
        if (parties.length != 6) {
            throw new IllegalArgumentException("Ligne de monstre invalide : " + ligne);
        }

        String nom = parties[0].trim();
        if (nom.isEmpty()) {
            throw new IllegalArgumentException("Nom de monstre invalide : " + ligne);
        }

        int niveauMin = Integer.parseInt(parties[1].trim());
        int niveauMax = Integer.parseInt(parties[2].trim());
        int vie = Integer.parseInt(parties[3].trim());
        int attaque = Integer.parseInt(parties[4].trim());
        int defense = Integer.parseInt(parties[5].trim());

        if (niveauMin < 1 || niveauMax < niveauMin) {
            throw new IllegalArgumentException("Plage de niveaux invalide pour le monstre : " + ligne);
        }

        return new Monstre(nom, niveauMin, niveauMax, vie, attaque, defense);
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

    public static Monstre tirerMonstreAleatoire(List<Monstre> monstres, int niveauJoueur) {
        if (monstres == null || monstres.isEmpty()) {
            throw new IllegalStateException("Aucun monstre disponible pour le tirage.");
        }

        List<Monstre> monstresDisponibles = new ArrayList<>();

        for (Monstre monstre : monstres) {
            if (monstre.correspondAuNiveau(niveauJoueur)) {
                monstresDisponibles.add(monstre);
            }
        }

        if (monstresDisponibles.isEmpty()) {
            throw new IllegalStateException(
                "Aucun monstre disponible pour le niveau " + niveauJoueur + ". Verifie les plages de niveaux dans monstres.csv."
            );
        }

        Monstre modele = monstresDisponibles.get(RANDOM.nextInt(monstresDisponibles.size()));
        return modele.copie();
    }

    private static boolean estEntete(String ligne) {
        return ligne.equalsIgnoreCase("nom,niveaumin,niveaumax,pv,atq,def");
    }
}
