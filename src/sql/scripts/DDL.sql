CREATE TABLE Campus (
                        cStAddress 		char(40),
                        cZipCode		char(7),
                        name     		char(20)   NOT NULL,
                        population 		int,
                        PRIMARY KEY(cStAddress, cZipCode)
);

CREATE TABLE ResidentialManagingOffice(
                                          rMOStAddress  	char(40),
                                          rMOZipCode		char(7),
                                          name 			char(20) NOT NULL,
                                          budget     		int,
                                          cStAddress    	char(40) NOT NULL,
                                          cZipCode		char(7) NOT NULL,
                                          PRIMARY KEY(rMOStAddress, rMOZipCode),
                                          FOREIGN KEY(cStAddress, cZipCode) REFERENCES Campus(cStAddress, cZipCode) ON DELETE CASCADE
);


CREATE TABLE BuildingManager(
                                bMEmployeeID 		int PRIMARY KEY,
                                name 				char(20) NOT NULL,
                                yearsOfExperience 	int,
                                phoneNumber 		char(12) NOT NULL,
                                rMOStAddress 		char(40) NOT NULL,
                                rMOZipCode			char(7) NOT NULL,
                                FOREIGN KEY(rMOStAddress, rMOZipCode) REFERENCES ResidentialManagingOffice(rMOStAddress, rMOZipCode)
);


--- RESIDENCE TABLES


CREATE TABLE ResidenceCapacity(
                                  resName				char(20),
                                  bMEmployeeID		int,
                                  capacity			int NOT NULL,
                                  PRIMARY KEY(resName, bMEmployeeID),
                                  FOREIGN KEY(bMEmployeeID) REFERENCES  BuildingManager(bMEmployeeID)
);

CREATE TABLE Residence(
                          resStAddress		char(40),
                          resZipCode			char(7),
                          resName				char(20) NOT NULL,
                          rMOStAddress		char(40) NOT NULL,
                          rMOZipCode			char(7) NOT NULL,
                          bMEmployeeID		int NOT NULL UNIQUE,
                          PRIMARY KEY(resStAddress, resZipCode),
                          FOREIGN KEY(rMOStAddress, rMOZipCode) REFERENCES ResidentialManagingOffice(rMOStAddress, rMOZipCode),
                          FOREIGN KEY(bMemployeeID) REFERENCES BuildingManager(bMEmployeeID),
                          FOREIGN KEY(resName, bMEmployeeID) REFERENCES  ResidenceCapacity(resName, bMEmployeeID)
);

CREATE TABLE ResidenceBudget(
                                resStAddress 			char(40),
                                resZipCode				char(7),
                                budget					int,
                                PRIMARY KEY(resStAddress, resZipCode),
                                FOREIGN KEY(resStAddress, resZipCode) REFERENCES Residence(resStAddress, resZipCode) ON DELETE CASCADE
                            );


--- END RESIDENCE TABLES

CREATE TABLE House(
                      houseName 	   	   		char(20),
                      capacity	   		int NOT NULL,
                      type		   		char(20) NOT NULL,
                      ageRestriction		int,
                      resStAddress	    char(40),
                      resZipCode			char(7),
                      PRIMARY KEY(houseName, resStAddress, resZipCode),
                      FOREIGN KEY(resStAddress, resZipCode) REFERENCES Residence(resStAddress, resZipCode) ON DELETE CASCADE
);


CREATE TABLE Floor(
                      fNumber			  	int,
                      capacity		  	int NOT NULL,
                      genderRestriction 	char(10),
                      houseName		  	char(20),
                      resStAddress		char(40),
                      resZipCode			char(7),
                      PRIMARY KEY(fNumber, houseName, resStAddress, resZipCode),
                      FOREIGN KEY(houseName, resStAddress, resZipCode) REFERENCES House(houseName, resStAddress, resZipCode) ON DELETE CASCADE
);

CREATE TABLE Unit(
                     uNumber				int,
                     capacity			int NOT NULL,
                     genderRestriction 	char(10) NOT NULL,
                     vacancy				int NOT NULL,
                     fNumber 			int,
                     houseName			char(20),
                     resStAddress		char(40),
                     resZipCode			char(7),
                     PRIMARY KEY(uNumber, fNumber, houseName, resStAddress, resZipCode),
                     FOREIGN KEY(fNumber, houseName, resStAddress, resZipCode) REFERENCES Floor(fNumber, houseName, resStAddress, resZipCode) ON DELETE CASCADE
);

--- RESIDENT TABLES

