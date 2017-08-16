package org.springframe.unit;

import java.util.Arrays;

/**
 * 二分查找算法
 */
public class BinarySearch {

    /**
     * 循环实现二分查找算法
     * @param arr 已排好序的数组
     * @param key 需要查找的数
     * @return -1 无法查到数据
     */
    public static int binarySearch(int [] arr, int key){
        int low = 0;
        int high = arr.length-1;
        while (low <= high){
            int middle = (low + high) / 2;
            if (key == arr[middle]){
                return middle;
            }else if (key < arr[middle]){
                high = middle - 1;
            }else {
                low = middle + 1;
            }
        }
        return -1;
    }

    public static int binarySearch(int [] arr, int beginIndex, int endIndex, int key){
        /*int middle = (beginIndex + endIndex) / 2;
        if (key < arr[beginIndex] || key > arr[endIndex] || beginIndex > endIndex){
            return -1;
        } else if (key < arr[middle]){
            return binarySearch(arr, key, beginIndex, middle - 1);
        }else if (key > arr[middle]){
            return binarySearch(arr, key, middle + 1, endIndex);
        }else {
            return middle;
        }*/
        int low = beginIndex;
        int high = endIndex - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int midVal = arr[mid];

            if (midVal < key){
                low = mid + 1;
            } else if (midVal > key){
                high = mid - 1;
            } else{
                return mid; // key found
            }
        }
        return -1;  // key not found.
    }


    public static void main(String [] args){
        int [] arr  = { 6, 12, 33, 87, 90, 97, 108, 561 };

        System.err.println("循环查找目标位置：" + binarySearch(arr, 22));
        //System.out.println("递归查找目标位置："+binarySearch(arr,89,3,arr.length));
        System.out.println("arr = " + binarySearch(arr,0,arr.length,33));
    }

}
