import asyncio
import base64
from PIL import Image, ImageDraw, ImageFont
import cv2
import pytesseract
import aiohttp
import requests


class TranslateManager:
    @staticmethod
    async def translate_word(text_to_translate, source_language, target_language):
        encoded_text = requests.utils.quote(text_to_translate)
        api_url = f"https://translate.googleapis.com/translate_a/single?client=gtx&sl={source_language}&tl={target_language}&dt=t&text={encoded_text}&op=translate"

        async with aiohttp.ClientSession() as session:
            async with session.get(api_url) as response:
                translation_data = await response.json()

        return await TranslateManager.parse_translation_response(translation_data)

    @staticmethod
    async def parse_translation_response(response):
        try:
            translation_segments = response[0]
            translated_text = "".join(segment[0] for segment in translation_segments)
            return translated_text
        except (KeyError, IndexError):
            return " "


async def make_word_invisible(image_path):
    pytesseract.pytesseract.tesseract_cmd = r'/usr/bin/tesseract'

    img = cv2.imread(image_path)
    h, w, _ = img.shape

    # Convert the image to RGB
    img_rgb = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)

    # Perform OCR to get the bounding box of the word
    data = pytesseract.image_to_data(img_rgb, output_type=pytesseract.Output.DICT)

    for i, word_text in enumerate(data['text']):
        word_text = word_text.lower()
        word_text = word_text.replace(')', '')
        word_text = word_text.replace('(', '')
        word_text = word_text.replace('.', '')
        word_text = word_text.replace(',', '')
        translated_text = word_text
        # try:
        #     translated_text = await TranslateManager.translate_word(word_text, 'en', 'vi')
        # except:
        #     pass
        # if(translated_text != ' '):
        #     print(translated_text)
        # Get the bounding box of the original word
        translated_text = translated_text.encode('utf-8').decode('utf-8')
        (x, y, w, h) = (data['left'][i], data['top'][i], data['width'][i], data['height'][i])

        # Draw a white rectangle over the original word
        cv2.rectangle(img_rgb, (x, y), (x + w, y + h), (255, 255, 255), -1)

        # Write the translated word in the box
        cv2.putText(img_rgb, translated_text, (x, y + h), cv2.FONT_HERSHEY_SIMPLEX, 1.0, (0, 0, 0), 2)

    # Save the image
    cv2.imwrite('result.png', cv2.cvtColor(img_rgb, cv2.COLOR_RGB2BGR))


async def main(base64_data: str):
    pytesseract.pytesseract.tesseract_cmd = r'/usr/bin/tesseract'
    base64_to_image(base64_data)
    img = cv2.imread("result.png")
    img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    h, w, _ = img.shape
    pil_img = Image.fromarray(img)
    font_path = "arial-unicode-ms.ttf"
    boxes = pytesseract.image_to_data(img, lang="eng", config="--psm 6")
    for x, b in enumerate(boxes.splitlines()):
        if x != 0:
            b = b.split()
            if len(b) == 12:
                x, y, text_w, text_h = int(b[6]), int(b[7]), int(b[8]), int(b[9])
                cv2.rectangle(img, (x, y), (x + text_w, y + text_h), (0, 0, 255), 2)
                text = b[11]
                text = await TranslateManager.translate_word(text, 'en', 'vi')
                print(text)
                font = ImageFont.truetype(font_path, 18)

                draw = ImageDraw.Draw(pil_img)

                text_region = pil_img.crop((x, y, x + text_w + 100, y + text_h + 100))

                pil_img.paste(text_region, (x, y))
                draw.rectangle([(x, y), (x + text_w, y + text_h)], fill=(255, 255, 255))
                draw.text((x, y), text, font=font, fill=(0, 0, 0))

    pil_img.save('result.png')
    return image_to_base64('result.png')
def image_to_base64(image_path):
    with open(image_path, 'rb') as f:
        image_data = f.read()
        base64_data = base64.b64encode(image_data).decode('utf-8')
    return base64_data


def base64_to_image(base64_code):
    decoded_data = base64.b64decode(base64_code)
    img_file = open('result.png', 'wb')
    img_file.write(decoded_data)
    img_file.close()

if(__name__ == "__main__"):
    asyncio.run(main(image_to_base64("/home/xuananle/Pictures/Pictures/Screenshots/Screenshot from 2023-06-25 22-27-05.png")))
