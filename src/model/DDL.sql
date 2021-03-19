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
			PRIMARY KEY(resStAddress, resZipCode, budget),
            FOREIGN KEY(resStAddress, resZipCode) REFERENCES Residence(resStAddress, resZipCode) ON DELETE CASCADE
            
);


--- END RESIDENCE TABLES

CREATE TABLE House(
			name 	   	   		char(20),
			capacity	   		int NOT NULL,
			type		   		char(20) NOT NULL,
			ageRestriction		int,
			resStAddress	    char(40),
			resZipCode			char(7),
			PRIMARY KEY(name, resStAddress, resZipCode),
			FOREIGN KEY(resStAddress, resZipCode) REFERENCES Residence(resStAddress, resZipCode) ON DELETE CASCADE
);							


CREATE TABLE Floor(
			fNumber			  	int,
			capacity		  	int NOT NULL,
			gengerRestriction 	char(10),
			houseName		  	char(20),
			resStAddress		char(40),
			resZipCode			char(7),
			PRIMARY KEY(fNumber, houseName, resStAddress, resZipCode),
			FOREIGN KEY(houseName, resStAddress, resZipCode) REFERENCES House(name, resStAddress, resZipCode) ON DELETE CASCADE
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
			FOREIGN KEY(supervisingHouseName, supervisingResStAddress, supervisingResZipCode) REFERENCES House(name, resStAddress, resZipCode) ON DELETE SET NULL
);

-- Assertion to make sure 1..n with RA

CREATE TABLE ResidenceAdvisor(
			rAEmployeeId 			int NOT NULL UNIQUE, 
			yearsOfExperience   	int, 
			individualBudget    	int, 
			studentNumber       	int PRIMARY KEY, 
			sRAStudentNumber		int NOT NULL,
			supervisingFloorNum 	int, 
			supervisingHouseName 	char(20),
			supervisingResStAddress char(20),
			supervisingResZipCode	char(7),
			UNIQUE(supervisingHousename, supervisingResStAddress, supervisingResZipCode, supervisingFloorNum),
			FOREIGN KEY(studentNumber) REFERENCES ResidentInfo(studentNumber) ON DELETE CASCADE,
			FOREIGN KEY(sRAStudentNumber) REFERENCES SeniorAdvisor(studentNumber),
            FOREIGN KEY(supervisingFloorNum, supervisingHouseName, supervisingResStAddress, supervisingResZipCode) REFERENCES Floor(fNumber, Housename, resStAddress, resZipCode) ON DELETE SET NULL
);								