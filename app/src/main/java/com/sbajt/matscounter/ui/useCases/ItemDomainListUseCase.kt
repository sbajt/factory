package com.sbajt.matscounter.ui.useCases

import com.sbajt.matscounter.domain.models.ItemDomain
import com.sbajt.matscounter.domain.repositories.ItemsRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

class ItemDomainListUseCase(
    private val itemsRepository: ItemsRepository,
) {
    operator fun invoke(): Flow<List<ItemDomain>> = itemsRepository.getData()
}
