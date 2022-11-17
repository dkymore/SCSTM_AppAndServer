from flask_httpauth import HTTPTokenAuth 
from authlib.jose import jwt, JoseError
from flask import g , jsonify , request
from db import db_sql
import re
from secert import SecertToken


auth = HTTPTokenAuth()

def generate_token(username):
    header = {'alg': 'HS256'}
    return jwt.encode(header=header,key=SecertToken, payload={'username': username}).decode('utf-8')

@auth.verify_token
def verify_token(token):
    try:
        data = jwt.decode(token,SecertToken)
    except JoseError:
        return False
    return True

@auth.error_handler
def unauthorized():
    return jsonify({'code':50012,'message': 'Token错误/过期','data':''})

def isUser():
    username = request.args.get("username")
    if re.match("^[a-zA-Z0-9_-]{4,16}$",username):
        pass
    else:
        return jsonify({'code':60204,'message': '用户名错误','data':'error'})
    sql = ''' SELECT `username` FROM user WHERE username = %s '''
    db = db_sql()
    res = db.read_sql(sql,value=(username))
    if res:
        return jsonify({'code':20000,'message': '成功','data':'true'})
    return jsonify({'code':60204,'message': '用户未注册','data':'false'})

def login():
    data = request.get_json(silent=True)
    if not data:
        return jsonify({'code':10000,'message': '数据错误','data':''})
     
    username = data['username']
    password = data['password']
    
    if re.match("^[a-zA-Z0-9_-]{4,16}$",username) and re.match("^[a-zA-Z0-9._-~!@#$^&*]{4,16}$",password):
        pass
    else:
        return jsonify({'code':60204,'message': '用户名密码错误','data':''})

    sql = ''' SELECT `passwd` FROM user WHERE username = %s '''
    db = db_sql()
    res = db.read_sql(sql,value=(username))
    if res and res[0]['passwd'] == password:
        return jsonify({'code':20000,'message': '成功','data':generate_token(username)})
    return jsonify({'code':60204,'message': '用户名密码错误','data':''})
    
def logup():
    data = request.get_json(silent=True)
    if not data:
        return jsonify({'code':10000,'message': '数据错误','data':''})
     
    username = data['username']
    password = data['password']
    if re.match("^[a-zA-Z0-9_-]{4,16}$",username) and re.match("^[a-zA-Z0-9._-~!@#$^&*]{4,16}$",password):
        pass
    else:
        return jsonify({'code':60204,'message': '数据格式错误','data':''})
    name = data["name"]
    phone = data["phone"]

    db = db_sql()
    db.insert_sql("INSERT INTO `user` (`username`, `passwd`, `name`, `phone`) VALUES (%s, %s, %s, %s);",value=(username,password,name,phone))
    return jsonify({'code':20000,'message': '成功','data':generate_token(username)})

def logout():
    return jsonify({'code':20000,'message': '成功','data':''})


"""
CREATE TABLE `user` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` text NULL,
  `passwd` text NULL,
  `name` text NULL,
  `phone` text NULL,
  PRIMARY KEY (`id`)
) ENGINE = innodb
"""

"""
CREATE TABLE `reserve` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `time` text NULL,
  `person` int NULL,
  `part` int NULL,
  `desp` text NULL,
  PRIMARY KEY (`id`)
) ENGINE = innodb
"""

"""

"""
"""

"""