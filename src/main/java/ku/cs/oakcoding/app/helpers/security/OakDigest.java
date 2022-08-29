package ku.cs.oakcoding.app.helpers.security;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class OakDigest {

    static final String DEFAULT_DIGEST_ALGORITHM;

    static {
        DEFAULT_DIGEST_ALGORITHM = "SHA-1";
    }

    public static String getDefaultDigestAlgorithm() {
        return DEFAULT_DIGEST_ALGORITHM;
    }

    public static String getDigestStringOf(String algorithm,
                                           String filePathString) throws FileNotFoundException {

        Path path = FileSystems.getDefault().getPath(filePathString);

        if (Files.exists(path) == false) {
            throw new FileNotFoundException(path.toString());
        }

        String fileDigestString = "";

        try (InputStream fis = Files.newInputStream(path, StandardOpenOption.READ)) {
            MessageDigest fileDigest = MessageDigest.getInstance(DEFAULT_DIGEST_ALGORITHM);
            DigestInputStream dis = new DigestInputStream(fis, fileDigest);

            while (dis.read() != -1) ;

            byte[] digestBytes = fileDigest.digest();

            for (int i = 0, len = digestBytes.length; i < len; i++)
                fileDigestString += Integer.toString((digestBytes[i] & 0xff) + 0x100, 16).substring(1);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileDigestString;
    }

    public static String getDigestStringOf(String filePathString) throws FileNotFoundException {
        return getDigestStringOf(DEFAULT_DIGEST_ALGORITHM, filePathString);
    }

    public static String getDigestStringOf(String algorithm,
                                           Path filePath) throws FileNotFoundException {
        return getDigestStringOf(algorithm, filePath.toString());
    }

    public static String getDigestStringOf(Path filePath) throws FileNotFoundException {
        return getDigestStringOf(DEFAULT_DIGEST_ALGORITHM, filePath);
    }
}
