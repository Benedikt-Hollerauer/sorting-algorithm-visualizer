package core.typeClass

import core.model.{SortedModel, VisualizeModel}

trait GetBarVisualisation:

	def getBarVisualisation(
		sortedModel: SortedModel
	): VisualizeModel
	
object GetBarVisualisation