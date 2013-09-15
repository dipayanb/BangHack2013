window.powerdial ||= {}
window.powerdial.ErrorView = Backbone.View.extend
  template: JST['src/templates/errorview.hbs']

  render: ->
    @$el.html @template()
    @
