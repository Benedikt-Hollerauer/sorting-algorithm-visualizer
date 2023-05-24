package mock.inputMock

import core.input.SortByBubbleSortInput
import core.model.{OrderModel, SortableModel, ValueWithIndexModel, IndexModel}
import mock.ToBeSortedMock

import scala.util.Random

object SortByBubbleSortInputMock:

    val ascendingOrder = SortByBubbleSortInput(
        toBeSorted = SortableModel.from(
            ToBeSortedMock.ascendingOrder.
                unsorted
                .zipWithIndex
                .map: (value, index) =>
                    ValueWithIndexModel(
                        value = value,
                        indexModel = IndexModel.from(
                            mayBeIndex = index
                        ).toOption.get
                    )
        ),
        ordering = OrderModel.Ascending
    )

    val descendingOrder = ascendingOrder.copy(
        ordering = OrderModel.Descending
    )

    val emptyListFailure = ascendingOrder.copy(
        toBeSorted = SortableModel.from(
            List.empty[Int].zipWithIndex
                .map: (value, index) =>
                    ValueWithIndexModel(
                        value = value,
                        indexModel = IndexModel.from(
                            mayBeIndex = index
                        ).toOption.get
                    )
        )
    )

    val toFewElementsFailure = ascendingOrder.copy(
        toBeSorted = SortableModel.from(
            List(1).zipWithIndex
                .map: (value, index) =>
                    ValueWithIndexModel(
                        value = value,
                        indexModel = IndexModel.from(
                            mayBeIndex = index
                        ).toOption.get
                    )
        )
    )

    val toManyElementsFailure = ascendingOrder.copy(
        toBeSorted = SortableModel.from(
            List.fill(500)(Random.nextInt(200))
                .zipWithIndex
                .map: (value, index) =>
                    ValueWithIndexModel(
                        value = value,
                        indexModel = IndexModel.from(
                            mayBeIndex = index
                        ).toOption.get
                )
        )
    )