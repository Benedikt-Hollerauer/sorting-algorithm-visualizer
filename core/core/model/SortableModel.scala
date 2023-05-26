package core.model

import core.model.ValueWithIndexModel
import error.modelError.SortableModelError

case class SortableModel private(
    valuesWithIndices: NonEmptyListModel[ValueWithIndexModel]
)

object SortableModel:

    def from(mayBeList: NonEmptyListModel[ValueWithIndexModel]): Either[SortableModelError, SortableModel] =
        if(mayBeList.list.length > 500) Left(SortableModelError.ToManyElements(mayBeList.list.length))
        else if(mayBeList.list.length == 1) Left(SortableModelError.ToFewElements(mayBeList.list.length))
        else Right(
            SortableModel(
                valuesWithIndices = mayBeList
            )
        )