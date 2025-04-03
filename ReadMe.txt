Microservice - librarymanagement
************************************************Introduction******************************************************************
A spring boot application to manage day to day activity of a library. 

Librarian  can add/modify/delete book.

Member  can checkout/reserved/return/renew books.

Scheduler Task - running at regular interval to find the blacklisted/default members.

Data Base - Mongo DB(embedded) - localhost:27017

*******************************************How To Build and Run************************************************************
How to build and Deploy - Below command will build and start spring boot application
mvn spring-boot:run

*******************************************Pre-Populate Data for testing************************************************************
Below members and books get added when application starts -
Librarian - 
Member id - "507f191e810c19729de860ea"
Password - "password"
Role - ADMIN
Status - ACTIVE
Authorization Token - Base 64 Encoded - {memberid}:{password} - NTA3ZjE5MWU4MTBjMTk3MjlkZTg2MGVhOnBhc3N3b3Jk

Member -
Member id - "507f191e810c19729de860eb"
Password - "password"
Role - BORROWER
Status - INACTIVE
Authorization Token - Base 64 Encoded - {memberid}:{password} - NTA3ZjE5MWU4MTBjMTk3MjlkZTg2MGViOnBhc3N3b3Jk

Books -
Two copies with ISBN - 123456789
i)barcode - 0000100001
ii)barcode - 000010002

Please check the last section of this file to check pre-populated data


*******************************************Testing***************************************************************
Once application is up and running, go to - http://localhost:8080/swagger-ui/#/  , Swagger-ui will be rendered
with Books Apis and Members apis

Members Apis - 
​i)POST /v1​/members - This api will be used by Librarian to create members - 
Click on "Try it out" and then "execute" - an api call to the application with default vaule -token and payload- 

This will create members with state INACTIVE

ii)POST - /v1​/members​/{memberId}​/status -This api will be used to change status of a member, being a librarian one can 
change to any state but being a member one can only change from active to canceled.

Click on "Try it out" and then "execute" - an api call to the application with default vaule -token and payload- 
This will change the status of pre-populated member to active.


Books Apis -
i)POST /v1​/books - This api will be used by Librarian to add books - 
Click on "Try it out" and then "execute" - an api call to the application with default vaule -token and payload-

a book copy with isbn -"123456789" will be added and as api-response one will get all the copies for this isbn.

ii)GET /v1​/books​/{isbn} -This api will be used by Librarian to get all books copies details by isbn 
Click on "Try it out" and then "execute" - an api call to the application with default vaule -token and payload-

iii)POST- /v1​/books​/{isbn}-This api will be used by member to checkout with given isbn.
Click on "Try it out" and then "execute" - an api call to the application with default vaule -token and payload-

book will be mapped to member id and this mapping with be stored in third  mongo collection - "transaction" ,

Please check last section of this file to see mapping sample data.

iv)DELETE -/v1​/books​/{isbn}​/{barcode} - This api will be used by Librarian to delete book - 
Click on "Try it out" and then "execute" - an api call to the application with default vaule -token and payload-

This will delete a book copy with isbn - 123456789 and barcode - 000010002

********************************pre-populated data****************************************************************

connect to data base on cross check data - 
> show dbs ;
admin        0.000GB
librarymgmt  0.000GB
local        0.000GB
>

> use librarymgmt 
switched to db librarymgmt
>

