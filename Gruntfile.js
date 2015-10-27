module.exports = function(grunt) {
  'use strict';
  require('load-grunt-tasks')(grunt);

  grunt.initConfig({
    pkg : grunt.file.readJSON('package.json'),

    src_dir_js : [ 'src/main/resources/public/js' ],

    build_dir_js : [ 'build/resources/main/public/js' ],

    jshint : {
      options : {
        boss : true,
        browser : true,
        curly : true,
        esnext : true,
        globals : {
          angular : true,
          '_' : true,
        },
        immed : true,
        newcap : true,
        noarg : true,
        node : true,
        sub : true,
        undef : true,
        unused : true,
        'quotmark' : 'single'
      },
      gruntfile : {
        src : 'Gruntfile.js'
      },
      main_js : {
        src : [ '<%= src_dir_js %>/**/*.js' ]
      }
    },
    concat : {
      options : {
        separator : grunt.util.linefeed
      },
      // first build without app.js
      core : {
        src : [ '<%= src_dir_js %>/**/*.js', '!<%= src_dir_js %>/app.js' ],
        dest : '<%= build_dir_js %>/app.js'
      },
      // second build with prepended app.js
      dist : {
        src : [ '<%= src_dir_js %>/app.js', '<%= concat.core.dest %>' ],
        dest : '<%= concat.core.dest %>'
      }
    },
    uglify : {
      options : {
        // the banner is inserted at the top of the output
        banner : '/*! <%= pkg.name %> <%= grunt.template.today("dd-mm-yyyy") %> */\n'
      },
      dist : {
        files : {
          '<%= build_dir_js %>/app.min.js' : [ '<%= concat.dist.dest %>' ]
        }
      }
    },
    watch : {
      gruntfile : {
        files : '<%= jshint.gruntfile.src %>',
        tasks : [ 'jshint:gruntfile' ]
      },
      main_js : {
        files : '<%= src_dir_js %>/**/*.js',
        tasks : [ 'jshint:main_js', 'concat', 'uglify' ]
      }
    }
  });

  grunt.registerTask('build', [ 'jshint', 'concat', 'uglify' ]);
  grunt.registerTask('default', [ 'jshint' ]);
};
