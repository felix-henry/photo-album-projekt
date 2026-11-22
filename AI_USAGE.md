# AI_USAGE – Einsatz von KI-Werkzeugen

## Fall 1 – Offene Fachfragen klären („Grill me“), Block 2

| Feld | Inhalt |
| --- | --- |
| **Aufgabe/Ziel** | Die offenen Fragen in [Abschnitt 3.2](REQUIREMENTS.md#32-annahmen-und-offene-fragen) klären und prüfen, welche Anforderungen, Use Cases und Akzeptanzkriterien dadurch angepasst werden müssen. |
| **Werkzeug/Modell** | Codex (Desktop-App), GPT-6.1 Sol und GrillMe. Grundlage waren die Requirements und die Projektbeschreibung. [Verwendeter Prompt](#prompt-zu-fall-1) |
| **Ergebnis** | Die KI stellte Rückfragen und verglich Varianten anhand konkreter Fälle. Dazu gehörten umbenannte Bilddateien, Konflikte beim Wiederherstellen und Speicherfehler nach einer Teiländerung. Die Entscheidungen stehen in [Abschnitt 3.2](REQUIREMENTS.md#32-annahmen-und-offene-fragen). |
| **Kritische Bewertung** | Eine umbenannte Kopie prüft, ob der Dateiname die Duplikaterkennung beeinflusst. Der Vorschlag, die Bilddaten beim Entfernen aus dem letzten Album sofort zu löschen, vermeidet unzugeordnete Dateien. Ein versehentlich entferntes Foto ließe sich dann jedoch nicht wiederherstellen. |
| **Entscheidung** | **Übernommen:** Bytegleicher Dateiinhalt gilt als Duplikatkriterium und erfasst auch umbenannte Kopien. **Angepasst:** Bei einem Fehler wird die betroffene reversible Aktion zurückgesetzt. Ein Import betrifft eine Datei, eine Albumaktion das ganze Album samt Fotos (A-11). Endgültiges Löschen wird pro Foto ausgewertet (A-12). **Verworfen:** Die App-Kopie beim Entfernen aus dem letzten Album sofort löschen. Wir verschieben sie in den Papierkorb, damit sich versehentlich entfernte Fotos wiederherstellen lassen (A-07). |

### Prompt zu Fall 1

```text
Hilf mir als kritischer Gesprächspartner, die offenen Fragen zum Photo Album in Abschnitt 3.2 der REQUIREMENTS.md zu klären. Lies dafür REQUIREMENTS.md und docs/task_description/Projektbeschreibung_Photo_Album.md.

1. Gehe die offenen Punkte einzeln durch. Nenne die ID, stelle eine Frage und warte auf meine Antwort. Frage nach, wenn eine Antwort einen wichtigen Grenzfall offenlässt.
2. Unterscheide Vorgaben aus der Projektbeschreibung, belegte Antworten der Auftraggeberin, Teamannahmen und offene Fragen. Kennzeichne deine Vorschläge. Frage bei Zahlen und Grenzwerten nach ihrer Quelle oder Begründung.
3. Zeige bei Bedarf eine konkrete Alternative und erkläre kurz die Folgen. Ziehe bestehende FA, NFA, AK und Use Cases heran, wenn eine Antwort sie betrifft. Die fachliche Entscheidung trifft das Team.
4. Halte bei fehlenden Angaben oder später fälligen Entscheidungen fest, was offen bleibt und wer es klärt, soweit bekannt.
5. Fasse am Ende pro Punkt die Entscheidung, ihre Begründung und die betroffenen IDs zusammen. Halte fest, welche Vorschläge ich übernommen, angepasst oder verworfen habe.

Beginne mit A-01 (Duplikate).
```

## Fall 2 – Architekturreview, Block 3

| Feld | Inhalt |
| --- | --- |
| **Aufgabe/Ziel** | Das Klassendiagramm aus Block 3 auf klare Verantwortlichkeiten und Abhängigkeiten prüfen lassen. Einen Vorschlag übernehmen und einen verwerfen, jeweils begründet. |
| **Werkzeug/Modell und Prompt oder Skill** | Claude Code mit Claude Opus 5 in einer neuen Sitzung ohne Vorwissen zum Entwurf. Grundlage waren [Klassendiagramm](docs/design/klassendiagramm.puml), [CRC-Karten](docs/design/crc-karten.md), ARCHITECTURE.md und REQUIREMENTS.md. [Verwendeter Prompt](#prompt-zu-fall-2) |
| **Ergebnis** | Fünf Befunde: (1) In den Use Cases fehlen Abhängigkeiten zu `Photo` und `Album`, `Photo` hat keinen Getter für seine ID und `AlbumLibrary` keinen Konstruktor. `Result` ist nicht erklärt. (2) Über das öffentliche `Album.rename` lässt sich die Namensprüfung in `AlbumLibrary` umgehen. (3) Für Löschzeitpunkt und Ursprungsalbum gibt es im Papierkorb keinen Platz. (4) Die Regeln für das Wiederherstellen sind nicht erreichbar. (5) Die Dateiregel A-03 liegt im Adapter, und der Name `ImageHasher` passt nicht zur Prüfaufgabe. |
| **Kritische Bewertung** | Die Verweise auf AK-03.3, AK-04.6 und AK-09.5 stimmen. Befund 1 deckt einen echten Widerspruch auf: Das Sequenzdiagramm übergibt die ID des Fotos an `PhotoStorage`, das Klassendiagramm bot dafür aber keine Methode. Die Befunde 2 bis 4 sind durch A-02, A-07 und A-09 belegt. Befund 5 ist eine Auslegungsfrage. Die KI gibt selbst an, die Vorgabe strenger auszulegen, und nennt unsere Begründung schlüssig. |
| **Entscheidung** | **Übernommen:** Befund 1 mit den fehlenden Pfeilen, `getId()`, dem Konstruktor von `AlbumLibrary` und einem Satz zu `Result`. Befund 2 mit paketsichtbarem `Album.rename`. Befund 3 mit der neuen Klasse `TrashEntry` und der Multiplizität `0..1` auf der Albumseite. Befund 4 in der vorgeschlagenen Variante: `Trash` prüft nur die Frist und gibt den Eintrag heraus, die Zielregeln prüfen `AlbumLibrary` und `Album`. **Verworfen:** Befund 5. Die Folien aus Block 3 weisen `ImageHasher` im CRC-Beispiel ausdrücklich die Prüfung von Dateityp und Größe zu. Die Prüfung braucht eine Bildbibliothek und gehört deshalb in die Infrastruktur. NFA-05 zählt die Dateiregel nicht zu den Regelgruppen, die ohne Dateizugriff testbar sein müssen. |

### Prompt zu Fall 2

```text
Prüfe als kritischer Reviewer das Klassendiagramm des Photo Albums. Lies dafür docs/design/klassendiagramm.puml, docs/design/crc-karten.md, ARCHITECTURE.md und REQUIREMENTS.md. Ändere keine Dateien.

1. Regel-Check: Wo wird jede Geschäftsregel aus REQUIREMENTS.md geprüft? Fehlt eine Regel oder liegt sie außerhalb der Domäne?
2. Verantwortlichkeiten: Hat eine Klasse zu viele Aufgaben oder eine Aufgabe, die nicht zu ihr passt? Stimmen Diagramm und CRC-Karten überein?
3. Abhängigkeiten: Zeigen alle Abhängigkeiten nach innen zur Domäne? Welche Klasse hat die meisten Abhängigkeiten und ist das begründet?
4. Multiplizitäten und Methoden: Passen sie zu den Anforderungen und Use Cases? Fehlt eine Methode, die „Foto hinzufügen“ oder „Album erstellen“ braucht?

Nenne höchstens fünf Befunde, die wichtigsten zuerst. Gib zu jedem Befund die Stelle im Diagramm, die betroffene Anforderung, das Problem und einen konkreten Verbesserungsvorschlag an. Kennzeichne, wenn du dir unsicher bist oder eine Anforderung anders auslegst. Die Entscheidung über die Vorschläge treffe ich.
```

## Fall 3 – Coding Agent bei der Implementierung, Block 4

| Feld | Inhalt |
| --- | --- |
| **Aufgabe/Ziel** | Block 4 mit einem Coding Agent umsetzen: Build und CI einrichten und die Pflichtfunktionalität samt Papierkorb in kleinen, geprüften Schritten nach der Roadmap implementieren. Der Agent analysiert den Stand, schlägt Code, Tests und Dokumentation vor und prüft jeden Schritt. Änderungen am Repository und alle Git-Schritte führt der Blockbearbeiter selbst aus. |
| **Werkzeug/Modell und Prompt oder Skill** | Claude Code in der VS-Code-Erweiterung mit Claude Opus 5.5, Sitzungen vom 12. bis 18.11.2026. Kontext waren Projektbeschreibung, ROADMAP, REQUIREMENTS.md, ARCHITECTURE.md, die ADRs, die CI-Konfiguration und das Sammel-Issue #26, ab dem 15.11. auch die gemeinsame [AGENTS.md](AGENTS.md). [Arbeitsauftrag und Regeln](#prompt-zu-fall-3) |
| **Ergebnis** | Mehr als 60 Commits auf `block-4` mit grüner CI: Maven-Wrapper, `pom.xml`, CI und Dependabot, die Use Cases für Alben, Fotos, Fototexte und Papierkorb sowie die Swing-Oberfläche. Dazu 214 automatisierte Tests mit Bezug zu den Akzeptanzkriterien und 62 manuelle Testfälle für die Oberfläche. In der Dokumentation entstanden ADR-000 und ADR-003, Ergänzungen in ADR-002 und REQUIREMENTS (A-03, A-08) sowie das Speicherformat und die Abweichungen vom Entwurf in ARCHITECTURE. |
| **Kritische Bewertung** | Jeder Vorschlag wurde vor dem Übernehmen gelesen, gebaut und gegen REQUIREMENTS und ADRs geprüft. Dabei und im Review fielen vier Arten von Fehlern auf. (1) **Umgebung statt Vorgabe:** Der Agent übernahm zunächst das lokal installierte JDK 25 und formulierte in ADR-000 „JDK ab Version 21“, obwohl die Projektbeschreibung Java 21 festlegt. Erkannt hat das ein Teammitglied beim Build-Check. (2) **Tests, die trotz falschen Verhaltens grün bleiben:** Ein Test-Fake gab dieselben `Photo`-Objekte zurück, statt wie die echte Speicherung Kopien zu halten, und eine vorgeschlagene Prüfung war immer wahr. Beides fiel beim Lesen der Tests auf, ein grüner Build hätte es nicht gezeigt. (3) **Lücken in den Anforderungen:** Die Rundung der Resttage, der Schutz vor sehr großen Bildern und die Vorschaubilder aus dem Klickprototyp waren nicht eindeutig geregelt. Der Agent erkannte diese Punkte und legte Optionen vor, statt still eine Annahme zu treffen. (4) **Fehler, die erst das Review fand:** Nach einem Speicherfehler beim endgültigen Löschen konnte ein Foto ohne Bilddatei wiederhergestellt werden, und eine Albumdatei mit fremder ID wurde gelesen. Weder der Agent noch die Tests hatten das erkannt. Felix und Alanur fanden es im Review von PR #35, die Korrekturen sind dort mit Regressionstests eingearbeitet. Erfundene APIs oder Bibliotheken sind nicht aufgefallen. Alle verwendeten Klassen stammen aus dem JDK, JUnit 5 oder Gson und sind durch Build und Tests belegt. |
| **Entscheidung** | **Übernommen:** die geprüften Vorschläge für Code, Tests und Dokumentation sowie die Teamentscheidungen nach vorgelegten Optionen: Pixelgrenze und Kopfprüfung großer Dateien (ADR-002, A-03), aufgerundete Resttage (A-08) und Einzelansicht ohne Vorschaubilder (ARCHITECTURE, Abschnitt 5). **Angepasst:** Die Test-Fakes halten jetzt Kopien wie die echte Speicherung. ADR-000 und die Anleitung zum Build-Check nennen JDK 21, die Version wird seitdem mit `mvnw -v` geprüft und steht als Verbot in AGENTS.md. **Verworfen:** „JDK ab Version 21“, weil PB §6 Java 21 vorgibt und die CI mit Temurin 21 baut, sowie die immer wahre Testprüfung, ersetzt durch eine Prüfung des gespeicherten Werts. **Bewusst offen gelassen:** Doppelungen in den Use Cases, die der Agent beim Prüfen fand, sind als Kandidat für das Refactoring in Block 6 vorgemerkt. |

### Prompt zu Fall 3

Arbeitsauftrag, zusammengefasst aus den Anweisungen in den Sitzungen:

```text
Ich bearbeite Block 4 (Issue #26). Führe mich Schritt für Schritt durch die Punkte der ROADMAP. Prüfe vor jedem Schritt Projektbeschreibung, ROADMAP, REQUIREMENTS.md, ARCHITECTURE.md, die ADRs und die CI-Konfiguration und melde Abweichungen. Schlage für jeden Schritt vollständige Dateien mit Tests vor und nenne die erwartete Testanzahl. Prüfe das Ergebnis danach anhand von Diff und Testberichten. Lege offene Fachfragen als Optionen mit Empfehlung vor. Die Entscheidung treffe ich oder das Team.
```

Dauerhafte Regeln aus der lokalen `CLAUDE.md`, die zusätzlich die gemeinsame [AGENTS.md](AGENTS.md) lädt:

```text
- Nur lesen und analysieren. Keine Git-Schreibaktionen (commit, push, pull, merge, branch, tag, PR, Kommentare) und keine Dateien im Projekt anlegen oder ändern. Git und Dateien mache ich selbst nach Anleitung.
- Keine Befehle ausführen, die ins Projekt schreiben.
- Kleinschrittig anleiten: vollständige Dateien zum Ersetzen, danach .\mvnw.cmd verify mit erwarteter Testanzahl. Ergebnisse nach jedem Schritt selbst prüfen.
- Vor jeder Anleitung gegen Projektbeschreibung, ROADMAP, CI und Teamabsprachen in #26 prüfen und Abweichungen selbst melden. Die JDK-Version mit .\mvnw.cmd -v prüfen.
```

## Fall 4 – Tests aus einem Akzeptanzkriterium ableiten, Block 5

| Feld | Inhalt |
| --- | --- |
| **Aufgabe/Ziel** | Aus einem Akzeptanzkriterium Tests generieren lassen und prüfen, ob die Assertions das geforderte Verhalten absichern. Gewählt ist AK-SR01.4: Beim endgültigen Löschen einer Albumkachel mit drei Fotos scheitert eines; zwei Fotos und ihre App-Kopien sind danach gelöscht, das dritte bleibt in der Kachel, und das System meldet den Teilerfolg. |
| **Werkzeug/Modell und Prompt oder Skill** | Claude Code (Desktop-App) mit Claude Opus 5.5 am 18.11.2026. Die Tests erzeugte ein eigener Agent ohne Projektkontext, der nur den Use Case, die Fakes und die Domänenklassen lesen durfte, den vorhandenen Test aber nicht. [Prompt](#prompt-zu-fall-4) |
| **Ergebnis** | Eine Testklasse mit einem Test nach Arrange–Act–Assert. Sie kompilierte ohne Änderung und lief grün. Geprüft werden die verbleibende App-Kopie, ein Eintrag im Papierkorb mit derselben ID und nur dem gescheiterten Foto, `isSuccess() == false` und der genaue Meldungstext. |
| **Kritische Bewertung** | Die Assertions decken drei der vier Teile des Kriteriums ab: gelöschte App-Kopien, verbleibendes Foto und Meldung. Die Zusage „bleibt in der Kachel“ prüft der Test nicht. Um das zu belegen, wurde in `Trash.remove` probeweise ein Fehler eingebaut, der aus der Kachel einen Einzelfoto-Eintrag macht. Der KI-Test blieb dabei grün, ebenso der vorhandene Test `DeletePermanentlyUseCaseTest.reportsPartialSuccessForTile`; nur `TrashTest` schlug an, und zwar über den Objektvergleich, nicht über eine gezielte Prüfung. Außerdem doppelt der KI-Test den vorhandenen Test fast genau. Die Prüfung `isSuccess() == false` hält eine Umsetzungsentscheidung fest, die das Kriterium nicht verlangt. Erfundene Klassen oder Methoden kamen nicht vor. |
| **Entscheidung** | **Verworfen:** die generierte Testklasse, weil sie den vorhandenen Test doppelt. **Übernommen und angepasst:** die Erkenntnis aus der Prüfung. `reportsPartialSuccessForTile` prüft jetzt zusätzlich, dass der verbleibende Eintrag eine Kachel ist. Mit dem probeweise eingebauten Fehler schlägt er an, mit dem echten Code ist er grün. |

### Prompt zu Fall 4

```text
Du sollst nur lesen und Code als Text zurückgeben. Lege keine Dateien an und ändere keine. Führe keine Befehle aus, die schreiben.

Projekt: Java-21-Fotoalbum unter <Projektordner> (Maven, JUnit 5).

Akzeptanzkriterium AK-SR01.4:
- Ausgangslage: Eine Albumkachel im Papierkorb enthält drei Fotos. Beim endgültigen Löschen schlägt der Zugriff auf eines davon fehl.
- Aktion: Der Nutzer bestätigt das endgültige Löschen der Kachel.
- Erwartetes Ergebnis: Zwei Fotos und ihre App-Kopien sind gelöscht. Das betroffene Foto bleibt in der Kachel, deren Anzahl auf eins sinkt. Das System meldet den Teilerfolg.

Lies dazu nur:
- src/main/java/de/commitmentissues/photoalbum/application/DeletePermanentlyUseCase.java
- src/test/java/de/commitmentissues/photoalbum/application/InMemoryTrashRepository.java
- src/test/java/de/commitmentissues/photoalbum/application/InMemoryPhotoStorage.java
- src/test/java/de/commitmentissues/photoalbum/application/InMemoryAlbumRepository.java
- die Domänenklassen, die du für die Testdaten brauchst (src/main/java/de/commitmentissues/photoalbum/domain/)

Lies NICHT die vorhandene Datei DeletePermanentlyUseCaseTest.java.

Aufgabe: Schreibe eine vollständige JUnit-5-Testklasse `DeletePermanentlyPartialSuccessTest` im Paket de.commitmentissues.photoalbum.application, die AK-SR01.4 mit den vorhandenen Fakes prüft. Nach Arrange–Act–Assert, mit @DisplayName, das mit „AK-SR01.4:“ beginnt. Gib nur die Java-Datei als Codeblock zurück, danach höchstens drei Sätze, welche Annahmen du getroffen hast.
```

## Fall 5 – Refactoring prüfen, Block 6

| Feld | Inhalt |
| --- | --- |
| **Aufgabe/Ziel** | Einen KI-Vorschlag für ein Refactoring prüfen und anhand des Diffs und der Tests bewerten, ob das Verhalten erhalten bleibt. Der Agent sollte eine Stelle mit Smell und Metrik auswählen, die den Change Request CR-1 erleichtert, sie in kleinen Schritten umbauen und das Ergebnis in [docs/REFACTORING.md](docs/REFACTORING.md) dokumentieren. |
| **Werkzeug/Modell und Prompt oder Skill** | Claude Code in der Desktop-App mit Claude Opus 5.5 am 22.11.2026. Anders als in Fall 3 hat der Agent in dieser Sitzung selbst gebaut, committet und auf `block-6` gepusht. Kontext waren ROADMAP Block 6, AGENTS.md, die Impact Analysis in CHANGE_REQUEST.md und der Code auf `block-6`. [Arbeitsauftrag](#prompt-zu-fall-5) |
| **Ergebnis** | Der Agent fand den Smell *Duplicated Code*: denselben Ladeblock in fünf Use Cases und dieselbe Foto-Suche an drei Stellen. Er baute in zwei Commits um (`2b0a107` `AlbumLookup.load`, `8afd7d1` `Album.findPhoto`), ließ nach jedem Schritt `.\mvnw.cmd verify` laufen (234 Tests grün) und schrieb REFACTORING.md mit Messwerten vorher und nachher. Tests musste er nicht ändern. |
| **Kritische Bewertung** | (1) **Verhaltensabweichung im Diff:** In Schritt 2 vergleicht die Suche jetzt `PhotoId` statt `UUID`. Eine `null`-Foto-ID führt dadurch zu einer `NullPointerException` statt zur Meldung „Das Foto wurde nicht gefunden.“. Das fiel erst bei der Diff-Prüfung über den Konstruktor von `PhotoId` auf, nicht durch die Tests: Kein Test übergibt `null`. Grüne Tests allein belegen die Verhaltensneutralität also nicht. (2) **Falsche Aussagen im Entwurf der Dokumentation:** Der Agent schrieb zuerst, im Paket seien 25 Zeilen weggefallen, und `RenameAlbumUseCase` lade über `AlbumLibrary`. Der Abgleich mit `git diff --shortstat` und dem Quelltext zeigte: In den Use Cases fallen 29 Zeilen weg, die neue Klasse hat 29, die Gesamtlänge bleibt gleich, und `RenameAlbumUseCase` lädt mit `findAll`. Beides wurde vor dem Commit korrigiert. Ähnlich bei der Coverage in diesem Block: Die zwei Zeilen in `Trash` mit nicht ausgeführtem Lambda hatte der Agent zuerst als grün beschrieben; JaCoCo zeigt sie gelb. (3) **Nutzen:** Das Refactoring ist klein. Dass es CR-1 erleichtert, ist eine Erwartung aus der Impact Analysis und zeigt sich erst bei der Umsetzung. |
| **Entscheidung** | **Übernommen:** beide Refactoring-Schritte als eigene `refactor`-Commits ohne fachliche Änderung. **Angepasst:** die Zahlen und die Aussage zu `RenameAlbumUseCase` in REFACTORING.md. Die `null`-Abweichung bleibt, weil die Album-ID sich schon vorher so verhielt und `AlbumWindow` die ID immer aus einer `PhotoSummary` nimmt; sie ist in REFACTORING.md, Abschnitt 3 begründet. **Verworfen:** auch `RenameAlbumUseCase` auf `AlbumLookup` umzustellen, weil er alle Alben lädt und eigene Meldungen hat; das hätte Verhalten geändert. |

### Prompt zu Fall 5

Arbeitsauftrag, zusammengefasst aus der Sitzung:

```text
Bearbeite Block 6 (Issue #28) Schritt für Schritt nach der ROADMAP und AGENTS.md. Immer nur den aktuellen Roadmap-Punkt, Umfang auf das Nötige beschränken, danach prüfen, ob das Ergebnis fachlich stimmt und zum Gesamtprojekt passt.
Aktueller Punkt: Refactoring. Wähle eine intern verbesserungsbedürftige Stelle mit Nachweis (Klasse, Metrik, Smell), vorzugsweise eine, die den Change Request CR-1 erleichtert. Gehe nach den fünf Schritten aus Block 6, S. 10 vor: Verhalten mit grünen Tests absichern, kleine Strukturänderung, Tests ausführen, Diff auf Verhaltensneutralität prüfen, eigener Refactoring-Commit. Dokumentiere Ausgangssituation, Problem, Schritte, Ergebnis und Testabsicherung in docs/REFACTORING.md.
```
