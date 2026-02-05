import api from "../api/axios";

export const sendChatMessage = (message) => {
  return api.post("/api/chat", {
    message,
  });
};