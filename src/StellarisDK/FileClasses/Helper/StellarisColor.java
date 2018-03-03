package StellarisDK.FileClasses.Helper;

public class StellarisColor {

    private String type;
    private double red;
    private double green;
    private double blue;

    public StellarisColor(){
        type = "hsv";
        red = 0;
        green = 0;
        blue = 0;
    }

    public StellarisColor(String type, double red, double green, double blue) {
        this.type = type;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public StellarisColor(String type, String red, String green, String blue) {
        this.type = type;
        this.red = Double.parseDouble(red);
        this.green = Double.parseDouble(green);
        this.blue = Double.parseDouble(blue);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRed() {
        return red;
    }

    public void setRed(double red) {
        this.red = red;
    }

    public double getGreen() {
        return green;
    }

    public void setGreen(double green) {
        this.green = green;
    }

    public double getBlue() {
        return blue;
    }

    public void setBlue(double blue) {
        this.blue = blue;
    }

    @Override
    public String toString(){
        return type+" { "+red+ " "+ green + " " + blue + " }";
    }
}
