import { useState } from "react";
import { sendChatMessage } from "../services/chatService";

function ChatPage() {
  const [message, setMessage] = useState("");
  const [responses, setResponses] = useState([]);

  // ✅ THIS is where your code belongs
  const handleSend = async () => {
    if (!message.trim()) return;

    try {
      const res = await sendChatMessage(message);
      setResponses(prev => [...prev, res.data.response]);
      setMessage("");
    } catch (err) {
      console.error("Chat error:", err);
      alert("Failed to send message");
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Chat</h2>

      <input
        type="text"
        value={message}
        onChange={e => setMessage(e.target.value)}
        placeholder="Type your message"
      />

      <button onClick={handleSend}>Send</button>

      <ul>
        {responses.map((r, i) => (
          <li key={i}>{r}</li>
        ))}
      </ul>
    </div>
  );
}

export default ChatPage;
