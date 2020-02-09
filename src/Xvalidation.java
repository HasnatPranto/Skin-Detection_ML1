import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Xvalidation {
    int trainCnt;
    List<File> alImg=new ArrayList<>();List<File> alMask = new ArrayList<>(); List<File> procSet=new ArrayList<>();
    List<String>a=new ArrayList<>(); List<String>b=new ArrayList<>(); List<File> testSet=new ArrayList<>();
    List<File> testMask=new ArrayList<>();
    BufferedImage orginal=null;
    BufferedImage bnw=null;

    public void beginCross() throws IOException {
        TrainerClass train=new TrainerClass();
        PredictorClass prdctr=new PredictorClass();

        Random rd=new Random();
        boolean[] lel;
        int index;
        double k;
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter fold count, k:\n");
        k=sc.nextDouble();

        alImg=Arrays.asList(new File("F:\\IITian's\\IDEA_WS\\Skin Detection\\ibtd").listFiles());
        alMask=Arrays.asList(new File("F:\\IITian's\\IDEA_WS\\Skin Detection\\Mask").listFiles());
        trainCnt= (int) (Math.ceil((alImg.size()/k))*(k-1));
        lel=new boolean[alImg.size()];
        //testSet=new File[alImg.size()-trainCnt];

      for(int i=0;i<alImg.size();i++){
          index=rd.nextInt(555);

          if(lel[index]==true){ i--; continue;}

          else if(i<trainCnt){
              a.add(alImg.get(index).getName());
              b.add(alMask.get(index).getName());
          }
          else {
              testSet.add(alImg.get(index));
              testMask.add(alMask.get(index));
          }
          lel[index]=true;
      }
      Collections.sort(testMask);
      //train.initializeTraining(a,b);
      prdctr.createImage(testSet);
      procSet= Arrays.asList(new File("F:\\IITian's\\IDEA_WS\\Skin Detection\\Processed").listFiles());

        for(File file: procSet)
            System.out.println(file.getName());
        System.out.println("\n");
        for(File file: testMask)
            System.out.println(file.getName());
        getTests();
    }

    public void getTests() throws IOException {
        Color myColor,orgColor;
        double accuracy=0, precision=0,recall=0, harmonicMean=0,total;
        total=procSet.size();

        for(int i=0;i<procSet.size();i++){

            orginal= ImageIO.read(new File(testMask.get(i).getAbsolutePath()));
            bnw=ImageIO.read(new File(procSet.get(i).getAbsolutePath()));
            double TP=0,TN=0,FP=0,FN=0;

            for(int x=orginal.getWidth()-1;x>=0;x--){

                for(int y=orginal.getHeight()-1;y>=0;y--) {

                  myColor=new Color(bnw.getRGB(x,y));
                  orgColor=new Color(orginal.getRGB(x,y));

                    if(myColor.getRed()==255 && myColor.getGreen()==255 && myColor.getBlue()==255){
                        if(orgColor.getRed()>250 && orgColor.getGreen()>250 && orgColor.getBlue()>250)
                            FP++;
                        else
                            TP++;
                    }
                  else{
                        if(orgColor.getRed()>250 && orgColor.getGreen()>250 && orgColor.getBlue()>250)
                            TN++;
                        else
                            FN++;
                  }
                }
            }
            accuracy+= (TP+TN)/(TP+TN+FP+FN);
            precision+= TP/(FP+TP);
            recall+= TP/(TP+FN);
            harmonicMean+= 2*(recall * precision) / (recall + precision);
        }
        System.out.println("Accuracy: "+ (double)(accuracy/total)*100+"%");
        System.out.println("Precision: "+ (double)(precision/total)*100+"%");
        System.out.println("Recall: "+ (double)(recall/total)*100+"%");
        System.out.println("F1-score: "+ (double)(harmonicMean/total));
    }

}
