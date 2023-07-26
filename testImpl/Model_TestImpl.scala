import test.modelTest.*

class Model_TestImpl extends TestUtil:
    
    Set(
        SortableModel_Test,
        IndexModel_Test
    ).foreach(implementTest)