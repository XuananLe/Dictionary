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
