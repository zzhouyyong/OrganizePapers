package com.papers.utils;

import java.util.*;

/**
 * @author zhouyong
 * @date 2021/3/18 16:15
 * 随机组卷算法
 */
public class AutoToPaperUtil {

    public static void shufflePaperIds (List<?> list) {
        int size = list.size();
        final Random random = new Random();
        // 判断当前传入的list是否是ArrayList
        if ( list instanceof RandomAccess) {
            for (int i = size; i > 1; i--)
                swap(list, i-1, random.nextInt(i));
        // 若是linkedList走这
        } else {
            Object arr[] = list.toArray();

            for (int i=size; i>1; i--)
                swap(arr, i-1, random.nextInt(i));

            ListIterator it = list.listIterator();
            for (int i=0; i<arr.length; i++) {
                it.next();
                it.set(arr[i]);
            }
        }
    }

    public static void swap(List<?> list, int i, int j) {
        final List l = list;
        l.set(i, l.set(j, l.get(i)));
    }

    private static void swap(Object[] arr, int i, int j) {
        Object tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