> db.member.find().pretty()
{
	"_id" : ObjectId("507f191e810c19729de860ea"),
	"password" : [
		"p",
		"a",
		"s",
		"s",
		"w",
		"o",
		"r",
		"d"
	],
	"contactNumber" : "9191919191",
	"emailId" : "testmail@test.com",
	"address" : {
		"addressLine1" : "House no. 212",
		"addressLine2" : "2nd floor",
		"area" : "HighLake Stree",
		"city" : "County01",
		"state" : "MI",
		"country" : "USA",
		"zipCode" : "000001"
	},
	"membershipStartTime" : ISODate("2020-12-02T10:25:27.518Z"),
	"membershipEndTime" : ISODate("2030-12-02T10:25:27.518Z"),
	"role" : "ADMIN",
	"status" : "ACTIVE",
	"_class" : "com.librarymanagement.entities.Member"
}
{
	"_id" : ObjectId("507f191e810c19729de860eb"),
	"password" : [
		"p",
		"a",
		"s",
		"s",
		"w",
		"o",
		"r",
		"d"
	],
	"contactNumber" : "9292929292",
	"emailId" : "testmail01@test.com",
	"address" : {
		"addressLine1" : "House no. 212",
		"addressLine2" : "2nd floor",
		"area" : "HighLake Stree",
		"city" : "County01",
		"state" : "MI",
		"country" : "USA",
		"zipCode" : "000001"
	},
	"membershipStartTime" : ISODate("2020-12-02T10:25:27.689Z"),
	"membershipEndTime" : ISODate("2030-12-02T10:25:27.689Z"),
	"role" : "BORROWER",
	"status" : "INACTIVE",
	"_class" : "com.librarymanagement.entities.Member"
}
>

> db.book.find().pretty()
{
	"_id" : ObjectId("5fc76b976caac74af1b2bf66"),
	"isbn" : "123456789",
	"title" : "JAVA FIRST",
	"subject" : "JAVA",
	"publisher" : "Good Books",
	"language" : "English",
	"numberOfPages" : 0,
	"authors" : [
		"James Gosling"
	],
	"bookCopy" : [
		{
			"currentStatus" : "AVAILABLE",
			"isReserved" : false,
			"price" : 1000,
			"dateOfPurchase" : ISODate("2020-11-01T18:30:00Z"),
			"publicationDate" : ISODate("2020-09-02T18:30:00Z"),
			"isbn" : "123456789",
			"title" : "JAVA FIRST",
			"barcode" : "0000100001",
			"subject" : "JAVA",
			"language" : "English",
			"numberOfPages" : 1000,
			"authors" : [
				"James Gosling"
			],
			"bookCopy" : [ ],
			"placedAt" : 1
		},
		{
			"currentStatus" : "AVAILABLE",
			"isReserved" : false,
			"price" : 1020,
			"dateOfPurchase" : ISODate("2020-10-22T18:30:00Z"),
			"publicationDate" : ISODate("2020-08-23T18:30:00Z"),
			"isbn" : "123456789",
			"title" : "JAVA FIRST",
			"barcode" : "000010002",
			"subject" : "JAVA",
			"language" : "English",
			"numberOfPages" : 1000,
			"authors" : [
				"James Gosling"
			],
			"bookCopy" : [ ],
			"placedAt" : 1
		}
	],
	"placedAt" : 1,
	"_class" : "com.librarymanagement.entities.Book"
}
>

> db.transaction.find().pretty()
{
	"_id" : ObjectId("507f191e810c19729de860eb"),
	"bookTransactions" : [
		{
			"isbn" : "123456789",
			"barcode" : "0000100001",
			"borrowedOn" : ISODate("2020-12-01T18:30:00Z"),
			"dueDate" : ISODate("2020-12-16T18:30:00Z"),
			"charge" : 20,
			"status" : "CHECKEDOUT"
		},
		{
			"isbn" : "123456789",
			"barcode" : "000010002",
			"borrowedOn" : ISODate("2020-12-01T18:30:00Z"),
			"dueDate" : ISODate("2020-12-16T18:30:00Z"),
			"charge" : 20,
			"status" : "CHECKEDOUT"
		},
		{
			"isbn" : "123456789",
			"barcode" : "0000100001",
			"borrowedOn" : ISODate("2020-12-01T18:30:00Z"),
			"dueDate" : ISODate("2020-12-16T18:30:00Z"),
			"charge" : 20,
			"status" : "RESERVED"
		},
		{
			"isbn" : "123456789",
			"barcode" : "000010002",
			"borrowedOn" : ISODate("2020-12-01T18:30:00Z"),
			"dueDate" : ISODate("2020-12-16T18:30:00Z"),
			"charge" : 20,
			"status" : "RESERVED"
		}
	],
	"_class" : "com.librarymanagement.entities.Transaction"
}
> 


Other Details -
Java - 1.8.0_151
Spring boot - 2.4.0
Maven - 3.5.0