CREATE TABLE ResidentInfo(
                             studentNumber		int PRIMARY KEY,
                             email				char(60) UNIQUE NOT NULL,
                             name				char(80) NOT NULL,
                             dob					char(10),
                             yearsInResidence	int
);

CREATE TABLE ResidentAddress(
                                email				char(60) PRIMARY KEY,
                                uNumber				int NOT NULL,
                                fNumber				int NOT NULL,
                                houseName			char(20) NOT NULL,
                                resStAddress		char(40) NOT NULL,
                                resZipCode			char(7) NOT NULL,
                                FOREIGN KEY(uNumber, fNumber, houseName, resStAddress, resZipCode) REFERENCES Unit(uNumber, fNumber, houseName, resStAddress, resZipCode),
                                FOREIGN KEY(email) REFERENCES ResidentInfo(email) ON DELETE CASCADE
);

-- END RESIDENT TABLES

CREATE TABLE SeniorAdvisor(
                              sAEmployeeId 			int NOT NULL UNIQUE,
                              yearsOfExperience   	int,
                              teamBudget          	int,
                              studentNumber       	int PRIMARY KEY,
                              supervisingHouseName 	char(20),
                              supervisingResStAddress char(40),
                              supervisingResZipCode	char(7),
                              UNIQUE(supervisingHouseName, supervisingResStAddress, supervisingResZipCode),
                              FOREIGN KEY(studentNumber) REFERENCES ResidentInfo(studentNumber) ON DELETE CASCADE,
                              FOREIGN KEY(supervisingHouseName, supervisingResStAddress, supervisingResZipCode) REFERENCES House(houseName, resStAddress, resZipCode) ON DELETE SET NULL
);

-- Assertion to make sure 1..n with RA //

CREATE TABLE ResidenceAdvisor(
                                 rAEmployeeId 			int NOT NULL UNIQUE,
                                 yearsOfExperience   	int,
                                 individualBudget    	int,
                                 studentNumber       	int PRIMARY KEY,
                                 sRAStudentNumber		int NOT NULL,
                                 supervisingFloorNum 	int,
                                 supervisingHouseName 	char(20),
                                 supervisingResStAddress char(40),
                                 supervisingResZipCode	char(7),
                                 UNIQUE(supervisingHousename, supervisingResStAddress, supervisingResZipCode, supervisingFloorNum),
                                 FOREIGN KEY(studentNumber) REFERENCES ResidentInfo(studentNumber) ON DELETE CASCADE,
                                 FOREIGN KEY(sRAStudentNumber) REFERENCES SeniorAdvisor(studentNumber),
                                 FOREIGN KEY(supervisingFloorNum, supervisingHouseName, supervisingResStAddress, supervisingResZipCode) REFERENCES Floor(fNumber, Housename, resStAddress, resZipCode) ON DELETE SET NULL
);
----- END DDL SCRIPT





INSERT INTO Campus
VALUES('2329 West Mall', 'V6T 1Z4', 'UBCV', 50000);

INSERT INTO Campus
VALUES('3333 University Way', 'V1V 1V7', 'UBCO', 10000);

INSERT INTO Campus
VALUES('222 Robersion Street', 'V3T 2O4', 'UBCR', 400);

INSERT INTO Campus
VALUES('1234 Simon Road', 'V2T 4Q1', 'SFUV', 20000);

INSERT INTO Campus
VALUES('4352 Surrey Street', 'V6E 2R2', 'SFUS', 50000);

INSERT INTO ResidentialManagingOffice
VALUES ('2205 West Mall', 'V6T 1Z4', 'SHSC', 2000000, (SELECT cStAddress FROM Campus WHERE cStAddress = '2329 West Mall' AND cZipCode = 'V6T 1Z4'),
        (SELECT cZipCode FROM Campus WHERE cStAddress = '2329 West Mall' AND cZipCode = 'V6T 1Z4'));

INSERT INTO ResidentialManagingOffice
VALUES ('5340 Happy Road', 'V1V 1V7', 'SHHS', 3000000, (SELECT cStAddress FROM Campus WHERE cStAddress = '3333 University Way' AND cZipCode = 'V1V 1V7'),
        (SELECT cZipCode FROM Campus WHERE cStAddress = '3333 University Way' AND cZipCode = 'V1V 1V7'));

INSERT INTO ResidentialManagingOffice
VALUES ('2342 Downtown Drive', 'V3T 2Q4', 'UBCSH', 2000, (SELECT cStAddress FROM Campus WHERE cStAddress = '222 Robersion Street' AND cZipCode = 'V3T 2O4'),
        (SELECT cZipCode FROM Campus WHERE cStAddress = '222 Robersion Street' AND cZipCode = 'V3T 2O4'));

