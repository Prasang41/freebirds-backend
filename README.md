FreeBirds - Job & Proposal Management Platform

FreeBirds is an application designed to streamline job postings, proposals, and contracts. It allows users to register, post jobs, submit proposals, and manage notifications seamlessly.


---

Tech Stack

Backend: Java, Spring Boot, Hibernate, JPA

Database: PostgreSQL

Authentication & Security: JWT

Email Notifications: Spring Mail (SMTP)

File Uploads: Spring Multipart



---

Features

1. User Management

Register and login with JWT authentication

Role-based access control (Admin, User)


2. Job Management

Post jobs with detailed descriptions

View all jobs (viewalljobs.html)

Update and delete job postings


3. Proposal Management

Submit proposals for posted jobs

Manage contracts and approvals

Receive notifications on proposal status


4. Email & Notification Service

Email notifications for registration, proposals, and contracts

Real-time alerts for users


5. File Uploads

Upload images or documents up to 2GB


Project Structure

FreeBirds/  
├── src/main/java/com/FreeBirds/FreeBirds/  
│   ├── controllers/      # REST  controllers  
│   ├── dtos/             # Data Transfer Objects  
│   ├── entities/         # Database entities  
│   ├── enums/            # Enum classes  
│   ├── exceptions/       # Custom exceptions  
│   ├── repository/       # JPA Repositories  
│   ├── services/         # Service interfaces  
│   └── services/impl/    # Service implementations  
├── src/main/resources/  
│   ├── application.properties  
│   └── templates/  
└── pom.xml               # Maven dependencies


---

Setup Instructions

1. Clone the repository

git clone https://github.com/yourusername/FreeBirds.git
cd FreeBirds

2. Database Setup

Create a database (freebirds) in PostgreSQL

Update your application.properties with credentials or environment variables


3. Configure Environment Variables (Recommended for Secrets)

DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
JWT_SECRET=your_jwt_secret
EMAIL_USERNAME=your_email
EMAIL_PASSWORD=your_email_app_password

4. Run the Application

mvn clean install
mvn spring-boot:run

5. Access the App

Open http://localhost:8080 in your browser


---

Environment & Dependencies

Java 17+

Spring Boot 3.x

Hibernate/JPA

Maven



---

Contributing

Fork the repository

Create a new branch for your feature

Commit your changes

Create a pull request
