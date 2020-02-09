import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        TrainerClass myT=new TrainerClass();
        Xvalidation xvd=new Xvalidation();
        PredictorClass myPr=new PredictorClass();

        File folder1 = new File("F:\\IITian's\\IDEA_WS\\Skin Detection\\ibtd");
        File folder2 = new File("F:\\IITian's\\IDEA_WS\\Skin Detection\\Mask");

        File[] files1=folder1.listFiles();
        File[] files2=folder2.listFiles();

        List<String> originalImages = new ArrayList<>();
        List<String> maskImages = new ArrayList<>();

        for(File file: files1)
            if(!file.isDirectory())
                originalImages.add(file.getName());
        for(File file: files2) {
            //System.out.println(file.getName());
            maskImages.add(file.getName());
        }
        Collections.sort(originalImages);
        Collections.sort(maskImages);
        int c=0;
        Scanner sc=new Scanner(System.in);
        while(c!=3) {
            System.out.println("1.Train Machine\n2.Evaluate\n3.Cross Check\n"); //myT.initializeTraining(originalImages,maskImages);
            c=sc.nextInt();
            switch (c){
                case 1:System.out.println("Training initialized, wait a few moments..");
                    myT.initializeTraining(originalImages,maskImages);
                    System.out.println("Ok, Done.");
                    break;
                case 2:System.out.println("Identifying Skin-Non-skin, Wait..");
                    File folder= new File("F:\\IITian's\\IDEA_WS\\Skin Detection\\TestSub"), outputFile=null;
                    File[] images=folder.listFiles();
                    List<File> imList=new ArrayList<>();
                    imList= Arrays.asList(images);
                    myPr.createImage(imList);
                    System.out.println("Function Completed! Check the result.");
                    break;
                case 3: xvd.beginCross();
                
            }

            // System.out.println("Training DOne..");
        }
    }
}
