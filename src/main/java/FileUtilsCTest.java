import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;

public class FileUtilsCTest {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        //saveBytesInPath_test();
        getMD5FromLargeFiles_test();
        getMD5FromBinary_test();
    }

    public static void saveBytesInPath_test() {
        byte[] archivo = new byte[]{1,2,3};
        String pathFileStr = "/logs"; //en local es j:, es rutaBase
        String fileName = "archivo_abc.txt";
        try {
            FileUtilsC.saveBytesInPath(archivo, pathFileStr, fileName);
            System.out.println("Documento guardado correctamente");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getMD5FromLargeFiles_test() {
        String pathFileStr = "L:\\descargas";
        String fileName = "ireport.zip";
        long time_start, time_end;
        double millis;

        time_start = System.currentTimeMillis();
        String md5 = FileUtilsC.getMD5FromLargeFiles(pathFileStr,fileName);
        System.out.println("MD5 large files");
        System.out.println(md5);
        time_end = System.currentTimeMillis();
        millis = ((time_end - time_start) / 1000.0);
        System.out.println("se terminó en "+millis+" milisegundos");
        System.out.println("----------");
    }

    public static void getMD5FromBinary_test() throws IOException, NoSuchAlgorithmException {
        String pathFileStr = "L:\\descargas";
        String fileName = "ireport.zip";
        long time_start, time_end;
        double millis;

        time_start = System.currentTimeMillis();
        System.out.println("MD5 binary");
        File file = new File(pathFileStr+"/"+fileName);
        byte[] bytes = Files.readAllBytes(file.toPath());
        String md52 = FileUtilsC.getMD5FromBinary(bytes);
        System.out.println(md52);
        time_end = System.currentTimeMillis();
        millis = ((time_end - time_start) / 1000.0);
        System.out.println("se terminó en "+millis+" milisegundos");
    }
}
