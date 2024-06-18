package com.ojajae.domain.store.service

import com.ojajae.common.exception.CustomException
import com.ojajae.common.web.ResultDTO
import com.ojajae.domain.store.entity.Store
import com.ojajae.domain.store.form.request.StoreRequestForm
import com.ojajae.domain.store.form.response.StoreDetailResponseForm
import com.ojajae.domain.store.repository.StoreCustomRepository
import org.springframework.stereotype.Service

@Service
class StoreService(
    val storeRepository: StoreCustomRepository
) {
    fun getStore(storeId: Long): ResultDTO {
        val store: Store = storeRepository.getStore(StoreRequestForm(storeId = storeId)) ?: throw CustomException("업체를 찾을 수 없습니다.")

        val resultDTO: ResultDTO = ResultDTO.createSuccess("")
        resultDTO.put("storeDetail", StoreDetailResponseForm.of(store))

        return resultDTO
    }
}