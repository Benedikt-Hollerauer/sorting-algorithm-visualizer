package error.modelError

enum SortableModelError:

    case EmptyList
    case ToFewElements(list: List[Int])
    case ToManyElements(amount: Int)