# Spectro Voz

Um aplicativo Android que analisa o espectro da voz do usuário para identificar e monitorar emoções, com a capacidade de notificar cuidadores.

## Sobre o Projeto

O Spectro Voz é um projeto de aplicativo móvel desenvolvido para a plataforma Android. Sua principal função é gravar a voz do usuário, processá-la e analisar o espectro vocal para detectar padrões associados a diferentes estados emocionais (como ansiedade, neutralidade, etc.).

O aplicativo oferece um feedback visual sobre a emoção detectada, armazena um histórico das análises para acompanhamento e inclui um sistema de alerta para notificar um cuidador ou pessoa de confiança caso certos padrões sejam identificados.

## Funcionalidades

*   **Gravação de Voz:** Interface simples para o usuário gravar sua voz descrevendo como se sente.
*   **Análise de Emoção:** Utiliza um backend em nuvem (Firebase Cloud Functions) para processar o áudio e identificar o estado emocional.
*   **Visualização de Resultados:** Apresenta a emoção detectada de forma clara e visual, utilizando gráficos para detalhar a análise.
*   **Histórico de Análises:** Mantém um registro das gravações e resultados anteriores para que o usuário possa acompanhar seu estado emocional ao longo do tempo.
*   **Sistema de Alertas:** Permite configurar o e-mail de um cuidador para enviar notificações em situações específicas.
*   **Onboarding Personalizado:** Tela de configuração inicial para coletar informações básicas e personalizar a experiência do usuário.

## Tecnologias Utilizadas

*   **Linguagem:** Kotlin
*   **UI/Componentes:** Android Jetpack, Material Design Components
*   **Backend & Banco de Dados:** Firebase (Cloud Firestore, Cloud Functions, Firebase Messaging)
*   **Gráficos:** MPAndroidChart
