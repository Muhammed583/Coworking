package repository.interfaces;

public interface IBookingRepository {
    boolean createBooking(int userId, int workspaceId, int hours);
    boolean showBookingHistory(int userId);

}
