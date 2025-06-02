# gestionnaire_notification
gestionnaire_notification
# Objectifs pédagogiques
Appliquer un design pattern comportemental (Observer)


Mettre en œuvre deux principes SOLID (SRP, OCP)


Renforcer la maîtrise des interfaces et de l'organisation du code


# Énoncé
Tu dois développer une application console qui simule un système de notifications dans une entreprise.
Plusieurs employés doivent pouvoir s’abonner ou désabonner  à un service de notification.


Lorsqu’un message est envoyé, chaque employé abonné doit recevoir une notification personnalisée.


Le système doit respecter :


Le principe de responsabilité unique (SRP)


Le principe d’ouverture/fermeture (OCP)


Ton application doit permettre de :
Afficher la liste des employés
Ajouter et retirer des abonnés
Envoyer un message(email et notification console) à tous les abonnés en indiquant la personne qui a envoyé le message.
Éviter que l’expéditeur reçoive son propre message ?


Afficher les notifications reçues d’un abonné
Donner la possibilité de verifier si un employé est abonné ou pas

# Contraintes
Utiliser des interfaces


Aucune interaction directe entre le classe de notification et les classes appelantes


Code lisible, structuré, commenté


Exécution attendue en console Java



