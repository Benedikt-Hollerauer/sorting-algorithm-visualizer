package core.model

import error.modelError.SortedModelError

case class SortableModel private(
    list: List[Int],
    changedIndices: List[Int]
)

object SortableModel:

    def from(
        mayBeList: List[Int],
        mayBeChangedIndices: List[Int]
    ): Either[SortedModelError, SortableModel] =
        if(mayBeList.isEmpty) Left(SortedModelError.EmptyList)
        else if(mayBeList.length == 1) Left(SortedModelError.ToFewElements(mayBeList.length))
        else if(mayBeList.length >= 500) Left(SortedModelError.ToManyElements(mayBeList.length))
        else if(mayBeChangedIndices.length < 1) Left(SortedModelError.ToFewChangedIndices(mayBeChangedIndices))
        else Right(SortableModel(
            mayBeList,
            mayBeChangedIndices
        ))