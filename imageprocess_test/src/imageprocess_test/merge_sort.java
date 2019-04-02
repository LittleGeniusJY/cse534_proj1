package imageprocess_test;

public class merge_sort {
    static int array[] = { 8, 7, 5, 2, 4, 6, 3, 6, 1 };

    static void merge(int a[], int low, int mid, int high) {
        System.out.println("^^^^^^^");

        int length = high - low + 1;
        int aux[] = new int[length];
        int l_shouldbe = mid - low + 1;
        int h_shouldbe = high - low + 1;
        int index = low;

        int l = 0;
        int h = mid + 1 - low;
        for (int i = 0; i < length; i++) {
            aux[i] = a[i + low];
        }
        System.out.println("aux是" + aux[0] + aux[1]);
        System.out.println(
                "a是" + a[0] + a[1] + a[2] + a[3] + a[4] + a[5] + a[6] + a[7]);
        System.out.println("l是" + l);
        System.out.println("h是" + h);
        System.out.println("l_shouldbe是" + l_shouldbe);
        System.out.println("h_shouldbe是" + h_shouldbe);
        for (index = low; index <= high; index++) {
            if (l == l_shouldbe) {
                a[index] = aux[h];
                h++;
                //index++;
            } else if (h == h_shouldbe) {
                a[index] = aux[l];
                l++;
                //index++;
            } else if (aux[l] <= aux[h]) {
                a[index] = aux[l];
                System.out.println("低位小于高位,index是" + index + ",a[index]是"
                        + a[index] + "\n");
                // index++;
                l++;
            } else {
                a[index] = aux[h];
                System.out.println("低位大于高位,index是" + index + ",a[index]是"
                        + a[index] + "\n");
                // index++;
                h++;
            }

        }
        System.out.println("合并之后 a是" + a[0] + a[1] + a[2] + a[3] + a[4] + a[5]
                + a[6] + a[7]);
    }

    static void merge_sort(int a[], int low, int high) {
        int mid = (low + high) / 2;
        System.out.print(mid + "\n");
        if (low == high) {
            return;
        }
        merge_sort(a, low, mid);
        merge_sort(a, mid + 1, high);

        System.out.print("when merge, low is" + low + " high is" + high
                + " mid is " + mid + "\n");
        merge(a, low, mid, high);
        for (int i = 0; i <= 8; i++) {
            System.out.print(a[i]);
        }
        System.out.print("\n");

    }

    public static void main(String args[]) {

        merge_sort(array, 0, 8);

    }

}
