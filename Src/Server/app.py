from flask import Flask , jsonify, render_template , request 
from flask_cors import CORS
from permission import login , logout , logup , isUser , getId ,auth
from db import db_sql

app = Flask(__name__)
CORS(app)


@app.route('/',methods=['GET'])
def index():
    return '''<h1> Welcome to the SCSTM API </h1>'''

app.route('/user/login',methods=['POST'])(login)

app.route('/user/isUser',methods=['POST'])(isUser)

app.route('/user/logout',methods=['POST'])(logout)

app.route('/user/logup',methods=['POST'])(logup)

app.route('/user/getId',methods=['POST'])(getId)

@app.route("/api/getReserves",methods=['POST'])
@auth.login_required
def getReserves():
    username = request.args.get("username")

    sql = """
    SELECT
    *
    FROM
    reserve
    WHERE
    `userid` = (
        SELECT
        id
        FROM
        user
        WHERE
        `username` = %s
    )
    """
    db = db_sql()
    res = db.read_sql(sql,value=(username))
    print(res)
    if res:
        return jsonify({'code':20000,'message': '成功','data':res})
    else:
        return jsonify({'code':20000,'message': '没有预约','data':[]})

@app.route("/api/setReserves",methods=['POST'])
@auth.login_required
def setReserves():
    data = request.get_json(silent=True)
    if not data:
        return jsonify({'code':10000,'message': '数据错误','data':''})
        
    userid = data["userid"]
    time = data["time"]
    person = data["person"]
    part = data["part"]
    desp = data["desp"]
    
    sql = """
    INSERT INTO `reserve` (`userid` ,`time`, `person`, `part`, `desp`) VALUES (%s,%s, %s, %s, %s);
    """
    db = db_sql()
    db.insert_sql(sql,value=(userid,time,person,part,desp))

    return jsonify({'code':20000,'message': '成功','data':""})

@app.route("/api/cancelReserves",methods=['POST'])
@auth.login_required
def cancelReserves():
    id = request.args.get("id")

    sql = """
    DELETE FROM `reserve` WHERE `id` = %s
    """
    db = db_sql()
    res = db.insert_sql(sql,value=(id))

    return jsonify({'code':20000,'message': '成功','data':""})

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=9000)


       