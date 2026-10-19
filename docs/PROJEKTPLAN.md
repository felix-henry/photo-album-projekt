# Projektplan Photo Album

**Grundsätze**
- **Grundlage** ist allein die Projektbeschreibung (PB). Jedes Paket erfüllt eine Vorgabe oder bringt Punkte (PB §16, §18). Extras lassen wir weg (PB §2).
- **Unabhängig von der Vorlesung.** Es gibt keine Termine, nur eine Reihenfolge. Wir arbeiten so schnell wie möglich, Pausen gehen jederzeit.
- **Erst der Kern, dann die Oberfläche.** Wir bauen zuerst Domäne, Use Cases und Speicherung komplett mit Tests. Wenn das quasi fertig ist, kommt eine Swing-Oberfläche dazu, schnell mit KI gebaut (Teamentscheidung).
- **Alle in der Git-Historie** (PB §3): Jede Person entwickelt einen eigenen Bereich und reviewt die PRs der anderen.

## 1 Einstieg (jede Person)

1. `git config user.name` und `user.email` setzen, mit der Adresse, die im eigenen GitHub-Konto verifiziert ist.
2. Die Einladung ins Repo annehmen.
3. Auf dem Branch von PR #4 `./mvnw verify` ausführen (Windows: `mvnw.cmd verify`) und das Ergebnis dort kommentieren.
4. Im Abstimmungs-Issue eine Rangfolge der Bereiche abgeben (§4).

## 2 Arbeitsweise

- **Kanban-Board:** To do → In Arbeit → Review → Fertig. Pro Person höchstens ein Paket in Arbeit. Offene Reviews haben Vorrang.
- **Ablauf (PB §9):** Issue → Branch → PR → Review → CI → Merge. Commit-Nachrichten konventionell, mit Anforderungs- oder Issue-ID. Jede Person macht mindestens ein Code Review.
- **Fertig heißt:**
  - Die Akzeptanzkriterien sind im selben PR als Tests umgesetzt: Normal-, Fehler- und Grenzfall. Der Fehlerfall prüft, dass der Zustand unverändert bleibt. Die AK-ID steht im Testnamen.
  - Diagramme und ARCHITECTURE.md sind nachgezogen.
  - Die CI ist grün.
- **KI (PB §13):**
  - PRs mit KI-Anteil bekommen das Label `ai-assisted`.
  - Jeder größere KI-Einsatz kommt als Fall in AI_USAGE.md (mindestens 3, davon mindestens einer begründet verworfen).
  - Prompts liegen im Repo.
- **Regeln:**
  - Freigeben darf nur, wer im PR nichts committet hat.
  - Gemergt wird per Merge-Commit.
  - Wer pausiert, schreibt eine Notiz ins Issue und schiebt das Paket zurück nach „To do“.
  - Entschieden wird per Kommentar im Issue. Bei Patt entscheidet Felix als Product Owner.
  - Was von außen kommt (Dozentin, Change Request), blockiert nichts. Fehlt etwas, wird trotzdem getaggt und später nachgeliefert.

## 3 Reihenfolge

**A – Block 1–3 abschließen.** Einarbeiten und mergen machen die anderen, nicht Pit.
- **A1:** Ein Abstimmungs-Issue zu diesen Punkten:
  - Repo öffentlich oder GitHub Pro? Das ist nötig für einen geschützten `main` (PB §6).
  - Maven ja oder nein?
  - Arbeitsweise
  - Bereichswahl

  Felix lädt Alanur und Alex ein und schützt danach `main`.
- **A2:** PR #4 (Projektgerüst) mergen, danach die CI aufsetzen (B1).
- **A3:** PR #1: Im README die Vorgehensentscheidung auf Kanban umstellen. Danach mergen, Tag `block-1`.
- **A4:** PR #2:
  - AI_USAGE Fall 1 („Grill me“) macht das Team.
  - Annahmen und NFA-Werte bestätigen. Keine NFA darf eine Zusatzfunktion erzwingen.
  - Danach mergen, Tag `block-2`.
- **A5:** PR #3:
  - ADRs bestätigen.
  - Offene Punkte entscheiden: `validate`, S-2 und die KI-Funktion (Vorschlag: nicht bauen, nur entwerfen).
  - UI-Technik Swing als kurzes ADR festhalten.
  - Den Übungs-Agenten streichen.
  - Danach mergen, Tag `block-3`.
  - Anschließend bewertet das Team AI_USAGE Fall 2 und 3.
- **A6:** Läuft ständig mit:
  - Antworten und Feedback der Dozentin kommen als Issue und werden per PR eingearbeitet (PB §4).
  - An die Dozentin fragen: Ist eine Teamgröße von 5 in Ordnung? Was gilt bei F-01 bis F-03?

