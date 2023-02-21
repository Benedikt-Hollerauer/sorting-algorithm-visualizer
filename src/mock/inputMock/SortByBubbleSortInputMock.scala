package mock.inputMock

import core.value.OrderValue
import input.SortByBubbleSortInput

object SortByBubbleSortInputMock:

    private val sortByBubbleSortInputMock = SortByBubbleSortInput(
        list = List(1, 2, 3, 4, 8, 16, 435, 5934),
        ordering = OrderValue.Ascending
    )

    val ascendingOrder = sortByBubbleSortInputMock

    val descendingOrder = sortByBubbleSortInputMock.copy(
        ordering = OrderValue.Descending
    )

    val emptyListFailure = sortByBubbleSortInputMock.copy(
        list = List.empty
    )

    val toFewElementsFailure = sortByBubbleSortInputMock.copy(
        list = List(1)
    )