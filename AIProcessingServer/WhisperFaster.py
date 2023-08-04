import app


def wav_to_text():
    segments, info = app.model.transcribe("result.wav", beam_size=3)

    res = ""

    for segment in segments:
        res += segment.text
    return res
