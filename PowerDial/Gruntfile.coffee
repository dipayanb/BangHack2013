module.exports = (grunt) ->
  grunt.initConfig
    pkg: grunt.file.readJSON 'package.json'

    coffee:
      compile:
        files:
          'target/js/powerdial-custom.js': [ 'src/coffee/models/*.coffee', 'src/coffee/collections/*.coffee',
                                             'src/coffee/views/*.coffee', 'src/coffee/utilities.coffee','src/coffee/app.coffee']

    handlebars:
      compile:
        files:
          'target/js/templates.js': ['src/templates/*.hbs', 'src/templates/*.handlebars']

    concat:
      dist:
        files:
          'target/js/bootstrap.js' : ['bower_components/bootstrap/docs/assets/js/bootstrap.js']
          'web-app/js/powerdial.js': [
                                      'bower_components/jquery/jquery.js',
                                      'target/js/bootstrap.js',
                                      'bower_components/handlebars/handlebars.js',
                                      'bower_components/underscore/underscore.js',
                                      'bower_components/backbone/backbone.js'
                                      'bower_components/select2/select2.js'
                                      'bower_components/jquery.cookie/jquery.cookie.js'
                                      'target/js/templates.js',
                                      'target/js/powerdial-custom.js']
          'web-app/css/powerdial.css': ['bower_components/bootstrap/docs/assets/css/bootstrap.css',
                                        'bower_components/select2/select2.css'
                                        ]


  grunt.loadNpmTasks 'grunt-contrib-coffee'
  grunt.loadNpmTasks 'grunt-contrib-handlebars'
  grunt.loadNpmTasks 'grunt-contrib-concat'
  #grunt.loadNpmTasks 'grunt-contrib-watch'

  grunt.registerTask 'default', ['coffee', 'handlebars', 'concat']
  #grunt.registerTask 'watch', ['watch']