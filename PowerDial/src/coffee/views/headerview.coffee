window.powerdial ||= {}
window.powerdial.HomeView = Backbone.View.extend
  template: JST['src/templates/headerview.hbs']

  events:
    'keyup #pd-search': 'search'

  initialize: ->
    window.powerdial.vent.on 'show:spinner', @showSpinner




  render: ->
    @$el.html @template()
    #@initSelect2('#pd-search')

  search: (event)->
    event.preventDefault()
    #console.log event.keyCode
    c = event.which || event.keyCode;
    if((c > 31 && c < 65) || (c > 90 && c < 97) || (c > 122 && c != 127))
      return false;
    #return if event.which < 40 and event.which  > 126
    isFinalSearch = false
    if event.which is 13
      isFinalSearch = true
    val = $(event.currentTarget).val()
    data = @parseData(val)
    if !_.isEmpty(data.p) and isFinalSearch is false
      return
    window.powerdial.vent.trigger "fetch:data", data
    #console.log data

  parseData: (value) ->
    matches = value.split('p:')
    data = {}
    if matches isnt null
      data.q = matches[0].trim()
      if matches.length > 1 and matches[1].length > 0
        data.p = matches[1].trim()
    data

  showSpinner: (flag) ->
    if flag is true
      $('#spinner').show()
    else
      $('#spinner').hide()
