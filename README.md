# 🧘‍♂️ Mind & Body Scheduler (Bip App)

Um aplicativo mobile híbrido desenvolvido com **Ionic**, **TypeScript** e **Capacitor**, projetado para criar uma rotina de bem-estar personalizada. O app calcula intervalos dinâmicos ao longo do dia para emitir bips e notificações nativas, incentivando hábitos saudáveis como correção postural ("desencoste seus dentes"), meditação, hidratação e treinos pélvicos.

---

## 🚀 Funcionalidades

*   **Configuração de Janela Ativa:** Definição personalizada de horário de acordar (`Wake Time`) e horário de dormir (`Sleep Time`).
*   **Algoritmo de Agendamento Diário (`generateDailySchedule`):** Calcula e sorteia horários de forma inteligente dentro da janela ativa para evitar bips repetitivos ou previsíveis.
*   **Notificações Nativas:** Integração com o sistema de alarmes do Android via plugins do Capacitor para garantir bips mesmo com o aplicativo fechado.

---

## 🛠️ Tecnologias Utilizadas

*   **Framework:** Ionic (com componentes nativos)
*   **Linguagem:** TypeScript / JavaScript
*   **Ponte Nativa:** Capacitor (Plugins de Notificação Local e Alarmes)
*   **Inteligência de Código:** Desenvolvido com auxílio do Antigravity CLI (Gemini 3.5 Flash)

---

## 🤖 Como Configurar e Executar

### Pré-requisitos
Certifique-se de ter o Node.js, CLI do Ionic e o Android Studio configurados na sua máquina.

### 1. Instalar as Dependências
```bash
npm install
