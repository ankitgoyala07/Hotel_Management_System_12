package model;

/**
 * Model class representing billing and invoice data.
 * Simplified to support: Room charges, Room Service & Amenities, and Food Orders.
 */
public class BillingModel {
    private String guestId;
    private String roomId;
    private String stayPeriod;
    private int nights;
    private double roomRate;
    private double roomService;
    private double foodOrders;
    private String roomType;
    private String discountDeal;

    public BillingModel() {
        this.guestId = "001";
        this.roomId = "101";
        this.stayPeriod = "Oct 14 - Oct 18 (4 Nights)";
        this.nights = 4;
        this.roomRate = 80.00;
        this.roomService = 0.0;
        this.foodOrders = 0.0;
        this.roomType = "Single";
        this.discountDeal = "None";
    }

    public BillingModel(String guestId, String roomId, String stayPeriod, int nights, double roomRate,
                        double roomService, double foodOrders, String roomType, String discountDeal) {
        this.guestId = guestId;
        this.roomId = roomId;
        this.stayPeriod = stayPeriod;
        this.nights = nights;
        this.roomRate = roomRate;
        this.roomService = roomService;
        this.foodOrders = foodOrders;
        this.roomType = roomType;
        this.discountDeal = discountDeal;
    }

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

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public String getDiscountDeal() { return discountDeal; }
    public void setDiscountDeal(String discountDeal) { this.discountDeal = discountDeal; }

    public boolean hasDiscount() {
        return discountDeal != null && !discountDeal.trim().isEmpty() && !discountDeal.equalsIgnoreCase("None");
    }

    public double getStayAmount() {
        return nights * roomRate;
    }

    public double getSubtotalBeforeDiscount() {
        return getStayAmount() + roomService + foodOrders;
    }

    public double getDiscountAmount() {
        if (hasDiscount()) {
            return getSubtotalBeforeDiscount() * 0.10;
        }
        return 0.0;
    }

    public double getSubtotal() {
        return getSubtotalBeforeDiscount() - getDiscountAmount();
    }

    public double getTax() {
        return getSubtotal() * 0.08;
    }

    public double getGrandTotal() {
        return getSubtotal() + getTax();
    }
}
