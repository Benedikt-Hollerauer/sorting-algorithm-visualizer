package core.model

import core.model.ValueWithIndexModel
import error.modelError.SortableModelError

case class SortableModel private(
    valuesWithIndices: List[ValueWithIndexModel]
)

object SortableModel: // TODO mayBeList should be a List[ValueWithIndexModel[ValueWithIndexModel[ValueWithIndexModel[ValueWithIndexModel]]]]

    def from(mayBeList: List[ValueWithIndexModel]): Either[SortableModelError, SortableModel] =
        if(mayBeList.isEmpty) Left(SortableModelError.EmptyList)
        else if(mayBeList.length == 1) Left(SortableModelError.ToFewElements(mayBeList.length))
        else if(mayBeList.length > 500) Left(SortableModelError.ToManyElements(mayBeList.length))
        else Right(
            SortableModel(
                valuesWithIndices = mayBeList
            )
        )