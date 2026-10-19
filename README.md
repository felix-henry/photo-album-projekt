<div align="center">

<h1>📷 Photo Album</h1>
<p><strong>Commitment Issues 💔</strong></p>
<p>Software Engineering II.1 · DHBW Heilbronn · WiSe 2026/27</p>
<p>
  <a href="https://github.com/felix-henry/photo-album-projekt">Repository</a>
  · <a href="https://photoalbum-kanban.vercel.app/">Kanban-Board</a>
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

Felix übernimmt die Rolle des Git-Maintainers. Die weitere Aufgabenverteilung ist noch offen und wird nach den erarbeiteten Anforderungen festgelegt. Wir stimmen uns nach Bedarf über WhatsApp ab, später zusätzlich über PR-Kommentare und Issues.

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

Wir nutzen **Kanban**, um Aufgaben und ihren Bearbeitungsstand auf unserem [Kanban-Board](https://photoalbum-kanban.vercel.app) sichtbar zu machen. Das passt zu unserer Abstimmung nach Bedarf und ermöglicht es, Prioritäten nach Feedback anzupassen. Die Blockabgaben bleiben unsere festen Meilensteine.

Für Änderungen ist folgender Ablauf vorgegeben:

**Issue → Branch → Pull Request → Review → CI → Merge**

Der technische Schutz von `main` ist für unser privates Repository mit GitHub Free nicht verfügbar. Den PR- und Review-Ablauf halten wir daher als Teamvereinbarung ein. Commit-Nachrichten sollen konventionell aufgebaut sein und auf eine Anforderungs- oder Issue-ID verweisen. KI-unterstützte Pull Requests erhalten das Label `ai-assisted`.

## 🗺️ Projektplanung

Meilensteine, geplante Releases, Abgabetermin und nächste Schritte stehen in der [Roadmap](ROADMAP.md).

## Dokumentation und Repository-Struktur

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
