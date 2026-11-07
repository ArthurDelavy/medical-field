# MedicalField - Prontuário Médico de Emergência

> **Disciplina:** Arquitetura de Software - Univates (2026/B)  
> **Professor:** Fabrício Pretto  
> **Tema:** Sistema Offline-First de Atendimento Pré-Hospitalar com Ecossistema Backend Multilinguagem.

---

## 📸 Visão Geral do Sistema

O **MedicalField** é uma plataforma desenvolvida para prover suporte a equipes de resgate e socorristas (ex.: SAMU, ambulâncias em rodovias, áreas de desastre) que atuam em cenários com conectividade de internet instável ou inexistente.

A solução adota uma abordagem **Offline-First**, permitindo a gravação de dados vitais (sinais vitais, Escala de Glasgow, triagem Manchester/START, medicamentos) em um banco local no dispositivo do socorrista. Assim que a conexão com a internet é restabelecida, o sistema sincroniza os dados pendentes com o servidor central, garantindo idempotência e auditoria.

---

## 🏛️ Arquitetura do Sistema

O ecossistema é composto por **3 microsserviços backend em linguagens distintas**, uma **Interface Web Administrativa (Portal)** e uma **Aplicação Cliente (Socorrista)** com banco local embarcado e simulador de rede.

```text
                               ┌─────────────────────────────────────────┐
                               │       Portal Admin Web (React/Next)     │
                               └────────────────────┬────────────────────┘
                                                    │ (HTTP REST)
┌─────────────────────────────┐                     │
│  Cliente Socorrista (React) │                     │
│ (Offline-First + IndexedDB) │                     │
└──────────────┬──────────────┘                     │
               │ HTTP / REST                        │
               ▼                                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                       NUVEM / VM UNIVATES (SERVIDORES)                  │
│                                                                         │
│  ┌───────────────────────┐ ┌──────────────────────┐ ┌─────────────────┐ │
│  │ Servicio 1: JAVA      │ │ Servicio 2: NODE.JS  │ │ Servicio 3:     │ │
│  │ (Spring Boot)         │ │ (Express/TS)         │ │ PYTHON (FastAPI)│ │
│  ├───────────────────────┤ ├──────────────────────┤ ├─────────────────┤ │
│  │ • Auth (JWT)          │ │ • Atendimentos       │ │ • Geração PDF   │ │
│  │ • Pessoas / Pacientes │ │ • Sync de Fila Off   │ │ • Envio E-mail  │ │
│  │ • Medicamentos        │ │ • Ingestão de Logs   │ │   (SMTP)        │ │
│  └───────────┬───────────┘ └──────────┬───────────┘ └────────┬────────┘ │
│              │                        │                      │          │
│              └────────────────────────┼──────────────────────┘          │
│                                       ▼                                 │
│                       ┌───────────────────────────────┐                 │
│                       │ Database Central (PostgreSQL) │                 │
│                       └───────────────────────────────┘                 │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 🛠️ Tecnologias e Divisão de Responsabilidades

### Backend (Multi-Linguagem)

1. **Serviço 1 — Auth & Core Domain (`Java` / Spring Boot)**
   * **Porta por padrão:** `8080`
   * **Responsabilidades:** Autenticação central de profissionais (CRM, COREN, ID Socorrista), emissão de tokens JWT, cadastro e consulta de pessoas/pacientes, prontuários e catálogo de medicamentos.
   * **Endpoints:**
     * `POST /auth/login` (Público)
     * `GET /medicamento` (Autenticado)
     * `POST /pessoa` (Autenticado)
     * `GET /prontuario` / `POST /prontuario` / `GET /prontuario/{id}` (Autenticado)

2. **Serviço 2 — Attendance & Sync (`Node.js` / Express + TypeScript)**
   * **Porta por padrão:** `3000`
   * **Responsabilidades:** Gestão dos registros de atendimento de emergência, sincronização de fichas gravadas offline e ingestão de logs remotos enviados pelos clientes.
   * **Endpoints:**
     * `POST /atendimento` (Autenticado)
     * `GET /atendimento/{id}` (Autenticado)
     * `POST /log` (Autenticado - Recepção de logs auditáveis offline)

3. **Serviço 3 — Documents & Notification (`Python` / FastAPI)**
   * **Porta por padrão:** `8000`
   * **Responsabilidades:** Processamento assíncrono para geração da Ficha de Atendimento em PDF (`ReportLab`/`pdfkit`) e disparo de e-mails com anexo (`smtplib`).
   * **Endpoints:**
     * `GET /atendimento/{id}/pdf` (Autenticado)
     * `POST /atendimento/{id}/email` (Autenticado)

---

### Frontend & Persistência Local

* **Portal Admin (Web Server):** Desenvolvido em **React / Next.js**. Conecta-se diretamente aos microsserviços centrais para gerenciamento da equipe hospitalar.
* **Cliente Socorrista (App Mobile/Desktop Web):** Desenvolvido em **React** com **Dexie.js (IndexedDB)** como banco embarcado no navegador.
  * **Simulador de Conectividade:** Toggle na interface para transição manual entre os modos `ONLINE` e `OFFLINE`.
  * **Fila Local:** Em modo `OFFLINE`, as gravações são persistidas no IndexedDB. Ao retornar para `ONLINE`, os dados são transmitidos automaticamente para a API central.

---

## 🔒 Segurança (JWT) e Auditoria LGPD

### Autenticação Única
* O login é realizado no serviço Java (`POST /auth/login`), que valida o usuário e gera um token JWT assinado com uma chave secreta (`SECRET_KEY`).
* Todos os serviços (**Node.js** e **Python**) contam com bibliotecas de validação JWT (`jsonwebtoken` e `PyJWT`) compartilhando a mesma `SECRET_KEY`, eliminando a necessidade de refazer mecanismos de login.

### Middlewares de Log de Auditoria
Todos os 3 serviços backend contam com um middleware interceptor que registra cada requisição HTTP recebida com os seguintes atributos obrigatórios:

```json
{
  "timestamp": "2026-11-07T14:30:00Z",
  "client_ip": "192.168.1.50",
  "id_profissional": "CRM-RS-998877",
  "http_method": "POST",
  "route": "/atendimento",
  "status_code": 201,
  "execution_time_ms": 35
}
```

---

## 📁 Estrutura do Repositório (Monorepo)

```text
medicalfield/
├── backend-auth-java/           # Spring Boot (Porta 8080)
├── backend-attendance-node/     # Express / TypeScript (Porta 3000)
├── backend-documents-python/    # FastAPI (Porta 8000)
├── frontend-portal-admin/       # Portal Web (React / Next.js)
├── frontend-client-socorrista/  # App Cliente (React + IndexedDB)
└── README.md
```

---

## 🚀 Como Executar o Projeto Localmente

### Pré-requisitos
* Java JDK 17+
* Node.js 18+ & npm/yarn
* Python 3.10+
* PostgreSQL (ou banco relacional configurado)

### 1. Inicializando o Backend Java (Spring Boot)
```bash
cd backend-auth-java
./mvnw spring-boot:run
```

### 2. Inicializando o Backend Node.js (Express)
```bash
cd backend-attendance-node
npm install
npm run dev
```

### 3. Inicializando o Backend Python (FastAPI)
```bash
cd backend-documents-python
python -m venv venv
source venv/bin/activate # ou venv\Scripts\activate no Windows
pip install -r requirements.txt
uvicorn main:app --reload --port 8000
```

### 4. Inicializando os Frontends (React)
```bash
# Terminal 1 - Portal Admin
cd frontend-portal-admin
npm install
npm run dev

# Terminal 2 - Cliente Socorrista
cd frontend-client-socorrista
npm install
npm run dev
```

---

## 📌 Padrões de Projeto Aplicados

1. -