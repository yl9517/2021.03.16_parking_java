# Parking lot Management (주차장 관리) 프로젝트

#### 프로젝트 기간 : 2021.03.16 ~ 2021.04.06 (약 3주)
#### 프로젝트 수행자 : 총 1명 - 이여름(yl9517@naver.com)
#### 시연 영상 :

## 환경
* JAVA (JavaFX)
* Scene Builder
* MySQL

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

## 구현한 기능
