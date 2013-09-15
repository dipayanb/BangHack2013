window.powerdial ||= {}
window.powerdial.ResultView = Backbone.View.extend
  template: JST['src/templates/resultview.hbs']

  tagName: 'li'

  render: ->
    @$el.html @template @model.toJSON()
    @