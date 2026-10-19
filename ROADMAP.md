# Roadmap

**Grundsätze**
- Grundlage ist allein die [Projektbeschreibung](docs/task_description/Projektbeschreibung_Photo_Album.md). Jedes Paket erfüllt eine Vorgabe oder bringt Punkte (PB §16, §18). Extras lassen wir weg (PB §2).
- Die Arbeit ist unabhängig von der Vorlesung. Es gibt keine Termine, nur eine Reihenfolge: so schnell wie möglich, Pausen gehen jederzeit.
- Erst der Kern, dann die Oberfläche: Domäne, Use Cases und Speicherung entstehen komplett mit Tests. Danach bauen wir eine Swing-Oberfläche mit KI.
- Alle stehen in der Git-Historie (PB §3): Jede Person entwickelt einen eigenen Bereich und reviewt die PRs der anderen.

## Meilensteine und Releases

| Block | Schwerpunkt | Wesentlicher Zwischenstand | Tag | Erledigt |
| --- | --- | --- | --- | :---: |
| 1 | Einführung und Vorgehen | Team, Repository, [README.md](README.md) und Vorgehensentscheidung | `block-1` | ☐ |
| 2 | Requirements Engineering | [REQUIREMENTS.md](REQUIREMENTS.md) mit 6–10 FA, 3–5 NFA, mindestens 2 Use Cases und Akzeptanzkriterien; UI-Prototyp; erster Eintrag in [AI_USAGE.md](ai_usage/AI_USAGE.md) | `block-2` | ☐ |
| 3 | Architektur und Entwurf | [ARCHITECTURE.md](ARCHITECTURE.md), Klassen- und Sequenzdiagramm, mindestens ein ADR und Projektgerüst | `block-3` | ☐ |
| 4 | Implementierung und Git | Pflichtfunktionalität, Build, Reviews und CI | `block-4` = `v0.1.0` | ☐ |
| 5 | Tests und Qualität | [TESTING.md](TESTING.md), Unit- und Integrationstests, 2 Blackbox-Verfahren, Usability-Protokoll und Evaluation Set; Pflichtfunktionalität komplett | `v1.0.0`, `block-5` | ☐ |
| 6 | Change Request und Refactoring | Impact Analysis in [CHANGE_REQUEST.md](CHANGE_REQUEST.md), Coverage-Interpretation, [REFACTORING.md](docs/REFACTORING.md) und Umsetzung des Change Requests | `v1.1.0`, `block-6` | ☐ |
| 7 | Risiken und Präsentation | [RISK_ANALYSIS.md](RISK_ANALYSIS.md), begründete Release-Entscheidung und Abschlusspräsentation | `final` nach der Präsentation | ☐ |

Releases werden nach Semantic Versioning getaggt und in [CHANGELOG.md](CHANGELOG.md) dokumentiert.

## Nächste gemeinsame Schritte

- [x] Teammitglieder und Teamnamen „Commitment Issues“ festhalten.
- [ ] Abstimmungs-Issue anlegen:
  - Repo öffentlich oder GitHub Pro, damit `main` geschützt werden kann (PB §6)
  - Maven ja oder nein
  - Arbeitsweise (siehe unten)
  - Board ja oder nein
  - Wahl der Bereiche
- [ ] Alanur und Alex einladen, danach `main` schützen.
- [ ] Jede Person setzt `git config user.name` und `user.email` mit der Adresse, die in ihrem GitHub-Konto verifiziert ist, und führt `./mvnw verify` auf dem Branch von PR #4 aus.
- [ ] PR #4 (Projektgerüst) mergen, danach die CI aufsetzen.
- [ ] PR #1 (Vorgehensentscheidung) prüfen und mergen, dann Tag `block-1`.
- [ ] PR #2 (Requirements):
  - AI_USAGE Fall 1 („Grill me“) durch das Team
  - Annahmen und NFA-Werte bestätigen
  - mergen, dann Tag `block-2`
- [ ] PR #3 (Architektur):
  - ADRs bestätigen
  - `validate` und S-2 entscheiden
  - Swing als ADR festhalten
  - mergen, dann Tag `block-3`
  - danach AI_USAGE Fall 2 und 3 bewerten
- [ ] Laufend: Antworten und Feedback der Dozentin kommen als Issue und werden per PR eingearbeitet (PB §4). Offene Fragen an die Dozentin: Teamgröße 5, F-01 bis F-03.

Einarbeiten und Freigeben in PR #1–#4 übernehmen die anderen, nicht Pit, der die Entwürfe erstellt hat.

## Danach: Reihenfolge in Block 4–7

