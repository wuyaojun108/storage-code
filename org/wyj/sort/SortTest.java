package org.wyj.sort;

import java.util.Arrays;

// 排序算法
public class SortTest {
    public static void main(String[] args) {
        // 声明并初始化数组
        Integer[] arr = new Integer[50000]; // 32
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 10000);
        }
        // 调用排序算法并计算排序所用时间
        long start = System.currentTimeMillis();
        quickSort(arr); // 12
        // mergeSort(arr); // 13
        // hillSort(arr); // 1746
        // insertSort(arr); // 2694
        // selectSort(arr); // 2184
        // bubbleSort(arr); // 9208
        long end = System.currentTimeMillis();
        long sub = end - start;
        System.out.println("Arrays.toString(arr) = " + Arrays.toString(arr));
        System.out.println("排序所用时间：" + sub + "毫秒");
    }

    /**
     * 冒泡排序：两两交换，大数下沉
     */
    public static  <T extends Comparable<T>> void bubbleSort(T[] arr){
        for(int i = 0; i < arr.length - 1; i++){
            for(int j = 1; j < arr.length - 1 - i; j++){
                if(arr[j].compareTo(arr[j + 1]) > 0){
                    T tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }

    /**
     *   选择排序：第一个数和后面的所有数进行比较，找出最小的，和第一个数进行交换，第二个数再次进行这样的比较，
     * 直到倒数第一个数，因为当前面所有数有序之后，倒数第一个数一定是有序的
     */
    public static <T extends Comparable<T>> void selectSort(T[] arr){
        for(int i = 0; i < arr.length; i++){
            int min = i;
            for(int j = i; j < arr.length; j++){
                if(arr[min].compareTo(arr[j]) > 0){
                    min = j;
                }
            }
            if(min != i){
                T tmp = arr[min];
                arr[min] = arr[i];
                arr[i] = tmp;
            }
        }
    }


    /**
     *   插入排序：类似于整理手中的扑克牌。把数组的第一个元素看成是一个有序数组，第二个元素和第一个元素进行比较，
     * 如果比它小，则第一个元素后移一位，第二个元素放到一个元素的位置，否则，第二个元素放到原来的位置，此时，第
     * 一个元素和第二个元素组成了一个包含两个元素的有序数组，第三个、第四个元素依次类推，直到所有元素有序
     */
    public static <T extends Comparable<T>> void insertSort(T[] arr){
        for(int i = 1; i < arr.length; i++){
            T current = arr[i];
            int preIndex = i - 1;
            while (preIndex >= 0 && current.compareTo(arr[preIndex]) < 0){
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex + 1] = current;
        }
    }


    /**
     *   希尔排序：插入排序的优化版，又称缩小增量排序。插入排序会按部就班地将每个元素和它前面的所有元素进行比较，直到
     * 找到比自己小的，插入到它的后面，而希尔排序，是在插入排序的基础上增加了一个增量的概念。增量一开始默认等于数组的
     * 长度除以2，每趟排序完成之后都除以2。把记录按照增量进行分组，对每组记录采用插入排序进行排序；随着增量逐步减小，
     * 所分成的组包含的元素越来越多，当增量等于0的时候，表示排序完成
     */
    public static <T extends Comparable<T>> void hillSort(T[] arr){
        for(int gap = arr.length / 2; gap > 0; gap /= 2){
            for(int i = gap; i < arr.length ; i += gap){
                T current = arr[i];
                int preIndex = i - gap;
                while (preIndex >= 0 && current.compareTo(arr[preIndex]) < 0){
                    arr[preIndex + gap] = arr[preIndex];
                    preIndex-=gap;
                }
                arr[preIndex + gap] = current;
            }
        }
    }


    /**
     *   归并排序：将序列从左往右，进行对半拆分，拆分出来的一半再次进行对半拆分，一直拆分到每个子序列都只有两个元素，
     * 将这两个子元素视为两个有序序列，使用一个辅助数组，把两个序列合并，合并时，序列中的第一个元素相互比较，小的元素
     * 先放进辅助数组中，然后再和另外一个数组中的下一个元素进行比较，直到其中一个数组为空，另一个数组的剩余元素也放进
     * 辅助数组中。再把合并后的序列进行两两合并，直到最终将所有的子序列全部合并
     */
    public static <T extends Comparable<T>> void mergeSort(T[] arr){
        ms(arr, 0, arr.length - 1);
    }

    public static <T extends Comparable<T>> void ms(T[] arr, int start, int end){
        if(start >= end) return;
        int mid = (start + end) / 2;
        ms(arr, start, mid);
        ms(arr, mid + 1, end);
        merge(arr, start, mid, end);
    }

    // start到mid是第一个数组的元素，mid+1到end是第二个数组的元素，对这两个数组进行归并操作
    public static <T extends Comparable<T>> void merge(T[] arr, int start, int mid, int end){
        Object[] tmpArr = new Object[end - start + 1];
        int i = start;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= end){
            if(arr[i].compareTo(arr[j]) < 0){
                tmpArr[k++] = arr[i++];
            } else {
                tmpArr[k++] = arr[j++];
            }
        }
        while (i <= mid) tmpArr[k++] = arr[i++];
        while (j <= end) tmpArr[k++] = arr[j++];
        System.arraycopy(tmpArr, 0, arr, start, tmpArr.length);
    }

    /**
     * 快速排序：
     *   第一步：选择数组最左侧的元素作为基准数。初始化两个指针，一个指向数组的左侧第二个元素，叫做i，一个指向数组
     * 的最右侧，叫做j，
     *   第二步：在i小于j的情况下：最后一个元素和基准数进行比较，如果大于等于基准数，指针前移，直到找到一个比基准数
     * 小的数，停留在那个数所在的位置；然后第二个元素和基准数进行比较，如果小于等于基准数，则指向后移动，直到找到一
     * 个比基准数大的数，指针停留在那个位置；此时如果i < j，则交换两个位置上的变量；
     *   第三步：重复第二步的动作，直到i=j，此时i指向的元素一定比基准数小。因为先从后向前找比基准数小的数字，然后再
     * 再从前向后找比基准数大的数字，如果i=j，此时j一定停留在一个比基准数小的数字上，所以i指向的数字一定比基准数小。
     * 然后i指向的元素和基准数交换位置
     *   第四步：此时i指针指向的元素就是基准数，如果i指针左边的元素的个数大于1，递归地执行第1到3步，如果i指针右边
     * 的元素个数大于1，也递归地执行第1到3步。
     */
    public static <T extends Comparable<T>> void quickSort(T[] arr){
        qs(arr, 0, arr.length - 1);
    }

    public static <T extends Comparable<T>> void qs(T[] arr, int left, int right){
        T base = arr[left];
        int i = left + 1, j = right;
        while (i < j){
            while (arr[j].compareTo(base) >= 0 && i < j) j--;
            while (arr[i].compareTo(base) <= 0 && i < j) i++;
            if(i < j){
                T tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        arr[left] = arr[i];
        arr[i] = base;
        if(left < i - 1) qs(arr, left, i - 1);
        if(i + 1 < right) qs(arr, i + 1, right);
    }

}
