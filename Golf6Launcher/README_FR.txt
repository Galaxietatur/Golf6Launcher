GOLF 6 LAUNCHER — PROTOTYPE 0.1

Ce dossier contient le CODE SOURCE d'un premier prototype Android.
Ce n'est PAS encore un fichier APK prêt à installer.

Fonctions présentes :
- écran paysage adapté à 1024 x 600 ;
- thème sombre avec accents bleus ;
- horloge et date ;
- boutons précédent / lecture-pause / suivant (envoi de commandes multimédia Android) ;
- menu Applications qui liste les applications installées.

Limites de cette première version :
- la vitesse GPS n'est pas encore activée ;
- le titre, l'artiste et la pochette ne sont pas encore récupérés ;
- les commandes multimédia dépendent de la compatibilité Bluetooth de l'autoradio ;
- l'application ne remplace pas automatiquement l'accueil : Android doit autoriser
  l'utilisateur à la choisir comme application d'accueil (Home).

POUR CONSTRUIRE L'APK :
1. Sur un ordinateur, installer Android Studio.
2. Ouvrir le dossier Golf6Launcher dans Android Studio.
3. Laisser Android Studio synchroniser Gradle et installer les composants demandés.
4. Menu Build > Build Bundle(s) / APK(s) > Build APK(s).
5. Récupérer l'APK dans app/build/outputs/apk/debug/.

Configuration :
- applicationId : fr.golf6.launcher
- minSdk : 23
- targetSdk : 32
- compileSdk : 35
- Java 17 recommandé avec Android Gradle Plugin 8.6.1.

Ne pas utiliser en conduisant pour configurer ou manipuler les menus.


COMPILATION AUTOMATIQUE SANS INSTALLER ANDROID STUDIO (AVEC GITHUB) :
1. Depuis un ordinateur ou un navigateur, connecte-toi à github.com ou crée un compte gratuit.
2. Crée un nouveau dépôt (repository), par exemple Golf6Launcher.
3. Décompresse ce ZIP sur ton ordinateur.
4. Dans le dépôt GitHub, utilise Add file > Upload files et dépose TOUS les fichiers et dossiers du projet, y compris le dossier caché .github.
5. Clique sur Commit changes.
6. Ouvre l'onglet Actions, puis le workflow "Build Golf 6 APK".
7. Quand l'exécution est terminée avec une coche verte, ouvre-la et télécharge l'artefact "Golf6Launcher-debug-apk".
8. Décompresse l'artefact : il contient app-debug.apk. Transfère-le sur l'autoradio et installe-le.

Cette compilation utilise les services de GitHub Actions. Il faut un compte GitHub.
Le fichier APK n'est pas encore compilé dans ce ZIP.
