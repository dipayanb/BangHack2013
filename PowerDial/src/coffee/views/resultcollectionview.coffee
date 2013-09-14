window.powerdial ||= {}
window.powerdial.ResultCollectionView = Backbone.View.extend
  template: JST['src/templates/resultcollectionview.hbs']

  initialize: ->
    @listenTo @collection, 'reset', @addAll

  render: ->
    @$el.html @template()
    @

  addAll: ->
    $('#pd-results').empty()
    @collection.each (model) =>
      @addOne(model)

  addOne: (model) ->
    resultView = new window.powerdial.ResultView model: model
    $('#pd-results').append resultView.render().el
