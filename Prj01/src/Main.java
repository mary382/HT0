public class Main {
    public static void main(String[] args) {
        Building building=new Building("Building 1");
        building.addRoom("123", 12, 3);
        building.getRoom("123").add(new Bulb(300),new Bulb(300));
        building.getRoom("123").add(new Chair("Royal",1));
        building.getRoom("123").add(new Chair("Bee",1, "plastic"));
        building.getRoom("123").add(new Bed("Multy",2));
        building.getRoom("123").add(new Table("IKEA",1, "wood"));

        building.describe();
    }
}
