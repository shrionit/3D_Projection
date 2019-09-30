package pkg3dprojection;

import static java.lang.Math.*;
import javafx.geometry.Point3D;

public class Camera {
    
    public static double[][] create(double aspectRatio, double fieldOfView, double Znear, double Zfar){
        double yScale = ((1 / Math.tan(Math.toRadians(fieldOfView / 2))) * aspectRatio);
        double xScale = yScale / aspectRatio;
        double frustum_length = Zfar - Znear;
        double a = aspectRatio;
        return new double[][]{
            {  xScale,           0,              0,                                      0 },
            {  0,                yScale,         0,                                      0 },
            {  0,                0,              -((Zfar + Znear) / frustum_length),    -((2 * Znear * Zfar) / frustum_length) },
            {  0,                0,              -1, 0 }
            
        };
    }
}
