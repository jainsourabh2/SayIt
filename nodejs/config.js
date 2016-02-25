var RandomColor = require('just.randomcolor');

var options = {
	r: [10, 90], 
	g: [100,200],
	a: [0.3, 0.7]
},

color = new RandomColor(options);

module.exports = {
    	'database': 'mongodb://localhost/sayit',
	'port': 9092,
	'defaultfeedback': '[{\"f\":\"1\",\"v\":\"Discount\"},{\"f\":\"2\",\"v\":\"Ambience\"},{\"f\":\"3\",\"v\":\"Price\"},{\"f\":\"4\",\"v\":\"Food\"}]',
	'defaultresponse':'<!DOCTYPE html><html lang="en-us"><head><style>.coupan {float: left; margin: 5px; padding: 15px; width: 250px; height: 300px; border: 1px solid black; background-color: ' + color.toHex().toCSS() + ';}.text-shadow { text-shadow: 1px 1px 1px #444; letter-spacing: 2px;}.text-white { color: #fff !important;}.text-white1 { color: #fff !important; font-size: 20px; margin: -5px 1px;}.text1 { color: #000fff; font-size: 14px; text-align: center; margin: -5px 1px;}.code { float: right; margin: -5px 40px; letter-spacing: 1px; padding: 10px; width: 100px; height: 20px; border: 1px solid black; background-color: #ffffff;}</style></head><body> <div class="coupan"> <h2>eBay</h2> <p class="text-white text-shadow">Shop & Get Free Prepaid Mobile Recharge Worth Rs. 200</p><br/><div><span class="text-white1">Code: </span><p class="code">EBY200</p></div><br/><div><p><span class="text1">Expires on 21 Dec</span></p></div> </div></body></html>'
	};
