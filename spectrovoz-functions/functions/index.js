/**
 * Import function triggers from their respective submodules
 */
const { setGlobalOptions } = require("firebase-functions");
const { onRequest } = require("firebase-functions/https");
const logger = require("firebase-functions/logger");

// Inicializa o Admin SDK (não precisa de chave privada dentro do Cloud Functions!)
const admin = require("firebase-admin");
admin.initializeApp();

// Configuração global: limite de instâncias para controle de custo
setGlobalOptions({ maxInstances: 10 });

// ✅ NOSSA FUNÇÃO: Envia notificação de estresse para um dispositivo
exports.sendStressAlert = require("firebase-functions/v2/https").onCall(async (request) => {
  const { data } = request;
  const { fcmToken, message = "Sinais de estresse detectados." } = data;

  if (!fcmToken) {
    throw new require("firebase-functions/v2/https").HttpsError(
      "invalid-argument",
      "Token FCM é obrigatório"
    );
  }

  const payload = {
    notification: {
      title: "Spectro Voz",
      body: message
    },
    token: fcmToken
  };

  try {
    const response = await admin.messaging().send(payload);
    logger.info("✅ Notificação enviada com sucesso", { messageId: response });
    return { success: true, messageId: response };
  } catch (error) {
    logger.error("❌ Erro ao enviar notificação", { error: error.message });
    throw new require("firebase-functions/v2/https").HttpsError("internal", error.message);
  }
});