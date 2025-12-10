🌐 AI Document Analysis API

A full-stack AI-powered document intelligence platform built with Spring Boot, OpenAI GPT models, and a beautiful glassmorphism UI.
Upload PDFs/DOCX files → get summaries, keywords, sentiment → chat with the document → save results.
🚀 Features
🔹 AI Document Analysis

Extracts text from PDFs & DOCX using Apache Tika

Generates:

📌 Summary

🔑 Keywords

🙂 Sentiment

Ensures strict JSON output for reliability

🔹 Interactive Document Chat

Ask any question related to the uploaded document

AI responds based on context and history

Conversational memory using chat history

🔹 User Authentication

Create account

Login

Session-based user tracking

Each user has separate saved history

🔹 Modern UI (HTML + CSS + JS)

Glassmorphism design

Drag & Drop file upload

Progress bar + loading effects

Real-time chat interface

File name preview after upload

🔹 History Storage

Saves:

Filename

Summary

Timestamp

History is user-specific

🧠 Tech Stack
Backend

Java 17

Spring Boot

Spring Security (simple local auth)

OpenAI GPT API

Apache Tika (PDF/DOCX extraction)

Frontend

HTML5

CSS3 (Glassmorphism UI + glowing components)

Vanilla JavaScript (no frameworks)
📡 API Endpoints
1️⃣ Analyze Document
POST /api/v1/document/analyze


Body: Multipart file
Returns: JSON (summary, keywords, sentiment)

2️⃣ Chat with Document
POST /api/v1/document/chat


Body:

{
  "documentText": "summary or extracted text",
  "question": "your question",
  "history": []
}

🏗️ Project Structure
src/main/java/com/docai/document_analyzer/
│
├── controller/
│   └── DocumentController.java
│
├── service/
│   ├── DocumentService.java
│   ├── OpenAIService.java
│   └── KeywordService.java
│
├── util/
│   ├── PdfUtil.java
│   └── DocxUtil.java
│
├── model/
│   ├── AnalysisResponse.java
│   ├── ChatRequest.java
│   └── ChatMessage.java
│
└── config/
    └── OpenAIConfig.java

🖥️ How to Run Locally

Clone repository

Add your API key in application.properties:

openai.api.key=YOUR_KEY
openai.model=gpt-4o-mini


Run Spring Boot app:

mvn spring-boot:run


Open UI:

http://localhost:8080/login.html

📎 Future Enhancements

PDF preview window

Export summary as PDF

Vector embeddings for deeper semantic search

Admin dashboard

Switch between OpenAI / Gemini / Local LLM (Ollama)

⭐ If you find this project useful

Give it a ⭐ on GitHub — it motivates future improvements!
