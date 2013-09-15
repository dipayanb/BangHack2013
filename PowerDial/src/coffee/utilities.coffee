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
      console.log "Inside: Lat: #{lat}, Lng: #{lng}"
      processed.lat = lat
      processed.lng = lng

    processed.results =  data.results
    processed

  setToCookie: (key, value) ->
    $.cookie key, value,
      expires: 30
      #domain: 'powerdial.com'

  getCookie: (key) ->
    $.cookie key

  getLatLngFromCookie: ->
    lat: @getCookie('lat'), lng: @getCookie('lng')
