package com.example.practice.domain

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class AuditingFields(
    @CreatedBy
    @Column(nullable = false, updatable = false)
    var createdBy : String? = null,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt : LocalDateTime? = null,

    @LastModifiedBy
    @Column(nullable = false)
    var modifiedBy : String? = null,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @LastModifiedDate
    @Column(nullable = false)
    var modifiedAt : LocalDateTime? = null
)