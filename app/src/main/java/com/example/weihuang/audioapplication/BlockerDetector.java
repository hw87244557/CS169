package com.example.weihuang.audioapplication;

public class BlockerDetector {
    int level_1_window_size = 100;
    int level_1_window_count = 0;
    int level_1_max_value = 0;

    int level_2_window_size = 20;
    int level_2_current_index = 0;
    int level_2_sum = 0;
    boolean level_2_flag = false;
    int[] level_2_slot = new int[level_2_window_size];

    int level_3_window_size = 20;
    int[] level_3_slot = new int[level_3_window_size];
    int level_3_current_index = 0;
    boolean level_3_flag = false;
    int local_min = 0;
    int local_max = 0;

    int count = 0;

    public boolean addValue(int v) {
        count++;

        int level_1_output = level_1(v);
        if (level_1_output == Integer.MIN_VALUE) {
            return false;
        }
        int level_2_output = level_2(level_1_output);
        if (level_2_output == Integer.MIN_VALUE) {
            return false;
        }
        int level_3_output = level_3(level_2_output);
        if (count > 50000 && level_3_output > 600) {
            return true;
        }

        return false;
    }

    /* Level 1: Get the maximum value in a window*/
    private int level_1(int v) {
        level_1_window_count++;
        if (level_1_max_value < v) {
            level_1_max_value = v;
        }
        if (level_1_window_count == level_1_window_size) {
            level_1_window_count = 0;
            int temp = level_1_max_value;
            level_1_max_value = 0;
            return temp;
        }
        return Integer.MIN_VALUE;
    }

    /* Level 2: Get the average value in a window*/
    private int level_2(int v) {
        int temp = level_2_slot[level_2_current_index];
        level_2_slot[level_2_current_index] = v;
        level_2_sum = level_2_sum + v - temp;
        level_2_current_index = (level_2_current_index + 1)%level_2_window_size;

        if (level_2_current_index == 0) {
            level_2_flag = true;
        }
        if (level_2_flag) {
            return  level_2_sum/level_2_window_size;
        }
        return Integer.MIN_VALUE;
    }

    private int level_3(int v) {
        level_3_slot[level_3_current_index] = v;
        level_3_current_index = (level_3_current_index + 1)%level_3_window_size;

        if (level_3_current_index == 0) {
            level_3_flag = true;
        }
        if (level_3_flag) {
            int min = Integer.MAX_VALUE;
            int min_index = -1;
            int max = Integer.MIN_VALUE;
            int max_index = -1;
            for (int i = 0; i < level_3_window_size; i++) {
                if (level_3_slot[i] < min) {
                    min = level_3_slot[i];
                    min_index = i;
                }
                if (level_3_slot[i] > max) {
                    max = level_3_slot[i];
                    max_index = i;
                }
            }
            int mid_index = (level_3_current_index + level_3_window_size/2)%level_3_window_size;
            if (mid_index == max_index) {
                local_max = level_3_slot[max_index];
            }
            if (mid_index == min_index) {
                local_min = level_3_slot[min_index];
            }
            return (local_max - local_min);
        }
        return Integer.MIN_VALUE;
    }
}
