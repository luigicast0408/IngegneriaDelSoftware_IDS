package easyExample;

import java.awt.*;

public class Rectangle implements Shape{
    private float width;
    private float height;
    private Color color;

    public Rectangle(float width, float height, Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Shape myClone() {
        return new Rectangle(this.width,this.height, this.color);
    }

    @Override
    public void draw() {
        System.out.println("Rectangle " + width + "x" + height + ", colour: " + color);

    }
}
