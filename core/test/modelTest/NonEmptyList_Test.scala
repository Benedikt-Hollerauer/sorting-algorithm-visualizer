package test.modelTest

import test.TestUtil.*

object NonEmptyList_Test:

	object from_should_return:

		def `NonEmptyList - List(1, 2, 3)`: Unit =
			val res = NonEmptyList.from(
				mayBeList = List(1, 2, 3)
			)
			assertRight(res)(
				(res: NonEmptyList) => Seq(
					res.list == List(1, 2, 3)
				)
			)

		def `ToFewElements`: Unit =
			val res = NonEmptyList.from(
				mayBeList = List(1)
			)
			assertLeft(res)(
				NonEmptyListModelError.ToFewElements(List(1))
			)

		def `EmptyList`: Unit =
			val res = NonEmptyList.from(
				mayBeList = List.empty
			)
			assertLeft(res)(
				NonEmptyListModelError.EmptyList
			)