import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.Key;
import javax.crypto.Cipher;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;
import javax.crypto.spec.SecretKeySpec;

public class AESenc {

    private static final String ALGO = "AES";
    private static final byte[] keyValue =
            new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};

    /**
     * Encrypt a string with AES algorithm.
     *
     * @param data is a string
     * @return the encrypted string
     */
    public static String encrypt(String data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        return new BASE64Encoder().encode(encVal);
    }

    /**
     * Decrypt a string with AES algorithm.
     *
     * @param encryptedData is a string
     * @return the decrypted string
     */
    public static String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        return new String(decValue);
    }

    /**
     * Generate a new encryption key.
     */
    private static Key generateKey() throws Exception {
        return new SecretKeySpec(keyValue, ALGO);
    }
    
    public static void main(String[] args) throws Exception {
    	
    	long startTime = System.currentTimeMillis();
    	AESenc f = new AESenc(); 
		RandomAccessFile fileR = new RandomAccessFile("toEncrypt.txt", "r");
		RandomAccessFile fileW = new RandomAccessFile("encrypted.txt", "rw");
		fileW.setLength(0);
		//fileW.write("racim\n".getBytes("UTF-8"));
		//fileW.write("boussaha\n".getBytes("UTF-8"));
        FileChannel channel = fileR.getChannel();

        System.out.println("File size is: " + channel.size());

        ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());

        channel.read(buffer);

        buffer.flip();//Restore buffer to position 0 to read it

        System.out.println("Reading content and encrypting ... ");
        
        for (int i = 0; i < channel.size(); i++) {
            //System.out.print((char) buffer.get());
           /*fileW.write(
        		   (
        		   f.encrypt(String.valueOf((char) buffer.get()))+"\n"
        		   ).getBytes("UTF-8")
           );*/
            fileW.write(
         		   (( f.encrypt(String.valueOf((char) buffer.get()))
         		   +"\n")
         		   ).getBytes("UTF-8")
            );
        	//fileW.writeChars("racim");
        }
        
        
        
        
        
		//System.out.println(f.encrypt("racim"));
		//System.out.println(f.decrypt("MzQv4BCK9OCrF575HdQQHw==\n" + 
		//		""));
		
		long endTime   = System.currentTimeMillis();

    	long totalTime = endTime - startTime;
    	
    	System.out.println(totalTime);
	}
}