package coreTest.useCaseTest

object SortByBubbleSortUseCase_Test:

    object apply_should_return:

        def `LazyList[AscendingSortable](ascending)`: Unit =
            for
                res <- SortByBubbleSortUseCase(
                    input = SortByBubbleSortInputMock.ascendingOrder
                )
            yield assert(res.reverse.head == List(1, 2, 3, 4, 8, 16, 435, 5934))

        def `LazyList[Sortable](descending)`: Unit =
            for
                res <- SortByBubbleSortUseCase(
                    input = SortByBubbleSortInputMock.descendingOrder
                )
            yield assert(res.reverse.head == List(5934, 435, 16, 8, 4, 3, 2, 1))

        def `InputFailure[EmptyList]`: Unit =
            for
                res <- SortByBubbleSortUseCase(
                    input = SortByBubbleSortInputMock.emptyListFailure
                ).left
            yield assert(res == SortByBubbleSortUseCaseError.InputFailure(
                SortableError.EmptyList(
                    List.empty
                )
            ))