INSERT INTO ResidentialManagingOffice
VALUES ('5638 Mountain Avenue', 'V2T 4Q1','SFUSHCS', 5444444 , (SELECT cStAddress FROM Campus WHERE cStAddress = '1234 Simon Road' AND cZipCode = 'V2T 4Q1'),
        (SELECT cZipCode FROM Campus WHERE cStAddress = '1234 Simon Road' AND cZipCode = 'V2T 4Q1'));

INSERT INTO ResidentialManagingOffice
VALUES ('1297 4th Avenue', 'V6E 2R2', 'SFUSHHS' , 5000000, (SELECT cStAddress FROM Campus WHERE cStAddress = '4352 Surrey Street' AND cZipCode = 'V6E 2R2'),
        (SELECT cZipCode FROM Campus WHERE cStAddress = '4352 Surrey Street' AND cZipCode = 'V6E 2R2'));

INSERT INTO BuildingManager
VALUES (43, 'Amy', 6, '123-234-5566', (SELECT rMOStAddress FROM ResidentialManagingOffice WHERE rMOStAddress = '2205 West Mall' AND rMOZipCode = 'V6T 1Z4'),
        (SELECT rMOZipCode FROM ResidentialManagingOffice WHERE rMOStAddress = '2205 West Mall' AND rMOZipCode = 'V6T 1Z4'));

INSERT INTO BuildingManager
VALUES (2, 'Katy', 3, '123-555-3444', (SELECT rMOStAddress FROM ResidentialManagingOffice WHERE rMOStAddress = '2205 West Mall' AND rMOZipCode = 'V6T 1Z4'),
        (SELECT rMOZipCode FROM ResidentialManagingOffice WHERE rMOStAddress = '2205 West Mall' AND rMOZipCode = 'V6T 1Z4'));

INSERT INTO BuildingManager
VALUES (12, 'Mason', null, '666-554-3232', (SELECT rMOStAddress FROM ResidentialManagingOffice WHERE rMOStAddress = '2205 West Mall' AND rMOZipCode = 'V6T 1Z4'),
        (SELECT rMOZipCode FROM ResidentialManagingOffice WHERE rMOStAddress = '2205 West Mall' AND rMOZipCode = 'V6T 1Z4'));

INSERT INTO BuildingManager
VALUES (21, 'Paul', 4, '343-343-2222', (SELECT rMOStAddress FROM ResidentialManagingOffice WHERE rMOStAddress = '5340 Happy Road' AND rMOZipCode = 'V1V 1V7'), (
    SELECT rMOZipCode FROM ResidentialManagingOffice WHERE rMOStAddress = '5340 Happy Road' AND rMOZipCode = 'V1V 1V7'));

INSERT INTO BuildingManager
VALUES (15, 'Mike', 4, '333-222-5555', (SELECT rMOStAddress FROM ResidentialManagingOffice WHERE rMOStAddress = '5340 Happy Road' AND rMOZipCode = 'V1V 1V7'),
        (SELECT rMOZipCode FROM ResidentialManagingOffice WHERE rMOStAddress = '5340 Happy Road' AND rMOZipCode = 'V1V 1V7'));



--RESIDENCE INSERTIONS

INSERT INTO ResidenceCapacity
VALUES ('Totem' , (SELECT bMEmployeeID FROM BuildingManager WHERE bMEmployeeID = 43), 1200);

INSERT INTO ResidenceCapacity
VALUES ('Gage', (SELECT bMEmployeeID FROM BuildingManager WHERE bMEmployeeID = 2), 600);

INSERT INTO ResidenceCapacity
VALUES ('Marine', (SELECT bMEmployeeID FROM BuildingManager WHERE bMEmployeeID = 12), 1000);

INSERT INTO ResidenceCapacity
VALUES ('Oak Tree', (SELECT bMEmployeeID FROM BuildingManager WHERE bMEmployeeID = 21), 1300);

INSERT INTO ResidenceCapacity
VALUES ('Grapes', (SELECT bMEmployeeID FROM BuildingManager WHERE bMEmployeeID = 15), 1600);



INSERT INTO Residence
VALUES ('2205 West Mall' , 'V6T 1Z4', (SELECT resName FROM ResidenceCapacity WHERE resName = 'Totem' AND bMEmployeeID = 43),
        (SELECT rMOStAddress FROM ResidentialManagingOffice WHERE rMOStAddress = '2205 West Mall' AND rMOZipCode = 'V6T 1Z4'),
        (SELECT rMOZipCode FROM ResidentialManagingOffice WHERE rMOStAddress = '2205 West Mall' AND rMOZipCode = 'V6T 1Z4'), (SELECT bMEmployeeID FROM BuildingManager WHERE bMEmployeeID = 43));

