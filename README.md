<div align="center">

<h1>📷 Photo Album</h1>
<p><strong>Commitment Issues 💔</strong></p>
<p>Software Engineering II.1 · DHBW Heilbronn · WiSe 2026/27</p>
<p>
  <a href="https://github.com/felix-henry/photo-album-projekt">Repository</a>
  · <a href="ROADMAP.md">Roadmap</a>
  · <a href="docs/task_description/Projektbeschreibung_Photo_Album.md">Projektbeschreibung</a>
</p>

</div>

## Projektziel

Wir entwickeln eine kleine Anwendung, mit der Jugendliche und junge Erwachsene Fotos organisieren und digitale Fotoalben erstellen und verwalten können. Die Daten werden lokal im Dateisystem gespeichert.

## 👥 Team

| Mitglied | GitHub |
| --- | --- |
| Moritz | [morham1](https://github.com/morham1) |
| Alex | [alexsubow1-ai](https://github.com/alexsubow1-ai) |
| Alanur | [alanurtinel](https://github.com/alanurtinel) |
| Pit | [GamerO815](https://github.com/GamerO815) |
| Felix | [felix-henry](https://github.com/felix-henry) |

Felix übernimmt die Rolle des Git-Maintainers. Die weitere Aufgabenverteilung ist noch offen. Wir stimmen uns nach Bedarf über WhatsApp ab, später zusätzlich über PR-Kommentare und Issues.

**Dozentin und Auftraggeberin:** Ana ([dragoana25](https://github.com/dragoana25)).

## Funktionsumfang

Geplant sind:

- Alben erstellen, anzeigen, umbenennen und löschen.
- Fotos zu einem Album hinzufügen, anzeigen und entfernen.
- Fotos mit einem Titel bzw. einer Beschreibung versehen.
- Ungültige Eingaben und definierte Fehlerfälle mit verständlichen fachlichen Fehlermeldungen behandeln. Bei abgewiesenen Operationen bleibt der Zustand unverändert.

Längenlimits, Fotolimit, erlaubte Dateitypen und die Erkennung doppelter Fotos sind noch mit der Ana zu klären. Die Anforderungen werden in [REQUIREMENTS.md](REQUIREMENTS.md) festgehalten.

## ⚙️ Technische Rahmenbedingungen

| Bereich | Vorgabe bzw. Entscheidungsstand |
| --- | --- |
| Programmiersprache | Java 21 |
| Build und Abhängigkeiten | Maven oder Gradle mit Wrapper; Auswahl offen |
| Oberfläche | JavaFX, Swing oder Konsole; Auswahl offen |
| Datenhaltung | Lokal im Dateisystem; Format offen und zu dokumentieren |
| Automatisierte Tests | JUnit 5 |
| Coverage | JaCoCo |
| Versionsverwaltung | Git, gemeinsames Repository und geschützter `main`-Branch |
| CI | Build und Tests bei jedem Pull Request; Anbieter und Konfiguration offen |

## Vorgehen und Zusammenarbeit

### Vorgehensentscheidung (Block 1)

> **Vorschlag, vom Team zu bestätigen.**

#### Ausgangslage

| Merkmal des Projekts | Quelle |
|---|---|
| Anforderungen stehen zu Beginn bewusst nicht fest; der Auftraggeber konkretisiert sie in Sprechstunden, nicht jede Frage wird beantwortet | PB §1, §4 |
| Auftraggeber ändert seine Meinung: Feedback nach Block 2/3, Change Request in Block 6 | PB §1, §4, §11 |
| Jeder Block liefert ein prüfbares Artefakt; Abgabe als Git-Tag `block-n` am Vorabend des Folgeblocks | B1 F2, PB §3 |
| Spätere Verbesserungen fließen in die Endbewertung ein (bis „final“) | PB §3 |
| Anwendung bewusst klein; zusätzliche Features verbessern die Bewertung nicht | PB §2, §6 |
| Dokumente je Block sind formell vorgegeben (z. B. REQUIREMENTS.md, AI_USAGE.md) | PB §3, §15 |
| Teamgröße: 5 Personen – abweichend von PB §3 (3–4 Personen); Zulässigkeit mit der Dozentin zu klären | Teamliste; PB §3 |

#### Einordnung nach dem Fazit plan- vs. change-driven (B1 F41)

| Aspekt | Lage im Projekt | Tendenz |
|---|---|---|
| Anforderungen | entwickeln sich während des Projekts (PB §1, §4) | change-driven |
| Risikosteuerung | Feedback nach jedem Block und Sprechstunde (B2 F42) = kurze Feedbackschleifen; Peer Review (B2 F33) = Review | beides (B1 F41: Reviews plan-driven, Feedbackschleifen change-driven) |
| Fokus | Auftraggeber bewertet Bedienbarkeit der Use Cases und Begründung der Entscheidungen (PB §4) | beides |
| Dokumentation | umfangreich und formell vorgegeben (PB §3) | plan-driven |

Ergebnis: überwiegend change-driven; der Dokumentationsumfang ist durch die Projektbeschreibung festgelegt.

#### Vorschlag: iterativ-inkrementelles, Scrum-orientiertes Vorgehen mit Kanban-Board und Wegwerf-Prototyp

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

#### Verworfene Alternativen

| Alternative | Grund für Verwerfen | Quelle |
|---|---|---|
| Wasserfall | Anforderungen sind zu Beginn nicht stabil; Feedback nach Block 2/3 und der Change Request in Block 6 träfen abgeschlossene Phasen („Late-Disaster“-Risiko, unflexible Anforderungen, kein Prototyping). Das Projekt ist zwar klein, hat aber keine fixen Anforderungen und ist kein Safety-Critical-System. | B1 F18, B1 F19, PB §1, §4 |
| Reines Kanban | kontinuierlicher Arbeitsfluss ohne feste Intervalle passt nicht zu festen Blockabgaben. Kanban-Praktiken (Board, WIP-Begrenzung) werden übernommen. | B1 F31, B1 F34–F35, PB §3 |
| Evolutionäres Prototyping der Oberfläche | ein Klick-/Papierprototyp (Figma, Papier-Foto oder HTML) lässt sich nicht zur Java-Oberfläche (JavaFX, Swing oder Konsole) ausbauen | B1 F22, B2 F44, PB §6 |
| Scrum of Scrums / SAFe | skalierte Methoden für mehrere Teams; Scrum of Scrums für „viele Menschen (>10 Personen)“ (B1 F37); das Projekt hat ein Team mit 5 Personen | B1 F37–F40 |

#### Grenzen des Vorschlags

- Die Sprintlänge bestimmt der Blockplan, nicht das Team (PB §3).
- Die Dokumentation ist umfangreicher als „just enough“ (B1 F41), weil die Artefakte je Block vorgegeben sind (PB §3).

### Ablauf je Änderung

Für Änderungen ist folgender Ablauf vorgegeben:

**Issue → Branch → Pull Request → Review → CI → Merge**

Der Schutz von `main` ist noch zu prüfen. Commit-Nachrichten sollen konventionell aufgebaut sein und auf eine Anforderungs- oder Issue-ID verweisen. KI-unterstützte Pull Requests erhalten das Label `ai-assisted`.

## 🗺️ Projektplanung

Meilensteine, geplante Releases, Abgabetermin und nächste Schritte stehen in der [Roadmap](ROADMAP.md).

## Dokumentation und Repository-Struktur

Quellenkürzel (gelten für alle Dokumente im Repository): **B1 Fn** = Folie n aus Block 1 (PDF-Seite, die Folien haben keine Nummer); **B2 Fn / B3 Fn** = Folie aus Block 2/3 mit der gedruckten Foliennummer in der Fußzeile; **PB §n** = Abschnitt der Projektbeschreibung Photo Album v3.

- [Projektbeschreibung als Markdown](docs/task_description/Projektbeschreibung_Photo_Album.md)
- [Original-PDF der Projektbeschreibung](docs/task_description/Projektbeschreibung_Photo_Album.pdf)
- [Block 1: Vorgehensmodelle](docs/lecture_slides/Block_1_Vorgehensmodelle.md)
- [Block 2: Requirements Engineering](docs/lecture_slides/Block_2_Requirements_Engineering.md)
- [Block 3: Entwurf und Entwurfsmuster](docs/lecture_slides/Block_3_Entwurf_und_Entwurfsmuster.md)

<details>
<summary>Dateibaum anzeigen</summary>

<pre>
.
├── <a href=".gitignore">.gitignore</a>
├── <a href="ARCHITECTURE.md"><strong>ARCHITECTURE.md</strong></a>
├── <a href="CHANGE_REQUEST.md"><strong>CHANGE_REQUEST.md</strong></a>
├── <a href="CHANGELOG.md"><strong>CHANGELOG.md</strong></a>
├── <strong>README.md</strong>
├── <a href="REQUIREMENTS.md"><strong>REQUIREMENTS.md</strong></a>
├── <a href="RISK_ANALYSIS.md"><strong>RISK_ANALYSIS.md</strong></a>
├── <a href="ROADMAP.md"><strong>ROADMAP.md</strong></a>
├── <a href="TESTING.md"><strong>TESTING.md</strong></a>
├── ai_usage/
│   └── <a href="ai_usage/AI_USAGE.md"><strong>AI_USAGE.md</strong></a>
└── docs/
    ├── <a href="docs/REFACTORING.md"><strong>REFACTORING.md</strong></a>
    ├── adr/
    │   └── <a href="docs/adr/Inhalt%20folgt">Inhalt folgt</a>
    ├── design/
    │   └── <a href="docs/design/Inhalt%20folgt">Inhalt folgt</a>
    ├── lecture_slides/
    │   ├── <a href="docs/lecture_slides/Block_1_Vorgehensmodelle.md">Block_1_Vorgehensmodelle.md</a>
    │   ├── <a href="docs/lecture_slides/Block_1_Vorgehsmodelle.pdf">Block_1_Vorgehsmodelle.pdf</a>
    │   ├── <a href="docs/lecture_slides/Block_2_Requirements_Engineering.md">Block_2_Requirements_Engineering.md</a>
    │   ├── <a href="docs/lecture_slides/Block_2_Requirements_Engineering.pdf">Block_2_Requirements_Engineering.pdf</a>
    │   ├── <a href="docs/lecture_slides/Block_3_Entwurf_und_Entwurfsmuster.md">Block_3_Entwurf_und_Entwurfsmuster.md</a>
    │   ├── <a href="docs/lecture_slides/Block_3_Entwurf_und_Entwurfsmuster.pdf">Block_3_Entwurf_und_Entwurfsmuster.pdf</a>
    │   └── assets/
    │       └── ...
    ├── presentation/
    │   └── <a href="docs/presentation/Inhalt%20folgt">Inhalt folgt</a>
    └── task_description/
        ├── <a href="docs/task_description/Projektbeschreibung_Photo_Album.md">Projektbeschreibung_Photo_Album.md</a>
        └── <a href="docs/task_description/Projektbeschreibung_Photo_Album.pdf">Projektbeschreibung_Photo_Album.pdf</a>
</pre>

</details>

## Build, Tests und Start

> [!NOTE]
> Das Projekt befindet sich in Vorbereitung. Quellcode, Build und CI sind noch nicht eingerichtet; eine Softwareversion ist noch nicht veröffentlicht.

Build-, Test- und Startbefehle ergänzen wir nach Einrichtung des Projekts.
