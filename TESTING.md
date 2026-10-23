# TESTING

Markierung je Test: `@Tag("normal")`, `@Tag("fehler")`, `@Tag("grenzfall")`.

## Verfahren 1: Äquivalenzklassen und Grenzwerte – Albumname (FA-02)

| Klasse | Typ | Beschreibung | Repräsentant | Erwartet |
|---|---|---|---|---|
| ÄK1 | gültig | 1–50 Zeichen | „Urlaub 2026“ | angelegt |
| ÄK2 | gültig | Leerzeichen am Rand | „  Urlaub  “ | angelegt als „Urlaub“ (A-02) |
| ÄK3 | gültig | Akzent in zerlegter Form | 50 × „é“ | angelegt, zählt als 50 Zeichen (A-13) |
| ÄK4 | ungültig | leer | „“ | `InvalidAlbumNameException` EMPTY |
| ÄK5 | ungültig | nur Whitespace | „   “, Tab | EMPTY |
| ÄK6 | ungültig | `null` | `null` | EMPTY, keine NullPointerException |
| ÄK7 | ungültig | mehr als 50 Zeichen | 51 × „a“ | TOO_LONG |

Grenzwerte nach dem Trimmen: 0 → EMPTY; 1, 2, 49, 50 → gültig; 51 → TOO_LONG.

## Verfahren 2: Entscheidungstabelle – Foto hinzufügen (UC-01)

Bedingungen in der Reihenfolge von UC-01; das Duplikat wird vor der Anzahl geprüft (FA-07, AK-07.6).

| | R1 | R2 | R3 | R4 |
|---|---|---|---|---|
| B1 JPEG/PNG, höchstens 20 MB (FA-06) | N | J | J | J |
| B2 Foto schon im Album (Hash) | – | J | N | N |
| B3 Album hat 10.000 Fotos (`Album.MAX_PHOTOS`) | – | – | J | N |
| A1 Foto hinzugefügt | | | | X |
| A2 `UnsupportedImageException` | X | | | |
| A3 `DuplicatePhotoException` | | X | | |
| A4 `AlbumFullException` | | | X | |
| A5 Album unverändert | X | X | X | |

## Zuordnung AK → Verfahren → Test

| AK | Verfahren | Testmethode |
|---|---|---|
| AK-02.1 | ÄK4, ÄK5 | `AlbumNameTest.rejectsBlankName` |
| AK-02.2 | Grenzwert 50 | `AlbumNameTest.acceptsLengthUpToMax` |
| AK-02.3 | Grenzwert 51 | `AlbumNameTest.rejectsLengthAboveMax` |
| AK-02.4 | ÄK2 | `AlbumNameTest.stripsSurroundingSpaces` |
| AK-06.4 | R1 | `AddPhotoDecisionTableTest.r1_rejectsUnsupportedImage` |
| AK-07.2, AK-07.6 | R2 | `AddPhotoDecisionTableTest.r2_rejectsDuplicate_andLeavesAlbumUnchanged` |
| AK-07.5 | R3 | `AddPhotoDecisionTableTest.r3_rejectsPhotoWhenAlbumFull_andLeavesAlbumUnchanged` |
| AK-07.4 | R4 | `AddPhotoDecisionTableTest.r4_addsPhotoUpToMax` |
