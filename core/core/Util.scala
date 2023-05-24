package core

import core.model.{IndexModel, ValueWithIndexModel}

object Util:

	extension (list: List[Int])
		def toValuesWithIndices: List[ValueWithIndexModel] = list
			.zipWithIndex
			.map: (value, index) =>
				ValueWithIndexModel(
					value = value,
					indexModel = IndexModel.from(
						mayBeIndex = index
					).toOption.get
				)