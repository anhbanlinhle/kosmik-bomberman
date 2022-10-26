package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.image.Image;
import uet.oop.bomberman.Map;

import java.util.List;

public abstract class DynamicEntity extends Entity {

    private int maxHP;

    protected boolean isAlive;

    protected int speed;
    protected DIRECTION direction;
    protected STATUS status;
    protected int healthPoint;
    protected int countFrame;

    List<Entity> mapEntity;

    public DynamicEntity(int x, int y, Image img) {
        super(x, y, img);
    }

    public DynamicEntity(int x, int y, Image img, List<Entity> map) {
        super(x, y, img);
        this.mapEntity = map;
    }

    public int getMaxHP() {
        return this.maxHP;
    }

    public int getHealthPoint() {
        return this.healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public DIRECTION getDirection() {
        return this.direction;
    }

    public void setDirection(DIRECTION direction) {
        this.direction = direction;
    }

    public STATUS getStatus() {
        return this.status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int convertToMapCordinate(int n) {
        int newCor = n + Sprite.DEFAULT_SIZE;
        newCor = newCor - newCor % Sprite.SCALED_SIZE;
        newCor /= Sprite.SCALED_SIZE;
        return newCor;
    }

    public boolean checkCollisionMap(Map map, int a, int b, DIRECTION direction) {

        // player's map cordinate
        int xMap = convertToMapCordinate(this.x);
        int yMap = convertToMapCordinate(this.y);

        // cor for check
        int xCheck;
        int yCheck;
        ENTITY_TYPE type_check;

        // check up
        if (direction == DIRECTION.UP) {
            xCheck = xMap;
            yCheck = yMap - 1;

            // just up
            type_check = map.entityTypeAtCordinate(xCheck, yCheck);
            if (type_check == ENTITY_TYPE.BRICK
                    || type_check == ENTITY_TYPE.WALL
                    || type_check == ENTITY_TYPE.BOMB) {
                if (b < (yCheck + 1) * 32) {
                    return false;
                }
            }


            // up left
            type_check = map.entityTypeAtCordinate(xCheck - 1, yCheck);
            if (type_check == ENTITY_TYPE.BRICK
                    || type_check == ENTITY_TYPE.WALL
                    || type_check == ENTITY_TYPE.BOMB) {
                if (b < (yCheck + 1) * 32 && a < (xCheck - 1) * 32 + 30) {
                    return false;
                }
            }

            // up right
            type_check = map.entityTypeAtCordinate(xCheck + 1, yCheck);
            if (type_check == ENTITY_TYPE.BRICK
                    || type_check == ENTITY_TYPE.WALL
                    || type_check == ENTITY_TYPE.BOMB) {
                if (b < (yCheck + 1) * 32 && a > (xCheck + 1) * 32 - 30) {
                    return false;
                }
            }

        }

        // check down
        if (direction == DIRECTION.DOWN) {
            xCheck = xMap;
            yCheck = yMap + 1;

            // just down
            type_check = map.entityTypeAtCordinate(xCheck, yCheck);
            if (type_check == ENTITY_TYPE.BRICK
                    || type_check == ENTITY_TYPE.WALL
                    || type_check == ENTITY_TYPE.BOMB) {
                if (b > (yCheck - 1) * 32) {
                    return false;
                }
            }

            // down left
            type_check = map.entityTypeAtCordinate(xCheck - 1, yCheck);
            if (type_check == ENTITY_TYPE.BRICK
                    || type_check == ENTITY_TYPE.WALL
                    || type_check == ENTITY_TYPE.BOMB) {
                if (b > (yCheck - 1) * 32 && a < (xCheck - 1) * 32 + 30) {
                    return false;
                }
            }

            // down right
            type_check = map.entityTypeAtCordinate(xCheck + 1, yCheck);
            if (type_check == ENTITY_TYPE.BRICK
                    || type_check == ENTITY_TYPE.WALL
                    || type_check == ENTITY_TYPE.BOMB) {
                if (b > (yCheck - 1) * 32 && a > (xCheck + 1) * 32 - 30) {
                    return false;
                }
            }
        }

        // check left
        if (direction == DIRECTION.LEFT) {
            xCheck = xMap - 1;
            yCheck = yMap;

            // just left
            type_check = map.entityTypeAtCordinate(xCheck, yCheck);
            if (type_check == ENTITY_TYPE.BRICK
                    || type_check == ENTITY_TYPE.WALL
                    || type_check == ENTITY_TYPE.BOMB) {
                if (a < (xCheck + 1) * 32) {
                    return false;
                }
            }

            // left up
            type_check = map.entityTypeAtCordinate(xCheck, yCheck - 1);
            if (type_check == ENTITY_TYPE.BRICK
                    || type_check == ENTITY_TYPE.WALL
                    || type_check == ENTITY_TYPE.BOMB) {
                if (a < (xCheck + 1) * 32 && b < (yCheck - 1) * 32 + 30) {
                    return false;
                }
            }

            // left down
            type_check = map.entityTypeAtCordinate(xCheck, yCheck + 1);
            if (type_check == ENTITY_TYPE.BRICK
                    || type_check == ENTITY_TYPE.WALL
                    || type_check == ENTITY_TYPE.BOMB) {
                if (a < (xCheck + 1) * 32 && b > (yCheck + 1) * 32 - 30) {
                    return false;
                }
            }
        }
        // check right
        if (direction == DIRECTION.RIGHT) {
            xCheck = xMap + 1;
            yCheck = yMap;

            // just right
            type_check = map.entityTypeAtCordinate(xCheck, yCheck);
            if (type_check == ENTITY_TYPE.BRICK
                    || type_check == ENTITY_TYPE.WALL
                    || type_check == ENTITY_TYPE.BOMB) {
                if (a - 4 > (xCheck - 1) * 32) {
                    return false;
                }
            }

            // right up
            type_check = map.entityTypeAtCordinate(xCheck, yCheck - 1);
            if (type_check == ENTITY_TYPE.BRICK
                    || type_check == ENTITY_TYPE.WALL
                    || type_check == ENTITY_TYPE.BOMB) {
                if (a - 4 > (xCheck - 1) * 32 && b < (yCheck - 1) * 32 + 30) {
                    return false;
                }
            }

            // right down
            type_check = map.entityTypeAtCordinate(xCheck, yCheck + 1);
            if (type_check == ENTITY_TYPE.BRICK
                    || type_check == ENTITY_TYPE.WALL
                    || type_check == ENTITY_TYPE.BOMB) {
                if (a - 4 > (xCheck - 1) * 32 && b > (yCheck + 1) * 32 - 30) {
                    return false;
                }
            }
        }
        return true;
    }

    public enum DIRECTION {
        UP,
        RIGHT,
        LEFT,
        DOWN
    }

    public enum STATUS {
        WALK,
        IDLE
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }
}