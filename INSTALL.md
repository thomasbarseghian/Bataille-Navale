# INSTALL.md – Bataille Navale

Ce document décrit les étapes nécessaires pour **cloner**, **compiler** et **lancer** l’application *Bataille Navale* développée en Java.

---

## Prérequis

Avant de commencer, assurez-vous d’avoir installé les outils suivants :

- **Java JDK 17** (ou version compatible)
- **Git**
- (Optionnel mais recommandé) **IntelliJ IDEA**

Pour vérifier l’installation de Java :


java -version


## Récupération du projet

### Clonage via SSH
```bash
git clone git@git.unistra.fr:barseghian-leger/a31-bataille-navale.git
```
### Clonage via HTTPS
```bash
git clone https://git.unistra.fr/barseghian-leger/a31-bataille-navale.git
```
Se placer ensuite dans le dossier du projet :
```bash
cd a31-bataille-navale
```

Compilation du projet
Compilation en ligne de commande
Depuis la racine du projet :
```bash
javac -d out $(find src -name "*.java")
```
Les fichiers .class seront générés dans le dossier out.

Compilation avec IntelliJ IDEA
Lancer IntelliJ IDEA
Cliquer sur File → Open
Sélectionner le dossier a31-bataille-navale
Vérifier la configuration du SDK :
File → Project Structure → Project SDK
Sélectionner un JDK Java 17
IntelliJ compile automatiquement le projet

Lancement de l’application
Lancement en ligne de commande
Depuis la racine du projet :
java -cp out game.Main

Lancement avec IntelliJ IDEA
Ouvrir la classe contenant la méthode main
Cliquer sur Run
L’application se lance (interface graphique ou terminal selon la vue utilisée)

## Structure du projet  
  
src/  
 ├── model/        # Logique métier (jeu, grille, joueurs, objets)  
 ├── view/         # Interfaces utilisateur (écrans, affichage)  
 ├── controller/   # Contrôleurs (MVC)  
 └── Main          #Pour lancer le jeu  
  
