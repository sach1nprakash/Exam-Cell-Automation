DROP TABLE student CASCADE CONSTRAINTS;
DROP TABLE branchsemestercourses CASCADE CONSTRAINTS;
DROP TABLE courses CASCADE CONSTRAINTS;
DROP table backlogs cascade constraints;
DROP TABLE studentcourse CASCADE CONSTRAINTS;
DROP TABLE schedule CASCADE CONSTRAINTS;

CREATE TABLE student (
studentId VARCHAR2(20) CONSTRAINT stu_pk PRIMARY KEY,
studentName VARCHAR2(25),
dateOfBirth DATE NOT NULL,
branch VARCHAR2(5),
sem NUMBER,
email VARCHAR2(15),
costofexams number,
ddnumber varchar2(15)
);

INSERT INTO student VALUES('s1','sai','16-Jan-1987','cse',4,'msain@adh');
INSERT INTO student VALUES('s2','ram','12-Feb-2002','ece',4,'ram@alf');



CREATE table branchsemestercourses(
branch VARCHAR2(10),
semester NUMBER,
PRIMARY KEY(branch,semester)
);


INSERT INTO branchsemestercourses VALUES('cse',1);
INSERT INTO branchsemestercourses VALUES('cse',4);
INSERT INTO branchsemestercourses VALUES('cse',3);
INSERT INTO branchsemestercourses VALUES('eee',2);

CREATE table courses(
courseId VARCHAR2(10),
courseName VARCHAR2(15),
courseType VARCHAR2(15),
branch VARCHAR2(10),
semester NUMBER,
registrationCost NUMBER,
primary key(courseId,semester,branch),
FOREIGN KEY (branch, semester) REFERENCES branchsemestercourses(branch, semester)
);

INSERT INTO courses VALUES('cse414','java','mandatory','cse',4,200);
INSERT INTO courses VALUES('cse410','c++','mandatory','cse',1,150);
INSERT INTO courses VALUES('cse412','networks','elective','cse',3,500);
INSERT INTO courses VALUES('che415','chemisty','mandatory','cse',1,200);
INSERT INTO courses VALUES('ece442','EAC ','mandatory','cse',4,451);
INSERT INTO courses VALUES('ece413','embedded','mandatory','cse',1,565);
INSERT INTO courses VALUES('cse443','graphics','mandatory','cse',4,222);
INSERT INTO courses VALUES('eee400','dsp','elective','cse',1,999);
INSERT INTO courses VALUES('eee404','electronics','mandatory','eee',2,452);
INSERT INTO courses VALUES('eee256','embedded','mandatory','eee',2,333);
INSERT INTO courses VALUES('cse434','FLA','elective','cse',4,555);
INSERT INTO courses VALUES('cse488','PR','elective','cse',4,111);


CREATE table backlogs(
studentId VARCHAR2(20) REFERENCES student(studentId),
courseId VARCHAR2(10),
branch VARCHAR2(10),
semester NUMBER,
primary key (studentId,courseId),
foreign key(courseId,branch,semester) references courses(courseId,branch,semester)
);

insert into backlogs values('s1','che415','cse',1);
insert into backlogs values('s1','cse410','cse',1);
insert into backlogs values('s2','che415','cse',1);
insert into backlogs values('s2','cse412','cse',3);
insert into backlogs values('s3','che415','cse',1);
insert into backlogs values('s3','cse410','cse',1);
insert into backlogs values('s5','eee485','eee',1);
insert into backlogs values('s5','eee410','eee',1);
insert into backlogs values('s6','ece210','ece',2);
insert into backlogs values('s6','ece390','ece',2);


CREATE TABLE studentcourse (
studentId VARCHAR2(20) REFERENCES student(studentId),
courseId VARCHAR2(10),
branch VARCHAR2(10),
semester NUMBER,
scheduleStatus varchar2(10) default 'null',
marks number,
primary key (studentId,courseId),
foreign key(courseId,branch,semester) references courses(courseId,branch,semester)
);

create table schedule(
scheduleId varchar2(10),
courseId varchar2(10),
dateofexam varchar2(30),
timing varchar2(20),
primary key(scheduleId,courseId)
);

create table backlogschedule(
studentId varchar2(20),
courseId varchar2(10),
dates varchar2(30),
timing varchar2(20),
primary key(studentId,courseId)
);

create table messages(
studentId varchar2(20),
email VARCHAR2(15),
message varchar2(3999),
primary key(studentId,message)
);

create table results(
studentId varchar2(20),
sem number,
courseId varchar2(10),
marks number,
grade varchar2(3),
result varchar2(5),
primary key(studentId,sem,courseId));

create table announcments(
announcment varchar2(3000)
);

COMMIT;
SELECT * FROM student;
SELECT * FROM courses;