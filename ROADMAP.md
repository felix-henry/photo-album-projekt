# Roadmap

## Meilensteine und Releases

| Block | Schwerpunkt | Wesentlicher Zwischenstand | Geplanter Tag | Erledigt |
| --- | --- | --- | --- | :---: |
| 1 | Einführung und Vorgehen | Team, Repository, [README.md](README.md), Vorgehensentscheidung für Kanban und [Kanban-Board](https://photoalbum-kanban.vercel.app) | `block-1` | ✅ 19.10.2026 |
| 2 | Requirements Engineering | [REQUIREMENTS.md](REQUIREMENTS.md) mit 6-10 FA, 3-5 NFA, mindestens 2 Use Cases und Akzeptanzkriterien; UI-Prototyp; erster Eintrag in [AI_USAGE.md](ai_usage/AI_USAGE.md); Anforderungsaufgaben im Kanban-Board | `block-2` | ☐ |
| 3 | Architektur und Entwurf | [ARCHITECTURE.md](ARCHITECTURE.md), Klassen- und Sequenzdiagramm, mindestens ein ADR und Projektgerüst; Entwurfsaufgaben im Kanban-Board | `block-3` | ☐ |
| 4 | Implementierung und Git | Pflichtfunktionalität, Build, Reviews und CI; Release `v0.1.0` | `block-4` | ☐ |
| 5 | Tests und Qualität | [TESTING.md](TESTING.md), Unit- und Integrationstests, 2 Blackbox-Verfahren, Usability-Protokoll und Evaluation Set | `block-5` | ☐ |
| 6 | Change Request und Refactoring | Impact Analysis in [CHANGE_REQUEST.md](CHANGE_REQUEST.md), Coverage-Interpretation, [REFACTORING.md](docs/REFACTORING.md) und Umsetzung des Change Requests; Release `v1.1.0` | `block-6` | ☐ |
| 7 | Risiken und Präsentation | [RISK_ANALYSIS.md](RISK_ANALYSIS.md), begründete Release-Entscheidung und Abschlusspräsentation | `final` nach Block 7 | ☐ |

Für die vollständige Pflichtfunktionalität ist außerdem Release `v1.0.0` vorgesehen. Releases werden nach Semantic Versioning getaggt und in [CHANGELOG.md](CHANGELOG.md) dokumentiert. Der verbindliche Abgabetag `final` muss laut [Projektbeschreibung](docs/task_description/Projektbeschreibung_Photo_Album.md) spätestens am **23. November** vorliegen.

## Nächste gemeinsame Schritte

- [x] Teammitglieder und Teamnamen „Commitment Issues“ festhalten.
- [ ] Weitere Aufgabenverteilung gemeinsam festlegen; Felix übernimmt Git-Maintenance.
- [x] Kanban als Vorgehen festlegen und in der [README.md](README.md) begründen.
- [x] [Kanban-Board](https://photoalbum-kanban.vercel.app) als Aufgabenboard festlegen.
- [x] Zugang zum Repository und Kanban-Board sicherstellen.
- [x] Schutz von `main` einrichten: Öffentliches Repository, Pull-Request-Pflicht und Freigaben durch die vier anderen Teammitglieder.
- [x] README und Kanban-Entscheidung vom Team bestätigen lassen.
- [x] Abschließende Änderungen per PR und Review in `main` übernehmen; anschließend den Stand als `block-1` taggen und den Tag pushen.
- [ ] Für Block 2 Stakeholder, Systemgrenze, Anforderungen und offene Fragen an die Auftraggeberin in [REQUIREMENTS.md](REQUIREMENTS.md) sammeln.
