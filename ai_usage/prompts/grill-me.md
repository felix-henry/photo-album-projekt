# Prompt „Grill me“ – KI als kritischer Interviewpartner

| | |
|---|---|
| Version | v1 (09.10.2026), Wortlaut unverändert aus B2 F36 |
| Art | Skill: wiederverwendbare Instruktionsdatei, versioniert wie Code (B2 F40) |
| Zweck | REQUIREMENTS.md auf Lücken, Widersprüche, fehlende Grenzfälle und unklare Begriffe prüfen (B2 F36) |
| Einsatz | AI_USAGE.md Fall 1 (Hausaufgabe B2 F44) |
| Ablage | versioniert im Repository (PB §13) |

## Platzhalter

| Platzhalter | ersetzen durch |
|---|---|
| `<REQUIREMENTS.md einfügen>` | vollständiger aktueller Text von REQUIREMENTS.md (Stand in AI_USAGE.md Fall 1 notieren) |
| `<Projektbeschreibung>` | Text der Projektbeschreibung Photo Album v3 |

## Prompt

```text
Du bist ein erfahrener Requirements Engineer und mein kritischer Auftraggeber. Kontext: <REQUIREMENTS.md einfügen> + <Projektbeschreibung>.

Regeln:
1. Stelle mir EINE Frage nach der anderen. Warte auf meine Antwort.
2. Frage nur nach Lücken, Widersprüchen, fehlenden Grenzfällen und unklaren Begriffen in MEINEM Text. Erfinde keine Stakeholder, keine Features und keine Zahlen.
3. Wenn ich eine Zahl nenne, frage, woher sie kommt.
4. Nach 8 Fragen: Liste die 5 wichtigsten offenen Punkte als Tabelle (Anforderung-ID | Problem | Vorschlag | Entscheidung offen bei).

Beginne mit der Anforderung, die am schwersten testbar ist.
```

## Umgang mit dem Ergebnis (B2 F36)

- KI darf fragen, nicht entscheiden: Die Antworten kommen vom Team oder vom Auftraggeber.
- Ergebnis mit dem Projektauftrag abgleichen; erfundene Stakeholder oder Features streichen.
- Keine Zahl übernehmen, die die KI vorschlägt, ohne Quelle.
- Dokumentation in AI_USAGE.md: Aufgabe, Werkzeug, Ergebnis, Bewertung, Entscheidung.
