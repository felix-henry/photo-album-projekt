# ADR-006: Oberfläche mit Swing, gebaut nach Kern und Use Cases

**Status:** akzeptiert (Teamentscheidung) · Block 3

**Kontext:** PB §6 lässt JavaFX, Swing oder Konsole zu; zusätzliche Technologien brauchen ein ADR und eine CI ohne manuelle Installation. Bewertet wird, ob die Use Cases bedienbar sind, nicht die Optik (PB §4). NFA-04 verlangt, dass Personen, die die Anwendung nie benutzt haben, ohne Erklärung ein Album anlegen und ein Foto hinzufügen; UC-01 sieht dafür die Eingabe eines Pfads vor, AK-N04.1 gibt ihn auf der Aufgabenkarte vor.

**Entscheidung:**
- Swing im Paket `ui`. Die UI ruft nur Use Cases auf, zeigt `Result`, `AlbumSummary` und `PhotoView` an und fragt vor dem Löschen nach (FA-05, FA-09); sie enthält keine Geschäftsregel (NFA-05).
- Pfadeingabe als Textfeld (UC-01 Schritt 2), wie im [Prototyp](../prototype/index.html).
- Die UI wird erst gebaut, wenn Domäne und Use Cases stehen.

**Verworfen:**
- JavaFX: eigene Bibliothek (OpenJFX) neben dem JDK, also eine Zusatzabhängigkeit für Build und CI (PB §6).
- Konsole: Befehle müssen erst gelernt werden, das ist schwächer für NFA-04.

**Konsequenzen:**
- **+** Keine Zusatzabhängigkeit, Swing gehört zum JDK. Die UI kann nur aufrufen, was es schon gibt; Regeln entstehen zuerst in Domäne und Use Cases.
- **−** Die Oberfläche entsteht zuletzt, die Bedienbarkeit (NFA-04, AK-N04.1) ist erst danach prüfbar.
