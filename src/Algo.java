import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import parcs.*;

public class Algo implements AM{

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();


    }
    private static void print_hash(int i) throws NoSuchAlgorithmException {
        String originaString = String.valueOf(i);
        MessageDigest digest;

        digest = MessageDigest.getInstance("SHA-256");

        byte[] encodedhash = digest.digest(
                originaString.getBytes(StandardCharsets.UTF_8));
        String sha3Hex = bytesToHex(encodedhash);
        System.out.println(sha3Hex);
    }

    private static List<String> finder(String target, int start, int finish) throws NoSuchAlgorithmException{

        List<String> list=new ArrayList<String>();
        for(int i=start;i<=finish;i++) {
            String originaString = String.valueOf(i);
            MessageDigest digest;

            digest = MessageDigest.getInstance("SHA-256");

            byte[] encodedhash = digest.digest(
                    originaString.getBytes(StandardCharsets.UTF_8));
            String sha3Hex = bytesToHex(encodedhash);
            if (target.equals(sha3Hex)){
                list.add(originaString);
            }
        }
        return list;
    }

    public void run(AMInfo info) {

        String target = (String) info.parent.readObject();
        Integer start = (Integer) info.parent.readObject();
        Integer finish = (Integer) info.parent.readObject();

        System.out.println("Started "+ String.valueOf(start)+" - "+ String.valueOf(finish));

        List<String> res = null;
        try {
            res = finder(target,start,finish);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String out = String.join(", ", res);
        System.out.println("Finished "+ String.valueOf(start)+" - "+ String.valueOf(finish));
        info.parent.write(out);



    }
}
