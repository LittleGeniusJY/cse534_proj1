package imageprocess_test;

public class insert_sort {

    public static void main(String args[]) {

        int array[] = new int[] { 7, 8, 5, 2, 4, 6, 3 };
        int index;

        for (int i = 1; i <= array.length - 1; i++) {
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

    }

}
