import time

import whisper
from faster_whisper import WhisperModel
from flask import Flask, request, jsonify
from flask_cors import CORS
import CoreImage
import requests
import CoreWav

app = Flask(__name__)
model_size = "tiny.en"
model = WhisperModel(model_size, device="cpu", compute_type="int8")
CORS(app)
app.config['CORS_HEADERS'] = 'Content-Type'


@app.route('/', methods=['GET', 'POST'])
async def index():
    return "Hello world this is the index page"


@app.route('/image', methods=['GET', 'POST'])
async def translate_image():
    if request.method == 'POST':
        try:
            data_from_client = request.get_json()
            base64_data = data_from_client.get('image_data', '')
            base64_data = await CoreImage.main(base64_data)
            response = jsonify({'image_data': base64_data})
            response.headers['Content-type'] = 'application/json'
            return response
        except:
            response = jsonify({'image_data': ''})
            response.headers['Content-type'] = 'application/json'
            return response
    elif request.method == "GET":
        return "Hello world"


@app.route('/wav', methods=['GET', 'POST'])
async def translate_wav():
    if request.method == 'POST':
        try:
            data_from_client = request.get_json()
            base64_data = data_from_client.get('wav_data', '')
            text1, text2 = await CoreWav.main(base64_data)
            response = jsonify({'original_text': text1, 'translated_text': text2})
            return response
        except:
            response = jsonify({'original_text': '', 'translated_text': ''})
            return response
    elif request.method == "GET":
        return jsonify({'message': 'Hello world'})


if __name__ == '__main__':
    app.run(host="0.0.0.0", debug=True)
