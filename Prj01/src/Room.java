import java.util.ArrayList;

public class Room {

    private String roomName;
    private int roomSpace;
    private int squareForFurniture;
    private int light;
    private int quantityOfWindow;
    private ArrayList<Furniture> pieceOfFurniture = new ArrayList<>();
    private ArrayList<Bulb> lightBulbs = new ArrayList<>();

    private static final int MIN_LIGHT = 300;
    private static final int MAX_LIGHT = 4000;
    private static final double MAX_FILLED_WITH_FURNITURE = 0.7;
    private static final int WINDOW_LIGHT = 700;
    private static final int MIN_WINDOW_NUMBER = 1;
    private static final int MAX_WINDOW_NUMBER = 5;


    public Room(String roomName, int roomSpace, int quantityOfWindow) {
        this.roomName = roomName;
        if (roomSpace <= 0) {
            try {
                throw new SpaceUsageTooFewException("Room area must be a positive number.");
            } catch (SpaceUsageTooFewException e) {
                System.out.println(e.getMessage());
            }
        } else {
            this.roomSpace = roomSpace;
        }

        if (quantityOfWindow > MAX_WINDOW_NUMBER) {
            try {
                throw new IlluminanceTooMuchException("An attempt to add " + quantityOfWindow + " windows has led to the lightness excess. The maximum amount of windows (5) will be set.");
            } catch (IlluminanceTooMuchException e) {
                System.out.println(e.getMessage());
                this.quantityOfWindow = MAX_WINDOW_NUMBER;
                light = MAX_WINDOW_NUMBER * WINDOW_LIGHT;
            }
        } else if (quantityOfWindow < MIN_WINDOW_NUMBER) {
            System.out.println("There must be at least one window at the room, so that room could have enough light.");
            this.quantityOfWindow = MIN_WINDOW_NUMBER;
            light = MIN_WINDOW_NUMBER * WINDOW_LIGHT;
        } else {
            this.quantityOfWindow = quantityOfWindow;
            light = quantityOfWindow * WINDOW_LIGHT;
        }
    }

    public int getSquareForFurniture() {
        return squareForFurniture;
    }

    public ArrayList<Furniture> getFurniture() {
        return pieceOfFurniture;
    }

    public int getLight() {
        return light;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getRoomSpace() {
        return roomSpace;
    }

    public void setRoomSpace(int roomSpace) {
        //проверка, чтобы мебель не занимала больше 70%S
        if (roomSpace < getSquareForFurniture() / MAX_FILLED_WITH_FURNITURE) {
            try {
                throw new SpaceUsageTooMuchException("Setting room area to " + roomSpace + " m^2 would lead to excess of admissible space covered with furniture. Room area won't be change.");
            } catch (SpaceUsageTooMuchException e) {
                System.out.println(e.getMessage());
            }
        } else if (roomSpace > 0) {
            this.roomSpace = roomSpace;
        } else {
            System.out.println("Room area must be a positive number.");
        }
    }

    public int getQuantityOfWindow() {
        return quantityOfWindow;
    }

    public int getBulbsLight() {
        int bulbsLight = 0;
        for (Bulb bulb : lightBulbs) {
            bulbsLight += bulb.getLight();
        }
        return bulbsLight;
    }

    public void setQuantityOfWindow(int quantityOfWindow) {
        int newLight = getBulbsLight() + quantityOfWindow * WINDOW_LIGHT;
        if (newLight > MAX_LIGHT) {
            try {
                throw new IlluminanceTooMuchException("Adding \"+windowQuantity+\" windows would lead to the lightness excess. Try to set less windows or remove some lightbulbs.");
            } catch (IlluminanceTooMuchException e) {
                System.out.println(e.getMessage());
            }
        } else if (newLight < MIN_LIGHT) {
            System.out.println("There is no enough lightness in the room. You can add some lightbulbs and try to reset windows again.");
        } else {
            this.quantityOfWindow = quantityOfWindow;
            light = newLight;
        }
    }

    public void add(Furniture... furnitures) {
        for (Furniture furniture : furnitures) {
            squareForFurniture += furniture.getFurnitureSquare();
            if (squareForFurniture < roomSpace * MAX_FILLED_WITH_FURNITURE) {
                pieceOfFurniture.add(furniture);
            } else {
                try {
                    throw new SpaceUsageTooMuchException("Adding " + furniture.getFurnitureSquare() + "m^2 " + furniture.getFurnitureName() + " would lead to excess. of 70% of room space occupied with furniture. You can increase area of the room and try to add it again.");

                } catch (SpaceUsageTooMuchException e) {
                    System.out.println(e.getMessage());
                }
                squareForFurniture -= furniture.getFurnitureSquare();
            }
        }
    }

    public void add(Bulb... bulbs) {
        for (Bulb bulb : bulbs) {
            light += bulb.getLight();
            if (light < MAX_LIGHT) {
                lightBulbs.add(bulb);
            } else {
                try {
                    throw new IlluminanceTooMuchException("Adding lightbulb with " + bulb.getLight() + " lx would lead to the lightness excess. Not added.");
                } catch (IlluminanceTooMuchException e) {
                    System.out.println(e.getMessage());
                }
                light -= bulb.getLight();
            }
        }
    }

    @Override
    public String toString() {
       int freeSpace=roomSpace-squareForFurniture;
       int freeSpaceInProcents=freeSpace*100/roomSpace;
       StringBuilder lightness=new StringBuilder();
       if(quantityOfWindow>0){
           lightness.append(quantityOfWindow+" window(s) 700 lx each,");
       }
       if(lightBulbs.size()>0){
           lightness.append(" lightbulbs:");
           for(Bulb bulb:lightBulbs){
               lightness.append(" "+bulb.getLight()+",");
           }
           lightness.deleteCharAt(lightness.lastIndexOf(","));
       }else {
           lightness.deleteCharAt(lightness.lastIndexOf(","));
       }

       StringBuilder roomInfo=new StringBuilder(" Room: "+roomName+ '\n'+" Light = "+getLight()+" lx("+lightness+")"+'\n'+" Square = "+getRoomSpace()+" m^2 (free space: "+freeSpace+" m^2 or "+freeSpaceInProcents+"%)");
       if(getFurniture().size()>0){
           roomInfo.append('\n'+" Furniture:"+'\n');
           for(Furniture furniture:getFurniture()){
               roomInfo.append("  "+furniture.getFurnitureName()+" ("+furniture.getInformationOfFurniture()+")"+'\n');
           }
       }else{
           roomInfo.append('\n'+" Without furniture"+'\n');
       }
       return String.valueOf(roomInfo);
    }
}