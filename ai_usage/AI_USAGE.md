# AI_USAGE – Photo Album

Stand 19.10.2026 · Nachweis des KI-Einsatzes nach PB §13 · Quellenkürzel: **PB** = Projektbeschreibung v3, **B1/B2/B3 F** = Folie Block 1/2/3.

## Regeln (PB §13; B2 F36, F43)

- Je Fall fünf Felder: **Aufgabe/Ziel** (was, in welchem Block) · **Werkzeug/Modell** (Produkt und Modellkennung; Prompt oder Skill versioniert im Repository unter `prompts/`) · **Ergebnis** (Kurzfassung oder Link) · **Kritische Bewertung** (was richtig, falsch, erfunden – woran erkannt?) · **Entscheidung** (übernommen / angepasst / verworfen – mit Begründung).
- Mindestens 3 substanzielle Fälle (empfohlen 5, einer je Block 2–6), davon mindestens 1 begründet verworfener Vorschlag.
- KI darf fragen, nicht entscheiden; keine Zahl der KI ohne Quelle übernehmen (B2 F36). Die Verantwortung für das Ergebnis bleibt beim Team; bewertet wird die transparente, kritische Bewertung, nicht die Menge der KI-Nutzung (PB §13).

## Übersicht

| Fall | Block | Aufgabe | Werkzeug/Modell | Prompt/Skill | Entscheidung | Status |
|---|---|---|---|---|---|---|
| 1 | 2 | „Grill me“ zu REQUIREMENTS.md | vom Team einzutragen | [prompts/grill-me.md](../prompts/grill-me.md) v1 | offen; mind. ein Vorschlag ist begründet zu verwerfen (B2 F44) | ausstehend: Durchlauf durch das Team |
| 2 | 3 | KI-Review des Klassendiagramms (Hausaufgabe B3 F50) | Claude Code, Subagent (claude-sonnet-5-5) | [prompts/klassendiagramm-review.md](../prompts/klassendiagramm-review.md) v1 | 1 übernommen, 1 angepasst, 1 verworfen, 2 offen (Vorschlag, vom Team zu bestätigen) | durchgeführt 19.10.2026 |
| 3 | 1–3 | Nacharbeit README, REQUIREMENTS.md, Prototyp, CRC-Karten, Sequenzdiagramm „Album löschen“ | Claude Code, claude-opus-5-5 | Arbeitsaufträge im Chat, nicht versioniert | offen | ausstehend: Bewertung durch das Team |

---

## Fall 1 – „Grill me“ zu REQUIREMENTS.md (Hausaufgabe B2 F44)

> Das Team führt den Durchlauf selbst durch. Die KI stellt nur Fragen; die Antworten kommen vom Team oder vom Auftraggeber, nicht von der KI (B2 F36). Mindestens ein Vorschlag wird begründet verworfen (B2 F44).

**Durchführung:** Platzhalter in `prompts/grill-me.md` durch den aktuellen Text von REQUIREMENTS.md und der Projektbeschreibung ersetzen → 8 Fragen der KI beantworten → Ergebnistabelle der KI unten eintragen → jeden Vorschlag mit dem Projektauftrag abgleichen (B2 F36).

| Feld | Inhalt |
|---|---|
| Aufgabe/Ziel | Block 2: REQUIREMENTS.md v1 auf Lücken, Widersprüche, fehlende Grenzfälle und unklare Begriffe prüfen lassen (B2 F36). Durchgeführt am _[Datum]_ von _[Namen]_. |
| Werkzeug/Modell | _[Produkt, Modellkennung]_ · Prompt: [prompts/grill-me.md](../prompts/grill-me.md) v1 (B2 F36) · Kontext: REQUIREMENTS.md Stand _[Datum/Commit]_, Projektbeschreibung v3 |
| Ergebnis | _[Kurzfassung der 8 Fragen und Antworten oder Link auf das Protokoll]_; Tabelle der KI siehe unten |
| Kritische Bewertung | _[Was war richtig, was falsch, was erfunden? Woran erkannt? Prüfen: erfundene Stakeholder oder Features, Zahlen ohne Quelle (B2 F36)]_ |
| Entscheidung | _[je Vorschlag: übernommen / angepasst / verworfen – mit Begründung; Änderung in REQUIREMENTS.md mit Anforderungs-ID]_ |

**Ergebnistabelle der KI (Regel 4 des Prompts) mit Entscheidung des Teams**

