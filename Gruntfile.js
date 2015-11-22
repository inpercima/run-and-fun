module.exports = function(grunt) {
  'use strict';
  require('load-grunt-tasks')(grunt);

  grunt.initConfig({
    pkg : grunt.file.readJSON('package.json'),

    build_app_js: 'build/resources/main/public/js/app.js',

    build_app_min_js: 'build/resources/main/public/js/app.min.js',

    build_dir_css: 'build/resources/main/public/css',

    build_dir_fonts: 'build/resources/main/public/fonts',

    build_dir_js: 'build/resources/main/public/js',

    gruntfile_js: 'Gruntfile.js',

    src_app_js: 'src/main/resources/public/js/app.js',

    src_files_js: 'src/main/resources/public/js/**/*.js',

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
      dist : {
        src : [ '<%= gruntfile_js %>', '<%= src_files_js %>' ]
      }
    },
    concat : {
      options : {
        separator : grunt.util.linefeed
      },
      // first build without app.js
      core : {
        src : [ '<%= src_files_js %>', '!<%= src_app_js %>' ],
        dest : '<%= build_app_js %>'
      },
      // second build with prepended app.js
      dist : {
        src : [ '<%= src_app_js %>', '<%= concat.core.dest %>' ],
        dest : '<%= concat.core.dest %>'
      }
    },
    uglify : {
      options : {
        // the banner is inserted at the top of the output
        banner : '/*! <%= pkg.name %> <%= grunt.template.today("dd-mm-yyyy") %> */\n'
      },
      dist: {
        src: '<%= concat.dist.dest %>',
        dest: '<%= build_app_min_js %>'
      }
    },
    watch: {
      files: [ '<%= gruntfile_js %>', '<%= src_files_js %>' ],
      tasks: [ 'jshint', 'concat', 'uglify', 'clean' ]
    },
    clean: {
      dist: {
        src: '<%= build_app_js %>'
      }
    },
    bowercopy: {
      dist: {
        files: {
          '<%= build_dir_css %>/angular-chart.min.css': 'angular-chart.js/dist/angular-chart.min.css',
          '<%= build_dir_css %>/bootstrap.min.css': 'bootstrap/dist/css/bootstrap.min.css',
          '<%= build_dir_fonts %>': 'bootstrap/dist/fonts',
          '<%= build_dir_js %>/angular.min.js': 'angularjs/angular.min.js',
          '<%= build_dir_js %>/angular-chart.min.js': 'angular-chart.js/dist/angular-chart.min.js',
          '<%= build_dir_js %>/angular-route.min.js': 'angular-route/angular-route.min.js',
          '<%= build_dir_js %>/Chart.min.js': 'Chart.js/Chart.min.js',
          '<%= build_dir_js %>/lodash.min.js': 'lodash/lodash.min.js'
        }
      }
    }
  });

  grunt.registerTask('build', [ 'jshint', 'concat', 'uglify', 'bowercopy', 'clean' ]);
  grunt.registerTask('default', [ 'jshint' ]);
};
