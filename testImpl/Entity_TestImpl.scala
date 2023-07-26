import test.entityTest.{BubbleSortEntity_Test, InsertionSortEntity_Test, VisualizeEntity_BubbleSort_Test, VisualizeEntity_InsertionSort_Test, VisualizeEntity_Test}

class Entity_TestImpl extends TestUtil:

	Set(
		BubbleSortEntity_Test,
		VisualizeEntity_Test,
		VisualizeEntity_BubbleSort_Test,
		VisualizeEntity_InsertionSort_Test,
		InsertionSortEntity_Test
	).foreach(implementTest)