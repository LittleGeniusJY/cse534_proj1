package imageprocess_test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Otsu {

    public static void main(String args[]) throws IOException {

        //System.out.print("&&&");
        int a = 0;
        File imagefile = new File("D://t3.jpg"); //read the file
        //System.out.print("*******");
        File outputfile = null;
        File ultimate_output_file = null;
        BufferedImage image = null;
        int Gray_int = 0;
        try {
            image = ImageIO.read(imagefile); //read the image

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.print("((((((");
        int image_gaodu = image.getHeight(); //get the number of the height
        //System.out.print("^^^^");
        //System.out.println(image_gaodu);

        int image_kuandu = image.getWidth(); //get the number of the width

        for (int i = 0; i < image_gaodu; i++) {
            for (int j = 0; j < image_kuandu; j++) {
                int totalrgb = image.getRGB(j, i);
                a = (totalrgb >> 24) & 0xff; //this is
                int r = (totalrgb >> 16) & 0xff; // get r of each pixel
                int g = (totalrgb >> 8) & 0xff; // get g of each pixel
                int b = totalrgb & 0xff; // get b of each pixel
                double Gray_double = r * 0.299 + g * 0.587 + b * 0.114;
                // System.out.print("%%%%");
                Gray_int = (int) Gray_double;

                System.out.print(Gray_int);

                totalrgb = (a << 24) | (Gray_int << 16) | (Gray_int << 8)
                        | Gray_int;
                image.setRGB(j, i, totalrgb); // convert it to the gray, save it to image

            }
        }
        outputfile = new File("D:\\Out.jpg");
        ImageIO.write(image, "jpg", outputfile);

        double ostu_max = 0;
        int the_chosen_therehold = 0;

        int cishu_of_appear[] = new int[256];//每个灰度的出现次数
        for (int x = 0; x < 256; x++) {
            cishu_of_appear[x] = 0;
        }
        int sum_everyscale[] = new int[256]; //每个灰度的和
        for (int y = 0; y < 256; y++) {
            sum_everyscale[y] = 0;
        }

        for (int i = 0; i < image_gaodu; i++) {
            for (int j = 0; j < image_kuandu; j++) {

                int totalrgb = image.getRGB(j, i);
                //System.out.print("totalrgb is" + totalrgb + "\n");
                int gray_scale = totalrgb & 0xff;
                // System.out.print("totalrgb is" + totalrgb + "\n");
                // System.out.print("gray_scale is" + gray_scale + "\n");
                cishu_of_appear[gray_scale]++;
                sum_everyscale[gray_scale] = sum_everyscale[gray_scale]
                        + gray_scale;

            }
        }

        for (int x = 0; x < 256; x++) {
            System.out.print("&&&&&&&&&&&&&&&&&");
            System.out.print("cishu" + x + " is " + cishu_of_appear[x] + "\n");
        }

        //implement the otsu to find the therehold.
        for (int therehold = 0; therehold < 256; therehold++) {

            System.out.print("*******therehold is " + therehold + "\n");
            int num_low = 0;
            int num_high = 0;
            int sum_grayscale_low = 0;
            int sum_grayscale_high = 0;
            /*
             * for (int i = 0; i < image_gaodu; i++) { for (int j = 0; j <
             * image_kuandu; j++) { System.out.print("i=" + i + ";j=" + j +
             * "\n"); System.out.print("gaodu=" + image_gaodu + ";kuandu=" +
             * image_kuandu + "\n"); int totalrgb = image.getRGB(j, i);
             * System.out.print("totalrgb is" + totalrgb + "\n"); int gray_scale
             * = totalrgb & 0xff; // System.out.print("gray_scale is" +
             * gray_scale + "\n"); if (gray_scale <= therehold) { num_low++;
             * sum_grayscale_low = sum_grayscale_low + gray_scale; } else if
             * (gray_scale > therehold) { num_high++; sum_grayscale_high =
             * sum_grayscale_high + gray_scale; } } }
             *///

            for (int x = 0; x < therehold; x++) {
                num_low = num_low + cishu_of_appear[x];
                // System.out.print("num_low is" + num_low + "\n");
                sum_grayscale_low = sum_grayscale_low + x * cishu_of_appear[x];

            }
            for (int y = therehold; y < 256; y++) {
                num_high = num_high + cishu_of_appear[y];
                //   System.out.print("num_high is" + num_high + "\n");
                sum_grayscale_high = sum_grayscale_high
                        + y * cishu_of_appear[y];

            }

            double rate_low = (double) num_low / (image_gaodu * image_kuandu);
            System.out.print("rate_low is" + rate_low + "\n");
            double rate_high = (double) num_high / (image_gaodu * image_kuandu);
            System.out.print("rate_high is" + rate_high + "\n");
            if (rate_low == 0 || rate_high == 0) {
                continue;
            }

            double avg_gscale_low = (double) sum_grayscale_low / num_low;
            double avg_gscale_high = (double) sum_grayscale_high / num_high;
            double ostu_result = rate_low * rate_high
                    * (avg_gscale_low - avg_gscale_high)
                    * (avg_gscale_low - avg_gscale_high);
            System.out.print(ostu_result + "\n");
            if (ostu_result > ostu_max) {
                ostu_max = ostu_result;
                the_chosen_therehold = therehold;
            }
        }
        System.out.print("the chosen therehold is" + the_chosen_therehold); //find the therehold.

        for (int i = 0; i < image_gaodu; i++) {
            for (int j = 0; j < image_kuandu; j++) {
                int new_color = 1000;
                int totalrgb = image.getRGB(j, i);
                int grayscale = totalrgb & 0xff; // get grayscale
                if (grayscale <= the_chosen_therehold) {
                    new_color = 0;
                } else if (grayscale > the_chosen_therehold) {
                    new_color = 255;
                }
                totalrgb = (a << 24) | (new_color << 16) | (new_color << 8)
                        | new_color;
                image.setRGB(j, i, totalrgb); // convert it to the gray, save it to image

            }
        }

        ultimate_output_file = new File("D:\\Final_Out.jpg");
        ImageIO.write(image, "jpg", ultimate_output_file);

    }

}
