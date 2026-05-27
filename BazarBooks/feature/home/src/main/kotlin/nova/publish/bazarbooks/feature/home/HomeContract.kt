package nova.publish.bazarbooks.feature.home

import nova.publish.bazarbooks.feature.home.model.HomeSectionUiModel

data class HomeState(val sections: List<HomeSectionUiModel> = emptyList(), val cartItemCount: Int = 0)
sealed interface HomeIntent
sealed interface HomeEffect
