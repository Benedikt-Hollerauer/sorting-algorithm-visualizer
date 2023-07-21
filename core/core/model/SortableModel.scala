package core.model

import core.model.ValueWithIndexModel
import core.model.ValueWithIndexModel.ordering
import error.modelError.SortableModelError

case class SortableModel private(
    valuesWithIndices: NonEmptyListModel[ValueWithIndexModel]
):
    def getSorted(ordering: OrderModel): SortableModel =
        val sorted = valuesWithIndices.list.sortWith: (x, y) =>
            ordering.getOrdering(x.value, y.value)
        SortableModel.from(
            NonEmptyListModel.fromUnsafe(
                sorted
            )
        ).toOption.get

object SortableModel:

    def from(mayBeValuesWithIndices: NonEmptyListModel[ValueWithIndexModel]): Either[SortableModelError, SortableModel] =
        if(mayBeValuesWithIndices.list.length > 500) Left(SortableModelError.ToManyElements(mayBeValuesWithIndices.list.length)) //TODO think about removing NonEmptyListModel and adding the rules of NonEmptyListModel to SortableModel
        else Right(
            SortableModel(
                valuesWithIndices = mayBeValuesWithIndices
            )
        )