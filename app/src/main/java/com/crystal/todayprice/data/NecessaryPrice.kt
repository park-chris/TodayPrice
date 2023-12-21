package com.crystal.todayprice.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ListNecessariesPricesResponse (
    @SerializedName("ListNecessariesPricesService")
    val listNecessariesPrices: ListNecessariesPrices
)

data class ListNecessariesPrices (
    @SerializedName("list_total_count")
    val listTotalCount: Int,
    @SerializedName("RESULT")
    val result: Result,
    @SerializedName("row")
    val row: List<NecessaryPrice>
)

data class Result(
    @SerializedName("CODE")
    val code: String,
    @SerializedName("MESSAGE")
    val message: String
)

/**
 * @property serialNumber 일련번호
 * @property marketId 시장/마트 번호
 * @property marketName 시장/마트 이름
 * @property itemId 품목 번호
 * @property itemName 품목 이름
 * @property itemUnit 실판매규격
 * @property itemPrice 가격 (원)
 * @property P_YEAR_MONTH 년도-월
 * @property note 비고
 * @property checkDate 점검 일자
 * @property marketTypeCode 시장 유형 구분 코드
 * @property marketTypeName 시장 유형 구분 이름
 * @property marketBoroughCode 자치구 코드
 * @property marketBoroughName 자치구 이름
 */
data class NecessaryPrice (
    @SerializedName("P_SEQ")
    val serialNumber: Double,
    @SerializedName("M_SEQ")
    val marketId: Double,
    @SerializedName("M_NAME")
    val marketName: String,
    @SerializedName("A_SEQ")
    val itemId: Double,
    @SerializedName("A_NAME")
    val itemName: String,
    @SerializedName("A_UNIT")
    val itemUnit: String,
    @SerializedName("A_PRICE")
    val itemPrice: Double,
    @SerializedName("P_YEAR_MONTH")
    val date: String,
    @SerializedName("ADD_COL")
    val note: String,
    @SerializedName("P_DATE")
    val checkDate: String,
    @SerializedName("M_TYPE_CODE")
    val marketTypeCode: String,
    @SerializedName("M_TYPE_NAME")
    val marketTypeName: String,
    @SerializedName("M_GU_CODE")
    val marketBoroughCode: String,
    @SerializedName("M_GU_NAME")
    val marketBoroughName: String
) : Serializable