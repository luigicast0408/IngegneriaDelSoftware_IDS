package easyExample;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Color red = new Color(255,0,0);
        Circle circle1 = new Circle(20,"Green");
        Circle cloneCircle = (Circle) circle1.myClone();

        circle1.draw();
        cloneCircle.draw();

        cloneCircle.setRadius(30);
        cloneCircle.setColour("Yellow");

        circle1.draw();
        cloneCircle.draw();

        Rectangle rectangle = new Rectangle(10,20,red);
        Rectangle cloneRectangle = (Rectangle) rectangle.myClone();

        rectangle.draw();
        cloneRectangle.draw();

        cloneRectangle.setColor(new Color(0,0,255));
        rectangle.draw();
        cloneRectangle.draw();
    }
}
