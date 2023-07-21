package test.modelTest

import core.model.{OrderModel, SortableModel, SortableModelOld}
import error.modelError.SortableModelError
import mock.modelMock.SortableModelMock
import test.TestUtil.*

import scala.util.{Random, Try}

object SortableModel_Test:

	object from_should_return:

		def `NonEmptyListModel - List(1, 2, 3)`: Unit =
			val res = SortableModel.from(
				mayBeList = List(1, 2, 3)
			)
			assertRight(res)(
				(res: SortableModel[Int]) => Seq(
					res.list == List(1, 2, 3)
				)
			)

		def `LessThan2Elements - one element`: Unit =
			val res = SortableModel.from(
				mayBeList = List(1)
			)
			assertLeft(res)(
				SortableModelError.LessThanTwoElements
			)

		def `LessThan2Elements - empty list`: Unit =
			val res = SortableModel.from(
				mayBeList = List.empty
			)
			assertLeft(res)(
				SortableModelError.LessThanTwoElements
			)

		def `ToManyElements`: Unit =
			val res = SortableModel.from(
				List.fill(501)(Random.nextInt(200))
			)
			assertLeft(res)(SortableModelError.ToManyElements(501))

	object fromUnsafe_should_return:

		def `NonEmptyListModel - List(1, 2, 3)`: Unit =
			val mayBeListMock = List(1, 2, 3)
			val res = SortableModel.fromUnsafe(
				mayBeList = mayBeListMock
			)
			assert(res.list == mayBeListMock)

		def `RuntimeException - less than two elements`: Unit =
			val res = Try(
				SortableModel.fromUnsafe(
					mayBeList = List.empty
				)
			)
			assert(res.isFailure)

		def `RuntimeException - to many elements`: Unit =
			val res = Try(
				SortableModel.fromUnsafe(
					mayBeList = List.fill(501)(Random.nextInt(200))
				)
			)
			assert(res.isFailure)

	object getSorted_should_return:

		private def isCorrectlySorted(mayBeCorrectlySorted: SortableModelOld, ordering: OrderModel): Boolean =
			mayBeCorrectlySorted.list.map(_.value) == (
				ordering match
					case OrderModel.Ascending => SortableModelMock.sortedAscending
					case OrderModel.Descending => SortableModelMock.sortedDescending
				).valuesWithIndices.list.map(_.value)

		def `SortableModel - ascending`: Unit =
			val res = SortableModelMock.unsorted
				.getSorted(OrderModel.Ascending)
			assert(isCorrectlySorted(res, OrderModel.Ascending))

		def `SortableModel - descending`: Unit =
			val res = SortableModelMock.unsorted
				.getSorted(OrderModel.Descending)
			assert(isCorrectlySorted(res, OrderModel.Descending))