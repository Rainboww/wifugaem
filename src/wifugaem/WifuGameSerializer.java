package wifugaem;

import java.io.*;

public class WifuGameSerializer {

    private static final String savename = "savefile.wifu";

    public static void makeFolders() {
        File dir = new File("./saves");
        dir.mkdirs();
    }


    //could probably use the playscreen object too? or some kinda registry, or actually knowing better

    public static void serializeToPlayer(Creature player) {
        try {
            FileOutputStream fileOut = new FileOutputStream("./saves/" + savename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(player);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Creature deserializeFromPlayer() {
        Creature player;
        try {
            FileInputStream fileIn = new FileInputStream("./saves/" + savename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            player = (Creature) in.readObject();
            in.close();
            fileIn.close();
            return player;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}