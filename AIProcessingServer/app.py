from flask import Flask, request, jsonify
from flask_cors import CORS
import CoreImage

app = Flask(__name__)
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


if __name__ == '__main__':
    app.run(host="0.0.0.0", debug=True)
