# ADR-000: Maven mit Wrapper als Build-Werkzeug

**Status:** akzeptiert · **Datum:** 13.11.2026

## Kontext

Die Projektbeschreibung verlangt einen reproduzierbaren Build mit Maven oder Gradle und Wrapper (PB §6). Jedes Teammitglied und die CI sollen das Projekt mit einem Befehl bauen und testen können, ohne vorher selbst Maven zu installieren.

## Entscheidung

- Wir bauen mit Maven. Die deklarative `pom.xml` und die feste Verzeichnisstruktur reichen für unser kleines Projekt aus. Das Projektgerüst aus Block 3 nutzt bereits Maven.
- Der Maven-Wrapper (`mvnw`, `mvnw.cmd`, `.mvn/wrapper/`) legt Maven 3.9.16 fest und lädt diese Version beim ersten Aufruf herunter. Gebaut wird mit `./mvnw verify`, unter Windows mit `.\mvnw.cmd verify`.

## Verworfene Alternativen

- **Gradle:** flexibler und bei großen Projekten schneller. Der zusätzliche Gestaltungsspielraum bringt bei unserem kleinen Projekt aber keinen Nutzen und erhöht den Einarbeitungsaufwand.
- **Maven ohne Wrapper:** Jeder bräuchte eine selbst installierte, passende Maven-Version. Der Build wäre dann nicht auf jedem Rechner gleich.

## Konsequenzen

- Ein frischer Clone baut mit einem Befehl. Vorausgesetzt ist nur ein installiertes JDK ab Version 21.
- Eine neue Maven-Version wird bewusst in `.mvn/wrapper/maven-wrapper.properties` geändert und per PR geprüft.
