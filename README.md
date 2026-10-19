# Photo Album

Anwendung zum Erstellen und Verwalten digitaler Fotoalben; primäre Zielgruppe sind Jugendliche und junge Erwachsene (PB §1). Pflichtfunktionalität: Alben erstellen, anzeigen, umbenennen und löschen; Fotos zu einem Album hinzufügen, anzeigen und entfernen; Fotos einen Titel bzw. eine Beschreibung geben (PB §5.1). Gruppenprojekt im Modul Software Engineering II.1, DHBW Heilbronn, WS 2026/27.

Quellenkürzel (gelten für alle Dokumente im Repository): **B1 Fn** = Folie n aus Block 1 (PDF-Seite, die Folien haben keine Nummer); **B2 Fn / B3 Fn** = Folie aus Block 2/3 mit der gedruckten Foliennummer in der Fußzeile; **PB §n** = Abschnitt der Projektbeschreibung Photo Album v3.

## Team

- Pit
- Felix
- Moritz
- Alanur
- Alex

Repository: https://github.com/felix-henry/photo-album-projekt

## Vorgehensentscheidung (Block 1)

> **Vorschlag, vom Team zu bestätigen.**

### Ausgangslage

| Merkmal des Projekts | Quelle |
|---|---|
| Anforderungen stehen zu Beginn bewusst nicht fest; der Auftraggeber konkretisiert sie in Sprechstunden, nicht jede Frage wird beantwortet | PB §1, §4 |
| Auftraggeber ändert seine Meinung: Feedback nach Block 2/3, Change Request in Block 6 | PB §1, §4, §11 |
| Jeder Block liefert ein prüfbares Artefakt; Abgabe als Git-Tag `block-n` am Vorabend des Folgeblocks | B1 F2, PB §3 |
| Spätere Verbesserungen fließen in die Endbewertung ein (bis „final“) | PB §3 |
| Anwendung bewusst klein; zusätzliche Features verbessern die Bewertung nicht | PB §2, §6 |
| Dokumente je Block sind formell vorgegeben (z. B. REQUIREMENTS.md, AI_USAGE.md) | PB §3, §15 |
| Teamgröße: 5 Personen – abweichend von PB §3 (3–4 Personen); Zulässigkeit mit der Dozentin zu klären | Teamliste; PB §3 |

### Einordnung nach dem Fazit plan- vs. change-driven (B1 F41)

| Aspekt | Lage im Projekt | Tendenz |
|---|---|---|
| Anforderungen | entwickeln sich während des Projekts (PB §1, §4) | change-driven |
| Risikosteuerung | Feedback nach jedem Block und Sprechstunde (B2 F42) = kurze Feedbackschleifen; Peer Review (B2 F33) = Review | beides (B1 F41: Reviews plan-driven, Feedbackschleifen change-driven) |
| Fokus | Auftraggeber bewertet Bedienbarkeit der Use Cases und Begründung der Entscheidungen (PB §4) | beides |
| Dokumentation | umfangreich und formell vorgegeben (PB §3) | plan-driven |

Ergebnis: überwiegend change-driven; der Dokumentationsumfang ist durch die Projektbeschreibung festgelegt.

### Vorschlag: iterativ-inkrementelles, Scrum-orientiertes Vorgehen mit Kanban-Board und Wegwerf-Prototyp

| Element | Festlegung | Quelle |
|---|---|---|
| Sprint | ein Block-Zyklus: Vorlesung Block n bis Git-Tag `block-n` am Vorabend von Block n+1. Feste Intervalle wie in Scrum, weil die Abgabetermine fest sind. | B1 F34–F35, PB §3 |
| Inkrement | das prüfbare Artefakt des Blocks („Stück für Stück“) | B1 F2, B1 F20 |
| Iteration | vorhandene Artefakte werden nach Feedback überarbeitet („das Gleiche, immer besser“): Review-Issue des Partnerteams, Auftraggeber-Antworten | B1 F20, B2 F33, B2 F43 |
| Product Backlog | User Stories im Backlog (Folie: „Issues.md“; umgesetzt als GitHub-Issues); jeder gelbe/rote Punkt des Projektcheckpoints wird ein Issue mit Verantwortlichem | B2 F15, B2 F43 |
| Nicht Fertiges | wandert zurück ins Product Backlog | B1 F29–F30 |
| Rollen | Product Owner verantwortet das Product Backlog; Scrum Master; Developer = alle. Besetzung legt das Team fest. | B1 F28, B1 F36 |
| Board | Workflow sichtbar machen, parallele Arbeit begrenzen (WIP); Werkzeug und WIP-Limit legt das Team fest | B1 F31 |
| Prototyp | Wegwerf-Prototyp (Klick- oder Papierprototyp) für die zwei Use Cases zur Anforderungsklärung und für frühes Feedback | B1 F21, B1 F22, B2 F44 |
| Auftraggeber-Antworten | offene Fragen gebündelt in die Sprechstunde (max. 3); Antworten als „Auftraggeber-Entscheidungen“, Unbeantwortetes als „Annahmen“ in REQUIREMENTS.md | B2 F32, B2 F42, PB §4 |

