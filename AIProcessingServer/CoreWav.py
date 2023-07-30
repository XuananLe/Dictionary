import base64
import whisper
import asyncio
import CoreImage


def wav_to_base64(wav_path):
    with open(wav_path, 'rb') as f:
        wav_data = f.read()
        base64_data = base64.b64encode(wav_data).decode('utf-8')
    return base64_data


def wav_to_text(models):
    model = whisper.load_model(models)
    result = model.transcribe("result.wav", fp16=False)
    return result['text']


def base64_to_wav(base64_code):
    decoded_data = base64.b64decode(base64_code)
    wav_file = open('result.wav', 'wb')
    wav_file.write(decoded_data)
    wav_file.close()


async def main(base64_data: str):
    base64_to_wav(base64_data)
    text1 = wav_to_text("base.en")
    text2 = await CoreImage.TranslateManager.translate_word(text1, 'en', 'vi')
    print("Original text: ", text1)
    print("Translated text: ", text2)
    return text1, text2

if __name__ == '__main__':
    print(asyncio.run(main(wav_to_base64("result.wav"))))
