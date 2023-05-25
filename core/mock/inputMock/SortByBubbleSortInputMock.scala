package mock.inputMock

import core.Util.toValuesWithIndices
import core.input.SortByBubbleSortInput
import core.model.{IndexModel, OrderModel, SortableModel, ValueWithIndexModel}
import mock.ToBeSortedMock

import scala.util.Random

object SortByBubbleSortInputMock:

    val ascendingOrder = SortByBubbleSortInput(
        toBeSorted = SortableModel.from(
            ToBeSortedMock.ascendingOrder.
                unsorted
                .toValuesWithIndices
        ),
        ordering = OrderModel.Ascending
    )

    val descendingOrder = ascendingOrder.copy(
        ordering = OrderModel.Descending
    )

    val emptyListFailure = ascendingOrder.copy(
        toBeSorted = SortableModel.from(
            List.empty[Int].toValuesWithIndices
        )
    )

    val toFewElementsFailure = ascendingOrder.copy(
        toBeSorted = SortableModel.from(
            List(1).toValuesWithIndices
        )
    )

    val toManyElementsFailure = ascendingOrder.copy(
        toBeSorted = SortableModel.from(
            List.fill(501)(Random.nextInt(200))
                .toValuesWithIndices
        )
    )