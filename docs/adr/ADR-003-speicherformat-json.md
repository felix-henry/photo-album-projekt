# ADR-003: Speicherformat JSON

**Status:** vorgeschlagen · 20.10.2026

**Kontext:** Die Daten liegen lokal im Dateisystem, in einem dokumentierten Format. Ein Abbruch beim Speichern darf keinen halben Stand hinterlassen.

**Entscheidung:**
- **Ablage:** Datenordner `~/.photo-album/` mit `albums.json` und den Bildkopien in `images/`.
- **Format:** `{"version":1,"albums":[{"id","name","photos":[{"id","hash","fileName","type","sizeBytes","title","description"}]}]}`.
- **Schreiben:** über eine Temp-Datei und atomares Umbenennen. Unlesbare Daten werden gemeldet, nicht überschrieben.
- **Bibliothek:** Jackson.

**Verworfen:**
- Java-Serialisierung, denn sie ist binär und lässt sich nicht als Format dokumentieren.
- Ein eigenes Textformat, denn der Parser wäre eine eigene Fehlerquelle.

**Konsequenzen:**
- **Plus:** Die Datei ist lesbar, nach einem Abbruch gilt der alte oder der neue Stand.
- **Minus:** Es kommt eine Abhängigkeit dazu, und jede Änderung schreibt die ganze Datei neu.
