package mock

import scala.util.Random

case class ToBeSortedMock private(
    unsorted: List[Int],
    sorted: List[Int]
)

object ToBeSortedMock:

    private val unsorted = List.fill(30)(Random.nextInt(200))

    val ascendingOrder = ToBeSortedMock(
        unsorted = unsorted,
        sorted = unsorted.sorted
    )

    val descendingOrder = ToBeSortedMock(
        unsorted = unsorted,
        sorted = unsorted.sorted(Ordering[Int].reverse)
    )