INSERT INTO Residence
VALUES ('4545 Argronomy Road', 'V6T 1Z4', (SELECT resName FROM ResidenceCapacity WHERE resName = 'Gage' AND bMEmployeeID = 2),
        (SELECT rMOStAddress FROM ResidentialManagingOffice WHERE rMOStAddress = '2205 West Mall' AND rMOZipCode = 'V6T 1Z4'),
        (SELECT rMOZipCode FROM ResidentialManagingOffice WHERE rMOStAddress = '2205 West Mall' AND rMOZipCode = 'V6T 1Z4'), (SELECT bMEmployeeID FROM BuildingManager WHERE bMEmployeeID = 2));

INSERT INTO Residence
VALUES ('2205 Lower Mall', 'V6T 1Z4', (SELECT resName FROM ResidenceCapacity WHERE resName = 'Marine' AND bMEmployeeID = 12),
        (SELECT rMOStAddress FROM ResidentialManagingOffice WHERE rMOStAddress = '2205 West Mall' AND rMOZipCode = 'V6T 1Z4'),
        (SELECT rMOZipCode FROM ResidentialManagingOffice WHERE rMOStAddress = '2205 West Mall' AND rMOZipCode = 'V6T 1Z4'), (SELECT bMEmployeeID FROM BuildingManager WHERE bMEmployeeID = 12));

INSERT INTO Residence
VALUES ('432 East Mall', 'V1V 1V7', (SELECT resName FROM ResidenceCapacity WHERE resName = 'Oak Tree' AND bMEmployeeID = 21),
        (SELECT rMOStAddress FROM ResidentialManagingOffice WHERE rMOStAddress = '5340 Happy Road' AND rMOZipCode = 'V1V 1V7'),
        (SELECT rMOZipCode FROM ResidentialManagingOffice WHERE rMOStAddress = '5340 Happy Road' AND rMOZipCode = 'V1V 1V7'), (SELECT bMEmployeeID FROM BuildingManager WHERE bMEmployeeID = 21));

INSERT INTO Residence
VALUES ('2333 Vine Street', 'V1V 1V7',(SELECT resName FROM ResidenceCapacity WHERE resName = 'Grapes' AND bMEmployeeID = 15),
        (SELECT rMOStAddress FROM ResidentialManagingOffice WHERE rMOStAddress = '5340 Happy Road' AND rMOZipCode = 'V1V 1V7'),
        (SELECT rMOZipCode FROM ResidentialManagingOffice WHERE rMOStAddress = '5340 Happy Road' AND rMOZipCode = 'V1V 1V7'), (SELECT bMEmployeeID FROM BuildingManager WHERE bMEmployeeID = 15));



