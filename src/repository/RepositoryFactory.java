package repository;

public final class RepositoryFactory {
    private static final WorkspaceRepository WORKSPACE_REPO = new WorkspaceRepository();
    private static final BookingRepository BOOKING_REPO = new BookingRepository();
    private static final AuthRepository AUTH_REPO = new AuthRepository();

    private RepositoryFactory() {}

    public static WorkspaceRepository workspaceRepo() { return WORKSPACE_REPO; }
    public static BookingRepository bookingRepo() { return BOOKING_REPO; }
    public static AuthRepository authRepo() { return AUTH_REPO; }
}
