// sayit.js

// BASE SETUP
// =============================================================================

// call the packages we need
var express    		= require('express');        // call express
var app        		= express();                 
var bodyParser 		= require('body-parser');
var mongoose   		= require('mongoose');
var config 		= require('./config'); // get our config file
var morgan		= require('morgan');
var fs 			= require('fs');
var FileStreamRotator 	= require('file-stream-rotator')

var logDirectory = '/opt/sayit/logs';

// ensure log directory exists 
fs.existsSync(logDirectory) || fs.mkdirSync(logDirectory);

// create a rotating write stream 
var accessLogStream = FileStreamRotator.getStream({
  filename: logDirectory + '/access-%DATE%.log',
  frequency: 'daily',
  verbose: false,
  date_format: "YYYY-MM-DD"
})

//Connection to MongoDB
mongoose.connect(config.database);

// configure app to use bodyParser()
// this will let us get the data from a POST
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// setup the logger 
app.use(morgan('combined', {stream: accessLogStream}));

var port = process.env.PORT || config.port;        // set our port

// ROUTES FOR OUR API
// =============================================================================
//var router = express.Router();              // get an instance of the express Router

var category	 = require('./app/routes/category');
var submission	 = require('./app/routes/submission');

app.use('/sayit',category);
app.use('/sayit',submission);

// START THE SERVER
// =============================================================================
app.listen(port);
console.log('App listening on port ' + port);
