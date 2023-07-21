package core

import core.input.SortingAlgorithmUseCaseInput
import core.model.{SortableModel, SortedModel, ValueWithIndexModel}

object Contract:
	
	trait SortingAlgorithmEntity:
		
		def sortAscending(sortable: SortableModel[ValueWithIndexModel]): SortedModel
		
		def sortDescending(sortable: SortableModel[ValueWithIndexModel]): SortedModel
		
	trait SortingAlgorithmUseCase:

		def apply(input: SortingAlgorithmUseCaseInput): SortedModel	