# ADR-004: Build mit Maven und Maven Wrapper, Java 21

- **Status:** vorgeschlagen (vom Team zu bestätigen) · **Datum:** 19.10.2026

## Kontext

- Gefordert: Java 21; nachvollziehbarer, reproduzierbarer Build mit Maven oder Gradle mit Wrapper; JUnit 5; CI ohne manuelle Installation (PB §6).
- Hausaufgabe B3 F50: `mvn verify` bzw. `gradle build` läuft bei jeder Person grün.

## Entscheidung

- Maven mit Maven Wrapper (`mvnw`, `mvnw.cmd`); `maven.compiler.release = 21`; JUnit 5 als Testabhängigkeit.
- Paketstruktur `domain`, `application`, `infrastructure`, `ui` (B3 F11, F50).

## Verworfene Alternative

- **Gradle mit Wrapper:** gleichwertig erlaubt (PB §6). Verworfen, weil Maven den Build deklarativ in einer Datei beschreibt und das Projekt keine eigene Build-Logik braucht. *Teamentscheidung offen: Entscheidend ist, welches Werkzeug das Team kennt – falls Gradle, dieses ADR umkehren.*

## Konsequenzen

- \+ deklarativer Build in einer Datei (`pom.xml`); Wrapper fixiert die Maven-Version
- − XML ist länger als ein Gradle-Skript
