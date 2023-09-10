package com.biuea.sportsplatform.core.infrastructure.file.client

import com.biuea.sportsplatform.fileserver.interfaces.FileStorageAccessorClient
import com.biuea.sportsplatform.fileserver.interfaces.request.CommonFileStorageAccessorRequest
import org.springframework.stereotype.Component
import java.io.InputStream
import java.time.ZonedDateTime

@Component
class FileStorageClient(
    private val fileStorageAccessorClient: FileStorageAccessorClient
) {
    fun assignSdkKey(
        userId: Long,
        email: String,
        expirationDate: ZonedDateTime
    ): String = fileStorageAccessorClient.assignSdkKey(
        requestUserId = userId,
        requestUserEmail = email,
        expirationDate = expirationDate
    )

    fun uploadObject(
        request: CommonFileStorageAccessorRequest,
        inputStream: InputStream,
        size: Long
    ) {
        fileStorageAccessorClient.uploadObject(
            builder = request,
            inputStream = inputStream,
            size = size
        )
    }
}