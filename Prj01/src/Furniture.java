public abstract class Furniture {

    private String furnitureName;
    private int furnitureSquare;
    private String furnitureMaterial;

    public Furniture(String furnitureName, int furnitureSquare) {
        this.furnitureName = furnitureName;
        this.furnitureSquare = furnitureSquare;
    }

    public String getFurnitureName() {
        return furnitureName;
    }

    public int getFurnitureSquare() {
        return furnitureSquare;
    }

    public String getFurnitureMaterial() {
        return furnitureMaterial;
    }

    public void setFurnitureName(String furnitureName) {
        this.furnitureName = furnitureName;
    }

    public void setFurnitureSquare(int furnitureSquare) {
        this.furnitureSquare = furnitureSquare;
    }

    public void setFurnitureMaterial(String furnitureMaterial) {
        this.furnitureMaterial = furnitureMaterial;
    }

    public abstract String getNameOfFurniture(); //метод для переопределения классами-наследниками

    public String getInformationOfFurniture() {
        return String.valueOf("Square of furniture: " + getFurnitureSquare() + " m^2, material of furniture: " + getFurnitureMaterial());
    }
}
