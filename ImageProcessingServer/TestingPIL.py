from PIL import Image, ImageDraw, ImageFont

w, h = 300, 100
text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. I wolud like to test this text. Spam spa"

im = Image.new("RGB", (w, h), "white")
draw = ImageDraw.Draw(im)

font_size = 40
font = ImageFont.truetype(r"arial-unicode-ms.ttf", font_size)

while font_size > 1:
  if font.getlength(text) < w:
    break
  font_size -= 1
  font = font.font_variant(size=font_size)

draw.text((w / 2, h / 2), text, font=font, fill="black", anchor="mm")
im.show()