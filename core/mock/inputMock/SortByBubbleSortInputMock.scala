package mock.inputMock

import core.Util.toValuesWithIndices
import core.input.SortByBubbleSortInput
import core.model.*
import mock.ToBeSortedMock

import scala.util.Random

object SortByBubbleSortInputMock:

    val ascendingOrder = SortByBubbleSortInput(
        toBeSorted = ToBeSortedMock.ascendingOrder.unsorted,
        ordering = OrderModel.Ascending
    )

    val descendingOrder = ascendingOrder.copy(
        ordering = OrderModel.Descending
    )

    val emptyListFailure = ascendingOrder.copy(
        toBeSorted = List.empty[Int]
    )

    val toFewElementsFailure = ascendingOrder.copy(
        toBeSorted = List(1)
    )

    val toManyElementsFailure = ascendingOrder.copy(
        toBeSorted = List.fill(501)(Random.nextInt(200))
    )