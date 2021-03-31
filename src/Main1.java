import parcs.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class Main1 {


    public static String[] readLines(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;

        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }

        bufferedReader.close();

        return lines.toArray(new String[lines.size()]);
    }

    public static void main(String[] args) throws IOException {
        task curtask = new task();
        curtask.addJarFile("Algo.jar");
        AMInfo info = new AMInfo(curtask, null);
//        FileReader fileReader = new FileReader("input.txt");

        String[] file = readLines("input.txt");
        String target = file[0];
        int global_start = Integer.parseInt(file[1]);
        int global_finish = Integer.parseInt(file[2]);
        int n = Integer.parseInt(file[3]);

//        String line;
//        BufferedReader bufferedReader = new BufferedReader(fileReader);
        long startTime = System.nanoTime();

        int delta = (global_finish- global_start)/n;
        for(int i = 0; i<n;i++){
            int s = i*delta;
            int f = (i+1)*delta-1;
            point p = info.createPoint();
            channel c =p.createChannel();
            p.execute("Algo");
            c.write(target);
            c.write(s);
            c.write(f);
            //channels.add(c);
            String out = (String)c.readObject();
            System.out.println(out);
            //list.add(out);

        }

        double estimatedTime = (double) (System.nanoTime() - startTime) /
                1000000000;
        System.out.println("Total time: " + estimatedTime);
        curtask.end();
    }
}