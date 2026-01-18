import { useState } from "react";
import api from "../api/axios";

function ChatPage() {
  const [message, setMessage] = useState("");
  const [responses, setResponses] = useState([]);

  const sendMessage = async () => {
    if (!message.trim()) return;

    try {
      const res = await api.post("/api/chat", {
        message: message
      });

      setResponses(prev => [...prev, res.data.reply]);
      setMessage("");
    } catch (err) {
      console.error(err);
      alert("Unauthorized or server error");
    }
  };

  return (
    <div>
      <h2>Chat</h2>

      <input
        value={message}
        onChange={e => setMessage(e.target.value)}
        placeholder="Type your message"
      />

      <button onClick={sendMessage}>Send</button>

      <ul>
        {responses.map((r, i) => (
          <li key={i}>{r}</li>
        ))}
      </ul>
    </div>
  );
}

export default ChatPage;
