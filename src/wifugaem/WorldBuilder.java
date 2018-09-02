package wifugaem;

import java.util.Random;

public class WorldBuilder {
    private int width;
    private int height;
    private Tile[][] tiles;

    public WorldBuilder(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
    }

    public World build() {
        return new World(tiles);
    }

    private WorldBuilder randomizeField() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = Math.random() < 0.05 ? new Tile(TileBase.GROUND, TileFilling.TREE) : new Tile(TileBase.GRASS);
            }
        }
        return this;
    }

    private WorldBuilder addAccessibleBuildings(int size, int attempts) {
        //size should be smaller than map size
        if (size > width || size > height) size = Math.min(width, height) - 1;
        //dont try to place zero interior sized buildings
        if (size < 3) return this;
        boolean isPossible = true;
        //check number of valid entry tiles for purpose of spreading evenly.. ish
        int validTiles = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (tiles[x][y].canEnter() && tiles[x][y].getBase() != TileBase.BUILDING_FLOOR) validTiles++;
            }
        }
        //random needed later
        Random random = new Random();

        //keep going while free tiles exist and buildings are left to place, can probably way optimize that
        while (attempts > 0 && isPossible) {
            isPossible = false;

            //just go over all tiles
            for (int x = 0; x < width && attempts > 0; x++) {
                for (int y = 0; y < height && attempts > 0; y++) {
                    //only check valid entrance tiles
                    if (tiles[x][y].isEmpty() && tiles[x][y].getBase() != TileBase.BUILDING_FLOOR) {
                        //dont always go for the first tile, spread out using earlier valid tiles check

                        int leewayXMinus = (x > size - 2) ? size - 2 : x;
                        int leewayXPlus = (width - x - 1 > size - 2) ? size - 2 : width - x - 1;
                        int leewayYMinus = (y > size - 2) ? size - 2 : y;
                        int leewayYPlus = (height - y - 1 > size - 2) ? size - 2 : height - y - 1;

                        boolean canBuildUp = checkBuildUp(x, y, size);
                        boolean canBuildDown = checkBuildDown(x, y, size);
                        boolean canBuildleft = checkBuildLeft(x, y, size);
                        boolean canBuildRight = checkBuildRight(x, y, size);
                        //randomize build direction
                        int buildOptions = 0;
                        if (canBuildDown) buildOptions++;
                        if (canBuildleft) buildOptions++;
                        if (canBuildRight) buildOptions++;
                        if (canBuildUp) buildOptions++;

                        if (buildOptions == 0) continue;
                        else isPossible = true;

                        if (Math.random() < ((double) attempts / validTiles)) {
                            //sadly can't do nextint(0)
                            int rnd;
                            if (buildOptions == 1) rnd = 0;
                            else rnd = random.nextInt(buildOptions - 1);

                            //select chosen option
                            if (canBuildUp) {
                                if (rnd == 0) {
                                    //randomly place in available space
                                    if (leewayXMinus + leewayXPlus + 1 == size) {
                                        buildBuilding(x - leewayXMinus, y - size, size);
                                    } else {
                                        int offsetX = random.nextInt((leewayXMinus + 1 + leewayXPlus) - size);
                                        buildBuilding(x - leewayXMinus + offsetX, y - size, size);
                                    }
                                    tiles[x][y - 1] = new Tile(TileBase.BUILDING_FLOOR, TileFilling.DOOR_CLOSED);
                                    attempts--;
                                }
                                rnd--;
                            }
                            if (canBuildDown) {
                                if (rnd == 0) {
                                    if (leewayXMinus + leewayXPlus + 1 == size) {
                                        buildBuilding(x - leewayXMinus, y + 1, size);
                                    } else {
                                        int offsetX = random.nextInt((leewayXMinus + 1 + leewayXPlus) - size);
                                        buildBuilding(x - leewayXMinus + offsetX, y + 1, size);
                                    }
                                    tiles[x][y + 1] = new Tile(TileBase.BUILDING_FLOOR, TileFilling.DOOR_CLOSED);
                                    attempts--;
                                }
                                rnd--;
                            }
                            if (canBuildleft) {
                                if (rnd == 0) {
                                    if (leewayYMinus + leewayYPlus + 1 == size) {
                                        buildBuilding(x - size, y - leewayYMinus, size);
                                    } else {
                                        int offsetY = random.nextInt((leewayYMinus + 1 + leewayYPlus) - size);
                                        buildBuilding(x - size, y - leewayYMinus + offsetY, size);
                                    }
                                    tiles[x - 1][y] = new Tile(TileBase.BUILDING_FLOOR, TileFilling.DOOR_CLOSED);
                                    attempts--;
                                }
                                rnd--;
                            }
                            if (canBuildRight) {
                                if (rnd == 0) {
                                    if (leewayYMinus + leewayYPlus + 1 == size) {
                                        buildBuilding(x + 1, y - leewayYMinus, size);
                                    } else {
                                        int offsetY = random.nextInt((leewayYMinus + 1 + leewayYPlus) - size);
                                        buildBuilding(x + 1, y - leewayYMinus + offsetY, size);
                                    }
                                    tiles[x + 1][y] = new Tile(TileBase.BUILDING_FLOOR, TileFilling.DOOR_CLOSED);
                                    attempts--;
                                }
                            }
                        }

                    }
                }
            }
        }
        return this;
    }

    //check for each direction
    private boolean checkBuildUp(int x, int y, int size) {
        //no space to fit it up
        if (y < size) return false;
        //cant do corners
        if (x == 0 || x == width - 1) return false;
        return true;
    }

    private boolean checkBuildDown(int x, int y, int size) {
        if (y > height - size - 1) return false;
        if (x == 0 || x == width - 1) return false;
        return true;
    }

    private boolean checkBuildLeft(int x, int y, int size) {
        if (x < size) return false;
        if (y == 0 || y == height - 1) return false;
        return true;
    }

    private boolean checkBuildRight(int x, int y, int size) {
        if (x > width - size - 1) return false;
        if (y == 0 || y == height - 1) return false;
        return true;
    }

    //build single doorless square building
    private void buildBuilding(int startX, int startY, int size) {
        //bottom wall
        for (int i = 0; i < size; i++) {
            tiles[startX + i][startY + size - 1] = new Tile(TileBase.BUILDING_FLOOR, TileFilling.BUILDING_WALL);
        }
        //top wall
        for (int i = 0; i < size; i++) {
            tiles[startX + i][startY] = new Tile(TileBase.BUILDING_FLOOR, TileFilling.BUILDING_WALL);
        }
        //left wall
        for (int i = 0; i < size - 2; i++) {
            tiles[startX][startY + i + 1] = new Tile(TileBase.BUILDING_FLOOR, TileFilling.BUILDING_WALL);
        }
        //right wall
        for (int i = 0; i < size - 2; i++) {
            tiles[startX + size - 1][startY + i + 1] = new Tile(TileBase.BUILDING_FLOOR, TileFilling.BUILDING_WALL);
        }
        //fill inside
        for (int i = 0; i < size - 2; i++) {
            for (int j = 0; j < size - 2; j++) {
                tiles[startX + j + 1][startY + i + 1] = new Tile(TileBase.BUILDING_FLOOR);
            }
        }
    }

    private WorldBuilder randomizeCave() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = Math.random() < 0.5 ? new Tile(TileBase.GROUND) : new Tile(TileBase.GROUND, TileFilling.DIRT_WALL);
            }
        }
        return this;
    }

    private WorldBuilder smoothCave(int times) {
        Tile[][] tiles2 = new Tile[width][height];
        for (int time = 0; time < times; time++) {

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int floors = 0;
                    int walls = 0;

                    for (int ox = -1; ox < 2; ox++) {
                        for (int oy = -1; oy < 2; oy++) {
                            if (x + ox < 0 || x + ox >= width || y + oy < 0
                                    || y + oy >= height)
                                continue;

                            if (tiles[x + ox][y + oy].isEmpty())
                                floors++;
                            else
                                walls++;
                        }
                    }
                    tiles2[x][y] = floors >= walls ? new Tile(TileBase.GROUND) : new Tile(TileBase.GROUND, TileFilling.DIRT_WALL);
                }
            }
            tiles = tiles2;
        }
        return this;
    }

    public WorldBuilder makeCaves() {
        return randomizeCave().smoothCave(8);
    }

    public WorldBuilder makeFields() {
        return randomizeField();
    }

    public WorldBuilder addBuildings() {
        return addAccessibleBuildings(10, 3);
    }
}