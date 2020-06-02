package bernardo.bernardinhio.mycompany.retrofit.model

// some free online converters from json to java
// http://pojo.sodhanalibrary.com/

class BackendDataModel {
    var roomNumber: String? = null
    var department: String? = null
    var name: String? = null
    var officeLevel: Int? = null
    var type: String? = null
    var id: String? = null
    var companyFact: CompanyFact? = null
    var typ: String? = null

    inner class CompanyFact {
        var title: String? = null
        var images: List<String>? = null
        var text: String? = null
    }
}

