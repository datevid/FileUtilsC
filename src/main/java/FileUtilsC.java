import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUtilsC {

    /**
     * guarda archivo en una ruta temporal
     * Proceso:
     * Agrega al nuEmi la fecha nuEmi+AAMMDDhhmm
     * Indica el nombre del documento en nombreArchivoInRutaTmpDes
     * Indica el path del documento en pathArchivoInRutaTmpDes
     * @return
     *
     * ver: https://attacomsian.com/blog/java-read-write-binary-files
     * para optimizar ver: https://stackoverflow.com/a/16710301/7105200
     * ver: https://stackoverflow.com/q/2818507/7105200
     */
    public static void saveBytesInPath(byte[] archivo, String pathFileStr, String fileName) throws IOException {
        try {
            String absolutePath = pathFileStr+"/"+fileName;
            FileOutputStream fos = new FileOutputStream(new File(absolutePath));
            BufferedOutputStream writer = new BufferedOutputStream(fos);
            writer.write(archivo);
            writer.flush();
            writer.close();
            archivo = null;//liberamos espacio en memoria
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * genera MD5 de archivos
     * Optimizado para archivos grandes
     * see: https://stackoverflow.com/a/9322214/7105200
     * https://attacomsian.com/blog/java-read-write-binary-files
     * @param pathFileStr
     * @return
     */
    public static String getMD5FromLargeFiles(String pathFileStr, String fileName){
        FileInputStream fis = null;
        MessageDigest md;
        try {
            fis = new FileInputStream(new File(pathFileStr+"/"+fileName));
            md = MessageDigest.getInstance("MD5");
            FileChannel channel = fis.getChannel();
            ByteBuffer buff = ByteBuffer.allocate(2048);
            while(channel.read(buff) != -1)
            {
                buff.flip();
                md.update(buff);
                buff.clear();
            }
            byte[] hashValue = md.digest();
            String md5Str = convertByteArrayToHexString(hashValue);
            return md5Str;
        }
        catch (NoSuchAlgorithmException e)
        {
            return null;
        }
        catch (IOException e)
        {
            return null;
        }
        finally
        {
            try {
                if(fis!=null)fis.close();
            } catch (IOException e) {

            }
        }
    }

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuffer.toString();
    }

    /**
     * Genera el MD5 de un binario
     * Carga todos los bytes a memoria por lo que se recomienda no usarlo para archivos pesados
     * @param bytes
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getMD5FromBinary(byte[] bytes) throws NoSuchAlgorithmException {
        String msgFile = "";
        MessageDigest digest = MessageDigest.getInstance("md5");
        byte[] hashedBytes = digest.digest(bytes);
        msgFile = convertByteArrayToHexString(hashedBytes);
        return msgFile;
    }
}
