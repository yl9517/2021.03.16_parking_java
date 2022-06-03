# :tulip: Parking lot Management (주차장 관리) 프로젝트
대다수 사람들의 주 이동수단인 자동차, 특정건물에 들어갈 때마다 매번 주차자리를 찾아 돌고 도는 번거로움을 덜기 위해 빠르고 편한 주차서비스를 제공합니다.

#### 프로젝트 수행자 : 총 1명 - 이여름
#### 프로젝트 기간 : 2021.03.16 ~ 2021.04.06 (약 3주)
![기간](https://user-images.githubusercontent.com/80736033/125040417-999eb080-e0d2-11eb-9bfd-2aa186236b2c.png)

#### 구현한 기능
- 주차장부 검색/작성/수정 (입차/출차)
- 일/월별 주차 시간대 누적 그래프차트
- 관리자 검색/추가/수정/삭제
- 근태내역 검색/작성/수정
- 시간에 따른 주차요금
- 일/월별 수입 그래프 차트
- 월/연별 수입 장부

## 💻링크
- 시연영상 :  https://youtu.be/qU-p2KDRApY
## 스킬
![image](https://user-images.githubusercontent.com/80736033/171784586-c9211fd7-477f-4a52-a7d2-081cd6d5b97c.png)

## DB정의서
![image](https://user-images.githubusercontent.com/80736033/135705389-b60a29b0-2c60-42b1-8161-c09636389f53.png)

## 사용자서비스
![002](https://user-images.githubusercontent.com/80736033/125043001-76293500-e0d5-11eb-9471-129198363b6f.png)

## 관리자서비스
![0001](https://user-images.githubusercontent.com/80736033/125043435-e1730700-e0d5-11eb-8cd0-9048d2116dda.png)



## 기능 (사용자)
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

##기능 (관리자)
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
- 이름 검색기능
- 직원 근태내역 확인 가능

##### 4. 주차장부
![image](https://user-images.githubusercontent.com/80736033/125758216-cdacbcb0-f7e6-4689-a4d6-253bca1ac9d9.png)
- 차량번호 검색기능
- 선택날짜 장부조회
- 당일,당월 시간별 입/출차 현황차트

![image](https://user-images.githubusercontent.com/80736033/125761943-523098a4-5cc2-41d7-9764-dd8e515f0b23.png)

##### 5. 수입
![image](https://user-images.githubusercontent.com/80736033/125882724-d6835dad-5472-45f7-b468-f8e30cf2f4fa.png)
![image](https://user-images.githubusercontent.com/80736033/125886830-1e1aa59a-447d-45ce-9905-09e87118eb0f.png)
![image](https://user-images.githubusercontent.com/80736033/125889018-07b73073-bb51-4620-84d9-d41dc1239a64.png)
- 일,월,연별 수입표 및 차트
