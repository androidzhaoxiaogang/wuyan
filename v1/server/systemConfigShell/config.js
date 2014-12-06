var mongo = db.getMongo();

var mydb = mongo.getDB("tongjuba");
var table = mydb.user;

 configDBUsername = "tongjuba";
 configDBPassword = "wecash";

var nginxIP = "121.199.65.171";
var nginxPort = 80;

var tomcat = "121.199.65.171";
var tomcatPort = 8080;

var mongodbIPs = "121.199.65.171:27017,121.199.65.210:27017";

mydb.addUser(configDBUsername,configDBPassword);

// detail server config
var publicConfig = {
	apiServerIP: nginxIP,
	apiServerPort: 80,
	timeoutHttp: 30,

	mongodbReplicaSet: mongodbIPs,
	mongodbUsername: "xkk",
	mongodbPassword: "wecash",
	mongodbWriteMode: "safe",
	mongodbReadMode: "master",
	logLevel: "debug"
};
var adminConfig = {
	username : "engourdi",
  	phone : "13693463991",
  	password : "96E79218965EB72C92A549DD5A330112",
  	sex : 1,
  	roleType : 0,
  	name : "franklin.xkk",
  	login : 0
};

var serverConfig = {
};

var apiConfig = {

};
var count = table.count({});
table.insert(adminConfig);

//db index
mydb.user.ensureIndex({username:1,location:"2d",price:1,habit:1,degree:1, occupation:1});
mydb.tag.ensureIndex({num:1,type:1});