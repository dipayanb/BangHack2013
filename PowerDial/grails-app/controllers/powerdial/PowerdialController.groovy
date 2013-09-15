package powerdial

import com.hackback.service.HackbackService
import grails.converters.JSON
import groovy.json.JsonBuilder
import org.codehaus.groovy.grails.web.json.JSONObject

class PowerdialController {

    HackbackService service = new HackbackService()

    def getData() {
        def query       = params.q
        def lang        = params.lang
        def place       = params.p
        def latitude    = params.lat
        def longitude   = params.lng

        def output
        def location = null
        if (!(longitude == null || latitude == null))
            location = "${longitude},${latitude}"
        if(place != null)
        {
            output = service.search('9731099002', query, 'bangalore', place, location)
        } else {
            output = service.substring_search(query, 'bangalore', place, location, lang)
        }
        //render new JsonBuilder(output)
        render new JSONObject(output)
    }

    def getDataBackup() {
        //Thread.sleep(2000)
        def result = [ lat: "80.001",
                        lng: "80.002",
                        results:
                                [
                                        [
                                                "justdial_id":"044P7300885",
                                                "companyname":"Sathyam Cinemas",
                                                "address":"No 8, Thiru VI Ka Road, Near Whites Road, Royapettah, Chennai- 600014",
                                                "city":"Chennai",
                                                "pincode":"600014",
                                                "landline":"+(91)-44-42244224,43920301",
                                                "mobile":"",
                                                "email":"help@spicinemas.com",
                                                "website":"www.spicinemas.in",
                                                "avg_rating":4.400000095367432,
                                                "total_ratings":18085,
                                                "location":[
                                                        "lng":80.258105,
                                                        "lat":13.055569
                                                ]
                                        ],
                                        [
                                                "justdial_id":"044PXX44.XX44.120621143902.H6Z9",
                                                "companyname":"S2 Cinemas",
                                                "address":"No 114 Spectrum Mall, Paper Mills Road, Perambur, Chennai- 600011",
                                                "city":"Chennai",
                                                "pincode":"600011",
                                                "landline":"+(91)-44-42244224,42244299",
                                                "mobile":"",
                                                "email":"help@spicinemas.in",
                                                "website":"www.spicinemas.in",
                                                "avg_rating":4.199999809265137,
                                                "total_ratings":2225,
                                                "location":[
                                                        "lng":80.2362209,
                                                        "lat":13.1123371
                                                ]
                                        ],
                                        [
                                                "justdial_id":"044PXX44.XX44.130131114052.R5K9",
                                                "companyname":"Luxe Cinemas (Opening Shortly)",
                                                "address":"No 142 Phoenix Marketcity 2nd Floor, Velachery Main Road, Velacheri, Chennai- 600042",
                                                "city":"Chennai",
                                                "pincode":"600042",
                                                "landline":"+(91)-44-42244224",
                                                "mobile":"",
                                                "email":"",
                                                "website":"www.spicinemas.in",
                                                "avg_rating":4.199999809265137,
                                                "total_ratings":35,
                                                "location":[
                                                        "lng":80.214351,
                                                        "lat":12.992783
                                                ]
                                        ],
                                        [
                                                "justdial_id":"044PXX44.XX44.120517112905.K4D5",
                                                "companyname":"Pix 5D Cinema",
                                                "address":"No 313 3rd Floor Express Avenue, , Royapettah, Chennai- 600014",
                                                "city":"Chennai",
                                                "pincode":"600014",
                                                "landline":"+(91)-44-28464538",
                                                "mobile":"+(91)-9626262616,9962661000",
                                                "email":"",
                                                "website":"",
                                                "avg_rating":4.199999809265137,
                                                "total_ratings":34,
                                                "location":[
                                                        "lng":80.264062,
                                                        "lat":13.058691
                                                ]
                                        ],
                                        [
                                                "justdial_id":"044PXX44.XX44.120830123655.D3H9",
                                                "companyname":"S2 Theyagaraja Cinemas",
                                                "address":"No 60, L B Road, Thiruvanmiyur, Chennai- 600041",
                                                "city":"Chennai",
                                                "pincode":"600041",
                                                "landline":"+(91)-44-42244224",
                                                "mobile":"",
                                                "email":"",
                                                "website":"www.spicinemas.in",
                                                "avg_rating":4.099999904632568,
                                                "total_ratings":1594,
                                                "location":[
                                                        "lng":80.256357118007,
                                                        "lat":12.989549126767
                                                ]
                                        ],
                                        [
                                                "justdial_id":"044P1232451856M7F5E9",
                                                "companyname":"Varadharaja Cinema Hall",
                                                "address":"No 190/2b, 1st Main Road, Near Police Station & Mit College, Chitlapakkam, Chennai- 600064",
                                                "city":"Chennai",
                                                "pincode":"600064",
                                                "landline":"+(91)-44-22231290",
                                                "mobile":"",
                                                "email":"varadharajatheatre@gmail.com",
                                                "website":"www.varadharajatheatres.com",
                                                "avg_rating":4.099999904632568,
                                                "total_ratings":849,
                                                "location":[
                                                        "lng":80.148425,
                                                        "lat":12.935303
                                                ]
                                        ],
                                        [
                                                "justdial_id":"044PXX44.XX44.110927151834.Q5H4",
                                                "companyname":"K K Cinema Hall",
                                                "address":", Katoor Rd, Near Railway Gate, Minjur, Chennai- 601203",
                                                "city":"Chennai",
                                                "pincode":"601203",
                                                "landline":"+(91)-44-27933001",
                                                "mobile":"",
                                                "email":"kkcinemas@yahoo.com",
                                                "website":"www.kkcinemas.com",
                                                "avg_rating":4.099999904632568,
                                                "total_ratings":34,
                                                "location":[
                                                        "lng":80.264475,
                                                        "lat":13.276537
                                                ]
                                        ],
                                        [
                                                "justdial_id":"044P3009876",
                                                "companyname":"Mahalakshmi Cinema Hall",
                                                "address":"No 46, Strahans Road, Opposite Farook Hotel, Pattalam, Chennai- 600012",
                                                "city":"Chennai",
                                                "pincode":"600012",
                                                "landline":"+(91)-44-26620637",
                                                "mobile":"+(91)-9840077450",
                                                "email":"",
                                                "website":"",
                                                "avg_rating":4.099999904632568,
                                                "total_ratings":12,
                                                "location":[
                                                        "lng":80.257144,
                                                        "lat":13.097508
                                                ]
                                        ],
                                        [
                                                "justdial_id":"044PXX44.XX44.110120173122.L4K7",
                                                "companyname":"Sangam Cinemas",
                                                "address":"No 870, Poonamallee High Road, Opposite Government Park, Kilpauk, Chennai- 600010",
                                                "city":"Chennai",
                                                "pincode":"600010",
                                                "landline":"+(91)-44-42082233",
                                                "mobile":"",
                                                "email":"bulkbookings@sangamcinemas.com,advertising@sangamcinemas.com,support@ticketnew.com",
                                                "website":"www.sangamcinemas.com",
                                                "avg_rating":4,
                                                "total_ratings":3442,
                                                "location":[
                                                        "lng":80.249328,
                                                        "lat":13.079089
                                                ]
                                        ],
                                        [
                                                "justdial_id":"044PF009897",
                                                "companyname":"Kamala Cinemas",
                                                "address":"No 156, Arcot Road, Opposite Vijaya Hospital, Vadapalani, Chennai- 600026",
                                                "city":"Chennai",
                                                "pincode":"600026",
                                                "landline":"+(91)-44-23652000,23652008,42454434,42454433",
                                                "mobile":"",
                                                "email":"info@kamalacinemas.com,kcinemas@gmail.com,support@ticketnew.com",
                                                "website":"www.kamalacinemas.com",
                                                "avg_rating":4,
                                                "total_ratings":3440,
                                                "location":[
                                                        "lng":80.210143,
                                                        "lat":13.049158
                                                ]
                                        ]
                                ]
        ]
        //response.status = 404;
        render result as JSON
    }
}
