package ku.cs.oakcoding.app.helpers.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;

import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;

public final class OakWorkspaceResource {
    private OakWorkspaceResource() {}

    private static final Path DEP_DIR_PREFIX = OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_DEP_DIR.value());

    private static final Path DEFAULT_DEP_DIR_TEMPLATE = OakResourcePrefix.getPrefix().resolve(OakAppDefaults.APP_DEFAULT_DEP_DIR_TEMPLATE.value());

    private static final Path DEFAULT_DEP_INFO_TEMPLATE = OakResourcePrefix.getPrefix().resolve(OakAppDefaults.APP_DEFAULT_DEP_INFO_TEMPLATE.value());

    public static Path getDepartmentDirOf(String depName) {
        return DEP_DIR_PREFIX.resolve(depName);
    }

    public static Path newDepartmentDirectory(String depName) {
        Path res = null;

        if (Files.exists(DEP_DIR_PREFIX.resolve(depName))) {
            OakLogger.log(Level.SEVERE, "Department directory is already exist");
            return null;
        }

        try {
            res = Files.createDirectories(DEP_DIR_PREFIX.resolve(depName));
            OakResource.recursiveCopyDirectory(DEFAULT_DEP_DIR_TEMPLATE, DEP_DIR_PREFIX.resolve(depName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            OakLogger.log(Level.SEVERE, "Got 'IOException' while attempting to create new department directory");
            return null;
        }

        return res;
    }

    public static boolean removeDepartmentDirectory(String orgName) {
        if (!Files.exists(DEP_DIR_PREFIX.resolve(orgName))) {
            OakLogger.log(Level.SEVERE, "Department directory does not exist");
            return false;
        }

        try {
            OakDirEditor.deleteDirectory(DEP_DIR_PREFIX.resolve(orgName));
        } catch (IOException e) {
            OakLogger.log(Level.SEVERE, "Got 'IOException' while attempting to remove department directory");
            return false;
        }

        return true;
    }

    public static String renameDepartmentDirectory(String oldName, String newName) {
        Path orgDir = DEP_DIR_PREFIX.resolve(oldName);

        try {
            Files.move(orgDir, orgDir.resolveSibling(newName));
        } catch (IOException e) {
            OakLogger.log(Level.SEVERE, "Got 'IOException' while attempting to rename department directory");
            return null;
        }

        return orgDir.toString();
    }

    public static void main(String[] args) {
        // System.out.println(OakUserResource.renameUserDirectory("eiei", "naiithink"));

        // System.out.println(OakUserResource.removeUserDirectory("hello"));
        // System.out.println(OakWorkspaceResource.newDepartmentDirectory("ACME"));
        System.out.println(OakWorkspaceResource.removeDepartmentDirectory("ACME"));
    }
}
