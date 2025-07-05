public class Room {
    private int numberRoom;
    private static int roomCount = 0;
    private boolean isEmpty;
    private RoomCondition roomCondition;
    private double priceForDay;
    private String clientID;

    public Room(double priceForDay) {
        if (priceForDay <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.numberRoom = ++roomCount;
        isEmpty = true;
        roomCondition = RoomCondition.READY;
        this.priceForDay = priceForDay;
    }

    public void signUpTenant(Client client){
        this.clientID = client.getClientId();
        isEmpty = false;
    }

    public void clearRoom(){
        isEmpty = true;
        clientID = null;
        roomCondition = RoomCondition.CLEANING_REQUIRED;
    }

    public void completeCleaningRoom(){
        roomCondition = RoomCondition.READY;
    }

    public int getNumberRoom() {
        return numberRoom;
    }

    public void needRepair(){
        roomCondition = RoomCondition.ON_REPAIR;
        isEmpty = false;
        clientID = null;
    }

    public void setNumberRoom(int numberRoom) {
        this.numberRoom = numberRoom;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public double getPriceForDay() {
        return priceForDay;
    }

    public void setPriceForDay(double priceForDay) {
        if (priceForDay <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.priceForDay = priceForDay;
    }

    public RoomCondition getRoomCondition() {
        return roomCondition;
    }

    public String getClientId() {
        return clientID;
    }

    public void setRoomCondition(RoomCondition status) {
        this.roomCondition = status;
    }

    @Override
    public String toString() {
        return "Room{" +
                "numberRoom=" + numberRoom +
                ", isEmpty=" + isEmpty +
                ", status=" + roomCondition +
                ", priceForDay=" + priceForDay +
                ", clientID='" + clientID + '\'' +
                '}';
    }
}
