package mock

import scala.util.Random

case class ToBeSortedMock private(
    sortedOnce: List[Int],
    sorted: List[Int],
)

object ToBeSortedMock:

    val unsorted: List[Int] = List(2, 3, 1)
    (2, 3)
    (3, 1)
    (2, 1)

    val biggest: Int = unsorted.max

    val middle: Int = 2

    val smallest: Int = unsorted.min

    val ascendingOrder = ToBeSortedMock(
        sortedOnce = List(2, 1, 3),
        sorted = unsorted.sorted
    )

    val descendingOrder = ToBeSortedMock(
        sortedOnce = List(3, 2, 1),
        sorted = unsorted.sorted(Ordering[Int].reverse)
    )