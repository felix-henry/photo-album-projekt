# ADR-003: Bilddateien über `ImageInspector` und `PhotoStorage`

**Status:** vorgeschlagen · Block 3

**Kontext:** UC-01 liest eine Bilddatei, erkennt JPEG/PNG am Inhalt (A-03), lehnt mehr als 20 MB ab (A-04) und erkennt Duplikate per SHA-256 (A-01). Jedes Album hat eigene Bildkopien, Originale bleiben unberührt (A-08), und dieselbe Datei darf in zwei Alben liegen (AK-07.3). B3 F18 und F20 legen die Typ- und Größenprüfung in den Adapter (`ImageHasher.validate`); NFA-05 verlangt aber die Grenzwerte an einer Stelle und Regeltests ohne Dateisystem.

**Entscheidung:**
- `ImageInspector.inspect(Path source)` liefert nur Fakten als `ImageInfo(ImageType type, long sizeBytes, ContentHash hash)` oder wirft `ImageNotReadableException`. Den Typ erkennt `ImageType.fromHeader(byte[])`, über die Annahme entscheidet der Konstruktor von `Photo` (`OTHER`, `MAX_FILE_SIZE_BYTES`).
- `PhotoStorage.store(Photo, Path source)` kopiert in den Datenordner und hinterlässt bei Fehler keine Teilkopie; `delete(Photo)` wirft `StorageException` mit Pfad (FA-05, FA-09). Schlüssel ist die `PhotoId`: `images/<photoId>.jpg` bzw. `.png`, kein Dateiname der Nutzerin.
- Adapter `FileImageInspector` (Header, Größe, SHA-256 per `MessageDigest`) und `FilePhotoStorage` in Block 4.

**Verworfen:**
- `ImageHasher.validate` mit Grenzwerten im Adapter (B3 F20): 20 MB stünde in der Infrastruktur, Regeltests bräuchten Dateien (NFA-05).
- Hash als Speicherschlüssel: Löschen in einem Album träfe die Kopie des anderen (AK-07.3, A-08).
- `Photo` liest selbst vom Dateisystem: Die Domäne kennt dann Technik (B3 F15, Aussage 4).

**Konsequenzen:**
- **+** Die Regeln hinter AK-06.2 bis AK-06.5 lassen sich als Domänentests ohne Dateisystem prüfen; gleichnamige Dateien kollidieren im Datenordner nicht.
- **−** Zwei Ports mehr. Der Adapter liest und hasht auch eine Datei, die `Photo` danach als zu groß ablehnt.
