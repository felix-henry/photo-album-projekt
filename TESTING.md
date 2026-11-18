# TESTING – Teststrategie und Qualitätsnachweise

## 1. Teststrategie

Die Tests folgen der Testpyramide: viele schnelle Unit-Tests, wenige Integrationstests, einzelne manuelle Systemtests. Welche Testarten eingesetzt werden, richtet sich nach dem Risiko ([Block 5, S. 7–9](docs/lecture_slides/Block_5_Testen_Blackbox.pdf#page=7)).

### 1.1 Teststufen

| Stufe | Was wird geprüft | Umsetzung im Projekt |
| --- | --- | --- |
| Unit-Test | Fachliche Regeln der Domäne und Abläufe der Use Cases, ohne Oberfläche, ohne echte Dateien und ohne Wartezeit (NFA-05) | JUnit 5 in `src/test/java/.../domain` und `.../application`. Use Cases laufen gegen Fakes (`InMemoryAlbumRepository`, `InMemoryPhotoStorage`, `InMemoryTrashRepository`, `FakeImageHasher`) und eine feste Uhr (`Clock.fixed`). |
| Integrationstest | Use Case mit echter Infrastruktur und einer fachlichen Frage zu Persistenz, Serialisierung oder Pfaden | JUnit 5 mit `@TempDir`, siehe Abschnitt 4. Die Adaptertests in `.../infrastructure` prüfen die Dateiadapter zusätzlich einzeln. |
| Systemtest | Die gesamte Anwendung über die Swing-Oberfläche | Manuell mit der gebauten JAR, siehe Abschnitt 5. |
| Abnahmetest | Prüfung gegen die Akzeptanzkriterien durch die Auftraggeberin | Abschlusspräsentation (Block 7). |

### 1.2 Testarten

| Testart | Frage | Umsetzung im Projekt |
| --- | --- | --- |
| Funktional | Tut das System, was FA und AK verlangen? | Unit- und Integrationstests mit AK-Bezug, Zuordnung in Abschnitt 2. |
| Regression | Funktioniert Bestehendes nach einer Änderung noch? | Die gesamte Suite läuft in der CI bei jedem Push auf `main` und bei jedem PR (`./mvnw verify`). In Block 6 sichern Regressionstests Change Request und Refactoring ab. |
| Sicherheit | Was passiert bei bösartigen oder fehlerhaften Dateien? | Tests für Dateieingaben, siehe Abschnitt 6. |
| Performance | Hält das System NFA-03 ein? | Messung mit 100 Fotos auf den Team-Laptops, siehe Abschnitt 7. |
| Usability | Können Nutzer ohne Erklärung ein Album erstellen und ein Foto hinzufügen (NFA-04)? | Selbsttest durch die Teammitglieder mit Protokoll, siehe Abschnitt 5. |
| Kompatibilität | Läuft das System dort, wo es laufen soll? | CI auf Linux (Ubuntu, JDK 21), Build und manuelle Tests auf den Laptops des Teams. |

Die Testabdeckung wird mit JaCoCo erhoben und je Schicht interpretiert, siehe Abschnitt 8.
