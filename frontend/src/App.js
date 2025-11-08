import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import HomePage from "./pages/HomePage";
import RegisterPage from "./pages/RegisterPage";
import LoginPage from "./pages/LoginPage";
import NavBar from './components/NavBar';

function App() {
  // 🧪 CORS test: runs once when app starts
  useEffect(() => {
    fetch("http://localhost:8080/api/test", { method: "GET" })
      .then((res) => res.text())
      .then((data) => console.log("✅ Backend says:", data))
      .catch((err) => console.error("❌ CORS error:", err));
  }, []);
  return (
    <Router>
      <div className="App">
        <NavBar />
        <main className="container">
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/login" element={<LoginPage />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
