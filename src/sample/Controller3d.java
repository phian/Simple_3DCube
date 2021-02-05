package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class Controller3d extends VBox {

    private Slider xs;
    private Slider ys;
    private Slider zs;
    private HBox xBox, yBox, zBox;

    public Controller3d() {}

    public Controller3d(String title, Property<Number> x, Property<Number> y, Property<Number> z, double max) {
        xs = new Slider();
        xs.setMax(max);
        xs.setValue(x.getValue().doubleValue());
        x.bindBidirectional(xs.valueProperty());

        ys = new Slider();
        ys.setMax(max);
        ys.setValue(y.getValue().doubleValue());
        y.bindBidirectional(ys.valueProperty());

        zs = new Slider();
        zs.setMax(max);
        zs.setValue(z.getValue().doubleValue());
        z.bindBidirectional(zs.valueProperty());

        // Add Slider và Label cho Slider
        xBox = new HBox();
        yBox = new HBox();
        zBox = new HBox();
        xBox.getChildren().addAll(new Label("x :\t"), xs);
        yBox.getChildren().addAll(new Label("y :\t"), ys);
        zBox.getChildren().addAll(new Label("z :\t"), zs);

        // Set căng khoảng cách giữa các Slider
        VBox.setMargin(xBox, new Insets(10, 0, 0, 104));
        VBox.setMargin(yBox, new Insets(10, 0, 0, 104));
        VBox.setMargin(zBox, new Insets(10, 0, 30, 104));

        // Add Slider vào pane
        getChildren().addAll(
                new Label(title),
                xBox, yBox, zBox
        );
    }

    // Xử lỹ mỗi khi data của Slider thay đổi
    public Controller3d(String title, double max, Callback<double[], Object> apply) {
        this(title, max);
        InvalidationListener l = (o) -> apply.call(new double[]{
                xs.getValue(), ys.getValue(), zs.getValue()
        });
        xs.valueProperty().addListener(l);
        ys.valueProperty().addListener(l);
        zs.valueProperty().addListener(l);
    }

    private Controller3d(String title, double max) {
        xs = new Slider();
        xs.setMax(max);

        ys = new Slider();
        ys.setMax(max);

        zs = new Slider();
        zs.setMax(max);

        // Add Slider và Label cho Slider
        xBox = new HBox();
        yBox = new HBox();
        zBox = new HBox();
        xBox.getChildren().addAll(new Label("x :\t"), xs);
        yBox.getChildren().addAll(new Label("y :\t"), ys);
        zBox.getChildren().addAll(new Label("z :\t"), zs);

        // Set căng khoảng cách giữa các Slider
        VBox.setMargin(xBox, new Insets(10, 0, 0, 104));
        VBox.setMargin(yBox, new Insets(10, 0, 0, 104));
        VBox.setMargin(zBox, new Insets(10, 0, 30, 104));

        // Add Slider vào pane
        getChildren().addAll(
                new Label(title),
                xBox, yBox, zBox
        );
    }

    // Di chuyển tọa đổ của Camera
    public Controller3d translation(Node n, double limit) {
        return new Controller3d("Coordinates", n.translateXProperty(), n.translateYProperty(), n.translateZProperty(), limit);
    }

    // 3 trục Slider x, y và z và dùng 3 giá trị này cập nhật tọa độ quay cho Box
    public Controller3d rotationAxis(Node n, double limit) {
        Controller3d c = new Controller3d("\n\t\t\t\tRotation Axis Sliders", limit,
                (d) -> {
                    n.setRotationAxis(new Point3D(d[0], d[1], d[2]));
                    return null;
                }
        );
        return c;
    }

    // Slider để quay box theo giá trị 3 trục
    public VBox rotation(Node n, double limit) {
        VBox c = new VBox();
        c.getChildren().add(new Label("\n\tRotation Slider"));

        Slider s = new Slider();
        s.setMax(limit);
        s.valueProperty().bindBidirectional(n.rotateProperty());
        s.setValue(50); // Value ban đầu
        VBox.setMargin(s, new Insets(10, 0, 0, 24));

        c.getChildren().add(s);

        return c;
    }
}