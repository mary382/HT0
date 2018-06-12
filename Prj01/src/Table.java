public class Table extends Furniture {

    private String material = "plastic";

    public Table(String furnitureName, int furnitureSquare) {
        super(furnitureName, furnitureSquare);
        setFurnitureMaterial(this.material);
    }

    public Table(String furnitureName, int furnitureSquare, String material) {
        super(furnitureName, furnitureSquare);
        setFurnitureMaterial(material);
    }

    @Override
    public String getNameOfFurniture() {
        return "table " + getFurnitureName();
    }
}
