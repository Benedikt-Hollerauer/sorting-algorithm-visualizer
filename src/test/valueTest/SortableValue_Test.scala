package test.valueTest

import error.valueError.SortableValueError

object SortableValue_Test:

    object from_Should_return:

        def `SortableValue`: Unit =
            for
                res <- core.value.SortableValue.from(
                    List(1, 4, 7)
                )
            yield assert(res.list == List(1, 4, 7))

        def `EmptyList`: Unit =
            for
                res <- core.value.SortableValue.from(
                    List.empty
                ).left
            yield assert(res == SortableValueError.EmptyList(
                List.empty
            ))

        def `ToFewElements`: Unit =
            for
                res <- core.value.SortableValue.from(
                    List(1)
                ).left
            yield assert(res == SortableValueError.ToFewElements(
                List(1)
            ))
