package ku.cs.oakcoding.app.helpers.resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import ku.cs.oakcoding.app.helpers.events.OakEventType;
import ku.cs.oakcoding.app.helpers.events.OakSignal;
import ku.cs.oakcoding.app.helpers.security.OakDigest;

public class OakTempResource
        implements OakResource {

    private Path path;

    private String digest;

    private static InputStream in;

    private boolean closed;

    public OakTempResource(Path path) throws FileNotFoundException,
                                                NoSuchAlgorithmException {
        Objects.requireNonNull(path);

        this.path = path;
        this.digest = OakDigest.getDigestStringOf(path);
        this.closed = true;
    }

    @Override
    public URI getURI() {
        return path.toUri();
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public File getFile() {
        return path.toFile();
    }

    @Override
    public String getContentType() throws IOException {
        return Files.probeContentType(path);
    }

    @Override
    public String getDigest() {
        return digest;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (in == null) {
            synchronized (OakTempResource.class) {
                if (in == null) {
                    in = Files.newInputStream(path, StandardOpenOption.READ);
                    closed = false;
                }
            }
        }

        return in;
    }

    @Override
    public boolean isStreamClosed() {
        return closed;
    }

    @Override
    public void kill(OakSignal sig,
                     String message) {
        switch (sig) {
            case SIGKILL:
                if (closed == false) {
                    try {
                        close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            default:

        }
    }

    @Override
    public <T> void update(OakEventType eventType,
                           T context) {
        
    }

    @Override
    public void close() throws IOException {
        if (in != null) {
            in.close();
            closed = true;
        }
    }
}
