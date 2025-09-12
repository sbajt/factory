package com.sbajt.matscounter.ui.useCases

import com.sbajt.matscounter.domain.models.ItemDomain
import com.sbajt.matscounter.domain.repositories.ItemsRepository
import com.sbajt.matscounter.ui.models.getName
import com.sbajt.matscounter.ui.models.toGroupType
import com.sbajt.matscounter.ui.models.toLowerGroupType
import com.sbajt.matscounter.ui.models.views.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.views.BuildMaterialUiState
import com.sbajt.matscounter.ui.models.views.ItemUiState
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

class ItemUiStateListUseCase(
    private val itemsRepository: ItemsRepository,
) {
    operator fun invoke(): Flow<List<ItemUiState>> = itemsRepository.getData().toItemUiStateList()

    private fun Flow<List<ItemDomain>>.toItemUiStateList(): Flow<List<ItemUiState>> = this
        .mapNotNull { itemDomainList ->
            itemDomainList.mapNotNull { it.toItemUiState() }
        }
}

fun ItemDomain?.toItemUiState(): ItemUiState? = if (this != null) {
    val itemGroupType = groupType.toGroupType()
    val lowerGroupType = itemGroupType.toLowerGroupType()
    ItemUiState(
        name = name ?: "",
        imageName = imageName ?: "",
        groupType = itemGroupType,
        buildMaterialListWrapper = BuildMaterialListWrapper(
            titleText = lowerGroupType.getName(),
            groupType = lowerGroupType,
            buildMaterialsList = buildMaterials.map {
                BuildMaterialUiState(
                    name = it.name ?: "",
                    amount = it.count,
                )
            }.toPersistentList(),
        )
    )
} else {
    null
}
