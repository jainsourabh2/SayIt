// server.js

// BASE SETUP
// =============================================================================

// call the packages we need
var express    		= require('express');        // call express
var app        		= express();                 
var Submission      	= require('../models/submission');
var config              = require('../../config'); // get our config file

// ROUTES FOR OUR API
// =============================================================================
	var router = express.Router();              // get an instance of the express Router
	
	// middleware to use for all requests
	router.use(function(req, res, next) {
	    	// do logging
		
	    //	console.log('Inside Middleware');
		next();
		
	});

        // get the required all the authors (accessed at GET http://localhost/blog/author/:authorid)
        router.post('/service/submission/',function(req, res) {
		
		//var config              = require('../../config'); // get our config file
                var submission      		= new Submission();      // create a new instance of the Author model
                submission.category 		= req.body.category;  // set the name (comes from the request)
                submission.feedback		= req.body.feedback;
		submission.devicemetrics	= req.body.devicemetrics;

                submission.save(function(err) {
                	if (err)
                        res.send(err);
			else{
			res.send(config.defaultresponse);
			}	
                        
                  });
        });

module.exports = router;
