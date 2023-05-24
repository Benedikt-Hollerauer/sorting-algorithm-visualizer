import test.modelTest.{IndexModel_Test, SortableModel_Test, SortedModel_Test, ValueWithIndexModel_Test}

class Model_TestImpl extends TestUtil:

    implementTest(SortableModel_Test)
    implementTest(SortedModel_Test)
    implementTest(ValueWithIndexModel_Test)
    implementTest(IndexModel_Test)