package mock.inputMock

import core.model.{OrderModel, SortableModel}
import core.input.SortByBubbleSortInput
import mock.ToBeSortedMock

import scala.util.Random

object SortByBubbleSortInputMock:

    val ascendingOrder = SortByBubbleSortInput(
        toBeSorted = SortableModel.from(ToBeSortedMock.ascendingOrder.unsorted),
        ordering = OrderModel.Ascending
    )

    val descendingOrder = ascendingOrder.copy(
        ordering = OrderModel.Descending
    )

    val emptyListFailure = ascendingOrder.copy(
        toBeSorted = SortableModel.from(List.empty[Int])
    )

    val toFewElementsFailure = ascendingOrder.copy(
        toBeSorted = SortableModel.from(List(1))
    )

    val toManyElementsFailure = ascendingOrder.copy(
        toBeSorted = SortableModel.from(List.fill(500)(Random.nextInt(200)))
    )