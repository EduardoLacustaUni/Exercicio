# Exercicio
App Android para propor atividades durante o dia.

# Kegel & Meditação - App de Micro-Hábitos

Um aplicativo Android minimalista e moderno projetado para integrar micro-hábitos de saúde e bem-estar à sua rotina diária de forma orgânica. O app agenda notificações ao longo do dia para a execução de exercícios pélvicos (Kegel) e pausas para meditação, respeitando rigorosamente o seu período de sono.

## 🚀 Funcionalidades Principais

*   **Janela de Tempo Inteligente:** Define automaticamente (ou via configurações) o horário em que você acorda e dorme para garantir que nenhum alerta dispare durante o seu descanso.
*   **Distribuição Aleatória Anti-Colisão:** Um algoritmo exclusivo divide o seu tempo ativo em blocos e sorteia os horários dos alertas, garantindo que as notificações não caiam coladas umas às outras.
*   **Sessão Ativa Integrada:** Ao receber o aviso ou iniciar manualmente, o app exibe um cronômetro regressivo na tela (2 a 3 minutos para Kegel e 5 minutos para meditação) com instruções rápidas.
*   **Histórico de Progresso:** Um painel limpo para acompanhar quantas atividades foram concluídas ao longo do dia.
*   **Persistência Local:** Seus dados e configurações ficam salvos de forma segura no dispositivo (`LocalStorage`).

## 🧠 O Motor do App: Como funciona o Agendamento?

Para evitar o incômodo de notificações em momentos inoportunos ou muito próximas, o arquivo `scheduler.ts` utiliza a técnica de **Segmentação de Tempo**:
1. O período em que você está acordado (ex: 14 horas = 840 minutos) é dividido uniformemente pelo número total de alertas do dia.
2. Dentro de cada "fatia" de tempo resultante, o algoritmo aplica uma margem de segurança de 15% nas extremidades.
3. O horário do alerta é sorteado dentro desse miolo seguro.
4. Caso a matemática resulte em um intervalo menor que **15 minutos** entre duas tarefas, o app empurra o próximo aviso automaticamente para frente, blindando sua rotina.

## 🛠️ Tecnologias Utilizadas

*   **Core:** React Web com TypeScript
*   **Estilização:** Tailwind CSS (com tema Escuro focado em economia de bateria)
*   **Ícones:** Lucide React
*   **Moldura Nativa:** @capacitor/core & @capacitor/android (para envelopamento nativo Android)
*   **Automação / CI:** GitHub Actions (para compilação do APK inteiramente na nuvem)

## 📦 Como gerar o seu arquivo .apk (Sem instalar nada no PC)

Este projeto está configurado para compilar o aplicativo direto pelos servidores do GitHub. Você só precisa do seu navegador:

1. Suba este código para o seu repositório do GitHub.
2. Certifique-se de que o arquivo de automação esteja no caminho `.github/workflows/android-build.yml`.
3. No topo do seu repositório no GitHub, clique na aba **Actions**.
4. Selecione o pipeline **"Build Android APK (Capacitor)"** na lateral esquerda.
5. Se o build não iniciar sozinho, clique em **Run workflow**.
6. Aguarde de 3 a 5 minutos. Quando o robô terminar (ficar com um check verde), role a página até o final e, na seção **Artifacts**, clique em `app-debug-apk` para baixar o arquivo direto para o seu celular Android!

---
*Desenvolvido como um protótipo leve e focado em performance.*
