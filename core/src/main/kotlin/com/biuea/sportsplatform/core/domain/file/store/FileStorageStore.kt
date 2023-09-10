package com.biuea.sportsplatform.core.domain.file.store

import java.io.InputStream
import java.time.ZonedDateTime

interface FileStorageStore {
    fun assignSdkKey(
        userId: Long,
        email: String,
        expirationDate: ZonedDateTime
    ): String

    fun uploadObject(
        bucket: String,
        key: String,
        sdkKey: String,
        inputStream: InputStream,
        size: Long
    )
}