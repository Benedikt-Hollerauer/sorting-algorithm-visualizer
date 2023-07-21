package test.modelTest

import core.model.IndexModel
import error.modelError.IndexModelError
import test.TestUtil.*

import scala.util.Try

object IndexModel_Test:

	object from_should_return:

		def `IndexModel - 0`: Unit =
			val mayBeIndexMock: Int = 0
			val res = IndexModel.from(
				mayBeIndex = mayBeIndexMock
			)
			assertRight(res)(
				checkRight = (i: IndexModel) => Seq(
					i.index == mayBeIndexMock
				)
			)

		def `IndexModel - 100`: Unit =
			val mayBeIndexMock: Int = 100
			val res = IndexModel.from(
				mayBeIndex = mayBeIndexMock
			)
			assertRight(res)(
				checkRight = (i: IndexModel) => Seq(
					i.index == mayBeIndexMock
				)
			)

		def `NegativeIndex`: Unit =
			val negativeIndexMock: Int = -1
			val res = IndexModel.from(
				mayBeIndex = negativeIndexMock
			)
			assertLeft(res)(
				IndexModelError.NegativeIndex(negativeIndexMock)
			)

	object fromUnsafe_should_return:

		def `IndexModel - 0`: Unit =
			val mayBeIndexMock: Int = 0
			val res = IndexModel.fromUnsafe(
				mayBeIndex = mayBeIndexMock
			)
			assert(res.index == mayBeIndexMock)

		def `IndexModel - 100`: Unit =
			val mayBeIndexMock: Int = 100
			val res = IndexModel.fromUnsafe(
				mayBeIndex = mayBeIndexMock
			)
			assert(res.index == mayBeIndexMock)

		def `RuntimeException`: Unit =
			val res = Try(
				IndexModel.fromUnsafe(
					mayBeIndex = -1
				)
			)
			assert(res.isFailure)