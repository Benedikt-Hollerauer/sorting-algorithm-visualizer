package mock.inputMock

import core.model.{OrderModel, SortedModel}
import core.input.SortByBubbleSortInput
import mock.ToBeSortedMock

import scala.util.Random

object SortByBubbleSortInputMock:

    val ascendingOrder = SortByBubbleSortInput(
        toBeSorted = SortedModel.from(ToBeSortedMock.ascendingOrder.unsorted),
        ordering = OrderModel.Ascending
    )

    val descendingOrder = ascendingOrder.copy(
        ordering = OrderModel.Descending
    )

    val emptyListFailure = ascendingOrder.copy(
        toBeSorted = SortedModel.from(List.empty[Int])
    )

    val toFewElementsFailure = ascendingOrder.copy(
        toBeSorted = SortedModel.from(List(1))
    )

    val toManyElementsFailure = ascendingOrder.copy(
        toBeSorted = SortedModel.from(List.fill(500)(Random.nextInt(200)))
    )