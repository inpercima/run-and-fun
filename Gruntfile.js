/* globals module, require */

const loadGruntTasks = require('load-grunt-tasks');
module.exports = function(grunt) {
  'use strict';

  loadGruntTasks(grunt);

  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),

    buildAppJs: 'build/resources/main/public/js/app.js',

    buildAppMinJs: 'build/resources/main/public/js/app.min.js',

    buildDirCss: 'build/resources/main/public/css',

    buildDirFonts: 'build/resources/main/public/fonts',

    buildDirJs: 'build/resources/main/public/js',
    
    buildFilesJs: 'build/resources/main/public/js/*.js*',

    gruntfileJs: 'Gruntfile.js',

    srcAppJs: 'src/main/resources/public/js/app.js',

    srcConfigJson: 'src/main/resources/public/js/config.json',

    srcFilesJs: 'src/main/resources/public/js/**/*.js',

    babel: {
      options: {
        presets: ['es2015'],
      },
      dist: {
        files: {
          '<%= concat.core.dest %>': '<%= concat.core.dest %>',
        },
      },
    },
    eslint: {
      options: {
        configFile: '.eslintrc.json',
      },
      target: ['<%= gruntfileJs %>', '<%= srcFilesJs %>'],
    },
    concat: {
      options: {
        separator: grunt.util.linefeed,
      },
      // first build without app.js
      core: {
        src: ['<%= srcFilesJs %>', '!<%= srcAppJs %>', '!<%= srcConfigJson %>'],
        dest: '<%= buildAppJs %>',
      },
      // second build with prepended app.js
      dist: {
        src: ['<%= srcAppJs %>', '<%= buildAppJs %>', '!<%= srcConfigJson %>'],
        dest: '<%= buildAppJs %>',
      },
    },
    uglify: {
      options: {
        // the banner is inserted at the top of the output
        banner: '/*! <%= pkg.name %> <%= grunt.template.today("dd-mm-yyyy") %> */\n',
      },
      dist: {
        src: '<%= buildAppJs %>',
        dest: '<%= buildAppMinJs %>',
      },
    },
    watch: {
      files: ['<%= gruntfileJs %>', '<%= srcFilesJs %>'],
      tasks: ['build'],
    },
    clean: {
      build: {
        src: '<%= buildFilesJs %>',
      },
      dist: {
        src: '<%= buildAppJs %>',
      },
    },
    copy: {
      dist: {
        files: [
          // js
          { src: ['node_modules/angular/angular.min.js'], dest: '<%= buildDirJs %>/angular.min.js' },
          {
            src: ['node_modules/angular-chart.js/dist/angular-chart.min.js'],
            dest: '<%= buildDirJs %>/angular-chart.min.js',
          },
          {
            src: ['node_modules/angular-route/angular-route.min.js'],
            dest: '<%= buildDirJs %>/angular-route.min.js',
          },
          {
            src: ['node_modules/angular-ui-bootstrap/dist/ui-bootstrap-tpls.js'],
            dest: '<%= buildDirJs %>/angular-ui-bootstrap-tpls.js',
          },
          { src: ['node_modules/chart.js/dist/Chart.min.js'], dest: '<%= buildDirJs %>/Chart.min.js' },
          { src: ['node_modules/lodash/lodash.min.js'], dest: '<%= buildDirJs %>/lodash.min.js' },
          // json
          { src: ['src/main/resources/public/js/config.json'], dest: '<%= buildDirJs %>/config.json' },
          // css
          {
            src: ['node_modules/bootstrap/dist/css/bootstrap.min.css'],
            dest: '<%= buildDirCss %>/bootstrap.min.css',
          },
          // fonts
          { expand: true, cwd: 'node_modules/bootstrap/dist/fonts/', src: ['**'], dest: '<%= buildDirFonts %>' },
        ],
      },
    },
  });

  grunt.registerTask('build', ['clean:build', 'eslint', 'concat', 'babel', 'uglify', 'copy', 'clean:dist']);
  grunt.registerTask('default', ['eslint']);
};
