# PROPOSITION DE JEU - Groupe G

# Sea of Crabes

### Phase de jeu classique : `jeu de rapidité`
- Un `océan aléatoire` à chaque partie dans lequel on peut naviguer
- `Viewport` : Une partie de la carte centrée sur chaque joueur
- Plusieurs ennemies et alliés (décrit plus bas)

#### Objectif    
- Vaincre le terrifiant Kraken
- Etre le plus rapide

#### Jeu
- Explorer toutes les îles afin de s'améliorer
- Barre de vie (ne pas mourir)  
- Etre assez fort pour vaincre le Kraken
- Gameplay de bateau en coopération sur Mer
- Gameplay individuelle sur Terre
#### Originalité       
- Une carte généré procéduralement à chaque début de partie
- Un jeu en 2D isométrique
- Ecran partagé

#### Scenario

Le but du jeu est d’explorer la mer qui s’offre aux joueurs et accumuler assez de ressources pour tuer le Kraken. Deux joueurs coopèrent sur une carte pour accomplir leur objectif. Ils pourront accoster des îles et trouver les coffres forts dans lesquels se trouvent les ressources mais attention des ennemis les protègent. Certains de ses trésors permettent d’entraver la progression de l'adversaire. La phase finale est le combat contre le Kraken. La victoire sera assurer en tuant le Kraken sauf si leur bar ne descend pas à zero avant !

#### Les Personnages

- Le personnage contrôlé par le joueur (Pirate)
- Le “Do or Die” (Le bateau du pirate)
- Les ennemis terres (des crabes assoiffés de sang)
- Les ennemis en mer ( Bateau ennemis )
- Le Kraken (Boss final de mer)
- Crabs King (Boss final de terre )  

#### Gameplay

##### Première phase

En mer : les joueurs manœuvre leurs bateau, l'un peut utiliser le canon pour combattre les ennemis en mer, l'autre controle les déplacements du bateau. Les ennemis vaincues donnent des améliorations pour les joueurs. Si par chance, vous trouvez en mer des barils de rhum, récupérez- les. Ils permettent au pirate de se régénérer.

Sur Terre : Les joueurs descendent de leur bateau et essaye chacun de récupérer le coffre sur l’île et les ressources qu’il contient. Le pirate se sert de son épée pour combattre les assauts des crabes qui feront tout pour protéger leur trésor. Les ressources permettent au joueur d’améliorer le bateau et les planches sur l’île de le régénérer. La dernière île dans l’avant dernière zone de jeu est obligatoire. Sur celle-ci se trouve le Crab King qui donne accès au combat contre le Kraken

##### Deuxième phase 

Une fois que les joueurs entrant dans la dernière  zone de jeu.. Ils se serviront de toutes ce qu’il ont collecté pour tuer le Kraken. Pour ce faire, ils doivent éviter les attaques de ses tentacules tout en lui infligeant un maximum de dégâts avec les boulets de canon.


#### Spécificités techniques
- La map est créée en tronçon, chaque tronçon contient une île, le dernier tronçon contient le Kraken. Les tronçons sont générés procéduralement et stockés dans une file d’attente jusqu’à être supprimés

- Chacun des tronçons est infini sur ses côtés. Implémentation en Donut, le côté droit nous emmène au côté gauche et inversement.

- Le joueur est toujours au centre de l’écran. Pour gérer le passage de gauche à droite, l’île est placée “au centre” du tronçon pour pouvoir passer d’un côté à l’autre sans voir l’île pour ne pas avoir l’impression de passer d’un côté à l’autre.

![Texte alternatif](/images-md/Representation_troncon.drawio.png "Schema de Vue")


---
    AUTHOR: MIRAS Romain, Polytech'Grenoble, Univ. Grenoble Alpes 


