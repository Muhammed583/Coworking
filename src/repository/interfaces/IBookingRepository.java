package repository.interfaces;

import java.sql.Timestamp;

public interface IBookingRepository {
    boolean createBooking(int userId, int workspaceId, Timestamp startTime, Timestamp endTime);
    boolean showBookingHistory(int userId);

}
