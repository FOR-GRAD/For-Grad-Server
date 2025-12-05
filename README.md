# 🎓 Forgrad (포어그래드)

> <b>"복잡한 졸업 요건, 더 이상 헤매지 마세요."</b>
> 방대한 학사 정보 속에서 길을 잃은 대학생들을 위해, 흩어진 정보를 한곳에 모아 보여주는 <b>대학생 맞춤형 졸업 로드맵 서비스</b>입니다.

<div align="left">
  </div>

<div align="left">
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/spring boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> 
  <img src="https://img.shields.io/badge/spring data jpa-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
  <img src="https://img.shields.io/badge/stomp-000000?style=for-the-badge&logoColor=white">
  <img src="https://img.shields.io/badge/jsoup-E34F26?style=for-the-badge&logo=html5&logoColor=white">
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
  <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
  <img src="https://img.shields.io/badge/aws-232F3E?style=for-the-badge&logo=amazonwebservices&logoColor=white"> 
  <img src="https://img.shields.io/badge/android-3DDC84?style=for-the-badge&logo=android&logoColor=white">
</div>

<br>

## 📖 프로젝트 소개 (Introduction)

<b>Forgrad</b>는 복잡하고 흩어져 있는 졸업 요건 정보를 한곳에 모아, 대학생들이 효율적이고 계획적인 학교생활을 할 수 있도록 돕는 <b>졸업 관리 및 학사 정보 플랫폼</b>입니다.

많은 학생들이 학교 홈페이지의 방대한 정보 속에서 자신에게 필요한 졸업 요건을 찾는 데 어려움을 겪고 있습니다. Forgrad는 이러한 일상의 불편함을 해소하고, 사용자가 본인의 졸업 진행 상황을 직관적으로 파악할 수 있도록 시각화된 정보를 제공합니다.

<br>

## 💡 기획 배경 (Background)

대학생들에게 '졸업'은 가장 중요한 목표 중 하나지만, 정작 졸업에 필요한 구체적인 요건이나 남은 학점 등을 정확히 파악하고 있는 학생은 많지 않습니다.

1. <b>정보의 파편화:</b> 학교 홈페이지 내 정보가 너무 방대하고 여러 곳에 흩어져 있어 접근성이 떨어집니다.
2. <b>관리의 어려움:</b> 개인별로 상이한 졸업 요건(전공, 학번별)을 스스로 챙기기가 쉽지 않습니다.

우리는 이러한 문제를 해결하고자 <b>"사용자들의 불편함을 해소하고 편의에 기여하는 서비스"</b>를 목표로 Forgrad를 기획하게 되었습니다.

<br>

## 🛠 시스템 아키텍처 (System Architecture)

<b>1. CI/CD 파이프라인</b>
GitHub Actions와 Docker를 활용하여 `PR Merge → Build → Docker Image Push → EC2 Pull & Run`으로 이어지는 자동화된 배포 프로세스를 구축했습니다.

<b>2. 서버 인프라 (AWS)</b>
- <b>Compute:</b> Amazon EC2 내 Docker 컨테이너 환경에서 Spring Boot 서버 실행
- <b>Database:</b> Amazon RDS(MySQL)를 이용한 관계형 데이터 관리
- <b>Storage:</b> Amazon S3를 활용한 파일(이미지 등) 업로드 및 관리

<b>3. 데이터 수집 및 통신</b>
- <b>Client:</b> Android 앱과 RESTful API(HTTP) 통신
- <b>Crawling:</b> Jsoup 라이브러리를 사용하여 학교 서버의 공지사항 및 졸업 요건 데이터 크롤링
<img width="1701" height="1261" alt="Image" src="https://github.com/user-attachments/assets/eaed8433-47ff-4922-b8a5-49ed59c842b6" />

<br>

## 🚀 주요 기능 (Key Features)

<b>1. 🎓 졸업 및 학사 관리 (Graduation Management)</b>
- <b>졸업 요건 확인:</b> 졸업 요건, 성적 사항, 개인별 이수 현황을 탭별로 상세 조회
- <b>학사 정보 시각화:</b> 홈 화면 대시보드 및 졸업 예정일(D-Day) 설정, 응원의 한마디 기능
- <b>비교과 활동:</b> 학교 비교과 포인트 조회 및 관리

<b>2. 📅 시간표 (Timetable)</b>
- <b>시간표 관리:</b> 시간표 조회, 추가, 수정, 삭제 기능
- <b>상세 검색:</b> 학년/학기별, 트랙별, 과목별 검색을 통한 손쉬운 시간표 구성

<b>3. 📂 커리어 및 스펙 쌓기 (Career & Portfolio)</b>
- <b>커리어 관리:</b> 활동 기록 저장, 수정, 삭제 및 카테고리별 목록 조회/검색
- <b>자격증 관리:</b> 취득한 자격증 추가, 조회, 수정, 삭제
- <b>메모 기능:</b> 자유로운 메모 작성(추가/조회/수정/삭제)을 통한 일정 및 할 일 관리
