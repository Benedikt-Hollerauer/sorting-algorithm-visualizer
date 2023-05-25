package test.modelTest

import core.model.IndexModel
import error.modelError.IndexModelError
import test.TestUtil.*

object IndexModel_Test:

	object from_should_return:

		def `IndexModel(0)`: Unit =
			val mayBeIndexMock: Int = 0
			val res = IndexModel.from(
				mayBeIndex = mayBeIndexMock
			)
			assertRight(res)(
				checkRight = Seq(
					(i: IndexModel) => i.index == mayBeIndexMock
				)
			)

		def `IndexModel(100)`: Unit =
			val mayBeIndexMock: Int = 100
			val res = IndexModel.from(
				mayBeIndex = mayBeIndexMock
			)
			assertRight(res)(
				checkRight = Seq(
					(i: IndexModel) => i.index == mayBeIndexMock
				)
			)

		def `NegativeIndex`: Unit =
			val negativeIndexMock: Int = -1
			val res = IndexModel.from(
				mayBeIndex = negativeIndexMock
			)
			assertLeft(res)(IndexModelError.NegativeIndex(negativeIndexMock))