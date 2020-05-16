package bernardo.bernardinhio.lovooapp.model

class LoginDataModel {

    var roomNumber: String? = null
    var department: String? = null
    var name: String? = null
    var officeLevel: Int? = null
    var type: String? = null
    var id: String? = null
    var lovooFact: LovooFact? = null
    var typ: String? = null

    inner class LovooFact {

        var title: String? = null
        var images: List<String>? = null
        var text: String? = null
    }
}

