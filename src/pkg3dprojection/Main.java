package pkg3dprojection;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.geometry.Point3D;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.animation.AnimationTimer;
import static java.lang.Math.*;


public class Main extends Application{

    private final double WIDTH = 1366;
    private final double HEIGHT = 768;
    private final String X = "X", Y = "Y", Z = "Z";
    private Canvas canvas;
    private GraphicsContext gc;
    
    //----------------------------variables------------------------------
    private double strokeWeight = 1;
    private double angleX = 0.0;
    private double angleY = 0.0;
    private double angleZ = 0.0;
    private double fov = 90;
    private void Log(Object o){System.out.println(o);}
    private Point3D points[] = new Point3D[]{
        new Point3D( -0.5,  0.5,  0.5 ),
        new Point3D( -0.5, -0.5,  0.5 ),
        new Point3D(  0.5, -0.5,  0.5 ),
        new Point3D(  0.5,  0.5,  0.5 ),
        new Point3D( -0.5,  0.5, -0.5 ),
        new Point3D( -0.5, -0.5, -0.5 ),
        new Point3D(  0.5, -0.5, -0.5 ),
        new Point3D(  0.5,  0.5, -0.5 )
    };
    
    private double[][] rotateX;
    private double[][] rotateY;
    private double[][] rotateZ;

    
    private double projectionOrtho[][] = new double[][]{
        {1, 0, 0},
        {0, 1, 0},
        {0, 0, 1}
    };
    
    //-------------------------------------------------------------------
    
    private void setup(){
        canvas = new Canvas(WIDTH, HEIGHT);
        canvas.setFocusTraversable(true);
        gc = canvas.getGraphicsContext2D();
        gc.translate(WIDTH/2, HEIGHT/2);
    }
    
    private void update(){
        gc.setFill(Color.rgb(0, 0, 0));
        gc.fillRect(-WIDTH/2, -HEIGHT/2, WIDTH, HEIGHT);
        canvas.setOnKeyPressed(k -> {
            if(k.getCode() == KeyCode.UP){
                fov+=100;
                System.out.println(fov);
            }
            if(k.getCode() == KeyCode.DOWN){
                fov-=100;
            }
        });
    }
    
    private void draw(){
        gc.setStroke(Color.RED);
        strokeWeight(5);
        Point3D projectedPoints[] = new Point3D[8];
        int index = 0;
        for(Point3D p : points){
            p = p.multiply(100);
            Point3D rotated = Matrix.matmul(rotate(X, angleX), p);
            rotated = Matrix.matmul(rotate(Y, angleY), rotated);
            rotated = Matrix.matmul(rotate(Z, angleZ), rotated);
            Vec4 projected2d = Vec4.toVec4(Matrix.matmul(Camera.create((HEIGHT/WIDTH), fov, -1, -500), new Vec4(rotated).toMat4()));
            //System.out.println(projected2d.getX() + " " + projected2d.getY() + " " +projected2d.getZ());
            projectedPoints[index] = projected2d.getPoint();
            //point(projected2d.getX(), projected2d.getY());
            index++;
        }
        strokeWeight(10);
        for(Point3D p : projectedPoints){
            point(p.getX(), p.getY(), Color.RED);
        }
        
        for (int i = 0; i < 4; i++) {
            connect(i, (i+1) % 4, projectedPoints);
            connect(i+4, ((i+1) % 4)+4, projectedPoints);
            connect(i, i+4, projectedPoints);
        }
        
    }
    
    //------------small functions
    
    private void point(double x1, double y1, Color c){
        gc.setFill(c);
        x1 = x1 - (strokeWeight/2);
        y1 = y1 - (strokeWeight/2);
        gc.fillOval(x1, y1, strokeWeight, strokeWeight);
    }
    
    private void connect(int i, int j, Point3D[] point){
        Point3D a = point[i];
        Point3D b = point[j];
        gc.setStroke(Color.GREEN);
        gc.strokeLine(a.getX(), a.getY(), b.getX(), b.getY());
        
    }
    
    private void strokeWeight(double w){
        strokeWeight = w;
    }
    
    private double[][] rotate(String AXIS, double angle){
        switch(AXIS){
            case "X":
                return new double[][]{
                        { 1, 0,           0          },
                        { 0, cos(angle), -sin(angle) },
                        { 0, sin(angle),  cos(angle) }
                    };
            case "Y":
                return new double[][]{
                        {  cos(angle), 0, sin(angle) },
                        {  0,          1, 0          },
                        { -sin(angle), 0, cos(angle) }
                    };
            case "Z":
                return new double[][]{
                        {  cos(angle), -sin(angle), 0 },
                        {  sin(angle),  cos(angle), 0 },
                        {  0,           0,          1 }
                    };
            default:
                return new double[0][3];
        }
    }
    
    
    //---------------------------
    
    
    
    private Parent getContent(){
        Pane root = new Pane();
        setup();
        new AnimationTimer(){
            public void handle(long now){
                update();
                draw();
                angleX += toRadians(1);
                angleY += toRadians(1);
                angleZ += toRadians(1);
            }
        }.start();
        root.getChildren().add(canvas);
        return root;
    }
    
    public void start(Stage stage) throws Exception{
        stage.setTitle("3D Projection");
        stage.setScene(new Scene(getContent(), WIDTH, HEIGHT));
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
