package imageprocess_test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageNegative {
    public static void main(String args[]) throws IOException {
        BufferedImage image = null;
        File f = null;

        try {
            f = new File("D:\\t1.jpg");
            image = ImageIO.read(f);

            int width = image.getWidth();
            int height = image.getHeight();

            int array[] = new int[width * height];
            int cishu = 0;

            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    int p = image.getRGB(i, j);
                    int a = (p >> 24) & 0xff;

                    System.out.println(a);
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;

                    array[cishu] = b;
                    cishu++;

                    r = 255 - r;
                    g = 255 - g;
                    b = 255 - b;

                    p = (a << 24) | (r << 16) | (g << 8) | b;
                    image.setRGB(i, j, p);
                }
            }

            int index;
            for (int i = 1; i <= array.length - 1; i++) {
                System.out.println(i);
                int x = array[i];
                index = i;
                for (int j = i - 1; j >= 0; j--) {
                    if (array[j] > x) {
                        // swap them
                        array[index] = array[j];
                        index = j;
                        array[j] = x;
                    }
                }
            }

            for (int x = 0; x < array.length; x++) {
                System.out.println(array[x]);
            }

            f = new File("D:\\Out.jpg");
            ImageIO.write(image, "jpg", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
