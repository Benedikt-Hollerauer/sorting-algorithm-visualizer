package core

import core.model.{SortableModel, SortedModel}

trait SortingAlgorithm:
	
	def sortAscending(sortable: SortableModel): SortedModel
	
	def sortDescending(sortable: SortableModel): SortedModel