import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TrainerClass {
    double [][][] skinPixelMatrix=new double[256][256][256];
    double singlePixel_POS;
    double[][][] nonSkinPixelMatrix=new double[256][256][256];

    public int getRedComp(int pix){

        return pix >> 16 & 0xff;
    }
    public int getGreenComp(int pix){
        
        return pix >> 8 & 0xff;
    }
    public int getBlueComp(int pix){
        
        return pix & 0xff;
    }

    public boolean isNonSkin(int pix){

        if(((pix >> 16 & 0xff)>250 && (pix >> 8 & 0xff)>250 && (pix & 0xff)>250))
            return true;
        else
            return false;
    }

    public void initializeTraining(List<String> originalImages,List<String> maskImages) throws IOException {

        int serial=0,height,width,pix,compR,compG,compB,skinPixCnt=0,nSkinPixCnt=0;

        String orgImageName="F:\\IITian's\\IDEA_WS\\Skin Detection\\ibtd\\",maskImageName="F:\\IITian's\\IDEA_WS\\Skin Detection\\ibtd\\Mask\\";

        BufferedImage orgImage=null;

        BufferedImage maskImage=null;

        while(serial<originalImages.size()){

            orgImage= ImageIO.read(new File(orgImageName+ originalImages.get(serial)));
            maskImage=ImageIO.read(new File(maskImageName+maskImages.get(serial)));

            height=orgImage.getHeight();
            width=orgImage.getWidth();

            for(int i=width-1;i>=0;i--){

                for(int j=height-1;j>=0;j--){

                    pix= orgImage.getRGB(i,j);

                    compR= getRedComp(pix);

                    compG=getGreenComp(pix);

                    compB=getBlueComp(pix);

                    if(isNonSkin(maskImage.getRGB(i,j))) nonSkinPixelMatrix[compR][compG][compB]++;

                    else skinPixelMatrix[compR][compG][compB]++;

                }
            }
            serial++;
        }

        for(int i=0; i<256; i++){

            for(int j=0; j<256; j++){

                for(int k=0; k<256; k++){

                    skinPixCnt+= skinPixelMatrix[i][j][k];

                    nSkinPixCnt+= nonSkinPixelMatrix[i][j][k];

                }
            }
        }

        FileWriter fileWriter = new FileWriter("F:\\IITian's\\IDEA_WS\\Skin Detection\\Data\\dataSet.txt");

        for(int i=0; i<256; i++){

            for(int j=0; j<256; j++){

                for(int k=0; k<256; k++){

                    nonSkinPixelMatrix[i][j][k]/=nSkinPixCnt;
                    skinPixelMatrix[i][j][k]/=skinPixCnt;
                    
                }}}
        for(int i=0; i<256; i++){

            for(int j=0; j<256; j++){

                for(int k=0; k<256; k++){

                    if(nonSkinPixelMatrix[i][j][k]==0){
                        if(skinPixelMatrix[i][j][k]!=0) 
                            singlePixel_POS=2;
                        else  
                            singlePixel_POS=0;
                    }
                    else 
                        singlePixel_POS= skinPixelMatrix[i][j][k]/nonSkinPixelMatrix[i][j][k];
                        
                        fileWriter.write(singlePixel_POS+",");
                }
                        fileWriter.write("\n");
            }
        }

        fileWriter.flush(); fileWriter.close();      
    }
}
