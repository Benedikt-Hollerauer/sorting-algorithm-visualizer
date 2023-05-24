package test.modelTest

import core.model.{SortableModel, ValueWithIndexModel}
import error.modelError.SortableModelError

import core.model.IndexModel
import scala.util.Random

object SortableModel_Test:

    object from_should_return:

        def `SortableModel(List(1, 4, 7))`: Unit =
            val correctMayBeListMock =
                List(1, 4, 7).zipWithIndex
                    .map: (value, index) =>
                        ValueWithIndexModel(
                            value = value,
                            indexModel = IndexModel.from(
                                mayBeIndex = index
                            ).toOption.get
                        )
            for
                res <- core.model.SortableModel.from(
                    mayBeList = correctMayBeListMock
                )
            yield
                assert(res.valuesWithIndices.head.value == 1)
                assert(res.valuesWithIndices.last.value == 7)
                assert(res.valuesWithIndices.head.indexModel.index == 0)
                assert(res.valuesWithIndices.last.indexModel.index == 2)

        def `EmptyList`: Unit =
            for
                res <- core.model.SortableModel.from(
                    mayBeList = List.empty
                ).left
            yield assert(res == SortableModelError.EmptyList)

        def `ToFewElements`: Unit =
            val toFewElementsMayBeListMock = List(1).zipWithIndex
                .map: (value, index) =>
                    ValueWithIndexModel(
                        value = value,
                        indexModel = IndexModel.from(
                            mayBeIndex = index
                        ).toOption.get
                    )
            for
                res <- core.model.SortableModel.from(
                    mayBeList = toFewElementsMayBeListMock
                ).left
            yield assert(res == SortableModelError.ToFewElements(1))

        def `ToManyElements`: Unit =

            val toManyElementsMayBeListMock = List.fill(500)(Random.nextInt(200))
                .zipWithIndex
                .map: (value, index) =>
                    ValueWithIndexModel(
                        value = value,
                        indexModel = IndexModel.from(
                            mayBeIndex = index
                        ).toOption.get
                    )
            for
                res <- core.model.SortableModel.from(
                    mayBeList = toManyElementsMayBeListMock
                ).left
            yield assert(res == SortableModelError.ToManyElements(500))