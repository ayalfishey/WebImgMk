/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package website.image.maker;

/**
 *
 * @author Ayalfishey
 */
public class CompressPixles {

    public static int[][] CompressColors(int color[][], int sizew, int sizeh) {
        int y;
        boolean endofh= false;
        int counter = 0;
        int newcolor = 0;
        int cpixle =0;
        int[][] compressedp = new int[(sizew / 5)][(sizeh / 5)];
        for (int i = 0; i < sizeh; i = i + 5) {
//            System.out.print("i="+i+", sizew/5="+sizew/5+", sizeh/5="+sizeh/5+"\n");
            counter = 0;
            newcolor = 0;
            for (int x = 0; x < sizew; x++) {
                if (x % 5 == 4 || endofh==true) {
                    compressedp[counter++][i / 5] = newcolor / cpixle;
                    newcolor = 0;
                    cpixle=0;
                    endofh=false;
                }
                for (y = i; y < i + 5; y++) {
                    newcolor = newcolor + color[x][y];
                    cpixle++;
                    //Check if it goes out of bounds
                    if (y+1>sizeh){
                        endofh=true;
                        break;
                    }
                }
            }
        }
        return compressedp;
    }
}
