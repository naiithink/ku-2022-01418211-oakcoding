package ku.cs.oakcoding.app.helpers.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;

import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;

public final class OakUserResource {
    private OakUserResource() {}

    private static final Path USER_DIR_PREFIX = OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_USER_DIR.value());

    private static final Path DEFAULT_USER_DIR_TEMPLATE = OakResourcePrefix.getPrefix().resolve(OakAppDefaults.APP_DEFAULT_USER_DIR_TEMPLATE.value());

    private static final Path DEFAULT_USER_INFO_TEMPLATE = OakResourcePrefix.getPrefix().resolve(OakAppDefaults.APP_DEFAULT_USER_INFO_TEMPLATE.value());

    private static final Path DEFAULT_PROFILE_IMAGE = OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_DEFAULT_IMAGE_DIR.value()).resolve(OakAppDefaults.APP_DEFAULT_PROFILE_IMAGE.value());

    public static Path getDefaultProfileImagePath() {
        return DEFAULT_PROFILE_IMAGE;
    }

    public static Path getUserDirOf(String userName) {
        return USER_DIR_PREFIX.resolve(userName);
    }

    public static Path newUserDirectory(String userName) {
        Path res = null;

        if (Files.exists(USER_DIR_PREFIX.resolve(userName))) {
            OakLogger.log(Level.SEVERE, "User directory is already exist");
            return null;
        }

        try {
            res = Files.createDirectories(USER_DIR_PREFIX.resolve(userName));
            OakResource.recursiveCopyDirectory(DEFAULT_USER_DIR_TEMPLATE, USER_DIR_PREFIX.resolve(userName));
            // Files.copy(DEFAULT_USER_DIR_TEMPLATE, USER_DIR_PREFIX.resolve(userName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            OakLogger.log(Level.SEVERE, "Got 'IOException' while attempting to create new user directory");
            return null;
        }

        return res;
    }

    public static boolean removeUserDirectory(String userName) {
        if (!Files.exists(USER_DIR_PREFIX.resolve(userName))) {
            OakLogger.log(Level.SEVERE, "User directory does not exist");
            return false;
        }

        try {
            OakDirEditor.deleteDirectory(USER_DIR_PREFIX.resolve(userName));
        } catch (IOException e) {
            OakLogger.log(Level.SEVERE, "Got 'IOException' while attempting to remove user directory");
            return false;
        }

        return true;
    }

    public static String renameUserDirectory(String oldName, String newName) {
        Path userDir = USER_DIR_PREFIX.resolve(oldName);

        try {
            Files.move(userDir, userDir.resolveSibling(newName));
        } catch (IOException e) {
            OakLogger.log(Level.SEVERE, "Got 'IOException' while attempting to rename directory");
            return null;
        }

        return userDir.toString();
    }

    public static void main(String[] args) {
        // System.out.println(OakUserResource.renameUserDirectory("eiei", "naiithink"));

        // System.out.println(OakUserResource.removeUserDirectory("hello"));
        System.out.println(OakUserResource.newUserDirectory("hello"));
    }
}
