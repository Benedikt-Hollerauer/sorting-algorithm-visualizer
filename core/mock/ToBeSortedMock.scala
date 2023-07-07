package mock

import scala.util.Random

case class ToBeSortedMock private(
    sortedOnce: List[Int],
    sorted: List[Int],
)

object ToBeSortedMock:

    val unsorted = List(636, 743, 5, 395, 266, 292, 662, 196, 267, 259)

    val biggest = 743

    val smallest = 5

    val ascendingOrder = ToBeSortedMock(
        sortedOnce = List(636, 5, 395, 266, 292, 662, 196, 267, 259, 743),
        sorted = unsorted.sorted
    )

    val descendingOrder = ToBeSortedMock(
        sortedOnce = List(743, 636, 395, 266, 292, 662, 196, 267, 259, 5),
        sorted = unsorted.sorted(Ordering[Int].reverse)
    )