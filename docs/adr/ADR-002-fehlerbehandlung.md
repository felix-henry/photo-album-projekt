# ADR-002: Fehlerbehandlung über Exceptions und Result

**Status:** akzeptiert · **Datum:** 10.11.2026

## Kontext

Abgelehnte Eingaben brauchen eine verständliche Meldung mit Grund. Der gespeicherte Zustand muss bei einem Fehler unverändert bleiben oder wiederhergestellt werden (NFA-01, A-11). Die UI soll keine Geschäftsregeln kennen.

## Entscheidung

- Die Domäne und `ImageHasher` lehnen Verstöße mit einer Exception samt Grund ab, zum Beispiel Duplikat, Album voll oder ungültige Datei.
- Der Use Case fängt diese Exceptions und auch Speicherfehler der Ports. Er gibt der UI ein `Result` zurück. Es enthält entweder den Erfolg oder die Meldung für den Nutzer.
- Ein Use Case prüft zuerst alles und speichert erst danach. Bis zum Speichern ändert er das Album nur im Arbeitsspeicher.
- Adapter schreiben jede Datei zuerst unter einem temporären Namen und ersetzen das Original erst danach in einem Schritt. Eine Datei ist dadurch immer vollständig alt oder vollständig neu. Eine abgebrochene Teildatei entfernt der Adapter selbst.
- Beim Hinzufügen eines Fotos legt der Use Case zuerst die App-Kopie an und speichert danach das Album. Scheitert das Anlegen, bleibt das gespeicherte Album unverändert. Scheitert das Speichern, löscht er die angelegte Kopie wieder. Scheitert auch dieses Löschen, gilt die Ausnahme aus A-11: Der Use Case meldet den Fehler, und beim nächsten Start bereinigt die Anwendung verwaiste Kopien, siehe Bereinigung beim Start unten.
- Beim Erstellen eines Albums gibt es nur einen Schreibvorgang. Scheitert er, ist nichts zurückzunehmen.
- Verschieben in den Papierkorb und Wiederherstellen ändern zwei Dateien: die Albumdatei und `trash.json`. Der jeweilige Use Case koordiniert beide. Er ändert zuerst die Datei, die das Foto oder Album aufnimmt, und danach die, aus der es verschwindet: beim Verschieben erst `trash.json`, dann die Albumdatei, beim Wiederherstellen umgekehrt. Scheitert der zweite Schritt, stellt er den alten Stand der ersten Datei wieder her (AK-04.2, AK-04.5, AK-09.2). So ist ein Foto nach einem Fehler höchstens doppelt gespeichert, aber nie verloren.
- Scheitert auch das Wiederherstellen des alten Stands, gilt die Ausnahme aus A-11: Dieselbe Foto-ID liegt dann zugleich in einem aktiven Album und im Papierkorb. Beim nächsten Start nimmt die Anwendung das Foto aus dem Papierkorbeintrag heraus, ein leerer Eintrag entfällt. Das Foto bleibt also im Album, auch wenn der Vorgang ein Wiederherstellen war.
- Beim Start bereinigt die Anwendung in dieser Reihenfolge: doppelte Foto-IDs, fällige Einträge (A-10), verwaiste Kopien. Fällige Einträge und verwaiste Kopien löscht sie nur, wenn jede Albumdatei und `trash.json` fehlerfrei gelesen wurden. `findAll` und `load` melden eine unlesbare Datei dafür als Fehler, statt sie zu überspringen. Sonst lässt sie alle Kopien unberührt (AK-02.4). Das Löschen einer schon fehlenden Kopie gilt als Erfolg. Endgültiges Löschen und die Bereinigung nach A-10 löschen keine App-Kopie, auf die noch ein Album verweist.

## Verworfene Alternativen

- **Exceptions bis in die UI weiterreichen.** Die UI müsste dann jeden fachlichen Fehler kennen und selbst in Meldungen übersetzen. Damit läge Fachwissen in der Oberfläche.
- **Rückgabewerte statt Exceptions in der Domäne.** Übersieht der Use Case einen Rückgabewert, legt er trotz Ablehnung eine Kopie an und speichert. Eine Exception bricht den Ablauf ab, bevor etwas gespeichert wird. An der Grenze zur UI genügt dagegen ein `Result`, weil die UI nur anzeigt und keinen Zustand ändert.

## Konsequenzen

- Die UI zeigt nur an, was im `Result` steht.
- Unit-Tests prüfen Ablehnungen direkt an der Exception und am unveränderten Zustand.
- Jeder Use Case braucht die Übersetzung von Exceptions in Meldungen. Die Meldungstexte stehen damit an einer Stelle je Use Case.
