# AI_USAGE – Einsatz von KI-Werkzeugen

Ausgewählte substanzielle KI-Einsatzfälle nach PB §13. Die zugehörigen Prompts stehen beim jeweiligen Fall.

## Fall 1 – Kritisches Review der Anforderungen („Grill me“), Block 2

Der Fall bezieht sich auf eine frühere Fassung der REQUIREMENTS.md.

| Feld | Inhalt |
| --- | --- |
| **Aufgabe/Ziel** | Anforderungen auf Lücken, Widersprüche, fehlende Grenzfälle und unklare Begriffe prüfen. |
| **Werkzeug/Modell** | Claude (claude.ai), Claude Opus 5.5. Ein zweiter Chat mit demselben Modell half beim Formulieren der Antworten. Eingaben: frühere Fassung der REQUIREMENTS.md und Projektbeschreibung; [Prompt unten](#prompt-zu-fall-1). |
| **Ergebnis** | Acht Rückfragen und eine Übersicht der fünf wichtigsten offenen Punkte, unter anderem zu Messbedingungen, Geschäftsregeln und Dateifehlern. |
| **Kritische Bewertung** | Die KI erkannte fehlende Messbedingungen und widersprüchliche Fehlerabläufe. Einzelne Vorschläge erweiterten jedoch den Projektumfang unnötig. Die Entscheidungen traf das Team. |
| **Entscheidung** | **Übernommen:** beobachtbaren Startpunkt für den Usability-Test ergänzt. **Angepasst:** den Begriff „Geschäftsregel“ auf Regeln zur Annahme oder Ablehnung einer Eingabe eingegrenzt. **Verworfen:** automatische Bereinigung verwaister Dateien, da dies eine zusätzliche Funktion außerhalb des Auftrags wäre. |

**Korrektur vom 22.10.2026:** Folienbeispiele waren fälschlich als verbindliche Vorgaben behandelt worden. Vereinbart sind 10.000 Fotos pro Album; andere Beispielwerte bleiben offen. Eine Ladezeit wurde daraus nicht abgeleitet.

### Prompt zu Fall 1

<details>
<summary>Vollständigen Prompt anzeigen</summary>

Platzhalter vor dem Absenden mit REQUIREMENTS.md und Projektbeschreibung füllen.

```text
Du bist ein erfahrener Requirements Engineer und mein kritischer Auftraggeber.

Kontext:
<REQUIREMENTS.md einfügen>

<Projektbeschreibung einfügen>

Regeln:
1. Stelle mir EINE Frage nach der anderen. Warte auf meine Antwort.
2. Frage nur nach Lücken, Widersprüchen, fehlenden Grenzfällen und unklaren Begriffen in MEINEM Text. Erfinde keine Stakeholder, keine Features und keine Zahlen.
3. Wenn ich eine Zahl nenne, frage, woher sie kommt.
4. Nach 8 Fragen: Liste die 5 wichtigsten offenen Punkte als Tabelle (Anforderung-ID | Problem | Vorschlag | Entscheidung offen bei).

Beginne mit der Anforderung, die am schwersten testbar ist.
```

</details>

## Fall 2 – KI-Review des Klassendiagramms, Block 3

| Feld | Inhalt |
| --- | --- |
| **Aufgabe/Ziel** | Klassen- und Sequenzdiagramm von einer KI prüfen lassen, je einen Vorschlag übernehmen und verwerfen. |
| **Werkzeug/Modell** | Claude Code, claude-opus-5-5 als Subagent; [Prompt unten](#prompt-zu-fall-2). |
| **Ergebnis** | Vier Vorschläge aus Regel-, Konsistenz- und Kopplungs-Check. |
| **Kritische Bewertung** | Die Fundstellen stimmten, erfunden war nichts. Zwei Vorschläge hätten eine Regel in den Adapter verlagert bzw. eine Klasse ohne Anforderung ergänzt. |
| **Entscheidung** | **Übernommen:** Abhängigkeiten von `ErrorMessages` zu den Domänenklassen im Diagramm zeigen. **Verworfen:** Dateigröße schon im Adapter prüfen, weil die Regel sonst in zwei Klassen stünde. |

### Prompt zu Fall 2

<details>
<summary>Vollständigen Prompt anzeigen</summary>

```text
Du bist ein erfahrener Software-Architekt und reviewst den Entwurf eines anderen Teams.

Lies:
- docs/design/klassendiagramm.puml
- docs/design/sequenz-foto-hinzufuegen.puml
- REQUIREMENTS.md

Prüfe in dieser Reihenfolge:
1. Regel-Check: In welcher Klasse wird jede Geschäftsregel aus REQUIREMENTS geprüft? Fehlt eine? Liegt eine in der UI?
2. Konsistenz-Check: Gibt es eine Nachricht im Sequenzdiagramm ohne passende Operation im Klassendiagramm? Passen Rückgabewerte und Exceptions der Antworten zum Klassendiagramm?
3. Kopplungs-Check: Welche Klasse hat die meisten Abhängigkeiten? Ist das begründet?

Regeln:
- Nenne zu jedem Punkt die Fundstelle (Klasse, Operation oder Nachricht). Erfinde keine Anforderungen, Meldungstexte oder Zahlen.
- Ändere nichts an den Dateien.
- Liefere 3 bis 5 Vorschläge als Tabelle: # | Check | Fundstelle | Problem | Vorschlag.
```

</details>
