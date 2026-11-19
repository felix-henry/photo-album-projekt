# Changelog

Alle wichtigen Änderungen am Photo Album stehen in dieser Datei. Das Format folgt [Keep a Changelog](https://keepachangelog.com/de/1.1.0/), die Versionsnummern folgen [Semantic Versioning](https://semver.org/lang/de/).

## [1.0.0] – 2026-11-19

Geprüfte Pflichtfunktionalität mit den Test- und Qualitätsnachweisen aus Block 5 ([#27](https://github.com/felix-henry/photo-album-projekt/issues/27), [#41](https://github.com/felix-henry/photo-album-projekt/pull/41)).

### Hinzugefügt

- [TESTING.md](TESTING.md) mit Teststrategie, Zuordnung der Akzeptanzkriterien zu Verfahren und Testmethoden, Test Design Lab (Äquivalenzklassen und Entscheidungstabelle), Unit-, Integrations- und Systemtests, Usability-, Security- und Performance-Nachweisen sowie Coverage je Schicht.
- Tests aus dem Test Design Lab, Integrationstests mit den echten Dateiadaptern nach einem Neustart und Security-Tests für Dateieingaben; zusammen 231 automatisierte Tests.
- Performance-Messung für NFA-03 (`OpenAlbumPerformanceTest`), die nur auf Abruf läuft. NFA-03 ist auf allen fünf Team-Laptops erfüllt.
- Evaluation Set für den nur entworfenen KI-Titelvorschlag mit Freigabe-Pass-Rate ([eval/describe-photo/cases.md](eval/describe-photo/cases.md)).

### Geändert

- NFA-04 und AK-N04.1: Die fünf Teammitglieder testen die Bedienung selbst (Teamabsprache).

### Behoben

- Die Dateiauswahl startet im Ordner „Pictures“ und merkt sich den zuletzt benutzten Ordner über alle Alben hinweg ([#42](https://github.com/felix-henry/photo-album-projekt/issues/42)).
- Die Albumansicht öffnet schneller, weil die Dateiauswahl erst beim ersten „Foto hinzufügen“ entsteht ([#43](https://github.com/felix-henry/photo-album-projekt/issues/43)).
- Ein eingetipptes Suchmuster wie `*.jpg` bleibt nicht mehr als Filter in der Dateiauswahl stehen ([#42](https://github.com/felix-henry/photo-album-projekt/issues/42)).

## [0.1.0] – 2026-11-18

Erste lauffähige Version mit der Pflichtfunktionalität aus Block 4 ([#26](https://github.com/felix-henry/photo-album-projekt/issues/26), [#35](https://github.com/felix-henry/photo-album-projekt/pull/35)).

### Hinzugefügt

- Alben erstellen, anzeigen, umbenennen und löschen. Albumnamen haben 1 bis 50 Zeichen und sind unabhängig von Groß- und Kleinschreibung eindeutig (FA-01 bis FA-04, A-02).
- Fotos hinzufügen und als Bilder ansehen. Zulässig sind JPEG und PNG bis 20 MiB, geprüft wird der Inhalt statt der Endung. Ein Album nimmt höchstens 100 Fotos und kein bytegleiches Foto doppelt auf (FA-05 bis FA-08, A-01, A-03).
- Titel mit bis zu 100 und Beschreibung mit bis zu 500 Zeichen für jedes Foto (FA-10, A-04).
- Papierkorb für entfernte Fotos und gelöschte Alben: Anzeige mit Resttagen, Wiederherstellen ins Ursprungsalbum oder in ein neues Album, endgültiges Löschen und automatische Bereinigung nach 30 × 24 Stunden (FA-04, FA-09, SR-01, A-07 bis A-12).
- Bildpuffer mit höchstens 5 Bildern in Anzeigegröße, damit auch große Alben flüssig bleiben (NFA-03, A-06).
- Lokale Speicherung als JSON-Dateien und App-Kopien im Ordner `.photo-album` im Benutzerverzeichnis. Fehlgeschlagene Vorgänge werden zurückgenommen, und eine Bereinigung beim Start räumt nach doppelten Fehlern auf (NFA-01, NFA-02, A-11, ADR-001, ADR-002, [Speicherformat](ARCHITECTURE.md#10-speicherformat)).
- Swing-Oberfläche mit Albumübersicht, Albumansicht und Papierkorb im Aussehen des Betriebssystems.
- Build mit Maven-Wrapper (Maven 3.9.16), Java 21, JUnit 5, JaCoCo und einem ausführbaren JAR mit Gson ([ADR-000](docs/adr/ADR-000-build.md), [ADR-003](docs/adr/ADR-003-json-bibliothek.md)).
- CI mit GitHub Actions, Dependabot, PR-Vorlage und gemeinsame Agentenregeln in [AGENTS.md](AGENTS.md).
- 214 automatisierte Tests mit Bezug zu den Akzeptanzkriterien.
