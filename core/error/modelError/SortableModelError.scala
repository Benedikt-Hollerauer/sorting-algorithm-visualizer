package error.modelError

enum SortableModelError:

    case ToManyElements(amount: Int)
    case ToFewElements(amount: Int)