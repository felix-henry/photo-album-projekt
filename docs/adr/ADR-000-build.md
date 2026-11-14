# ADR-000: Maven mit Wrapper als Build-Werkzeug

**Status:** akzeptiert · **Datum:** 13.11.2026

## Kontext

Die Projektbeschreibung verlangt einen reproduzierbaren Build mit Maven oder Gradle und Wrapper (PB §6). Jedes Teammitglied und die CI sollen das Projekt mit einem Befehl bauen und testen können, ohne vorher selbst Maven zu installieren.

## Entscheidung

- Wir bauen mit Maven. Die deklarative `pom.xml` und die feste Verzeichnisstruktur reichen für unser kleines Projekt aus. Das Projektgerüst aus Block 3 nutzt bereits Maven.
- Der Maven-Wrapper (`mvnw`, `mvnw.cmd`, `.mvn/wrapper/`) legt Maven 3.9.16 fest und lädt diese Version beim ersten Aufruf herunter. Gebaut wird mit `./mvnw verify`, unter Windows mit `.\mvnw.cmd verify`.
- Die Versionen der Maven-Standard-Plugins für Ressourcen, Compiler und JAR sind in der `pom.xml` fest eingetragen. Darüber hinaus nutzt sie nur drei Bausteine mit fester Version, alle für Build und Tests. In der fertigen App ist keiner davon enthalten:
  - **JUnit 5.14.4** (nur im Test-Scope): das von der Projektbeschreibung vorgegebene Test-Framework (PB §6).
  - **Surefire 3.5.6:** führt die JUnit-Tests beim Build aus. Ein fehlgeschlagener Test bricht den Build ab.
  - **JaCoCo 0.8.16:** misst die Testabdeckung und erzeugt in der Phase `verify` den Bericht unter `target/site/jacoco/`, den Block 5 und 6 auswerten (PB §10). Die Version 0.8.12 aus den Folien unterstützt neuere JDKs wie Java 25 noch nicht.
- **Shade-Plugin 3.6.2:** packt beim Bauen Gson mit in das JAR und trägt `Main` als Startklasse ein. Die App startet damit aus einer einzigen Datei mit `java -jar target/photo-album-0.1.0-SNAPSHOT.jar` (ADR-003).

## Verworfene Alternativen

- **Gradle:** flexibler und bei großen Projekten schneller. Der zusätzliche Gestaltungsspielraum bringt bei unserem kleinen Projekt aber keinen Nutzen und erhöht den Einarbeitungsaufwand.
- **Maven ohne Wrapper:** Jeder bräuchte eine selbst installierte, passende Maven-Version. Der Build wäre dann nicht auf jedem Rechner gleich.
- **JUnit 6:** der Nachfolger von JUnit 5. Die Projektbeschreibung verlangt JUnit 5, und unsere Tests brauchen keine Funktion, die nur Version 6 bietet.
- **JAR mit separatem `lib`-Ordner für Gson:** funktioniert ebenfalls, aber JAR und Ordner müssen immer zusammen weitergegeben werden.

## Konsequenzen

- Ein frischer Clone baut mit einem Befehl. Vorausgesetzt ist nur ein installiertes JDK 21 (PB §6), wie es auch die CI verwendet.
- Eine neue Maven-Version wird bewusst in `.mvn/wrapper/maven-wrapper.properties` geändert und per PR geprüft.
- Weitere Bibliotheken, etwa für JSON, kommen nur mit eigenem ADR hinzu. Versionsupdates werden bewusst per PR übernommen.
