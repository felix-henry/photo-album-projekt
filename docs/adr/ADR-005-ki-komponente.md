# ADR-005: KI-Funktion „Beschreibung vorschlagen“ bleibt Won't, Port ist entworfen

- **Status:** vorgeschlagen (vom Team zu bestätigen, Übung B3 F45 Punkt 3) · **Datum:** 19.10.2026

## Kontext

- Der Entwurf einer optionalen KI-Komponente (Port, Adapter, Guardrails) ist Pflicht, die Umsetzung freiwillig (PB §8).
- Zusätzliche Features verbessern die Bewertung nicht (PB §2); T-02 in REQUIREMENTS.md schlägt „wird“ vor.
- KI-01 bis KI-03 (REQUIREMENTS.md §8) legen Datenzugriff, Tool-Nutzung und Freigabe fest.

## Entscheidung

- KI-Funktion bleibt **Won't** in v1 und ist dokumentiert; das entspricht der Verbindlichkeit „wird“ (B2 F24) von KI-00 in REQUIREMENTS.md.
- Entworfen: Port `DescriptionSuggester` (`Optional<String> suggest(Path image)`) in `application`, Adapter (z. B. `ClaudeDescriptionSuggester`) in `infrastructure`, `FakeSuggester` für Tests (B3 F32, F44).
- Guardrails im Adapter: vor der Anfrage Metadaten entfernen und Timeout setzen; nach der Antwort Ausgabe bereinigen und auf ≤ 100 Zeichen ohne URLs prüfen (B3 F32, F43).
- Ein Vorschlag wird erst nach Bestätigung der Nutzerin gespeichert (KI-03).

## Verworfene Alternative

- **Should mit FakeSuggester in Tests:** mehr Code und Tests ohne Bewertungsvorteil (PB §2). Neu bewerten, wenn der Change Request (Block 6) eine KI-Funktion verlangt.

## Konsequenzen

- \+ kein externer Dienst, keine Fotos verlassen den Rechner (KI-01)
- − das Evaluation Set (PB §10, Pflicht auch für eine nur entworfene KI-Funktion) lässt sich in v1 nur manuell prüfen
