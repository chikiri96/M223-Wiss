# M223-Wiss

Hier wird ein Back- und Frontend erstellt, bei dem ein tokenbasiertes Login ermöglicht wird. 


USERSTORIES:

Als Benutzer möchte ich mich in das System einloggen können, um auf meine persönliche Adressliste zuzugreifen.

Als Administrator möchte ich die Möglichkeit haben, neue Benutzerkonten zu erstellen und zu verwalten.

Als Benutzer möchte ich meine Kontakte nach verschiedenen Kriterien filtern können, wie z.B. nach Name, Stadt oder Postleitzahl.


Sicherheitskonzept_

Das Sicherheitskonzept umfasst verschiedene Aspekte der Authentifizierung und Autorisierung innerhalb der Anwendungsarchitektur. Im Folgenden werde ich das Sicherheitskonzept auf Basis des bereitgestellten Codes dokumentieren:

Benutzerauthentifizierung und Autorisierung: Das Sicherheitskonzept basiert auf der Authentifizierung und Autorisierung von Benutzern. Es gibt drei Hauptrollen: Benutzer, Moderator und Administrator. Diese Rollen bestimmen den Zugriff auf verschiedene Teile der Anwendung.

JWT-Token-basierte Authentifizierung: Die Benutzerauthentifizierung wird durch JSON Web Tokens (JWT) realisiert. Benutzer authentifizieren sich durch Bereitstellung ihrer Anmeldeinformationen. Nach erfolgreicher Authentifizierung wird ein JWT-Token generiert und an den Client zurückgegeben. Dieses Token wird für alle weiteren Anfragen des Benutzers verwendet und enthält Informationen über die Berechtigungen des Benutzers.

Passwortverschlüsselung: Die Benutzerpasswörter werden vor dem Speichern verschlüsselt, um die Sicherheit zu gewährleisten. Hier wird der BCrypt-PasswordEncoder verwendet, um die Passwörter zu hashen.

Ausnahmebehandlung: Eine zentrale Ausnahmebehandlungsklasse (AuthEntryPointJwt) wird verwendet, um unbefugten Zugriff zu behandeln. Wenn ein Benutzer versucht, auf einen geschützten Endpunkt zuzugreifen, ohne die erforderlichen Berechtigungen zu besitzen, wird eine entsprechende Fehlermeldung zurückgegeben.

Authentifizierungsfilter: Ein Filter (AuthTokenFilter) wird verwendet, um JWT-Token aus den Anfragen zu extrahieren und die Authentifizierung des Benutzers zu überprüfen. Wenn ein gültiges Token vorhanden ist, wird der Benutzer authentifiziert und seine Berechtigungen werden festgelegt.

Sicherheitskonfiguration: Die WebSecurityConfig-Klasse konfiguriert die Sicherheitsrichtlinien für die Anwendung. Sie legt die Berechtigungen für verschiedene Endpunkte fest und konfiguriert die CORS (Cross-Origin Resource Sharing)-Richtlinien, um den Zugriff auf Ressourcen von verschiedenen Domänen zu steuern.

Insgesamt gewährleistet dieses Sicherheitskonzept die sichere Authentifizierung und Autorisierung von Benutzern innerhalb der Anwendung, wodurch der Zugriff auf geschützte Ressourcen kontrolliert und unbefugter Zugriff verhindert wird.

Frontend_

React ist wie digitales LEGO. Du baust deine Website aus wiederverwendbaren Bausteinen, den sogenannten Komponenten. Diese Komponenten kannst du immer wieder verwenden, um Zeit zu sparen. Außerdem aktualisiert React nur die Teile deiner Seite, die sich tatsächlich ändern, was die Performance verbessert. Es ist einfach zu lernen und hat eine große Gemeinschaft von Entwicklern, die dir helfen können.

Backend_

Das Backend der Adressverwaltung ist wie das Gehirn einer Maschine, das Anfragen verarbeitet, auf die Datenbank zugreift und dem Frontend die benötigten Informationen liefert. Es wurde mit Java und Spring Boot entwickelt, was uns hilft, schnell und effizient RESTful-Services zu erstellen.

Um sicherzustellen, dass nur autorisierte Benutzer auf unsere Daten zugreifen können, verwenden wir Spring Security. Dieses Tool ermöglicht es uns, Benutzer anzumelden und zu überprüfen, ob sie die nötigen Berechtigungen haben. Wenn sich ein Benutzer anmeldet, erhalten sie ein spezielles Token, das sie verwenden können, um ihre Identität zu bestätigen.

Unsere Daten werden in einer MySQL-Datenbank gespeichert, in der wir separate Tabellen für Benutzer und Adressen haben. Die Sicherheit unserer Daten ist uns wichtig, daher werden Benutzerpasswörter verschlüsselt, um sicherzustellen, dass sie geschützt sind.

Unsere API bietet verschiedene Endpunkte, über die Benutzer sich anmelden können oder neue Konten erstellen können. Außerdem können sie über spezielle Endpunkte Adressen hinzufügen, aktualisieren oder löschen.

Insgesamt haben wir ein umfassendes Sicherheitskonzept implementiert, um die Vertraulichkeit, Integrität und Verfügbarkeit unserer Daten zu gewährleisten. Wir verwenden Verschlüsselung, HTTPS für sichere Datenübertragungen und sorgen dafür, dass nur autorisierte Benutzer auf unsere Daten zugreifen können.





Quellen: Ian Walser + [ChatGPT](https://chatgpt.com/)
