# PIT_AGENTS.md – Regeln für KI-Coding-Agents in diesem Repository

## Build & Test
- Build: `./mvnw verify` (muss grün sein, bevor ein PR geöffnet wird)
- Tests: JUnit 5 in `src/test/java`, Spiegel von `src/main/java`

## Architektur (siehe ARCHITECTURE.md, docs/adr/)
- Pakete unter `de.dhbw.photoalbum`: `ui` → `application` → `domain`; `infrastructure` implementiert die Ports
- Geschäftsregeln nur in `domain`, nie in `ui` oder `infrastructure`
- Keine neuen Dependencies ohne ADR

## Verboten
- Direkte Commits auf `main`; Änderungen an `.github/workflows` ohne Rückfrage
- Löschen oder Abschwächen bestehender Tests
- Secrets, absolute Pfade, `System.exit`, `Thread.sleep` in Produktionscode
- Duplikate am Dateinamen statt am Inhalt erkennen
- Dateityp an der Endung statt am Inhalt erkennen
- Originaldateien der Nutzerin verändern oder löschen

## Definition of Done für einen PR
- Anforderungs-ID im Titel, Tests für Normal-, Fehler- und Grenzfall, Doku aktualisiert
- War ein Agent beteiligt: Label `ai-assisted` und Fall in `AI_USAGE.md` (Aufgabe, Werkzeug/Modell mit Prompt, Ergebnis, Bewertung, Entscheidung)