| Anforderung-ID | Problem | Vorschlag | Entscheidung offen bei | Entscheidung Team | Begründung |
|---|---|---|---|---|---|
| | | | | | |
| | | | | | |
| | | | | | |
| | | | | | |
| | | | | | |

**Begründet verworfener Vorschlag (Pflicht, B2 F44; PB §13):** _[Vorschlag · Grund des Verwerfens · Quelle, z. B. Widerspruch zu PB oder Zahl ohne Quelle]_

---

## Fall 2 – KI-Review des Klassendiagramms (Hausaufgabe B3 F50)

| Feld | Inhalt |
|---|---|
| Aufgabe/Ziel | Block 3: Klassendiagramm v1 nach den drei Review-Checks aus B3 F48 prüfen lassen (Regel-, Konsistenz-, Kopplungs-Check); einen Vorschlag übernehmen, einen verwerfen (B3 F50). Durchgeführt am 19.10.2026. |
| Werkzeug/Modell | Claude Code, Subagent mit Modell claude-sonnet-5-5 · Prompt: [prompts/klassendiagramm-review.md](../prompts/klassendiagramm-review.md) v1 · Kontext: `docs/design/klassendiagramm.puml`, `docs/design/sequenz-foto-hinzufuegen.puml`, REQUIREMENTS.md §3, §6, §7 (Stand 19.10.2026) |
| Ergebnis | Regel-Check: FA-01, FA-05, FA-06 in `Album`, FA-07 in `Photo`, FA-04 in `ImageHasher.validate`; keine Regel in der UI. Konsistenz-Check: jede Nachricht hat eine Operation, aber die geworfenen Exceptions fehlen im Klassendiagramm. Kopplungs-Check: `AddPhotoUseCase` hat 6 Abhängigkeiten, begründet als Koordinator; keine Abhängigkeit zeigt nach außen. Fünf Vorschläge (Tabelle unten). |
| Kritische Bewertung | Die Checks sind sachlich richtig und bleiben im gegebenen Kontext; nichts erfunden. Vorschlag 2 begründet die Teilung mit der Kopplung von `AddPhotoUseCase` – das ist falsch: Ein zusätzlicher Port erhöht dessen Abhängigkeiten von 6 auf 7. Vorschlag 4 verkennt, dass das Diagramm laut B3 F47 nur „entscheidungsrelevante Methoden“ zeigen soll; die übrigen Use Cases stehen in ARCHITECTURE.md §1. |
| Entscheidung | siehe Tabelle |

| Nr | Vorschlag der KI | Entscheidung | Begründung |
|---|---|---|---|
| 1 | FA-04-Grenzwerte sichtbar machen oder in die Domäne ziehen | offen (Team) | Die Folie gibt die Prüfung bewusst `ImageHasher` (B3 F18); ob die Grenzwerte in die Domäne wandern, entscheidet das Team. Als offener Punkt in ADR-002 notiert. |
| 2 | `ImageHasher` in zwei Ports teilen | **verworfen** | Die CRC-Karte auf B3 F18 gibt `ImageHasher` bewusst beide Aufgaben (Hash berechnen, Typ und Größe prüfen); beide betreffen dieselbe gewählte Datei (Kohäsion, B3 F11). Teilen erhöht die Kopplung von `AddPhotoUseCase` statt sie zu senken und erzeugt einen Port ohne benanntes Änderungsszenario (B3 F34). |
| 3 | Exceptions ins Klassendiagramm aufnehmen | **übernommen** | Sequenzdiagramm nutzt `InvalidImageException`, `DuplicatePhotoException`, `AlbumFullException`; Konsistenz-Check B3 F48. Ergänzt in `klassendiagramm.puml` (mit `<<throw>>`-Abhängigkeiten). |
| 4 | Use Cases für FA-01, FA-02, FA-07, FA-08 zeigen | angepasst | nicht ins Diagramm (B3 F47: nur entscheidungsrelevante Methoden); in ARCHITECTURE.md §1 als „gleiches Muster, Block 4“ genannt. |
| 5 | Port/Operation für Löschprotokoll (NFA-04) | offen (Team) | deckt sich mit offenem Punkt S-2 in ARCHITECTURE.md §8. |

**Begründet verworfener Vorschlag:** Nr. 2 (siehe Tabelle).

---

## Fall 3 – Nacharbeit mit Claude Code (Modell claude-opus-5-5)

