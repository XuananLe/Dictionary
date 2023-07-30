import whisper
print(whisper.available_models())
model = whisper.load_model("tiny")
result = model.transcribe("Recording 1.flac", fp16=False)
print(result['text'])
