package error.useCaseError

import error.valueError.SortableValueError

enum SortByBubbleSortUseCaseError:
    
    case InputFailure(sortableValueError: SortableValueError)