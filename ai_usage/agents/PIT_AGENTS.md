# PIT_AGENTS.md – Regeln für KI-Coding-Agents in diesem Repository (Pit)

## Build & Test
- Build: `./mvnw verify` (muss grün sein, bevor ein PR geöffnet wird)
- Tests: JUnit 5 in `src/test/java`, Spiegel von `src/main/java`

## Architektur (siehe ARCHITECTURE.md, docs/adr/)
- Pakete unter `de.dhbw.photoalbum`: `ui` → `application` → `domain`; `infrastructure` implementiert die Ports
- Geschäftsregeln gehören in `domain` (außer „Datei lesbar“, siehe ARCHITECTURE.md), nie in `ui`
- Keine neuen Dependencies ohne ADR

## Verboten
- Direkte Commits auf `main`; Änderungen an `.github/workflows` ohne Rückfrage
- Löschen oder Abschwächen bestehender Tests
- Secrets, absolute Pfade, `System.exit`, `Thread.sleep` in Produktionscode
- Duplikate am Dateinamen statt am Inhalt erkennen (FA-07)
- Dateityp an der Endung statt am Inhalt erkennen (A-03)
- Originaldateien der Nutzerin verändern oder löschen (A-08)

## Definition of Done für einen PR
- Anforderungs-ID im Titel, Tests für Normal-, Fehler- und Grenzfall, Doku aktualisiert
- War ein Agent beteiligt: Label `ai-assisted` und Fall in `ai_usage/AI_USAGE.md` (Aufgabe, Werkzeug/Modell mit Prompt, Ergebnis, Bewertung, Entscheidung)
