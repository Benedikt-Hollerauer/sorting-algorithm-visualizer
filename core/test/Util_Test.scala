package test

import core.Util.*

object Util_Test:

	object toValuesWithIndices_should_return:

		def `List[ValueWithIndexModel]`: Unit =
			val res = List(9, 2, 5, 11, 23).toValuesWithIndices
			assert(res.head.value == 9)
			assert(res.last.value == 23)
			assert(res.head.indexModel.index == 0)
			assert(res.last.indexModel.index == 4)