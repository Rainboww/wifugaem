package wifugaem;

import java.io.*;

public class Serializer {

    private final String filename = "worldfile.dat";

    public void serializeWorld(World world) {
        try {
            FileOutputStream fileOut = new FileOutputStream("/saves/" + filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(world);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public World deserializeWorld() {
        World world;
        try {
            FileInputStream fileIn = new FileInputStream("/saves/" + filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            world = (World) in.readObject();
            in.close();
            fileIn.close();
            return world;
        } catch (Exception e) {
            e.printStackTrace();
            return new WorldBuilder(10,10).build();
        }
    }
}