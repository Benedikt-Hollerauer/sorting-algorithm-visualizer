package error.modelError

enum SortableModelError:

	case LessThanTwoElements
	case ToManyElements(amount: Int)