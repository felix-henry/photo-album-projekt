# Regeln für Coding Agents

## Arbeitsweise

- Lies vor Änderungen das Sammel-Issue und den aktuellen Schritt in der [ROADMAP.md](ROADMAP.md). Die [Projektbeschreibung](docs/task_description/Projektbeschreibung_Photo_Album.md) ist die Hauptquelle. Ergänzend gelten die in der Roadmap aufgenommenen Folienaufträge und Teamentscheidungen.
- Bearbeite mit dem Blockbearbeiter einen Roadmap-Schritt nach dem anderen und beschränke ihn auf das vereinbarte Minimum. Gleiche fachliche Änderungen mit [REQUIREMENTS.md](REQUIREMENTS.md) und technische Entscheidungen mit [ARCHITECTURE.md](ARCHITECTURE.md) und den [ADRs](docs/adr/) ab.
- Prüfe nach jedem Schritt fachliche Richtigkeit, Vollständigkeit und Konsistenz mit dem Gesamtstand. Nenne kurz das Ergebnis, offene Punkte und den nächsten Schritt. Hake den Punkt nach erfolgreicher Prüfung ab, bevor du weitergehst.
- Halte offene Fachfragen im Sammel-Issue und konkrete Review-Befunde im PR fest. Kennzeichne Vorschläge und Annahmen. Fachliche Entscheidungen und Erweiterungen stimmt das Team ab.

## Implementierung und Prüfung

- Verwende den Maven-Wrapper aus dem Repository. Prüfe Änderungen mit `./mvnw verify` bzw. unter Windows mit `.\mvnw.cmd verify`.
- Halte die Pakete `ui`, `application`, `domain` und `infrastructure` unter `de.commitmentissues.photoalbum` gemäß der Architektur getrennt. Lege Geschäftsregeln in der Domäne ab und binde Dateizugriffe über die vorgesehenen Ports ein.
- Schreibe JUnit-5-Tests in den entsprechenden Paketen unter `src/test/java`. Ordne Tests den betroffenen Anforderungen und Akzeptanzkriterien zu. Prüfe bei Fehlerfällen auch den vorgesehenen Zustand nach der Aktion.
- Aktualisiere die betroffene Dokumentation und berichte nur Prüfungen, die tatsächlich ausgeführt wurden.

### Projektspezifische Verbote

- Triff keine unbegründeten Annahmen. Frage beim Nutzer nach, wenn dir für eine Entscheidung die Grundlage fehlt.
- Verwende für die Java-Entwicklung, den Build und die Tests keine andere JDK-Hauptversion als 21.
- Nimm ohne vorherige Rücksprache mit dem Blockbearbeiter keine Änderungen außerhalb des vereinbarten Roadmap-Schritts vor.

## Git, Review und KI-Einsatz

- Wenn du für die Bearbeitung eines neuen Blocks zuständig bist, wechsle auf `main` und aktualisiere ihn mit `git pull --ff-only origin main`. Lege von diesem Stand den Arbeitsbranch `block-x` an und pushe ihn mit `git push -u origin block-x`.
- Wenn du den Block bearbeitest, erstelle den Draft-PR, sobald der erste Commit des Block-Branches auf GitHub liegt. Verknüpfe das Sammel-Issue und wähle die passenden Labels und den Meilenstein des Blocks aus.
- Verwende für Änderungen konventionelle Commit-Nachrichten mit Anforderungs- oder Issue-ID. Änderungen an `main` laufen über einen mit dem Sammel-Issue verknüpften PR mit vier Freigaben der anderen Teammitglieder.
- In Block 2–6 übernimmt der Blockbearbeiter auch die Review-Korrekturen. Für Reviews und Re-Reviews gilt der [Review-Leitfaden](docs/review/BLOCK_REVIEW_GUIDE.md). Als Beispiele für PR-Beschreibung und Review dienen [Block 2](https://github.com/felix-henry/photo-album-projekt/pull/32) und [Block 3](https://github.com/felix-henry/photo-album-projekt/pull/33). Block 7 und das Abschlussreview bearbeitet das Team gemeinsam.
- Gleiche vor dem Abschluss-PR Kanban, Issue und Roadmap ab. Hake erledigte Punkte ab, trage das Erledigt-Datum ein und pushe den Abschlussstand vor dem Merge. Ab Block 4 müssen die CI-Pflichtprüfungen grün sein. Felix koordiniert Merge und Tagging gemäß Roadmap. Der Arbeitsbranch bleibt lokal und auf GitHub erhalten.
- Kennzeichne KI-unterstützte PRs mit `ai-assisted`. Halte in [AI_USAGE.md](AI_USAGE.md) pro substanziellem KI-Einsatz Ziel und Block, Werkzeug und Modellkennung (soweit bekannt), Prompt oder Skill, Ergebnis, kritische Bewertung und begründete Entscheidung fest.
- Versioniere die verwendeten projektbezogenen Prompts, Skills und Modellkonfigurationen. Die KI-Funktion der Anwendung bleibt beim Entwurf.

## Persönliche Anweisungen

- **Claude Code:** Verwende deine lokale `CLAUDE.md` im Hauptverzeichnis. Füge dort `@AGENTS.md` als eigene Zeile ein, damit Claude Code die gemeinsamen Regeln lädt.
- **Codex:** Persönliche Regeln kannst du in der lokalen `AGENTS.override.md` ablegen. Weise Codex darin an, auch die gemeinsame `AGENTS.md` zu lesen.

Git ignoriert beide persönlichen Dateien im Hauptverzeichnis.
