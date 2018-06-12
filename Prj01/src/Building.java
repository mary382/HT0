import java.util.ArrayList;

public class Building {

    private String buildingName;
    private ArrayList<Room> rooms = new ArrayList<>();

    public Building(String buildingName) {
        this.buildingName = buildingName;
    }

    public void addRoom(String roomName, int roomSquare, int windowQuantity) {
        Room someRoom = new Room(roomName, roomSquare, windowQuantity);
        rooms.add(someRoom);
    }

    public Room getRoom(String roomName) {
        Room someRoom = null;
        for (Room room : rooms) {
            if (room.getRoomName().equals(roomName)) {
                someRoom = room;
            }
        }
        //если комнаты с данным названием не существует созд исключение NoSuchRoomException
        if (someRoom == null) {
            try {
                throw new NoSuchRoomException("There are no room with this name.");
            } catch (NoSuchRoomException e) {
                System.out.println(e.getMessage());
            }
            someRoom = new Room(roomName, 7, 2);
            rooms.add(someRoom);
            return someRoom;
        } else {
            return someRoom;
        }
    }

    public void deleteRoom(String roomName) {
        int n = 0;
        for (Room room : rooms) {
            if (room.getRoomName().equals(roomName)) {
                rooms.remove(room);
                n++;
            }
        }
        if (n == 0) {
            System.out.println("No room with name " + roomName + " has been found.");
        } else {
            System.out.println(n + " room(s) with name " + roomName + " has(have) been deleted.");
        }
    }

    public void describe() {
        System.out.println("Building: " + buildingName);
        if (rooms.size() > 0) {
            for (Room room : rooms) {
                System.out.println(room.toString());
            }
        } else {
            System.out.println("There are no rooms.");
        }
    }
}
