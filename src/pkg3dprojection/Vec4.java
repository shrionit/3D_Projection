package pkg3dprojection;

import javafx.geometry.Point3D;

public class Vec4 {
    public double x = 0;
    public double y = 0;
    public double z = 0;
    public double w = 0;
    
    public Vec4(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
        w = 1;
    }
    
    public Vec4(Point3D p){
        this.x = p.getX();
        this.y = p.getY();
        this.z = p.getZ();
        w = 1;
    }
    
    public static Vec4 toVec4(double[][] mat){
        return new Vec4(mat[0][0], mat[1][0], mat[2][0]);
    }
    
    public Point3D getPoint(){
        return new Point3D(x, y, z);
    }
    
    public double[][] toMat4(){
        return new double[][]{
            {x},
            {y},
            {z},
            {w}
        };
    }
    
    public double[][] toMat3(){
        return new double[][]{
            {x},
            {y},
            {z}
        };
    }
    
}
