package ku.cs.oakcoding.app.models.users;

public enum UserManagerStatus {
    SUCCESSFUL,
    INVALID_ACCESS,
    USER_NAME_ALREADY_EXIST,
    USER_NAME_CONTAINS_UPPER_CASE,
    INVALID_PROFILE_IMAGE_FILE_TYPE,
    USER_REPORT_REFERENCE_NOT_FOUND /* When create a new unsuspend request, reference report is required. */,
    FAILURE
}
