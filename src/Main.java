import java.io.*;
import parcs.*;


import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main {


    public static void main(String[] args) throws Exception {
        task curtask = new task();
        curtask.addJarFile("Algo.jar");

        AMInfo info = new AMInfo(curtask, null);

        String[] file = readLines("input.txt");
        String target = file[0];
        int global_start = Integer.parseInt(file[1]);
        int global_finish = Integer.parseInt(file[2]);
        int n = Integer.parseInt(file[3]);

        System.out.println("Start executing");
        long startTime = System.nanoTime();

        List<channel> channels = new ArrayList<>();
        List<String> list=new ArrayList<String>();
        int delta = (global_finish- global_start)/n;
//        for(int i = 0; i<n;i++){
//            int s = i*delta;
//            int f = (i+1)*delta-1;
//            point p = info.createPoint();
//            channel c =p.createChannel();
//            p.execute("Algo");
//            c.write(target);
//            c.write(s);
//            c.write(f);
//            channels.add(c);
//
//        }
        int i=0;
        int s = i*delta;
        int f = (i+1)*delta-1;
        System.out.println("Creating point1");
        point p = info.createPoint();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Creating channel1");
        channel c =p.createChannel();
        channels.add(c);
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Execute point1");
        p.execute("Algo");
        TimeUnit.SECONDS.sleep(1);
        c.write(target);
        c.write(s);
        c.write(f);


        i=1;
        s = i*delta;
        f = (i+1)*delta-1;
        System.out.println("Creating point2");
        point p2 = info.createPoint();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Creating channel2");
        channel c2 =p2.createChannel();
        channels.add(c2);
        TimeUnit.SECONDS.sleep(1);
        p2.execute("Algo");
        TimeUnit.SECONDS.sleep(1);
        c2.write(target);
        c2.write(s);
        c2.write(f);

        



        i = 1;
        for (parcs.channel channel : channels) {
            System.out.println("\n\n\n\n Processing point" + String.valueOf(i));
            i++;

            String out_list = (String) channel.readObject();
            list.add(out_list);


        }

        double estimatedTime = (double) (System.nanoTime() - startTime) / 1000000000;
        System.out.println("Time total (excluding IO): " + estimatedTime);
        System.out.println("Result: ");
        System.out.println(list);

        curtask.end();
    }


    public static String read(String path) {
        Path path_to_read = Paths.get(path);

        String contents = null;
        try {
            contents = Files.readString(path_to_read, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println("Trouble with reading from file");
        }
        return contents;
    }

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
}

