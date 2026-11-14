package de.commitmentissues.photoalbum.infrastructure;

import java.util.List;

import de.commitmentissues.photoalbum.domain.Trash;

/**
 * Inhalt von {@code trash.json}. Nur Gson arbeitet mit dieser Klasse (ADR-003).
 */
record TrashFile(List<TrashEntryFile> entries) {

    static TrashFile of(Trash trash) {
        return new TrashFile(trash.getEntries().stream().map(TrashEntryFile::of).toList());
    }
}
