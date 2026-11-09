# Roadmap

## Meilensteine und Releases

| Block | Schwerpunkt | Wesentlicher Zwischenstand | Geplanter Tag | Erledigt |
| --- | --- | --- | --- | :---: |
| [1](#block-1-einführung-und-vorgehen) | Einführung und Vorgehen | Team, Repository, [README.md](README.md), Vorgehensentscheidung für Kanban und [Kanban-Board](https://photoalbum-kanban.vercel.app) | `block-1` | ✅ 19.10.2026 |
| [2](#block-2-requirements-engineering) | Requirements Engineering | [REQUIREMENTS.md](REQUIREMENTS.md) mit 6-10 FA, 3-5 NFA, mindestens 2 Use Cases und Akzeptanzkriterien; [UI-Prototyp](docs/prototype/index.html); erster Eintrag in [AI_USAGE.md](AI_USAGE.md); Anforderungsaufgaben im Kanban-Board | `block-2` | ☐ |
| [3](#block-3-architektur-und-entwurf) | Architektur und Entwurf | [ARCHITECTURE.md](ARCHITECTURE.md), Klassen- und Sequenzdiagramm, mindestens ein ADR und Projektgerüst | `block-3` | ☐ |
| [4](#block-4-implementierung-und-git) | Implementierung und Git | Pflichtfunktionalität, Build, Reviews und CI; Release `v0.1.0` | `block-4` | ☐ |
| [5](#block-5-tests-und-qualität) | Tests und Qualität | [TESTING.md](TESTING.md), Unit- und Integrationstests, 2 Blackbox-Verfahren, Usability-Protokoll und Evaluation Set | `block-5` | ☐ |
| [6](#block-6-whitebox-change-request-und-refactoring) | Change Request und Refactoring | Impact Analysis in [CHANGE_REQUEST.md](CHANGE_REQUEST.md), Coverage-Interpretation, [REFACTORING.md](docs/REFACTORING.md) und Umsetzung des Change Requests; Release `v1.1.0` | `block-6` | ☐ |
| [7](#block-7-risiken-release-entscheidung-und-präsentation) | Risiken und Präsentation | [RISK_ANALYSIS.md](RISK_ANALYSIS.md), begründete Release-Entscheidung und Abschlusspräsentation | `final` | ☐ |

Für die vollständige Pflichtfunktionalität ist außerdem Release `v1.0.0` vorgesehen. Releases werden nach Semantic Versioning getaggt und in [CHANGELOG.md](CHANGELOG.md) dokumentiert. Der verbindliche Abgabetag `final` muss laut [Projektbeschreibung](docs/task_description/Projektbeschreibung_Photo_Album.md) spätestens am **23. November** vorliegen.

### Arbeitsverteilung

| Block | Bearbeiter |
| --- | --- |
| 1 | Alle |
| 2 | Felix |
| 3 | Alanur |
| 4 | Moritz |
| 5 | Pit |
| 6 | Alex |
| 7 | Alle |

Bei Block 2–6 übernimmt der Bearbeiter die Aufgaben und Dokumentation seines Blocks sowie die Korrekturen aus den Reviews. Die vier anderen Teammitglieder reviewen die Ergebnisse gründlich. Persönliche Nachweise wie Code Reviews, Agentenregeln und tatsächliche KI-Einsätze liefert das jeweilige Teammitglied. (Projektfestlegung)

**Blockabschlüsse:** Alle fünf prüfen den zusammengeführten Stand, einschließlich des Blockbearbeiters. Die vier anderen Teammitglieder geben die erforderlichen PR-Freigaben ab. Korrekturen übernimmt der jeweilige Blockbearbeiter; bei Block 1 und 7 arbeitet das Team gemeinsam. Nach Änderungen prüfen die Reviewer die betroffenen Punkte erneut. (Projektfestlegung)

**Git-Maintenance:** Felix übernimmt weiterhin die technische Abwicklung von Branches, Merges und Tags. Nach Block 7 prüfen alle fünf den vollständigen Abgabestand im gemeinsamen Abschlussreview. (Projektfestlegung)

<details>
<summary>Hinweise zu Quellen und Bearbeitungsstand</summary>

**Dateiangaben:** Sie nennen die Ziel- oder Nachweisdateien des jeweiligen Arbeitspakets. Konkrete Quellcode-, Test-, Build- und CI-Dateien werden benannt, sobald das Projektgerüst und die Werkzeuge feststehen.

**Arbeitsgrundlage:** Die Projektbeschreibung bleibt die Hauptquelle. In Block 3–7 sind zusätzlich die gemeinsam ausgewählten Folienaufträge und Projektfestlegungen enthalten.

Die Unterteilung ordnet die Vorgaben den jeweiligen Blöcken zu. Ergänzende Folienaufträge werden nach gemeinsamer Prüfung aufgenommen.

Die Listen ab Block 2 sind Arbeitslisten. Ein vorhandener Entwurf erfüllt einen Punkt erst nach Prüfung; der gesamte Block wird oben mit Datum abgehakt, sobald sein abschließender Stand in `main` liegt und der Tag auf GitHub vorhanden ist. Die Quellenlage und widersprüchliche Angaben stehen [am Ende](#quellenstand-und-offene-auslegungen).

</details>

---

### Block 1: Einführung und Vorgehen

**Ziel:** Das Team kann im gemeinsamen Repository mit einem vereinbarten Vorgehen arbeiten. **Abschluss:** `block-1`, erledigt am 19.10.2026. Sammel-Issue: [#14](https://github.com/felix-henry/photo-album-projekt/issues/14).

<details>
<summary>Arbeitsschritte und Zuständigkeiten</summary>

#### Ergebnisse

> **Dateien:** [README.md](README.md)

- [x] **Team und Repository:** Mitglieder und GitHub-Namen in der [README](README.md) nennen; Zugänge für alle Teammitglieder und Ana sicherstellen.
- [x] **Vorgehensentscheidung:** Kanban auswählen und die Entscheidung in der [README](README.md) begründen. Das Team hat [README](README.md) und Vorgehen bestätigt.
- [x] **Zusammenarbeit:** [Kanban-Board](https://photoalbum-kanban.vercel.app) verlinken und zugänglich machen; Vorgehen und Kommunikation in der [README](README.md) festhalten.

#### Blockabschluss

> **Reviewer:** Moritz, Alex, Alanur, Pit und Felix.
> **Korrekturen und Abschlussarbeiten:** Alle.

> **Dateien:** [README.md](README.md) · [ROADMAP.md](ROADMAP.md)

**Rückblick:** Block 1 bleibt zum 19.10.2026 abgeschlossen. PR und Tag sind erfolgt; die übrigen Abschlussnachweise sind hier noch zu prüfen.

- [x] **Review:** [README.md](README.md), Teamorganisation und Vorgehen gemeinsam prüfen; Projektübersicht, Zusammenarbeit und Repository-/Board-Links abgleichen. Review-Kommentare beantworten.
- [x] **Korrekturen und Verbesserungen (bei Bedarf):** Review-Befunde einarbeiten und geänderte Punkte erneut reviewen.
- [x] **PR durchführen:** [README](README.md) und Projektgrundlage per PR nach `main` übernehmen.
- [x] **Tag auf Merge-Commit setzen:** `block-1` auf den Merge-Commit setzen und auf GitHub pushen. Auf GitHub prüfen, dass jeder Tag auf den vorgesehenen Merge-Commit zeigt.
- [x] **Kanban-Board, Issue und [ROADMAP](ROADMAP.md) prüfen:** Kanban-Board und Sammel-Issue mit dem Ergebnis abgleichen; offene Aufgaben sichtbar lassen. Bei vollständigem Abschluss [ROADMAP](ROADMAP.md) abhaken und tatsächliches Erledigt-Datum eintragen. Anschließend lokal auf `main` wechseln und aktualisieren; Arbeitsbranch optional löschen.

**Quellen:** [Projektbeschreibung §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine); [PDF S. 2](docs/lecture_slides/Block_1_Vorgehsmodelle.pdf#page=2); bestätigte Teamorganisation aus der [README](README.md#vorgehen-und-zusammenarbeit).

</details>

---

### Block 2: Requirements Engineering

**Ziel:** Anforderungen, Grenzen und prüfbare Abnahmekriterien stehen fest; offene Fachfragen sind ersichtlich. **Abschluss:** `block-2`, bis zum Vorabend von Block 3. Sammel-Issue: [#31](https://github.com/felix-henry/photo-album-projekt/issues/31).

<details open>
<summary>Arbeitsschritte und Zuständigkeiten</summary>

#### 1. Stakeholder, Systemgrenze und Use Cases

> **Dateien:** [REQUIREMENTS.md](REQUIREMENTS.md)

- [x] **Stakeholder festhalten:** Mindestens vier Stakeholder mit Interesse, Einfluss (hoch/mittel/niedrig) und je einer Anforderung aus ihrer besonderen Sicht in [REQUIREMENTS.md](REQUIREMENTS.md) beschreiben. Die Übung verlangt eine Anforderung, die nur dieser Stakeholder stellt. Überschneidungen der allgemeinen Interessen ersetzen diese Zuordnung nicht.
- [x] **Kontextdiagramm erstellen:** Systeminhalt, Menschen/Nachbarsysteme, Schnittstellen und bewusst ausgeschlossenen Umfang zeigen. Fachliche Funktionen und Oberfläche liegen im System; der Nutzer und das lokale Dateisystem außerhalb. Eingaben, Ausgaben und Dateizugriffe als Schnittstellen benennen. Nur tatsächlich vorgesehene Schnittstellen einzeichnen.
- [x] **Use-Case-Diagramm korrigieren:** Die Use Cases aus Nutzersicht und innerhalb der vereinbarten Systemgrenze darstellen; technische Einzelschritte nicht ungeprüft als eigene Nutzerziele übernehmen.
- [x] **Mindestens zwei Use Cases ausarbeiten:** „Foto hinzufügen“ und ein zweiter zentraler Use Case, bei uns „Album erstellen“. Akteur, Ziel, Vor-/Nachbedingungen sowie Standard- und Alternativabläufe beschreiben. Für „Foto hinzufügen“ nennt die Übung einen Standardablauf mit höchstens sechs Schritten sowie zwei Alternativabläufe.
- [x] **Drei FA aus „Foto hinzufügen“ ableiten:** Je eine aus einem Systemschritt, einem Alternativablauf und einer Vorbedingung; mit den Use-Case-Stellen verknüpfen. Diese drei Anforderungen gehören zu den insgesamt 6-10 FA und kommen nicht zusätzlich dazu. Diagramm, Use-Case-Beschreibung und Ableitung stehen gemeinsam in REQUIREMENTS.md.

#### 2. Anforderungen und Akzeptanzkriterien

> **Dateien:** [REQUIREMENTS.md](REQUIREMENTS.md)

- [x] **6-10 FA vervollständigen; unser Ziel sind zehn:** ID `FA-xx`, SOPHIST-Satzschablone und Verbindlichkeit (muss/sollte). Die Pflichtfunktionen abdecken: Alben erstellen/anzeigen/umbenennen/löschen, Fotos hinzufügen/anzeigen/aus Alben entfernen, Titel bzw. Beschreibung bearbeiten, ungültige Eingaben und definierte Fehlerfälle behandeln. Anforderungen einzeln prüfbar und ohne unnötige technische Vorentscheidungen formulieren. Jede FA mindestens einem Use Case zuordnen. Die vier Ausgangsregeln ausdrücklich abdecken: Albumname nicht leer und mit definierter Länge, Fotoobergrenze, maximale Textlängen und kein identisches Foto mehrfach im selben Album.
- [x] **3-5 NFA ausarbeiten; unser Ziel sind fünf:** Jeweils Qualitätsmerkmal, konkrete Messgröße und Bedingung nennen. Messgerät, Datensatz und Belastung festlegen, soweit sie für die Prüfung nötig sind. Vorschläge noch nicht als bestätigte Ziele ausgeben. Die vereinbarte gepufferte Bilddarstellung und das Ladeziel aus NFA-03 berücksichtigen.
- [x] **Alle Anforderungen mit AK (Akzeptanzkriterium) versehen:** Prüfbare Given/When/Then-Kriterien mit IDs `AK-xx.n` und Zuordnung zur Anforderung. Ausgangszustand, Aktion und beobachtbares Ergebnis beschreiben; bei Ablehnung Fehlermeldung und unveränderten Zustand prüfen. Die Folien nennen je Anforderung mindestens einen Normal- und einen Fehlerfall. Die Kriterien müssen auch zu verbindlichen NFA und Sicherheitsanforderungen passen.
- [x] **Zwei Geschäftsregeln prüfen:** Jeweils mindestens drei AK für Normal-, Fehler- und Grenzfall formulieren und offene Fachfragen kennzeichnen.
- [x] **Sprach- und Konsistenzprüfung durchführen:** Unklare Wörter, fehlende Bedingungen, vermischte Anforderungen, widersprüchliche Regeln und falsche Verweise bereinigen. Nutzen, Systemgrenze, Realisierbarkeit, Prüfbarkeit und vollständige Abdeckung der Pflichtfunktionen prüfen.

#### 3. Auftraggeber, Annahmen und Sicherheit

> **Dateien:** [REQUIREMENTS.md](REQUIREMENTS.md)

- [x] **Offene Fachfragen klären:** Namens- und Textlängen, Dateitypen, Bedeutung von „identisch“ sowie offene Fragen aus den Use Cases klären. Antworten unter „Auftraggeber-Entscheidungen“, eigene Festlegungen begründet unter „Annahmen“ dokumentieren.
- [x] **KI-Umsetzung festlegen:** Entscheidung dokumentieren (keine KI-Systeme).
- [x] **„KI und Sicherheit“ ausarbeiten:** Einen konkreten Prompt-Injection-Angriff über ein Bild bzw. dessen fremden Inhalt mit betroffenem Tool und Schaden beschreiben. Drei Anforderungen nach Satzschablone zu Datenzugriff, Tool-Nutzung und Freigabe formulieren. Festhalten, welche Datenschutzanforderung auch ohne KI-Funktion gilt. Die Begründung gegen die fünf Schutzebenen der Folien prüfen: fremde Inhalte als Daten behandeln, Rechte begrenzen, menschliche Freigabe, Ausgabeprüfung und Protokollierung.

#### 4. Prototyp und KI-Dokumentation

> **Dateien:** [docs/prototype/index.html](docs/prototype/index.html)

- [x] **Ersten UI-Prototyp abgleichen:** Klickbarer oder Papier-Prototyp für die zwei ausgearbeiteten Use Cases; im Repository ablegen und aus [REQUIREMENTS.md](REQUIREMENTS.md) verlinken. Für uns ist das der [Klickprototyp](docs/prototype/index.html). Die Abläufe müssen zu den Anforderungen passen; vorläufige Regeln kennzeichnen.

> **Dateien:** [AI_USAGE.md](AI_USAGE.md)

- [x] **KI-Fall 1 dokumentieren:** Den „Grill me“-Durchlauf kritisch in [AI_USAGE.md](AI_USAGE.md) festhalten: Aufgabe/Block, Werkzeug/Modell soweit bekannt, tatsächlicher versionierter Prompt/Skill, Ergebnis, Bewertung und Entscheidung. Mindestens einen tatsächlichen Vorschlag begründet verwerfen. Unser vorhandener Fall wird auf Vollständigkeit geprüft; Routineaktionen brauchen keine eigenen Fälle.

#### Blockabschluss

> **Reviewer:** Moritz, Alex, Alanur, Pit und Felix.
> **Korrekturen und Abschlussarbeiten:** Felix.

> **Dateien:** [REQUIREMENTS.md](REQUIREMENTS.md) · [docs/prototype/index.html](docs/prototype/index.html) · [AI_USAGE.md](AI_USAGE.md) · [README.md](README.md) · [ROADMAP.md](ROADMAP.md)
> **Nachweise:** [PR #32](https://github.com/felix-henry/photo-album-projekt/pull/32), Reviews, Git-Tag, Kanban-Board und Sammel-Issue.

- [x] **Begleitende Nachweise prüfen:** [README.md](README.md) mit Projekt und Zusammenarbeit abgleichen; [AI_USAGE.md](AI_USAGE.md), Fall 1, auf tatsächlichen Prompt, Ergebnis, kritische Bewertung und begründete Entscheidung prüfen.
- [ ] **Review:** Stakeholder und Kontext, zehn FA, fünf NFA, zwei Use Cases, AK, Entscheidungen und Annahmen, Sicherheit, Prototyp und KI-Fall gemeinsam prüfen. Bei Codeänderungen passende Tests ausführen. Review-Kommentare beantworten. Die vier anderen Teammitglieder geben vor dem Merge jeweils ein eigenes PR-Review ab. ([Block 2, S. 54–55](docs/lecture_slides/Block_2_Requirements_Engineering.pdf#page=54))
- [ ] **Korrekturen und Verbesserungen (bei Bedarf):** Review-Befunde einarbeiten und geänderte Punkte erneut reviewen.

- [ ] **PR durchführen:** Issue-Bezug der Commits und des PR sowie das Label `ai-assisted` bei KI-Unterstützung prüfen. Die vier Freigaben der anderen Teammitglieder abwarten, dann Änderungen nach `main` mergen.
- [ ] **Tag auf Merge-Commit setzen:** `block-2` auf den Merge-Commit setzen und auf GitHub pushen. Auf GitHub prüfen, dass jeder Tag auf den vorgesehenen Merge-Commit zeigt.
- [ ] **Kanban-Board, Issue und [ROADMAP](ROADMAP.md) prüfen:** Kanban-Board und Sammel-Issue mit dem Ergebnis abgleichen; offene Aufgaben sichtbar lassen. Bei vollständigem Abschluss [ROADMAP](ROADMAP.md) abhaken und tatsächliches Erledigt-Datum eintragen. Anschließend lokal auf `main` wechseln und aktualisieren; Arbeitsbranch optional löschen.

**Quellen:** [Projektbeschreibung §4-7 und §13](docs/task_description/Projektbeschreibung_Photo_Album.md); [Block-2-Folien als Markdown](docs/lecture_slides/Block_2_Requirements_Engineering.md), [Original-PDF, 56 Seiten](docs/lecture_slides/Block_2_Requirements_Engineering.pdf).

</details>

---

### Block 3: Architektur und Entwurf

**Ziel:** Architektur, Diagramme und Entscheidungen sind dokumentiert; ein Projektgerüst liegt vor. **Abschluss:** `block-3`. Sammel-Issue: [#25](https://github.com/felix-henry/photo-album-projekt/issues/25). ([PB §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine))

<details>
<summary>Arbeitsschritte und Zuständigkeiten</summary>

#### 1. Architektur und Diagramme

> **Dateien:** [ARCHITECTURE.md](ARCHITECTURE.md)

- [ ] **Grundlegende Architektur mit Schichten und Abhängigkeitsrichtung darstellen und begründen:** Die vier Schichten UI, Anwendung, Domäne und Infrastruktur mit ihren Klassen und der Abhängigkeitsrichtung in [ARCHITECTURE.md](ARCHITECTURE.md) darstellen und begründen; eine Skizze reicht. ([PB §8](docs/task_description/Projektbeschreibung_Photo_Album.md#8-architektur-und-entwurf-block-3); [Block 3, S. 49](docs/lecture_slides/Block_3_Entwurf_und_Entwurfsmuster.pdf#page=49))
- [ ] **Komponenten, Verantwortlichkeiten und Schnittstellen (Ports) festlegen:** Komponenten mit ihren Verantwortlichkeiten und Schnittstellen im Architekturentwurf beschreiben. ([PB §8](docs/task_description/Projektbeschreibung_Photo_Album.md#8-architektur-und-entwurf-block-3))

> **Dateien:** [ARCHITECTURE.md](ARCHITECTURE.md) · [docs/design/](docs/design/)

- [ ] **CRC-Karten erstellen und fotografieren:** Aus den beiden Use Cases und den Geschäftsregeln Karten für Domäne, Anwendung und Ports ableiten. Jede Geschäftsregel genau einer Karte zuordnen und „Foto hinzufügen“ damit durchspielen. Die fotografierten Karten als Rohmaterial für das Klassendiagramm in [docs/design/](docs/design/) ablegen. ([Block 3, S. 21](docs/lecture_slides/Block_3_Entwurf_und_Entwurfsmuster.pdf#page=21))
- [ ] **Klassendiagramm für Domäne, Anwendung und Ports erstellen:** Aus den CRC-Karten ein Klassendiagramm mit Multiplizitäten und nur entscheidungsrelevanten Methoden ableiten. Als PlantUML oder Bild unter [docs/design/](docs/design/) ablegen und aus [ARCHITECTURE.md](ARCHITECTURE.md) verlinken. ([PB §8](docs/task_description/Projektbeschreibung_Photo_Album.md#8-architektur-und-entwurf-block-3); [PB §15](docs/task_description/Projektbeschreibung_Photo_Album.md#15-abgabe); [Block 3, S. 49](docs/lecture_slides/Block_3_Entwurf_und_Entwurfsmuster.pdf#page=49))

> **Dateien:** [ARCHITECTURE.md](ARCHITECTURE.md) · [docs/design/](docs/design/)

- [ ] **Sequenzdiagramm „Foto hinzufügen“ inklusive Fehlerpfad erstellen:** Den Ablauf mit dem Duplikat-Fehlerpfad als `alt`-Fragment darstellen und die Konsistenz mit dem Klassendiagramm prüfen. Als PlantUML oder Bild unter [docs/design/](docs/design/) ablegen und aus [ARCHITECTURE.md](ARCHITECTURE.md) verlinken. ([PB §8](docs/task_description/Projektbeschreibung_Photo_Album.md#8-architektur-und-entwurf-block-3); [PB §15](docs/task_description/Projektbeschreibung_Photo_Album.md#15-abgabe); [Block 3, S. 49](docs/lecture_slides/Block_3_Entwurf_und_Entwurfsmuster.pdf#page=49))

#### 2. Architekturentscheidungen

> **Dateien:** [docs/adr/](docs/adr/) · [ARCHITECTURE.md](ARCHITECTURE.md)

- [ ] **Mindestens ein ADR mit verworfener Alternative dokumentieren:** Die wichtigste Architekturentscheidung unter [docs/adr/](docs/adr/) festhalten, die verworfene Alternative begründen und das ADR aus [ARCHITECTURE.md](ARCHITECTURE.md) verlinken. Beispielpfad für die Speicherentscheidung: `docs/adr/ADR-001-repository.md`. Mindestens ein ADR ist Pflicht; drei bis vier zu Speicher, Fehlerbehandlung, UI-Technik und KI-Port sind eine realistische Orientierung. ([PB §8](docs/task_description/Projektbeschreibung_Photo_Album.md#8-architektur-und-entwurf-block-3); [Block 3, S. 48–49](docs/lecture_slides/Block_3_Entwurf_und_Entwurfsmuster.pdf#page=48))
- [ ] **Entwurfsmuster nur für ein benanntes Problem einsetzen:** Jedes tatsächlich eingesetzte Pattern durch ein Problem begründen und in einem ADR dokumentieren. Eine bestimmte Anzahl von Patterns ist nicht vorgegeben. ([PB §8](docs/task_description/Projektbeschreibung_Photo_Album.md#8-architektur-und-entwurf-block-3))
- [ ] **Duplikatprüfung entwerfen:** Das Verfahren zur Erkennung bytegleicher Duplikate beim Fotoimport festlegen. ([Teamfestlegung A-01](REQUIREMENTS.md#32-annahmen-und-offene-fragen))
- [ ] **Grenze für den Bildpuffer festlegen:** Die Puffergrenze für das bedarfsgerechte Nachladen großer Alben in [ARCHITECTURE.md](ARCHITECTURE.md) festhalten. ([Teamfestlegung A-06](REQUIREMENTS.md#32-annahmen-und-offene-fragen))

#### 3. KI-Entwurf und Projektgerüst

> **Dateien:** [ARCHITECTURE.md](ARCHITECTURE.md) · [docs/design/](docs/design/)

- [ ] **KI-Komponente entwerfen und als Won’t dokumentieren:** Die KI-Funktion bleibt Won’t und wird nicht umgesetzt. `DescriptionSuggester` als Port in der Anwendung und einen Adapter in der Infrastruktur im Klassendiagramm ergänzen. Zwei Adapterprüfungen festlegen: eine vor der Anfrage und eine nach der Antwort. In [ARCHITECTURE.md](ARCHITECTURE.md) fünf Zeilen „KI-Komponente“ mit Entwurf, Guardrails und Won’t-Entscheidung festhalten. ([PB §8](docs/task_description/Projektbeschreibung_Photo_Album.md#8-architektur-und-entwurf-block-3); [Block 3, S. 47](docs/lecture_slides/Block_3_Entwurf_und_Entwurfsmuster.pdf#page=47); Projektfestlegung)

> **Dateien:** [ARCHITECTURE.md](ARCHITECTURE.md)

- [ ] **Tool-Liste für den Agentenentwurf „Fotos aufräumen“ festhalten:** In [ARCHITECTURE.md](ARCHITECTURE.md) höchstens vier `ToolSpec`s mit je einer Beschreibung notieren. `deleteAlbum`, `removePhoto`, `renameAlbum` und `shareAlbum` den Kategorien „direkt ausführen“, „nur mit Freigabe“ oder „gar nicht anbieten“ zuordnen. Das bleibt ein Entwurf. ([Block 3, S. 47](docs/lecture_slides/Block_3_Entwurf_und_Entwurfsmuster.pdf#page=47))

> **Dateien:** Quellcode und Maven-Konfiguration des Projektgerüsts; konkrete Dateien werden beim Anlegen festgelegt.

- [ ] **Projektgerüst anlegen:** Ein zum Architekturentwurf passendes Projektgerüst für Java 21 mit Maven vorbereiten. ([PB §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine); [PB §6](docs/task_description/Projektbeschreibung_Photo_Album.md#6-technische-rahmenbedingungen))

#### 4. KI-Einsatz dokumentieren

> **Dateien:** [AI_USAGE.md](AI_USAGE.md)

- [ ] **KI-Einsatz beim Entwurf kritisch dokumentieren (empfohlen):** Einen tatsächlichen substanziellen Einsatz in [AI_USAGE.md](AI_USAGE.md) nach den Angaben aus PB §13 auswerten. Ein Fall je Block 2–6 ist empfohlen, keine Mindestquote pro Block. ([PB §13](docs/task_description/Projektbeschreibung_Photo_Album.md#13-einsatz-von-ki))

#### Blockabschluss

> **Reviewer:** Moritz, Alex, Alanur, Pit und Felix.
> **Korrekturen und Abschlussarbeiten:** Alanur.

> **Dateien:** [ARCHITECTURE.md](ARCHITECTURE.md) · [docs/adr/](docs/adr/) · [docs/design/](docs/design/) · Projektgerüst.
> **Nachweise:** PR, Reviews und Git-Tag.

- [ ] **Diagramm-Review über Kreuz erhalten, vergeben und einarbeiten:** Ein Review-Issue vom Partnerteam erhalten und im Partner-Repository eines mit dem Titel `review: architecture v1` und drei Prüfpunkten anlegen. Die Reviewer prüfen Klassen- und Sequenzdiagramm auf die Zuordnung aller Geschäftsregeln (keine in der UI), passende Klassenoperationen zu jeder Sequenznachricht und begründete Abhängigkeiten. Die Rückmeldungen vor dem Blockabschluss einarbeiten. ([Block 3, S. 50](docs/lecture_slides/Block_3_Entwurf_und_Entwurfsmuster.pdf#page=50))
- [ ] **Zwischenstand über PR und Review übernehmen:** Den prüfbaren Architektur- und Projektstand im Repository über den vorgesehenen Git-Prozess zusammenführen. ([PB §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine); [PB §9](docs/task_description/Projektbeschreibung_Photo_Album.md#9-implementierung-git-und-ci-block-4))
- [ ] **Tag `block-3` setzen:** Den Zwischenstand mit `block-3` kennzeichnen; die vorgeschlagene Abgabe ist der Vorabend von Block 4. ([PB §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine))

**Quellen:** [Projektbeschreibung](docs/task_description/Projektbeschreibung_Photo_Album.md) und die an den Punkten verlinkten Block-3-Folien.

</details>

---

### Block 4: Implementierung und Git

**Ziel:** Die Pflichtfunktionalität läuft mit reproduzierbarem Build, Reviews und grüner CI; Release v0.1.0 liegt vor. **Abschluss:** `block-4`. Sammel-Issue: [#26](https://github.com/felix-henry/photo-album-projekt/issues/26). ([PB §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine))

<details>
<summary>Arbeitsschritte und Zuständigkeiten</summary>

#### 1. Pflichtfunktionalität und Datenhaltung

> **Dateien:** Quellcode und Tests der Domäne/Albumverwaltung; bei betroffenem Feedback auch [REQUIREMENTS.md](REQUIREMENTS.md) und [ARCHITECTURE.md](ARCHITECTURE.md).

- [ ] **Auftraggeber-Feedback aus Block 2 und 3 berücksichtigen:** Rückmeldungen bei den betroffenen Anforderungen und beim Entwurf einarbeiten und die Umsetzung darauf aufbauen. ([PB §4](docs/task_description/Projektbeschreibung_Photo_Album.md#4-rolle-des-auftraggebers))
- [ ] **Alben erstellen, anzeigen, umbenennen und löschen:** Die vier vorgegebenen Albumfunktionen umsetzen. ([PB §5.1](docs/task_description/Projektbeschreibung_Photo_Album.md#51-pflichtfunktionalität))
- [ ] **Geschäftsregeln und definierte Fehlerfälle umsetzen:** Regeln zu Albumnamen, Fotoanzahl, Textlängen und Duplikaten berücksichtigen. Ungültige Eingaben und definierte Fehlerfälle mit fachlicher Fehlermeldung behandeln; der Zustand bleibt dabei unverändert. ([PB §5.1](docs/task_description/Projektbeschreibung_Photo_Album.md#51-pflichtfunktionalität); [PB §5.2](docs/task_description/Projektbeschreibung_Photo_Album.md#52-geschäftsregeln-ausgangsstand---werden-im-requirements-engineering-präzisiert))

> **Dateien:** Quellcode und Tests der Foto-Use-Cases und Fototexte.

- [ ] **Fotos zu Alben hinzufügen, anzeigen und entfernen:** Die vorgegebenen Fotofunktionen umsetzen. ([PB §5.1](docs/task_description/Projektbeschreibung_Photo_Album.md#51-pflichtfunktionalität))
- [ ] **Einem Foto einen Titel bzw. eine Beschreibung geben:** Die vorgegebene Bearbeitung der Fototexte ermöglichen. ([PB §5.1](docs/task_description/Projektbeschreibung_Photo_Album.md#51-pflichtfunktionalität))

> **Dateien:** Quellcode und Tests der Dateispeicherung · [ARCHITECTURE.md](ARCHITECTURE.md) (Speicherformat).

- [ ] **Lokale Datenhaltung umsetzen und Speicherformat dokumentieren:** Daten im lokalen Dateisystem speichern und das verwendete Format dokumentieren. ([PB §6](docs/task_description/Projektbeschreibung_Photo_Album.md#6-technische-rahmenbedingungen))

> **Dateien:** Quellcode und Tests der Benutzeroberfläche.

- [ ] **Eine einfache, zweckmäßige Benutzeroberfläche bereitstellen:** JavaFX, Swing oder Konsole sind zulässig. Die Use Cases müssen bedienbar sein; die optische Gestaltung ist nicht der Bewertungsmaßstab. ([PB §4](docs/task_description/Projektbeschreibung_Photo_Album.md#4-rolle-des-auftraggebers); [PB §6](docs/task_description/Projektbeschreibung_Photo_Album.md#6-technische-rahmenbedingungen))

#### 2. Build, Projektstruktur und CI

> **Dateien:** `pom.xml` · Maven-Wrapper (`mvnw`, `mvnw.cmd`, `.mvn/wrapper/`) · [docs/adr/](docs/adr/) · `src/main/java/` und `src/test/java/` · [.gitignore](.gitignore) · `.github/workflows/ci.yml` · `.github/dependabot.yml`.

- [ ] **Maven als Build-Werkzeug festhalten und den Wrapper einrichten:** Die festgelegte Maven-Wahl in `ADR-000` unter [docs/adr/](docs/adr/) kurz begründen. Den Maven-Wrapper samt festgelegter Maven-Version im Repository bereitstellen. ([PB §6](docs/task_description/Projektbeschreibung_Photo_Album.md#6-technische-rahmenbedingungen); [Block 4, S. 7–8](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.pdf#page=7); Projektfestlegung)
- [ ] **`pom.xml` für Java 21, Tests und Coverage konfigurieren:** Feste Dependency- und Plugin-Versionen, `maven.compiler.release=21`, UTF-8 und JUnit 5 im Test-Scope festlegen. Surefire führt die Tests aus; JaCoCo erzeugt den Report in der `verify`-Phase. Bis zum ersten Release `v0.1.0` die Projektversion als `0.1.0-SNAPSHOT` führen. ([Block 4, S. 9](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.pdf#page=9))
- [ ] **Ein JAR als Build-Artefakt erzeugen:** Der Wrapper-Aufruf `./mvnw verify` führt Build und Tests aus und erzeugt ein JAR unter `target/`. Ein fehlgeschlagener Test lässt den Build fehlschlagen. ([Block 4, S. 8–9](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.pdf#page=8))
- [ ] **Dependencies begründen und unnötige Bibliotheken entfernen:** Jede verwendete Dependency in einem ADR begründen und nicht benötigte Bibliotheken entfernen. Zusätzliche Technologien brauchen eine fachliche Begründung. ([PB §6](docs/task_description/Projektbeschreibung_Photo_Album.md#6-technische-rahmenbedingungen); [Block 4, S. 10](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.pdf#page=10))
- [ ] **Repository nach Schichten strukturieren und [.gitignore](.gitignore) pflegen:** Quellcode nach Schichten ordnen und Projektdokumente auf oberster Ebene führen. Die Testpakete unter `src/test/java/` spiegeln die Produktionspakete unter `src/main/java/`. Build-Ausgaben und IDE-Dateien wie `target/`, `.idea/`, `.vscode/` und `*.iml` ausschließen. ([PB §9](docs/task_description/Projektbeschreibung_Photo_Album.md#9-implementierung-git-und-ci-block-4); [Block 4, S. 6](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.pdf#page=6))
- [ ] **Build-Check aus frischem Clone durch jedes Teammitglied durchführen:** Jeder klont das Repository in ein neues Verzeichnis und führt `./mvnw verify` aus. Fehlerursachen wie fehlender Wrapper, falsche Java-Version, IDE-Abhängigkeit oder absoluter Pfad im passenden Issue festhalten und mit einem zugehörigen Fix-Commit beheben. ([Block 4, S. 11](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.pdf#page=11))
- [ ] **CI für Push auf `main` und jeden Pull Request einrichten:** In `.github/workflows/ci.yml` Java 21 passend zur `pom.xml`, Maven-Cache und `./mvnw --batch-mode verify` konfigurieren; keine manuelle Installation voraussetzen. Den JaCoCo-Report unter `target/site/jacoco/` als CI-Artefakt hochladen. Der geschützte `main` verlangt PRs, Reviews und den erfolgreichen Statuscheck `build`. ([PB §6](docs/task_description/Projektbeschreibung_Photo_Album.md#6-technische-rahmenbedingungen); [PB §9](docs/task_description/Projektbeschreibung_Photo_Album.md#9-implementierung-git-und-ci-block-4); [Block 4, S. 21](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.pdf#page=21))
- [ ] **GitHub Dependabot aktivieren:** `.github/dependabot.yml` anlegen und damit Abhängigkeitsupdates prüfen lassen. Hinweise bewerten und Updates vor dem Merge prüfen. ([Block 4, S. 10](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.pdf#page=10); [Block 4, S. 22](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.pdf#page=22); Projektfestlegung)
- [ ] **SpotBugs-Job ergänzen (optional):** Bei Bedarf einen zusätzlichen Job für statische Analyse in `.github/workflows/ci.yml` einrichten. ([Block 4, S. 22](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.pdf#page=22))

#### 3. Git-Prozess, Agentenregeln und Dokumentation

> **Dateien:** `.github/pull_request_template.md`.
> **Nachweise:** GitHub-Issues, Branches, Commit-Nachrichten, PRs und CI.

- [ ] **Git-Workflow mit Sammel-Issues und Block-Branches anwenden:** Die vorhandenen Sammel-Issues weiterverwenden; unser Branch-Schema ist `block-x`, für diesen Block `block-4`. Änderungen über Issue → Branch → PR → Review → CI → Merge bearbeiten und den Arbeitsbranch nach erfolgreichem Merge löschen. KI-unterstützte PRs durchlaufen denselben Review-Prozess und tragen `ai-assisted`. ([PB §9](docs/task_description/Projektbeschreibung_Photo_Album.md#9-implementierung-git-und-ci-block-4); [PB §13](docs/task_description/Projektbeschreibung_Photo_Album.md#13-einsatz-von-ki); [Block 4, S. 13](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.pdf#page=13); Projektfestlegung)
- [ ] **Kleine, grün bauende Commits mit Anforderungs- oder Issue-Bezug erstellen:** Konventionelle Commit-Nachrichten verwenden; keine bloßen `fix`-/`WIP`-Nachrichten auf `main`. Jeder Commit baut grün, damit `git bisect` zuverlässig nutzbar bleibt. ([PB §9](docs/task_description/Projektbeschreibung_Photo_Album.md#9-implementierung-git-und-ci-block-4); [Block 4, S. 14](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.pdf#page=14))
- [ ] **PR-Template hinterlegen:** In `.github/pull_request_template.md` Ziel, Prüfung und offene Risiken abfragen. ([Block 4, S. 14](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.pdf#page=14); [Block 4, S. 30](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.pdf#page=30))

> **Dateien:** GitHub-PRs und dokumentierte Review-Beiträge.

- [ ] **Dokumentierte Code Reviews nachweisen:** Für jedes Teammitglied mindestens einen tatsächlichen Code-Review-Beitrag dokumentieren. Mindestens ein PR enthält ein dokumentiertes Review zu fachlicher Korrektheit, Verantwortlichkeiten und Tests (Ebenen 1–3) sowie Antworten auf die Review-Kommentare. Befunde beantworten oder einarbeiten. ([PB §9](docs/task_description/Projektbeschreibung_Photo_Album.md#9-implementierung-git-und-ci-block-4); [Block 4, S. 18](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.pdf#page=18); [Block 4, S. 30](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.pdf#page=30))
- [ ] **Review-Kommentare als begründete Fragen formulieren:** Frage statt Befehl, Bezug auf eine Anforderung oder ein Prinzip und einen Vorschlag beilegen. „Warum …?“ ist erlaubt, ein bloßes „falsch“ nicht. ([Block 4, S. 18](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.pdf#page=18))

> **Dateien:** Gemeinsame [AGENTS.md](AGENTS.md) · `agents/<Name>-AGENTS.md` · tatsächlich verwendete Prompt-, Skill- und Modellkonfigurationsdateien.

- [ ] **[AGENTS.md](AGENTS.md) sowie Prompts, Skills und Modellkonfigurationen versionieren:** Gemeinsame Agentenregeln und die tatsächlich verwendeten projektbezogenen KI-Konfigurationen im Repository führen. ([PB §9](docs/task_description/Projektbeschreibung_Photo_Album.md#9-implementierung-git-und-ci-block-4))
- [ ] **Persönliche Agentenregeln aller Teammitglieder sammeln:** Jedes Teammitglied legt seine Regeln als `agents/<Name>-AGENTS.md` ab. Die gemeinsame [AGENTS.md](AGENTS.md) bleibt zusätzlich auf oberster Ebene bestehen. (Projektfestlegung)

> **Dateien:** [README.md](README.md)

- [ ] **Build, Start, Team und Versionen in [README.md](README.md) beschreiben:** Die [README](README.md) enthält Projektzweck, Team, verwendete Versionen sowie nachvollziehbare Build- und Startanweisungen. ([PB §15](docs/task_description/Projektbeschreibung_Photo_Album.md#15-abgabe))

> **Dateien:** [README.md](README.md) (Secrets-Regel).

- [ ] **Secrets-Regel in [README.md](README.md) dokumentieren:** Keine Zugangsdaten ins Repository aufnehmen; CI-Secrets getrennt über GitHub Secrets verwalten. ([Block 4, S. 22](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.pdf#page=22))

#### 4. KI-Einsatz dokumentieren

> **Dateien:** [AI_USAGE.md](AI_USAGE.md)

- [ ] **KI-Fall 3 zum Coding-Agent-Einsatz dokumentieren:** Einen tatsächlichen substanziellen Coding-Agent-Einsatz bei der Implementierung in [AI_USAGE.md](AI_USAGE.md) mit Aufgabe/Ziel, Werkzeug/Modell samt Prompt oder Skill, Ergebnis, kritischer Bewertung und begründeter Entscheidung auswerten. ([PB §13](docs/task_description/Projektbeschreibung_Photo_Album.md#13-einsatz-von-ki); [Block 4, S. 30](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.pdf#page=30))

#### Blockabschluss

> **Reviewer:** Moritz, Alex, Alanur, Pit und Felix.
> **Korrekturen und Abschlussarbeiten:** Moritz.

> **Dateien:** [CHANGELOG.md](CHANGELOG.md) · Ergebnisdateien dieses Blocks.
> **Nachweise:** PR, Reviews, CI und Git-Tags.

- [ ] **Pflichtfunktionalität mit Build, Reviews und grüner CI als Zwischenstand nachweisen:** Den Stand über Pull Request, Review und erfolgreiche CI zusammenführen. Den vertikalen Schnitt „Album erstellen und anzeigen“ über UI → Use Case → Domäne → Repository auf `main` lauffähig nachweisen. ([PB §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine); [PB §9](docs/task_description/Projektbeschreibung_Photo_Album.md#9-implementierung-git-und-ci-block-4); [Block 4, S. 30](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.pdf#page=30))
- [ ] **Release `v0.1.0` mit [CHANGELOG.md](CHANGELOG.md) erstellen und `block-4` setzen:** Änderungen im Changelog festhalten und Release- sowie Blocktag bereitstellen. `v1.0.0` kennzeichnet zusätzlich den vollständigen ursprünglichen Pflichtumfang. ([PB §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine); [PB §9](docs/task_description/Projektbeschreibung_Photo_Album.md#9-implementierung-git-und-ci-block-4))

**Quellen:** [Projektbeschreibung](docs/task_description/Projektbeschreibung_Photo_Album.md) und die an den Punkten verlinkten Block-4-Folien.

</details>

---

### Block 5: Tests und Qualität

**Ziel:** Testableitung, automatisierte Tests und Qualitätsnachweise sind in [TESTING.md](TESTING.md) dokumentiert. **Abschluss:** `block-5`. Sammel-Issue: [#27](https://github.com/felix-henry/photo-album-projekt/issues/27). ([PB §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine))

<details>
<summary>Arbeitsschritte und Zuständigkeiten</summary>

#### 1. Teststrategie, Ableitung und automatisierte Tests

> **Dateien:** [TESTING.md](TESTING.md) · Testdateien (AK-Zuordnung).

- [ ] **Teststrategie mit Teststufen und Testarten beschreiben:** In [TESTING.md](TESTING.md) festhalten, welche Teststufen und Testarten für die Anwendung eingesetzt werden. ([Block 5, S. 7–9](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=7); [Block 5, S. 28](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=28))
- [ ] **Akzeptanzkriterien, Verfahren und Testmethoden zuordnen:** Die Kette Akzeptanzkriterium → Verfahren → Testmethode in [TESTING.md](TESTING.md) nachvollziehbar dokumentieren. ([PB §10](docs/task_description/Projektbeschreibung_Photo_Album.md#10-testing-und-qualitätssicherung-block-5-und-6))

> **Dateien:** [TESTING.md](TESTING.md) · Unit-Testdateien für Domäne und Use Cases.

- [ ] **Test Design Lab mit zwei Verfahren dokumentieren:** Eine Geschäftsregel und einen Use Case aus [REQUIREMENTS.md](REQUIREMENTS.md) wählen. Äquivalenzklassen und Grenzwerte auf die Regel anwenden; zusätzlich eine Entscheidungstabelle oder ein Zustandsmodell für den Use Case erstellen. Tabellen beziehungsweise Diagramme in [TESTING.md](TESTING.md) dokumentieren. ([PB §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine); [PB §10](docs/task_description/Projektbeschreibung_Photo_Album.md#10-testing-und-qualitätssicherung-block-5-und-6); [Block 5, S. 19](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=19))
- [ ] **Äquivalenzklassentabellen nachvollziehbar ausarbeiten:** Gültige und ungültige Klassen, Repräsentanten und erwartetes Verhalten in [TESTING.md](TESTING.md) festhalten. Offene fachliche Fragen kennzeichnen; die eigenen Projektregeln verwenden. ([Block 5, S. 12](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=12))
- [ ] **Mindestens zehn sinnvolle Unit-Tests für Domäne und Use Cases mit Fakes nachweisen:** Normal-, Fehler- und Grenzfälle abdecken und mit `@Tag` oder Kommentar kennzeichnen. Bei Fehlerfällen den unveränderten Zustand prüfen. ([PB §10](docs/task_description/Projektbeschreibung_Photo_Album.md#10-testing-und-qualitätssicherung-block-5-und-6); [Block 5, S. 7](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=7); [Block 5, S. 19](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=19))
- [ ] **Tests mit klarer Absicht nach Arrange–Act–Assert schreiben:** Testnamen beschreiben Verhalten und Bedingung; den AK-Bezug im Kommentar oder in `@DisplayName` angeben. Nur notwendige Daten vorbereiten, genau eine Aktion ausführen und Ergebnis sowie Zustand prüfen. Jeder Test hat eine klare Absicht. ([Block 5, S. 10](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=10))
- [ ] **Die Lab-Ableitung in mindestens sechs neue Tests überführen:** Jede ausgewählte Tabellenzeile, Regel oder jeden Übergang in einen passenden JUnit-Test übersetzen und im Lab-PR mindestens sechs neue Tests nachweisen. Neue Unit-Tests aus dem Lab zählen zu den insgesamt mindestens zehn Unit-Tests. ([Block 5, S. 19](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=19))

> **Dateien:** Integrationstestdateien · [TESTING.md](TESTING.md).

- [ ] **Mindestens zwei Integrationstests mit echter Infrastruktur nachweisen:** Use Case und echte Infrastruktur verbinden, beispielsweise Datei-Repository und Hasher. Je Test eine fachliche Frage zu Persistenz, Serialisierung oder Pfadverhalten prüfen; Dateitests mit `@TempDir` isolieren. ([PB §10](docs/task_description/Projektbeschreibung_Photo_Album.md#10-testing-und-qualitätssicherung-block-5-und-6); [Block 5, S. 21](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=21))

> **Dateien:** Testdateien für UI-Adapter/Use Case · [TESTING.md](TESTING.md).
> **Hinweis:** Der UI-Adapter-Test kann einer der beiden Integrationstests sein; es entsteht keine zusätzliche Testquote.

- [ ] **UI-Adapter als mögliche zweite Integration prüfen:** Für einen der beiden Integrationstests kommt ein UI-Adapter mit dem Use Case infrage; bei JavaFX beispielsweise Controller-Logik ohne echtes Fenster. ([Block 5, S. 21](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=21))

#### 2. Qualität und nichtfunktionale Anforderungen prüfen

> **Dateien:** [TESTING.md](TESTING.md) (Systemtests und Usability-Protokoll).
> **Hinweis:** Für den Usability-Test einen tatsächlichen Testnutzer einbeziehen.
> **Nachweise:** Drei konkrete Usability-Issues auf GitHub.

- [ ] **Ein bis zwei manuelle Systemtests dokumentieren:** Die gesamte Anwendung über die Benutzeroberfläche prüfen und Ablauf sowie Ergebnisse in [TESTING.md](TESTING.md) festhalten. ([Block 5, S. 7](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=7))
- [ ] **Usability-Test protokollieren und drei konkrete Issues festhalten:** Durchführung und Ergebnisse in [TESTING.md](TESTING.md) dokumentieren. Drei beobachtete Usability-Probleme als GitHub-Issues festhalten und mit dem Protokoll verknüpfen. ([PB §10](docs/task_description/Projektbeschreibung_Photo_Album.md#10-testing-und-qualitätssicherung-block-5-und-6); [Block 5, S. 28](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=28))
- [ ] **Mindestens ein Usability-Issue per PR beheben:** Mindestens eines der beobachteten Usability-Probleme beheben und die Änderung per PR mit dem zugehörigen Issue verknüpfen. ([Block 5, S. 29](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=29))

> **Dateien:** Datei-/Security-Testdateien · [TESTING.md](TESTING.md) (Security-Fälle, Messaufbau und Performance-Ergebnisse).

- [ ] **Security-Dateifälle konkret planen und testen:** Fälle wie falsche Endung, leere oder übergroße Datei in [TESTING.md](TESTING.md) aufführen und die Sicherheitsprüfung mit tatsächlichen Tests nachweisen. ([PB §10](docs/task_description/Projektbeschreibung_Photo_Album.md#10-testing-und-qualitätssicherung-block-5-und-6); [Block 5, S. 23](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=23))
- [ ] **Performance-Messung vorbereiten:** Einen gemeinsamen Datensatz mit 100 Fotos zusammenstellen und seine Formate, Auflösungen und Dateigrößen dokumentieren. Jedes Teammitglied erfasst Betriebssystem, Prozessor und RAM seines Laptops für den Messaufbau in [TESTING.md](TESTING.md). ([Teamfestlegung A-06](REQUIREMENTS.md#32-annahmen-und-offene-fragen))
- [ ] **Performance-Messung für eine NFA durchführen:** Die Performance unter den Bedingungen der betreffenden NFA messen und das Ergebnis dokumentieren. ([PB §10](docs/task_description/Projektbeschreibung_Photo_Album.md#10-testing-und-qualitätssicherung-block-5-und-6))

> **Dateien:** Build-Konfiguration (JaCoCo) · [TESTING.md](TESTING.md).
> **Nachweise:** Generierter JaCoCo-Report.

- [ ] **Coverage mit JaCoCo erheben:** Einen Coverage-Report als Grundlage für die Interpretation je Schicht erzeugen. ([PB §10](docs/task_description/Projektbeschreibung_Photo_Album.md#10-testing-und-qualitätssicherung-block-5-und-6))
- [ ] **Drei Klassen mit der niedrigsten Branch-Coverage notieren:** Im JaCoCo-Report die drei Klassen mit der niedrigsten Branch-Coverage ermitteln und in [TESTING.md](TESTING.md) festhalten. ([Block 5, S. 29](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=29))

#### 3. KI-Evaluation und KI-Einsatz

> **Dateien:** `eval/describe-photo/cases.md` · [TESTING.md](TESTING.md) (Verweis auf das Evaluation Set).

- [ ] **Mini-Evaluation-Set erstellen (Pflicht, auch bei Won’t):** Fünf Fälle mit konkreter Eingabe festlegen: normal, mehrdeutig, sensibel, adversarial und fehlerhaft. Je Fall zwei bis drei prüfbare Kriterien formulieren, möglichst regelbasiert. Das Set bleibt auch für die nur entworfene KI-Funktion verpflichtend. ([PB §10](docs/task_description/Projektbeschreibung_Photo_Album.md#10-testing-und-qualitätssicherung-block-5-und-6); [Block 5, S. 27](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=27))
- [ ] **Freigabe-Pass-Rate begründen und das Evaluation Set versionieren:** Festlegen, ab welcher Pass-Rate die entworfene Funktion freigegeben würde, und die Zahl begründen. Das Set unter `eval/describe-photo/cases.md` ablegen und aus [TESTING.md](TESTING.md) verlinken. ([Block 5, S. 27](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=27))

> **Dateien:** [AI_USAGE.md](AI_USAGE.md)

- [ ] **KI-Fall 4 zur Testgenerierung dokumentieren:** Aus einem Akzeptanzkriterium Tests von einer KI generieren lassen und kritisch prüfen, ob die Assertions tatsächlich das Kriterium prüfen. Den Einsatz mit Prompt, Ergebnis, kritischer Bewertung und begründeter Entscheidung in [AI_USAGE.md](AI_USAGE.md) dokumentieren. ([PB §13](docs/task_description/Projektbeschreibung_Photo_Album.md#13-einsatz-von-ki); [Block 5, S. 29](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=29))

#### Blockabschluss

> **Reviewer:** Moritz, Alex, Alanur, Pit und Felix.
> **Korrekturen und Abschlussarbeiten:** Pit.

> **Dateien:** [TESTING.md](TESTING.md) · Testdateien · Build-Konfiguration · `eval/describe-photo/cases.md`.
> **Nachweise:** Testergebnisse, Coverage-Report, PR, Reviews, CI und Git-Tag.

- [ ] **[TESTING.md](TESTING.md) mit den geforderten Test- und Qualitätsnachweisen abgeben:** Teststrategie, Blackbox-Verfahren, Unit-/Integrations- und manuelle Systemtests, Zuordnungen, Usability-, Security- und Performance-Nachweise sowie den Verweis auf das verpflichtende Evaluation Set bereitstellen. ([PB §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine); [PB §10](docs/task_description/Projektbeschreibung_Photo_Album.md#10-testing-und-qualitätssicherung-block-5-und-6); [Block 5, S. 28](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=28))
- [ ] **Review über Kreuz für den Test-PR durchführen:** Den Lab-/Test-PR über Kreuz reviewen lassen, Rückmeldungen beantworten und erforderliche Korrekturen einarbeiten. ([Block 5, S. 19](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=19))
- [ ] **Teststand über PR, Review und grüne CI übernehmen und `block-5` setzen:** Den prüfbaren Zwischenstand im Repository kennzeichnen. ([PB §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine); [PB §9](docs/task_description/Projektbeschreibung_Photo_Album.md#9-implementierung-git-und-ci-block-4))

**Quellen:** [Projektbeschreibung](docs/task_description/Projektbeschreibung_Photo_Album.md) und die an den Punkten verlinkten Block-5-Folien.

</details>

---

### Block 6: Whitebox, Change Request und Refactoring

**Ziel:** Coverage ist interpretiert; der Change Request ist analysiert und umgesetzt; Refactoring und Release v1.1.0 sind dokumentiert. **Abschluss:** `block-6`. Sammel-Issue: [#28](https://github.com/felix-henry/photo-album-projekt/issues/28). ([PB §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine))

<details>
<summary>Arbeitsschritte und Zuständigkeiten</summary>

#### 1. Coverage und Regressionstests

> **Dateien:** [TESTING.md](TESTING.md) · JaCoCo-/Build-Konfiguration.
> **Nachweise:** Generierter JaCoCo-Report unter `target/site/jacoco/index.html`.

- [ ] **Coverage je Schicht erheben und interpretieren:** Mit `./mvnw verify` den JaCoCo-Report erzeugen und `target/site/jacoco/index.html` öffnen. Statement- und Branch-Coverage je Schicht in [TESTING.md](TESTING.md) notieren und ihre Aussagekraft erklären. Eine hohe Coverage allein ist kein Qualitätsnachweis. ([PB §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine); [PB §10](docs/task_description/Projektbeschreibung_Photo_Album.md#10-testing-und-qualitätssicherung-block-5-und-6); [Block 6, S. 13](docs/lecture_slides/Block_6_Whitebox_Wartung_Change_Request_final.pdf#page=13))

> **Dateien:** Domänentestdateien · [TESTING.md](TESTING.md).

- [ ] **Gelbe Domänenzeilen erklären und fehlende Zweige absichern:** Die nur teilweise abgedeckten Zweige der Domäne erklären und mindestens zwei Tests für bislang nicht abgedeckte Zweige ergänzen. Die Ergebnisse in [TESTING.md](TESTING.md) festhalten. ([Block 6, S. 13](docs/lecture_slides/Block_6_Whitebox_Wartung_Change_Request_final.pdf#page=13))

> **Dateien:** Regressionstestdateien · [TESTING.md](TESTING.md).

- [ ] **Change Request und Refactoring durch Regressionstests absichern:** Vorhandenes und fachlich geändertes Verhalten mit passenden Tests absichern. ([PB §10](docs/task_description/Projektbeschreibung_Photo_Album.md#10-testing-und-qualitätssicherung-block-5-und-6); [PB §12](docs/task_description/Projektbeschreibung_Photo_Album.md#12-wartung-und-refactoring-block-6))

#### 2. Change Request analysieren und umsetzen

> **Dateien:** [CHANGE_REQUEST.md](CHANGE_REQUEST.md)

- [ ] **Den tatsächlichen Change Request mit eindeutigen IDs dokumentieren:** Den CR und seine Priorität in [CHANGE_REQUEST.md](CHANGE_REQUEST.md) festhalten und die einzelnen Änderungsanforderungen nummerieren, beispielsweise `CR-1.1`, `CR-1.2` usw. ([PB §11](docs/task_description/Projektbeschreibung_Photo_Album.md#11-change-request-block-6); [Block 6, S. 11](docs/lecture_slides/Block_6_Whitebox_Wartung_Change_Request_final.pdf#page=11))
- [ ] **Die Mini-Impact-Analysis mit drei konkreten Antworten beginnen:** In [CHANGE_REQUEST.md](CHANGE_REQUEST.md) festhalten: Welche Anforderung widerspricht dem tatsächlichen CR (ID aus [REQUIREMENTS.md](REQUIREMENTS.md))? Welche Klasse ändert sich am stärksten? Welcher vorhandene Test wird nach der Änderung rot (Testname)? ([Block 6, S. 12](docs/lecture_slides/Block_6_Whitebox_Wartung_Change_Request_final.pdf#page=12))
- [ ] **Betroffene und neue Anforderungen sowie Akzeptanzkriterien analysieren:** Vor CR-Code die Impact Analysis in [CHANGE_REQUEST.md](CHANGE_REQUEST.md) erstellen; betroffene Anforderungen und AK mit IDs sowie neue Anforderungen nennen. ([PB §11](docs/task_description/Projektbeschreibung_Photo_Album.md#11-change-request-block-6))
- [ ] **Auswirkungen auf Architektur, Datenmodell und ADRs beschreiben:** Betroffene Teile, ungültig gewordene ADRs und ein neues ADR in der Impact Analysis festhalten. ([PB §11](docs/task_description/Projektbeschreibung_Photo_Album.md#11-change-request-block-6))
- [ ] **Rot werdende und neue Tests benennen:** Regressionen und neu erforderliche Tests vor der Umsetzung in der Impact Analysis beschreiben. ([PB §11](docs/task_description/Projektbeschreibung_Photo_Album.md#11-change-request-block-6))
- [ ] **Risiken, Aufwand je Teil und Umsetzungsreihenfolge festhalten:** Änderungsrisiken, Aufwandsschätzung je Teil und Reihenfolge der Umsetzung in der Impact Analysis dokumentieren. ([PB §11](docs/task_description/Projektbeschreibung_Photo_Album.md#11-change-request-block-6))

> **Dateien:** Betroffene Quellcode- und Testdateien · [REQUIREMENTS.md](REQUIREMENTS.md) · [ARCHITECTURE.md](ARCHITECTURE.md) · [docs/adr/](docs/adr/) · [TESTING.md](TESTING.md).

- [ ] **Change Request nach der Impact Analysis bis Block 7 umsetzen:** Zunächst die Analyse fertigstellen, danach den tatsächlichen CR auf dieser Grundlage umsetzen; betroffene Anforderungen, Architektur, Datenmodell und Tests berücksichtigen. Umsetzung und Release `v1.1.0` bis Block 7 abschließen. Eine unvollständige Umsetzung ehrlich dokumentieren. ([PB §11](docs/task_description/Projektbeschreibung_Photo_Album.md#11-change-request-block-6); [Block 6, S. 11](docs/lecture_slides/Block_6_Whitebox_Wartung_Change_Request_final.pdf#page=11))

> **Dateien:** [CHANGE_REQUEST.md](CHANGE_REQUEST.md)

- [ ] **Rückblick nach der Umsetzung dokumentieren:** Geschätzten und tatsächlichen Aufwand sowie Überraschungen in [CHANGE_REQUEST.md](CHANGE_REQUEST.md) gegenüberstellen. ([PB §11](docs/task_description/Projektbeschreibung_Photo_Album.md#11-change-request-block-6))

#### 3. Refactoring

> **Dateien:** Betroffene Quellcode- und Testdateien · [docs/REFACTORING.md](docs/REFACTORING.md).

- [ ] **Refactoring anhand von Klasse, Metrik und Smell begründen:** Eine intern verbesserungsbedürftige Stelle mit Nachweis auswählen; vorzugsweise eine, deren Verbesserung den CR erleichtert. ([PB §12](docs/task_description/Projektbeschreibung_Photo_Album.md#12-wartung-und-refactoring-block-6))
- [ ] **Verhaltensneutral nach den fünf Refactoring-Schritten vorgehen:** Verhalten durch grüne Tests absichern → kleine Strukturänderung → Tests ausführen → Diff auf Verhaltensneutralität prüfen → eigener Refactoring-Commit. Jeden Änderungsschritt mit grünen Tests absichern; fachliche Änderungen bleiben in getrennten Commits. ([PB §12](docs/task_description/Projektbeschreibung_Photo_Album.md#12-wartung-und-refactoring-block-6); [Block 6, S. 10](docs/lecture_slides/Block_6_Whitebox_Wartung_Change_Request_final.pdf#page=10))
- [ ] **Refactoring in [docs/REFACTORING.md](docs/REFACTORING.md) dokumentieren:** Ausgangssituation, Problem, Änderungsschritte, Ergebnis und Testabsicherung festhalten. ([PB §12](docs/task_description/Projektbeschreibung_Photo_Album.md#12-wartung-und-refactoring-block-6))

#### 4. KI-Einsatz dokumentieren

> **Dateien:** [AI_USAGE.md](AI_USAGE.md)

- [ ] **KI-Einsatz bei der Weiterentwicklung kritisch dokumentieren (empfohlen):** Einen tatsächlichen substanziellen Einsatz nach PB §13 in [AI_USAGE.md](AI_USAGE.md) auswerten. Ein Fall aus Block 6 gehört zur Empfehlung von fünf Fällen insgesamt. ([PB §13](docs/task_description/Projektbeschreibung_Photo_Album.md#13-einsatz-von-ki))

#### Blockabschluss

> **Reviewer:** Moritz, Alex, Alanur, Pit und Felix.
> **Korrekturen und Abschlussarbeiten:** Alex.

> **Dateien:** [CHANGE_REQUEST.md](CHANGE_REQUEST.md) · [docs/REFACTORING.md](docs/REFACTORING.md) · [TESTING.md](TESTING.md) · [CHANGELOG.md](CHANGELOG.md) · geänderte Quellcode- und Testdateien.
> **Nachweise:** PR, Reviews, CI und Git-Tags.

- [ ] **Änderungen über PR, Review und grüne CI zusammenführen:** Tests, Analyse, Refactoring-Dokumentation und umgesetzten Stand im Repository zusammenführen. ([PB §9](docs/task_description/Projektbeschreibung_Photo_Album.md#9-implementierung-git-und-ci-block-4); [PB §10](docs/task_description/Projektbeschreibung_Photo_Album.md#10-testing-und-qualitätssicherung-block-5-und-6); [PB §11](docs/task_description/Projektbeschreibung_Photo_Album.md#11-change-request-block-6); [PB §12](docs/task_description/Projektbeschreibung_Photo_Album.md#12-wartung-und-refactoring-block-6))
- [ ] **Release `v1.1.0` mit [CHANGELOG.md](CHANGELOG.md) erstellen und `block-6` setzen:** Den CR-Release bis Block 7 bereitstellen und den prüfbaren Zwischenstand mit dem Blocktag kennzeichnen. ([PB §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine); [PB §9](docs/task_description/Projektbeschreibung_Photo_Album.md#9-implementierung-git-und-ci-block-4); [Block 6, S. 11](docs/lecture_slides/Block_6_Whitebox_Wartung_Change_Request_final.pdf#page=11))

**Quellen:** [Projektbeschreibung](docs/task_description/Projektbeschreibung_Photo_Album.md) und die an den Punkten verlinkten Block-6-Folien.

</details>

---

### Block 7: Risiken, Release-Entscheidung und Präsentation

**Ziel:** Risikoanalyse, begründete Release-Entscheidung, Präsentation und vollständiger Abgabestand liegen für das Abschlussreview vor. **Abschluss:** `final` nach dem Abschlussreview. Sammel-Issue: [#29](https://github.com/felix-henry/photo-album-projekt/issues/29). ([PB §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine); Projektfestlegung)

<details>
<summary>Arbeitsschritte und Zuständigkeiten</summary>

#### 1. Risikoanalyse und Release-Entscheidung

> **Dateien:** [RISK_ANALYSIS.md](RISK_ANALYSIS.md) (Risiken, Matrix und Maßnahmen).

- [ ] **Mindestens drei Risiken bewerten:** In [RISK_ANALYSIS.md](RISK_ANALYSIS.md) mindestens ein Produkt- und ein Projektrisiko berücksichtigen. Je Risiko Eintrittswahrscheinlichkeit und Auswirkung kurz begründen, den Risikowert als Wahrscheinlichkeit × Auswirkung bestimmen und in einer Risikomatrix einordnen. ([PB §14](docs/task_description/Projektbeschreibung_Photo_Album.md#14-release--und-risikoanalyse-block-7); [Block 7, S. 15](docs/lecture_slides/Block_7_Management_und_Abschlusspraesentationen_final.pdf#page=15))
- [ ] **Risikostrategien und Maßnahmen mit Verantwortlichen festlegen:** Je Risiko eine Strategie, eine konkrete Maßnahme und die zuständige Person nennen. ([PB §14](docs/task_description/Projektbeschreibung_Photo_Album.md#14-release--und-risikoanalyse-block-7); [Block 7, S. 15](docs/lecture_slides/Block_7_Management_und_Abschlusspraesentationen_final.pdf#page=15))

> **Dateien:** [RISK_ANALYSIS.md](RISK_ANALYSIS.md) (Quality Gates und Beleglinks).

- [ ] **Vier bis sechs Quality Gates mit Beleglinks bewerten:** Je Gate einen konkreten Link zum Nachweis und den Status erfüllt/nicht erfüllt festhalten. ([PB §14](docs/task_description/Projektbeschreibung_Photo_Album.md#14-release--und-risikoanalyse-block-7); [Block 7, S. 15](docs/lecture_slides/Block_7_Management_und_Abschlusspraesentationen_final.pdf#page=15))

> **Dateien:** [RISK_ANALYSIS.md](RISK_ANALYSIS.md) (Rollout, Migration/Rollback und KI-Governance).

- [ ] **Rollout-Plan und KI-Governance beschreiben:** Strategie, Migration und Rollback sowie einen Abschnitt KI-Governance dokumentieren. ([PB §14](docs/task_description/Projektbeschreibung_Photo_Album.md#14-release--und-risikoanalyse-block-7))

> **Dateien:** [RISK_ANALYSIS.md](RISK_ANALYSIS.md) (Release-Entscheidung).

- [ ] **Release-Entscheidung mit Matrix und Gates belegen:** Ja, nein oder unter Bedingungen entscheiden und die verwendete Risikomatrix sowie die Quality Gates mit ihren Beleglinks in der Begründung verknüpfen. Eine begründete Ablehnung wird nicht schlechter bewertet als eine Zustimmung. ([PB §14](docs/task_description/Projektbeschreibung_Photo_Album.md#14-release--und-risikoanalyse-block-7); [Block 7, S. 15](docs/lecture_slides/Block_7_Management_und_Abschlusspraesentationen_final.pdf#page=15))

#### 2. Abschlusspräsentation

> **Dateien:** [docs/presentation/](docs/presentation/) (Präsentation als PDF).

- [ ] **Präsentationsformat einhalten:** Zehn Minuten Präsentation mit hartem Stopp und fünf Minuten Diskussion; maximal acht Folien und eine gezielte Demo von höchstens 90 Sekunden. Alle Teammitglieder sprechen substanziell. ([PB §16.2](docs/task_description/Projektbeschreibung_Photo_Album.md#162-präsentation--diskussion---100-punkte))
- [ ] **Die sechs Leitfragen beantworten und eine Entscheidung mit einem Diagramm erklären:** Anforderungsentwicklung, wichtige Architekturentscheidung und ihr Grund, hergeleiteten Testfall, CR-Auswirkungen, KI-Einsatz samt verworfenem Vorschlag sowie die heutige Release-Entscheidung behandeln. ([PB §16.2](docs/task_description/Projektbeschreibung_Photo_Album.md#162-präsentation--diskussion---100-punkte))
- [ ] **Das Gesamtprojekt erklären können:** Jede Person kann Fragen zu Anforderungen, Architektur und Qualitätsmaßnahmen des Gesamtprojekts beantworten. ([PB §16.2](docs/task_description/Projektbeschreibung_Photo_Album.md#162-präsentation--diskussion---100-punkte))
- [ ] **Präsentation halten und PDF versionieren:** In Block 7 präsentieren und die PDF unter [docs/presentation/](docs/presentation/) ablegen. ([PB §15](docs/task_description/Projektbeschreibung_Photo_Album.md#15-abgabe); [PB §16.2](docs/task_description/Projektbeschreibung_Photo_Album.md#162-präsentation--diskussion---100-punkte))

#### 3. Repository für die finale Abgabe vorbereiten

> **Dateien:** [README.md](README.md)

- [ ] **[README.md](README.md) vervollständigen:** Zweck, Build, Run, Team und Versionen dokumentieren. ([PB §15](docs/task_description/Projektbeschreibung_Photo_Album.md#15-abgabe))

> **Dateien:** [REQUIREMENTS.md](REQUIREMENTS.md) · [ARCHITECTURE.md](ARCHITECTURE.md) · [docs/adr/](docs/adr/) · [docs/design/](docs/design/) · [TESTING.md](TESTING.md) · [CHANGE_REQUEST.md](CHANGE_REQUEST.md) · [docs/REFACTORING.md](docs/REFACTORING.md) · [AI_USAGE.md](AI_USAGE.md) · [RISK_ANALYSIS.md](RISK_ANALYSIS.md)

- [ ] **Alle geforderten Fachdokumente bereitstellen:** [REQUIREMENTS.md](REQUIREMENTS.md), [ARCHITECTURE.md](ARCHITECTURE.md) mit [docs/adr/](docs/adr/) und [docs/design/](docs/design/), [TESTING.md](TESTING.md), [CHANGE_REQUEST.md](CHANGE_REQUEST.md), [docs/REFACTORING.md](docs/REFACTORING.md), [AI_USAGE.md](AI_USAGE.md) und [RISK_ANALYSIS.md](RISK_ANALYSIS.md) im Repository bereitstellen. ([PB §15](docs/task_description/Projektbeschreibung_Photo_Album.md#15-abgabe))

> **Dateien:** Quellcode-/Testdateien · Build-Konfiguration und Wrapper · CI-Konfiguration · gemeinsame [AGENTS.md](AGENTS.md) · [CHANGELOG.md](CHANGELOG.md) · verwendete KI-Konfigurationen.

- [ ] **Quellcode, Tests und Konfigurationen versionieren:** Quellcode und Tests, Build-Konfiguration mit Wrapper, CI-Konfiguration, [AGENTS.md](AGENTS.md) und [CHANGELOG.md](CHANGELOG.md) sowie verwendete Prompts, Skills und Modellkonfigurationen im Repository bereitstellen. ([PB §9](docs/task_description/Projektbeschreibung_Photo_Album.md#9-implementierung-git-und-ci-block-4); [PB §15](docs/task_description/Projektbeschreibung_Photo_Album.md#15-abgabe))

> **Dateien:** [AI_USAGE.md](AI_USAGE.md) · gegebenenfalls separat verwendete Prompt-/Skill-Dateien.

- [ ] **[AI_USAGE.md](AI_USAGE.md) auf Umfang und Pflichtangaben prüfen:** Mindestens drei substanzielle Fälle, empfohlen fünf mit je einem aus Block 2–6. Je Fall Aufgabe/Ziel und Block, Werkzeug/Modell soweit bekannt, versionierten Prompt oder Skill, Ergebnis, kritische Bewertung und begründete Entscheidung dokumentieren. Mindestens einen tatsächlichen Vorschlag begründet verwerfen. ([PB §13](docs/task_description/Projektbeschreibung_Photo_Album.md#13-einsatz-von-ki))

> **Dateien:** Git-Historie und dokumentierte GitHub-PR-Reviews.

- [ ] **Teambeiträge und Code Reviews nachweisen:** Substanzielle Beiträge aller Teammitglieder müssen in der Git-Historie nachvollziehbar sein; mindestens ein dokumentiertes Code Review je Person liegt vor. ([PB §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine); [PB §9](docs/task_description/Projektbeschreibung_Photo_Album.md#9-implementierung-git-und-ci-block-4))

#### Blockabschluss

> **Reviewer:** Moritz, Alex, Alanur, Pit und Felix.
> **Korrekturen und Abschlussarbeiten:** Alle.

Der abschließende PR-Merge und der Tag `final` folgen nach dem [Abschlussreview](#abschlussreview-nach-block-7).

**Quellen:** [Projektbeschreibung](docs/task_description/Projektbeschreibung_Photo_Album.md) und die an den Punkten verlinkten Block-7-Folien.

</details>

---

### Abschlussreview nach Block 7

**Ziel:** Das Team hat den vollständigen Abgabestand auf Vollständigkeit, Konsistenz und Qualität geprüft. Das Review erfolgt vor dem finalen PR-Merge und dem Tag `final`. (Projektfestlegung)

<details>
<summary>Prüfung und Freigabe der finalen Abgabe</summary>

> **Reviewer:** Moritz, Alex, Alanur, Pit und Felix – jeder prüft das Gesamtprojekt.
> **Koordination:** Felix.
> **Korrekturen:** Jeweiliger Blockbearbeiter; bei Block 1 und 7 gemeinsam.
> **Dateien:** Vollständiger Repository-Stand einschließlich aller Dokumente und Nachweise aus den Blöcken 1–7.
> **Nachweise:** Fünf dokumentierte Abschlussprüfungen mit Datum und Ergebnis; finaler PR mit vier Freigaben der anderen Teammitglieder, CI und die geforderten Git-Tags.

#### Gemeinsamer Prüfumfang

- [ ] **Abgabe vollständig gegen Projektbeschreibung und ROADMAP prüfen:** Den tatsächlichen Repository-Stand Punkt für Punkt mit den geforderten Funktionen, Dokumentinhalten, Mindestmengen, Nachweisen und Versionen abgleichen. Offene ROADMAP-Punkte mit dem nachgewiesenen Stand vergleichen. ([PB §15](docs/task_description/Projektbeschreibung_Photo_Album.md#15-abgabe); [PB §18](docs/task_description/Projektbeschreibung_Photo_Album.md#18-checkliste-abgabe); Projektfestlegung)
- [ ] **Konsistenz und Qualität des Gesamtprojekts prüfen:** Anforderungen, Architektur, Quellcode, Tests und Change Request aufeinander abstimmen. Kohäsion, Codeverständlichkeit, Wartbarkeit, Modularisierung und Codekonventionen ausdrücklich prüfen. Bekannte Schwächen und Grenzen der Lösung in [RISK_ANALYSIS.md](RISK_ANALYSIS.md) benennen. ([PB §16.1](docs/task_description/Projektbeschreibung_Photo_Album.md#161-projektartefakte--software---100-punkte); [PB §17](docs/task_description/Projektbeschreibung_Photo_Album.md#17-was-eine-sehr-gute-leistung-auszeichnet))
- [ ] **Befunde bearbeiten und Änderungen erneut reviewen:** Fehlende oder fehlerhafte Punkte im jeweiligen Sammel-Issue festhalten, Korrekturen zuordnen und geänderte Punkte erneut prüfen. Verbleibende Lücken und Schwächen transparent dokumentieren. Jeder Reviewer dokumentiert seine Prüfung des abschließenden Stands. (Projektfestlegung)

#### Persönliche Abschlussreviews

Jeder prüft den vollständigen Abgabestand anhand der obigen Punkte, dokumentiert Datum und Ergebnis am finalen PR und prüft spätere Korrekturen an seinen Befunden erneut. Die Haken bestätigen die Prüfung des abschließenden Stands. Felix dokumentiert seine eigene Prüfung ebenfalls; die vier erforderlichen PR-Freigaben kommen von den anderen Teammitgliedern. (Projektfestlegung)

- [ ] **Abschlussreview Moritz:** Prüfung mit Datum und Ergebnis dokumentiert. (Projektfestlegung)
- [ ] **Abschlussreview Alex:** Prüfung mit Datum und Ergebnis dokumentiert. (Projektfestlegung)
- [ ] **Abschlussreview Alanur:** Prüfung mit Datum und Ergebnis dokumentiert. (Projektfestlegung)
- [ ] **Abschlussreview Pit:** Prüfung mit Datum und Ergebnis dokumentiert. (Projektfestlegung)
- [ ] **Abschlussreview Felix:** Prüfung mit Datum und Ergebnis dokumentiert. (Projektfestlegung)

#### Abgabe nach den fünf Abschlussreviews

> **Git-Maintenance:** Felix.

- [ ] **Finalen Stand über PR, Review und grüne CI zusammenführen:** Nach dem Abschlussreview, vier Freigaben der anderen Teammitglieder und grüner CI die finale Repository-Abgabe nach `main` übernehmen. ([PB §9](docs/task_description/Projektbeschreibung_Photo_Album.md#9-implementierung-git-und-ci-block-4); [PB §15](docs/task_description/Projektbeschreibung_Photo_Album.md#15-abgabe); Projektfestlegung)
- [ ] **Erforderliche Tags und verbindliche Abgabe bereitstellen:** `block-1` bis `block-6`, `v0.1.0`, `v1.0.0` und `v1.1.0` prüfen. `final` auf den geprüften Merge-Commit setzen, pushen und die Zuordnung auf GitHub kontrollieren. `final` ist die verbindliche Abgabe, laut Projektbeschreibung spätestens am **23. November**. ([PB §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine); [PB §15](docs/task_description/Projektbeschreibung_Photo_Album.md#15-abgabe))

</details>

---

## Übergreifende Regeln und Termine

<details>
<summary>Vorgaben der Projektbeschreibung anzeigen</summary>

- **Zwischenstände und Abgabe:** Jeder Block endet mit einem prüfbaren Zwischenstand im Repository. PB §3 schlägt die Blocktags für den Vorabend des Folgeblocks vor. Die Tagstände werden bewertet; spätere Verbesserungen zählen, wenn sie bis `final` vorliegen. `final` ist spätestens am 23. November abzugeben. ([PB §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine))
- **Versionen:** `v0.1.0` nach Block 4, `v1.0.0` für die vollständige ursprüngliche Pflichtfunktionalität und `v1.1.0` nach dem CR; Releases werden mit [CHANGELOG.md](CHANGELOG.md) dokumentiert. ([PB §9](docs/task_description/Projektbeschreibung_Photo_Album.md#9-implementierung-git-und-ci-block-4))
- **KI-Dokumentation:** Mindestens drei substanzielle Einsatzfälle insgesamt, empfohlen fünf mit je einem aus Block 2–6. Aufgabe/Ziel, Werkzeug/Modell samt versioniertem Prompt oder Skill, Ergebnis, kritische Bewertung und begründete Entscheidung dokumentieren. Mindestens einen tatsächlichen Vorschlag begründet verwerfen; Coding-Agent-PRs erhalten `ai-assisted` und denselben Review-Prozess. ([PB §13](docs/task_description/Projektbeschreibung_Photo_Album.md#13-einsatz-von-ki))
- **Zusammenarbeit und Mitarbeit:** Alle tragen nachvollziehbar und substanziell bei; die Git-Historie ist Teil der Bewertung. Aktive Beteiligung in der Vorlesung gehört ebenfalls zur Aufgabe. ([PB §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine); [PB §17](docs/task_description/Projektbeschreibung_Photo_Album.md#17-was-eine-sehr-gute-leistung-auszeichnet); [PB §18](docs/task_description/Projektbeschreibung_Photo_Album.md#18-checkliste-abgabe))
- **Qualität und Bewertung:** Eine kleine, zuverlässige und verständliche Anwendung mit testbaren Anforderungen, begründetem Entwurf und systematischen Tests entwickeln. Bewertet werden Projektartefakte/Software mit 50 %, Präsentation mit 30 % und aktive Mitarbeit mit 20 %. ([PB §16](docs/task_description/Projektbeschreibung_Photo_Album.md#16-bewertung); [PB §17](docs/task_description/Projektbeschreibung_Photo_Album.md#17-was-eine-sehr-gute-leistung-auszeichnet))

</details>

## Quellenstand und offene Auslegungen

<details>
<summary>Grundlage und Auslegungen anzeigen</summary>

**Stand 27.10.2026:** Grundlage der Arbeitspunkte ab Block 3 ist die [Projektbeschreibung](docs/task_description/Projektbeschreibung_Photo_Album.md). Block 3–7 enthalten zusätzlich die gemeinsam ausgewählten Folienaufträge und Projektfestlegungen, darunter KI als Won’t, Maven, Dependabot und persönliche Agentenregeln unter `agents/`. Zuständigkeiten und die Klappbereiche dienen der Organisation; sie sind keine zusätzlichen Anforderungen der Projektbeschreibung.

- **Blackbox-Verfahren:** Die Meilensteintabelle nennt für Block 5 zwei Verfahren; §10 fordert mindestens eines und empfiehlt zwei. Der Plan enthält deshalb zwei. ([PB §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine); [PB §10](docs/task_description/Projektbeschreibung_Photo_Album.md#10-testing-und-qualitätssicherung-block-5-und-6))
- **KI-Entwurf und Evaluation:** §8 fordert den Entwurf mit Port, Adapter und Guardrails; nur die Umsetzung ist freiwillig. §10 verlangt ein Evaluation Set auch für eine nur entworfene KI-Funktion. Das Set bleibt Pflicht; die Kennzeichnung „optional“ im Block-5-Checkpoint wird nicht übernommen. ([PB §8](docs/task_description/Projektbeschreibung_Photo_Album.md#8-architektur-und-entwurf-block-3); [PB §10](docs/task_description/Projektbeschreibung_Photo_Album.md#10-testing-und-qualitätssicherung-block-5-und-6); [Block 5, S. 27–28](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=27))
- **Change Request:** Die konkreten CR-Funktionen aus den Folien bleiben Beispiele. Dokumentiert und analysiert wird der tatsächlich gestellte CR mit eindeutigen Unterpunkten wie `CR-1.1`. Erst nach der Impact Analysis folgt die Umsetzung; Release `v1.1.0` ist bis Block 7 vorgesehen. ([PB §11](docs/task_description/Projektbeschreibung_Photo_Album.md#11-change-request-block-6); [Block 6, S. 11–12](docs/lecture_slides/Block_6_Whitebox_Wartung_Change_Request_final.pdf#page=11))
- **Finaler Tag:** §3 erwähnt „block-7“ beim letzten Meilenstein. Die Mindestliste in §15 nennt `block-1` bis `block-6` und `final`; eine zusätzliche Tagpflicht `block-7` wird daraus hier vorerst nicht abgeleitet. ([PB §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine); [PB §15](docs/task_description/Projektbeschreibung_Photo_Album.md#15-abgabe))

</details>
