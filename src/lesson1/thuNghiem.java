package lesson1;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class thuNghiem {
    public int ucll(int a, int b){
        if (b==0) return a;
        else {
            a=b;
            b= a%b;
            return ucll(a, b);
        }
    }
    public void lamTest(String outputName) throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(outputName));
        Random random = new Random();
        for (int i=1; i<=5000; i++){

            dos.writeBytes(String.format("%02d:%02d:%02d",random.nextInt(24),random.nextInt(60),random.nextInt(60))+"\n");
        }
    }
    public static void main(String[] args) {
        List<Integer> listColl = new ArrayList<>();
        List<Integer> listQuick = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("temp_prices.txt")));
            String str = br.readLine();
            while (str!=null){
                listColl.add(Integer.parseInt(str));
                listQuick.add(Integer.parseInt(str));
                str=br.readLine();
            }
            Collections.sort(listColl);
            Sorts.quickSort(listQuick.stream().mapToInt(Integer::intValue).toArray());
            DataOutputStream dos = new DataOutputStream(new FileOutputStream("input/thinghiem.txt"));
            DataOutputStream dos2 = new DataOutputStream(new FileOutputStream("input/thinghiem2.txt"));
            for (int i :listColl){
                dos.writeBytes(String.valueOf(i)+"\n");
            }
            for (int j :listColl){
                dos2.writeBytes(String.valueOf(j)+"\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
