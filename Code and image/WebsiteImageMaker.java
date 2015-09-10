/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package website.image.maker;

import java.io.*;
import java.nio.file.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 *
 * @author Ayalfishey
 */
public class WebsiteImageMaker {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
        //picture derectory
        File file = new File("D:" + System.getProperty("file.separator") + "work"
                + System.getProperty("file.separator") + "Website Image Maker" + System.getProperty("file.separator")
                + "src" + System.getProperty("file.separator") + "website" + System.getProperty("file.separator")
                + "image" + System.getProperty("file.separator") + "maker" + System.getProperty("file.separator")
                + "orc.jpg");
        BufferedImage image = ImageIO.read(file);
        // int y=0;
        // int x=0;
        // Getting pixel color by position x and y
        int height = image.getHeight();
        int width = image.getWidth();
        int[][] red = new int[width][height];
        int[][] green = new int[width][height];
        int[][] blue = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int clr = image.getRGB(x, y);
                red[x][y] = (clr & 0x00ff0000) >> 16;
                green[x][y] = (clr & 0x0000ff00) >> 8;
                blue[x][y] = clr & 0x000000ff;
            }
        }
        int cwidth= width/5;
        int cheight = height/5;
        int[][] cred = new int[cwidth][cheight];
        int[][] cgreen = new int[cwidth][cheight];
        int[][] cblue = new int[cwidth][cheight];
        cred = CompressPixles.CompressColors(red, width, height);
        cgreen = CompressPixles.CompressColors(green, width, height);
        cblue = CompressPixles.CompressColors(blue, width, height);

        //make a new htm file
        File webp = new File("D:\\work\\Website Image Maker\\src\\website\\image\\maker\\webp.htm");
        //New line
        String newLineChar = System.getProperty("line.separator");
        if (!webp.exists()) {
            webp.createNewFile();
        }
        FileWriter fw = new FileWriter(webp.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        /*
         <HTML>
         <HEAD>
         ...
         */
        //css code needed for tinting
        bw.write("<HTML>" + newLineChar + "<HEAD>" + newLineChar + "<STYLE>" + newLineChar + "img { display: block;}" + newLineChar + "div {" + newLineChar + "  line-height:0;" + newLineChar + "}" + newLineChar + newLineChar
                + ".warp {" + newLineChar + "     overflow: hidden;" + newLineChar + "    width: " + (5 * (width/5)) + "px;" + newLineChar + "     margin: 0 auto;" + newLineChar + "}" + newLineChar + newLineChar
                + ".ad {" + newLineChar + "      position: relative;" + newLineChar + "      float: left;" + newLineChar + "     margin-right: -40px;" + newLineChar + "      margin-bottom: -16px;" + newLineChar + "     cursor: pointer;" + newLineChar + "}" + newLineChar
                + ".ad:before {" + newLineChar + "   content:\"\";" + newLineChar + "  display: block;" + newLineChar + "  position: absolute;" + newLineChar + "  top: 0;" + newLineChar + "  bottom: 0;" + newLineChar + "   left: 0;" + newLineChar + "right: 0;" + newLineChar + "   background: rgba(255,0,0,0.5);" + newLineChar + "}" + newLineChar);
        //make a tint for each pixle
        for (int y = 0; y < (cheight); y++) {
            for (int x = 0; x < (cwidth); x++) {
//                System.out.println("x="+x+",y="+y);
//                System.out.println("w="+cwidth+" h="+cheight);
                bw.write(".ad" + ((cwidth) * y + x) + ":before { background: rgba(" + cred[x][y] + "," + cgreen[x][y] + "," + cblue[x][y] + ",0.5);" + "}");
                bw.newLine();

            }
            bw.newLine();
        }
        bw.write("</STYLE>" + newLineChar + "</HEAD>" + newLineChar + "<BODY>" + newLineChar + "<div class=\"warp\">" + newLineChar + newLineChar + " <figure class=\"ad\">" + newLineChar + "      <img src=\"http://cdn.impressivewebs.com/2011-11/greece001.jpg\" alt=\"\" width=\"5\" height=\"5\" />" + newLineChar + "    </figure>" + newLineChar);
        //make each img and give it a tint
        for (int y = 0; y < cheight; y++) {
            for (int x = 0; x < cwidth; x++) {
                bw.write("<figure class=\"ad ad" + ((cwidth) * y + x) + "\">" + newLineChar + "      <img src=\"http://cdn.impressivewebs.com/2011-11/greece006.jpg\" alt=\"\" width=\"5\" height=\"5\" />" + newLineChar + "    </figure>");
                bw.newLine();
            }
            bw.write("<BR>");
            bw.newLine();
        }
        bw.write("</div><!-- .wrap -->" + newLineChar + "</BODY>" + newLineChar + "</HTML>");
        bw.close();
    }
}
    