Begründung: Die Photo Management Challenge (B1 F8–F11) war so angelegt, dass der Auftraggeber manche Anforderungen nur auf Nachfrage nennt und Prototypen über Feedbackrunden angepasst werden. Dieselbe Lage beschreibt die Projektbeschreibung (PB §1, §4). Ein change-driven Vorgehen integriert Feedback kontinuierlich (B1 F7) und steuert Risiken über kurze Feedbackschleifen (B1 F41).

### Verworfene Alternativen

| Alternative | Grund für Verwerfen | Quelle |
|---|---|---|
| Wasserfall | Anforderungen sind zu Beginn nicht stabil; Feedback nach Block 2/3 und der Change Request in Block 6 träfen abgeschlossene Phasen („Late-Disaster“-Risiko, unflexible Anforderungen, kein Prototyping). Das Projekt ist zwar klein, hat aber keine fixen Anforderungen und ist kein Safety-Critical-System. | B1 F18, B1 F19, PB §1, §4 |
| Reines Kanban | kontinuierlicher Arbeitsfluss ohne feste Intervalle passt nicht zu festen Blockabgaben. Kanban-Praktiken (Board, WIP-Begrenzung) werden übernommen. | B1 F31, B1 F34–F35, PB §3 |
| Evolutionäres Prototyping der Oberfläche | ein Klick-/Papierprototyp (Figma, Papier-Foto oder HTML) lässt sich nicht zur Java-Oberfläche (JavaFX, Swing oder Konsole) ausbauen | B1 F22, B2 F44, PB §6 |
| Scrum of Scrums / SAFe | skalierte Methoden für mehrere Teams; Scrum of Scrums für „viele Menschen (>10 Personen)“ (B1 F37); das Projekt hat ein Team mit 5 Personen | B1 F37–F40 |

### Grenzen des Vorschlags

- Die Sprintlänge bestimmt der Blockplan, nicht das Team (PB §3).
- Die Dokumentation ist umfangreicher als „just enough“ (B1 F41), weil die Artefakte je Block vorgegeben sind (PB §3).

## Dokumente

| Dokument | Inhalt | Block |
|---|---|---|
| [README.md](README.md) | Zweck, Team, Vorgehensentscheidung | 1 |
| [REQUIREMENTS.md](REQUIREMENTS.md) | Anforderungen v1: Stakeholder, FA, NFA, Use Cases, Akzeptanzkriterien, Auftraggeber-Entscheidungen, Annahmen (PB §7) | 2 |
| [AI_USAGE.md](AI_USAGE.md) | dokumentierte KI-Einsatzfälle (PB §13) | 2 |
| [docs/prototyp/](docs/prototyp/) | UI-Prototyp der zwei Use Cases (B2 F44) | 2 |
| [ARCHITECTURE.md](ARCHITECTURE.md) | Schichten, Ports, Klassen- und Sequenzdiagramme, Pattern, KI-Komponente (PB §8) | 3 |
| [docs/design/](docs/design/) | CRC-Karten, Pattern-Bewertung, Klassendiagramm, Sequenzdiagramme „Foto hinzufügen“ und „Album löschen“ (PlantUML/Markdown) | 3 |
| [docs/adr/](docs/adr/) | ADR-001 bis ADR-005 (Repository, Adapter, Fehlerbehandlung, Build, KI-Komponente) | 3 |
| [prompts/](prompts/) | versionierte Prompts für die KI-Einsatzfälle (PB §13) | 2–3 |

## Build (Projektgerüst, Block 3)

Voraussetzung: Java 21. Maven-Projekt mit Paketen `domain`, `application`, `infrastructure`, `ui` (ADR-004).

```bash
./mvnw verify
```

Unter Windows (cmd/PowerShell): `mvnw.cmd verify`.

## Run

Noch keine ausführbare Anwendung; die Oberfläche entsteht in Block 4 (PB §9). Die UI-Technik ist noch Teamentscheidung (PB §6).

## Versionen

Abgaben als Git-Tags `block-1` … `block-6` und `final`; Releases nach Semantic Versioning (`v0.1.0`, `v1.0.0`, `v1.1.0`) mit CHANGELOG.md ab Block 4 (PB §9, §15). Bisher kein Release.