- **Block 4:**
  1. CI mit GitHub Actions (`./mvnw verify`, JaCoCo).
  2. Ports, Fakes und Domänen-API (Bereich 1). Danach laufen die Bereiche parallel: Use Cases und Datei-Adapter, abgesichert über Tests.
  3. Wenn alle Use Cases stehen, kommt die Swing-Oberfläche (mit KI, Label `ai-assisted`, als Fall in AI_USAGE). Sie ruft nur die Use Cases auf.
  4. CHANGELOG, `v0.1.0`.
- **Block 5:**
  - TESTING.md mit Grenzwertanalyse und Entscheidungstabelle, dazu die Zuordnung Akzeptanzkriterium → Verfahren → Test.
  - Mindestens 10 Unit-Tests und mindestens 2 Integrationstests (AK-10.1, AK-09.1).
  - Usability-Test, Security-Tests, eine Performance-Messung und das Evaluation Set.
  - Danach `v1.0.0`.
- **Block 6:**
  - Coverage je Schicht interpretieren.
  - Sobald der Change Request bekannt ist, zuerst die Impact Analysis schreiben.
  - Refactoring nur mit grünen Tests.
  - Change Request umsetzen; eine ehrlich dokumentierte Teilumsetzung ist in Ordnung (PB §11).
  - Danach `v1.1.0`.
- **Block 7:**
  - Risikoanalyse und Release-Entscheidung.
  - Präsentation: 10 Minuten, höchstens 8 Folien, PDF in `docs/presentation/`. Sie beantwortet die sechs Fragen aus PB §16.2, jede Person übernimmt eine davon und kann jeden Bereich erklären.
  - AGENTS.md gemeinsam abstimmen und aufnehmen (bis dahin lokal laut `.gitignore`).
  - README fertigstellen, Checkliste PB §18 abarbeiten, dann `final`.

## Arbeitsweise

- **Board (Kanban):** To do → In Arbeit → Review → Fertig. Jede Person hat höchstens ein Paket in Arbeit. Offene Reviews gehen vor.
- **Ablauf (PB §9):** Issue → Branch → Pull Request → Review → CI → Merge. Commits sind konventionell und nennen eine Anforderungs- oder Issue-ID. Jede Person macht mindestens ein Code Review.
- **Fertig heißt:**
  - Die Akzeptanzkriterien sind im selben PR als Tests umgesetzt: Normal-, Fehler- und Grenzfall. Der Fehlerfall prüft, dass der Zustand unverändert bleibt. Die AK-ID steht im Testnamen.
  - Diagramme und ARCHITECTURE.md sind nachgezogen.
  - Die CI ist grün.
- **KI (PB §13):** KI-unterstützte PRs bekommen das Label `ai-assisted`. Jeder größere KI-Einsatz wird ein Fall in AI_USAGE.md: mindestens 3 Fälle, davon mindestens einer begründet verworfen. Prompts liegen im Repo.
- **Regeln:**
  - Freigeben darf nur, wer im PR nichts committet hat. Gemergt wird per Merge-Commit.
  - Wer pausiert, schreibt eine Notiz ins Issue und schiebt das Paket zurück nach To do.
  - Entscheidungen fallen per Kommentar im Issue. Bei Patt entscheidet Felix als Product Owner.
  - Was von außen kommt (Dozentin, Change Request), blockiert nichts. Fehlt etwas, wird trotzdem getaggt und später nachgeliefert.

## Entwicklungsbereiche zum Aussuchen

Jede Person gibt im Abstimmungs-Issue eine Rangfolge ab. Bei Gleichstand einigen wir uns, sonst wird gelost. Jeder Bereich umfasst Code, Tests und einen eigenen Abschnitt in TESTING.md.

| Bereich | Entwickelt | Testet zusätzlich |
| --- | --- | --- |
| 1 Kern & CI | CI, Ports, Fakes, Domänenregeln in `Album`/`Photo` | Domänentests, Coverage |
| 2 Foto hinzufügen | UC-01, Hash-Adapter | Grenzwertanalyse, Security-Tests |
| 3 Löschen & Entfernen | UC-02, Foto entfernen | Entscheidungstabelle, Integrationstest Löschen |
| 4 Speicherung & Beschreiben | Datei-Adapter mit Speicherformat, Foto beschreiben | Integrationstest Speicherfehler, Regression beim Change Request |
| 5 Alben & Oberfläche | Album erstellen/anzeigen/umbenennen, Main; am Ende die Swing-Oberfläche | Usability-Test, Performance |

**Zusätzlich übernimmt jede Person eine dieser Aufgaben:**
- TESTING.md zusammenführen
- Evaluation Set
- Impact Analysis und Refactoring
- Risikoanalyse und Release-Entscheidung
- Präsentations-PDF

**Felix** ist Git-Maintainer, Product Owner und Kontakt zur Dozentin. Jedes Release setzt eine andere Person.
