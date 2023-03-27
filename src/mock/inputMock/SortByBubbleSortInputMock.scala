package mock.inputMock

import core.model.{OrderValue, SortableModel}
import input.SortByBubbleSortInput

object SortByBubbleSortInputMock:

    private val sortByBubbleSortInputMock = SortByBubbleSortInput(
        toBeSorted = SortableModel.from(List(3, 2, 8, 16, 4, 5934, 435, 1)),
        ordering = OrderValue.Ascending
    )

    val ascendingOrder = sortByBubbleSortInputMock

    val descendingOrder = sortByBubbleSortInputMock.copy(
        ordering = OrderValue.Descending
    )

    val emptyListFailure = sortByBubbleSortInputMock.copy(
        toBeSorted = SortableModel.from(List.empty[Int])
    )

    val toFewElementsFailure = sortByBubbleSortInputMock.copy(
        toBeSorted = SortableModel.from(List(1))
    )