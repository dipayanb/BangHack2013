package powerdial

import grails.converters.JSON

class PowerdialController {

    def getData() {
        Thread.sleep(2000)
        def result = [ lat: "80.001",
                        lng: "80.002",
                        results: [[ name: 'Hydrabadi Biriyani house',
                       address: 'Koramangala',
                       phone: ['9731099002']
                     ],
                    [ name: 'Kobe\'s Sizzlers',
                            address: 'Koramangala',
                            phone: ['9731099002']
                    ]
        ]]
        //response.status = 404;
        render result as JSON
    }
}
