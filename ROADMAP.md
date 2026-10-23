# Roadmap

## Meilensteine und Releases

| Block | Schwerpunkt | Wesentlicher Zwischenstand | Geplanter Tag | Erledigt |
| --- | --- | --- | --- | :---: |
| [1](#block-1-einführung-und-vorgehen) | Einführung und Vorgehen | Team, Repository, [README.md](README.md), Vorgehensentscheidung für Kanban und [Kanban-Board](https://photoalbum-kanban.vercel.app) | `block-1` | ✅ 19.10.2026 |
| [2](#block-2-requirements-engineering) | Requirements Engineering | [REQUIREMENTS.md](REQUIREMENTS.md) mit 6-10 FA, 3-5 NFA, mindestens 2 Use Cases und Akzeptanzkriterien; [UI-Prototyp](docs/prototype/index.html); erster Eintrag in [AI_USAGE.md](AI_USAGE.md); Anforderungsaufgaben im Kanban-Board | `block-2` | ☐ |
| [3](#block-3-architektur-und-entwurf) | Architektur und Entwurf | [ARCHITECTURE.md](ARCHITECTURE.md), Klassen- und Sequenzdiagramm, mindestens ein ADR und Projektgerüst; Entwurfsaufgaben im Kanban-Board | `block-3` | ☐ |
| [4](#block-4-implementierung-und-git) | Implementierung und Git | Pflichtfunktionalität, Build, Reviews und CI; Release `v0.1.0` | `block-4` | ☐ |
| [5](#block-5-tests-und-qualität) | Tests und Qualität | [TESTING.md](TESTING.md), Unit- und Integrationstests, 2 Blackbox-Verfahren, Usability-Protokoll und Evaluation Set | `block-5` | ☐ |
| [6](#block-6-whitebox-change-request-und-refactoring) | Change Request und Refactoring | Impact Analysis in [CHANGE_REQUEST.md](CHANGE_REQUEST.md), Coverage-Interpretation, [REFACTORING.md](docs/REFACTORING.md) und Umsetzung des Change Requests; Release `v1.1.0` | `block-6` | ☐ |
| [7](#block-7-risiken-release-entscheidung-und-präsentation) | Risiken und Präsentation | [RISK_ANALYSIS.md](RISK_ANALYSIS.md), begründete Release-Entscheidung und Abschlusspräsentation | `block-7`, danach `final` | ☐ |

Für die vollständige Pflichtfunktionalität ist außerdem Release `v1.0.0` vorgesehen. Releases werden nach Semantic Versioning getaggt und in [CHANGELOG.md](CHANGELOG.md) dokumentiert. Der verbindliche Abgabetag `final` muss laut [Projektbeschreibung](docs/task_description/Projektbeschreibung_Photo_Album.md) spätestens am **23. November** vorliegen.

Die **Projektbeschreibung ist die Hauptquelle**. Projektbezogene Arbeitsaufträge aus den Folien gelten als **zusätzliche verbindliche Anforderungen**; solche Aufträge sind hier auch als „Projektübung“ bezeichnet. Bei einem Widerspruch hat die Projektbeschreibung Vorrang. Ausdrücklich optionale Teile bleiben optional; widersprüchliche Folienangaben werden als offen markiert.

Die Reihenfolge führt vom Projektergebnis im Unterricht über die Nacharbeit zum jeweiligen Tag. Allgemeine Lernübungen, Exit Tickets und Aufgaben für andere Übungsrepositories sind nicht Teil dieses Arbeitsplans. Bestätigte Teamentscheidungen sind als solche bezeichnet.

Die Listen ab Block 2 sind Arbeitslisten. Ein vorhandener Entwurf erfüllt einen Punkt erst nach Prüfung; der gesamte Block wird oben mit Datum abgehakt, sobald sein abschließender Stand in `main` liegt und der Tag auf GitHub vorhanden ist. Die Quellenlage und widersprüchliche Angaben stehen [am Ende](#quellenstand-und-offene-auslegungen).

### Block 1: Einführung und Vorgehen

**Ziel:** Das Team kann im gemeinsamen Repository mit einem vereinbarten Vorgehen arbeiten. **Abschluss:** `block-1`, erledigt am 19.10.2026. Sammel-Issue: [#14](https://github.com/felix-henry/photo-album-projekt/issues/14).

#### Ergebnisse

- [x] **Team und Repository:** Mitglieder und GitHub-Namen in der [README](README.md) nennen; Zugänge für alle Teammitglieder und Ana sicherstellen.
- [x] **Vorgehensentscheidung:** Kanban auswählen und die Entscheidung in der README begründen. Das Team hat README und Vorgehen bestätigt.
- [x] **Zusammenarbeit:** [Kanban-Board](https://photoalbum-kanban.vercel.app) verlinken und zugänglich machen; Vorgehen und Kommunikation in der README festhalten.
- [x] **Zwischenstand abgeben:** README und Projektgrundlage nach `main` übernehmen und den abschließenden Commit mit `block-1` taggen.

**Quellen:** [Projektbeschreibung §3](docs/task_description/Projektbeschreibung_Photo_Album.md#3-organisation-und-meilensteine); [Block-1-Folien als Markdown](docs/lecture_slides/Block_1_Vorgehensmodelle.md), [PDF S. 2](docs/lecture_slides/Block_1_Vorgehsmodelle.pdf#page=2); bestätigte Teamorganisation aus der [README](README.md#vorgehen-und-zusammenarbeit).

### Block 2: Requirements Engineering

**Ziel:** Anforderungen, Grenzen und prüfbare Abnahmekriterien stehen fest; offene Fachfragen sind erkennbar. **Abschluss:** `block-2`, bis zum Vorabend von Block 3. Sammel-Issue: [#15](https://github.com/felix-henry/photo-album-projekt/issues/15).

#### 1. Stakeholder, Systemgrenze und Use Cases

- [x] **Stakeholder festhalten:** Mindestens vier Stakeholder mit Interesse, Einfluss (hoch/mittel/niedrig) und je einer Anforderung aus ihrer besonderen Sicht in [REQUIREMENTS.md](REQUIREMENTS.md) beschreiben. Die Übung verlangt eine Anforderung, die nur dieser Stakeholder stellt. Überschneidungen der allgemeinen Interessen ersetzen diese Zuordnung nicht. *(Pflicht: PB §7; Projektübung: Folien S. 8.)*
- [x] **Kontextdiagramm erstellen:** Systeminhalt, Menschen/Nachbarsysteme, Schnittstellen und bewusst ausgeschlossenen Umfang zeigen. Fachliche Funktionen und Oberfläche liegen im System; der Nutzer und das lokale Dateisystem außerhalb. Eingaben, Ausgaben und Dateizugriffe als Schnittstellen benennen. Nur tatsächlich vorgesehene Schnittstellen einzeichnen. *(PB §7; Folien S. 9.)*
- [x] **Use-Case-Diagramm korrigieren:** Die Use Cases aus Nutzersicht und innerhalb der vereinbarten Systemgrenze darstellen; technische Einzelschritte nicht ungeprüft als eigene Nutzerziele übernehmen. *(Projektübung: S. 13-16.)*
- [x] **Mindestens zwei Use Cases ausarbeiten:** „Foto hinzufügen“ und ein zweiter zentraler Use Case, bei uns „Album erstellen“. Akteur, Ziel, Vor-/Nachbedingungen sowie Standard- und Alternativabläufe beschreiben. Für „Foto hinzufügen“ nennt die Übung einen Standardablauf mit höchstens sechs Schritten sowie zwei Alternativabläufe. *(PB §7; S. 13-17 und 40.)*
- [x] **Drei FA aus „Foto hinzufügen“ ableiten:** Je eine aus einem Systemschritt, einem Alternativablauf und einer Vorbedingung; mit den Use-Case-Stellen verknüpfen. Diese drei Anforderungen gehören zu den insgesamt 6-10 FA und kommen nicht zusätzlich dazu. Diagramm, Use-Case-Beschreibung und Ableitung stehen gemeinsam in REQUIREMENTS.md. *(Projektübung: S. 13 und 16.)*

Lokal geprüft am **23.10.2026**. Die in REQUIREMENTS.md gekennzeichneten Fachfragen, insbesondere A-17 zu Dateifehlern, bleiben unter „3. Auftraggeber, Annahmen und Sicherheit“ zu klären.

#### 2. Anforderungen und Akzeptanzkriterien

- [ ] **6-10 FA vervollständigen; unser Ziel sind zehn:** ID `FA-xx`, SOPHIST-Satzschablone und Verbindlichkeit (muss/sollte). Die Pflichtfunktionen abdecken: Alben erstellen/anzeigen/umbenennen/löschen, Fotos hinzufügen/anzeigen/aus Alben entfernen, Titel bzw. Beschreibung bearbeiten, ungültige Eingaben und definierte Fehlerfälle behandeln. Anforderungen einzeln prüfbar und ohne unnötige technische Vorentscheidungen formulieren. Jede FA mindestens einem Use Case zuordnen. *(PB §5 und §7; S. 24-27 und 40.)*
- [ ] **3-5 NFA ausarbeiten; unser Ziel sind fünf:** Jeweils Qualitätsmerkmal, konkrete Messgröße und Bedingung nennen. Messgerät, Datensatz und Belastung festlegen, soweit sie für die Prüfung nötig sind. Vorschläge noch nicht als bestätigte Ziele ausgeben. Unsere vereinbarte gepufferte Bilddarstellung berücksichtigen; eine Zeitvorgabe für das gleichzeitige Laden aller 10.000 Bilder ist nicht vereinbart. *(PB §7; S. 20.)*
- [ ] **Alle Muss-Anforderungen mit AK versehen:** Prüfbare Given/When/Then-Kriterien mit IDs `AK-xx.n` und Zuordnung zur Anforderung. Ausgangszustand, Aktion und beobachtbares Ergebnis beschreiben; bei Ablehnung Fehlermeldung und unveränderten Zustand prüfen. Die Folien nennen je Anforderung mindestens einen Normal- und einen Fehlerfall. Die Kriterien müssen auch zu verbindlichen NFA und Sicherheitsanforderungen passen. *(PB §5.1 und §7; S. 35-40.)*
- [ ] **Zwei Geschäftsregeln genauer prüfen:** Für jede der beiden gewählten Regeln mindestens drei AK formulieren: Normalfall, Fehlerfall und Grenzfall. Fachlich ungeklärte Begriffe markieren. Unsere bestätigte Albumgrenze beträgt **10.000 Fotos**; dazu passen etwa 9.999, 10.000 und 10.001 als Grenzwerte. Die Folienwerte 100 Fotos oder 100 Titelzeichen sind keine Bestätigung für unser Projekt. *(Projektübung: S. 39; Team-/Gesprächsstand zur Albumgrenze.)*
- [ ] **Sprach- und Konsistenzprüfung durchführen:** Unklare Wörter, fehlende Bedingungen, vermischte Anforderungen, widersprüchliche Regeln und falsche Verweise bereinigen. Nutzen, Systemgrenze, Realisierbarkeit, Prüfbarkeit und vollständige Abdeckung der Pflichtfunktionen prüfen. *(S. 24-27 und 40-42.)*

#### 3. Auftraggeber, Annahmen und Sicherheit

- [ ] **Fachfragen klären und Antworten belegen:** Albumname und Länge, Fotoanzahl, Titel-/Beschreibungslänge, erlaubte Dateitypen und Bedeutung von „identisch“ klären. Zusätzlich offene Fragen aus den Use Cases aufnehmen. Antworten von Ana mit Quelle unter „Auftraggeber-Entscheidungen“ festhalten; bewusst eigene Entscheidungen begründet unter „Annahmen“ dokumentieren. Ein Folienbeispiel ist keine Auftraggeber-Antwort. *(PB §4-5 und §7.)*
- [ ] **Sprechstunde vorbereiten:** Aus der Requirement Clinic höchstens drei offene Fragen sammeln; in der Sprechstunde erlaubt die Folie höchstens eine Frage pro Team. Auch Antworten an andere Teams aufnehmen, soweit sie verbindlich für alle gelten, und mit den veröffentlichten Moodle-Antworten abgleichen. *(Projektübungen: S. 40 und 53.)*
- [ ] **„KI und Sicherheit“ ausarbeiten:** Einen konkreten Prompt-Injection-Angriff über ein Bild bzw. dessen fremden Inhalt mit betroffenem Tool und Schaden beschreiben. Drei Anforderungen nach Satzschablone zu Datenzugriff, Tool-Nutzung und Freigabe formulieren. Festhalten, welche Datenschutzanforderung auch ohne KI-Funktion gilt. Die Begründung gegen die fünf Schutzebenen der Folien prüfen: fremde Inhalte als Daten behandeln, Rechte begrenzen, menschliche Freigabe, Ausgabeprüfung und Protokollierung. Das Szenario dient der Anforderungsanalyse und verpflichtet nicht zum Bau eines Agenten. *(PB §7; Projektübung: S. 49-51.)*
- [ ] **Optionale KI-Funktion einordnen:** Wird eine KI-Funktion vorgesehen, Zweck/Grenzen, zulässige Daten, Qualität, Umgang mit Unsicherheit, menschliche Freigabe und Verhalten bei Ausfall spezifizieren. Bei einem Agenten zusätzlich Aufgabe, erlaubte Tools, Datenzugriff, Bestätigung und Protokollierung begrenzen. Die Umsetzung einer KI-Funktion bleibt freiwillig; der Sicherheitsabschnitt bleibt erforderlich. *(S. 45 und 48-51.)*

#### 4. Prototyp, Review und Abgabe

- [ ] **Ersten UI-Prototyp abgleichen:** Klickbarer oder Papier-Prototyp für die zwei ausgearbeiteten Use Cases; im Repository ablegen und aus REQUIREMENTS.md verlinken. Für uns ist das der [Klickprototyp](docs/prototype/index.html). Die Abläufe müssen zu den Anforderungen passen; vorläufige Regeln kennzeichnen. *(PB §7; S. 55.)*
- [ ] **Partner-Review vergeben und erhalten:** Im Partner-Repository ein Review-Issue mit vier Punkten erstellen: zwei aus Auftraggebersicht, zwei aus Testsicht. Das erhaltene Review prüfen und die Übernahme oder begründete Ablehnung nachvollziehbar festhalten. Die konkrete Partnerzuordnung im Unterricht klären. *(Projektübung: S. 41; Nacharbeit: S. 55.)*
- [ ] **KI-Fall 1 dokumentieren:** Den „Grill me“-Durchlauf kritisch in [AI_USAGE.md](AI_USAGE.md) festhalten: Aufgabe/Block, Werkzeug/Modell soweit bekannt, tatsächlicher versionierter Prompt/Skill, Ergebnis, Bewertung und Entscheidung. Mindestens einen tatsächlichen Vorschlag begründet verwerfen. Unser vorhandener Fall wird auf Vollständigkeit geprüft; Routineaktionen brauchen keine eigenen Fälle. *(PB §3 und §13; S. 44 und 55.)*
- [ ] **User Stories im Backlog und Aufgaben im Kanban-Board pflegen:** Kurze User Stories nach „Als Rolle möchte ich Ziel, damit Nutzen“ mit Akzeptanzkriterien und denselben FA-IDs wie bei den Use Cases festhalten. Die Folie benennt dafür `Issues.md`; diese Backlog-Datei in den Projektumfang aufnehmen und mit Board/GitHub-Issues verknüpfen. Eine abweichende Ablage gilt erst nach Bestätigung als Ersatz. *(Teamentscheidung Kanban; ergänzende Vorgabe S. 17.)*
- [ ] **Checkpoint und Tag:** Stakeholder/Kontext, zehn FA, fünf NFA, zwei Use Cases, AK, Entscheidungen/Annahmen, Sicherheit, Prototyp, Review und KI-Fall gemeinsam prüfen. Offene Punkte mit Verantwortlichem im passenden Issue festhalten. Änderungen über PR und Review nach `main` übernehmen, dann `block-2` setzen. *(PB §3 und §7; S. 54-55.)*

**Quellen:** [Projektbeschreibung §4-7 und §13](docs/task_description/Projektbeschreibung_Photo_Album.md); [Block-2-Folien als Markdown](docs/lecture_slides/Block_2_Requirements_Engineering.md), [Original-PDF, 56 Seiten](docs/lecture_slides/Block_2_Requirements_Engineering.pdf).

### Block 3: Architektur und Entwurf

**Ziel:** Der Entwurf zeigt, wer welche Verantwortung trägt und wie die Use Cases einschließlich Fehlerfällen ablaufen. **Abschluss:** `block-3`, bis zum Vorabend von Block 4. Sammel-Issue: [#16](https://github.com/felix-henry/photo-album-projekt/issues/16).

#### 1. Verantwortlichkeiten und Diagramme

- [ ] **Requirements als Grundlage prüfen:** Auftraggeber- und Review-Feedback aus Block 2 einarbeiten; tatsächliche Regeln und NFA in den Entwurf übernehmen. Entwurfsaufgaben im Kanban-Board pflegen. *(PB §4 und §8; Teamentscheidung Kanban.)*
- [ ] **Schichten und Komponenten begründen:** In [ARCHITECTURE.md](ARCHITECTURE.md) Oberfläche, Anwendung/Use Cases, Domäne und Infrastruktur mit Verantwortlichkeiten und Abhängigkeitsrichtung darstellen. Geschäftsregeln an einer fachlich passenden Stelle halten; Domäne von UI, Dateisystem und KI trennen. Verantwortlichkeiten anhand von Kohäsion, Kopplung und Kapselung prüfen. Die Architektur mit den relevanten NFA begründen, etwa Testbarkeit, Fehlerbehandlung und gepuffertem Laden. *(PB §8; S. 7-17.)*
- [ ] **Schnittstellen festlegen:** Benötigte Ports und Adapter benennen; wer ruft wen auf, was wird übergeben, welche Fehler sind möglich? Use Cases kennen die Ports; konkrete Adapter werden zentral in `Main` bzw. im Test verdrahtet. Build-Werkzeug, Oberfläche und lokales Speicherformat auswählen und begründen. Das Speicherformat muss dokumentiert sein. Zusätzliche Technologien benötigen eine fachliche Begründung im ADR und müssen in CI ohne manuelle Installation laufen. Bibliotheks- und Klassenbezeichnungen aus den Beispielen sind keine vorgeschriebene Implementierung. *(PB §6 und §8; S. 8-11.)*
- [ ] **CRC-Karten erarbeiten:** Aus den zwei Use Cases Klassenkandidaten gewinnen, Attribute von Klassen trennen, Synonyme bereinigen. Je Klasse aus Domäne, Anwendung und Ports eine Karte mit Verantwortlichkeiten/Zusammenarbeit anlegen; jede Geschäftsregel genau einer Karte zuordnen. „Foto hinzufügen“ mit den Karten durchspielen und Fotos der Karten in [docs/design/](docs/design/) ablegen. *(Projektübung: S. 19-21.)*
- [ ] **Klassendiagramm erstellen:** Domäne, Anwendung und Ports mit relevanten Beziehungen, Multiplizitäten und entscheidungsrelevanten Operationen zeigen. Mit CRC-Karten und Geschäftsregeln abgleichen, unter docs/design/ ablegen und aus ARCHITECTURE.md verlinken. *(PB §8; S. 22-23 und 49.)*
- [ ] **Sequenz „Foto hinzufügen“ erstellen:** Standardablauf und mindestens einen fachlichen Fehlerpfad, in den Folien das Duplikat, darstellen. Jede Nachricht muss zu einer passenden Operation im Klassenmodell gehören; Speicherung und Fehlerbehandlung nachvollziehbar zeigen. Diagramm unter docs/design/ ablegen und verlinken. *(PB §8; S. 25-26 und 49.)*
- [ ] **Projektübung „Album löschen“ bearbeiten:** Eine weitere Sequenz mit UI, Lösch-Use-Case, Album, Repository und Datei-Port zeichnen; Bestätigung und Abbruch als `alt` zeigen und gegen die CRC-Karten prüfen. Das in der Übung angenommene Mitlöschen von Fotos zuerst mit unseren tatsächlichen Löschregeln abgleichen; daraus keine unbestätigte Regel übernehmen. Das zusätzliche Übungsdiagramm ist vom ausdrücklich geforderten Pflichtdiagramm „Foto hinzufügen“ zu unterscheiden. *(S. 27.)*

#### 2. Entscheidungen und KI-Entwurf

- [ ] **Mindestens ein ADR schreiben:** Unter [docs/adr/](docs/adr/) Titel, Status/Datum, Kontext, Entscheidung, verworfene Alternative mit Grund und Konsequenzen festhalten. Die wichtigste tatsächliche Entscheidung wählen. Die Folien nennen drei bis vier ADR als realistisch, schreiben diese Anzahl aber nicht vor. *(PB §8; S. 48-49.)*
- [ ] **Patterns am Problem prüfen:** Jedes tatsächlich eingesetzte Entwurfsmuster braucht ein benanntes Problem und ein ADR mit begründet verworfener Alternative. Die behandelten Patterns sind eine Auswahl von Lösungswegen, keine vorgeschriebene Anzahl einzubauender Muster. *(PB §8; S. 29-36.)*
- [ ] **Optionale KI-Komponente entwerfen:** Port in der Anwendung und Adapter in der Infrastruktur sowie Guardrails zeigen. Für den Adapter insgesamt zwei Prüfungen benennen und dem Ablauf vor der Anfrage bzw. nach der Antwort zuordnen. Die Übung ergänzt dafür das Klassendiagramm und einen kurzen Abschnitt „KI-Komponente“ in ARCHITECTURE.md; sie nennt dafür fünf Zeilen. Umsetzung und externe Anbindung bleiben freiwillig. *(PB §8; S. 34 und 45-47.)*
- [ ] **Agentenrechte als Entwurfsübung begrenzen:** Für „Fotos aufräumen“ höchstens vier Tools mit kurzer Beschreibung skizzieren. Die vorgegebenen Aktionen `deleteAlbum`, `removePhoto`, `renameAlbum` und `shareAlbum` als direkt ausführbar, nur mit Freigabe oder gar nicht angeboten einordnen. Das ist eine Entwurfsübung; sie erweitert unseren Funktionsumfang nicht automatisch um Teilen oder einen Agenten. Festhalten, ob die KI-Funktion vorerst ausgeschlossen (`Won't`) oder vorgesehen (`Should`) ist; für `Should` sieht die Übung eine Testattrappe (`FakeSuggester`) in Tests vor. *(Projektübung: S. 47.)*

#### 3. Review, Gerüst und Abgabe

- [ ] **Diagramm-Review vergeben und erhalten:** Ein Review-Issue mit drei konkreten Punkten im Partner-Repository: Ort der Geschäftsregeln, Widerspruch zwischen Sequenz- und Klassendiagramm, stärkste Kopplung und deren Begründung. Erhaltenes Feedback einarbeiten oder begründet ablehnen. Partnerzuordnung im Unterricht bestätigen. *(Projektübung: S. 50; Nacharbeit: S. 52.)*
- [ ] **Projektgerüst anlegen:** Java-21-Projekt mit Maven oder Gradle, Paketen für Domäne/Anwendung/Infrastruktur/UI sowie ersten Klassen `Album` und `Photo` mit Konstruktoren und Invarianten gemäß Entwurf. Jede Person hat Java 21, IDE und Git lauffähig; der Build läuft lokal grün, auch wenn erst wenige oder noch keine Tests existieren. *(PB §3 und §6; S. 52.)*
- [ ] **Arbeitsumgebung und Zugriff prüfen:** Alle Teammitglieder können das Projekt bauen und auf das Repository zugreifen. `main` ist durch verpflichtende PRs geschützt; bei uns sind vier Teamfreigaben vereinbart. *(PB §6; S. 52; Teamvereinbarung.)*
- [ ] **KI-Entwurfsreview dokumentieren:** Ein Klassendiagramm-Review durch KI kritisch auswerten; einen Vorschlag übernehmen und einen mit Begründung verwerfen, soweit der tatsächliche Review das hergibt. Als vorgesehenen zweiten substanziellen Fall in AI_USAGE.md dokumentieren; keine Entscheidungen erfinden, um eine Vorlage zu füllen. *(Hausaufgabe: S. 52; PB §13.)*
- [ ] **Checkpoint und Tag:** Architekturbegründung, Ports, CRC-/Klassen-Konsistenz, Sequenz mit Fehlerpfad, ADR samt Alternative, KI-Entwurf, Review und Projektgerüst prüfen. ARCHITECTURE.md verlinkt Diagramme und ADRs. Abschluss über PR nach `main`, dann `block-3`. *(PB §3 und §8; S. 49-52.)*

**Quellen:** [Projektbeschreibung §6 und §8](docs/task_description/Projektbeschreibung_Photo_Album.md); [Block-3-Folien als Markdown](docs/lecture_slides/Block_3_Entwurf_und_Entwurfsmuster.md), [Original-PDF, 53 Seiten](docs/lecture_slides/Block_3_Entwurf_und_Entwurfsmuster.pdf).

### Block 4: Implementierung und Git

**Ziel:** Aus dem Entwurf wird eine bedienbare Anwendung mit reproduzierbarem Build, Tests, Review und CI. **Abschluss:** `block-4` und `v0.1.0`, bis zum Vorabend von Block 5. Sammel-Issue: [#17](https://github.com/felix-henry/photo-album-projekt/issues/17).

#### 1. Reproduzierbarer Build und lauffähiger Kern

- [ ] **Feedback aus Block 3 einarbeiten:** Auftraggeber- und Entwurfsreview-Rückmeldungen bewerten; betroffene Anforderungen, Architektur und ADRs entsprechend nachführen und Entscheidungen festhalten. Mit dem abgestimmten Stand implementieren. *(PB §4, §8 und §9.)*
- [ ] **Build-Entscheidung dokumentieren:** Maven oder Gradle wählen und in einem ADR knapp begründen. Java 21 in der Build-Konfiguration festlegen, Abhängigkeiten und Plugins mit festen Versionen verwalten; den Grund für jede Dependency im ADR festhalten. Dafür ist kein eigener ADR je Bibliothek vorgeschrieben. *(PB §6; S. 7-11.)*
- [ ] **Wrapper und Tests einrichten:** Build-Konfiguration und Wrapper versionieren; JUnit 5 anbinden. Ein dokumentierter Befehl löst Dependencies auf, kompiliert, führt Tests aus und erzeugt das Artefakt. Ein fehlgeschlagener Test lässt den Build scheitern. Build-/IDE-Ausgaben über `.gitignore` ausschließen, Quell- und Testpakete nach Schichten ordnen. *(PB §6 und §9; S. 6-9.)*
- [ ] **Build-Check bei allen Teammitgliedern:** Jeder klont das Projekt in ein frisches Verzeichnis und führt `./mvnw verify` bzw. `./gradlew build` aus; unter Windows den entsprechenden Wrapper verwenden. Fehlerursachen wie fehlender Wrapper, Java-Version oder IDE-Abhängigkeit im Issue festhalten und beheben. *(Projektübung: S. 11.)*
- [ ] **Durchgängigen Kern implementieren:** „Album erstellen und anzeigen“ läuft von der Oberfläche über Anwendung und Domäne bis zur lokalen Speicherung. Das ist der Unterrichtscheckpoint; Geschäftsregeln gehören in die vorgesehenen fachlichen Klassen. *(S. 3 und 30.)*
- [ ] **Bis zur Abgabe alle Pflichtfunktionen fertigstellen:** Fotos hinzufügen/anzeigen/entfernen, Titel bzw. Beschreibung bearbeiten sowie Alben umbenennen/löschen ergänzen. Definierte Fehlerfälle mit fachlicher Meldung und korrekt behandeltem Zustand absichern. Alle Geschäftsregeln aus REQUIREMENTS.md umsetzen und mit mindestens einem Test je Regel absichern. *(PB §5 und Meilenstein Block 4; Hausaufgabe S. 31.)*
- [ ] **Build und Start beschreiben:** README enthält tatsächliche Voraussetzungen/Versionen, Build-, Test- und Startbefehle. Das verwendete Dateiformat und die Speicherorganisation sind dokumentiert. Die Oberfläche muss die Use Cases bedienbar machen; eine zusätzliche Web-/Cloud-Plattform ist nicht gefordert. *(PB §4, §6 und §15.)*

#### 2. Teamprozess, Reviews und CI

- [ ] **Git-Workflow anwenden:** Passendes Issue → Branch → kleine nachvollziehbare Commits → PR → Review → CI → Merge → Arbeitsbranch löschen. Jeder Commit enthält eine zusammengehörige Änderung mit grünem Build/Teststand. Konventionelle Commit-Nachrichten mit Typ und Anforderungs- oder Issue-ID verwenden; keine bloßen `fix`-/`WIP`-Nachrichten auf `main`. PR-Beschreibung nennt Ziel, Umsetzung, Prüfung und offene Risiken und verlinkt Issue/gegebenenfalls ADR. PR-Template mit Prüfungen zu Tests, Dokumentation und KI-Nutzung sowie Commit-Konvention im Repository festhalten. Die Folien nennen kurze Betreffe im Imperativ mit höchstens 72 Zeichen und kleine PRs unter 400 geänderten Zeilen als Richtwerte. *(PB §9; S. 13-16 und 30.)*
- [ ] **Inhaltliche Reviews nachweisen:** Mindestens ein PR mit dokumentiertem Review und beantworteten Kommentaren zum Unterrichtscheckpoint. Fachliche Korrektheit, Verantwortlichkeiten und Tests zuerst prüfen, danach Fehlerbehandlung/Sicherheit, Verständlichkeit und Stil. Zusätzlich verlangt PB §9 mindestens ein dokumentiertes Code Review je Person; im Team für alle fünf nachweisen. *(PB §9; S. 18-19 und 30.)*
- [ ] **CI aufsetzen und grün nachweisen:** Saubere Umgebung mit Java 21, Wrapper-Build und Tests bei jedem PR gemäß Projektbeschreibung und zusätzlich bei jedem Push auf jedem Branch gemäß Folie 20. Das Minimal-YAML auf Folie 21 dafür erweitern. Testergebnis im PR anzeigen; JAR und JaCoCo-Report als herunterladbare CI-Artefakte bereitstellen. Erforderlichen CI-Job im Schutz von `main` hinterlegen; die vier Teamfreigaben bleiben bestehen. *(PB §6; ergänzende Vorgabe S. 20, Minimalbeispiel S. 21, Checkpoint S. 30.)*
- [ ] **Dependencies und Secrets behandeln:** Benötigte Bibliotheken bewusst begrenzen, Änderungen mit Tests prüfen, Secrets aus dem Repository halten und die Secrets-Regel in der README festhalten. Dependabot und statische Analyse sind gesondert einzuordnen, siehe Quellenhinweise. *(S. 11 und 22.)*

#### 3. Agentenregeln, Versionen und Abgabe

- [ ] **Gemeinsame `AGENTS.md` versionieren:** Tatsächliche Build-/Testbefehle, Paketnamen, Architekturregeln und drei projektspezifische Verbote festhalten; PR-Label `ai-assisted` und Art der KI-Dokumentation vereinbaren. Die gemeinsame Einreichungsfassung muss im Team noch abgestimmt werden. Die Vorgabe gilt bereits für Block 4. *(PB §3 und §9; Projektübung S. 27 und 29-30.)*
- [ ] **Verwendete KI-Konfiguration versionieren:** Tatsächlich eingesetzte Prompts, Skills und Modellkonfigurationen samt relevanten Parametern; bei vorhandenen Tools auch Rechte/Tool-Konfiguration. Für verwendete Skills Aufgabe, Ergebnisformat und Verbote beschreiben; bei tatsächlichen MCP-Tools Name, Ein-/Ausgabeschema, Fehlerfälle, Berechtigungen und Timeout festhalten. Die Beispielordner aus der Folie sind keine vorgeschriebene Verzeichnisstruktur. Prompts ausgewählter Fälle dürfen direkt in AI_USAGE.md stehen. *(PB §9 und §13; Block 2 S. 50; Block 4 S. 28.)*
- [ ] **KI-Fall zum Coding-Einsatz bewerten:** Aufgabe abgrenzen, Diff und Tests prüfen, angenommene/geänderte/verworfene Vorschläge begründen und den substanziellen Einsatz in AI_USAGE.md dokumentieren. Die Folien sehen hier Fall 3 vor; Routineaktionen bleiben Teil eines zusammengehörigen Falls. Agenten-PRs erhalten `ai-assisted` und denselben menschlichen Review-Prozess. *(PB §13; S. 25-30.)*
- [ ] **Release und Blocktag erstellen:** Änderungen und bekannte Einschränkungen in [CHANGELOG.md](CHANGELOG.md) festhalten, Versionen abstimmen, erfolgreiche Build-/Teststände über PR nach `main` übernehmen. `v0.1.0` und `block-4` taggen. Zusätzlich `v1.0.0` auf den nachweislich vollständigen Pflichtumfang setzen; die Projektbeschreibung nennt dafür keinen eigenen festen Blocktermin. *(PB §3 und §9; S. 23 und 31.)*

**Quellen:** [Projektbeschreibung §5-6, §9 und §13](docs/task_description/Projektbeschreibung_Photo_Album.md); [Block-4-Folien als Markdown](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.md), [Original-PDF, 32 Seiten](docs/lecture_slides/Block_4_Implementierung_und_Versionsmanagement.pdf).

### Block 5: Tests und Qualität

**Ziel:** Tests sind aus Anforderungen und Risiken hergeleitet; ihre Aussage und Ergebnisse sind nachvollziehbar. **Abschluss:** `block-5`, bis zum Vorabend von Block 6. Sammel-Issue: [#18](https://github.com/felix-henry/photo-album-projekt/issues/18).

#### 1. Testentwurf und automatisierte Tests

- [ ] **Teststrategie beschreiben:** In [TESTING.md](TESTING.md) Teststufen und relevante Testarten, Testbefehle und Zuständigkeit der Prüfungen festhalten. Domäne/Use Cases isoliert, Zusammenspiel mit echter Infrastruktur, Bedienbarkeit über die UI und Abnahme unterscheiden. Ein bis zwei manuelle Systemtests dokumentieren, etwa einen Ablauf über die UI einschließlich Neustart. *(PB §10; S. 6-10 und 28.)*
- [ ] **Zwei Blackbox-Verfahren dokumentieren:** Die Meilensteintabelle verlangt zwei; auch das Test Design Lab liefert zwei Ausarbeitungen. Eine Geschäftsregel mit Äquivalenzklassen und Grenzwerten untersuchen und einen Use Case mit Entscheidungstabelle oder Zustandsmodell. Eingaben/Bedingungen, erwartete Ergebnisse und abgeleitete Testfälle in Tabellen bzw. Diagramm zeigen. Nur tatsächliche Projektzustände und bestätigte Grenzen verwenden. *(PB §3 und §10; Projektübung S. 12-19.)*
- [ ] **Titel-/Beschreibungsregeln systematisch prüfen:** Gültige, ungültige und ungeklärte Äquivalenzklassen mit Repräsentanten sowie Grenzwertliste erstellen. Die dabei entstehende Fachfrage, etwa die Bedeutung von „optional“/„leer“, notieren und klären. Nicht unbesehen 100/500 Zeichen aus dem Übungsbeispiel übernehmen. *(Projektübung: S. 14.)*
- [ ] **Lab-Ergebnisse implementieren:** Jede relevante Tabellenzeile/Regel bzw. jeden Übergang als Test mit verständlichem Namen umsetzen. Normal-, Fehler- und Grenzfall durch Kommentar oder Tag kennzeichnen. Im Lab mindestens sechs neue Tests in einer PR erstellen; neue Unit-Tests daraus zählen zum geforderten Gesamtbestand von mindestens zehn Unit-Tests. Vorhandene Tests sind keine neuen Lab-Tests. *(Projektübung: S. 19; PB §10.)*
- [ ] **Mindestens zehn sinnvolle Unit-Tests nachweisen:** Normal-, Fehler- und Grenzfälle für Domäne und Use Cases; Arrange/Act/Assert mit klarer Aktion und erwartbarem Ergebnis. Bei Ablehnung sowohl den Fehler als auch den unveränderten Zustand prüfen. Tests sollen das geforderte Verhalten prüfen, nicht bloß die eigene Implementierung nachbilden. *(PB §10; S. 7, 10 und 28-29.)*
- [ ] **Mindestens zwei Integrationstests nachweisen:** Echte Infrastruktur und eine fachliche Fragestellung, beispielsweise Persistenz plus Duplikaterkennung nach erneutem Laden. Temporäre Verzeichnisse und reproduzierbare Testdaten verwenden; bloß „speichern und laden“ ohne fachliche Aussage reicht nicht. *(PB §10; S. 21 und 28.)*
- [ ] **Traceability herstellen:** `AK-ID → Verfahren → Testmethode` dokumentieren; jedes Akzeptanzkriterium soll zu mindestens einem Testfall führen. Automatisierte Testmethoden bzw. benannte manuelle Prüfungen und Messungen nachvollziehbar zuordnen. Testnamen, Tabellen und aktuelle IDs müssen zusammenpassen; Fälle, die mehrere Fehler gleichzeitig enthalten, brauchen ein geklärtes erwartetes Verhalten. *(PB §10; Block 2 S. 35; Block 5 S. 7, 16 und 19.)*
- [ ] **Wirksamkeit der eigenen Tests prüfen:** An der eigenen Geschäftslogik nachvollziehen, ob die Tests eine falsche Grenzprüfung oder entfernte Duplikatprüfung erkennen würden. Außerdem prüfen, ob eine vertauschte Reihenfolge von Kapazitäts- und Duplikatprüfung auffällt und laut vereinbarter Fehlerpriorität auffallen sollte. Fehlende fachlich nötige Tests ergänzen. Die Übung erlaubt eine gedankliche Prüfung; ein Mutationstest-Werkzeug ist dafür nicht vorgeschrieben. *(Projektübung: S. 20.)*
- [ ] **Test-PR im Team prüfen:** Lab-PRs über Kreuz reviewen, Rückmeldungen bearbeiten und alle automatisierten Tests in CI grün ausführen. Teststand und tatsächliche Ergebnisse in TESTING.md festhalten. *(S. 19 und 29.)*

#### 2. Usability, Sicherheit, Performance und Coverage

- [ ] **Usability-Kreuztest durchführen:** Eine Testperson erledigt eine konkrete Aufgabe ohne Hilfe; beobachten, protokollieren und Probleme nach Auswirkung priorisieren. Die Checkpoint-Folie verlangt ein Usability-Protokoll und drei Issues; nur tatsächliche Befunde erfassen. Mindestens ein Usability-Issue bis zum Folgeblock per PR beheben. *(PB §10; S. 23 und 28-29.)*
- [ ] **Security-Tests für Dateien ausführen:** Unter anderem falsche Endung/Inhalt, leere Datei, übergroße Datei, problematische Pfade und Steuerzeichen gegen unsere tatsächlichen Regeln testen. Anforderungen und erwartete Ablehnung zuerst festlegen. Bei umgesetzter KI auch den Umgang mit Bildmetadaten und fremden Anweisungen gegen die entworfenen Schutzgrenzen prüfen. Im Checkpoint zunächst geplante Dateifälle sind laut Hausaufgabe bis zum Folgeblock zu implementieren. *(PB §10; S. 23 und 28-29.)*
- [ ] **Eine Performance-NFA messen:** Unter den vereinbarten Bedingungen mit echten Testdaten messen und Gerät, Datensatz, Messvorgehen und Ergebnis dokumentieren. Für unseren Stand die gepufferte Darstellung und die vereinbarte Albumgröße berücksichtigen; Folien-Beispielwerte sind keine neuen Zielwerte. *(PB §10; S. 23 und 29.)*
- [ ] **JaCoCo-Report erzeugen:** Report aus dem Build zugänglich machen und die drei Klassen mit der niedrigsten Branch Coverage notieren. Das ist die Grundlage für die Interpretation in Block 6; eine hohe Prozentzahl allein ist noch keine Qualitätsaussage. *(PB §10; S. 3 und 29.)*

**Ergänzender Testhinweis aus den Folien:** Das Verhalten bei großen bzw. vielen Alben und, soweit die Anwendung das zulässt, parallelen Aufrufen untersuchen und dokumentieren. Die dort beispielhaft genannten 1.000 Fotos liegen bei uns innerhalb der bestätigten 10.000; Belastungs- und Grenzfälle deshalb zum eigenen Anforderungsstand passend wählen. Daraus entsteht keine neue Pflichtfunktion für parallele Bedienung. *(S. 23.)*

#### 3. KI-Evaluation und Abgabe

- [ ] **Evaluation Set für die entworfene KI-Funktion erstellen:** Auch bei nicht gebauter KI fünf konkrete Fälle: normal, mehrdeutig, sensibel, adversarial und fehlerhaft. Je Fall zwei bis drei prüfbare Kriterien sowie eine begründete Freigabequote festlegen. Falltabelle unter `eval/` versionieren und aus TESTING.md verlinken; als Ergebnisdatei gibt die Folie `eval/describe-photo/cases.md` vor. *(PB §10; Projektübung S. 27.)*
- [ ] **Durchführung ehrlich abgrenzen:** Bei vorhandener KI das Set wiederholt ausführen, variable Ausgaben/Fehlerkategorien und tatsächliche Ergebnisse bewerten; bei jeder Prompt- oder Modelländerung als Regression erneut ausführen. Bei reinem Entwurf Kriterien und geplantes Verfahren dokumentieren, keine Messungen erfinden. Bei einem vorhandenen Agenten auch Toolwahl/Argumente, Rechte, Freigaben, Injection/Datenabfluss, Aufruflimits, Timeout, Abbruch und Protokollierung testen. Beispielquoten aus den Folien sind kein verbindlicher Zielwert. *(S. 25-27.)*
- [ ] **KI-Testgenerierung kritisch auswerten:** Die Folien sehen Fall 4 in AI_USAGE.md vor: Tests aus einem AK generieren lassen und prüfen, ob die Assertions wirklich das Kriterium absichern. Ergebnis und begründete Entscheidung dokumentieren. *(S. 29; PB §13.)*
- [ ] **Checkpoint und Tag:** Strategie, zwei Blackbox-Ausarbeitungen, mindestens zehn Unit-/zwei Integrationstests, Traceability, Usability-Protokoll/Befunde, Security-Tests, Performance-Messung, Coverage-Report und Evaluation Set prüfen. Nach Review und grüner CI den Abschlussstand nach `main` übernehmen und `block-5` setzen. *(PB §3 und §10; S. 28-29.)*

**Quellen:** [Projektbeschreibung §3 und §10](docs/task_description/Projektbeschreibung_Photo_Album.md); [Block-5-Folien als Markdown](docs/lecture_slides/Block_5_Testen_Blackbox.md), [Original-PDF, 30 Seiten](docs/lecture_slides/Block_5_Testen_Blackbox.pdf).

### Block 6: Whitebox, Change Request und Refactoring

**Ziel:** Testlücken begründet schließen und eine fachliche Änderung kontrolliert analysieren, umsetzen und absichern. **Abschluss:** `block-6` und `v1.1.0`, nach der Hausaufgabe bis zu Block 7. Sammel-Issue: [#19](https://github.com/felix-henry/photo-album-projekt/issues/19).

#### 1. Coverage auswerten

- [ ] **Coverage je Schicht interpretieren:** JaCoCo-Report erzeugen und die Anweisungs-/Branch-Werte je Schicht in TESTING.md notieren. Konkrete Lücken erklären, besonders gelbe Zeilen und fehlende Zweige in der Domäne. Aussagekraft und Grenzen begründen; keine pauschale Zielquote aus den Lehrbeispielen ableiten. *(PB §10; S. 4-6 und 13.)*
- [ ] **Mindestens zwei Tests ergänzen:** Bisher fehlende Zweige gezielt mit fachlich passenden Eingaben absichern. Vorherige Lücke und Ergebnis nach den Tests dokumentieren. *(Hausaufgabe: S. 13.)*

#### 2. Change Request zuerst analysieren

Der konkrete **CR-1 „Fotos in mehreren Alben und Tags“** steht bereits in den bereitgestellten Block-6-Folien. Er gehört in Block 6 und wird nicht vorzeitig als Teil des anfänglichen Pflichtumfangs umgesetzt. Vor der Anwendung mit Ana den abweichenden Fotogrenzwert abgleichen; bis dahin bleibt unser bestätigter Ausgangsstand von 10.000 Fotos bestehen.

| CR | Inhalt des Arbeitsauftrags aus PDF-Seite 11 |
| --- | --- |
| CR-1.1 | Ein Foto kann gleichzeitig in mehreren Alben liegen; die Bilddatei wird nur einmal gespeichert. Titel und Beschreibung gehören zum Foto. |
| CR-1.2 | Entfernen aus einem Album verändert das Foto in anderen Alben nicht. Ohne Albumzuordnung kann es nach Bestätigung endgültig gelöscht werden. |
| CR-1.3 | Bis zu fünf Tags pro Foto; je Tag 1-20 Zeichen; je Foto ohne Beachtung der Groß-/Kleinschreibung eindeutig. Zuweisen und Entfernen sind möglich. |
| CR-1.4 | Fotos eines Albums nach einem Tag filtern; Trefferzahl in der Albumansicht zeigen. |

Die Folie bezeichnet Duplikatregel je Album und Zählung je Album als unverändert; sie nennt dabei **100** Fotos und pauschal weiter gültige bisherige AK. Betroffene eigene AK trotzdem einzeln auf Widersprüche prüfen. Die Folienangabe überschreibt unsere dokumentierten Entscheidungen nicht automatisch.

- [ ] **Mini-Impact-Analysis vor jeder Codeänderung:** Drei konkrete Antworten in [CHANGE_REQUEST.md](CHANGE_REQUEST.md): Welche vorhandene Anforderung mit ID widerspricht dem CR? Welche Klasse ändert sich am stärksten? Welcher bestehende Test wird rot, mit tatsächlichem Testnamen? *(Projektübung: S. 12.)*
- [ ] **Vollständige Impact Analysis fertigstellen:** Betroffene und neue Anforderungen/AK mit IDs, Architektur und Datenmodell, ungültig gewordene ADRs und neues ADR, rot werdende und neue Tests, Risiken, Aufwandsschätzung je Teil und Umsetzungsreihenfolge beschreiben. Analyse vor dem Implementierungsstand im Repository nachvollziehbar festhalten. *(PB §11; S. 13.)*
- [ ] **CR kontrolliert umsetzen:** Nach der Analyse Anforderungen, Entwurf, Speicher-/Datenmodell, ADRs und Tests mitführen. Bestehende Daten und mögliche Migration berücksichtigen. Regressionstests und neue Tests ausführen; bestehende Erwartungen nur bei begründeter fachlicher Änderung anpassen. *(PB §11; S. 11-13.)*
- [ ] **Rückblick festhalten:** Geschätzten und tatsächlichen Aufwand je Teil, Überraschungen und verbleibende Lücken gegenüberstellen. Eine unvollständige Umsetzung ehrlich benennen; Analyse und Testergebnisse müssen dennoch nachvollziehbar sein. *(PB §11.)*

#### 3. Refactoring und Abgabe

- [ ] **Begründetes Refactoring auswählen:** Ausgangszustand mit betroffener Klasse, relevanter Metrik und konkretem Smell samt Nachweis beschreiben. Möglichst eine Verbesserung wählen, die den CR erleichtert. Das ist eine Strukturverbesserung mit gleichem Verhalten. *(PB §12; S. 10.)*
- [ ] **Kleine abgesicherte Schritte durchführen:** Vorhandenes Verhalten mit grünen Tests fixieren, kleine Änderung vornehmen, Tests ausführen, Diff auf unverändertes Verhalten prüfen und separat committen. Bei fehlenden Tests zunächst das relevante Verhalten absichern. Fachliche CR-Änderungen in eigene Commits trennen. *(PB §12; S. 10.)*
- [ ] **Refactoring dokumentieren:** In [docs/REFACTORING.md](docs/REFACTORING.md) Ausgangssituation, Problem, Schritte mit grünen Tests, Ergebnis und Testabsicherung festhalten. Der Ablageort folgt der Projektbeschreibung, auch wenn die Folie nur den Dateinamen nennt. *(PB §12 und §15; S. 13.)*
- [ ] **KI-Nutzung prüfen:** Ein tatsächlicher substanzieller Einsatz etwa bei Impact Analysis, Refactoring oder Review kann als weiterer Fall in AI_USAGE.md dokumentiert werden. PB §13 empfiehlt einen Fall aus jedem Block 2-6; ein fünfter Fall ist keine zusätzliche Mindestquote pro Block.
- [ ] **Präsentations-Storyboard vorbereiten:** Noch in der Nacharbeit zu Block 6 ein Storyboard mit höchstens acht Folien erstellen. Anforderungen, Entscheidung, Tests, Änderung und Reflexion sollen eine nachvollziehbare Geschichte bilden. *(Hausaufgabe: S. 13; PB §16.2.)*
- [ ] **Release und Blocktag erstellen:** Neue und bestehende Tests sind grün; Dokumente und CHANGELOG.md passen zum implementierten CR. Den Stand nach Review nach `main` übernehmen, `v1.1.0` taggen und pushen und nach Abschluss der Hausaufgabe `block-6` setzen. `v1.0.0` muss den vollständigen ursprünglichen Pflichtumfang nachvollziehbar kennzeichnen. *(PB §3, §9 und §11; S. 11 und 13.)*

**Quellen:** [Projektbeschreibung §10-13 und §16](docs/task_description/Projektbeschreibung_Photo_Album.md); [Block-6-Folien als Markdown](docs/lecture_slides/Block_6_Whitebox_Wartung_Change_Request_final.md), [Original-PDF, 14 Seiten](docs/lecture_slides/Block_6_Whitebox_Wartung_Change_Request_final.pdf).

### Block 7: Risiken, Release-Entscheidung und Präsentation

**Ziel:** Belegt über die Freigabe entscheiden und das Gesamtprojekt erklären können. **Abschluss:** Präsentation in Block 7 und Tag `block-7`; finaler Repository-Stand nach Block 7 mit `final`, spätestens **23. November**. Sammel-Issue: [#20](https://github.com/felix-henry/photo-album-projekt/issues/20).

**Quellenlage:** Für Block 7 liegen noch keine eigenen Folien im Repository. Grundlage sind die Projektbeschreibung und bereits belegte Ergänzungen aus früheren Blöcken. Weitere projektbezogene Arbeitsaufträge werden nach Bereitstellung abgeglichen.

#### 1. Risikoanalyse und Release-Entscheidung

- [ ] **Mindestens drei Risiken bewerten:** In [RISK_ANALYSIS.md](RISK_ANALYSIS.md) mindestens ein Produkt- und ein Projektrisiko berücksichtigen; Eintrittswahrscheinlichkeit und Auswirkung begründen und in einer Risikomatrix einordnen. *(PB §14.)*
- [ ] **Maßnahmen und Verantwortliche festlegen:** Für die Risikoreduktion konkrete Maßnahmen und zuständige Personen nennen; mit tatsächlichen Problemen und Nachweisen aus Tests, Änderungen und Teamprozess verbinden. *(PB §14.)*
- [ ] **Vier bis sechs Quality Gates bewerten:** Je Gate ein prüfbares Kriterium, tatsächlichen Beleg und Status erfüllt/nicht erfüllt festhalten. Bekannte Schwächen und fehlende Nachweise sichtbar lassen. *(PB §14.)*
- [ ] **Rollout und KI-Governance beschreiben:** Strategie, nötige Migration und Rollback planen; im KI-Governance-Abschnitt den tatsächlichen bzw. vorgesehenen Einsatz und seine Grenzen behandeln. Ein produktives Cloud-Deployment ist dafür nicht vorgeschrieben. *(PB §6 und §14.)*
- [ ] **Release-Entscheidung begründen:** „Ja“, „nein“ oder „unter Bedingungen“ mit Bezug auf Matrix, Gates und verbleibende Risiken. Eine begründete Ablehnung wird nicht schlechter bewertet als eine Zustimmung. *(PB §14.)*

#### 2. Abschlusspräsentation

- [ ] **Format einhalten:** Zehn Minuten Präsentation mit hartem Stopp, anschließend fünf Minuten Diskussion; höchstens acht Folien und eine gezielte Demo von höchstens 90 Sekunden. Für unser bestätigtes Fünferteam sprechen alle fünf substanziell. *(PB §16.2; bestätigte Teamgröße.)*
- [ ] **Die sechs Leitfragen beantworten:** Veränderung der Anforderungen; wichtige Architekturentscheidung samt Grund; interessanter hergeleiteter Testfall; Auswirkungen des CR; KI-Einsatz und verworfener Vorschlag; heutige Release-Entscheidung. Mindestens ein Diagramm erklärt eine Entscheidung. *(PB §16.2.)*
- [ ] **Gemeinsam proben:** Zeit und Demo prüfen, Alternativen und Schwächen erklären können. Jede Person kann Anforderungen, Architektur und Qualitätsmaßnahmen des Gesamtprojekts beantworten. *(PB §16.2.)*
- [ ] **Präsentieren und PDF ablegen:** Präsentation in Block 7 halten und die endgültige PDF unter [docs/presentation/](docs/presentation/) versionieren. *(PB §15-16.)*

#### 3. Finale Repository-Prüfung

- [ ] **README vollständig:** Zweck, Team, tatsächlich verwendete Versionen sowie reproduzierbare Build- und Startanleitung. Links und Speicherformat-Dokumentation passen zum abgegebenen Stand. *(PB §6 und §15.)*
- [ ] **Fachliche Dokumente vollständig:** [REQUIREMENTS.md](REQUIREMENTS.md), [ARCHITECTURE.md](ARCHITECTURE.md) mit [ADRs](docs/adr/) und [Diagrammen](docs/design/), [TESTING.md](TESTING.md), [CHANGE_REQUEST.md](CHANGE_REQUEST.md), [docs/REFACTORING.md](docs/REFACTORING.md), [AI_USAGE.md](AI_USAGE.md) und [RISK_ANALYSIS.md](RISK_ANALYSIS.md). Zusätzlich die in Block 2 benannte Backlog-Datei `Issues.md` berücksichtigen, solange keine andere Ablage bestätigt ist. Keine leeren Pflichtdokumente; Inhalte und Verweise entsprechen dem tatsächlichen Ergebnis. *(PB §15 und §18; Block 2 S. 17.)*
- [ ] **Implementierung und Konfiguration vollständig:** Quellcode, Tests, Build-Konfiguration samt Wrapper, CI-Konfiguration, gemeinsame `AGENTS.md` und [CHANGELOG.md](CHANGELOG.md) sind versioniert. Tatsächlich verwendete projektbezogene Prompts, Skills und Modellkonfigurationen liegen nachvollziehbar vor. *(PB §9 und §15.)*
- [ ] **KI-Nachweise prüfen:** Insgesamt mindestens drei substanzielle Fälle, empfohlen fünf über Block 2-6; Pflichtangaben vollständig, Prompts/Skills versioniert und mindestens ein tatsächlicher Vorschlag begründet verworfen. Keine Protokollpflicht für jede kleine KI-Aktion. *(PB §13.)*
- [ ] **Teamprozess und Beiträge prüfen:** Nachvollziehbare substanzielle Beiträge aller Teammitglieder, Issues/PRs mit Reviews, mindestens ein dokumentiertes Code Review je Person und grüne CI. Nachträgliche Reviews älterer PRs als spätere Prüfung kennzeichnen; sie sind kein rückwirkender Beleg für ein Review vor dem Merge. *(PB §3, §9 und §16-18.)*
- [ ] **Tags und endgültige Abgabe prüfen:** `block-1` bis `block-6`, `v0.1.0`, `v1.0.0`, `v1.1.0` und `final` gemäß Projektbeschreibung sowie zusätzlich `block-7` gemäß Block-4-Folie 23 auf GitHub bereitstellen. Tags zeigen auf die passenden Stände. Finale Änderungen über PR/Review übernehmen und `final` spätestens am 23. November setzen und pushen; `block-7` ersetzt `final` nicht. *(PB §3, §9 und §15; ergänzende Vorgabe Block 4 S. 23.)*

**Quellen:** [Projektbeschreibung §13-18](docs/task_description/Projektbeschreibung_Photo_Album.md), [Original-PDF S. 5-7](docs/task_description/Projektbeschreibung_Photo_Album.pdf#page=5).

## Übergreifende Regeln und Termine

- **Zwischenstände:** PB §3 schlägt die Blocktags jeweils für den Vorabend des Folgeblocks vor; die Checkpoint-/Hausaufgabenfolien konkretisieren diese Nacharbeit. Die Tagstände werden bewertet. Spätere Verbesserungen zählen zur Endbewertung, wenn sie bis `final` vorliegen. Die verbindliche Endabgabe ist **23. November**; konkrete Termine der einzelnen Lehrblöcke stehen in den vorliegenden Quellen nicht vollständig.
- **Git-Prozess:** Bestehendes passendes Sammel-Issue verwenden, zusammengehörige Aufgaben im Board verknüpfen, auf dem Arbeitsbranch bearbeiten und über PR/Review nach `main` übernehmen. Für unser Repository gelten vier Freigaben durch die anderen Teammitglieder; nach Einrichtung der CI kommen die erfolgreichen Pflichtprüfungen dazu. KI-unterstützte PRs tragen `ai-assisted`. Die Review-Aufträge zwischen Partnerteams aus Block 2 und 3 sind zusätzlich ausdrücklich als Issues vorgesehen.
- **Checkpoints in Block 2-5:** Die Folien verlangen eine Selbsteinschätzung je Checkpoint-Punkt: grün = im Repository sichtbar, gelb = begonnen, rot = fehlt. Für jeden gelben/roten Punkt ein Issue mit Verantwortlichem sicherstellen. Diese Aufgabe ist vom bloßen Erstellen eines Issues je kleinem Commit zu unterscheiden. *(Block 2 S. 54; Block 3 S. 51; Block 4 S. 30; Block 5 S. 28.)*
- **Versions-Tags:** `v0.1.0` nach Block 4; `v1.0.0` für den vollständigen ursprünglichen Pflichtumfang; `v1.1.0` nach dem CR. Release-Tags und Blocktags erfüllen unterschiedliche Zwecke und dürfen denselben passenden Commit kennzeichnen. Beispielversionen wie `v0.2.0` oder Release Candidates aus den Folien sind keine zusätzlichen Pflichtabgaben.
- **KI-Dokumentation:** PB §13 verlangt mindestens drei substanzielle Fälle insgesamt und empfiehlt fünf mit je einem aus Block 2-6. Die Folien geben zusätzlich vier konkrete Arbeitsaufträge als Fälle 1-4 vor: Grill me, Entwurfsreview, Coding und Testgenerierung. Diese Aufgaben sind bei den jeweiligen Blöcken aufgeführt; die allgemeine Mindestquote ersetzt sie nicht. Dokumentiert werden echte Ergebnisse und Entscheidungen, keine künstlichen Zusatzfälle. Drei Fälle je Block sind nicht gefordert.
- **Bestätigter Projektstand:** Fünferteam „Commitment Issues“, Kanban und 10.000 Fotos je Album. Die Aufgabenverteilung liegt im Kanban-Board; Fortschritt ist dort und in GitHub-Issues sichtbar. Abstimmung über Issues/PRs, optional WhatsApp für schnelle Absprachen. Fachliche Beispielwerte oder technische Lösungen der Folien ersetzen diese Festlegungen nicht. Weitere Regeln werden in REQUIREMENTS.md als Auftraggeber-Entscheidung, begründete Annahme oder offen festgehalten.

## Quellenstand und offene Auslegungen

Stand des Abgleichs: **23.10.2026**. Ausgewertet wurden die Projektbeschreibung und die Originalfolien zu Block 1-6, einschließlich Diagrammen, farbigen Hinweisen, Checkpoints und projektbezogenen Hausaufgaben. Seitenangaben in dieser Roadmap beziehen sich auf die **PDF-Seiten ab 1**, nicht auf abweichende gedruckte Foliennummern. Die Markdown-Fassungen dienen zum Nachlesen.

| Stelle | Unterschied oder fehlende Grundlage | Umgang im Arbeitsplan |
| --- | --- | --- |
| PB §3/§7 und Block 2, S. 13/16 | Insgesamt 6-10 FA gegenüber zunächst drei abgeleiteten FA. | Drei sind ein Zwischenschritt innerhalb des Gesamtbestands; unser Ziel bleibt zehn FA und fünf NFA. |
| Block 2 S. 17 | Die Folie benennt User Stories im Backlog und `Issues.md`; die Projektbeschreibung nennt eine Mindestliste an Dokumenten. | Als ergänzende Vorgabe aufnehmen. Board/GitHub-Issues verknüpfen; eine andere Ablage ersetzt die benannte Datei erst nach Bestätigung. |
| Block 2/3/5/6, verschiedene Beispiele | Unter anderem 100 Fotos, 50 Namenszeichen, 100/500 Titel-/Beschreibungszeichen oder 20 MB. | Unsere Albumgrenze bleibt 10.000. Andere Werte nur nach tatsächlicher Klärung bzw. begründeter Annahme übernehmen. Das gilt auch für „Auftraggeber-Entscheidung“ genannte Regeln innerhalb eines Beispiels, die nicht unseren Gesprächsstand wiedergeben. |
| Block 2 S. 41 und Block 3 S. 50 | Unterschiedliche Partnerzuordnungen für die Reviews. | Das Review mit der tatsächlich im Unterricht vereinbarten Partnergruppe durchführen; keine Gruppennummer erfinden. |
| PB §3 gegenüber Block 4 S. 3/30/31 | Meilenstein verlangt Pflichtfunktionalität, Unterrichtscheckpoint zunächst nur Album erstellen/anzeigen. | Kern im Unterricht; vollständiger Pflichtumfang als Nacharbeit bis Block 5. |
| PB §6 und Block 4 S. 20/21 | PB verlangt CI bei jedem PR, Folie 20 zusätzlich bei jeder Änderung auf jedem Branch. Das Minimal-YAML deckt nur Pushes auf `main` und PRs ab. | Die ergänzende Vorgabe erfüllen: alle Branch-Pushes und PRs prüfen. Das Minimalbeispiel entsprechend erweitern. |
| Block 4 S. 22 gegenüber S. 31 | Dependabot als Projektmaßnahme genannt, in der Hausaufgabe ausdrücklich optional. | Abweichung offenhalten; Dependabot nicht als eindeutig verpflichtende Abgabe ausgeben. Secrets-Regel und bewusste Dependency-Verwaltung aufnehmen; SpotBugs ist ausdrücklich optional. |
| PB §10 gegenüber PB §3 und Block 5 S. 19/28 | Fließtext mindestens ein Blackbox-Verfahren, empfohlen zwei; Meilenstein und Lab verlangen zwei. | Zwei dokumentierte Ausarbeitungen vorsehen, wie bereits im Team vereinbart. |
| PB §10/Meilenstein gegenüber Block 5 S. 27/28 | Eval-Set gefordert, im Checkpoint zugleich „optional“ genannt. | Set auch für eine nur entworfene KI-Funktion erstellen; tatsächliche Ausführung hängt von einer vorhandenen Implementierung ab. |
| PB §11 und Block 6 S. 11 | CR wird in Block 6 bekannt gegeben; konkrete Folie liegt bereits vor und nennt die abweichende Albumgrenze 100. | Inhalt für Block 6 einplanen, vor Anwendung mit unserem Requirements-Stand und Ana abgleichen. Keine vorzeitige Erweiterung des Anfangsumfangs. |
| PB §15 und Block 4 S. 23 | Die Mindestliste nennt `block-1` bis `block-6` und `final`; die Folie ergänzt ausdrücklich `block-7`. | Beide Vorgaben erfüllen: `block-7` zusätzlich zu den Tags aus der Projektbeschreibung vorsehen. `final` bleibt die verbindliche Endabgabe. |
| Folien Block 7 | Noch nicht vorhanden. | Nach Bereitstellung abgleichen; bis dahin nach Projektbeschreibung und den bereits vorliegenden Ergänzungen aus früheren Blöcken planen. |
| Moodle | Verbindliche Sprechstundenantworten und zusätzliche Projektchecklisten/Vorlagen liegen nicht vollständig im Repository. | Tatsächliche Antworten und Unterrichtsergänzungen prüfen, bevor davon abhängige Punkte abgeschlossen werden. |

Neue verbindliche Antworten von Ana, weitere Folien und Änderungen im Unterricht werden mit Quelle beim betroffenen Block ergänzt.
