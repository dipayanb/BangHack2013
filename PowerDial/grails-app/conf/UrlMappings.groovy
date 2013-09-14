class UrlMappings {

	static mappings = {
        "/pd/"(controller: "powerdial", parseRequest: true) {
            action = [GET: "getData", PUT: "save"]
        }

        /*
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		} */

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
