package mock

import scala.util.Random

case class ToBeSortedMock private(
    sortedOnce: List[Int],
    sorted: List[Int],
)

object ToBeSortedMock:

    val unsorted = List(2, 3, 1)

    val biggest = unsorted.max

    val smallest = unsorted.min

    val ascendingOrder = ToBeSortedMock(
        sortedOnce = List(2, 1, 3),
        sorted = unsorted.sorted
    )

    val descendingOrder = ToBeSortedMock(
        sortedOnce = List(3, 2, 1),
        sorted = unsorted.sorted(Ordering[Int].reverse)
    )