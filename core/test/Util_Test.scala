package test

import core.Util.*
import core.model.ValueWithIndexModel
import test.TestUtil.assertRight

object Util_Test:

	object toValuesWithIndices_should_return:

		def `List[ValueWithIndexModel]`: Unit = // TODO use toValuesWithIndices only on NonEmptyList
			val res = Right(
				List(9, 2, 5, 11, 23).toValuesWithIndices
			)
			assertRight(res)(
				(res: List[ValueWithIndexModel]) => Seq(
					res.head.value == 9,
					res.last.value == 23,
					res.head.indexModel.index == 0,
					res.last.indexModel.index == 4
				)
			)