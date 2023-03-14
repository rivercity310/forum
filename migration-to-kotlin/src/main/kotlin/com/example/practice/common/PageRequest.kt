package com.example.practice.common

import org.springframework.data.domain.Sort

class PageRequest(
    private var size : Int,
    private var page : Int,
    private var direction : Sort.Direction?
) {
    internal fun of() : org.springframework.data.domain.PageRequest {
        if (size > 50) size = 10
        if (page <= 0) page = 1
        if (direction == null) direction = Sort.Direction.ASC

        return org.springframework.data.domain.PageRequest.of(page - 1, size, direction!!, "createdAt")
    }
}
