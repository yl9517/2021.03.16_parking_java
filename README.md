# :tulip: Parking lot Management (주차장 관리) 프로젝트

#### 프로젝트 수행자 : 총 1명 - 이여름(yl9517@naver.com)
#### 프로젝트 기간 : 2021.03.16 ~ 2021.04.06 (약 3주)
![기간](https://user-images.githubusercontent.com/80736033/125040417-999eb080-e0d2-11eb-9bfd-2aa186236b2c.png)
#### 시연 영상 : https://youtu.be/qU-p2KDRApY

## 개요
대다수 사람들의 주 이동수단인 자동차, 특정건물에 들어갈 때마다 매번 주차자리를 찾아 돌고 도는 번거로움을 덜기 위해 빠르고 편한 주차서비스를 제공합니다.

## 환경
* 사용언어 : JAVA (JavaFX)
* 환경 : Eclipse
* UI : JavaFX Scene Builder
* DBMS : MySQL
* OS : Windows 10

## MySQL 테이블 생성
~~~~
   create database park;
~~~~
~~~~
//직원DB
create table park.manage(
    name varchar(30) not null,
    password varchar(30) primary key,
    gender varchar(20) not null,
    birth varchar(30) not null,
    phone int not null,
    email varchar(40) not null,
    power varchar(20) not null
);
~~~~
~~~~
//근태DB
create table park.work(
    num int primary key auto_increment,
    startDay varchar(30) not null,
    startTime varchar(30) not null,
    endDay varchar(30),
    endTime varchar(30),
    worker varchar(30) not null
);
~~~~
~~~~
//주차상황DB
create table park.parking(
    parkNum int primary key auto_increment,
    floor varchar(20) not null,
    seat varchar(20) not null,
    carNum varchar(20) unique
);
~~~~
~~~~
//주차장부DB
create table park.parkbook(
    num int primary key auto_increment,
    date varchar(30) not null,
    carNum varchar(30) not null,
    inTime varchar(30) not null,
    outTime varchar(30) ,
    price int,
    staff varchar(30) not null,
    finish boolean not null
);
~~~~
~~~~
//수입BD
create table park.income(
      incomeNum int primary key auto_increment,
      year int not null,
      month int not null,
      day int not null,
      income int not null
);
~~~~
## 사용자서비스
![002](https://user-images.githubusercontent.com/80736033/125043001-76293500-e0d5-11eb-9471-129198363b6f.png)

## 관리자서비스
![0001](https://user-images.githubusercontent.com/80736033/125043435-e1730700-e0d5-11eb-8cd0-9048d2116dda.png)



## 구현한 기능 (사용자)
##### 1. 메인
![image](https://user-images.githubusercontent.com/80736033/125622540-2d9cd904-c8e1-4ae5-a78a-9e6bed955f2b.png)

##### 2. 주차장부
![image](https://user-images.githubusercontent.com/80736033/125622967-8a57b324-ee94-4b29-a690-6b29605e2e8d.png)
- B1, B2, B3층 별 주차현황 확인

##### 3. 입차
![image](https://user-images.githubusercontent.com/80736033/125623257-bb33a19f-b43a-4039-9ef6-463575b08944.png)
![image](https://user-images.githubusercontent.com/80736033/125623347-7220271f-4b9d-4634-be6e-2ce8feedb8a0.png)
![image](https://user-images.githubusercontent.com/80736033/125623866-913e1b4a-7b2a-46c5-85ef-829f9ca4e88f.png)
- 올바른 차량번호 입력 후 자리 선택하여 입차완료
- 이미 주차된 자리는 자리선택 불가

##### 4. 출차
![image](https://user-images.githubusercontent.com/80736033/125717159-961617a4-60f6-4010-97d4-ff9e902063c9.png)
- 입차 시간에 비례한 요금 계산

## 구현한 기능 (관리자)
##### 1. 메인
![image](https://user-images.githubusercontent.com/80736033/125627405-2735be3f-18db-41d0-ad8d-addd4f144410.png)
![image](https://user-images.githubusercontent.com/80736033/125731834-334ff0dc-a6cb-42bd-b162-b1b4af63cc32.png)
![image](https://user-images.githubusercontent.com/80736033/125732118-7c105aae-aaa0-40d0-a2c3-4f4df89f1472.png)

##### 2. 주차장부
![image](https://user-images.githubusercontent.com/80736033/125732384-d31753e2-7658-46be-8939-6fbf49548659.png)
- 현재 주차되어 있는 차량 확인 가능
- 주차차량 선택 시 현재까지의 요금 확인 가능

##### 3. 직원관리
![image](https://user-images.githubusercontent.com/80736033/125732897-5d686226-8ad7-4ec1-85b2-2d42b326b997.png)
![image](https://user-images.githubusercontent.com/80736033/125733118-c0ad0b41-5b8c-4797-9654-361ea98352f5.png)
![image](https://user-images.githubusercontent.com/80736033/125733178-3a1d48af-bf47-4e3f-987b-6a9231043928.png)
- 직원 미선택 후 수정/삭제 시 안내
- staff권한을 가진 직원은 등록/수정/삭제 클릭불가
- 이름 검색기능 O
- 직원 근태내역 확인 가능

##### 4. 주차장부
![image](https://user-images.githubusercontent.com/80736033/125758216-cdacbcb0-f7e6-4689-a4d6-253bca1ac9d9.png)
- 차량번호 검색기능 O 
- 선택날짜 장부조회
- 당일,당월 시간별 입/출차 현황차트

![image](https://user-images.githubusercontent.com/80736033/125761943-523098a4-5cc2-41d7-9764-dd8e515f0b23.png)

##### 5. 수입
![image](https://user-images.githubusercontent.com/80736033/125882724-d6835dad-5472-45f7-b468-f8e30cf2f4fa.png)
![image](https://user-images.githubusercontent.com/80736033/125886830-1e1aa59a-447d-45ce-9905-09e87118eb0f.png)
![image](https://user-images.githubusercontent.com/80736033/125889018-07b73073-bb51-4620-84d9-d41dc1239a64.png)
- 일,월,연별 수입표 및 차트



## 보완점
- [사용자] 요금표 
- [사용자] 번호인식 확대 필요 (세자리 수)
- [관리자] 요금 변경란
- [관리자] 요금계산기능 리팩토링

## 느낀점
java에 대한 학습을 끝마쳤을 때, 평소 '이런 기능은 왜 없지?' 라는 프로그램을 간소하게나마 첫 프로젝트로 만들어보았다.

기능을 구현하면서도, 요금 계산기능은 더 쉽게 구현할 것 같은데 라는 생각이 들었다. 그나마 조금 코드를 줄였지만 많이 부족한 것 같다.

그렇지만 3주라는 시간 내에 기획부터 UI설계, 기능 구현까지 혼자 힘으로 해내면서 java에 대한 이해도가 높아진 경험이다.

다음에 하게 될 프로젝트는 좀 더 향상 시켜서 만들어보고 싶다!
