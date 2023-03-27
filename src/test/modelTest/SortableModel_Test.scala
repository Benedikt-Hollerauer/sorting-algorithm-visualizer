package test.modelTest

import error.modelError.SortableModelError

object SortableModel_Test:

    object from_should_return:

        def `SortableValue`: Unit =
            for
                res <- core.model.SortableValue.from(
                    List(1, 4, 7)
                )
            yield assert(res.list == List(1, 4, 7))

        def `EmptyList`: Unit =
            for
                res <- core.model.SortableValue.from(
                    List.empty
                ).left
            yield assert(res == SortableModelError.EmptyList(
                List.empty
            ))

        def `ToFewElements`: Unit =
            for
                res <- core.model.SortableValue.from(
                    List(1)
                ).left
            yield assert(res == SortableModelError.ToFewElements(
                List(1)
            ))
