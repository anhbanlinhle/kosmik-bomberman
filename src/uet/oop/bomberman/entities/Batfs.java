package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.controller.Sound;
import uet.oop.bomberman.graphics.Sprite;

import java.util.*;

import static uet.oop.bomberman.BombermanGame.bomberman;
import static uet.oop.bomberman.BombermanGame.map;

public class Batfs extends Enemy {
    int count = 0;
    DIRECTION lastDir;

    public Batfs(int x, int y, Image img) {
        super(x, y, img);
        direction = DIRECTION.NOT_MOVE;
        lastDir = DIRECTION.NOT_MOVE;
        speed = 1;
    }

    public void getNextDirection() {
        List<List<Integer>> formatMap = map.formatMapData();

        int endX = bomberman.getMapY();
        int endY = bomberman.getMapX();

        int startX = getMapY();
        int startY = getMapX();


        int width = map.getWidth();
        int height = map.getHeight();

        if (startX == endX && startY == endY) direction = DIRECTION.NOT_MOVE;

        formatMap.get(endX).set(endY, 0);
        formatMap.get(startX).set(startY, 0);


        Queue<Pair<Integer, Integer>> q = new LinkedList<>();
        q.add(new Pair<>(startX, startY));

        int[][] distance = new int[height][width];

        boolean[][] visited = new boolean[height][width];
        visited[startX][startY] = true;

        Pair<Integer, Integer>[][] last = new Pair[height][width];
        last[startX][startY] = new Pair<>(-1, -1);

//        for (int i = 0; i < height; i++) {
//            for (int j = 0; j < width; j++) {
//                System.out.println(formatMap.h);
//            }
//        }

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!q.isEmpty()) {
            Pair<Integer, Integer> tmp = q.poll();

            for (int i = 0; i < 4; i++) {
                int newX = tmp.getKey() + dx[i];
                int newY = tmp.getValue() + dy[i];

                if (newY >= 0 && newY < width && newX >= 0 && newX < height && formatMap.get(newX).get(newY) == 0 && !visited[newX][newY]) {
                    q.add(new Pair<>(newX, newY));
                    distance[newX][newY] = distance[tmp.getKey()][tmp.getValue()] + 1;
                    last[newX][newY] = new Pair<>(tmp.getKey(), tmp.getValue());
                    visited[newX][newY] = true;
                }
            }
        }

        if(q == null) super.getRandomDirection();
        if (distance[endX][endY] == 0) return;

        List<Pair<Integer, Integer>> pathCoordinate = new ArrayList<>();
        int X = last[endX][endY].getKey();
        int Y = last[endX][endY].getValue();
        pathCoordinate.add(0, new Pair<>(endX, endY));



        while (true) {
            if (last[X][Y].getKey() == -1 && last[X][Y].getValue() == -1) {
                pathCoordinate.add(0, new Pair<>(X, Y));
                break;
            }

            pathCoordinate.add(0, new Pair<>(X, Y));
            int tmpX = X;
            int tmpY = Y;
            X = last[tmpX][tmpY].getKey();
            Y = last[tmpX][tmpY].getValue();
        }

        // for (int i = 0; i < pathCoordinate.size(); i++) {
        //     System.out.print(pathCoordinate.get(i). getKey() + " " +  pathCoordinate.get(i).getValue()  + "| ");
        // }
        // System.out.println();
        // System.out.println("------");

