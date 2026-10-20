# ADR-007: KI-Titelvorschlag als Port entworfen, Umsetzung Won't

**Status:** vorgeschlagen · Block 3

**Kontext:** PB §8 verlangt den Entwurf einer optionalen KI-Komponente mit Port, Adapter und Guardrails; die Umsetzung ist freiwillig. REQUIREMENTS 6.1 setzt sie nicht um, SR-01 verbietet Netzwerkübertragung und hat Vorrang. 6.2 beschreibt einen Titelvorschlag mit 5 s Timeout, KI-01 bis KI-03 begrenzen Daten, Werkzeuge und Freigabe, 6.3 nennt Prompt Injection.

**Entscheidung** (alles «Entwurf, Won't», kein Code):
- Port `TitleSuggester` in `application.port`: `Optional<String> suggestTitle(byte[] imageContent)`. Der Name folgt REQUIREMENTS 6.2; B3 F32 nennt die Rolle `DescriptionSuggester`.
- `SuggestTitleUseCase(AlbumRepository, PhotoStorage, TitleSuggester)` liefert nur einen als „Vorschlag“ gekennzeichneten Text; gespeichert wird erst nach Bestätigung über `DescribePhotoUseCase` (KI-03).
- Adapter `HttpTitleSuggester` in `infrastructure`, in `Main` nicht verdrahtet (SR-01). Die Domäne kennt keine KI (B3 F44).
- Guardrails im Adapter (B3 F43): **vor** der Anfrage Metadaten entfernen und keinen Dateinamen senden (KI-01), nach 5 s `Optional.empty()` (6.2 „Ausfall“); **nach** der Antwort nur Text annehmen und keine Änderungsfunktion anbieten (KI-02), Steuerzeichen entfernen, keine URLs zulassen, mehr als `Photo.MAX_TITLE_LENGTH` Codepoints nach NFC verwerfen (KI-03, A-13, NFA-05).

**Verworfen:**
- Should mit Fake-Adapter in Tests (B3 F45): mehr Code ohne Bewertungsvorteil (PB §2).
- Agent mit Werkzeugen (B3 F37–F43): KI-02 schließt Änderungsfunktionen aus; ein präpariertes Foto könnte sonst alle Beschreibungen überschreiben (REQUIREMENTS 6.3).

**Konsequenzen:**
- **+** Keine Daten verlassen das Gerät (SR-01). Eine Umsetzung ändert weder Domäne noch bestehende Use Cases, nur `PhotoStorage` bekommt `read(Photo): byte[]` (im Klassendiagramm «Entwurf, Won't»).
- **−** Eine Umsetzung setzt voraus, dass der Auftraggeber SR-01 ändert (REQUIREMENTS 6.4); das Evaluation Set (PB §10) läuft nicht gegen einen echten Dienst.
