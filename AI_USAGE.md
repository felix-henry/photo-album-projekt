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
