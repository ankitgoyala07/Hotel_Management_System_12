package model;

/**
 * Model representation of a Room entity with fields for number, type, floor, facility, price, and status.
 */
public class roommanagementModel {
    private final String roomNumber;
    private final String roomType;
    private final String roomFloor;
    private final String roomFacility;
    private final String status;
    private final double pricePerNight;

    // Full constructor (for database loading)
    public roommanagementModel(String roomNumber, String roomType, String roomFloor, String roomFacility, String status, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomFloor = roomFloor;
        this.roomFacility = roomFacility;
        this.status = status;
        this.pricePerNight = pricePerNight;
    }

    // Legacy/Short constructor (for adding new rooms)
    public roommanagementModel(String roomNumber, String roomType, String status, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.status = status;
        this.pricePerNight = pricePerNight;
        this.roomFloor = calculateFloor(roomNumber);
        this.roomFacility = calculateFacility(roomType);
    }

    private String calculateFloor(String number) {
        String cleanNum = number.replace("#", "").trim();
        if (!cleanNum.isEmpty()) {
            char firstChar = cleanNum.charAt(0);
            if (Character.isDigit(firstChar)) {
                return "Floor - " + firstChar;
            }
        }
        return "Floor - 1";
    }

    private String calculateFacility(String type) {
        String normalized = type.trim();
        if (normalized.equalsIgnoreCase("Single") || normalized.equalsIgnoreCase("Single bed") || normalized.equalsIgnoreCase("Single Bed Room")) {
            return "AC, shower, bed, TV";
        } else if (normalized.equalsIgnoreCase("Double") || normalized.equalsIgnoreCase("Double bed") || normalized.equalsIgnoreCase("Double Bed Room")) {
            return "AC, shower, Double bed, towel bathtub, TV";
        } else {
            return "AC, shower, Double bed, towel bathtub, coffee, drinks, fridge, TV";
        }
    }

    public String getRoomNumber() {
        if (roomNumber.startsWith("#")) {
            return roomNumber;
        }
        return "#" + roomNumber;
    }

    public String getRoomType() {
        if (roomType.trim().equalsIgnoreCase("Single") || roomType.trim().equalsIgnoreCase("Single bed") || roomType.trim().equalsIgnoreCase("Single Bed Room")) {
            return "Single bed";
        } else if (roomType.trim().equalsIgnoreCase("Double") || roomType.trim().equalsIgnoreCase("Double bed") || roomType.trim().equalsIgnoreCase("Double Bed Room")) {
            return "Double bed";
        } else if (roomType.trim().equalsIgnoreCase("Suite") || roomType.trim().equalsIgnoreCase("Deluxe") || roomType.trim().equalsIgnoreCase("VIP")) {
            return "VIP";
        }
        return roomType;
    }

    public String getRoomFloor() {
        return roomFloor != null ? roomFloor : calculateFloor(roomNumber);
    }

    public String getRoomFacility() {
        return roomFacility != null ? roomFacility : calculateFacility(roomType);
    }

    public String getStatus() {
        return status;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }
}
