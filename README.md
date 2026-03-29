# RPJava — Jeu de rôle en console

RPJava est un jeu de rôle au tour par tour en console. Le joueur choisit une classe, combat des monstres sur un plateau 1D, monte de niveau et collecte des objets.

---

## Lancer le programme

### Prérequis
- Java 11 ou supérieur installé (`java -version` pour vérifier)
- PowerShell (Windows)

### Démarrage
Depuis la racine du projet :

```powershell
.\run.ps1
```

Le script compile automatiquement tous les fichiers `.java` dans `out/`, puis lance le jeu.

> **Lancer sur Linux / macOS (sans PowerShell) :**
>
> 1. Créer le dossier de sortie :
>    ```bash
>    mkdir -p out
>    ```
> 2. Compiler tous les fichiers Java :
>    ```bash
>    find src -name "*.java" > sources.txt
>    javac -d out @sources.txt
>    ```
> 3. Lancer le jeu **depuis la racine du projet** (important : `monstres.csv` et `saves/` sont cherchés depuis le répertoire courant) :
>    ```bash
>    java -cp out rpjava.Main
>    ```
> 4. (Optionnel) Nettoyer le fichier temporaire :
>    ```bash
>    rm sources.txt
>    ```

---

## Format du fichier `monstres.csv`

Le fichier `monstres.csv` doit se trouver à la racine du projet. Il définit tous les monstres disponibles dans le jeu.

```
nom,niveauMin,niveauMax,pv,atq,def
Rat enrage,1,2,55,12,3
Dragon ancien,12,99,360,40,20
```

| Colonne     | Type   | Description                                              |
|-------------|--------|----------------------------------------------------------|
| `nom`       | String | Nom du monstre                                           |
| `niveauMin` | int    | Niveau minimum du joueur pour rencontrer ce monstre      |
| `niveauMax` | int    | Niveau maximum du joueur pour rencontrer ce monstre      |
| `pv`        | int    | Points de vie du monstre                                 |
| `atq`       | int    | Valeur d'attaque                                         |
| `def`       | int    | Valeur de défense                                        |

- La première ligne (en-tête) est ignorée automatiquement.
- Les lignes vides sont ignorées.
- Un monstre est tiré aléatoirement parmi ceux dont `niveauMin <= niveauJoueur <= niveauMax`.

---

## Format du fichier de sauvegarde (`saves/<nom>.txt`)

Les sauvegardes se trouvent dans le dossier `saves/`. Chaque personnage a son propre fichier `<nom>.txt`.

```
nom=Arthur
classe=GUERRIER
lv=3
exp=40
pvActuels=120
atq=25
def=13
decede=non
```

| Clé          | Valeurs possibles         | Description                        |
|--------------|---------------------------|------------------------------------|
| `nom`        | texte                     | Nom du personnage                  |
| `classe`     | `GUERRIER`, `MAGE`        | Classe du personnage               |
| `lv`         | entier ≥ 1                | Niveau actuel                      |
| `exp`        | entier 0–99               | XP accumulée dans le niveau        |
| `pvActuels`  | entier                    | HP au moment de la sauvegarde      |
| `atq`        | entier                    | Valeur d'attaque                   |
| `def`        | entier                    | Valeur de défense                  |
| `decede`     | `oui`, `non`              | Un personnage décédé ne peut pas être chargé |

---

## Choix de conception

### Héritage
- `Personnage` (abstraite) — attributs communs à tous (HP, ATQ, DEF, position, défense)
  - `Player` (abstraite) — ajoute XP, niveau, inventaire ; méthode `bonusNiveau()` abstraite
    - `Guerrier` — épée (portée 1) et arc (portée 2–3)
    - `Mage` — projectile magique (portée 1–2) et boule de feu (portée 2–5)
  - `Monstre` — IA simple : avance vers le joueur si hors portée, attaque sinon

### Système de combat au tour par tour
Chaque tour, le joueur choisit une action, puis le monstre joue automatiquement son tour.

### Plateau 1D
Le combat se déroule sur un plateau de 10 cases (`J` = joueur, `M` = monstre). La distance entre les deux personnages conditionne quelles attaques sont utilisables.

### Exceptions métier
Trois exceptions métier dédiées (`ActionInvalideException`, `AttaqueInvalideException`, `ClasseInvalideException`) permettent de gérer les saisies incorrectes sans planter le programme.

### toString()
Chaque entité affichable (`Personnage`, `Player`, `Objet`) implémente `toString()` pour centraliser le formatage de l'affichage.

---

## Fonctionnalités bonus

### Système d'inventaire et objets aléatoires
Après chaque victoire, le monstre a **70% de chance** de lâcher un objet. Les objets ont 4 raretés avec des bonus croissants :

| Rareté      | Probabilité | Bonus HP   | Bonus ATQ | Bonus DEF |
|-------------|-------------|------------|-----------|-----------|
| COMMUN      | 50%         | +3 à +8    | +1 à +3   | +0 à +2   |
| RARE        | 30%         | +8 à +18   | +3 à +7   | +2 à +5   |
| EPIQUE      | 15%         | +20 à +40  | +8 à +14  | +5 à +10  |
| LEGENDAIRE  | 5%          | +50 à +100 | +15 à +25 | +10 à +20 |

**Utilisation :**
1. Choisir `4. Inventaire` dans le menu de combat
2. Saisir le numéro de l'objet à utiliser, ou `0` pour annuler
3. Les bonus sont appliqués **définitivement** sur le personnage
4. Utiliser un objet consomme le tour (le monstre attaque ensuite)

> Les objets ne sont pas sauvegardés actuellement — ils sont perdus à la fermeture.

### Système de défense
L'action `3. Defendre` augmente la défense de 30% pour le tour suivant du monstre, puis revient à la normale.

### Chargement / sauvegarde de personnage
Au démarrage, le joueur peut créer un nouveau personnage ou charger une sauvegarde existante. À la fermeture (`q`), le jeu propose de sauvegarder.

---

## Structure d'une sauvegarde

Les fichiers de sauvegarde se trouvent dans `saves/<nom>.txt`. Exemple pour un guerrier nommé `lo` :

```
nom=lo
classe=GUERRIER
lv=1
exp=20
pvActuels=73
atq=15
def=10
decede=non
```

