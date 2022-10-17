package ku.cs.oakcoding.app.models.users;

public enum UserManagerStatus {
    SUCCESSFUL,
    INVALID_ACCESS,
    USER_NAME_ALREADY_EXIST,
    USER_NAME_CONTAINS_UPPER_CASE,
    INVALID_PROFILE_IMAGE_FILE_TYPE,
    FAILURE
}
