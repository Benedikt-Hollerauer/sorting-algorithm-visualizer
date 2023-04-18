package test.modelTest

import core.model.SortableModel
import error.modelError.SortableModelError

import scala.util.Random

object SortableModel_Test:

    object from_should_return:

        def `SortableModel`: Unit =
            for
                res <- core.model.SortableModel.from(
                    mayBeList = List(1, 4, 7)
                )
            yield assert(res.list == List(1, 4, 7))

        def `EmptyList`: Unit =
            for
                res <- core.model.SortableModel.from(
                    mayBeList = List.empty
                ).left
            yield assert(res == SortableModelError.EmptyList)

        def `ToFewElements`: Unit =
            for
                res <- core.model.SortableModel.from(
                    mayBeList = List(1)
                ).left
            yield assert(res == SortableModelError.ToFewElements(1))

        def `ToManyElements`: Unit =
            for
                res <- core.model.SortableModel.from(
                    mayBeList = List.fill(500)(Random.nextInt(200))
                ).left
            yield assert(res == SortableModelError.ToManyElements(500))