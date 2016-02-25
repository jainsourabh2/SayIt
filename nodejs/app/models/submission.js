var mongoose	= require('mongoose');
var Schema	= mongoose.Schema;

var SubmissionSchema = new Schema({
        category : {type : String,trim: true, required: true}, //Authors Date of Birth
	feedback : {type : String,trim: true, required:true}, //Temporary Timestamp for creation of token	
	devicemetrics : {type : String,trim: true, required:true}
});

module.exports = mongoose.model('Submission', SubmissionSchema);
