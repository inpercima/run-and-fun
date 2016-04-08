module.exports = function(grunt) {
  'use strict';
  require('load-grunt-tasks')(grunt);

  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),

    build_app_js: 'build/resources/main/public/js/app.js',

    build_app_min_js: 'build/resources/main/public/js/app.min.js',

    build_dir_css: 'build/resources/main/public/css',

    build_dir_fonts: 'build/resources/main/public/fonts',

    build_dir_js: 'build/resources/main/public/js',

    gruntfile_js: 'Gruntfile.js',

    src_app_js: 'src/main/resources/public/js/app.js',

    src_files_js: 'src/main/resources/public/js/**/*.js',

    jshint: {
      options: {
        boss: true,
        browser: true,
        curly: true,
        esnext: true,
        globals: {
          angular: true,
          '_': true,
        },
        immed: true,
        newcap: true,
        noarg: true,
        node: true,
        sub: true,
        undef: true,
        unused: true,
        'quotmark': 'single'
      },
      dist: {
        src: [ '<%= gruntfile_js %>', '<%= src_files_js %>' ]
      }
    },
    jscs: {
      src: [ '<%= gruntfile_js %>', '<%= src_files_js %>' ],
      options: {
        config: '.jscs.json'
      }
    },
    concat: {
      options: {
        separator: grunt.util.linefeed
      },
      // first build without app.js
      core: {
        src: [ '<%= src_files_js %>', '!<%= src_app_js %>' ],
        dest: '<%= build_app_js %>'
      },
      // second build with prepended app.js
      dist: {
        src: [ '<%= src_app_js %>', '<%= concat.core.dest %>' ],
        dest: '<%= concat.core.dest %>'
      }
    },
    uglify: {
      options: {
        // the banner is inserted at the top of the output
        banner: '/*! <%= pkg.name %> <%= grunt.template.today("dd-mm-yyyy") %> */\n'
      },
      dist: {
        src: '<%= concat.dist.dest %>',
        dest: '<%= build_app_min_js %>'
      }
    },
    watch: {
      files: [ '<%= gruntfile_js %>', '<%= src_files_js %>' ],
      tasks: [ 'jshint', 'jscs', 'concat', 'uglify', 'copy', 'clean' ]
    },
    clean: {
      dist: {
        src: '<%= build_app_js %>'
      }
    },
    copy: {
      dist: {
        files: [
          // js
          { src: [ 'node_modules/angular/angular.min.js' ], dest: '<%= build_dir_js %>/angular.min.js' },
          { src: [ 'node_modules/angular-chart.js/dist/angular-chart.min.js' ], dest: '<%= build_dir_js %>/angular-chart.min.js' },
          { src: [ 'node_modules/angular-route/angular-route.min.js' ], dest: '<%= build_dir_js %>/angular-route.min.js' },
          { src: [ 'node_modules/chart.js/Chart.min.js'], dest: '<%= build_dir_js %>/Chart.min.js' },
          { src: [ 'node_modules/lodash/lodash.min.js'], dest: '<%= build_dir_js %>/lodash.min.js' },
          // css
          { src: [ 'node_modules/angular-chart.js/dist/angular-chart.min.css'], dest: '<%= build_dir_css %>/angular-chart.min.css' },
          { src: [ 'node_modules/bootstrap/dist/css/bootstrap.min.css'], dest: '<%= build_dir_css %>/bootstrap.min.css' },
          // fonts
          { expand: true, cwd: 'node_modules/bootstrap/dist/fonts/', src: [ '**'], dest: '<%= build_dir_fonts %>' },
        ]
      }
    }
  });

  grunt.registerTask('build', [ 'jshint', 'jscs', 'concat', 'uglify', 'copy', 'clean' ]);
  grunt.registerTask('default', [ 'jshint', 'jscs' ]);
};
