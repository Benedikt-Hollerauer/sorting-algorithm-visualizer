package mock.inputMock

import core.model.{OrderModel, SortableModel}
import input.SortByBubbleSortInput

object SortByBubbleSortInputMock:

    private val sortByBubbleSortInputMock = SortByBubbleSortInput(
        toBeSorted = SortableModel.from(List(3, 2, 8, 16, 4, 5934, 435, 1)),
        ordering = OrderModel.Ascending
    )

    val ascendingOrder = sortByBubbleSortInputMock

    val descendingOrder = sortByBubbleSortInputMock.copy(
        ordering = OrderModel.Descending
    )

    val emptyListFailure = sortByBubbleSortInputMock.copy(
        toBeSorted = SortableModel.from(List.empty[Int])
    )

    val toFewElementsFailure = sortByBubbleSortInputMock.copy(
        toBeSorted = SortableModel.from(List(1))
    )