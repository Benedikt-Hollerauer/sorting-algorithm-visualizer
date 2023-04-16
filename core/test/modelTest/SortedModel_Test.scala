package test.modelTest

import core.model.SortableModel
import error.modelError.SortedModelError

import scala.util.Random

object SortedModel_Test:

    private val correctChangedIndicesMock: List[Int] = List(0, 1)

    object from_should_return:

        def `SortableModel`: Unit =
            for
                res <- core.model.SortableModel.from(
                    mayBeList = List(1, 4, 7),
                    mayBeChangedIndices = correctChangedIndicesMock
                )
            yield
                assert(res.list == List(1, 4, 7))
                assert(res.changedIndices == correctChangedIndicesMock)

        def `EmptyList`: Unit =
            for
                res <- core.model.SortableModel.from(
                    mayBeList = List.empty,
                    mayBeChangedIndices = correctChangedIndicesMock
                ).left
            yield assert(res == SortedModelError.EmptyList)

        def `ToFewElements`: Unit =
            for
                res <- core.model.SortableModel.from(
                    mayBeList = List(1),
                    mayBeChangedIndices = correctChangedIndicesMock
                ).left
            yield assert(res == SortedModelError.ToFewElements(1))

        def `ToManyElements`: Unit =
            for
                res <- core.model.SortableModel.from(
                    mayBeList = List.fill(500)(Random.nextInt(200)),
                    mayBeChangedIndices = correctChangedIndicesMock
                ).left
            yield assert(res == SortedModelError.ToManyElements(500))

        def `ToFewChangedIndices`: Unit =
            for
                res <- core.model.SortableModel.from(
                    mayBeList = List.fill(500)(Random.nextInt(200)),
                    mayBeChangedIndices = List.empty
                ).left
            yield assert(res == SortedModelError.ToManyChangedIndices(List.empty))