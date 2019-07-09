import flask, json
from flask import Flask, request

app = Flask('__main__', template_folder="", static_folder="", root_path="", static_url_path="")
msgs = []

@app.route('/')
def index_page():
    return ("Hello")

@app.route('/knjige')
def ret_knjige():
    return flask.render_template("knjige.json")

@app.route('/renthistory')
def ret_history():
    return flask.render_template("renthistory.json")

@app.route('/knjige/<number>')
def prikaz_jedne_knjige(number=None):
    try:
        with open("knjige.json") as f:
            data = json.load(f)
            for el in data:
                if el['id'] == number:
                    return str(el)
    except(Exception):
        return "Greška"

@app.route('/renthistory/<number>')
def prikaz_jedne_knjige2(number=None):
    try:
        with open("renthistory.json") as f:
            data = json.load(f)
            for el in data:
                if el['id'] == number:
                    return str(el)
    except(Exception):
        return "Greška"



app.run("0.0.0.0", 5000)
