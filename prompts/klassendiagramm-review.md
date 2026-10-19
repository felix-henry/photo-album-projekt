# Prompt „Klassendiagramm-Review“ – KI als Reviewer

| | |
|---|---|
| Version | v1 (19.10.2026) |
| Zweck | Hausaufgabe B3 F50: KI ein Klassendiagramm-Review machen lassen; einen Vorschlag übernehmen, einen verwerfen – mit Begründung |
| Einsatz | AI_USAGE.md Fall 2 |
| Prüfkriterien | Review-Checks B3 F48 (Regel-, Konsistenz-, Kopplungs-Check), Checkpoint B3 F49 |

## Platzhalter

| Platzhalter | ersetzen durch |
|---|---|
| `<klassendiagramm.puml>` | Inhalt von docs/design/klassendiagramm.puml |
| `<sequenz-foto-hinzufuegen.puml>` | Inhalt von docs/design/sequenz-foto-hinzufuegen.puml |
| `<Geschäftsregeln>` | Abschnitt 3 und 6–7 aus REQUIREMENTS.md |

## Prompt

```text
Du bist Reviewer für ein Klassendiagramm eines kleinen Java-Projekts (Schichten domain, application, infrastructure, ui; Ports und Adapter).
Kontext: <klassendiagramm.puml> + <sequenz-foto-hinzufuegen.puml> + <Geschäftsregeln>.

Prüfe genau drei Dinge:
1. Regel-Check: In welcher Klasse wird jede Geschäftsregel geprüft? Fehlt eine? Liegt eine in der UI oder in einem Adapter?
2. Konsistenz-Check: Gibt es eine Nachricht im Sequenzdiagramm ohne passende Operation im Klassendiagramm?
3. Kopplungs-Check: Welche Klasse hat die meisten Abhängigkeiten? Ist das begründet? Zeigt eine Abhängigkeit nach außen?

Regeln:
- Erfinde keine Anforderungen, Klassen oder Zahlen, die nicht im Kontext stehen.
- Höchstens 5 Vorschläge, als Tabelle: Nr | Befund | Vorschlag | Begründung | betroffene Klasse.
```
