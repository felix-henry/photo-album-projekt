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

Geplant (empfohlen).

| Feld | Inhalt |
| --- | --- |
| **Aufgabe/Ziel** | Das Klassendiagramm aus Block 3 auf klare Verantwortlichkeiten und Abhängigkeiten prüfen lassen. Einen Vorschlag übernehmen und einen verwerfen, jeweils begründet. |
| **Werkzeug/Modell und Prompt oder Skill** | [Verwendeter Prompt](#prompt-zu-fall-2) |
| **Ergebnis** | |
| **Kritische Bewertung** | |
| **Entscheidung** | |

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

Geplant.

| Feld | Inhalt |
| --- | --- |
| **Aufgabe/Ziel** | Einen Coding Agent für eine abgegrenzte Implementierungsaufgabe einsetzen und seinen Diff anhand der Anforderungen und Tests prüfen. |
| **Werkzeug/Modell und Prompt oder Skill** | |
| **Ergebnis** | |
| **Kritische Bewertung** | |
| **Entscheidung** | |

## Fall 4 – Tests aus einem Akzeptanzkriterium ableiten, Block 5

Geplant.

| Feld | Inhalt |
| --- | --- |
| **Aufgabe/Ziel** | Aus einem Akzeptanzkriterium Tests generieren lassen und prüfen, ob die Assertions das geforderte Verhalten absichern. |
| **Werkzeug/Modell und Prompt oder Skill** | |
| **Ergebnis** | |
| **Kritische Bewertung** | |
| **Entscheidung** | |

## Fall 5 – Refactoring prüfen, Block 6

Geplant (empfohlen).

| Feld | Inhalt |
| --- | --- |
| **Aufgabe/Ziel** | Einen KI-Vorschlag für ein Refactoring prüfen und anhand des Diffs und der Tests bewerten, ob das Verhalten erhalten bleibt. |
| **Werkzeug/Modell und Prompt oder Skill** | |
| **Ergebnis** | |
| **Kritische Bewertung** | |
| **Entscheidung** | |