**B – Pflichtfunktionalität (Block 4)**
- **B1:** CI mit GitHub Actions (`./mvnw verify`, JaCoCo). AGENTS.md bleibt laut `.gitignore` vorerst lokal und kommt zum Abschluss gemeinsam ins Repo (PB §9).
- **B2:** Ports, Fakes und Domänen-API (Bereich 1). Danach laufen die Bereiche parallel.
- **B3:** Datei-Adapter (Bereich 4) und Use Cases aller Bereiche, ohne Oberfläche, über Tests abgesichert.
- **B4:** Swing-Oberfläche, sobald alle Use Cases stehen: schnell mit KI gebaut, Label `ai-assisted`, als Fall in AI_USAGE.md. Sie ruft nur die Use Cases auf und enthält keine Regeln.
- **B5:** CHANGELOG, Release `v0.1.0`, Tag `block-4`.

**C – Testen (Block 5, PB §10)**
- TESTING.md mit 2 Blackbox-Verfahren (Grenzwertanalyse, Entscheidungstabelle) und der Zuordnung Akzeptanzkriterium → Verfahren → Test.
- Mindestens 10 Unit-Tests (entstehen über „Fertig heißt“) und mindestens 2 Integrationstests (AK-10.1, AK-09.1).
- Usability-Test (mit der Swing-Oberfläche), Security-Tests für Dateieingaben, Performance-Messung einer NFA, Evaluation Set für die entworfene KI-Funktion.
- Release `v1.0.0`, Tag `block-5`.

**D – Change Request (Block 6, PB §10–§12)**
- Coverage je Schicht interpretieren.
- Sobald der Change Request bekannt ist: CHANGE_REQUEST.md mit Impact Analysis, **vor** der Umsetzung.
- Refactoring dokumentieren (REFACTORING.md), möglichst so, dass es den Change Request erleichtert. Nur mit grünen Tests, ohne Verhaltensänderung.
- Change Request umsetzen. Eine ehrlich dokumentierte Teilumsetzung ist in Ordnung. Rückblick, `v1.1.0`, Tag `block-6`.

**E – Abschluss (Block 7, PB §14–§16)**
- RISK_ANALYSIS.md mit Release-Entscheidung.
- Präsentation (PDF in `docs/presentation/`):
  - 10 Minuten, höchstens 8 Folien.
  - Sie beantwortet die sechs Fragen aus PB §16.2. Jede Person übernimmt eine Frage.
  - Alle können jeden Bereich erklären.
- AGENTS.md gemeinsam abstimmen und aufnehmen, README final, Checkliste aus PB §18 abhaken, Tag `final` nach der Präsentation.

Tag-Folge: `block-1` → `block-2` → `block-3` → `block-4` = `v0.1.0` → `v1.0.0` → `block-5` → `v1.1.0` → `block-6` → `final`

## 4 Entwicklungsbereiche zum Aussuchen

Wer welchen Bereich bekommt, ergibt sich aus der Rangfolge im Abstimmungs-Issue. Bei Gleichstand einigen sich die Beteiligten, sonst wird gelost. Jeder Bereich umfasst Code, Tests und einen eigenen Abschnitt in TESTING.md. Masken gibt es erst mit der Swing-Oberfläche (B4).

| Bereich | Entwickelt | Testet zusätzlich |
|---|---|---|
| 1 Kern & CI | CI, Ports, Fakes, Domänenregeln in `Album`/`Photo` | Domänentests, Coverage |
| 2 Foto hinzufügen | UC-01, Hash-Adapter | Grenzwertanalyse, Security-Tests |
| 3 Löschen & Entfernen | UC-02, Foto entfernen | Entscheidungstabelle, Integrationstest Löschen |
| 4 Speicherung & Beschreiben | Datei-Adapter mit Speicherformat, Foto beschreiben | Integrationstest Speicherfehler, Regression beim Change Request |
| 5 Alben & Oberfläche | Album erstellen/anzeigen/umbenennen, Main/Verdrahtung; am Ende die Swing-Oberfläche (B4) | Usability-Test, Performance |

Dazu übernimmt jede Person eine Zusatzaufgabe:
- TESTING.md zusammenführen
- Evaluation Set
- Impact Analysis und Refactoring
- Risikoanalyse und Release-Entscheidung
- Präsentations-PDF

Felix übernimmt stattdessen Product Owner, Repo-Admin und den Kontakt zur Dozentin. Jedes Release setzt eine andere Person.

## 5 Offen

- Repo öffentlich oder GitHub Pro?
- Wer nimmt welchen Bereich?
- An die Dozentin: Teamgröße 5, F-01 bis F-03

Nur Vorlesung, keine Projektvorgabe: Git-Hausaufgabe, Partner-Reviews und Checkpoints laufen nebenbei und blockieren nichts.
