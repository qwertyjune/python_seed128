# python_seed128
python seed_128

KISA에서 제공하는 seed128 소스코드와 동일한 아웃풋을 내주는 파이썬 파일입니다.
python의  openSSL wrapper 인 cryptography 모듈을 사용하였습니다.

16비트 키, 16비트 평문을 16비트의 복문으로 바꿔주는 역할.
패딩이 필요할 시엔 \x00을 붙여서 16비트를 맞춤.

실제 API 통신을 할 때, 주로 byte stream을 utf-8로 인코딩해서 보내는데, 
이 과정에서 먼저 utf-16 (\x0000~ \xFFFF)로 변환 한 뒤, 이를 다시 utf-8로 인코딩합니다.


