package imageprocess_test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;

public class popular_algorithm {

    public static void main(String args[]) {

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

        int rgb_comb[][] = new int[image_kuandu][image_gaodu];
        HashMap<Integer, Integer> data_cishu = new HashMap<>();////

        for (int i = 0; i < image_gaodu; i++) {
            for (int j = 0; j < image_kuandu; j++) {
                int totalrgb = image.getRGB(j, i);
                a = (totalrgb >> 24) & 0xff; //this is
                int red = (totalrgb >> 16) & 0xff; // get r of each pixel
                int green = (totalrgb >> 8) & 0xff; // get g of each pixel
                int blue = totalrgb & 0xff; // get b of each pixel
                int rgb_combine = red * 1000000 + green * 1000 + blue;
                rgb_comb[j][i] = rgb_combine;
                if (data_cishu.get(rgb_comb[j][i]) != null) {

                    int temp = data_cishu.get(rgb_comb[j][i]);
                    temp++;
                    data_cishu.put(rgb_comb[j][i], temp); //if the same data appear, make it +1

                } else {
                    data_cishu.put(rgb_comb[j][i], 1);
                }

            }
        }

        int map_size = data_cishu.size();
        int data_cishu_array[] = new int[map_size]; //save the appear time and rgb_value in two arrays
        int data_rgb_array[] = new int[map_size];

        Set<Integer> set_key = data_cishu.keySet();
        Iterator<Integer> iter = set_key.iterator();
        int data_cishu_array_ax = 0;
        while (iter.hasNext()) {
            int i = 0;
            int key = iter.next();
            int value = data_cishu.get(key);

            data_rgb_array[data_cishu_array_ax] = key; //                        0 1 2 3 4....
                                                       //      data_rgb_array
            data_cishu_array[data_cishu_array_ax] = value; //data_cishu_array
            data_cishu_array_ax++;
        }

        System.out.println("length=" + data_cishu_array.length);

        //   for (int i = 0; i < data_cishu_array.length; i++) {
        //      System.out.println(data_cishu_array[i]);
        //  }

        int bit_level = 8;
        int color_catagroy = 2;
        int most_fre_array_rgb[] = new int[color_catagroy];
        int most_fre_array[] = new int[color_catagroy]; //build a array which include the most famous cishu
        int most_frequent = 0;
        int most_frequent_num = 0;
        for (int i = 0; i < color_catagroy; i++) {
            most_frequent = 0;
            for (int j = 0; j < data_cishu_array.length; j++) {
                if (data_cishu_array[j] > most_frequent) {
                    most_frequent = data_cishu_array[j];
                    most_frequent_num = j;
                }
            }
            most_fre_array[i] = most_frequent; // the appear次数 of certain rgb
            most_fre_array_rgb[i] = data_rgb_array[most_frequent_num]; // the rgb information dg:200-150-240
            data_cishu_array[most_frequent_num] = 0;

        }
        for (int i = 0; i < most_fre_array.length; i++) {
            System.out.println(
                    most_fre_array_rgb[i] + "的次数为" + most_fre_array[i]);
        }

        int min_distance = 195075; //255*255*3
        int eachtime_distance = 0;
        int r_standard = 0;
        int g_standard = 0;
        int b_standard = 0;
        int r_pixel = 0;
        int g_pixel = 0;
        int b_pixel = 0;
        int total_rgb_afterchange = 0;
        int most_suitable_pixel = 0;
        int r_standard_array[] = new int[color_catagroy];
        int g_standard_array[] = new int[color_catagroy];
        int b_standard_array[] = new int[color_catagroy];

        for (int k = 0; k < color_catagroy; k++) { //把最流行的那个大数组分成三个通道
            r_standard_array[k] = most_fre_array_rgb[k] / 1000000;
            g_standard_array[k] = (most_fre_array_rgb[k]
                    - r_standard_array[k] * 1000000) / 1000;
            b_standard_array[k] = (most_fre_array_rgb[k]
                    - r_standard_array[k] * 1000000
                    - g_standard_array[k] * 1000);
        }

        for (int i = 0; i < image_gaodu; i++) {
            for (int j = 0; j < image_kuandu; j++) {

                r_pixel = rgb_comb[j][i] / 1000000;
                g_pixel = (rgb_comb[j][i] - r_pixel * 1000000) / 1000;
                b_pixel = (rgb_comb[j][i] - r_pixel * 1000000 - g_pixel * 1000);

                // System.out.println("r_pixel=" + r_pixel + " g_pixel=" + g_pixel
                //         + " b_pixel=" + b_pixel);
                min_distance = 195075;
                for (int k = 0; k < color_catagroy; k++) {
                    //  r_standard = most_fre_array_rgb[k] / 1000000;
                    //   g_standard = (most_fre_array_rgb[k] - r_standard * 1000000)
                    //          / 1000;
                    //  b_standard = (most_fre_array_rgb[k] - r_standard * 1000000
                    //          - g_standard * 1000);

                    /*
                     * System.out.println("j=" + j + "i=" + i + "r_standard=" +
                     * r_standard + " g_standard=" + g_standard + " b_standard="
                     * + b_standard + "most_fre" + most_fre_array_rgb[k]);
                     */
                    if ((r_pixel - r_standard_array[k])
                            * (r_pixel - r_standard_array[k]) > min_distance
                            || (g_pixel - g_standard_array[k]) * (g_pixel
                                    - g_standard_array[k]) > min_distance
                            || (b_pixel - b_standard_array[k]) * (b_pixel
                                    - b_standard_array[k]) > min_distance) {
                        continue;
                    }
                    eachtime_distance = (r_pixel - r_standard_array[k])
                            * (r_pixel - r_standard_array[k])
                            + (g_pixel - g_standard_array[k])
                                    * (g_pixel - g_standard_array[k])
                            + (b_pixel - b_standard_array[k])
                                    * (b_pixel - b_standard_array[k]);

                    if (eachtime_distance < min_distance) { //calculate the most small distance.
                        min_distance = eachtime_distance;
                        most_suitable_pixel = k;
                    }
                }
                r_pixel = most_fre_array_rgb[most_suitable_pixel] / 1000000;
                g_pixel = (most_fre_array_rgb[most_suitable_pixel]
                        - r_pixel * 1000000) / 1000;
                b_pixel = (most_fre_array_rgb[most_suitable_pixel]
                        - r_pixel * 1000000 - g_pixel * 1000);

                //  System.out.println("j=" + j + "i=" + i + "r_pixel=" + r_pixel
                //        + " g_pixel=" + g_pixel + " b_pixel=" + b_pixel);

                total_rgb_afterchange = (a << 24) | (r_pixel << 16)
                        | (g_pixel << 8) | b_pixel;
                image.setRGB(j, i, total_rgb_afterchange);

            }
            System.out.println(min_distance);
        }
        outputfile = new File("D:\\Out.jpg");
        try {
            ImageIO.write(image, "jpg", outputfile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
