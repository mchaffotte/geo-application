package fr.chaffotm.geobase.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class IOManager {

    private final Charset charset;

    public IOManager(final Charset charset) {
        this.charset = charset;
    }

    public String getContent(final InputStream inputStream) throws IOException {
        try (final ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            final byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString(charset.name());
        }
    }

    public InputStream newStream(final String content) {
       return new ByteArrayInputStream(content.getBytes(charset));
    }

}
