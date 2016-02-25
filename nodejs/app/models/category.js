var mongoose	= require('mongoose');
var Schema	= mongoose.Schema;

SALT_WORK_FACTOR = 10;

var CategorySchema = new Schema({
        category : {type : String,trim: true, required: true,unique: true}, //Authors Date of Birth
	feedback : {type : String,trim: true, required:true} //Temporary Timestamp for creation of token	
});

module.exports = mongoose.model('Category', CategorySchema);
