package rpjava.model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SauvegardePersonnage {
    private static final String DOSSIER = "saves";
    private static final String EXTENSION = ".txt";

    public static void sauvegarder(Player joueur) {
        if (joueur == null) {
            return;
        }

        Path dossier = Paths.get(DOSSIER);
        try {
            Files.createDirectories(dossier);
            Path fichier = dossier.resolve(nomFichier(joueur.getNom()));

            List<String> lignes = new ArrayList<>();
            lignes.add("nom=" + joueur.getNom());
            lignes.add("classe=" + classePour(joueur));
            lignes.add("lv=" + joueur.getNiveau());
            lignes.add("exp=" + joueur.getXp());
            lignes.add("pvActuels=" + joueur.getHp());
            lignes.add("atq=" + joueur.getAtq());
            lignes.add("def=" + joueur.getDef());
            lignes.add("decede=" + (joueur.estVivant() ? "non" : "oui"));

            Files.write(fichier, lignes, StandardCharsets.UTF_8);
            System.out.println("Sauvegarde effectuee pour " + joueur.getNom() + ".");
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    public static Player charger(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            return null;
        }

        Path fichier = Paths.get(DOSSIER, nomFichier(nom));
        if (!Files.exists(fichier)) {
            System.out.println("Sauvegarde introuvable : " + fichier);
            return null;
        }

        Map<String, String> data = new HashMap<>();
        try {
            for (String ligne : Files.readAllLines(fichier, StandardCharsets.UTF_8)) {
                int idx = ligne.indexOf('=');
                if (idx <= 0) {
                    continue;
                }
                String cle = ligne.substring(0, idx).trim().toLowerCase();
                String valeur = ligne.substring(idx + 1).trim();
                data.put(cle, valeur);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement : " + e.getMessage());
            return null;
        }

        String nomLu = data.getOrDefault("nom", nom.trim());
        String classe = data.get("classe");
        Player joueur = creerDepuisClasse(classe, nomLu);
        if (joueur == null) {
            System.out.println("Classe invalide dans la sauvegarde.");
            return null;
        }

        joueur.niveau = lireInt(data.get("lv"), joueur.getNiveau());
        joueur.xp = lireInt(data.get("exp"), joueur.getXp());
        joueur.hp = lireInt(data.get("pvactuels"), joueur.getHp());
        joueur.atq = lireInt(data.get("atq"), joueur.getAtq());
        joueur.def = lireInt(data.get("def"), joueur.getDef());

        int hpMax = data.containsKey("pvmax") ? lireInt(data.get("pvmax"), joueur.hp) : joueur.hp;
        joueur.hpMax = hpMax;
        int defBase = data.containsKey("defbase") ? lireInt(data.get("defbase"), joueur.def) : joueur.def;
        joueur.defBase = defBase;
        joueur.defenseActive = false;
        joueur.position = 0;

        return joueur;
    }

    public static List<String> listerSauvegardes() {
        Path dossier = Paths.get(DOSSIER);
        if (!Files.isDirectory(dossier)) {
            return new ArrayList<>();
        }

        try (Stream<Path> stream = Files.list(dossier)) {
            return stream
                .filter(p -> p.getFileName().toString().toLowerCase().endsWith(EXTENSION))
                .map(p -> p.getFileName().toString())
                .map(SauvegardePersonnage::nomSansExtension)
                .sorted()
                .collect(Collectors.toList());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private static Player creerDepuisClasse(String classe, String nom) {
        if (classe == null) {
            return null;
        }
        String classeNormalisee = classe.trim().toUpperCase();
        if (classeNormalisee.equals("GUERRIER")) {
            return new Guerrier(nom);
        }
        if (classeNormalisee.equals("MAGE")) {
            return new Mage(nom);
        }
        return null;
    }

    private static String classePour(Player joueur) {
        if (joueur instanceof Guerrier) {
            return "GUERRIER";
        }
        if (joueur instanceof Mage) {
            return "MAGE";
        }
        return "INCONNU";
    }

    private static int lireInt(String valeur, int defaut) {
        try {
            return Integer.parseInt(valeur);
        } catch (Exception e) {
            return defaut;
        }
    }

    private static String nomFichier(String nom) {
        String base = nom.trim();
        if (base.isEmpty()) {
            base = "SansNom";
        }
        return base + EXTENSION;
    }

    private static String nomSansExtension(String nomFichier) {
        if (nomFichier.toLowerCase().endsWith(EXTENSION)) {
            return nomFichier.substring(0, nomFichier.length() - EXTENSION.length());
        }
        return nomFichier;
    }
}
