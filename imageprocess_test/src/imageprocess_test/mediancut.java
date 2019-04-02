package imageprocess_test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class mediancut {

    static int a = 0;
    static int standard[] = new int[256];

    static int min_in_array(int array[]) {
        int min = 255;
        int height = array.length;
        // int wit = array[0].length;
        for (int i = 0; i < height; i++) {
            // for (int j = 0; j < height; j++) {
            if (array[i] < min) {
                min = array[i];
            }

        }
        return min;
    }

    static int max_in_array(int array[]) {
        int max = 0;
        int height = array.length;
        // int wit = array[0].length;
        for (int i = 0; i < height; i++) {
            //  for (int j = 0; j < height; j++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        return max;
    }

    static int max_gap(int array[]) {
        int max = max_in_array(array);
        int min = min_in_array(array);
        return max - min;
    }

    static void mediancut(int n, int r_array[], int g_array[], int b_array[]) {

        //int length = rgb_comb.length;
        // int r_array[] = new int[length];
        // int g_array[] = new int[length];
        // int b_array[] = new int[length];
        int length = r_array.length;
        int length_next_left = length / 2;
        int length_next_right = length - length / 2;
        int r_array_next_left[] = new int[length_next_left];
        int g_array_next_left[] = new int[length_next_left];
        int b_array_next_left[] = new int[length_next_left];

        int b_array_next_right[] = new int[length_next_right];
        int r_array_next_right[] = new int[length_next_right];
        int g_array_next_right[] = new int[length_next_right];

        int r_sum_left = 0;
        int g_sum_left = 0;
        int b_sum_left = 0;

        int r_sum_right = 0;
        int g_sum_right = 0;
        int b_sum_right = 0;

        int r_temp;
        int b_temp;
        int g_temp;
        int right_all;
        int left_all;

        //  for (int i = 0; i < rgb_comb.length; i++) {
        //     r_array[i] = rgb_comb[i] / 1000000;
        //      g_array[i] = (rgb_comb[i] - r_array[i] * 1000000) / 1000;
        //      b_array[i] = (rgb_comb[i] - r_array[i] * 1000000
        //              - g_array[i] * 1000);
        //
        //  }
        int max_gap_blue = max_gap(b_array);
        int max_gap_green = max_gap(g_array);
        int max_gap_red = max_gap(r_array);
        int index;
        //if leibie ==3 &&。。。才可排序，之后 id leibie==3 &&, 才可改变

        System.out.println("blue=" + max_gap_blue + " green=" + max_gap_green
                + " red=" + max_gap_red);
        //the blue is the longest dimension sorting
        if (max_gap_blue >= max_gap_green && max_gap_blue >= max_gap_red) {
            for (int i = 1; i <= b_array.length - 1; i++) {
                // System.out.println(i);
                int x = b_array[i];
                index = i;
                for (int j = i - 1; j >= 0; j--) {

                    if (b_array[j] > x) {
                        // swap them
                        b_array[index] = b_array[j];

                        r_temp = r_array[index];
                        g_temp = g_array[index];
                        r_array[index] = r_array[j];
                        g_array[index] = g_array[j];
                        index = j;
                        b_array[j] = x;

                        r_array[j] = r_temp;
                        g_array[j] = g_temp;
                    }
                }
            }

        }
        // green dimension
        else if (max_gap_green >= max_gap_blue
                && max_gap_green >= max_gap_red) {
            for (int i = 1; i <= g_array.length - 1; i++) {
                int x = g_array[i];
                index = i;
                for (int j = i - 1; j >= 0; j--) {
                    if (g_array[j] > x) {
                        // swap them
                        g_array[index] = g_array[j];

                        r_temp = r_array[index];
                        b_temp = b_array[index];

                        r_array[index] = r_array[j];
                        b_array[index] = b_array[j];
                        index = j;
                        g_array[j] = x;

                        r_array[j] = r_temp;
                        b_array[j] = b_temp;
                    }
                }
            }
        }

        //when select red dimension
        else if (max_gap_red >= max_gap_green && max_gap_red >= max_gap_blue) {
            for (int i = 1; i <= r_array.length - 1; i++) {
                int x = r_array[i];
                index = i;
                for (int j = i - 1; j >= 0; j--) {
                    if (r_array[j] > x) {
                        // swap them
                        r_array[index] = r_array[j];

                        g_temp = g_array[index];
                        b_temp = b_array[index];

                        g_array[index] = g_array[j];
                        b_array[index] = b_array[j];
                        index = j;
                        r_array[j] = x;

                        g_array[j] = g_temp;
                        b_array[j] = b_temp;
                    }
                }
            }
        }
        //divide the array,calculate the mean rgb value
        for (int x = 0; x < length_next_left; x++) {
            r_array_next_left[x] = r_array[x];
            g_array_next_left[x] = g_array[x];
            b_array_next_left[x] = b_array[x];
            r_sum_left = r_sum_left + r_array_next_left[x];
            g_sum_left = g_sum_left + g_array_next_left[x];
            b_sum_left = b_sum_left + b_array_next_left[x];

        }
        for (int y = 0; y < length_next_right; y++) {
            r_array_next_right[y] = r_array[y + length_next_left];
            g_array_next_right[y] = g_array[y + length_next_left];
            b_array_next_right[y] = b_array[y + length_next_left];
            r_sum_right = r_sum_right + r_array_next_right[y];
            g_sum_right = g_sum_right + g_array_next_right[y];
            b_sum_right = b_sum_right + b_array_next_right[y];

        }
        r_sum_left = r_sum_left / r_array_next_left.length;
        g_sum_left = g_sum_left / r_array_next_left.length;
        b_sum_left = b_sum_left / r_array_next_left.length;
        left_all = r_sum_left * 1000000 + g_sum_left * 1000 + b_sum_left;

        r_sum_right = r_sum_right / r_array_next_right.length;
        g_sum_right = g_sum_right / r_array_next_right.length;
        b_sum_right = b_sum_right / r_array_next_right.length;
        right_all = r_sum_right * 1000000 + g_sum_right * 1000 + b_sum_right;
        // decide when to return
        if (n == 7) {

            System.out.println("********" + n);

            standard[a] = left_all;
            a = a + 1;
            standard[a] = right_all;
            a = a + 1;
            System.out.println("********" + a);
            return;

        }

        mediancut(n + 1, r_array_next_left, g_array_next_left,
                b_array_next_left);
        mediancut(n + 1, r_array_next_right, g_array_next_right,
                b_array_next_right);

    }

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

        int max_gap_blue;
        int max_gap_green;
        int max_gap_red;

        int cishu = 0;

        int rgb_comb[][] = new int[image_kuandu][image_gaodu];
        int blue_array[] = new int[image_kuandu * image_gaodu];
        int green_array[] = new int[image_kuandu * image_gaodu];
        int red_array[] = new int[image_kuandu * image_gaodu];

        for (int i = 0; i < image_gaodu; i++) {
            for (int j = 0; j < image_kuandu; j++) {
                int totalrgb = image.getRGB(j, i);
                a = (totalrgb >> 24) & 0xff; //this is
                int red = (totalrgb >> 16) & 0xff; // get r of each pixel
                int green = (totalrgb >> 8) & 0xff; // get g of each pixel
                int blue = totalrgb & 0xff; // get b of each pixel
                int rgb_combine = red * 1000000 + green * 1000 + blue;
                rgb_comb[j][i] = rgb_combine;

                blue_array[cishu] = blue;
                green_array[cishu] = green;
                red_array[cishu] = red;
                cishu++;
            }
        }
        //  for (int j = 2000; j <= 10111; j++) {
        ///     System.out.println(blue_array[j]);
        // }

        mediancut(0, red_array, green_array, blue_array);

        for (int i = 0; i < standard.length; i++) {
            System.out.println("%%%%%%" + standard[i]);
        }
        // first choose the biggest channel
        //  max_gap_blue = max_gap(blue_array);
        // max_gap_green = max_gap(green_array);
        // max_gap_red = max_gap(red_array);
        // 到这里我们得出了“标杆”rgb值，存在standard[]中

        int r_pixel = 0;
        int g_pixel = 0;
        int b_pixel = 0;
        int min_distance = 195075;
        int eachtime_distance = 0;

        int color_catagroy = 256;

        int r_standard_array[] = new int[color_catagroy];
        int g_standard_array[] = new int[color_catagroy];
        int b_standard_array[] = new int[color_catagroy];

        int total_rgb_afterchange = 0;

        int most_suitable_pixel = 0;

        for (int k = 0; k < color_catagroy; k++) {
            r_standard_array[k] = standard[k] / 1000000;
            g_standard_array[k] = (standard[k] - r_standard_array[k] * 1000000)
                    / 1000;
            b_standard_array[k] = (standard[k] - r_standard_array[k] * 1000000
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

                    //     System.out.println("j=" + j + "i=" + i + "r_standard="
                    //             + r_standard + " g_standard=" + g_standard
                    //            + " b_standard=" + b_standard + "most_fre"
                    //            + most_fre_array_rgb[k]);
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

                    if (eachtime_distance < min_distance) {
                        min_distance = eachtime_distance;
                        most_suitable_pixel = k;
                    }
                }
                r_pixel = standard[most_suitable_pixel] / 1000000;
                g_pixel = (standard[most_suitable_pixel] - r_pixel * 1000000)
                        / 1000;
                b_pixel = (standard[most_suitable_pixel] - r_pixel * 1000000
                        - g_pixel * 1000);

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
