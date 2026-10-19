# Roadmap

## Meilensteine und Releases

| Block | Schwerpunkt | Wesentlicher Zwischenstand | Geplanter Tag | Erledigt |
| --- | --- | --- | --- | :---: |
| 1 | Einführung und Vorgehen | Team, Repository, [README.md](README.md) und Vorgehensentscheidung | `block-1` | ☐ |
| 2 | Requirements Engineering | [REQUIREMENTS.md](REQUIREMENTS.md) mit 6-10 FA, 3-5 NFA, mindestens 2 Use Cases und Akzeptanzkriterien; UI-Prototyp; erster Eintrag in [AI_USAGE.md](ai_usage/AI_USAGE.md) | `block-2` | ☐ |
| 3 | Architektur und Entwurf | [ARCHITECTURE.md](ARCHITECTURE.md), Klassen- und Sequenzdiagramm, mindestens ein ADR und Projektgerüst | `block-3` | ☐ |
| 4 | Implementierung und Git | Pflichtfunktionalität, Build, Reviews und CI; Release `v0.1.0` | `block-4` | ☐ |
| 5 | Tests und Qualität | [TESTING.md](TESTING.md), Unit- und Integrationstests, 2 Blackbox-Verfahren, Usability-Protokoll und Evaluation Set | `block-5` | ☐ |
| 6 | Change Request und Refactoring | Impact Analysis in [CHANGE_REQUEST.md](CHANGE_REQUEST.md), Coverage-Interpretation, [REFACTORING.md](docs/REFACTORING.md) und Umsetzung des Change Requests; Release `v1.1.0` | `block-6` | ☐ |
| 7 | Risiken und Präsentation | [RISK_ANALYSIS.md](RISK_ANALYSIS.md), begründete Release-Entscheidung und Abschlusspräsentation | `final` nach Block 7 | ☐ |

Für die vollständige Pflichtfunktionalität ist außerdem Release `v1.0.0` vorgesehen. Releases werden nach Semantic Versioning getaggt und in [CHANGELOG.md](CHANGELOG.md) dokumentiert. Bewertet wird der Stand an jedem Block-Tag; verbindliche Abgabe ist `final` (PB §3).

## Nächste gemeinsame Schritte

- [x] Teammitglieder und Teamnamen „Commitment Issues“ festhalten.
- [x] Für Block 2 Stakeholder, Systemgrenze, Anforderungen und offene Fragen an die Auftraggeberin in [REQUIREMENTS.md](REQUIREMENTS.md) sammeln.
- [ ] Weitere Aufgabenverteilung gemeinsam festlegen: Rangfolge der [Entwicklungsbereiche](#entwicklungsbereiche-zum-aussuchen); Felix übernimmt Git-Maintenance.
- [ ] Vorgehensvorschlag besprechen und Entscheidung mit Begründung festhalten (Vorschlag in der [README](README.md#vorgehensentscheidung): Kanban-Fluss).
- [ ] Über die Nutzung eines Aufgabenboards entscheiden.
- [ ] Gemeinsames Repository, Zugriffsrechte und Schutz von `main` prüfen bzw. einrichten: Alanur und Alex einladen; für den Branch-Schutz Repo öffentlich oder GitHub Pro.
- [ ] Jede Person: `git config` mit der im GitHub-Konto verifizierten E-Mail, `./mvnw verify` ausführen.
- [ ] Zwischenstände für Block 1–3 gemeinsam prüfen (PR #8) und anschließend `block-1` bis `block-3` taggen. Vorher:
  - AI_USAGE Fall 1 („Grill me“) durch das Team
  - Annahmen und NFA-Werte bestätigen
  - ADRs bestätigen, Swing als ADR festhalten
- [ ] Laufend: Antworten und Feedback der Ana als Issue aufnehmen und einarbeiten. Offen: Teamgröße 5, F-01 bis F-03.

## Entwicklungsbereiche zum Aussuchen

Jede Person gibt im Abstimmungs-Issue eine Rangfolge ab. Bei Gleichstand einigen wir uns, sonst wird gelost. Jeder Bereich umfasst Code, Tests und einen eigenen Abschnitt in TESTING.md.

| Bereich | Entwickelt | Testet zusätzlich |
| --- | --- | --- |
| 1 Kern & CI | CI, Ports, Fakes, Domänenregeln in `Album`/`Photo` | Domänentests, Coverage |
| 2 Foto hinzufügen | UC-01, Hash-Adapter | Grenzwertanalyse, Security-Tests |
| 3 Löschen & Entfernen | UC-02, Foto entfernen | Entscheidungstabelle, Integrationstest Löschen |
| 4 Speicherung & Beschreiben | Datei-Adapter mit Speicherformat, Foto beschreiben | Integrationstest Speicherfehler, Regression beim Change Request |
| 5 Alben & Oberfläche | Album erstellen/anzeigen/umbenennen, Foto anzeigen, Main; am Ende die Swing-Oberfläche | Usability-Test, Performance |

**Zusätzlich übernimmt jede Person eine dieser Aufgaben:**
- TESTING.md zusammenführen
- Evaluation Set
- Impact Analysis und Refactoring
- Risikoanalyse und Release-Entscheidung
- Präsentations-PDF

**Felix** ist Git-Maintainer; Product Owner und Kontakt zur Dozentin sind ein Vorschlag. Jedes Release setzt eine andere Person.
