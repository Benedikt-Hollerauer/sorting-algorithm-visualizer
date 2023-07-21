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
				NonEmptyListModelError.ToFewElements
			)

		def `LessThan2Elements - EmptyList`: Unit =
			val res = NonEmptyListModel.from(
				mayBeList = List.empty
			)
			assertLeft(res)(
				NonEmptyListModelError.ToFewElements
			)

	object fromUnsafe_should_return:

		def `NonEmptyListModel - List(1, 2, 3)`: Unit =
			val res = NonEmptyListModel.fromUnsafe(
				mayBeList = List(1, 2, 3)
			)
			assert(res.list == List(1, 2, 3))

		def `RuntimeException`: Unit =
			Try(
				NonEmptyListModel.fromUnsafe(
					mayBeList = List.empty
				)
			).isFailure