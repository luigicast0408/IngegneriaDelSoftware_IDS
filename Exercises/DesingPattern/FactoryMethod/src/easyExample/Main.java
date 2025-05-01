package easyExample;

import javax.print.attribute.standard.SheetCollate;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Shape shape1 = ShapeFactory.crateShape("circle");
        Shape shape2 = ShapeFactory.crateShape("square");

        shape1.draw();
        shape2.draw();
    }

}
