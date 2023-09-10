package com.biuea.sportsplatform.core.infrastructure.file.store

import com.biuea.sportsplatform.core.domain.file.store.FileStorageStore
import com.biuea.sportsplatform.core.infrastructure.file.client.FileStorageClient
import com.biuea.sportsplatform.fileserver.interfaces.request.CommonFileStorageAccessorRequest
import org.springframework.stereotype.Component
import java.io.InputStream
import java.time.ZonedDateTime

@Component
class FileStorageStoreImpl(
    private val fileStorageClient: FileStorageClient
): FileStorageStore {
    override fun assignSdkKey(
        userId: Long,
        email: String,
        expirationDate: ZonedDateTime
    ): String = fileStorageClient.assignSdkKey(
        userId = userId,
        email = email,
        expirationDate = expirationDate
    )

    override fun uploadObject(
        bucket: String,
        key: String,
        sdkKey: String,
        inputStream: InputStream,
        size: Long
    ) {
        val request = CommonFileStorageAccessorRequest(
            bucket = bucket,
            key = key,
            sdkKey = sdkKey
        )
        fileStorageClient.uploadObject(
            request = request,
            inputStream = inputStream,
            size = size
        )
    }
}