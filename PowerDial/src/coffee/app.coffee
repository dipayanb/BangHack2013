window.powerdial ||= {}
window.powerdial.AppRouter = Backbone.Router.extend
  routes:
    "" :            "start"

  start: ->
    headerview = new window.powerdial.HomeView el: '#header'
    headerview.render()

window.powerdial.utilities = new window.powerdial.Utilities()

window.powerdial.vent = {}
_.extend(window.powerdial.vent, Backbone.Events)

window.powerdial.vent.on 'result:fetched', (object) ->
  collection = new window.powerdial.ResultCollection()
  results = new window.powerdial.ResultCollectionView collection: collection
  $('#results').html results.render().el
  collection.reset object


window.powerdial.vent.on 'fetch:data', (object) ->
  loc = window.powerdial.utilities.getLatLngFromCookie()
  if loc and loc.lat and loc.lng
    object.lat = loc.lat
    object.lng = loc.lng
  req = $.ajax
          url: 'pd'
          data: object
          beforeSend: (xhr, object) ->
            window.powerdial.vent.trigger 'show:spinner', true
  req.done (data)->
    window.powerdial.vent.trigger 'show:spinner', false
    processedData = window.powerdial.utilities.processResponseData(data)
    window.powerdial.vent.trigger 'result:fetched', processedData.results
  req.error ->
    window.powerdial.vent.trigger 'show:spinner', false
    errorview = new window.powerdial.ErrorView()
    $('#results').html errorview.render().el




app_router = new window.powerdial.AppRouter();
Backbone.history.start pushState: true
app_router.navigate '', trigger: true