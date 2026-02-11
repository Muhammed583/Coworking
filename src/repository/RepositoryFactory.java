package repository;

import repository.interfaces.IAuthRepository;
import repository.interfaces.IBookingRepository;
import repository.interfaces.IWorkspaceRepository;

public final class RepositoryFactory {
    private static final IWorkspaceRepository WORKSPACE_REPO = new WorkspaceRepository();
    private static final IBookingRepository BOOKING_REPO = new BookingRepository();
    private static final IAuthRepository AUTH_REPO = new AuthRepository();

    private RepositoryFactory() {}

    public static IWorkspaceRepository workspaceRepo() { return WORKSPACE_REPO; }
    public static IBookingRepository bookingRepo() { return BOOKING_REPO; }
    public static IAuthRepository authRepo() { return AUTH_REPO; }
}
