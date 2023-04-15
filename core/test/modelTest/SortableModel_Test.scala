package test.modelTest

import core.model.SortableModel
import error.modelError.SortableModelError

import scala.util.Random

object SortableModel_Test:

    val correctChangedIndices: List[Int] = List(0, 1)

    object from_should_return:

        def `SortableValue`: Unit =
            for
                res <- SortableModel.from(
                    mayBeList = List(1, 4, 7),
                    mayBeChangedIndices = correctChangedIndices
                )
            yield
                assert(res.list == List(1, 4, 7))
                assert(res.changedIndices == correctChangedIndices)

        def `EmptyList`: Unit =
            for
                res <- SortableModel.from(
                    mayBeList = List.empty,
                    mayBeChangedIndices = correctChangedIndices
                ).left
            yield assert(res == SortableModelError.EmptyList)

        def `ToFewElements`: Unit =
            for
                res <- SortableModel.from(
                    mayBeList = List(1),
                    mayBeChangedIndices = correctChangedIndices
                ).left
            yield assert(res == SortableModelError.ToFewElements(1))

        def `ToManyElements`: Unit =
            for
                res <- SortableModel.from(
                    mayBeList = List.fill(500)(Random.nextInt(200)),
                    mayBeChangedIndices = correctChangedIndices
                ).left
            yield assert(res == SortableModelError.ToManyElements(500))

        def `ToFewChangedIndices`: Unit =
            for
                res <- SortableModel.from(
                    mayBeList = List.fill(500)(Random.nextInt(200)),
                    mayBeChangedIndices = List.empty
                ).left
            yield assert(res == SortableModelError.ToFewChangedIndices(List.empty))