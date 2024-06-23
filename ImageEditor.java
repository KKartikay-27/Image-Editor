import java.io.File;
import java.util.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageEditor {
    public static void printPixelValues(BufferedImage inputImage){
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                Color pixel =new Color(inputImage.getRGB(j,i));
                System.out.print(pixel.getRed()+" "+ pixel.getBlue()+" "+pixel.getGreen());
            }
            System.out.println();
        }
    }
    public static BufferedImage converttoGreyscale(BufferedImage inputImage){
        int height=inputImage.getHeight();
        int width=inputImage.getWidth();
        BufferedImage outputImage=new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                outputImage.setRGB(j,i,inputImage.getRGB(j,i));
            }
        }
        return outputImage;        
    }
    public static BufferedImage changeBrightness(BufferedImage inputImage , int change){
        int height=inputImage.getHeight();
        int width=inputImage.getWidth();
        BufferedImage outputImage=new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                Color pixel = new Color(inputImage.getRGB(j, i));
                int red=pixel.getRed();
                int blue=pixel.getBlue();
                int green=pixel.getGreen();
                red=red+(red * change/100);
                green=green+(green * change/100);
                blue=blue+(blue*change/100);
                if(red>255) red=255;
                if(blue>255) blue=255;
                if(green>255) green=255;
                if(red<0) red=0;
                if(green<0) green=0;
                if(blue<0) blue=0;
                Color newPixel = new Color(red, green, blue);
                outputImage.setRGB(j, i, newPixel.getRGB());
                }
            }
        return outputImage;
    }
    public static BufferedImage horizontalInversion(BufferedImage inputImage){
        int height=inputImage.getHeight();
        int width=inputImage.getWidth();
        BufferedImage outputImage=new BufferedImage(width, height,BufferedImage.TYPE_3BYTE_BGR);
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                outputImage.setRGB(width-1-j,i,inputImage.getRGB(j, i));                
            }
        }
        return outputImage;
    }
    public static BufferedImage verticalInversion(BufferedImage inputImage){
        int height=inputImage.getHeight();
        int width=inputImage.getWidth();
        BufferedImage outputImage=new BufferedImage(width, height,BufferedImage.TYPE_3BYTE_BGR);
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                outputImage.setRGB(j,height-1-i,inputImage.getRGB(j, i));                
            }
        }
        return outputImage;
    }
    public static BufferedImage anticlockwiseRotation(BufferedImage inputImage){
        int height=inputImage.getHeight();
        int width=inputImage.getWidth();
        BufferedImage outputImage=new BufferedImage(height, width, BufferedImage.TYPE_3BYTE_BGR);
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                outputImage.setRGB(i, j, inputImage.getRGB(j, i));
            }
        }
        BufferedImage outputImage1=new BufferedImage(height, width, BufferedImage.TYPE_3BYTE_BGR);
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                outputImage1.setRGB(i,width-1-j,outputImage.getRGB(i, j));                
            }
        }
        return outputImage1;
    }
    public static BufferedImage clockwiseRotation(BufferedImage inputImage){
        int height=inputImage.getHeight();
        int width=inputImage.getWidth();
        BufferedImage outputImage=new BufferedImage(height, width, BufferedImage.TYPE_3BYTE_BGR);
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                outputImage.setRGB(i, j, inputImage.getRGB(j, i));
            }
        }
        BufferedImage outputImage1=new BufferedImage(height, width, BufferedImage.TYPE_3BYTE_BGR);
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                outputImage1.setRGB(height-1-i,j,outputImage.getRGB(i, j));                
            }
        }
        return outputImage1;
    }
    public static BufferedImage colorInversion(BufferedImage inputImage){
        int height=inputImage.getHeight();
        int width=inputImage.getWidth();
        BufferedImage outputImage=new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                Color pixel = new Color(inputImage.getRGB(j, i));
                int red=pixel.getRed();
                int blue=pixel.getBlue();
                int green=pixel.getGreen();
                red=255-red;
                green=255-green;
                blue=255-blue;
                Color newPixel = new Color(red, green, blue);
                outputImage.setRGB(j, i, newPixel.getRGB());
                }
            }
        return outputImage;
    }
    public static BufferedImage blurImage(BufferedImage inputImage, int pixelCount){
        int height=inputImage.getHeight();
        int width=inputImage.getWidth();
        BufferedImage outputImage=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int rowStart=0;
        int rowEnd = pixelCount-1;

        while(rowEnd<height){

            int columnStart = 0;
            int columnEnd = pixelCount-1 ;

            while(columnEnd<width){

                int sumRed = 0;
                int sumGreen = 0;
                int sumBlue = 0;

                // Calculate the average color values in the pixel square
                for(int i=rowStart ; i<=rowEnd ; i++){
                    for(int j=columnStart ; j<=columnEnd ; j++){

                        Color pixel = new Color(inputImage.getRGB(j,i));

                        sumRed += pixel.getRed();
                        sumBlue += pixel.getBlue();
                        sumGreen += pixel.getGreen();

                    }
                }

                int avgRed = sumRed/(pixelCount*pixelCount);
                int avgBlue = sumBlue/(pixelCount*pixelCount);
                int avgGreen = sumGreen/(pixelCount*pixelCount);

                Color newPixel = new Color(avgRed , avgGreen , avgBlue);

                // Set the entire pixel square in the output image to the average color
                for(int i=rowStart ; i<=rowEnd ; i++){
                    for(int j=columnStart ; j<=columnEnd ; j++){
                        outputImage.setRGB(j , i , newPixel.getRGB() );
                    }
                }

                columnStart+=pixelCount;
                columnEnd+=pixelCount;
            }

            rowStart+=pixelCount;
            rowEnd+=pixelCount;
        }

        return outputImage;
    }
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        File inputFile = new File("image.jpg");
        try {
            BufferedImage inputImage = ImageIO.read(inputFile);
            System.out.println("----------Welcome to the Image Editor----------");
            System.out.println("1 : Show the original image.");
            System.out.println("2 : Convert the image to Greyscale.");
            System.out.println("3 : Change Brigthness of the image.");
            System.out.println("4 : Invert the image horizontally.");
            System.out.println("5 : Invert the image vertically.");
            System.out.println("6 : Rotate the image Clockwise.");
            System.out.println("7 : Rotate the image Anticlockwise.");
            System.out.println("8 : To invert the colours of the image.");
            System.out.println("9 : Blur the image.");
            System.out.println("10 : Exit.");
            ///////// 
            //
            //
            //
            //
            /////////
            System.out.print("Enter your desired value for image editing : ");
                while(true){
                int n=scan.nextInt();
                if(n==1){
                    BufferedImage original=inputImage;
                    File originalImage = new File("output.jpg");
                    ImageIO.write(original, "jpg", originalImage );
                    System.out.print("Enter your desired value for image editing : ");
                }
                else if(n==2){
                    BufferedImage grayScale = converttoGreyscale(inputImage);
                    File graScaleImage = new File("output.jpg");
                    ImageIO.write(grayScale, "jpg", graScaleImage);
                    System.out.print("Enter your desired value for image editing : ");
                }
                else if(n==3){
                    System.out.println("Enter the value for change inn brightness : ");
                    int i=scan.nextInt();
                    BufferedImage changedBrightness = changeBrightness(inputImage, i);
                    File changedBrightnessImage = new File("output.jpg");
                    ImageIO.write(changedBrightness, "jpg", changedBrightnessImage);
                    System.out.print("Enter your desired value for image editing : ");
                }
                else if(n==4){
                    BufferedImage horizontalInverted= horizontalInversion(inputImage);
                    File hInverted = new File("output.jpg");
                    ImageIO.write(horizontalInverted, "jpg", hInverted);
                    System.out.print("Enter your desired value for image editing : ");
                }
                else if(n==5){
                    BufferedImage verticalInverted= verticalInversion(inputImage);
                    File vInverted = new File("output.jpg");
                    ImageIO.write(verticalInverted, "jpg", vInverted);
                    System.out.print("Enter your desired value for image editing : ");
                }
                else if(n==6){
                    BufferedImage cw= clockwiseRotation(inputImage);
                    File cRoatated = new File("output.jpg");
                    ImageIO.write(cw, "jpg", cRoatated);
                    System.out.print("Enter your desired value for image editing : ");
                }
                else if(n==7){
                    BufferedImage acw= anticlockwiseRotation(inputImage);
                    File acRoatated = new File("output.jpg");
                    ImageIO.write(acw, "jpg", acRoatated);
                    System.out.print("Enter your desired value for image editing : ");
                }
                else if(n==8){
                    BufferedImage cInversion= colorInversion(inputImage);
                    File imInverse = new File("output.jpg");
                    ImageIO.write(cInversion, "jpg", imInverse);
                    System.out.print("Enter your desired value for image editing : ");
                }
                else if(n==9){
                    System.out.println("Enter value of square : ");
                    int k=scan.nextInt();
                    BufferedImage blurredImage=blurImage(inputImage,k);
                    File imBlur= new File("output.jpg");
                    ImageIO.write(blurredImage, "jpg", imBlur);
                    System.out.print("Enter your desired value for image editing : ");
                }
                else if(n==10){
                    break;
                }
                }
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    } 