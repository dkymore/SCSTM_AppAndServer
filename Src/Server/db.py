
import pymysql
import logging 

logger = logging.getLogger('db')
logger.setLevel(logging.DEBUG)
hdr = logging.StreamHandler()
formatter = logging.Formatter('[%(asctime)s] %(name)s:%(levelname)s: %(message)s')
hdr.setFormatter(formatter)
logger.addHandler(hdr)
from secert import mysql

class config:
    mysql = mysql


class db_sql:
    def __init__(self):
        self.conn = pymysql.connect(
        host=config.mysql.host,
        port=config.mysql.port,
        user=config.mysql.user,
        passwd=config.mysql.passwd,
        db=config.mysql.db
        )  
        self.cur = self.conn.cursor(pymysql.cursors.DictCursor)
        self.isDebug = True
    def close_db(self):
        self.cur.close()    
        self.conn.close()   
    def insert_sql(self,sql:str,value:tuple=None) ->str :
        try: 
            if self.isDebug:
                logger.info(sql)
            self.cur.execute(sql,value)
        except Exception as e:
            logger.error("error",e)
            self.conn.rollback()
        else:
            self.conn.commit()
    def read_sql(self,sql:str,value:tuple=None) ->str :
        try:
            if self.isDebug:
                logger.info(sql)
            self.cur.execute(sql,value)
        except Exception as e:
            logger.error(e)
            self.conn.rollback()
            ret = None
        else:
            ret = self.cur.fetchall()
            if self.isDebug:
                logger.info(ret)
        return ret
