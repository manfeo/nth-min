package com.example.test.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NthMinService {

    public int findNthSmallest(List<Integer> numbers, int n) {
        if (n < 1 || n > numbers.size()) {
            throw new IllegalArgumentException("N is out of bounds");
        }

        List<Integer> nums = new ArrayList<>(numbers);

        int result = 0;
        for (int i = 0; i < n; i++) {
            int minIndex = -1;
            int minVal = Integer.MAX_VALUE;

            for (int j = 0; j < nums.size(); j++) {
                Integer num = nums.get(j);
                if (num != null && num < minVal) {
                    minVal = num;
                    minIndex = j;
                }
            }

            result = minVal;
            nums.set(minIndex, null);
        }

        return result;
    }
}
