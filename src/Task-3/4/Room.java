public class Room {
    private int numberRoom;
    private static int roomCount = 0;
    private boolean isEmpty;
    private StatusRoom status;
    private double priceForDay;
    private String clientID;

    public Room(double priceForDay) {
        if (priceForDay <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.numberRoom = ++roomCount;
        isEmpty = true;
        status = StatusRoom.READY;
        this.priceForDay = priceForDay;
    }

    public void signUpTenant(Client client){
        this.clientID = client.getClientId();
        isEmpty = false;
    }

    public void clearRoom(){
        isEmpty = true;
        clientID = null;
        status = StatusRoom.CLEANING_REQUIRED;
    }

    public void completeCleaningRoom(){
        status = StatusRoom.READY;
    }

    public int getNumberRoom() {
        return numberRoom;
    }

    public void needRepair(){
        status = StatusRoom.ON_REPAIR;
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

    public StatusRoom getStatus() {
        return status;
    }

    public String getClientID() {
        return clientID;
    }

    public void setStatus(StatusRoom status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Room{" +
                "numberRoom=" + numberRoom +
                ", isEmpty=" + isEmpty +
                ", status=" + status +
                ", priceForDay=" + priceForDay +
                ", clientID='" + clientID + '\'' +
                '}';
    }
}
