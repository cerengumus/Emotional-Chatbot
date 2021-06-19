#!/usr/bin/env python
# -*- coding: utf8 -*-
from flask import Flask, request, jsonify

from main import chatWithBot
from chat import chat
app = Flask(__name__)


@app.route('/chat', methods=['GET', 'POST'])
def chatBot():
    chatInput = request.form['chatInput']
    return jsonify(chatBotReply=chat(chatInput))


if __name__ == '__main__':
    app.run(host='192.168.154.1',debug=True)