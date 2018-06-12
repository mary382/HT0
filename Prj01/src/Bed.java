public class Bed extends Furniture {
    private String material = "leather";

    public Bed(String furnitureName, int furnitureSquare) {
        super(furnitureName, furnitureSquare);
        setFurnitureMaterial(this.material);
    }

    public Bed(String furnitureName, int furnitureSquare, String material) {
        super(furnitureName, furnitureSquare);
        setFurnitureMaterial(material);
    }

    @Override
    public String getNameOfFurniture() {
        return "bed " + getFurnitureName();
    }
}
