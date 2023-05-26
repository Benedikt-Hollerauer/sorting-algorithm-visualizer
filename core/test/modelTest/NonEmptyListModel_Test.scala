package test.modelTest

import core.model.NonEmptyListModel
import error.modelError.NonEmptyListModelError
import test.TestUtil.*

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

		def `EmptyList`: Unit =
			val res = NonEmptyListModel.from(
				mayBeList = List.empty
			)
			assertLeft(res)(
				NonEmptyListModelError.EmptyList
			)

		def `ToFewElements`: Unit =
			val res = NonEmptyListModel.from(
				mayBeList = List(1)
			)
			assertLeft(res)(
				NonEmptyListModelError.ToFewElements(1)
			)