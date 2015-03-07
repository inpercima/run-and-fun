module.exports = function(grunt) {
  'use strict';
  grunt.initConfig({
    pkg : grunt.file.readJSON('package.json'),

    src_main_js : [ 'src/main/resources/public/js/**/*.js' ],

    build_dir_js : [ 'build/classes/public/js' ],

    jshint : {
      options : {
        boss : true,
        browser : true,
        curly : true,
        esnext : true,
        globals : {
          angular : true,
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
        src : [ '<%= src_main_js %>' ]
      }
    },
    concat : {
      options : {
        // define a string to put between each file in the concatenated output
        separator : ';'
      },
      dist : {
        src : [ '<%= src_main_js %>' ],
        dest : '<%= build_dir_js %>/app.js'
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
      lib_test : {
        files : '<%= src_main_js %>',
        tasks : [ 'jshint:main_js' ]
      }
    }
  });

  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-watch');

  grunt.registerTask('build', [ 'jshint', 'concat', 'uglify' ]);
  grunt.registerTask('default', [ 'jshint' ]);
};
