import asyncio
import base64

import cv2
import pytesseract
from PIL import Image, ImageDraw, ImageFont

import CoreTranslation


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
                text = await CoreTranslation.TranslateManager.translate_word(text, 'en', 'vi')
                draw = ImageDraw.Draw(pil_img)
                font_size = 40
                font = ImageFont.truetype(font_path, font_size)
                while font_size > 1:
                    if font.getlength(text) - text_w < 0000000.1:
                        break
                    font_size -= 1
                    font = ImageFont.truetype(font_path, font_size)

                font_size -= 1
                print(
                    f"TextSize: {font_size}, font.getLenghth: {font.getbbox(text)}, text heigh: {text_h}, text width: {text_w}   TEXT {text}")
                draw.rectangle([(x, y), (x + text_w, y + text_h)], fill=(255, 255, 255, 0))
                text_width, text_height = font.getsize(text)
                draw.text((x + (text_w - text_width) // 2, y + (text_h - text_height) // 2), text, font=font,
                          align="left", fill=(0, 0, 0))  # draw text in the middle of the box)

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


if __name__ == "__main__":
    asyncio.run(
        main(image_to_base64("/home/xuananle/Pictures/Screenshots/Screenshot from 2023-07-29 09-55-10.png")))
