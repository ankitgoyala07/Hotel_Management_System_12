package model;

/**
 * Model class representing billing and invoice data.
 *
 * @author i3
 */
public class BillingModel {
    private String guestId;
    private String roomId;
    private String stayPeriod;
    private int nights;
    private double roomRate;
    private double roomService;
    private double foodOrders;
    private double laundry;
    private double miniBar;

    public BillingModel() {
        // Default constructor with standard reference values
        this.guestId = "001";
        this.roomId = "001";
        this.stayPeriod = "Oct 14 - Oct 18 (4 Nights)";
        this.nights = 4;
        this.roomRate = 250.00;
        this.roomService = 65.50;
        this.foodOrders = 145.00;
        this.laundry = 36.00;
        this.miniBar = 22.00;
    }

    public BillingModel(String guestId, String roomId, String stayPeriod, int nights, double roomRate,
                        double roomService, double foodOrders, double laundry, double miniBar) {
        this.guestId = guestId;
        this.roomId = roomId;
        this.stayPeriod = stayPeriod;
        this.nights = nights;
        this.roomRate = roomRate;
        this.roomService = roomService;
        this.foodOrders = foodOrders;
        this.laundry = laundry;
        this.miniBar = miniBar;
    }

    // Getters and Setters
    public String getGuestId() { return guestId; }
    public void setGuestId(String guestId) { this.guestId = guestId; }

    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }

    public String getStayPeriod() { return stayPeriod; }
    public void setStayPeriod(String stayPeriod) { this.stayPeriod = stayPeriod; }

    public int getNights() { return nights; }
    public void setNights(int nights) { this.nights = nights; }

    public double getRoomRate() { return roomRate; }
    public void setRoomRate(double roomRate) { this.roomRate = roomRate; }

    public double getRoomService() { return roomService; }
    public void setRoomService(double roomService) { this.roomService = roomService; }

    public double getFoodOrders() { return foodOrders; }
    public void setFoodOrders(double foodOrders) { this.foodOrders = foodOrders; }

    public double getLaundry() { return laundry; }
    public void setLaundry(double laundry) { this.laundry = laundry; }

    public double getMiniBar() { return miniBar; }
    public void setMiniBar(double miniBar) { this.miniBar = miniBar; }

    // Computed totals
    public double getStayAmount() {
        return nights * roomRate;
    }

    public double getSubtotal() {
        return getStayAmount() + roomService + foodOrders + laundry + miniBar;
    }

    public double getTax() {
        return getSubtotal() * 0.08;
    }

    public double getGrandTotal() {
        return getSubtotal() + getTax();
    }
}
