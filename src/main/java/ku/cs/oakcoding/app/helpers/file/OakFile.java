package ku.cs.oakcoding.app.helpers.file;

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
import java.util.logging.Level;

import ku.cs.oakcoding.app.helpers.events.OakEventType;
import ku.cs.oakcoding.app.helpers.events.OakSignal;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.helpers.resources.OakResource;
import ku.cs.oakcoding.app.helpers.resources.OakResourceAlreadyExistsException;
import ku.cs.oakcoding.app.helpers.resources.OakPopulatableResource;
import ku.cs.oakcoding.app.helpers.security.OakDigest;

public class OakFile
        implements OakResource,
                   OakPopulatableResource<OakFile> {

    private Path filePath;

    private String digest;

    private static InputStream in;

    private boolean closed;

    protected OakFile(Path filePath) throws OakResourceAlreadyExistsException {
        Objects.requireNonNull(filePath);

        if (Files.exists(filePath)) {
            throw new OakResourceAlreadyExistsException(filePath.toString());
        }

        this.filePath = filePath;

        try {
            this.digest = OakDigest.getDigestStringOf(filePath);
        } catch (NoSuchAlgorithmException e) {
            OakLogger.log(Level.WARNING, "Got NoSuchAlgorithmException while creating file");
        } catch (FileNotFoundException e) {
            OakLogger.log(Level.SEVERE, "Got FileNotFoundException");
        }
    }

    /**
     * @todo improve name resolving, use OakResourceTable instead of just try iterating over
     */
    @Override
    public OakFile duplicate() {
        int i = 1;  /**< use with OakResourceTable? */
        OakFile target;
        Path targetPath = filePath.getParent().resolve(this.getName() + DUPLICATE_NAME_DELIMITER + i);

        while (Files.exists(targetPath) == false) {
            targetPath = filePath.getParent().resolve(this.getName() + DUPLICATE_NAME_DELIMITER + ++i);
        }

        try {
            target = new OakFile(targetPath);
            return target;
        } catch (OakResourceAlreadyExistsException e) {
            OakLogger.log(Level.WARNING, "Cannot duplicate OakFile, resource already exists");
        }

        return null;
    }

    @Override
    public OakFile duplicate(String name) {
        OakFile target;
        Path targetPath = filePath.getParent().resolve(name);

        try {
            target = new OakFile(targetPath);
            return target;
        } catch (OakResourceAlreadyExistsException e) {
            OakLogger.log(Level.WARNING, "Cannot duplicate OakFile, resource already exists");
        }

        return null;
    }

    @Override
    public String getName() {
        return filePath.relativize(filePath.getParent()).toString();
    }

    @Override
    public String getFullName() {
        return filePath.toAbsolutePath().toString();
    }

    @Override
    public URI getURI() {
        return filePath.toUri();
    }

    @Override
    public Path getPath() {
        return filePath;
    }

    @Override
    public Path getParentPath() {
        return filePath.getParent();
    }

    @Override
    public File getFile() {
        return filePath.toFile();
    }

    @Override
    public String getContentType() throws IOException {
        return Files.probeContentType(filePath);
    }

    @Override
    public String getDigest() {
        if (digest == null) {
            OakLogger.log(Level.WARNING, "Try getting digest that is null");
        }

        return digest;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (in == null) {
            synchronized (OakFile.class) {
                if (in == null) {
                    in = Files.newInputStream(filePath, StandardOpenOption.READ);
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
    public void close() throws IOException {
        if (closed == false) {
            in.close();
            closed = true;
        }
    }

    /**
     * @todo implement
     */
    @Override
    public <T> void update(OakEventType eventType,
                           T context) {}

    @Override
    public void kill(OakSignal sig,
                     String message) {

        try {
            switch(sig) {
                case SIGKILL:
                case SIGQUIT:
                    close();
            }
        } catch (IOException e) {
            OakLogger.log(Level.WARNING, "Cannot close stream while responding to signal");
        }
    }
}
