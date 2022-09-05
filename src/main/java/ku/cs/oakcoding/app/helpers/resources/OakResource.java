package ku.cs.oakcoding.app.helpers.resources;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;

import ku.cs.oakcoding.app.helpers.events.listeners.OakEventListener;

public interface OakResource
        extends Closeable,
                OakEventListener {

    String getName();

    String getFullName();

    URI getURI();

    Path getPath();

    Path getParentPath();

    File getFile();

    String getContentType() throws IOException;

    String getDigest();

    InputStream getInputStream() throws IOException;

    boolean isStreamClosed();
}
