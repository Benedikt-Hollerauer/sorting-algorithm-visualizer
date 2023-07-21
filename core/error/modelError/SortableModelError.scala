package error.modelError

import core.model.SortableModel

enum SortableModelError:

	case LessThanTwoElements
	case ToManyElements(
		amount: Int,
		maxAllowedElements: Int = SortableModel.maxAllowedElements
	)