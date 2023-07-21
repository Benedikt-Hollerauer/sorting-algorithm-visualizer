package test.modelTest

import core.model.{NonEmptyListModel, OrderModel, SortableModel}
import error.modelError.{NonEmptyListModelError, SortableModelError}
import test.TestUtil.*
import mock.modelMock.SortableModelMock

import scala.util.{Random, Try}

object NonEmptyListModel_Test:

	object from_should_return:

		def `NonEmptyListModel - List(1, 2, 3)`: Unit =
			val res = NonEmptyListModel.from(
				mayBeList = List(1, 2, 3)
			)
			assertRight(res)(
				(res: NonEmptyListModel[Int]) => Seq(
					res.list == List(1, 2, 3)
				)
			)

		def `LessThan2Elements - one element`: Unit =
			val res = NonEmptyListModel.from(
				mayBeList = List(1)
			)
			assertLeft(res)(
				NonEmptyListModelError.LessThanTwoElements
			)

		def `LessThan2Elements - empty list`: Unit =
			val res = NonEmptyListModel.from(
				mayBeList = List.empty
			)
			assertLeft(res)(
				NonEmptyListModelError.LessThanTwoElements
			)

		def `ToManyElements`: Unit =
			val res = NonEmptyListModel.from(
				List.fill(501)(Random.nextInt(200))
			)
			assertLeft(res)(NonEmptyListModelError.ToManyElements(501))

	object fromUnsafe_should_return:

		def `NonEmptyListModel - List(1, 2, 3)`: Unit =
			val mayBeListMock = List(1, 2, 3)
			val res = NonEmptyListModel.fromUnsafe(
				mayBeList = mayBeListMock
			)
			assert(res.list == mayBeListMock)

		def `RuntimeException - less than two elements`: Unit =
			val res = Try(
				NonEmptyListModel.fromUnsafe(
					mayBeList = List.empty
				)
			)
			assert(res.isFailure)

		def `RuntimeException - to many elements`: Unit =
			val res = Try(
				NonEmptyListModel.fromUnsafe(
					mayBeList = List.fill(501)(Random.nextInt(200))
				)
			)
			assert(res.isFailure)

	object getSorted_should_return:

		private def isCorrectlySorted(mayBeCorrectlySorted: SortableModel, ordering: OrderModel): Boolean =
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