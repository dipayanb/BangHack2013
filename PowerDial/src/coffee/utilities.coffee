window.powerdial ||= {}

class window.powerdial.Utilities
  processResponseData: (data) ->
    processed = {}
    lat = data.lat
    lng = data.lng
    if lat and lng
      # set latitude and longitude in cookie
      @setToCookie 'lat', lat
      @setToCookie 'lng', lng
      processed.lat = lat
      processed.lng = lng

    processed.results =  data.results
    _.each processed.results, (entry) =>
      entry.phone = @mixPhoneNumbers entry.landline, entry.mobile
    processed

  setToCookie: (key, value) ->
    $.cookie key, value,
      expires: 30
      #domain: 'powerdial.com'

  getCookie: (key) ->
    $.cookie key

  getLatLngFromCookie: ->
    lat: @getCookie('lat'), lng: @getCookie('lng')

  mixPhoneNumbers: (landline, mobile) ->
    phone = []
    if !_.isEmpty(landline)
      landlines = landline.split ','
      phone.push landlines[0]
      if landlines.length > 1
        @modifyPhones landlines, phone
    if !_.isEmpty(mobile)
      mobiles = mobile.split ','
      phone.push mobiles[0]
      if mobiles.length > 1
        @modifyPhones mobiles, phone
    phone

  modifyPhones: (entry, phone) ->
    lastIndex = entry[0].lastIndexOf '-'
    stdprefix = entry[0].substr(0, lastIndex + 1)
    rest = _.rest entry
    _.each rest, (entry) ->
      phone.push "#{stdprefix}#{entry}"