        //get next direction
        if (pathCoordinate.get(1).getKey() - pathCoordinate.get(0).getKey() == 0 && pathCoordinate.get(1).getValue() - pathCoordinate.get(0).getValue() > 0) {
            direction = DIRECTION.RIGHT;
        } else
        if (pathCoordinate.get(1).getKey() - pathCoordinate.get(0).getKey() == 0 && pathCoordinate.get(1).getValue() - pathCoordinate.get(0).getValue() < 0) {
            direction = DIRECTION.LEFT;
        } else
        if (pathCoordinate.get(1).getKey() - pathCoordinate.get(0).getKey()  < 0 && pathCoordinate.get(1).getValue() - pathCoordinate.get(0).getValue() == 0) {
            direction = DIRECTION.UP;
        } else
        if (pathCoordinate.get(1).getKey() - pathCoordinate.get(0).getKey() > 0 && pathCoordinate.get(1).getValue() - pathCoordinate.get(0).getValue() == 0) {
            direction = DIRECTION.DOWN;
        }

    }

    public Image setFrame() {
        return switch (direction) {
            case LEFT ->
                    Sprite.movingSprite(Sprite.batfs_left1, Sprite.batfs_left2, Sprite.batfs_left3, countFrame, 60).getFxImage();
            case RIGHT ->
                    Sprite.movingSprite(Sprite.batfs_right1, Sprite.batfs_right2, Sprite.batfs_right3, countFrame, 60).getFxImage();
            case UP ->
                    Sprite.movingSprite(Sprite.batfs_up1, Sprite.batfs_up2, Sprite.batfs_up3, countFrame, 60).getFxImage();
            case DOWN ->
                    Sprite.movingSprite(Sprite.batfs_down1, Sprite.batfs_down2, Sprite.batfs_down3, countFrame, 60).getFxImage();
            case NOT_MOVE ->
                    Sprite.movingSprite(Sprite.batfs_down3, Sprite.batfs_down3, Sprite.batfs_down3, countFrame, 60).getFxImage();
        };
    }

    @Override
    public void updateMove(Map map) {
        switch (direction){
            case UP:
                if (checkCollisionMap(map, x, y - speed, DIRECTION.UP, ENTITY_TYPE.BRICK)
                        && checkCollisionMap(map, x, y - speed, DIRECTION.UP, ENTITY_TYPE.WALL)
                        && checkCollisionMap(map, x, y - speed, DIRECTION.UP, ENTITY_TYPE.BOMB)) {
                    lastDir = DIRECTION.UP;
                    y -= speed;
                } else alternateMoven();
                break;
            case DOWN:
                if (checkCollisionMap(map, x, y + speed, DIRECTION.DOWN, ENTITY_TYPE.BRICK)
                        && checkCollisionMap(map, x, y + speed, DIRECTION.DOWN, ENTITY_TYPE.WALL)
                        && checkCollisionMap(map, x, y + speed, DIRECTION.DOWN, ENTITY_TYPE.BOMB)) {
                    lastDir = DIRECTION.DOWN;
                    y += speed;
                } else alternateMoven();
                break;
            case LEFT:
                if (checkCollisionMap(map, x - speed, y, DIRECTION.LEFT, ENTITY_TYPE.BRICK)
                        && checkCollisionMap(map, x - speed, y, DIRECTION.LEFT, ENTITY_TYPE.WALL)
                        && checkCollisionMap(map, x - speed, y, DIRECTION.LEFT, ENTITY_TYPE.BOMB)) {
                    lastDir = DIRECTION.LEFT;
                    x -= speed;
                } else alternateMoven();
                break;
            case RIGHT:
                if (checkCollisionMap(map, x + speed, y, DIRECTION.RIGHT, ENTITY_TYPE.BRICK)
                        && checkCollisionMap(map, x + speed, y, DIRECTION.RIGHT, ENTITY_TYPE.WALL)
                        && checkCollisionMap(map, x + speed, y, DIRECTION.RIGHT, ENTITY_TYPE.BOMB)) {
                    lastDir = DIRECTION.RIGHT;
                    x += speed;
                } else alternateMoven();
                break;
            default:
                System.out.println("move random");
                super.getRandomDirection();
                break;
        }
    }

    @Override
    public void update() {
        super.update();
        getNextDirection();
        img = setFrame();
        countFrame++;
    }

    public void alternateMoven(){
        switch (lastDir){
            case UP:
                if (checkCollisionMap(map, x, y - speed, DIRECTION.UP, ENTITY_TYPE.BRICK)
                        && checkCollisionMap(map, x, y - speed, DIRECTION.UP, ENTITY_TYPE.WALL)
                        && checkCollisionMap(map, x, y - speed, DIRECTION.UP, ENTITY_TYPE.BOMB)) {
                    y -= speed;
                }
                break;
            case DOWN:
                if (checkCollisionMap(map, x, y + speed, DIRECTION.DOWN, ENTITY_TYPE.BRICK)
                        && checkCollisionMap(map, x, y + speed, DIRECTION.DOWN, ENTITY_TYPE.WALL)
                        && checkCollisionMap(map, x, y + speed, DIRECTION.DOWN, ENTITY_TYPE.BOMB)) {
                    y += speed;
                }
                break;
            case LEFT:
                if (checkCollisionMap(map, x - speed, y, DIRECTION.LEFT, ENTITY_TYPE.BRICK)
                        && checkCollisionMap(map, x - speed, y, DIRECTION.LEFT, ENTITY_TYPE.WALL)
                        && checkCollisionMap(map, x - speed, y, DIRECTION.LEFT, ENTITY_TYPE.BOMB)) {
                    x -= speed;
                }
                break;
            case RIGHT:
                if (checkCollisionMap(map, x + speed, y, DIRECTION.RIGHT, ENTITY_TYPE.BRICK)
                        && checkCollisionMap(map, x + speed, y, DIRECTION.RIGHT, ENTITY_TYPE.WALL)
                        && checkCollisionMap(map, x + speed, y, DIRECTION.RIGHT, ENTITY_TYPE.BOMB)) {
                    x += speed;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void die() {
        img = Sprite.batfs_dead.getFxImage();
    }

    public void loadDie(int count) {
        img = Sprite.movingSprite(Sprite.purple_dead1, Sprite.purple_dead2, Sprite.purple_dead3, count, 36).getFxImage();
    }
}