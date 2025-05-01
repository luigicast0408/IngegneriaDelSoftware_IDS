package easyExample;

public class ShapeFactory {
    public static Shape crateShape(String type) throws IllegalAccessException {
        if (type.equals("circle"))
            return new Circle();
        else if (type.equals("square"))
            return  new Square();
        else
            throw new IllegalAccessException("Tipe of shhape not allowed: "+type);
    }
}
