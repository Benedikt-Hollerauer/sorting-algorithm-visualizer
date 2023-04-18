package core.input

import core.model.{OrderModel, SortedModel}
import error.modelError.SortableModelError

case class SortByBubbleSortInput(
    toBeSorted: Either[SortableModelError, SortedModel],
    ordering: OrderModel
)