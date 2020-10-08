import com.sun.org.apache.bcel.internal.classfile.Utility;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        String helpMsg = "---------Example---------\n"
                        + "Decode:\n"
                        + "java -jar BCELCodeman.jar d [BCEL_CODE]\n"
                        + "Encode:\n"
                        + "java -jar BCELCodeman.jar e [Class_Filepath]\n\n"
                        + "  -=Coding By F1tz=-";
        try{
            switch (args[0]){
                case "d" :
                    String bcelCode = args[1];
                    decode(bcelCode);
                    break;
                case "e" :
                    String classPath = args[1];
                    encode(classPath);
                    break;
                default :
                    System.out.println(helpMsg);
            }
        }catch (Exception e){
            System.out.println(helpMsg);
            return;
        }
    }

    public static void decode(String cdata){
        String path = "./Decoded.class";
        if(cdata.startsWith("$$BCEL$$")){
            cdata = cdata.substring(8);
        }
        String cryptdata= cdata;
        FileOutputStream fos = null;
        FileChannel channel = null;
        try {
            fos = new FileOutputStream(path);
            channel = fos.getChannel();
            byte[] array =  Utility.decode(cryptdata,true);
            ByteBuffer buffer = ByteBuffer.wrap(array);
            channel.write(buffer);
            System.out.println("[*] Decode BCELcode successfully, find Class file in ./Decoded.class");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void encode(String Classpath) {
        Path path = Paths.get(Classpath);
        try {
            byte[] data = Files.readAllBytes(path);
            String s = Utility.encode(data, true);
            System.out.println("$$BCEL$$" + s);
            System.out.println("\n[*] Encode BCELcode successfully. Have fun :)");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
