package mock.inputMock

import core.Util.toValuesWithIndices
import core.input.SortByBubbleSortInput
import core.model.*
import mock.ToBeSortedMock
import mock.modelMock.SortableModelMock

import scala.util.Random

object SortByBubbleSortInputMock:

    val ascendingOrder = SortByBubbleSortInput(
        toBeSorted = SortableModelMock.sortable,
        ordering = OrderModel.Ascending
    )

    val descendingOrder = ascendingOrder.copy(
        ordering = OrderModel.Descending
    )