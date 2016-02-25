// server.js

// BASE SETUP
// =============================================================================

// call the packages we need
var express    		= require('express');        // call express
var app        		= express();                 
var Category      	= require('../models/category');
var async		= require('async');
var config              = require('../../config'); // get our config file
var changeCase 		= require('change-case')

// ROUTES FOR OUR API
// =============================================================================
	var router = express.Router();              // get an instance of the express Router
	
	// middleware to use for all requests
	router.use(function(req, res, next) {
	    	// do logging
		
	    	console.log('Inside Middleware');
		next();
		
	});

	// test route to make sure everything is working (accessed at GET http://localhost:9091/blog)
	router.get('/', function(req, res) {
	    res.json({ message: 'Sayit Up and Running' });   
	});


    	// get all the authors (accessed at GET http://localhost/blog/author)
    	router.get('/service/cat',function(req, res) {
		//console.log("Inside author get all");
        	Category.find(function(err, rows) {
            		if (err)
                	res.send(err);
            		res.json(rows);
        	});
    	});

        // get the required all the authors (accessed at GET http://localhost/blog/author/:authorid)
        router.get('/service/cat/:category',function(req, res) {
                //console.log("Inside author get single");
                Category.find({'category': changeCase.lowerCase(req.params.category)},{'feedback':1,'_id':0},function(err, rows) {
                if (err)
                res.send(err);
		//console.log(rows.length);
		if(rows.length > 0){
		//console.log(res.json(JSON.parse(rows)));
		res.json(rows[0].feedback);
		}	
		else{
		res.json(config.defaultfeedback);
		}
                });
        });

        // UPDATE feedback for a particular Category - PUT API http://host/sayit/service/cat/:category
        router.put('/service/cat/:category',function(req, res) {
                //console.log("Inside Category Update");
		//console.log(req.body.feedback);
		//console.log(req.params.category);
                Category.findOneAndUpdate({'category': changeCase.lowerCase(req.params.category)},{'feedback':req.body.feedback},function(err, rows) {
                if (err)
                	res.send(err);
		res.json({ message: 'Category Updated Succesfully!!' });
                });
        });


        // POST the category (accessed at POST http://host/sayit/service/cat/)
        router.post('/service/cat/',function(req, res) {
                var category      = new Category();      			// create a new instance of the Category model
                category.category = changeCase.lowerCase(req.body.category);    // set the name (comes from the request)
                category.feedback = req.body.feedback;

                category.save(function(err) {
                	if (err)
                        res.send(err);
			else{
			res.json({ message: 'Category Created!!' });
			}	
                        
                  });
        });

module.exports = router;
