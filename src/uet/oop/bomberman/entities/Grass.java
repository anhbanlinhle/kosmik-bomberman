package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Grass extends Entity {
    public Grass(int x, int y, Image img) {
        super(x, y, img);
        setType(ENTITY_TYPE.GRASS);
    }
}
