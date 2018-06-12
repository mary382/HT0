public class Chair extends Furniture {
    private String material = "plastic";

    public Chair(String furnitureName, int furnitureSquare) {
        super(furnitureName, furnitureSquare);
        setFurnitureMaterial(this.material);
    }

    public Chair(String furnitureName, int furnitureSquare, String material) {
        super(furnitureName, furnitureSquare);
        setFurnitureMaterial(material);
    }

    @Override
    public String getNameOfFurniture() {
        return "chair " + getFurnitureName();
    }

}
