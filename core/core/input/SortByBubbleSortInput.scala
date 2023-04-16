package core.input

import core.model.{OrderModel, SortableModel}
import error.modelError.SortedModelError

case class SortByBubbleSortInput(
    toBeSorted: Either[SortedModelError, SortableModel],
    ordering: OrderModel
)