# project-didabara

## 문서를 공유하고 일정 관리를 도와주는 웹 어플리케이션 입니다.

### 노션 링크
### https://stream-seagull-0fe.notion.site/Didabara-project-fd63f80b05ff476b814b45294b7c2fa5

### ERD 다이어그램
![Didabara ERD](https://user-images.githubusercontent.com/98901579/191989160-279fcd0e-8973-4415-9944-a77aa8ebfea5.png)

## 사용 기술
### Back-End

- `Java` `Spring boot`
- `MyBatis` `Jpa CRUD`
- `MySql`
- `AWS S3`

### Front-End

- `React`
- `HTML` `CSS`
- `MUI`

### Process

- 커뮤니티 - `노션`
- 이슈관리 - `Github`
- 버전관리 - `Git` 

## 주요 기능
- 로그인 회원가입
    - 회원 가입시 이메일 인증
    - 아이디, 비밀번호 찾기 sms 인증
    - 카카오톡 회원가입, 로그인
- 초대 및 구독
    - 초대코드가 있으면 방에 구독 가능
    - 나의 구독 목록 보기 기능
- 파일 업로드 및 미리보기
    - office 문서 업로드시 pdf 로 변환하여 미리보기 가능
- 게시글
    - 게시글 댓글 작성
    - 게시글 마다 채팅 가능
    - 읽은 문서인지 체크 기능
    - 게시글의 문서의 시작일 종료일 설정가능
    - 종료일이 지난 문서만 따로 보기 가능 

## 맡은 역할
### Back-End
#### 기간 : 2022.08.16 ~ 2022.09.16
- 회원가입, 로그인, 사용자 정보 수정
    - 사용자 회원 가입, 탈퇴시 유효성 검사 및 DB 저장
    - 사용자 로그인 시 유효성 검사 및 토큰 발급
    - 소셜 로그인 구현 (카카오톡 로그인)
    - 사용자 정보 업데이트시 유효성 검사
- File Upload
    - ppt, excel, word, jpg 파일 업로드시 pdf 파일과 jpg 파일로 변환하여 AWS S3에 업로드
- Email, SMS 인증
    - 사용자 회원 가입시 입력한 이메일로 인증 코드 전송 및 확인 후 회원 가입
    - 사용자 이메일 찾기, 임시 비밀번호 발급시 입력받은 값과 DB의 값 일치시 SMS 인증코드 전송
