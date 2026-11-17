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

Felix übernimmt die Rolle des Git-Maintainers.

**Dozentin und Auftraggeberin:** Ana ([dragoana25](https://github.com/dragoana25)).

## Funktionsumfang

Die Anwendung bietet:

- Alben erstellen, anzeigen, umbenennen und löschen.
- Fotos zu einem Album hinzufügen, anzeigen und entfernen.
- Fotos mit einem Titel bzw. einer Beschreibung versehen.
- Einen Papierkorb: Gelöschte Alben und entfernte Fotos lassen sich 30 Tage lang wiederherstellen oder vorher endgültig löschen.
- Ungültige Eingaben und definierte Fehlerfälle mit verständlichen fachlichen Fehlermeldungen behandeln. Bei abgewiesenen Operationen bleibt der Zustand unverändert.

Die Anforderungen, Auftraggeberentscheidungen und Teamannahmen stehen in [REQUIREMENTS.md](REQUIREMENTS.md).

## ⚙️ Technische Rahmenbedingungen

| Bereich | Festlegung und Version |
| --- | --- |
| Programmiersprache | Java 21 (JDK 21, zum Beispiel Eclipse Temurin) |
| Build | Maven 3.9.16 über den Maven-Wrapper im Repository ([ADR-000](docs/adr/ADR-000-build.md)) |
| Oberfläche | Swing aus dem JDK ([ARCHITECTURE.md](ARCHITECTURE.md)) |
| Datenhaltung | JSON-Dateien im lokalen Dateisystem mit Gson 2.15.0 ([ADR-001](docs/adr/ADR-001-speicherung.md), [ADR-003](docs/adr/ADR-003-json-bibliothek.md)); das Format beschreibt [ARCHITECTURE.md, Abschnitt 10](ARCHITECTURE.md#10-speicherformat) |
| Automatisierte Tests | JUnit 5.14.4 mit Maven Surefire 3.5.6 |
| Coverage | JaCoCo 0.8.16 |
| Versionsverwaltung | Git, gemeinsames Repository und geschützter `main`-Branch |
| CI | GitHub Actions mit Temurin JDK 21 bei jedem Push auf `main` und jedem Pull Request; die Prüfung `build` ist für `main` Pflicht |
| Abhängigkeitsupdates | GitHub Dependabot, wöchentlich für Maven und GitHub Actions |

## Vorgehen und Zusammenarbeit

Wir nutzen **Kanban**. Die Aufgabenverteilung ist im [Kanban-Board](https://photoalbum-kanban.vercel.app) festgehalten; unser Fortschritt ist dort und in den [GitHub-Issues](https://github.com/felix-henry/photo-album-projekt/issues) sichtbar. So können wir Aufgaben nach Bedarf priorisieren und auf Feedback reagieren. Die Blockabgaben bleiben unsere festen Meilensteine.

Wir stimmen uns über GitHub-Issues und [Pull Requests](https://github.com/felix-henry/photo-album-projekt/pulls) ab. Für schnelle Absprachen nutzen wir optional WhatsApp.

Für Änderungen ist folgender Ablauf vorgegeben:

**Issue → Branch → Pull Request → Review → CI → Merge**

Das Repository ist öffentlich. Änderungen an `main` erfordern einen Pull Request mit Freigaben (Approvals) durch die vier anderen Teammitglieder. Commit-Nachrichten sollen konventionell aufgebaut sein und auf eine Anforderungs- oder Issue-ID verweisen. KI-unterstützte Pull Requests erhalten das Label `ai-assisted`.

Gemeinsame Regeln für Coding Agents wie Claude Code stehen in [AGENTS.md](AGENTS.md).

## 🔒 Secrets

- Zugangsdaten, Tokens, Passwörter und private Schlüssel gehören nicht ins Repository, auch nicht in alte Commits oder Logs. Die Anwendung selbst braucht keine Zugangsdaten.
- Braucht die CI künftig ein Secret, wird es als GitHub Secret unter *Settings → Secrets and variables → Actions* hinterlegt und im Workflow über `${{ secrets.NAME }}` gelesen.
- Lokale Dateien mit persönlichen Einstellungen oder Zugangsdaten stehen in der [.gitignore](.gitignore), etwa die persönliche `CLAUDE.md`.
- Gelangt doch ein Secret ins Repository, wird es sofort ungültig gemacht und erneuert. Löschen allein reicht nicht, weil es in der Git-Historie bleibt.

## 🗺️ Projektplanung

Meilensteine, geplante Releases, Abgaben und Arbeitsschritte je Block stehen in der [Roadmap](ROADMAP.md).

## Dokumentation und Repository-Struktur

- [Projektbeschreibung als Markdown](docs/task_description/Projektbeschreibung_Photo_Album.md)
- [Original-PDF der Projektbeschreibung](docs/task_description/Projektbeschreibung_Photo_Album.pdf)
- [Block 1: Vorgehensmodelle](docs/lecture_slides/Block_1_Vorgehensmodelle.md)
- [Block 2: Requirements Engineering](docs/lecture_slides/Block_2_Requirements_Engineering.md)
- [Block 3: Entwurf und Entwurfsmuster](docs/lecture_slides/Block_3_Entwurf_und_Entwurfsmuster.md)
- [Block 4: Implementierung und Versionsmanagement](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.md)
- [Block 5: Testen – Blackbox](docs/lecture_slides/Block_5_Testen_Blackbox.md)
- [Block 6: Whitebox, Wartung und Change Request](docs/lecture_slides/Block_6_Whitebox_Wartung_Change_Request_final.md)
- [Block 7: Management und Abschlusspräsentationen](docs/lecture_slides/Block_7_Management_und_Abschlusspraesentationen_final.md)

<details>
<summary>Dateibaum anzeigen</summary>

Versionierte Dateien und Ordner; Unterrichtsfolien und Projektbeschreibung sind zusammengefasst.

<pre>
.
├── <a href=".github/">.github/</a>
│   └── ...
├── <a href=".mvn/">.mvn/</a>
│   └── ...
├── <a href=".gitignore">.gitignore</a>
├── <a href="AGENTS.md"><strong>AGENTS.md</strong></a>
├── <a href="AI_USAGE.md"><strong>AI_USAGE.md</strong></a>
├── <a href="ARCHITECTURE.md"><strong>ARCHITECTURE.md</strong></a>
├── <a href="CHANGE_REQUEST.md"><strong>CHANGE_REQUEST.md</strong></a>
├── <a href="CHANGELOG.md"><strong>CHANGELOG.md</strong></a>
├── <a href="README.md"><strong>README.md</strong></a>
├── <a href="REQUIREMENTS.md"><strong>REQUIREMENTS.md</strong></a>
├── <a href="RISK_ANALYSIS.md"><strong>RISK_ANALYSIS.md</strong></a>
├── <a href="ROADMAP.md"><strong>ROADMAP.md</strong></a>
├── <a href="TESTING.md"><strong>TESTING.md</strong></a>
├── <a href="mvnw">mvnw</a>
├── <a href="mvnw.cmd">mvnw.cmd</a>
├── <a href="pom.xml">pom.xml</a>
├── <a href="src/">src/</a>
│   └── ...
└── <a href="docs/">docs/</a>
    ├── <a href="docs/REFACTORING.md"><strong>REFACTORING.md</strong></a>
    ├── <a href="docs/adr/">adr/</a>
    │   └── ...
    ├── <a href="docs/design/">design/</a>
    │   └── ...
    ├── <a href="docs/lecture_slides/">lecture_slides/</a>
    │   └── ...
    ├── <a href="docs/presentation/">presentation/</a>
    │   └── <a href="docs/presentation/Inhalt%20folgt">Inhalt folgt</a>
    ├── <a href="docs/prototype/">prototype/</a>
    │   └── <a href="docs/prototype/index.html">index.html</a>
    ├── <a href="docs/review/">review/</a>
    │   └── <a href="docs/review/BLOCK_REVIEW_GUIDE.md">BLOCK_REVIEW_GUIDE.md</a>
    └── <a href="docs/task_description/">task_description/</a>
        └── ...
</pre>

</details>

## Build, Tests und Start

**Voraussetzung:** ein JDK 21. Maven muss nicht installiert sein, der Maven-Wrapper im Repository lädt Maven 3.9.16 beim ersten Aufruf selbst. Welches JDK der Wrapper verwendet, zeigt:

```bash
./mvnw -v          # Windows: .\mvnw.cmd -v
```

Unter „Java version“ muss 21 stehen. Sonst `JAVA_HOME` auf das JDK 21 setzen.

**Bauen und testen:**

```bash
./mvnw verify      # Windows: .\mvnw.cmd verify
```

Der Befehl kompiliert, führt alle Tests aus und baut das ausführbare JAR `target/photo-album-0.1.0-SNAPSHOT.jar`. Schlägt ein Test fehl, schlägt auch der Build fehl. Den Coverage-Report von JaCoCo öffnet `target/site/jacoco/index.html`.

**Starten:**

```bash
java -jar target/photo-album-0.1.0-SNAPSHOT.jar
```

`java` muss dabei ebenfalls Version 21 sein (`java -version`). Unter Windows geht es auch direkt über `JAVA_HOME`: `& "$env:JAVA_HOME\bin\java.exe" -jar target\photo-album-0.1.0-SNAPSHOT.jar`.

Die Anwendung legt ihre Daten im Ordner `.photo-album` im Benutzerverzeichnis ab, unter Windows also `C:\Users\<Name>\.photo-album`. Aufbau und Dateiformat beschreibt [ARCHITECTURE.md, Abschnitt 10](ARCHITECTURE.md#10-speicherformat). Wer mit leeren Daten starten will, benennt den Ordner vorher um. Gelöscht wird dabei nichts.
