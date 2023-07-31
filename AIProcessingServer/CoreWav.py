import base64
import os
import whisper
import asyncio
import openai

openai.api_key = "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"
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


def OpenAI_translate(text, language):
    prompt = f"Translate this {text} to {language}:"
    response = openai.Completion.create(
        engine="text-davinci-003",
        prompt=prompt,
        temperature=0.3,
        max_tokens=1500,
        top_p=1,
        frequency_penalty=0,
        presence_penalty=0,
    )
    return response.choices[0].text.strip()


async def main(base64_data: str):
    base64_to_wav(base64_data)
    text1 = wav_to_text("tiny.en")
    text2 = OpenAI_translate(text1, "Vietnamese")
    print("Original text: ", text1)
    print("Translated text: ", text2)
    return text1, text2


if __name__ == '__main__':
    asyncio.run(main(wav_to_base64("Client.wav")))
