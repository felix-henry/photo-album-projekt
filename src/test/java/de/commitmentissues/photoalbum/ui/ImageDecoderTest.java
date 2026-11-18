package de.commitmentissues.photoalbum.ui;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.commitmentissues.photoalbum.application.Result;

class ImageDecoderTest {

    @Test
    @DisplayName("ARCHITECTURE 5: Ein großes Bild wird mit gleichem Seitenverhältnis auf Anzeigegröße verkleinert")
    void shrinksLargeImageToDisplaySize() throws IOException {
        byte[] png = png(400, 300);

        Result<BufferedImage> result = ImageDecoder.decode(png, 200, 200);

        assertEquals(200, result.getValue().getWidth());
        assertEquals(150, result.getValue().getHeight());
    }

    @Test
    @DisplayName("Ein kleines Bild wird nicht vergrößert")
    void keepsSmallImageSize() throws IOException {
        byte[] png = png(40, 30);

        Result<BufferedImage> result = ImageDecoder.decode(png, 200, 200);

        assertEquals(40, result.getValue().getWidth());
        assertEquals(30, result.getValue().getHeight());
    }

    @Test
    @DisplayName("AK-08.3: Nicht darstellbare Bilddaten werden als Lesefehler gemeldet")
    void reportsUndecodableData() {
        Result<BufferedImage> result = ImageDecoder.decode(new byte[] {1, 2, 3}, 200, 200);

        assertEquals("Das Foto konnte nicht gelesen werden.", result.getMessage());
    }

    private static byte[] png(int width, int height) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB), "png", out);
        return out.toByteArray();
    }
}
