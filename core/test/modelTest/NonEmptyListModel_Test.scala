package test.modelTest

import core.model.NonEmptyListModel
import error.modelError.NonEmptyListModelError
import test.TestUtil.*

import scala.util.Try

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

		def `LessThan2Elements - 1 Element`: Unit =
			val res = NonEmptyListModel.from(
				mayBeList = List(1)
			)
			assertLeft(res)(
				NonEmptyListModelError.LessThanTwoElements
			)

		def `LessThan2Elements - EmptyList`: Unit =
			val res = NonEmptyListModel.from(
				mayBeList = List.empty
			)
			assertLeft(res)(
				NonEmptyListModelError.LessThanTwoElements
			)

	object fromUnsafe_should_return:

		def `NonEmptyListModel - List(1, 2, 3)`: Unit =
			val mayBeListMock = List(1, 2, 3)
			val res = NonEmptyListModel.fromUnsafe(
				mayBeList = mayBeListMock
			)
			assert(res.list == mayBeListMock)

		def `RuntimeException`: Unit =
			val res = Try(
				NonEmptyListModel.fromUnsafe(
					mayBeList = List.empty
				)
			)
			assert(res.isFailure)