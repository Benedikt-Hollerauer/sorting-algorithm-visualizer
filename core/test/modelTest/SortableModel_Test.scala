package test.modelTest

import core.Util.*
import core.model.{IndexModel, SortableModel, ValueWithIndexModel}
import error.modelError.SortableModelError
import test.TestUtil.*

import scala.util.Random

object SortableModel_Test:

    object from_should_return:

        def `SortableModel(List(1, 4, 7))`: Unit =
            val res = SortableModel.from(
                mayBeList = List(1, 4, 7).toValuesWithIndices
            )
            assertRight(res)(
                (res: SortableModel) =>
                    Seq(
                        res.valuesWithIndices.head.value == 1,
                        res.valuesWithIndices.last.value == 7,
                        res.valuesWithIndices.head.indexModel.index == 0,
                        res.valuesWithIndices.last.indexModel.index == 2
                    )
            )

        def `EmptyList`: Unit =
            val res = SortableModel.from(
                mayBeList = List.empty[Int].toValuesWithIndices
            )
            assertLeft(res)(SortableModelError.EmptyList)

        def `ToFewElements`: Unit =
            val res = SortableModel.from(
                mayBeList = List(1).toValuesWithIndices
            )
            assertLeft(res)(SortableModelError.ToFewElements(1))

        def `ToManyElements`: Unit =
            val res = SortableModel.from(
                mayBeList = List.fill(501)(Random.nextInt(200)).toValuesWithIndices
            )
            assertLeft(res)(SortableModelError.ToManyElements(501))