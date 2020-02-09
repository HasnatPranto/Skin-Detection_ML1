import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class PredictorClass {

    double[][][] pixelPro=new double[256][256][256];
    int pix;
    double threshold=1.5;
    Color wh = new Color(255,255,255);
    Color bl = new Color(0);

    public void reloadData() throws IOException {

        int i=0,j=0,f=0;
        String []field;
        String row;
        BufferedReader fileReader = new BufferedReader(new FileReader("F:\\IITian's\\IDEA_WS\\Skin Detection\\Data\\dataSet.txt"));

        while ((row = fileReader.readLine()) != null) {
            field=row.split(",");

            for(f=0;f<256;f++)
                pixelPro[i][j][f] = Double.parseDouble(field[f]);
            if(f==256){
                j++;
                if(j==256){
                    i++;
                    j=0;
                }
            }
        }

        fileReader.close();
    }


    public void createImage(List<File> images) throws IOException {

        reloadData();
        int h,w, r,g,b;
        File outputFile=null;
        BufferedImage inputImg=null,outputImg=null;

        for(File curImage: images){

            inputImg= ImageIO.read(new File(curImage.getAbsolutePath()));

            w=inputImg.getWidth();

            h=inputImg.getHeight();

            outputImg= new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);

            for(int x=w-1;x>=0;x--){

                for (int y=h-1;y>=0;y--){

                    pix=inputImg.getRGB(x,y);
                    r = (pix >> 16) & 0x000000FF;
                    g = (pix >> 8) & 0x000000FF;
                    b = (pix) & 0x000000FF;

                    if(pixelPro[r][g][b]> threshold)
                        outputImg.setRGB(x,y,wh.getRGB());
                    else
                        outputImg.setRGB(x,y,bl.getRGB());
                }
            }
            outputFile = new File("F:\\IITian's\\IDEA_WS\\Skin Detection\\Processed\\"+ curImage.getName().replace(".bmp",".jpg"));
            ImageIO.write(outputImg, "jpg", outputFile);
        }
    }
}

