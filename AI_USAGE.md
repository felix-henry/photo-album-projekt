# AI_USAGE – Einsatz von KI-Werkzeugen

## Fall 1 – Offene Fachfragen klären („Grill me“), Block 2

| Feld | Inhalt |
| --- | --- |
| **Aufgabe/Ziel** | Die offenen Fragen in [Abschnitt 3.2](REQUIREMENTS.md#32-annahmen-und-offene-fragen) klären und prüfen, welche Anforderungen, Use Cases und Akzeptanzkriterien dadurch angepasst werden müssen. |
| **Werkzeug/Modell** | Codex (Desktop-App), GPT-6.1 Sol und [GrillMe](plugin://engineering-suite-grill-me@openai-curated-remote). Grundlage waren die Requirements und die Projektbeschreibung. [Verwendeter Prompt](#prompt-zu-fall-1) |
| **Ergebnis** | Die KI stellte Rückfragen und verglich Varianten anhand konkreter Fälle. Dazu gehörten umbenannte Bilddateien, Konflikte beim Wiederherstellen und Speicherfehler nach einer Teiländerung. Die Entscheidungen stehen in [Abschnitt 3.2](REQUIREMENTS.md#32-annahmen-und-offene-fragen). |
| **Kritische Bewertung** | Eine umbenannte Kopie prüft, ob der Dateiname die Duplikaterkennung beeinflusst. Die KI schlug vor, bei einem Fehler den gesamten reversiblen Vorgang zurückzusetzen. Das hält ein Album zusammen, wenn beim Verschieben in den Papierkorb bereits ein Foto verschoben wurde. Bei unabhängigen Importen würden dadurch auch erfolgreich importierte Fotos wieder entfernt. |
| **Entscheidung** | **Übernommen:** Bytegleicher Dateiinhalt gilt als Duplikatkriterium und erfasst auch umbenannte Kopien. **Angepasst:** Reversible Albumaktionen werden als Einheit abgeschlossen oder zurückgenommen (A-16). Endgültiges Löschen wird pro Foto ausgewertet (A-17). **Verworfen:** Bei einem Fehler alle Importe desselben Vorgangs zurücksetzen. Bereits erfolgreich importierte Fotos sollen erhalten bleiben, damit der Nutzer sie nicht erneut hinzufügen muss (A-16). |

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
| **Aufgabe/Ziel** | Den Entwurf für Fotoimport und Dateispeicherung auf klare Verantwortlichkeiten und Abhängigkeiten prüfen. |
| **Werkzeug/Modell und Prompt oder Skill** | |
| **Ergebnis** | |
| **Kritische Bewertung** | |
| **Entscheidung** | |

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
