package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.Map;

public abstract class Entity {
    // Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    // Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    protected ENTITY_TYPE type;

    public Entity() {
    }

    // Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public void update(Map map) {

    }

    public void update() {

    }

    public ENTITY_TYPE getType() {
        return this.type;
    }

    public void setType(ENTITY_TYPE type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public enum ENTITY_TYPE {
        BRICK,
        BOMB,
        WALL,
        FLAME,
        GRASS
    }
}
