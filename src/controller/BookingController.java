package controller;

import dao.BookingDAO;
import java.util.List;
import model.BookingModel;

public class BookingController {
    private BookingDAO dao;

    public BookingController() {
        this.dao = new BookingDAO();
    }

    public List<BookingModel> getBookings(String search, String roomType, String statusFilter, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return dao.getBookings(search, roomType, statusFilter, offset, pageSize);
    }

    public int getBookingsCount(String search, String roomType, String statusFilter) {
        return dao.getBookingsCount(search, roomType, statusFilter);
    }
}
