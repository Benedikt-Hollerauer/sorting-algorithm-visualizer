package core

import core.input.SortingAlgorithmUseCaseInput
import core.model.{SortableModel, SortedModel, SortingModel, ValueWithIndexModel}

object Contract:
	
	trait SortingAlgorithmEntity[T <: SortingModel]:
		
		def sortAscending(sortable: SortableModel[ValueWithIndexModel]): SortedModel[T]
		
		def sortDescending(sortable: SortableModel[ValueWithIndexModel]): SortedModel[T]
		
	trait SortingAlgorithmUseCase[T <: SortingModel]:

		def apply(input: SortingAlgorithmUseCaseInput): SortedModel[T]