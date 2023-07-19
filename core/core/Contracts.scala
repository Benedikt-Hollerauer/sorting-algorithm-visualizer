package core

import core.input.SortingAlgorithmUseCaseInput
import core.model.{SortableModel, SortedModel}

object Contracts:
	
	trait SortingAlgorithmEntity:
		
		def sortAscending(sortable: SortableModel): SortedModel
		
		def sortDescending(sortable: SortableModel): SortedModel
		
	trait SortingAlgorithmUseCase:

		def apply(input: SortingAlgorithmUseCaseInput): SortedModel	