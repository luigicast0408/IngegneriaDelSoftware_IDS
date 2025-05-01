package easyExample;

public class Circle implements Shape{
    private float radius;
    private String colour;

    public Circle(float radius, String colour){
        this.radius = radius;
        this.colour = colour;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public Shape myClone() {
        return new Circle(this.radius,this.colour);
    }


    @Override
    public void draw() {
        System.out.println("Cicle of radius: "+radius +" and colour: "+colour);
    }
}
