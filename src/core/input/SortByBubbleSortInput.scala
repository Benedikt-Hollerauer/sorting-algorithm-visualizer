package input

import core.model.{OrderValue, SortableModel}
import error.modelError.SortableModelError

case class SortByBubbleSortInput(
    toBeSorted: Either[SortableModelError, SortableModel],
    ordering: OrderValue
)