package de.commitmentissues.photoalbum.ui;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import de.commitmentissues.photoalbum.application.LoadPhotoUseCase;
import de.commitmentissues.photoalbum.application.Result;

/**
 * Wandelt die Bilddaten einer App-Kopie in ein Bild in Anzeigegröße um (ARCHITECTURE, Abschnitt 5).
 * Größere Bilder werden verkleinert, kleinere nicht vergrößert.
 */
final class ImageDecoder {

    private ImageDecoder() {
    }

    static Result<BufferedImage> decode(byte[] data, int maxWidth, int maxHeight) {
        try (ImageInputStream input = ImageIO.createImageInputStream(new ByteArrayInputStream(data))) {
            Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
            if (!readers.hasNext()) {
                return Result.failure(LoadPhotoUseCase.UNREADABLE);
            }
            ImageReader reader = readers.next();
            try {
                reader.setInput(input, true, true);
                int step = Math.max(1, Math.max(reader.getWidth(0) / maxWidth, reader.getHeight(0) / maxHeight));
                ImageReadParam param = reader.getDefaultReadParam();
                param.setSourceSubsampling(step, step, 0, 0);
                return Result.success(fit(reader.read(0, param), maxWidth, maxHeight));
            } finally {
                reader.dispose();
            }
        } catch (IOException | RuntimeException e) {
            return Result.failure(LoadPhotoUseCase.UNREADABLE);
        }
    }

    private static BufferedImage fit(BufferedImage image, int maxWidth, int maxHeight) {
        double scale = Math.min(1.0, Math.min(
                (double) maxWidth / image.getWidth(), (double) maxHeight / image.getHeight()));
        int width = Math.max(1, (int) Math.round(image.getWidth() * scale));
        int height = Math.max(1, (int) Math.round(image.getHeight() * scale));
        if (width == image.getWidth() && height == image.getHeight()) {
            return image;
        }
        BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = scaled.createGraphics();
        try {
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics.drawImage(image, 0, 0, width, height, null);
        } finally {
            graphics.dispose();
        }
        return scaled;
    }
}
