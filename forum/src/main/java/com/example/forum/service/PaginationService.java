package com.example.forum.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaginationService {
    private static final int BAR_LENGTH = 5;

    public int currentBarLength() {
        return BAR_LENGTH;
    }

    public List<Integer> getPaginationBarNumbers(int currentPageNum, int totalPages) {
        int startNumber = Math.max(currentPageNum - (BAR_LENGTH / 2), 0);
        int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages);

        return IntStream.range(startNumber, endNumber).boxed().toList();
    }
}