| Feld | Inhalt |
|---|---|
| Aufgabe/Ziel | Blöcke 1–3: Nacharbeit der Artefakte aus Block 1 und 2 und der Übungen aus Block 3 bis F30 (09.10.2026); Block-3-Artefakte aus B3 F45–F50 (19.10.2026). Vorgaben an die KI: nur Folieninhalt bis B3 F30 und Projektbeschreibung; konkrete Werte nur mit Quelle; Folienbeispiele als Beispiel/Annahme kennzeichnen; Teamentscheidungen als Vorschlag; SOPHIST-Satzschablonen (B2 F23). |
| Werkzeug/Modell | Claude Code (Anthropic), Modell claude-opus-5-5. Prompt: Arbeitsaufträge im Chat, je Dokument an einen Subagenten delegiert; nicht im Repository versioniert (Abweichung von PB §13). Eingaben: Folientexte B1–B3, Projektbeschreibung v3, vorläufiges Klassendiagramm des Teams (Foto). |
| Ergebnis | Mit Claude Code erstellt bzw. nachgearbeitet (Tabelle unten). |
| Kritische Bewertung | _[vom Team auszufüllen]_ |
| Entscheidung | _[vom Team auszufüllen]_ |

| Artefakt | Ort | Bezug |
|---|---|---|
| README mit Vorgehensvorschlag | `README.md` | B1; PB §3 |
| Anforderungen v1 | `REQUIREMENTS.md` | PB §7; B2 |
| UI-Prototyp der zwei Use Cases | `docs/prototyp/` | B2 F44 |
| CRC-Karten | `docs/design/` | B3 F19 |
| Pattern-Bewertung | `docs/design/pattern-bewertung.md` | B3 F27–F34 |
| Sequenzdiagramm „Album löschen“ | `docs/design/` | B3 F25 |
| AI_USAGE.md (Struktur, Fall 1 als Vorlage), „Grill me“-Prompt | `ai_usage/AI_USAGE.md`, `prompts/grill-me.md` | PB §13; B2 F36, F43 |
| ARCHITECTURE.md, Klassendiagramm, Sequenzdiagramm „Foto hinzufügen“, ADR-001 bis ADR-005, Projektgerüst (19.10.2026) | `ARCHITECTURE.md`, `docs/design/`, `docs/adr/`, `pom.xml`, `src/` | PB §8; B3 F45–F50 |

### Prüfhinweise zu REQUIREMENTS.md

Stellen, an denen Claude Code Werte ohne Auftraggeberquelle gesetzt oder Teamentscheidungen vorweggenommen hat. Wo REQUIREMENTS.md „Teamanalyse“ oder „Einschätzung des Teams“ schreibt (Abschnitte 1.1, 2.1), stammt der Entwurf aus diesem Fall; die Bezeichnung trifft erst nach Prüfung durch das Team zu.

1. **A-01 Albumname 1–50 Zeichen** (FA-01, AK-01.3, AK-01.4): 50 ist ein Folienbeispiel (B3 F7, Codebeispiel B3 F13), kein Auftraggeberwert; PB §5.2 verlangt einen definierten Wert vom Auftraggeber. Offen als F-02.
2. **A-02 Beschreibung höchstens 100 Zeichen, getrennt vom Titel** (FA-07, AK-07.4, AK-07.5): B2 F31 nennt 100 nur für den Titel. Der Wert für die Beschreibung ist übertragen (Titel; KI-Beispiel B2 F37); getrennte Felder stützen sich auf B3 F20 und das nur vorläufige Team-Klassendiagramm. Offen als F-02.
3. **A-03/A-04 JPEG/PNG, höchstens 20 MB** (FA-04, UC-01 Schritt 4, AK-04.x, NFA-01): Folienbeispiele (B2 F14, F21, F23), keine Auftraggeberwerte; PB §5.2 verlangt die Dateitypen vom Auftraggeber. „1 MB = 1 048 576 Byte“ hat keine Quelle. Offen als F-03.
4. **NFA-01 bis NFA-05**: Alle Zahlen (≤ 2 s, 8 von 10 in < 60 s, ≤ 24 h, Unit-Tests < 30 s) sind Beispielwerte aus B2 F17 (T-05); das Referenzgerät (A-08) ist nicht festgelegt. NFA-02 erweitert das Folienbeispiel („Abbruch beim Upload“) auf „Album löschen“ und „nach einem Neustart“.
5. **A-06 / T-04 Foto gehört zu genau einem Album, Kopie der Datei, Nachfrage beim Entfernen** (FA-08, FA-09, NFA-04, AK-08.x, AK-09.1, UC-02): aus dem vorläufigen Team-Klassendiagramm abgeleitet und bewusst abweichend vom Folienbeispiel B2 F26. Bei Änderung des Klassendiagramms erneut prüfen.
