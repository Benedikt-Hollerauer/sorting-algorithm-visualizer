package core.useCase

import core.entity.VisualizeEntity
import core.input.VisualizeSortingInput
import core.model.{SortingModel, VisualizeModel}
import core.typeClass.GetBarModel.given
import core.typeClass.GetBarVisualisation.given
import core.typeClass.{GetBarModel, GetBarVisualisation}

trait VisualizeSortingUseCase[T <: SortingModel]:

	def apply(input: VisualizeSortingInput[T]): VisualizeModel

object VisualizeSortingUseCase:

	given VisualizeSortingUseCase[SortingModel.BubbleSort] with
		override def apply(
			input: VisualizeSortingInput[SortingModel.BubbleSort]
		): VisualizeModel =
			summon[VisualizeEntity[SortingModel.BubbleSort]].getBarVisualisation(
				sortedModel = input.sortedModel
			)