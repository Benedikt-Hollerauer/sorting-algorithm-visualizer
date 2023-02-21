package core.value

import error.valueError.SortableValueError

case class SortableValue private(list: List[Int])

object SortableValue:

    def from(mayBeList: List[Int]): Either[SortableValueError, SortableValue] =
        if(mayBeList.isEmpty) Left(SortableValueError.EmptyList(mayBeList))
        else if(mayBeList.length == 1) Left(SortableValueError.ToFewElements(mayBeList))
        else Right(SortableValue(mayBeList))