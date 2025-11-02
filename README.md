# 🍹 DrinkApp — The Ultimate Drink Challenge with Friends

---

## 🧭 Overview

**DrinkApp** is a social competition platform where friends can **challenge each other to drink more, share experiences, and earn points** 🍻.  
Users can post their favorite drinks, track scores, comment, and react — making it part social app, part friendly drinking contest.  

Built with **Spring Boot (Java 21)** and **React**, DrinkApp combines social networking features with a competitive twist — because who doesn’t love a bit of fun with friends? 😎

---

## ⚙️ Tech Stack

### 🧩 Backend
- **Java 21**, **Spring Boot 3.x**
- **Spring Security + JWT** for authentication  
- **Spring Data JPA / Hibernate**
- **Lombok**, **MapStruct**
- **MySQL** (development) / **PostgreSQL** (production)
- **Flyway** for schema migrations  
- **Maven** build tool  

### 🎨 Frontend
- **React + Vite**
- **TailwindCSS** for UI
- **Axios / Fetch API** for backend calls
- **.env** configuration for endpoints

---

## 🍻 Main Features

| Module | Description |
|---------|-------------|
| 🔐 **Authentication** | Register / Login with JWT |
| 🧍‍♂️ **Profiles** | Each user has a profile with picture & drink stats |
| 🍺 **Posts** | Share what you’re drinking — text or photo |
| 💬 **Comments** | React and comment on friends’ posts |
| 🥂 **Friendships** | Send, accept, or decline friend requests |
| 🔔 **Notifications** | Real-time system powered by a `NotificationSenderFactory` |
| 🏆 **Challenges** | Compete with your friends to see who drinks more (measured in points) |
| 🎯 **Feed** | Aggregates posts from you and your friends, newest first |
