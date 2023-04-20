package core.input

import core.model.{OrderModel, SortableModel}
import error.modelError.SortableModelError

case class SortByBubbleSortInput(
    toBeSorted: Either[SortableModelError, SortableModel],
    ordering: OrderModel
)