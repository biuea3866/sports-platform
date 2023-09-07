package com.biuea.sportsplatform.core.common.response

data class CommonPageResponse<T>(
    var page: Int,
    var pageSize: Int,
    var totalCount: Int,
    var datas: List<T>
) {
    var totalPage: Int = 0
    var hasPrev: Boolean = false
    var hasNext: Boolean = false

    init {
        val totalPagePre = this.totalCount / this.pageSize

        this.totalPage = if (this.totalCount % this.pageSize == 0) {
            if (totalPagePre == 0) 0
            else totalPagePre - 1
        } else {
            totalPagePre
        }
        this.hasPrev = this.page != 0 && this.page <= this.totalPage
        this.hasNext =
            this.page != this.totalPage && this.page <= this.totalPage
    }
}
