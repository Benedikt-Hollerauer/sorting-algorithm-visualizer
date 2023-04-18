package error.modelError

enum SortableModelError:

    case EmptyList
    case ToFewElements(amount: Int)
    case ToManyElements(amount: Int)