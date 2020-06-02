package bernardo.bernardinhio.mycompany.datamodel

class MainInfoItemDataModel(
    var name : String = "",
    var department : String = "",
    var type : String = "",
    var roomNumber : String = "",
    var officeLevel : String = "",
    var id : String = "",
    var factTitle : String = "",
    var factText : String = "",
    var factImageList: List<String> = listOf<String>()
)