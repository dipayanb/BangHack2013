window.powerdial ||= {}
window.powerdial.HomeView = Backbone.View.extend
  template: JST['src/templates/headerview.hbs']

  events:
    'keyup #pd-search': 'search'

  initialize: ->
    window.powerdial.vent.on 'show:spinner', @showSpinner

  render: ->
    @$el.html @template()

  search: (event)->
    event.preventDefault()
    val = $(event.currentTarget).val().toLowerCase()
    return if val.length <= 3 # dont do anything if the number of characters is less than 3

    c = event.which || event.keyCode;
    if((c > 31 && c < 65) || (c > 90 && c < 97) || (c > 122 && c != 127))
      return false;
    isFinalSearch = false
    if event.which is 13
      isFinalSearch = true

    lang = $('#pd-lang').val()
    data = @parseData(val, lang)
    return if data  == null || _.isEmpty(data.q)
    if !_.isEmpty(data.p) and isFinalSearch is false
      return
    window.powerdial.vent.trigger "fetch:data", data

  parseData: (value, lang) ->
    matches = value.split('p:')
    data = {}
    if matches isnt null
      data.q = matches[0].trim()
      data.lang = lang
      if matches.length > 1 and matches[1].length > 0
        data.p = matches[1].trim()
      data
    else
      return null


  showSpinner: (flag) ->
    if flag is true
      $('#spinner').show()
    else
      $('#spinner').hide()
