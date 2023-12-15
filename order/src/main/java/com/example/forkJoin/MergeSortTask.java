package com.example.forkJoin;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;
import java.util.function.Supplier;

public class MergeSortTask extends RecursiveAction{

  private final int THRESHOLD; // 拆分的阈值， 即当拆分到只剩下THRESHOLD个元素时，不再拆分
  private int[] arrayToSort; // 待排序的数组

  public MergeSortTask(int[] arrayToSort, int threshold) {
    this.arrayToSort = arrayToSort;
    this.THRESHOLD = threshold;
  }

  @Override
  protected void compute() {
    if (arrayToSort.length < THRESHOLD) {
      // 当待排序的数组长度小于THRESHOLD时，直接进行排序
      Arrays.sort(arrayToSort);
      return;
    }

    // 拆分数组
    int midIndex = arrayToSort.length / 2;
    int[] leftArray = Arrays.copyOfRange(arrayToSort, 0, midIndex);
    int[] rightArray = Arrays.copyOfRange(arrayToSort, midIndex, arrayToSort.length);

    // 拆分后的数组继续拆分，直到拆分到只剩下THRESHOLD个元素
    MergeSortTask leftTask = new MergeSortTask(leftArray, THRESHOLD);
    MergeSortTask rightTask = new MergeSortTask(rightArray, THRESHOLD);

    // 执行拆分后的任务
    invokeAll(leftTask, rightTask);

    // 合并拆分后的数组
    arrayToSort = merge(leftTask.arrayToSort, rightTask.arrayToSort);

  }

  /**
   * 合并两个有序数组
   *
   * @param leftArray  左有序数组
   * @param rightArray 右有序数组
   * @return 合并后的有序数组
   */
  private int[] merge(int[] leftArray, int[] rightArray) {
    int[] mergedArray = new int[leftArray.length + rightArray.length];
    int mergeIndex = 0;
    int leftIndex = 0;
    int rightIndex = 0;

    while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
      if (leftArray[leftIndex] < rightArray[rightIndex]) {
        mergedArray[mergeIndex] = leftArray[leftIndex];
        leftIndex++;
      } else {
        mergedArray[mergeIndex] = rightArray[rightIndex];
        rightIndex++;
      }
      mergeIndex++;
    }

    while (leftIndex < leftArray.length) {
      mergedArray[mergeIndex] = leftArray[leftIndex];
      leftIndex++;
      mergeIndex++;
    }

    while (rightIndex < rightArray.length) {
      mergedArray[mergeIndex] = rightArray[rightIndex];
      rightIndex++;
      mergeIndex++;
    }

    return mergedArray;
  }
}
