package com.sbajt.matscounter.ui.useCases

import com.sbajt.matscounter.domain.models.ItemDomain
import com.sbajt.matscounter.domain.repositories.ItemsRepository
import com.sbajt.matscounter.ui.mappers.toItemUiState
import com.sbajt.matscounter.ui.models.ItemUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

class ItemDomainListUseCase(
    private val itemsRepository: ItemsRepository,
) {
    operator fun invoke(): Flow<List<ItemUiState>> = itemsRepository.getData().toItemUiStateList()

    private fun Flow<List<ItemDomain>>.toItemUiStateList(): Flow<List<ItemUiState>> = this
        .mapNotNull { itemDomainList ->
            itemDomainList.mapNotNull { it.toItemUiState() }
        }
}
