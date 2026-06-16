package model;

public class roommanagementModel {
    private final String roomNumber;
    private final String roomType;
    private final String status;
    private final double pricePerNight;

    public roommanagementModel(String roomNumber, String roomType, String status, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.status = status;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomNumber() {
        if (roomNumber.startsWith("#")) {
            return roomNumber;
        }
        return "#" + roomNumber;
    }

    public String getRoomType() {
        // Normalize room type to match mockup (Single -> Single bed, Double -> Double bed, Suite/Deluxe -> VIP)
        if (roomType.equalsIgnoreCase("Single")) {
            return "Single bed";
        } else if (roomType.equalsIgnoreCase("Double")) {
            return "Double bed";
        } else if (roomType.equalsIgnoreCase("Suite") || roomType.equalsIgnoreCase("Deluxe")) {
            return "VIP";
        }
        return roomType;
    }

    public String getRoomFloor() {
        // Extract floor from room number (e.g. "101" -> "Floor - 1")
        String cleanNum = roomNumber.replace("#", "").trim();
        if (!cleanNum.isEmpty()) {
            char firstChar = cleanNum.charAt(0);
            if (Character.isDigit(firstChar)) {
                return "Floor - " + firstChar;
            }
        }
        return "Floor - 1";
    }

    public String getRoomFacility() {
        // Return standard facilities matching mockup based on room type
        String type = getRoomType();
        if (type.equalsIgnoreCase("Single bed")) {
            return "AC, shower, bed, TV";
        } else if (type.equalsIgnoreCase("Double bed")) {
            return "AC, shower, Double bed, towel bathtub, TV";
        } else {
            return "AC, shower, Double bed, towel bathtub, TV, Mini-bar, Jacuzzi";
        }
    }

    public String getStatus() {
        return status;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }
}
