//それっぽいレスポンスを返すexpress APP
var express = require('express');
var bodyParser = require('body-parser');
var app = express();

app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(bodyParser.json());

//getはエラーにしておく
app.get('/api', function(req,res){
	res.json({
		"success": false,
		"message": "not post...",
		"estimated_data": {}
	});
});

//postはimage_path必須、さらに10回に1回は失敗
app.post('/api', function(req,res){

	//正常・エラー判定かつ整数戻り値
	var cls = Math.floor(Math.random() * Math.floor(10)) + 1;

	//引数チェック
	var image_path = req.body.image_path || '';
	if(!image_path){
		 console.log("image_pathなし。。。")
		 cls = -1;
	}
	//実数戻り値
	var cfd = Math.floor(Math.random() * 10000) / 10000;

	//レスポンス作成
	if(cls < 10 && cls > 0){
		res.json({
			"success": true,
			"message": "success",
			"estimated_data": {
				"class" : cls ,
				"confidence" : cfd
			}
		});
	} else {
		res.json({
			"success": false,
			"message": "Error:E50012",
			"estimated_data": {}
		});
	}
});

app.listen(3000, function(){});

