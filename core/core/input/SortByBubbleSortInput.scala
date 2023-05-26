package core.input

import core.model.{NonEmptyListModel, OrderModel, SortableModel}
import error.modelError.{NonEmptyListModelError, SortableModelError}

case class SortByBubbleSortInput(
    toBeSorted: SortableModel,
    ordering: OrderModel
)