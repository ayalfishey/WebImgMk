/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 *
 * @author Ayalfishey
 */
public class WebsiteImageMaker {

	/**
	 * @param args the command line arguments
	 * 
	 */
	public static void main(String args[]) throws IOException {
		//picture directory
		String filePath;
		String outputPath;
		String Fsep = System.getProperty("file.separator");
		
		//input
		if(args.length == 0){
			//temp default
			filePath = "D:" + Fsep + "EclipseWorkspace"+ Fsep + "WebImgMk" + Fsep + "src" + Fsep + Fsep + "orc.jpg";
		}else{
			filePath=args[0];
		}
		
		//output
		if (args.length <2){
			//temp default
			outputPath = "D:" + Fsep + "EclipseWorkspace" + Fsep + "WebImgMk" + Fsep + "src" + Fsep + Fsep + "orc2.jpg";
		}else{
			outputPath=args[1];
		}

		//get size of picture
		File infile = new File(filePath);
		
		BufferedImage inImage = ImageIO.read(infile);
		int height = inImage.getHeight();
		int width = inImage.getWidth();
		
		int scaledWidth = width/4;
		int scaledHeight = height/4;
		
		//take img and compress it so loading time wont take forever
	 	ResizeImg.resize(inImage, outputPath, scaledWidth, scaledHeight);

		//get new created img
		File outfile = new File(outputPath);
		BufferedImage outImage = ImageIO.read(outfile);
		
		// Getting pixel color by position x and y;
		int[][] red = new int[scaledWidth][scaledHeight];
		int[][] green = new int[scaledWidth][scaledHeight];
		int[][] blue = new int[scaledWidth][scaledHeight];
		for (int x = 0; x < scaledWidth; x++) {
			for (int y = 0; y < scaledHeight; y++) {
				int clr = outImage.getRGB(x, y);
				red[x][y] = (clr & 0x00ff0000) >> 16;
			green[x][y] = (clr & 0x0000ff00) >> 8;
			blue[x][y] = clr & 0x000000ff;
			}
		}

		//initial idea scraped because there was no need to reinvent the wheel
		/*
        int cwidth= width/5;
        int cheight = height/5;
        int[][] cred = new int[cwidth][cheight];
        int[][] cgreen = new int[cwidth][cheight];
        int[][] cblue = new int[cwidth][cheight];
        cred = CompressPixles.CompressColors(red, width, height);
        cgreen = CompressPixles.CompressColors(green, width, height);
        cblue = CompressPixles.CompressColors(blue, width, height);
		 */

		//make a new htm file
		File webp = new File("D:\\EclipseWorkspace\\WebImgMk\\src\\webp.htm");
		
		//New line variable
		String newLineChar = System.getProperty("line.separator");
		
		//makes sure there is a file to write to
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
		
		//html and css code needed for tinting
		//send help I have no idea what im doing
		bw.write("<HTML>" + newLineChar + 
				"<HEAD>" + newLineChar + 
				"<STYLE>" + newLineChar + 
				"img { display: block;}" + newLineChar + 
				"div {" + newLineChar + 
				"  line-height:0;" + newLineChar + 
				"}" + newLineChar + 
				newLineChar +
				".warp {" + newLineChar + 
//				"     overflow: hidden;" + newLineChar + 
				"     width: " + (5 * (scaledWidth)) + "px;" + newLineChar + 
				"     margin: 0 auto;" + newLineChar + 
				"}" + newLineChar + 
				newLineChar +
				".ad {" + newLineChar + 
				"      position: relative;" + newLineChar + 
				"      float: left;" + newLineChar + 
				"      margin-right: -40px;" + newLineChar + 
				"      margin-bottom: -16px;" + newLineChar + 
				"      cursor: pointer;" + newLineChar + 
				"}" + newLineChar +
				newLineChar +
				".ad:before {" + newLineChar + 
				"      content:\"\";" + newLineChar + 
				"      display: block;" + newLineChar + 
				"      position: absolute;" + newLineChar + 
				"      top: 0;" + newLineChar + 
				"      bottom: 0;" + newLineChar + 
				"      left: 0;" + newLineChar + 
				"      right: 0;" + newLineChar + 
				"      background: rgba(255,0,0,0.5);" + newLineChar + 
				"}" + newLineChar +
				newLineChar);
		
		//make a tint for each pixel
		for (int y = 0; y < (scaledHeight); y++) {
			for (int x = 0; x < (scaledWidth); x++) {
				//                System.out.println("x="+x+",y="+y);
				//                System.out.println("w="+cwidth+" h="+cheight);
				bw.write(".ad" + ((scaledWidth) * y + x) + ":before { background: rgba(" + red[x][y] + "," + green[x][y] + "," + blue[x][y] + ",0.5);" + "}");
				bw.newLine();

			}
			bw.newLine();
		}
		bw.write("</STYLE>" + newLineChar +
				 "</HEAD>" + newLineChar + 
				 "<BODY>" + newLineChar + 
				 "<div class=\"warp\">" + newLineChar + 
				 newLineChar + 
//				 " <figure class=\"ad ad" + ((scaledWidth) * 0 + 0) +"\">" + newLineChar + 
//				 "      <img src=\"D:\\EclipseWorkspace\\WebImgMk\\src\\orc.jpg\" alt=\"\" width=\"5\" height=\"5\" />" + newLineChar + 
//				 "</figure>" + 
				 newLineChar);
		
		//temp tests to make each img and give it the tint
		for (int y = 0; y < scaledHeight; y++) {
			for (int x = 0; x < scaledWidth; x++) {
				bw.write("<figure class=\"ad ad" + ((scaledWidth) * y + x) + "\">" + newLineChar + 
						 "      <img src=\"D:\\EclipseWorkspace\\WebImgMk\\src\\orc.jpg\" alt=\"\" width=\"5\" height=\"5\" />" + newLineChar + 
						 "</figure>");
				bw.newLine();
			}
			bw.write("<BR>");
			bw.newLine();
		}
		bw.write("</div><!-- .wrap -->" + newLineChar + "</BODY>" + newLineChar + "</HTML>");
		bw.close();
	}
}
