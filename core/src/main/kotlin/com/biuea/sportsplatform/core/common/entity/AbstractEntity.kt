package com.biuea.sportsplatform.core.common.entity

import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@MappedSuperclass
class AbstractEntity {
    @Column(name = "create_date", nullable = false)
    var createDate: ZonedDateTime = ZonedDateTime.now()

    @Column(name = "update_date", nullable = false)
    var updateDate: ZonedDateTime = ZonedDateTime.now()

    @PrePersist
    fun prePersist() {
        createDate = ZonedDateTime.now()
        updateDate = ZonedDateTime.now()
    }

    @PreUpdate
    fun preUpdate() {
        updateDate = ZonedDateTime.now()
    }
}