INSERT INTO ResidenceBudget
VALUES((SELECT resStAddress FROM Residence WHERE resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Residence WHERE resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'), 50000);

INSERT INTO ResidenceBudget
VALUES((SELECT resStAddress FROM Residence WHERE resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Residence WHERE resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'), 30000);

INSERT INTO ResidenceBudget
VALUES((SELECT resStAddress FROM Residence WHERE resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Residence WHERE resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'), 200000);

INSERT INTO ResidenceBudget
VALUES((SELECT resStAddress FROM Residence WHERE resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT resZipCode FROM Residence WHERE resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'), null);

INSERT INTO ResidenceBudget
VALUES((SELECT resStAddress FROM Residence WHERE resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT resZipCode FROM Residence WHERE resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'), 40000);



--HOUSING INSERTIONS

INSERT INTO House
VALUES('Haida', 200, 'first year', null, (SELECT resStAddress FROM Residence WHERE resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Residence WHERE resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'));

INSERT INTO House
VALUES('North Tower', 400, 'upper year', 19, (SELECT resStAddress FROM Residence WHERE resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Residence WHERE resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'));

INSERT INTO House
VALUES('Building Five', 150, 'upper year', 19, (SELECT resStAddress FROM Residence WHERE resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Residence WHERE resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'));

INSERT INTO House
VALUES('Korea House', 250, 'first year', null, (SELECT resStAddress FROM Residence WHERE resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT resZipCode FROM Residence WHERE resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'));

INSERT INTO House
VALUES('Salish', 300, 'first year', null, (SELECT resStAddress FROM Residence WHERE resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT resZipCode FROM Residence WHERE resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'));



INSERT INTO Floor
VALUES(10, 20, 'male', (SELECT houseName FROM House WHERE houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM House WHERE houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM House WHERE houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'));

INSERT INTO Floor
VALUES(4, 25, null, (SELECT houseName FROM House WHERE houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM House  WHERE houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM House WHERE houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'));

INSERT INTO Floor
VALUES(15, 20, null, (SELECT houseName FROM House WHERE houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM House WHERE houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM House WHERE houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'));

INSERT INTO Floor
VALUES(6, 25, null, (SELECT houseName FROM House WHERE houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM House WHERE houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM House WHERE houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'));

INSERT INTO Floor
VALUES(16, 16, null, (SELECT houseName FROM House WHERE houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM House WHERE houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM House WHERE houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'));

INSERT INTO Floor
VALUES(2, 16, null, (SELECT houseName FROM House WHERE houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM House WHERE houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM House WHERE houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'));

INSERT INTO Floor
VALUES(9, 20, 'female', (SELECT houseName FROM House WHERE houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT resStAddress FROM House WHERE houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT resZipCode FROM House WHERE houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'));

INSERT INTO Floor
VALUES(8, 20, null, (SELECT houseName FROM House WHERE houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT resStAddress FROM House WHERE houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT resZipCode FROM House WHERE houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'));

INSERT INTO Floor
VALUES(3, 20, 'male', (SELECT houseName FROM House WHERE houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT resStAddress FROM House WHERE houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT resZipCode FROM House WHERE houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'));

INSERT INTO Floor
VALUES(2, 20, 'female', (SELECT houseName FROM House WHERE houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT resStAddress FROM House WHERE houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT resZipCode FROM House WHERE houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'));


INSERT INTO Unit
VALUES(4, 4,  'male' , 3, (SELECT fNumber FROM Floor WHERE fNumber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT houseName FROM Floor WHERE fNumber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM Floor WHERE fNumber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Floor WHERE fNumber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'));

INSERT INTO Unit
VALUES(2, 2,  'male' , 1, (SELECT fNumber FROM Floor WHERE fNumber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT houseName FROM Floor WHERE fNumber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM Floor WHERE fNumber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Floor WHERE fNumber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'));

INSERT INTO Unit
VALUES(5, 2, 'female', 1, (SELECT fNumber FROM Floor WHERE fNumber = 4 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT houseName FROM Floor WHERE fNumber = 4 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM Floor WHERE fNumber = 4 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Floor WHERE fNumber = 4 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'));


INSERT INTO Unit
VALUES(13, 4, 'male', 3, (SELECT fNumber FROM Floor WHERE fNumber = 15 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT houseName FROM Floor WHERE fNumber = 15 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM Floor WHERE fNumber = 15 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Floor WHERE fNumber = 15 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'));

INSERT INTO Unit
VALUES(3, 1, 'female', 0, (SELECT fNumber FROM Floor WHERE fNumber = 6 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT houseName FROM Floor WHERE fNumber = 6 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM Floor WHERE fNumber = 6 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Floor WHERE fNumber = 6 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'));

INSERT INTO Unit
VALUES(1, 1, 'female', 0, (SELECT fNumber FROM Floor WHERE fNumber = 16 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT houseName FROM Floor WHERE fNumber = 16 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM Floor WHERE fNumber = 16 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Floor WHERE fNumber = 16 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'));

INSERT INTO Unit
VALUES(1, 2, 'female', 0, (SELECT fNumber FROM Floor WHERE fNumber = 2 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT houseName FROM Floor WHERE fNumber = 2 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM Floor WHERE fNumber = 2 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Floor WHERE fNumber = 2 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'));

INSERT INTO Unit
VALUES(10, 6, 'female', 2, (SELECT fNumber FROM Floor WHERE fNumber = 9 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT houseName FROM Floor WHERE fNumber = 9 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT resStAddress FROM Floor WHERE fNumber = 9 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT resZipCode FROM Floor WHERE fNumber = 9 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'));

INSERT INTO Unit
VALUES(9, 2, 'female', 1, (SELECT fNumber FROM Floor WHERE fNumber = 8 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT houseName FROM Floor WHERE fNumber = 8 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT resStAddress FROM Floor WHERE fNumber = 8 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT resZipCode FROM Floor WHERE fNumber = 8 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'));

INSERT INTO Unit
VALUES(11, 3, 'female', 1, (SELECT fNumber FROM Floor WHERE fNumber = 8 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT houseName FROM Floor WHERE fNumber = 8 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT resStAddress FROM Floor WHERE fNumber = 8 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT resZipCode FROM Floor WHERE fNumber = 8 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'));


INSERT INTO Unit
VALUES(4, 2, 'male', 1, (SELECT fNumber FROM Floor WHERE fNumber = 3 AND houseName = 'Salish' AND resStAddress ='2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT houseName FROM Floor WHERE fNumber = 3 AND houseName = 'Salish' AND resStAddress ='2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT resStAddress FROM Floor WHERE fNumber = 3 AND houseName = 'Salish' AND resStAddress ='2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT resZipCode FROM Floor WHERE fNumber = 3 AND houseName = 'Salish' AND resStAddress ='2333 Vine Street' AND resZipCode = 'V1V 1V7'));

INSERT INTO Unit
VALUES(12, 1, 'female', 0, (SELECT fNumber FROM Floor WHERE fNumber = 2 AND houseName = 'Salish' AND resStAddress ='2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT houseName FROM Floor WHERE fNumber = 2 AND houseName = 'Salish' AND resStAddress ='2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT resStAddress FROM Floor WHERE fNumber = 2 AND houseName = 'Salish' AND resStAddress ='2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT resZipCode FROM Floor WHERE fNumber = 2 AND houseName = 'Salish' AND resStAddress ='2333 Vine Street' AND resZipCode = 'V1V 1V7'));


--RESIDENT INSERTIONS

INSERT INTO ResidentInfo
VALUES(23049250, 'test1@ubc.ca', 'Bill', '5/2/99', null);

INSERT INTO ResidentInfo
VALUES(23954485, 'tuu@ubc.ca', 'Will', '5/2/99', 3);

INSERT INTO ResidentInfo
VALUES(68594095, 'mar@ubc.ca', 'Maryam', '3/25/00', 2);

INSERT INTO ResidentInfo
VALUES(45069450, 'sarah@ubc.ca', 'Sarah', null, 5);

INSERT INTO ResidentInfo
VALUES(59403945, 'bobby@ubc.ca', 'Bob', '2/6/89', null);

INSERT INTO ResidentInfo
VALUES(59485932, 'joan@ubc.ca', 'Joan', '7/17/89', 3);

INSERT INTO ResidentInfo
VALUES(45544432, 'timothy@ubc.ca', 'Timothy', '12/4/93', 2);


INSERT INTO ResidentInfo
VALUES(23452034, 'au@ubc.ca', 'Arthur', '12/23/98', null);

INSERT INTO ResidentInfo
VALUES(34534605, 'katy@ubc.ca', 'Katy', '9/3/97', 2);

INSERT INTO ResidentInfo
VALUES(23490506, 'stef@ubc.ca', 'Stefani', '8/13/00', 3);

INSERT INTO ResidentInfo
VALUES(60892195, 'alex@ubc.ca', 'Alex', '1/7/98', 3);

INSERT INTO ResidentAddress
VALUES((SELECT email FROM ResidentInfo WHERE email = 'test1@ubc.ca'), (SELECT uNumber FROM Unit WHERE uNumber = 4 AND fNUmber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT fNumber FROM Unit WHERE uNumber = 4 AND fNUmber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT houseName FROM Unit WHERE uNumber = 4 AND fNUmber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM Unit WHERE uNumber = 4 AND fNUmber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Unit WHERE uNumber = 4 AND fNUmber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'));

INSERT INTO ResidentAddress
VALUES((SELECT email FROM ResidentInfo WHERE email = 'tuu@ubc.ca'), (SELECT uNumber FROM Unit WHERE uNumber = 5 AND fNumber = 4 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT fNumber FROM Unit WHERE uNumber = 5 AND fNumber = 4 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT houseName FROM Unit WHERE uNumber = 5 AND fNumber = 4 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM Unit WHERE uNumber = 5 AND fNumber = 4 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Unit WHERE uNumber = 5 AND fNumber = 4 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'));

INSERT INTO ResidentAddress
VALUES((SELECT email FROM ResidentInfo WHERE email = 'mar@ubc.ca'), (SELECT uNumber FROM Unit WHERE uNumber = 13 AND fNumber = 15 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT fNumber FROM Unit WHERE uNumber = 13 AND fNumber = 15 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT houseName FROM Unit WHERE uNumber = 13 AND fNumber = 15 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM Unit WHERE uNumber = 13 AND fNumber = 15 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Unit WHERE uNumber = 13 AND fNumber = 15 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'));

INSERT INTO ResidentAddress
VALUES((SELECT email FROM ResidentInfo WHERE email = 'katy@ubc.ca'), (SELECT uNumber FROM Unit WHERE uNumber = 3 AND fNumber = 6 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT fNumber FROM Unit WHERE uNumber = 3 AND fNumber = 6 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT houseName FROM Unit WHERE uNumber = 3 AND fNumber = 6 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM Unit WHERE uNumber = 3 AND fNumber = 6 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Unit WHERE uNumber = 3 AND fNumber = 6 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'));

INSERT INTO ResidentAddress
VALUES((SELECT email FROM ResidentInfo WHERE email = 'sarah@ubc.ca'), (SELECT uNumber FROM Unit WHERE uNumber = 1 AND fNumber = 16 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT fNumber FROM Unit WHERE uNumber = 1 AND fNumber = 16 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT houseName FROM Unit WHERE uNumber = 1 AND fNumber = 16 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM Unit WHERE uNumber = 1 AND fNumber = 16 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Unit WHERE uNumber = 1 AND fNumber = 16 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'));

INSERT INTO ResidentAddress
VALUES((SELECT email FROM ResidentInfo WHERE email = 'bobby@ubc.ca'), (SELECT uNumber FROM Unit WHERE uNumber = 10 AND fNumber = 9 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT fNumber FROM Unit WHERE uNumber = 10 AND fNumber = 9 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT houseName FROM Unit WHERE uNumber = 10 AND fNumber = 9 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT resStAddress FROM Unit WHERE uNumber = 10 AND fNumber = 9 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT resZipCode FROM Unit WHERE uNumber = 10 AND fNumber = 9 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'));

INSERT INTO ResidentAddress
VALUES((SELECT email FROM ResidentInfo WHERE email = 'stef@ubc.ca'), (SELECT uNumber FROM Unit WHERE uNumber = 11 AND fNumber = 8 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT fNumber FROM Unit WHERE uNumber = 11 AND fNumber = 8 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT houseName FROM Unit WHERE uNumber = 11 AND fNumber = 8 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT resStAddress FROM Unit WHERE uNumber = 11 AND fNumber = 8 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT resZipCode FROM Unit WHERE uNumber = 11 AND fNumber = 8 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'));

INSERT INTO ResidentAddress
VALUES((SELECT email FROM ResidentInfo WHERE email = 'joan@ubc.ca'), (SELECT uNumber FROM Unit WHERE uNumber = 1 AND fNumber = 2 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT fNumber FROM Unit WHERE uNumber = 1 AND fNumber = 2 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT houseName FROM Unit WHERE uNumber = 1 AND fNumber = 2 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM Unit WHERE uNumber = 1 AND fNumber = 2 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Unit WHERE uNumber = 1 AND fNumber = 2 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'));

INSERT INTO ResidentAddress
VALUES((SELECT email FROM ResidentInfo WHERE email = 'timothy@ubc.ca'), (SELECT uNumber FROM Unit WHERE uNumber = 4 AND fNumber = 3 AND houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT fNumber FROM Unit WHERE uNumber = 4 AND fNumber = 3 AND houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT houseName FROM Unit WHERE uNumber = 4 AND fNumber = 3 AND houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT resStAddress FROM Unit WHERE uNumber = 4 AND fNumber = 3 AND houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT resZipCode FROM Unit WHERE uNumber = 4 AND fNumber = 3 AND houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'));

INSERT INTO ResidentAddress
VALUES((SELECT email FROM ResidentInfo WHERE email = 'alex@ubc.ca'), (SELECT uNumber FROM Unit WHERE uNumber = 12 AND fNumber = 2 AND houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT fNumber FROM Unit WHERE uNumber = 12 AND fNumber = 2 AND houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT houseName FROM Unit WHERE uNumber = 12 AND fNumber = 2 AND houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT resStAddress FROM Unit WHERE uNumber = 12 AND fNumber = 2 AND houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT resZipCode FROM Unit WHERE uNumber = 12 AND fNumber = 2 AND houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'));

INSERT INTO ResidentAddress
VALUES((SELECT email FROM ResidentInfo WHERE email = 'au@ubc.ca'), (SELECT uNumber FROM Unit WHERE uNumber = 2 AND fNUmber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT fNumber FROM Unit WHERE uNumber = 2 AND fNUmber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT houseName FROM Unit WHERE uNumber = 2 AND fNUmber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM Unit WHERE uNumber = 2 AND fNUmber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Unit WHERE uNumber = 2 AND fNUmber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'));



--Advisor INSERTIONS
INSERT INTO SeniorAdvisor
VALUES(332, null, 600, (SELECT studentNumber FROM ResidentInfo WHERE studentNumber = 23049250), (SELECT houseName FROM House WHERE houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM House WHERE houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM House WHERE houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'));

INSERT INTO SeniorAdvisor
VALUES(554, 2, 500, (SELECT studentNumber FROM ResidentInfo WHERE studentNumber = 23954485), (SELECT houseName FROM House WHERE houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM House WHERE houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM House WHERE houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'));

INSERT INTO SeniorAdvisor
VALUES(668, 4, 550, (SELECT studentNumber FROM ResidentInfo WHERE studentNumber = 45544432), (SELECT houseName FROM House WHERE houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT resStAddress FROM House WHERE houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT resZipCode FROM House WHERE houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'));

INSERT INTO SeniorAdvisor
VALUES(339, 1, null, (SELECT studentNumber FROM ResidentInfo WHERE studentNumber = 59485932), (SELECT houseName FROM House WHERE houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM House WHERE houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM House WHERE houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'));

INSERT INTO SeniorAdvisor
VALUES(756, null, 250, (SELECT studentNumber FROM ResidentInfo WHERE studentNumber = 59403945), (SELECT houseName FROM House WHERE houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT resStAddress FROM House WHERE houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT resZipCode FROM House WHERE houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'));



INSERT INTO ResidenceAdvisor
VALUES(33333, null, 35, (SELECT studentNumber FROM ResidentInfo WHERE studentNumber = 23452034), (SELECT studentNumber FROM SeniorAdvisor WHERE studentNumber = 23049250),
       (SELECT fNumber FROM Floor WHERE fNumber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT houseName FROM Floor WHERE fNumber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM Floor WHERE fNumber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Floor WHERE fNumber = 10 AND houseName = 'Haida' AND resStAddress = '2205 West Mall' AND resZipCode = 'V6T 1Z4'));

INSERT INTO ResidenceAdvisor
VALUES(44344, 1, 50, (SELECT studentNumber FROM ResidentInfo WHERE studentNumber = 34534605), (SELECT studentNumber FROM SeniorAdvisor WHERE studentNumber = 23954485),
       (SELECT fNumber FROM Floor WHERE fNumber = 6 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT houseName FROM Floor WHERE fNumber = 6 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM Floor WHERE fNumber = 6 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Floor WHERE fNumber = 6 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'));

INSERT INTO ResidenceAdvisor
VALUES(43534, 4, 32, (SELECT studentNumber FROM ResidentInfo WHERE studentNumber = 68594095), (SELECT studentNumber FROM SeniorAdvisor WHERE studentNumber = 23954485),
       (SELECT fNumber FROM Floor WHERE fNumber = 15 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT houseName FROM Floor WHERE fNumber = 15 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM Floor WHERE fNumber = 15 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Floor WHERE fNumber = 15 AND houseName = 'Building Five' AND resStAddress = '2205 Lower Mall' AND resZipCode = 'V6T 1Z4'));

INSERT INTO ResidenceAdvisor
VALUES(95946, 3, 43, (SELECT studentNumber FROM ResidentInfo WHERE studentNumber = 45069450), (SELECT studentNumber FROM SeniorAdvisor WHERE studentNumber = 59485932),
       (SELECT fNumber FROM Floor WHERE fNumber = 16 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT houseName FROM Floor WHERE fNumber = 16 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT resStAddress FROM Floor WHERE fNumber = 16 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'),
       (SELECT resZipCode FROM Floor WHERE fNumber = 16 AND houseName = 'North Tower' AND resStAddress = '4545 Argronomy Road' AND resZipCode = 'V6T 1Z4'));


INSERT INTO ResidenceAdvisor
VALUES(53455, 2, 99, (SELECT studentNumber FROM ResidentInfo WHERE studentNumber = 23490506), (SELECT studentNumber FROM SeniorAdvisor WHERE studentNumber = 59403945),
       (SELECT fNumber FROM Floor WHERE fNumber = 8 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT houseName FROM Floor WHERE fNumber = 8 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT resStAddress FROM Floor WHERE fNumber = 8 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'),
       (SELECT resZipCode FROM Floor WHERE fNumber = 8 AND houseName = 'Korea House' AND resStAddress = '432 East Mall' AND resZipCode = 'V1V 1V7'));

INSERT INTO ResidenceAdvisor
VALUES(22344, null, null, (SELECT studentNumber FROM ResidentInfo WHERE studentNumber = 60892195), (SELECT studentNumber FROM SeniorAdvisor WHERE studentNumber = 45544432),
       (SELECT fNumber FROM Floor WHERE fNumber = 2 AND houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT houseName FROM Floor WHERE fNumber = 2 AND houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT resStAddress FROM Floor WHERE fNumber = 2 AND houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'),
       (SELECT resZipCode FROM Floor WHERE fNumber = 2 AND houseName = 'Salish' AND resStAddress = '2333 Vine Street' AND resZipCode = 'V1V 1V7'));
