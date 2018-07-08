package wifugaem;

import java.io.*;

public class WifuGameSerializer {

    private static final String worldname = "worldfile.dat";
    private static final String playername = "player.dat";

    public static void makeFolders() {
        File dir = new File("./saves");
        dir.mkdirs();
    }

    public static void serializePlayer(Creature player) {
        try {
            FileOutputStream fileOut = new FileOutputStream("./saves/" + playername);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(player);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Creature deserializePlayer(World world) {
        Creature player;
        try {
            FileInputStream fileIn = new FileInputStream("./saves/" + playername);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            player = (Creature) in.readObject();
            in.close();
            fileIn.close();
            return player;
        } catch (Exception e) {
            e.printStackTrace();
            return new CreatureFactory(world).newPlayer();
        }
    }

    public static void serializeWorld(World world) {
        try {
            FileOutputStream fileOut = new FileOutputStream("./saves/" + worldname);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(world);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static World deserializeWorld() {
        World world;
        try {
            FileInputStream fileIn = new FileInputStream("./saves/" + worldname);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            world = (World) in.readObject();
            in.close();
            fileIn.close();
            return world;
        } catch (Exception e) {
            e.printStackTrace();
            return new WorldBuilder(90,31).build();
        }
    }
}