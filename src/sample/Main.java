package sample;

import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import java.util.Random;

public class Main extends Cube3DApp {

    protected void add3dObjects() {
        Controller3d controller3d = new Controller3d();
        Random r = new Random();
        Button resetButton = new Button("Reset");

        // Box
        Box box = new Box(512, 512, 512);

        // Random màu cho box
        PhongMaterial material = new PhongMaterial(Color.web("4cc9f0"));
        // Set màu cho hộp
        box.setMaterial(material);

        // Set vị trí đặt của box
        box.setTranslateX(400);
        box.setTranslateY(-700);
        box.setTranslateZ(1000);

        int rotSpeed = r.nextInt(3); // Tốc độ quay khi rotate
        box.setRotationAxis(new Point3D(0, -1, 0));
        box.setRotate(box.getRotate() + rotSpeed);

        // Phần camera
        PerspectiveCamera cam = new PerspectiveCamera();

        // Tab hiển thị các thanh Slider
        addControllerTab("Sliders Tab",
//                Controller3d.translation(cam, 1000),
                controller3d.rotation(box, 360),
                controller3d.rotationAxis(box, 3),
                resetButton
        );

        // Căng khoảng cách với bottom của pane 1 đoạn
        VBox.setMargin(box, new Insets(0, 0, 20, 0));

        // Add box vào pane hiển thị
        pane.getChildren().add(box);

        // Đèn
        PointLight light = new PointLight(Color.WHITESMOKE);
        light.translateXProperty().bind(cam.translateXProperty());
        light.translateYProperty().bind(cam.translateYProperty());
        light.translateZProperty().bind(cam.translateZProperty());
        pane.getChildren().add(light);

        cam.setTranslateY(-1000);
        subScene.setCamera(cam);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
