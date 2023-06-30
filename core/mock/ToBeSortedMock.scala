package mock

import scala.util.Random

case class ToBeSortedMock private(
    unsorted: List[Int],
    sortedOnce: List[Int],
    sorted: List[Int],
)

object ToBeSortedMock:

    val unsorted = List(-2, 999999, 123, 1, 6, 6, 0, 0, 134, 564, 123, 76, 234, 84, 234, 6587, 234, 134, 6578 ,1234 ,564, -1, -500)

    val ascendingOrder = ToBeSortedMock(
        unsorted = unsorted,
        sortedOnce = List(-2, 123, 1, 6, 6, 0, 0, 134, 564, 123, 76, 234, 84, 234, 6587, 234, 134, 6578, 1234, 564, -1, -500, 999999),
        sorted = unsorted.sorted
    )

    val descendingOrder = ToBeSortedMock(
        unsorted = unsorted,
        sortedOnce = List(999999, 123, 1, 6, 6, 0, 0, 134, 564, 123, 76, 234, 84, 234, 6587, 234, 134, 6578, 1234, 564, -1, -2, -500),
        sorted = unsorted.sorted(Ordering[Int].reverse)
    )